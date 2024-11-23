package com.example.funwithflags;

import android.os.Parcel;
import android.os.Parcelable;

public class Answer implements Parcelable {
    private String answer;
    private boolean correct;

    Answer(String inAnswer, boolean inCorrect){
        answer = inAnswer;
        correct =  inCorrect;
    }

    protected Answer(Parcel in) {
        answer = in.readString();
        correct = in.readByte() != 0;
    }

    public static final Creator<Answer> CREATOR = new Creator<Answer>() {
        @Override
        public Answer createFromParcel(Parcel in) {
            return new Answer(in);
        }

        @Override
        public Answer[] newArray(int size) {
            return new Answer[size];
        }
    };

    // getters and setters
    public boolean isCorrect(){
        return correct;
    }

    public String getAnswerText(){
        return answer;
    }

    public void print(){
        System.out.println("     " + answer + " " + correct);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(answer);
        dest.writeByte((byte) (correct ? 1 : 0));
    }
}
