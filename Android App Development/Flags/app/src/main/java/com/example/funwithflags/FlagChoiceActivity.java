/******************************************************************
 * Flag choice activity, this activity controls all the game question
 * answer and country fragments
 *****************************************************************/

package com.example.funwithflags;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.GridLayout;

import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;

public class FlagChoiceActivity extends AppCompatActivity implements
        CountryAdapter.CountryAdapterListener, FragmentA.FragAListener,
        FragmentB.FragBListener, FragmentC.FragCListener, FragmentD.FragmentDListener,
        QuestionAdapter.QuestionAdapterListener, FragmentE.FragmentEListener {

    private int rowCol = 2; // int for switching row/col count
    private boolean horizontal = true; // flag for toggle horizontal/vertical button
    private FragmentA fragA;
    private FragmentB fragB;
    private FragmentC fragC;
    private FragmentD fragD;
    private FragmentE fragE;
    private FrameLayout frameA;
    private GridLayout frameB;
    private FrameLayout frameC;
    private FragmentManager fm;
    private FrameLayout answer_frame;
    int orientation;
    private int outcome;
    private GameInstance gameInstance;
    private String deviceModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // restore data
        Bundle data = getIntent().getExtras();
        // restore data from mainActivity
        if(data != null) {
            gameInstance = data.getParcelable("GameInstance");
        }

        // get the device type
        //sdk_gphone_x86 = pixel2
        //sdk_gphone_x86_arm = Nexus 10
        deviceModel = android.os.Build.MODEL;

        // get the orientation of device display
        orientation = this.getResources().getConfiguration().orientation;

        // set content view
        setContentView(R.layout.activity_flag_choice);

        // setup fragment manager
        fm = getSupportFragmentManager();

        // if tablet setup answer frame
        if(deviceModel.equals("sdk_gphone_x86_arm")) {
            answer_frame = (FrameLayout) findViewById(R.id.answer_frame);
            answer_frame.setVisibility(View.GONE);
        }

        if(fragA == null) {
            // setup frameA fragA
            frameA = (FrameLayout) findViewById(R.id.FrameA);
            fragA = (FragmentA) fm.findFragmentById(R.id.fragmentA);
            fragA = new FragmentA();
            fm.beginTransaction().add(R.id.FrameA, fragA).commit();
            frameA.setVisibility(View.VISIBLE);
        }

        if(fragC == null) {
            // setup frameC fragC
            frameC = (FrameLayout) findViewById(R.id.FrameC);
            fragC = (FragmentC) fm.findFragmentById(R.id.fragmentC);
            fragC = new FragmentC();
            fm.beginTransaction().add(R.id.FrameC, fragC).commit();
            frameC.setVisibility(View.VISIBLE);
        }

        if(fragB == null) {
            // setup frameB fragB
            frameB = (GridLayout) findViewById(R.id.FrameB);
            fragB = (FragmentB) fm.findFragmentById(R.id.fragmentB);
            fragB = new FragmentB();
            fm.beginTransaction().add(R.id.FrameB, fragB).commit();
            frameB.setVisibility(View.VISIBLE);
        }

        if(fragD == null){
            fragD = (FragmentD) fm.findFragmentById(R.id.fragmentD);
            fragD = new FragmentD();
        }

        if(fragE == null) {
            fragE = (FragmentE) fm.findFragmentById(R.id.fragmentE);
            fragE = new FragmentE();
        }

        // if this is not the first time the activity has been created
        if(savedInstanceState != null) {

            // restore data
            horizontal = savedInstanceState.getBoolean("Horizontal");
            rowCol = savedInstanceState.getInt("RowCol");
            gameInstance = savedInstanceState.getParcelable("GameInstance");
            String f = savedInstanceState.getString("currentFragment");

            // replace the fragment in FrameB
            try {
                switch (f) {
                    case "B":
                        fm.beginTransaction().replace(R.id.FrameB, fragB).commit();
                        break;
                    case "D":
                        fm.beginTransaction().replace(R.id.FrameB, fragD).commit();
                        break;
                    case "E":
                        fm.beginTransaction().replace(R.id.FrameB, fragE).commit();
                        break;
                }
            } catch (NullPointerException e) {
                System.out.println(e + " Error in data restoration of fragments in FlagChoiceActivity");
            }
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {

        // save data
        outState.putBoolean("Horizontal", horizontal);
        outState.putInt("RowCol", rowCol);
        outState.putParcelable("GameInstance", gameInstance);

        // this if statement to find what fragment currently in Frame B
        Fragment myFragment = fm.findFragmentById(R.id.FrameB);
        if(myFragment instanceof FragmentB){
            //fm.putFragment(outState, "fragB", fragB);
            outState.putString("currentFragment", "B");
        }
        else if(myFragment instanceof FragmentD){
            //fm.putFragment(outState, "fragD", fragD);
            outState.putString("currentFragment", "D");
        }
        else if(myFragment instanceof FragmentE ){
            //fm.putFragment(outState, "fragE", fragE);
            outState.putString("currentFragment", "E");
        }

        super.onSaveInstanceState(outState);

    }


    /******************************************************************
     * Event handlers
     *****************************************************************/

    @Override
    public void onCountryCardClicked(int index) {

            // start the question activity and pass data
            gameInstance.setCurrentCountryIndex(index);
            fragD = (FragmentD) fm.findFragmentById(R.id.fragmentD);
            fragD = new FragmentD();
            fm.beginTransaction().replace(R.id.FrameB, fragD).commit();

            // make frag C back button visible
            fragC.displayBackButton();



    }

    @Override
    public void onDirectionClicked(boolean horizontal, int inRowCol) {

        if (fm.findFragmentById(R.id.FrameB) instanceof FragmentB) {
            fragB.onDirectionClicked(horizontal, inRowCol);
        }

        if(fm.findFragmentById(R.id.FrameB) instanceof FragmentD){
            fragD.onDirectionClicked(horizontal, inRowCol);
        }
    }

    @Override
    public void onBackClicked() {


        // check if player has won or lost
        if(outcome == 3 || outcome == 4){
            winnerLoser();
        }

        // restore frameA
        frameA.setVisibility(View.VISIBLE);

        // case for tablet in landscape mode
        if (deviceModel.equals("sdk_gphone_x86_arm") && orientation == ORIENTATION_LANDSCAPE) {

            if (gameInstance.getCurrentQuestions().size() == 0) {
                fragC.hideBackButton();
                fm.beginTransaction().replace(R.id.FrameB, fragB).commit();
                answer_frame.setVisibility(View.GONE);
                fragC.hideBackButton();
                continueAfterAnswer();
                //outcomeClicked();
            }
            else {
                fm.beginTransaction().replace(R.id.FrameB, fragB).commit();
                answer_frame.setVisibility(View.GONE);
                fragC.hideBackButton();
                fragC.setWinLose("");
            }
        }

        else {
            Fragment myFrag = fm.findFragmentById(R.id.FrameB);

            // find instance
            if (myFrag instanceof FragmentD) {
                fragC.hideBackButton();
                fm.beginTransaction().replace(R.id.FrameB, fragB).commit();
            }
            if (myFrag instanceof FragmentE && gameInstance.getCurrentQuestions().size() == 0) {
                fm.beginTransaction().replace(R.id.FrameB, fragB).commit();
                outcomeClicked();
            }
            else if (myFrag instanceof FragmentE) {
                fm.beginTransaction().replace(R.id.FrameB, fragD).commit();
                fragC.setWinLose("");
            }
        }
    }

    @Override
    public void onQuestionCardClicked(int index) {

        // start the question activity and pass data
        gameInstance.setCurrentQuestionIndex(index);
        fragE = (FragmentE) fm.findFragmentById(R.id.fragmentE);
        fragE = new FragmentE();

        if(deviceModel.equals("sdk_gphone_x86_arm") && orientation == ORIENTATION_LANDSCAPE){
            fm.beginTransaction().replace(R.id.FrameB, fragD).commit();
            answer_frame.setVisibility(View.VISIBLE);
            fm.beginTransaction().replace(R.id.answer_frame, fragE).commit();
        }
        else{
            fm.beginTransaction().replace(R.id.FrameB, fragE).commit();
            frameA.setVisibility(View.GONE);
        }
    }


    /******************************************************************
     * Getters/setters
     *****************************************************************/

    public void setOutcome(int inOutcome){
        outcome = inOutcome;
    }

    @Override
    public void setRowCol(int inRowCol) {
        rowCol = inRowCol;
    }

    @Override
    public int getRowCol() {
        return rowCol;
    }


    @Override
    public GameInstance getGameInstance() {
        return gameInstance;
    }


    @Override
    public void setHorizontal(boolean inHorizontal) {
        horizontal = inHorizontal;
    }

    @Override
    public boolean getHorizontal() {
        return horizontal;
    }

    @Override
    public int getOrientation() {
        return orientation;
    }

    /******************************************************************
     * outcome methods
     *****************************************************************/

    @Override
    public void displayOutcome() {

        // update the current score
        fragC.updateScore(gameInstance.getCurrentScore());

        // nullify the current question
        gameInstance.nullifyQuestion();

        // outcome cases
        // 1 =  correct
        // 2 = incorrect
        // 3 = loser
        // 4 = winner
        fragE.hideButtons();
        switch(outcome){
            case 1:
                fragC.setWinLose("Correct");
                fragE.displayOutcome("Correct");
                break;
            case 2:
                fragC.setWinLose("Incorrect");
                fragE.displayOutcome("Incorrect");
                break;
            case 3:
                fragC.setWinLose("Loser");
                fragE.displayOutcome("You are a loser");
                break;
            case 4:
                fragC.setWinLose("Winner");
                fragE.displayOutcome("You have won");
                break;
        }


    }

    public void outcomeClicked() {

        switch(outcome) {
            case 1:
            case 2:
                continueAfterAnswer();
                break;
            case 3:
            case 4:
                winnerLoser();
                break;
            default:
                break;

        }
    }

    public void continueAfterAnswer() {

        // case for tablet
        if(deviceModel.equals("sdk_gphone_x86_arm") && orientation == ORIENTATION_LANDSCAPE) {

            // check if any questions left in this country
            if (gameInstance.getCurrentQuestions().size() == 0) {
                fm.beginTransaction().replace(R.id.FrameB, fragB).commit();
                answer_frame.setVisibility(View.GONE);
                fragC.hideBackButton();
                gameInstance.nullifyCountry();
            }
        }

        // case for phone
        else {
            // check if any questions left in this country
            if (gameInstance.getCurrentQuestions().size() == 0) {
                fm.beginTransaction().replace(R.id.FrameB, fragB).commit();
                fragC.hideBackButton();
                gameInstance.nullifyCountry();
            }

            // go back to the question list
            else {
                fm.beginTransaction().replace(R.id.FrameB, fragD).commit();
            }
        }
    }

    /******************************************************************
     * end of this activity, back to main activity, new instance.
     *****************************************************************/
    // return to main activity to start new game.
    public void winnerLoser() {
        Intent intent = new Intent(FlagChoiceActivity.this, MainActivity.class);
        startActivity(intent);
    }
}