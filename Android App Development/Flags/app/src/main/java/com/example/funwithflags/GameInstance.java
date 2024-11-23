
/******************************************************************
 * GameInstance class, manages all data throughout the app.
 * Root of the parcelable used between main activity and
 * flag choice activity and on config changes.
 *****************************************************************/

package com.example.funwithflags;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

public class GameInstance implements Parcelable{

    private int currentScore;
    private int winScore;
    private ArrayList<Country> countryList = new ArrayList<>();
    private int currentCountryIndex;
    private int currentQuestionIndex;

    GameInstance(int inWinScore, int inStartScore ){
        this.winScore = inWinScore;
        this.currentScore = inStartScore;
        this.currentCountryIndex = 0;
        this.currentQuestionIndex = 0;
        countryList = DataCreator.create();
    }


    protected GameInstance(Parcel in) {
        currentScore = in.readInt();
        winScore = in.readInt();
        countryList = in.createTypedArrayList(Country.CREATOR);
        currentCountryIndex = in.readInt();
        currentQuestionIndex = in.readInt();
    }


    /******************************************************************
     * Getters and setters
     *****************************************************************/

    public int getCurrentCountryIndex() {
        return currentCountryIndex;
    }

    public int getCurrentQuestionIndex() {
        return currentQuestionIndex;
    }

    public void setCurrentCountryIndex(int currentCountryIndex) {
        this.currentCountryIndex = currentCountryIndex;
    }

    public void setCurrentQuestionIndex(int currentQuestionIndex) {
        this.currentQuestionIndex = currentQuestionIndex;
    }

    public int getCurrentScore() {
        return currentScore;
    }

    public int getWinScore() {
        return winScore;
    }

    public ArrayList<Country> getCountryList() {
        return countryList;
    }

    public ArrayList<Question> getCurrentQuestions(){
        return countryList.get(currentCountryIndex).getQuestions();
    }

    public Question getCurrentQuestion(){
        return countryList.get(currentCountryIndex).getQuestions().get(currentQuestionIndex);
    }

    public ArrayList<Answer> getCurrentAnswers(){
        return getCurrentQuestions().get(currentQuestionIndex).getAnswers();
    }
    public String getQuestionString(){
        return countryList.get(currentCountryIndex).getQuestions().get(currentQuestionIndex).getQuestion();
    }

    public void print(){
        for(int ii = 0; ii < countryList.size(); ii++){
            countryList.get(ii).print();
        }
    }


    /******************************************************************
     * Mutators
     *****************************************************************/


    // mutator to update score
    public void updateScore(int inValue){
        currentScore += inValue;
    }

    // method to remove questions after answering
    public void nullifyQuestion(){
        countryList.get(currentCountryIndex).getQuestions().remove(currentQuestionIndex);
    }

    // method to nullify country after answering all questions
    public void nullifyCountry(){
        countryList.remove(currentCountryIndex);
    }

    public boolean checkLose(){
        return currentScore < 0;
    }

    /******************************************************************
     * Parcelable methods
     *****************************************************************/

    public static final Creator<GameInstance> CREATOR = new Creator<GameInstance>() {


    @Override
    public GameInstance createFromParcel(Parcel in) {
        return new GameInstance(in);
    }

    @Override
    public GameInstance[] newArray(int size) {
        return new GameInstance[size];
    }
    };
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(currentScore);
        dest.writeInt(winScore);
        dest.writeTypedList(countryList);
        dest.writeInt(currentCountryIndex);
        dest.writeInt(currentQuestionIndex);
    }

}
