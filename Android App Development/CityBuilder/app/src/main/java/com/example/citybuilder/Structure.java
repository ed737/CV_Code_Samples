/***************************
* Structure  object.
 *  parent class to
 *  road residential and commercial
 *  also bulldozer and details.
***************************/

package com.example.citybuilder;
import android.os.Parcel;
import android.os.Parcelable;

public class Structure implements Parcelable {

    private int imageId;
    private int cost;
    private String type;

    public Structure(int inImageId, int inCost, String inType) {
        this.imageId = inImageId;
        this.cost = inCost;
        this.type = inType;
    }

    protected Structure(Parcel in) {
        imageId = in.readInt();
        cost = in.readInt();
        type = in.readString();
    }

    public static final Creator<Structure> CREATOR = new Creator<Structure>() {
        @Override
        public Structure createFromParcel(Parcel in) {
            return new Structure(in);
        }

        @Override
        public Structure[] newArray(int size) {
            return new Structure[size];
        }
    };

    public String getType() {
        if(type != null) {
            return type;
        }
        else{
            return "null";
        }
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(imageId);
        dest.writeInt(cost);
        dest.writeString(type);
    }
}
