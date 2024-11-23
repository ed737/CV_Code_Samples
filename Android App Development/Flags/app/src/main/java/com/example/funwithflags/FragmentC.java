/******************************************************************
 * FragmentC, contains the back button and score data
 *****************************************************************/

package com.example.funwithflags;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class FragmentC extends Fragment implements View.OnClickListener{

    TextView scoreDisplay;
    TextView winLose;
    Button backButton;
    FragCListener parentActivity;


    public interface FragCListener{
    GameInstance getGameInstance();
    void onBackClicked();
    }


    public FragmentC() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        parentActivity = (FragCListener) this.getActivity();

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_c, container, false);

        // setup widgets
        scoreDisplay = (TextView) view.findViewById(R.id.score);
        winLose = (TextView) view.findViewById(R.id.winLose);
        backButton = (Button) view.findViewById(R.id.back_button);

        // set text views in frag c
        String scrDis = "Score  = " + parentActivity.getGameInstance().getCurrentScore()
                + "/" + parentActivity.getGameInstance().getWinScore();
        scoreDisplay.setText(scrDis);
        winLose.setText("");
        System.out.println("on createView = " + backButton.getVisibility());

        // set click listener
        backButton.setOnClickListener(this);

        if(savedInstanceState != null){
            backButton.setVisibility(savedInstanceState.getInt("bbvis"));
        }


        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt("bbvis", backButton.getVisibility());
        super.onSaveInstanceState(outState);
    }

    public void displayBackButton(){ backButton.setVisibility(View.VISIBLE);
    }
    public void hideBackButton(){backButton.setVisibility(View.GONE);}

    // update score when answer clicked
    public void updateScore(int inValue){
        String txt = "Score = " + inValue
                + "/" + parentActivity.getGameInstance().getWinScore();
        scoreDisplay.setText(txt);
    }

    public void setWinLose(String inText){
        winLose.setText(inText);
    }

    // back button clicked
    @Override
    public void onClick(View v) {
        parentActivity.onBackClicked();
    }

}