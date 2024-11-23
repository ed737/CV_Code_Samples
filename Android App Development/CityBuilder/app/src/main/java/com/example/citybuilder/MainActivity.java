
package com.example.citybuilder;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import java.io.File;
import static com.example.citybuilder.Constants.*;

public class MainActivity
        extends AppCompatActivity
        implements SettingsFragment.SettingsFragmentListener, TitleFragment.TitleFragmentListener {

    private SettingsFragment settingsFragment;
    private TitleFragment titleFragment;
    private Settings settings;
    private FrameLayout frameA;
    private FragmentManager fm;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // setup fragment manager and frames
        fm = getSupportFragmentManager();
        frameA = findViewById(R.id.FrameA);

        // restore data from savedInstanceState
        if(savedInstanceState!= null){
            titleFragment = new TitleFragment();
            settingsFragment = (SettingsFragment) fm.getFragment(savedInstanceState, SETTINGS_FRAGMENT);
            String currentFrag = savedInstanceState.getString(CURRENT_FRAGMENT);

            // restore settings
            settings = savedInstanceState.getParcelable(SETTINGS_PARCEL);


            // restore the current fragment in frame A
            if(currentFrag.equals(SETTINGS_FRAGMENT)){
                fm.beginTransaction().replace(R.id.FrameA, settingsFragment).commit();
            }
            else{
                fm.beginTransaction().replace(R.id.FrameA, titleFragment).commit();
            }
        }


        else{

            // if this is first startup create a new default settings object
            // this is passed to mapActivity via parcelable
            settings = new Settings(PLAYER_NAME);

            // setup fragments
            titleFragment = new TitleFragment();
            settingsFragment = new SettingsFragment();
            fm.beginTransaction().replace(R.id.FrameA, titleFragment).commit();


        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {

        // save the current settings
        outState.putParcelable(SETTINGS_PARCEL, settings);

        // find instance of current frag in frame A
        Fragment myFrag = fm.findFragmentById(R.id.FrameA);

        // save the current fragment state in FrameA
        if(myFrag instanceof SettingsFragment){
            fm.putFragment(outState, SETTINGS_FRAGMENT, settingsFragment);
            outState.putString(CURRENT_FRAGMENT, SETTINGS_FRAGMENT);
        }
        else{
            outState.putString(CURRENT_FRAGMENT, TITLE_FRAGMENT);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public Settings getSettings() {
        return settings;
    }


    /*********************************
     * Event handlers
     *********************************/
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Fragment myFrag = fm.findFragmentById(R.id.FrameA);

        if (myFrag instanceof SettingsFragment) {
            fm.beginTransaction().replace(R.id.FrameA, titleFragment).commit();
        } else {

            // create an alert dialog to verify exit
            // adapted from code at:
            // https://stackoverflow.com/questions/6413700/android-proper-way-to-use-onbackpressed-with-toast
            // accessed 8/10/2020
            new AlertDialog.Builder(this).setMessage("Are you sure you want to exit?")
                    .setPositiveButton("Exit", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            // save user settings here.
                            MainActivity.super.onBackPressed();
                        }

                    })

                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).create().show();
        }
    }

    @Override
    public void resumeGame() {
        Intent intent = new Intent(MainActivity.this, MapActivity.class)
                .putExtra(MAIN_ACTIVITY_CHOICE, RESUME_GAME);
        startActivity(intent);
    }

    @Override
    public void launchSettingFragment() {
        fm.beginTransaction().replace(R.id.FrameA, settingsFragment).commit();
    }

    @Override
    public void checkDatabase() {

        // check if the database file exists and database contains data
        File dbFile = getDatabasePath("city_builder.db");
        if(dbFile.exists()) {
            db = new CityBuilderDBHelper(getApplicationContext()).getReadableDatabase();

            // select all entries from gameDataTable
            Cursor myCursor = db.rawQuery(" SELECT * FROM " + DBSchema.CityBuilderSchema.GameDataTable.NAME, null);
            if (!myCursor.moveToFirst()) {

                // if cursor is empty
                titleFragment.setResumeUnClickable();
                System.out.println("Database is empty");
            }
            myCursor.close();
            db.close();
        }
        else{
            titleFragment.setResumeUnClickable();
            System.out.println("Database does not exist");
        }
    }

    @Override
    public void startNewGame() {
        Intent intent = new Intent(MainActivity.this, MapActivity.class)
                .putExtra(SETTINGS_PARCEL, settings) // attach parcelable settings
                .putExtra(MAIN_ACTIVITY_CHOICE, NEW_GAME);
        startActivity(intent);
    }

}