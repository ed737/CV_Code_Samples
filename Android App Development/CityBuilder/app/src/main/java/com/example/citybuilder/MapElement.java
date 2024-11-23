package com.example.citybuilder;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.Nullable;

public class MapElement implements Parcelable {

    private Structure structure;
    private Bitmap image;
    private String ownerName;
    private String playerEnteredName;
    private int buildable;
    private int row;
    private int col;
    private int arrayIndex;

    // TODO implement these in terrain editor
    /*
    private String terrainType;
    private int northWest;
    private int northEast;
    private int southWest;
    private int southEast;
*/

    // constructor
    public MapElement(@Nullable Structure structure, @Nullable Bitmap image, String ownerName, String inPlayerEnteredName, int inBuildable, int inRow, int inCol, int inArrayIndex) {
        this.structure = structure;
        this.image = image;
        this.ownerName = ownerName;
        this.buildable = inBuildable;
        this.row = inRow;
        this.col = inCol;
        this.arrayIndex = inArrayIndex;
        this.playerEnteredName = inPlayerEnteredName;
    }

    public MapElement() {
        this.structure = null;
        this.image = null;
        this.ownerName = "";
        this.buildable = 0;
        this.row = 0;
        this.col = 0;
        this.arrayIndex = 0;
        this.playerEnteredName = "";
    }

    protected MapElement(Parcel in) {
        structure = in.readParcelable(Structure.class.getClassLoader());
        image = in.readParcelable(Bitmap.class.getClassLoader());
        ownerName = in.readString();
        buildable = in.readInt();
        row = in.readInt();
        col = in.readInt();
    }

    public static final Creator<MapElement> CREATOR = new Creator<MapElement>() {
        @Override
        public MapElement createFromParcel(Parcel in) {
            return new MapElement(in);
        }

        @Override
        public MapElement[] newArray(int size) {
            return new MapElement[size];
        }
    };

    /***************************
     * Getters and Setters
     ***************************/

    public Structure getStructure() {
        return structure;
    }

    public void setStructure(Structure structure) {
        this.structure = structure;
    }

    public String getPlayerEnteredName() {
        return playerEnteredName;
    }

    public void setPlayerEnteredName(String playerEnteredName) {
        this.playerEnteredName = playerEnteredName;
    }

    public int getArrayIndex() {
        return arrayIndex;
    }

    public void setArrayIndex(int arrayIndex) {
        this.arrayIndex = arrayIndex;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getBuildable() {
        return buildable;
    }

    public void setBuildable(int buildable) {
        this.buildable = buildable;
    }

    public void updateBuildable(int inPosNeg){ this.buildable += inPosNeg;}

    public Drawable getImageDrawable() {

        return new BitmapDrawable(image);
    }

    public Bitmap getImageBitmap() {

        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(structure, flags);
        dest.writeParcelable(image, flags);
        dest.writeString(ownerName);
        dest.writeInt(buildable);
        dest.writeInt(row);
        dest.writeInt(col);
    }
}
