package com.example.citybuilder;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Nullable;

public final class Bulldozer extends Structure implements Parcelable{

    private int[][] mask;

    public Bulldozer(int inImageId, int inCost, @Nullable int[][] inMask) {
        super(inImageId, inCost, "bulldozer");

        mask = inMask;
    }


    protected Bulldozer(Parcel in) {
        super(in);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Bulldozer> CREATOR = new Creator<Bulldozer>() {
        @Override
        public Bulldozer createFromParcel(Parcel in) {
            return new Bulldozer(in);
        }

        @Override
        public Bulldozer[] newArray(int size) {
            return new Bulldozer[size];
        }
    };

    public int[][] getMask(){
        return this.mask;
    }


}
