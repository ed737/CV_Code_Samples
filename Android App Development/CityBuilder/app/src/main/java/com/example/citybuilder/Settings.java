package com.example.citybuilder;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.os.Parcel;
import android.os.Parcelable;

public class Settings implements Parcelable {

    private int mapWidth, mapHeight, startMoney, familySize, shopSize, salary, serviceCost,
                houseCost, commercialCost, roadCost;
    private float taxRate;
    private String cityName, playerName;

    // default constructor
    Settings(String inPlayerName){
        playerName = inPlayerName;
        mapWidth = 50;
        mapHeight = 10;
        startMoney = 1000;
        familySize = 4;
        shopSize = 6;
        salary = 10;
        serviceCost = 2;
        houseCost = 100;
        commercialCost = 500;
        roadCost = 20;
        taxRate = 0.3f;
        cityName = "Perth";
    }

    // db constructor
    Settings(String inPlayerName, int inMapWidth, int inMapHeight, int inStartMoney, int inFamilySize,
             int inShopSize, int inSalary, int inServiceCost, int inHouseCost, int inCommercialCost,
             int inRoadCost, float inTaxRate, String inCityName){
        playerName = inPlayerName;
        mapWidth = inMapWidth;
        mapHeight = inMapHeight;
        startMoney = inStartMoney;
        familySize = inFamilySize;
        shopSize = inShopSize;
        salary = inSalary;
        serviceCost = inServiceCost;
        houseCost = inHouseCost;
        commercialCost = inCommercialCost;
        roadCost = inRoadCost;
        taxRate = inTaxRate;
        cityName = inCityName;
    }




    /***************************
     * Parcelable methods
     ***************************/
    protected Settings(Parcel in) {
        playerName = in.readString();
        mapWidth = in.readInt();
        mapHeight = in.readInt();
        startMoney = in.readInt();
        familySize = in.readInt();
        shopSize = in.readInt();
        salary = in.readInt();
        serviceCost = in.readInt();
        houseCost = in.readInt();
        commercialCost = in.readInt();
        roadCost = in.readInt();
        taxRate = in.readFloat();
        cityName = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(playerName);
        dest.writeInt(mapWidth);
        dest.writeInt(mapHeight);
        dest.writeInt(startMoney);
        dest.writeInt(familySize);
        dest.writeInt(shopSize);
        dest.writeInt(salary);
        dest.writeInt(serviceCost);
        dest.writeInt(houseCost);
        dest.writeInt(commercialCost);
        dest.writeInt(roadCost);
        dest.writeFloat(taxRate);
        dest.writeString(cityName);
    }

    public static final Creator<Settings> CREATOR = new Creator<Settings>() {
        @Override
        public Settings createFromParcel(Parcel in) {
            return new Settings(in);
        }

        @Override
        public Settings[] newArray(int size) {
            return new Settings[size];
        }
    };

    /***************************
     * Getters and Setters
     ***************************/


    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String inPlayerName) {
        this.playerName = inPlayerName;
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public void setMapWidth(int mapWidth) {
        this.mapWidth = mapWidth;
    }

    public int getMapHeight() {
        return mapHeight;
    }

    public void setMapHeight(int mapHeight) {
        this.mapHeight = mapHeight;
    }

    public int getStartMoney() {
        return startMoney;
    }

    public void setStartMoney(int startMoney) {
        this.startMoney = startMoney;
    }

    public int getFamilySize() {
        return familySize;
    }

    public void setFamilySize(int familySize) {
        this.familySize = familySize;
    }

    public int getShopSize() {
        return shopSize;
    }

    public void setShopSize(int shopSize) {
        this.shopSize = shopSize;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public int getServiceCost() {
        return serviceCost;
    }

    public void setServiceCost(int serviceCost) {
        this.serviceCost = serviceCost;
    }

    public int getHouseCost() {
        return houseCost;
    }

    public void setHouseCost(int houseCost) {
        this.houseCost = houseCost;
    }

    public int getCommercialCost() {
        return commercialCost;
    }

    public void setCommercialCost(int commercialCost) {
        this.commercialCost = commercialCost;
    }

    public int getRoadCost() {
        return roadCost;
    }

    public void setRoadCost(int roadCost) {
        this.roadCost = roadCost;
    }

    public float getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(float taxRate) {
        this.taxRate = taxRate;
    }

    public String toString(){
        return "Player Name = " + playerName + "\n" +
                "Width = " + mapWidth + "\n" +
                "Height = " + mapHeight + "\n" +
                "Start money = " + startMoney + "\n" +
                "Family size = " + familySize +"\n" +
                "Shop size = " + shopSize + "\n" +
                "Salary = " + salary + "\n" +
                "Service cost = " + serviceCost + "\n" +
                "House cost = " + houseCost + "\n" +
                "Commercial cost = " + commercialCost + "\n" +
                "Road cost = " + roadCost + "\n" +
                "Tax rate = " + taxRate + "\n" +
                "City Name = " + cityName + "\n";
    }
}
