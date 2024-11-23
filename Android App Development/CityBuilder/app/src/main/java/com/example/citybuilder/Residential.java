package com.example.citybuilder;

import android.os.Parcel;
import android.os.Parcelable;

public class Residential extends Structure implements Parcelable {

    public Residential(int imageId, int cost) {
        super(imageId, cost, "residential");
    }

    protected Residential(Parcel in) {
        super(in);
    }

    public static final Creator<Residential> CREATOR = new Creator<Residential>() {
        @Override
        public Residential createFromParcel(Parcel in) {
            return new Residential(in);
        }

        @Override
        public Residential[] newArray(int size) {
            return new Residential[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
