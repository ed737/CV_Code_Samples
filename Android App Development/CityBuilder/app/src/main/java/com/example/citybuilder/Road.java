package com.example.citybuilder;

import android.os.Parcel;
import android.os.Parcelable;

public class Road extends Structure implements Parcelable {

        private int[][] mask;

    public Road(int imageId, int cost, int[][] inMask) {
        super(imageId, cost, "road");
        mask = inMask;
    }

    protected Road(Parcel in) {
        super(in);
    }

    public static final Creator<Road> CREATOR = new Creator<Road>() {
        @Override
        public Road createFromParcel(Parcel in) {
            return new Road(in);
        }

        @Override
        public Road[] newArray(int size) {
            return new Road[size];
        }
    };

    public int[][] getMask() { return this.mask;}

    public void setMask(int[][] mask) {
        this.mask = mask;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
