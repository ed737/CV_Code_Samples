package com.example.citybuilder;

import android.os.Parcel;
import android.os.Parcelable;

public class Commercial extends Structure implements Parcelable {
    public Commercial(int imageId, int cost) {
        super(imageId, cost, "commercial");
    }

    protected Commercial(Parcel in) {
        super(in);
    }

    public static final Creator<Commercial> CREATOR = new Creator<Commercial>() {
        @Override
        public Commercial createFromParcel(Parcel in) {
            return new Commercial(in);
        }

        @Override
        public Commercial[] newArray(int size) {
            return new Commercial[size];
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
