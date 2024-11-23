package com.example.funwithflags;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

public class Country implements Parcelable {

    // class fields
    private int flagImg;
    String name;
    ArrayList<Question> questions;

    // constructor
    Country(int inFlagImg , ArrayList<Question> inQuestions, String inName){
        flagImg = inFlagImg;
        name = inName;
        questions = inQuestions;
    }


    protected Country(Parcel in) {
        flagImg = in.readInt();
        name = in.readString();
        questions = in.createTypedArrayList(Question.CREATOR);
    }

    public static final Creator<Country> CREATOR = new Creator<Country>() {
        @Override
        public Country createFromParcel(Parcel in) {
            return new Country(in);
        }

        @Override
        public Country[] newArray(int size) {
            return new Country[size];
        }
    };

    // getters and setters
    public int getFlagImage(){
        return flagImg;
    }

    public String getName(){
        return name;
    }

    public ArrayList<Question> getQuestions(){
        return questions;
    }

    public void print(){
        System.out.println(this.name);
        for(int ii = 0; ii < questions.size(); ii++){
            questions.get(ii).print();
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(flagImg);
        dest.writeString(name);
        dest.writeTypedList(questions);
    }
}
