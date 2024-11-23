/******************************************************************
 * Fun with flags,
 * Mobile Application Development
 * Edward Munns 18871773
 * Curtin University
 * Semester 2 2020
 * references are in readme.
 *****************************************************************/

package com.example.funwithflags;

import androidx.annotation.NonNull;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import java.util.Random;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    private int startScore;
    private int winScore;
    private GameInstance gameInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        // assign widgets
        Button start = (Button) findViewById(R.id.start_button);
        TextView title = (TextView) findViewById(R.id.title_text);
        TextView startScoreText = (TextView) findViewById(R.id.start_score_text);
        TextView winScoreText = (TextView) findViewById(R.id.win_score_text);


        // seed generation for first start up
        if(savedInstanceState == null) {
            Random rd = new Random();

            // choose win score between 50 and 100
            winScore = rd.nextInt(50) + 50;
            // choose start score lower than winscore - 20
            startScore = rd.nextInt(winScore - 20);
        }

        // if saved instance state exists, rebuild with saved data (orientation change)
        else{
            winScore = savedInstanceState.getInt("winScore");
            startScore = savedInstanceState.getInt("startScore");
        }

        // set text in text views
        String winText = "Win Score = " + winScore;
        String startText = "StartScore = " + startScore;
        winScoreText.setText(winText);
        startScoreText.setText(startText);

        // start button click listener setup
        start.setOnClickListener(new View.OnClickListener() {

            // save data and start Flag choice activity
            @Override
            public void onClick(View v) {
                // create gameInstance to hold all data
                gameInstance = new GameInstance(winScore, startScore);

                Intent intent = new Intent(MainActivity.this, FlagChoiceActivity.class)
                        .putExtra("GameInstance", gameInstance);
                startActivity(intent);
            }
        });

    }

    // save data to bundle on state change (screen rotation)
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putInt("winScore", winScore);
        outState.putInt("startScore", startScore);
        outState.putParcelable("GameInstance", gameInstance);
    }

}