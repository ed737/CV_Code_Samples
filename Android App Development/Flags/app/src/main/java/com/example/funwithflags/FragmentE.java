/******************************************************************
 * Frag E, Displays answers and waits for click.
 * Buttons set to visible depending upon quantity of
 * answers. This implemented using match constraint
 * in xml and setting visibility to GONE.
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
import java.util.ArrayList;

public class FragmentE extends Fragment implements View.OnClickListener {

    private Button answer1, answer2, answer3, answer4;
    private TextView questionText;
    private ArrayList<Answer> answers;
    private FragmentEListener parentActivity;

    public interface FragmentEListener{
        void setOutcome(int outcome);
        void displayOutcome();
        GameInstance getGameInstance();
        void outcomeClicked();
    }

    public FragmentE() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_e, container, false);

        parentActivity = (FragmentEListener) this.getActivity();

        // get list of current answers
        assert parentActivity != null;
        answers = parentActivity.getGameInstance().getCurrentAnswers();

        // assign ids
        questionText = (TextView) view.findViewById(R.id.question_text);
        questionText.setText(parentActivity.getGameInstance().getQuestionString());
        answer1 = (Button) view.findViewById(R.id.answer1);
        answer2 = (Button) view.findViewById(R.id.answer2);
        answer3 = (Button) view.findViewById(R.id.answer3);
        answer4 = (Button) view.findViewById(R.id.answer4);

        // setup click listeners
        answer1.setOnClickListener(this);
        answer2.setOnClickListener(this);
        answer3.setOnClickListener(this);
        answer4.setOnClickListener(this);


        // setup buttons depending on amount of answers
        String txt;

        switch (answers.size()) {
            case 2:
                txt = answers.get(0).getAnswerText();
                answer1.setText(txt);
                answer1.setTag((Answer) answers.get(0));
                txt = answers.get(1).getAnswerText();
                answer2.setText(txt);
                answer3.setVisibility(View.GONE);
                answer4.setVisibility(View.GONE);
                break;

            case 3:

                txt = answers.get(0).getAnswerText();
                answer1.setText(txt);
                txt = answers.get(1).getAnswerText();
                answer2.setText(txt);
                txt = answers.get(2).getAnswerText();
                answer3.setText(txt);
                answer4.setVisibility(View.GONE);
                break;

            case 4:

                txt = answers.get(0).getAnswerText();
                answer1.setText(txt);
                txt = answers.get(1).getAnswerText();
                answer2.setText(txt);
                txt = answers.get(2).getAnswerText();
                answer3.setText(txt);
                txt = answers.get(3).getAnswerText();
                answer4.setText(txt);
                break;
        }

        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        /*outState.putString("QTXT", questionText.getText().toString());
        outState.putInt("A1VIS", answer1.getVisibility());
        outState.putString("A1TXT", answer1.getText().toString());
        outState.putInt("A2VIS", answer2.getVisibility());
        outState.putString("A2TXT", answer2.getText().toString());
        outState.putInt("A3VIS", answer3.getVisibility());
        outState.putString("A3TXT", answer3.getText().toString());
        outState.putInt("A4VIS", answer4.getVisibility());
        outState.putString("A4TXT", answer4.getText().toString()); */
        super.onSaveInstanceState(outState);
    }
    public void displayOutcome(String inText){
        questionText.setText(inText);
    }

    public void hideButtons(){
        answer1.setVisibility(View.GONE);
        answer2.setVisibility(View.GONE);
        answer3.setVisibility(View.GONE);
        answer4.setVisibility(View.GONE);

    }


    /******************************************************************
     * Event handler
     *****************************************************************/

    @Override
    public void onClick(View v) {

        // logic for correct or incorrect answer and score updating

        Answer myAnswer;
        Question myQuestion = parentActivity.getGameInstance().getCurrentQuestion();

        switch (v.getId()) {
            case R.id.answer2:
                myAnswer = answers.get(1);
                break;
            case R.id.answer3:
                myAnswer = answers.get(2);
                break;
            case R.id.answer4:
                myAnswer = answers.get(3);
                break;
            default:
                myAnswer = answers.get(0);
                break;
        }

        // check if answer is correct and update score
        if(myAnswer.isCorrect()){
            parentActivity.getGameInstance().updateScore(myQuestion.getPoint());
        }
        else{
            parentActivity.getGameInstance().updateScore(-1 * myQuestion.getPenalty());
        }

        int outcome;
        // answer is correct but player hasn't won yet
        if (myAnswer.isCorrect() && parentActivity.getGameInstance().getCurrentScore() <
                parentActivity.getGameInstance().getWinScore()) {

            outcome = 1;
        }

        // case for incorrect but not a loser yet
        else if(!myAnswer.isCorrect() && parentActivity.getGameInstance().getCurrentScore() > -1){
           outcome = 2;
        }
        else if(parentActivity.getGameInstance().getCurrentScore() <  0){
            outcome = 3;
        }
        else{
            outcome = 4;
        }

        parentActivity.setOutcome(outcome);
        parentActivity.displayOutcome();
    }
}