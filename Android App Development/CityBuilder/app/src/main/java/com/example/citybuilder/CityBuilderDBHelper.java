package com.example.citybuilder;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import static com.example.citybuilder.DBSchema.CityBuilderSchema.*;

public class CityBuilderDBHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "city_builder.db";

    public CityBuilderDBHelper(Context context){
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // game data and setting in one table
        db.execSQL("CREATE TABLE " + GameDataTable.NAME + "(" +
                        GameDataTable.COLS.PLAYER_NAME + " TEXT, " +
                        GameDataTable.COLS.MAP_WIDTH + " INTEGER, " +
                        GameDataTable.COLS.MAP_HEIGHT + " INTEGER, " +
                        GameDataTable.COLS.START_MONEY + " INTEGER, " +
                        GameDataTable.COLS.FAMILY_SIZE + " INTEGER, " +
                        GameDataTable.COLS.SHOP_SIZE + " INTEGER, " +
                        GameDataTable.COLS.SALARY + " INTEGER, " +
                        GameDataTable.COLS.TAX_RATE + " REAL, " +
                        GameDataTable.COLS.SERVICE_COST + " INTEGER, " +
                        GameDataTable.COLS.HOUSE_COST + " INTEGER, " +
                        GameDataTable.COLS.COMMERCIAL_COST + " INTEGER, " +
                        GameDataTable.COLS.ROAD_COST + " INTEGER, " +
                        GameDataTable.COLS.CITY_NAME + " TEXT, " +
                        GameDataTable.COLS.CURRENT_MONEY + " INTEGER, " +
                        GameDataTable.COLS.CURRENT_YEAR_EXPENDITURE + " INTEGER, " +
                        GameDataTable.COLS.GAME_MONTH  + " INTEGER, " +
                        GameDataTable.COLS.GAME_YEAR  + " INTEGER, " +
                        GameDataTable.COLS.POPULATION  + " INTEGER, " +
                        GameDataTable.COLS.INCOME  + " INTEGER, " +
                        GameDataTable.COLS.EMPLOYMENT  + " REAL, " +
                        GameDataTable.COLS.TEMPERATURE  + " REAL)");

        // large table for current saved map
        db.execSQL("CREATE TABLE " + MapTable.NAME + "(" +
                    MapTable.COLS.TYPE + " TEXT, " +
                    MapTable.COLS.ROW_NUMBER  + " INTEGER, " +
                    MapTable.COLS.COLUMN_NUMBER + " INTEGER, " +
                    MapTable.COLS.IMAGE  + " BLOB, " +
                    MapTable.COLS.BUILDABLE  + " INTEGER, " +
                    MapTable.COLS.OWNER_NAME  + " TEXT, " +
                    MapTable.COLS.IMAGE_ID  + " INTEGER, " +
                    MapTable.COLS.ARRAY_INDEX  + " INTEGER, " +
                    MapTable.COLS.PLAYER_ENTERED_NAME + " TEXT, " +
                    MapTable.COLS.COST +  " INTEGER)");

        // TODO table for saved terrain map names
        // TODO table for saved terrain maps

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
