/************************************
 * Map activity, all further functionality
 * controlled from this activity. All
 * further screens in fragments
 *************************************/

package com.example.citybuilder;
import static androidx.core.content.ContextCompat.startActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.view.View;
import android.widget.FrameLayout;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

// imports of constants from Constants.java for strings in parcelable implementation
import static com.example.citybuilder.Constants.*;

public class MapActivity
        extends AppCompatActivity
        implements MapAdapter.MapAdapterListener,
        MapFragment.MapFragListener, LinearLayoutAdapter.LinearLayoutListener,
        ResidentialFragment.ResidentialFragListener, CommercialFragment.CommercialFragListener,
        RoadFragment.RoadFragListener, OptionsFragment.OptionsFragListener,
        DataFragment.DataFragListener, DetailsFragment.DetailsFragmentListener {

    private static GameData gameData;
    private int orientation;
    private FrameLayout optionsFrame;
    private FrameLayout mapFrame;
    private FrameLayout selectionFrame;
    private FrameLayout dataFrame;
    private FrameLayout detailsFrame;
    private MapFragment mapFragment;
    private RoadFragment roadFragment;
    private ResidentialFragment residentialFragment;
    private CommercialFragment commercialFragment;
    private OptionsFragment optionsFragment;
    private DataFragment dataFragment;
    private DetailsFragment detailsFragment;
    private FragmentManager fm;
    private Settings settings;
    private HardDataCreator hd;
    private Structure currentStructure;

    // timer variables
    private CountDownTimer timer;
    private static final long TIMER_COUNTDOWN = 12000;
    private static final int TIMER_INTERVAL = 1000;
    private long timeRemainingMillis = TIMER_COUNTDOWN;
    private static final int REQUEST_PHOTO_CAPTURE = 101;

    // async references
    private GetTemperature getTemperature;
    private UpdateDatabaseAsync updateDatabaseAsync;
    private CreateNewDatabaseAsync createNewDatabaseAsync;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        // get current device orientation
        orientation = this.getResources().getConfiguration().orientation;

        // setup frames.
        optionsFrame = (FrameLayout) findViewById(R.id.optionsFrame);
        mapFrame = (FrameLayout) findViewById(R.id.mapFrame);
        dataFrame = (FrameLayout) findViewById(R.id.dataFrame);
        selectionFrame = (FrameLayout) findViewById(R.id.selectionFrame);
        detailsFrame = (FrameLayout) findViewById(R.id.detailsFrame);

        fm = getSupportFragmentManager();


    /************************************
     * Case 1, screen has been rotated,
     * or other config change.
     *************************************/
        if(savedInstanceState != null){

            // restore game dependencies
            timeRemainingMillis = savedInstanceState.getLong(CURRENT_TIME);

            //gameData = new GameData(this.getApplicationContext());
            hd = new HardDataCreator(gameData.getSettings());

            // setup fragments
            mapFragment = (MapFragment) fm.getFragment(savedInstanceState, MAP_FRAGMENT);
            optionsFragment = (OptionsFragment) fm.getFragment(savedInstanceState, OPTIONS_FRAGMENT);
            dataFragment = (DataFragment) fm.getFragment(savedInstanceState, DATA_FRAGMENT);
            roadFragment = (RoadFragment) fm.getFragment(savedInstanceState, ROAD_FRAGMENT);
            residentialFragment = (ResidentialFragment) fm.getFragment(savedInstanceState, RESIDENTIAL_FRAGMENT);
            commercialFragment = (CommercialFragment) fm.getFragment(savedInstanceState, COMMERCIAL_FRAGMENT);
            detailsFragment = (DetailsFragment) fm.getFragment(savedInstanceState, DETAILS_FRAGMENT);

            fm.beginTransaction().replace(R.id.mapFrame, mapFragment).commit();
            fm.beginTransaction().replace(R.id.optionsFrame, optionsFragment).commit();
            fm.beginTransaction().replace(R.id.dataFrame, dataFragment).commit();
            fm.beginTransaction().replace(R.id.detailsFrame, detailsFragment).commit();


            /**********************************************************
             * visibility method of fragment replacement using frames
             **********************************************************/
            String currentVisibility = savedInstanceState.getString(CURRENT_VISIBILITY);

            if(currentVisibility == DETAILS_FRAGMENT){
                mapFrame.setVisibility(View.GONE);
                dataFrame.setVisibility(View.GONE);
                selectionFrame.setVisibility(View.GONE);
                optionsFrame.setVisibility(View.GONE);
                detailsFrame.setVisibility(View.VISIBLE);
            }
            else{
                detailsFrame.setVisibility(View.GONE);
                dataFrame.setVisibility(View.VISIBLE);
                optionsFrame.setVisibility(View.VISIBLE);
                selectionFrame.setVisibility(View.VISIBLE);
                mapFrame.setVisibility(View.VISIBLE);
            }

            // restore current fragment in selection frame
            String currentFragment = savedInstanceState.getString(CURRENT_FRAGMENT);

            if(roadFragment == null){
                roadFragment = new RoadFragment();
            }

            if(residentialFragment == null){
                residentialFragment = new ResidentialFragment();
            }

            if(commercialFragment == null){
                commercialFragment = new CommercialFragment();
            }

            if(currentFragment.equals(ROAD_FRAGMENT)){
                fm.beginTransaction().replace(R.id.selectionFrame, roadFragment).commit();
            }

            if(currentFragment.equals(RESIDENTIAL_FRAGMENT)){
                fm.beginTransaction().replace(R.id.selectionFrame, residentialFragment).commit();
            }

            if(currentFragment.equals(COMMERCIAL_FRAGMENT)){
                fm.beginTransaction().replace(R.id.selectionFrame, commercialFragment).commit();
            }

        }


        // first time startup
        else {

            // get bundle from main activity
            Bundle b = getIntent().getExtras();
            if(b != null) {
                String newLoad = b.getString(MAIN_ACTIVITY_CHOICE);

                /************************************
                 * Case 2, new game selected,
                 * wipe old database and start a new
                 * game with a blank map.
                 *************************************/
                if(newLoad.equals(NEW_GAME)) {
                    settings = b.getParcelable(SETTINGS_PARCEL);

                    // get settings from main activity
                    assert settings != null;
                    hd = new HardDataCreator(settings);
                    gameData = new GameData(getApplicationContext(), settings);
                    createNewDatabaseAsync = new CreateNewDatabaseAsync();
                    createNewDatabaseAsync.execute();
                }

                /************************************
                 * Case 3, the resume game function
                 * has been selected from main activity
                 * load data from database and recreate
                 * game data object from this (Resume)
                 *************************************/
                if(newLoad.equals(RESUME_GAME)) {
                    gameData = new GameData(this.getApplicationContext());
                    hd = new HardDataCreator(gameData.getSettings());
                }


                // new fragment instances
                roadFragment = new RoadFragment();
                residentialFragment = new ResidentialFragment();
                commercialFragment = new CommercialFragment();
                optionsFragment = new OptionsFragment();
                mapFragment = new MapFragment();
                dataFragment = new DataFragment();
                detailsFragment = new DetailsFragment();

                fm.beginTransaction().replace(R.id.dataFrame, dataFragment).commit();
                fm.beginTransaction().replace(R.id.optionsFrame, optionsFragment).commit();
                fm.beginTransaction().replace(R.id.mapFrame, mapFragment).commit();
                fm.beginTransaction().replace(R.id.detailsFrame, detailsFragment).commit();

            }

        }
        getTemperature = new GetTemperature();
        getTemperature.execute();
        startTimer();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {

        // find instance of current frag in frame A
        outState.putParcelable(SETTINGS_PARCEL, gameData.getSettings());
        outState.putLong(CURRENT_TIME, timeRemainingMillis);


        Fragment selFragment = fm.findFragmentById(R.id.selectionFrame);
        if(detailsFrame.getVisibility() == View.VISIBLE){
            outState.putString(CURRENT_VISIBILITY, DETAILS_FRAGMENT);
        }
        else{
            outState.putString(CURRENT_VISIBILITY, MAP_FRAGMENT);
        }

        // save the current fragment state in FrameA
        if(selFragment instanceof RoadFragment){
            outState.putString(CURRENT_FRAGMENT, ROAD_FRAGMENT);
        }
        else if(selFragment instanceof ResidentialFragment){
            outState.putString(CURRENT_FRAGMENT, RESIDENTIAL_FRAGMENT);
        }
        else if(selFragment instanceof CommercialFragment){
            outState.putString(CURRENT_FRAGMENT, COMMERCIAL_FRAGMENT);
        }
        else{
            outState.putString(CURRENT_FRAGMENT, "NULL");
        }

        // store fragments in outstate
        fm.putFragment(outState, MAP_FRAGMENT, mapFragment);
        fm.putFragment(outState, OPTIONS_FRAGMENT, optionsFragment);
        fm.putFragment(outState, DATA_FRAGMENT, dataFragment);
        fm.putFragment(outState, DETAILS_FRAGMENT, detailsFragment);
        super.onSaveInstanceState(outState);
    }

    /***************************************
     * Setup count down timer for 1 second
     * per month, pause on year increment
     * and update all player money.
     * Note time interval for income calculation
     * is done yearly so algorithm is multiplied
     * by 12 in gameData.calcYear().
     * Done to reduce processing without
     * effecting game play.
     * Player has 12 seconds to build
     * structures per year.
     **************************************/

    private void startTimer(){

        if(gameData.getGameMonth() == 0){
            timeRemainingMillis = TIMER_COUNTDOWN;
        }
            timer = new CountDownTimer(timeRemainingMillis, TIMER_INTERVAL){

            @Override
            public void onTick(long millisUntilFinished) {
                timeRemainingMillis = millisUntilFinished;
                gameData.incrementGameMonth();
                dataFragment.updateMonth();
            }

            @Override
            public void onFinish() {
                resetTimerAndCalcIncome();
            }
        }.start();
    }

    private void pauseTimer(){
        timer.cancel();
        timeRemainingMillis = TIMER_COUNTDOWN;
    }

    private void resetTimerAndCalcIncome(){
        // every 5 game years update the temp.
        if(gameData.getGameYear() % 5 == 0 ) {
            getTemperature = new GetTemperature();
            getTemperature.execute();
        }
        pauseTimer();
        calcYear();
        gameData.resetGameMonth();
        gameData.incrementGameYear();
        gameData.setCurrentYearExpenditure(0);
        dataFragment.updateAll(gameData);
        startTimer();

    }

    public void calcYear(){
        gameData.calcYear();

        // if money is negative clear database and start new game
        if(gameData.getCurrentMoney() < 0){
            // clear the database
            gameData.removeGameData();
            gameData.deleteMap();
            gameData.closeDb();
            // restart mainActivity.
            new AlertDialog.Builder(this).setMessage("You have lost.")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener(){

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(MapActivity.this, MainActivity.class);
                            startActivity(intent);
                        }

                    }).create().show();
        }
    }


    /******************************
     * Override listener methods
     ******************************/

    @Override
    public int getOrientation() {
        return orientation;
    }

    @Override
    public int getSelectionFragHeight() {

        Fragment myFrag = fm.findFragmentById(R.id.selectionFrame);
        return myFrag.getView().getMeasuredHeight();
    }

    @Override
    public int getMapFragHeight() {
        return mapFragment.getView().getMeasuredHeight();
    }

    @Override
    public void updateMoneyText() {
        dataFragment.updateMoneyText();
    }

    @Override
    public void runDetailsFragment(MapElement me) {
        pauseTimer();
        selectionFrame.setVisibility(View.GONE);
        dataFrame.setVisibility(View.GONE);
        optionsFrame.setVisibility(View.GONE);
        mapFrame.setVisibility(View.GONE);
        detailsFragment.setDetailsFragment(me);
        detailsFrame.setVisibility(View.VISIBLE);
    }

    public void runMapFragment() {
        detailsFrame.setVisibility(View.GONE);
        selectionFrame.setVisibility(View.VISIBLE);
        dataFrame.setVisibility(View.VISIBLE);
        optionsFrame.setVisibility(View.VISIBLE);
        mapFrame.setVisibility(View.VISIBLE);
        startTimer();
    }

    @Override
    public void setSelectionFrag(int inFragId) {

        switch(inFragId){
            // case for bulldozer or info
            case -1:
                Fragment myFrag = fm.findFragmentById(R.id.selectionFrame);

                if(myFrag instanceof RoadFragment){
                    fm.beginTransaction().remove(roadFragment).commit();
                }
                if(myFrag instanceof CommercialFragment){
                    fm.beginTransaction().remove(commercialFragment).commit();
                }
                if(myFrag instanceof ResidentialFragment){
                    fm.beginTransaction().remove(residentialFragment).commit();
                }
                break;

            // cases for switching or setting fragment
            case R.id.roadFragment:
                fm.beginTransaction().replace(R.id.selectionFrame, roadFragment).commit();
                break;
            case R.id.residentialFragment:
                fm.beginTransaction().replace(R.id.selectionFrame, residentialFragment).commit();
                break;
            case R.id.commercialFragment:
                fm.beginTransaction().replace(R.id.selectionFrame, commercialFragment).commit();
                break;
        }
    }

    @Override
    public void setCurrentStructure(@Nullable Structure currentStructure) {

        if(currentStructure instanceof Road){
            int[][] mask = hd.getRoadMask(currentStructure.getImageId());
            Road myRoad =  new Road(currentStructure.getImageId(),currentStructure.getCost(), mask);
            this.currentStructure = myRoad;
        }
        else if(currentStructure instanceof Residential){
            Residential myResidential = new Residential(currentStructure.getImageId(),currentStructure.getCost());
            this.currentStructure = myResidential;
        }
        else if(currentStructure instanceof Commercial){
            Commercial myCommercial = new Commercial(currentStructure.getImageId(), currentStructure.getCost());
            this.currentStructure = myCommercial;
        }
        else if(currentStructure instanceof Bulldozer){
            this.currentStructure = getBulldozer();
        }
        else if(currentStructure instanceof Detail){
            this.currentStructure = getDetail();
        }
        else{
            this.currentStructure = null;
        }

    }

    @Override
    public Bulldozer getBulldozer() {
        return hd.getBulldozer();
    }

    @Override
    public void updateDatabase() {
        updateDatabaseAsync = new UpdateDatabaseAsync();
        updateDatabaseAsync.execute();
    }

    /******************************
     *   image capture methods
     ******************************/

    /* Take photo methods adapted from code from Android tutorial (2018) - 75 - Camera API - Take Photos
     *  by Prabeesh RK https://www.youtube.com/watch?v=u5PDdg1G4Q4
     * accessed 2/11/2020
     */

    @Override
    public void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_PHOTO_CAPTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_PHOTO_CAPTURE && resultCode == RESULT_OK) {
            Bundle b = data.getExtras();
            Bitmap image = (Bitmap) b.get("data");
            detailsFragment.setImage(image);
        }
    }

    /******************************
     * Standard getters and setters
     ******************************/

    public GameData getGameData() {
        return gameData;
    }

    public Structure getCurrentStructure() {
        return currentStructure;
    }

    public ArrayList<Structure> getResidentialList(){
        return hd.getResidentialList();
    }

    public ArrayList<Structure> getRoadList(){
        return hd.getRoadList();
    }

    public ArrayList<Structure> getCommercialList(){
        return hd.getCommercialList();
    }

    public Detail getDetail() {return hd.getDetail();}

    /******************************
     *   Async tasks
     ******************************/

    // adopted from code from lecture "Remote Data"
    // Mobile app development, Curtin University, 2020.
    private class GetTemperature extends AsyncTask<Void, Void, Double>{

        @Override
        protected Double doInBackground(Void... voids) {

            String data = "";
            String urlString =
                    Uri.parse("https://api.openweathermap.org/data/2.5/weather")
                            .buildUpon()
                            .appendQueryParameter("q", gameData.getCityName()+",au")
                            .appendQueryParameter("APPID", API_KEY)
                            .appendQueryParameter("format", "json")
                            .appendQueryParameter("nojsoncallback", "1")
                            .appendQueryParameter("extras", "url_s")
                            .build().toString();

            HttpURLConnection connection = null;
            try {
                URL url = new URL(urlString);
                connection = (HttpURLConnection) url.openConnection();
                if(connection.getResponseCode() != HttpURLConnection.HTTP_OK)
                {
                    throw new IOException("Could not connect to openWeatherMap.net");
                }

                /**********************************
                 * Adapted from code at https://stackoverflow.com/questions/
                 * 309424/how-do-i-read-convert-an-inputstream-into-a-string-in-java
                 * Accessed 4/11/2020
                 **********************************/
                else{
                    InputStream inputStream = connection.getInputStream();
                    ByteArrayOutputStream response = new ByteArrayOutputStream();
                    byte[] buffer = new byte[1024];
                    int length;

                    // read in response to output stream
                    while((length = inputStream.read(buffer)) != -1){
                        response.write(buffer, 0, length);
                    }
                    data = response.toString("UTF-8");

                    // this print left in for printing of data return from openWeatherMap
                    System.out.println(data);
                    }
            } catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                if(connection != null) {
                    connection.disconnect();
                }
            }

            /**********************
             * parse json data
             **********************/
            Double  tempCelcius = 25.0;
            try {
                JSONObject jBase = new JSONObject(data);
                JSONObject mainObj = jBase.getJSONObject("main");
                double temperatureKelvin = mainObj.getDouble("temp");
                tempCelcius = temperatureKelvin -273.0;

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return tempCelcius;
        }

        @Override
        protected void onPostExecute(Double inTemp) {
            super.onPostExecute(inTemp);
            gameData.setTemperature(inTemp);
            dataFragment.updateAll(gameData);
        }
    }

    private class CreateNewDatabaseAsync extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            gameData.removeGameData();
            gameData.deleteMap();
            gameData.addGameMap(gameData.getMap());
            gameData.addGameData(gameData);
            return null;
        }
    }

    private class UpdateDatabaseAsync extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            gameData.updateGameData(gameData);
            gameData.updateMap(gameData.getMap());
            return null;
        }
    }

    /******************************
     * on back pressed and on destroy handle all resource closing
     ******************************/
    @Override
    public void onDetailsSaveClicked() {
            runMapFragment();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (detailsFrame.getVisibility() == View.VISIBLE) {
            runMapFragment();
        } else {
            // cancel async tasks if running
            if (updateDatabaseAsync != null) {
                updateDatabaseAsync.cancel(true);
            }
            if (createNewDatabaseAsync != null) {
                createNewDatabaseAsync.cancel(true);
            }
            if (getTemperature != null) {
                getTemperature.cancel(true);
            }

            // update database
            gameData.updateGameData(gameData);
            gameData.updateMap(gameData.getMap());

            // close db
            gameData.closeDb();

            // cancel timer
            timer.cancel();

            // return to main activity.
            Intent intent = new Intent(MapActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // cancel async tasks if running
        if(updateDatabaseAsync != null){
            updateDatabaseAsync.cancel(true);
        }
        if(createNewDatabaseAsync != null){
            createNewDatabaseAsync.cancel(true);
        }
        if(getTemperature != null){
            getTemperature.cancel(true);
        }

        // update database
        gameData.updateGameData(gameData);
        gameData.updateMap(gameData.getMap());

        // cancel timer
        timer.cancel();
    }


}