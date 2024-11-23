/******************************************************************
 * Question class, son of country, father of answer.
 *****************************************************************/

package com.example.funwithflags;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

public class Question implements Parcelable {

    private String question;
    private int questionNum;
    private int point;
    private int penalty;
    private boolean special;
    private ArrayList<Answer> answers;


    // constructor
    Question(String inQuestion, ArrayList<Answer> inAnswers, int inQuestionNum, int inPoint, int inPenalty, boolean inSpecial){
        question = inQuestion;
        answers = inAnswers;
        questionNum = inQuestionNum;
        point = inPoint;
        penalty = inPenalty;
        special = inSpecial;
    }

    /******************************************************************
     * Getters/Setters
     *****************************************************************/
    public String getQuestion(){
        return question;
    }

    public ArrayList<Answer> getAnswers(){
        return answers;
    }

    public int getQuestionNum(){
        return questionNum;
    }
    public int getPoint(){
        return point;
    }

    public int getPenalty(){
        return penalty;
    }

    public boolean isSpecial(){
        return special;
    }

    public void print(){
        System.out.println(getQuestion());
        for(int ii = 0; ii < answers.size(); ii++){
            answers.get(ii).print();
        }
    }

    /******************************************************************
     * Parcelable methods
     *****************************************************************/

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(question);
        dest.writeInt(questionNum);
        dest.writeInt(point);
        dest.writeInt(penalty);
        dest.writeByte((byte) (special ? 1 : 0));
        dest.writeTypedList(answers);
    }

    protected Question(Parcel in) {
        question = in.readString();
        questionNum = in.readInt();
        point = in.readInt();
        penalty = in.readInt();
        special = in.readByte() != 0;
        answers = in.createTypedArrayList(Answer.CREATOR);
    }

    public static final Creator<Question> CREATOR = new Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };
}
