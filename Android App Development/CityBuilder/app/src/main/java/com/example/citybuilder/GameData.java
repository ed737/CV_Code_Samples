package com.example.citybuilder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;
import androidx.annotation.Nullable;
import com.example.citybuilder.DBSchema.CityBuilderSchema.*;
import java.util.ArrayList;
import static com.example.citybuilder.Constants.PLAYER_NAME;

public class GameData{
    private Settings settings;
    private MapElement[][] map;
    private int currentMoney;
    private int currentYearExpenditure;
    private int gameMonth;
    private int gameYear;
    private int pop;
    private int income;
    private float employ;
    private Double temperature;
    private HardDataCreator hd;
    private SQLiteDatabase db;
    private GameDataCursor gameDataCursor;
    private MapCursor mapCursor;


    /******************************
     * Constructor for loading game
     ******************************/
    public GameData(Context context){
        GameData temp;
        db = new CityBuilderDBHelper(context.getApplicationContext()).getWritableDatabase();
        gameDataCursor = new GameDataCursor(db.query(
                GameDataTable.NAME, null, null,
                null, null, null, null));
        try{
            gameDataCursor.moveToFirst();
            temp = gameDataCursor.getGameData(db);
        }
        finally{
            gameDataCursor.close();
        }
        this.settings = temp.getSettings();
        this.map = temp.getMap();
        this.currentMoney = temp.getCurrentMoney();
        this.gameMonth = temp.getGameMonth();
        this.gameYear = temp.getGameYear();
        this.currentYearExpenditure = temp.getCurrentYearExpenditure();
        this.pop = temp.getPop();
        this.income = temp.getIncome();
        this.employ = temp.getEmploy();
        this.temperature = temp.getTemperature();
    }


    /***************************
     * Constructor for new game
     ***************************/
    public GameData(Context context, Settings settings) {
        db = new CityBuilderDBHelper(context.getApplicationContext()).getWritableDatabase();
        this.settings = settings;
        this.map = createNewMap();
        this.currentMoney = settings.getStartMoney();
        this.gameMonth = 0;
        this.gameYear = 0;
        this.currentMoney = settings.getStartMoney();
        this.pop = 0;
        this.income = 0;
        this.employ = 0.0f;
        this.temperature = 25.0;

    }

    /**********************************
     * Constructor for temp db loading
     **********************************/
    public GameData(@Nullable Settings settings, @Nullable MapElement[][] map, int currentMoney, int inCurrentYearExpenditure, int inPop, int inIncome, int gameMonth, int gameYear, float inEmploy, Double inTemp) {
        this.settings = settings;
        this.map = map;
        this.currentMoney = currentMoney;
        this.gameMonth = gameMonth;
        this.gameYear = gameYear;
        this.currentYearExpenditure = inCurrentYearExpenditure;
        this.pop = inPop;
        this.income = inIncome;
        this.employ = inEmploy;
        this.temperature = inTemp;

    }

    public void updateCosts(int inPrice){
        currentMoney -= inPrice;
        currentYearExpenditure +=  inPrice;
    }

    public String getCityName(){
        return settings.getCityName();
    }

    public int getPop() {
        return pop;
    }

    public void setPop(int pop) {
        this.pop = pop;
    }

    public int getIncome() {
        return income;
    }

    public void setIncome(int income) {
        this.income = income;
    }

    public float getEmploy() {
        return employ;
    }

    public void setMapElement(MapElement inMapElement, int inRow, int inCol){
        map[inRow][inCol] = inMapElement;
    }
    public int getCurrentYearExpenditure() {
        return currentYearExpenditure;
    }

    public void setCurrentYearExpenditure(int currentYearExpenditure) {
        this.currentYearExpenditure = currentYearExpenditure;
    }

    public void setEmploy(float employ) {
        this.employ = employ;
    }

    public void closeDb() {
        db.close();
    }

    public Double getTemperature() {
        return temperature;
    }

    public HardDataCreator getHd() {
        return hd;
    }

    public void setHd(HardDataCreator hd) {
        this.hd = hd;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public int getGameMonth() {
        return gameMonth;
    }

    public void setGameMonth(int gameMonth) {
        this.gameMonth = gameMonth;
    }

    public void incrementGameMonth(){
        if(gameMonth != 12) {
            gameMonth++;
        }
    }

    public void resetGameMonth(){
        gameMonth = 0;
    }

    public void incrementGameYear(){
        gameYear++;
    }

    public int getGameYear() {
        return gameYear;
    }

    public void setGameYear(int gameYear) {
        this.gameYear = gameYear;
    }

    public Settings getSettings() {
        return settings;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    public MapElement[][] getMap() {
        return map;
    }

    public void setMap(MapElement[][] map) {
        this.map = map;
    }

    public int getCurrentMoney() {
        return currentMoney;
    }

    public void setCurrentMoney(int currentMoney) {
        this.currentMoney = currentMoney;
    }


    // note a buffer border of 1 cell added to map to allow usage of masks for set buildable
    public MapElement[][] createNewMap() {
        map = new MapElement[settings.getMapHeight()+2][settings.getMapWidth()+2];

        int index = 0;
        for(int rr = 0; rr < settings.getMapHeight() + 2; rr++){
            for (int cc = 0; cc < settings.getMapWidth() + 2; cc++) {
                map[rr][cc] = new MapElement(null, null, PLAYER_NAME,"",0 , rr, cc, 0);
                index++;
            }
        }
        return map;
    }

    // note a buffer border of 1 cell added to map to allow usage of masks for set buildable
    public MapElement[][] recreateMapFromDataBase(ArrayList<MapElement> inList, Settings inSettings) {

        this.settings = inSettings;
        map = createNewMap();
        int arrayIndex = 0;
        for(int rr = 1; rr < inSettings.getMapHeight() + 1; rr++){
            for (int cc = 1; cc < inSettings.getMapWidth() + 1; cc++) {
                map[rr][cc] = new MapElement(inList.get(arrayIndex).getStructure(),
                        inList.get(arrayIndex).getImageBitmap(), inList.get(arrayIndex).getOwnerName(),
                        inList.get(arrayIndex).getPlayerEnteredName(),
                        inList.get(arrayIndex).getBuildable(), rr, cc, arrayIndex);
                arrayIndex++;
            }
        }
        return map;
    }

    public void calcYear(){
        int nResidential = 0;
        int nCommercial = 0;

        // go through map and calc number of structures
        // note matrix is padded one cell on all sides
        for(int rr = 1; rr < settings.getMapHeight() + 1; rr++){
            for(int cc = 1; cc <settings.getMapWidth() + 1; cc++){
                MapElement me = map[rr][cc];
                if(me.getStructure() instanceof Residential){
                    nResidential++;
                }
                if(me.getStructure() instanceof Commercial){
                    nCommercial++;
                }
            }
        }

        pop = settings.getFamilySize() * nResidential;

        if(pop != 0) {
            employ = Math.min(1.0f, (float)nCommercial * settings.getShopSize() / (float)pop);
        }
        else{
            employ = 0.0f;
        }

        // this algorithm multiplied by 12 to give player time to place structures.
        income = 12*(int)(pop * (employ*settings.getSalary()*settings.getTaxRate()-settings.getServiceCost()));
        currentMoney += income;

    }


    /**************************
     *  GameData Database methods
     **************************/
    public void addGameData(GameData inGameData) {
        ContentValues cv = new ContentValues();
        cv.put(GameDataTable.COLS.PLAYER_NAME, inGameData.getSettings().getPlayerName());
        cv.put(GameDataTable.COLS.MAP_WIDTH, inGameData.getSettings().getMapWidth());
        cv.put(GameDataTable.COLS.MAP_HEIGHT, inGameData.getSettings().getMapHeight());
        cv.put(GameDataTable.COLS.START_MONEY, inGameData.getSettings().getStartMoney());
        cv.put(GameDataTable.COLS.FAMILY_SIZE, inGameData.getSettings().getFamilySize());
        cv.put(GameDataTable.COLS.SHOP_SIZE, inGameData.getSettings().getShopSize());
        cv.put(GameDataTable.COLS.SALARY, inGameData.getSettings().getSalary());
        cv.put(GameDataTable.COLS.TAX_RATE, inGameData.getSettings().getTaxRate());
        cv.put(GameDataTable.COLS.SERVICE_COST, inGameData.getSettings().getServiceCost());
        cv.put(GameDataTable.COLS.HOUSE_COST, inGameData.getSettings().getHouseCost());
        cv.put(GameDataTable.COLS.COMMERCIAL_COST, inGameData.getSettings().getCommercialCost());
        cv.put(GameDataTable.COLS.ROAD_COST, inGameData.getSettings().getRoadCost());
        cv.put(GameDataTable.COLS.CITY_NAME, inGameData.getSettings().getCityName());
        cv.put(GameDataTable.COLS.CURRENT_MONEY, inGameData.currentMoney);
        cv.put(GameDataTable.COLS.CURRENT_YEAR_EXPENDITURE, inGameData.currentYearExpenditure);
        cv.put(GameDataTable.COLS.GAME_MONTH, inGameData.gameMonth);
        cv.put(GameDataTable.COLS.GAME_YEAR, inGameData.gameYear);
        cv.put(GameDataTable.COLS.POPULATION, inGameData.pop);
        cv.put(GameDataTable.COLS.INCOME, inGameData.income);
        cv.put(GameDataTable.COLS.EMPLOYMENT, inGameData.employ);
        cv.put(GameDataTable.COLS.TEMPERATURE, inGameData.temperature);
        db.insert(GameDataTable.NAME, null, cv);
    }

    public void updateGameData(GameData inGameData) {
        ContentValues cv = new ContentValues();
        cv.put(GameDataTable.COLS.PLAYER_NAME, inGameData.getSettings().getPlayerName());
        cv.put(GameDataTable.COLS.MAP_WIDTH, inGameData.getSettings().getMapWidth());
        cv.put(GameDataTable.COLS.MAP_HEIGHT, inGameData.getSettings().getMapHeight());
        cv.put(GameDataTable.COLS.START_MONEY, inGameData.getSettings().getStartMoney());
        cv.put(GameDataTable.COLS.FAMILY_SIZE, inGameData.getSettings().getFamilySize());
        cv.put(GameDataTable.COLS.SHOP_SIZE, inGameData.getSettings().getShopSize());
        cv.put(GameDataTable.COLS.SALARY, inGameData.getSettings().getSalary());
        cv.put(GameDataTable.COLS.TAX_RATE, inGameData.getSettings().getTaxRate());
        cv.put(GameDataTable.COLS.SERVICE_COST, inGameData.getSettings().getServiceCost());
        cv.put(GameDataTable.COLS.HOUSE_COST, inGameData.getSettings().getHouseCost());
        cv.put(GameDataTable.COLS.COMMERCIAL_COST, inGameData.getSettings().getCommercialCost());
        cv.put(GameDataTable.COLS.ROAD_COST, inGameData.getSettings().getRoadCost());
        cv.put(GameDataTable.COLS.CITY_NAME, inGameData.getSettings().getCityName());
        cv.put(GameDataTable.COLS.CURRENT_MONEY, inGameData.currentMoney);
        cv.put(GameDataTable.COLS.CURRENT_YEAR_EXPENDITURE, inGameData.currentYearExpenditure);
        cv.put(GameDataTable.COLS.GAME_MONTH, inGameData.gameMonth);
        cv.put(GameDataTable.COLS.GAME_YEAR, inGameData.gameYear);
        cv.put(GameDataTable.COLS.POPULATION, inGameData.pop);
        cv.put(GameDataTable.COLS.INCOME, inGameData.income);
        cv.put(GameDataTable.COLS.EMPLOYMENT, inGameData.employ);
        cv.put(GameDataTable.COLS.TEMPERATURE, inGameData.temperature);
        String[] whereValue = {PLAYER_NAME};
        db.update(GameDataTable.NAME, cv, GameDataTable.COLS.PLAYER_NAME + " = ?", whereValue);
    }

    public void removeGameData() {
        String[] whereValue = {PLAYER_NAME};
        db.delete(GameDataTable.NAME,
                GameDataTable.COLS.PLAYER_NAME + " = ?", whereValue);
    }

    public class GameDataCursor extends CursorWrapper {

        public GameDataCursor(Cursor cursor) {
            super(cursor);
        }

        public GameData getGameData(SQLiteDatabase db) {
            int mapWidth, mapHeight, startMoney, familySize, shopSize, salary, serviceCost,
                    houseCost, roadCost, commercialCost, currentMoney, currentYearExpenditure,
                    gameMonth, gameYear, population, income;
            float taxRate, employment;
            Double temperature;
            String cityName, playerName;
            Settings mySettings;
            ArrayList<MapElement> mapList = new ArrayList<>();
            MapElement[][] map;

            playerName = getString(getColumnIndex(GameDataTable.COLS.PLAYER_NAME));
            mapWidth = getInt(getColumnIndex(GameDataTable.COLS.MAP_WIDTH));
            mapHeight = getInt(getColumnIndex(GameDataTable.COLS.MAP_HEIGHT));
            startMoney = getInt(getColumnIndex(GameDataTable.COLS.START_MONEY));
            familySize = getInt(getColumnIndex(GameDataTable.COLS.FAMILY_SIZE));
            shopSize = getInt(getColumnIndex(GameDataTable.COLS.SHOP_SIZE));
            salary = getInt(getColumnIndex(GameDataTable.COLS.SALARY));
            serviceCost = getInt(getColumnIndex(GameDataTable.COLS.SERVICE_COST));
            houseCost = getInt(getColumnIndex(GameDataTable.COLS.HOUSE_COST));
            commercialCost = getInt(getColumnIndex(GameDataTable.COLS.COMMERCIAL_COST));
            roadCost = getInt(getColumnIndex(GameDataTable.COLS.ROAD_COST));
            taxRate = getFloat(getColumnIndex(GameDataTable.COLS.TAX_RATE));
            cityName = getString(getColumnIndex(GameDataTable.COLS.CITY_NAME));
            currentMoney = getInt(getColumnIndex(GameDataTable.COLS.CURRENT_MONEY));
            currentYearExpenditure = getInt(getColumnIndex(GameDataTable.COLS.CURRENT_YEAR_EXPENDITURE));
            gameMonth = getInt(getColumnIndex(GameDataTable.COLS.GAME_MONTH));
            gameYear = getInt(getColumnIndex(GameDataTable.COLS.GAME_YEAR));
            population = getInt(getColumnIndex(GameDataTable.COLS.POPULATION));
            income = getInt(getColumnIndex(GameDataTable.COLS.INCOME));
            employment = getFloat(getColumnIndex(GameDataTable.COLS.EMPLOYMENT));
            temperature = getDouble(getColumnIndex(GameDataTable.COLS.TEMPERATURE));

            // recreate settings object
            mySettings =  new Settings(playerName, mapWidth, mapHeight, startMoney,
                    familySize, shopSize, salary,
                    serviceCost, houseCost, commercialCost, roadCost, taxRate, cityName);

            // recreate map
            mapCursor = new MapCursor(db.query(
                    MapTable.NAME, null, null,
                    null, null, null, null), mySettings);
            try{
                mapCursor.moveToFirst();
                while(!mapCursor.isAfterLast()){
                    mapList.add(mapCursor.getMapElement());
                    mapCursor.moveToNext();
                }
            }
            finally{
                mapCursor.close();
            }
            map = recreateMapFromDataBase(mapList, mySettings);
            // recreate game data
            return new GameData(mySettings, map, currentMoney, currentYearExpenditure, population, income, gameMonth, gameYear, employment, temperature);
        }
    }



    /**************************
     * Map Database methods
     **************************/
    public void addGameMap(MapElement[][] inMap) {
        ContentValues cv = new ContentValues();

        for (int rr = 1; rr < settings.getMapHeight() + 1; rr++) {
            for (int cc = 1; cc < settings.getMapWidth() + 1; cc++) {

                if(inMap[rr][cc].getStructure() != null) {
                    cv.put(MapTable.COLS.TYPE, inMap[rr][cc].getStructure().getType());
                    cv.put(MapTable.COLS.IMAGE_ID, inMap[rr][cc].getStructure().getImageId());
                    cv.put(MapTable.COLS.COST, inMap[rr][cc].getStructure().getCost());
                }
                else{
                    cv.put(MapTable.COLS.TYPE, "");
                    cv.put(MapTable.COLS.IMAGE_ID, "");
                    cv.put(MapTable.COLS.COST, "");
                }
                cv.put(MapTable.COLS.ROW_NUMBER, inMap[rr][cc].getRow());
                cv.put(MapTable.COLS.COLUMN_NUMBER, inMap[rr][cc].getCol());
                cv.put(MapTable.COLS.IMAGE, 0);
                cv.put(MapTable.COLS.BUILDABLE, inMap[rr][cc].getBuildable());
                cv.put(MapTable.COLS.OWNER_NAME, inMap[rr][cc].getOwnerName());
                cv.put(MapTable.COLS.PLAYER_ENTERED_NAME, inMap[rr][cc].getPlayerEnteredName());
                cv.put(MapTable.COLS.ARRAY_INDEX, inMap[rr][cc].getArrayIndex());
                db.insert(MapTable.NAME, null, cv);
                cv = new ContentValues();
            }
        }

    }

    public void updateMap(MapElement[][] inMap){
        ContentValues cv;
        for (int rr = 1; rr < settings.getMapHeight() + 1; rr++) {
            for (int cc = 1; cc < settings.getMapWidth() + 1; cc++) {
                cv = new ContentValues();

                String[] whereValue = {String.valueOf(rr), String.valueOf(cc)};

                if(inMap[rr][cc].getStructure() != null) {
                    cv.put(MapTable.COLS.TYPE, inMap[rr][cc].getStructure().getType());
                    cv.put(MapTable.COLS.IMAGE_ID, inMap[rr][cc].getStructure().getImageId());
                    cv.put(MapTable.COLS.COST, inMap[rr][cc].getStructure().getCost());
                }
                else{
                    cv.put(MapTable.COLS.TYPE, "");
                    cv.put(MapTable.COLS.IMAGE_ID, "");
                    cv.put(MapTable.COLS.COST, "");
                }
                cv.put(MapTable.COLS.ROW_NUMBER, inMap[rr][cc].getRow());
                cv.put(MapTable.COLS.COLUMN_NUMBER, inMap[rr][cc].getCol());
                cv.put(MapTable.COLS.IMAGE, 0);
                cv.put(MapTable.COLS.BUILDABLE, inMap[rr][cc].getBuildable());
                cv.put(MapTable.COLS.OWNER_NAME, inMap[rr][cc].getOwnerName());
                cv.put(MapTable.COLS.ARRAY_INDEX, inMap[rr][cc].getArrayIndex());
                cv.put(MapTable.COLS.PLAYER_ENTERED_NAME, inMap[rr][cc].getPlayerEnteredName());
                db.update(MapTable.NAME, cv, MapTable.COLS.ROW_NUMBER + " = ? AND " + MapTable.COLS.COLUMN_NUMBER + " = ? ", whereValue);
            }
        }

    }

    public void deleteMap() {
        String[] whereValue = {String.valueOf(PLAYER_NAME)};
        db.delete(MapTable.NAME,
                MapTable.COLS.OWNER_NAME + " = ?", whereValue);
    }

    public class MapCursor extends CursorWrapper {

        private Settings settings;

        public MapCursor(Cursor cursor, Settings inSettings) {
            super(cursor);
            settings = inSettings;
        }

        public MapElement getMapElement() {
            String type, ownerName, playerEnteredName;
            int row, col, image, buildable, imageId, arrayIndex, cost;
            MapElement me;

            type = getString(getColumnIndex(MapTable.COLS.TYPE));
            row = getInt(getColumnIndex(MapTable.COLS.ROW_NUMBER));
            col = getInt(getColumnIndex(MapTable.COLS.COLUMN_NUMBER));
            image = getInt(getColumnIndex(MapTable.COLS.IMAGE));
            buildable = getInt(getColumnIndex(MapTable.COLS.BUILDABLE));
            ownerName = getString(getColumnIndex(MapTable.COLS.OWNER_NAME));
            imageId = getInt(getColumnIndex(MapTable.COLS.IMAGE_ID));
            arrayIndex = getInt(getColumnIndex(MapTable.COLS.ARRAY_INDEX));
            playerEnteredName = getString(getColumnIndex(MapTable.COLS.PLAYER_ENTERED_NAME));
            cost = getInt(getColumnIndex(MapTable.COLS.COST));

            HardDataCreator hd = new HardDataCreator(settings);

            if(type != null) {
                switch (type) {
                    case "road":

                        // get the road mask from hard data
                        int[][] mask = hd.getRoadMask(imageId);
                        Road myRoad = new Road(imageId, cost, mask);
                        me = new MapElement(myRoad, null, ownerName,playerEnteredName, buildable, row, col, arrayIndex);
                        break;
                    case "residential":
                        Residential myResidential = new Residential(imageId, cost);
                        me = new MapElement(myResidential, null, ownerName,playerEnteredName, buildable, row, col, arrayIndex);
                        break;
                    case "commercial":
                        Commercial myCommercial = new Commercial(imageId, cost);
                        me = new MapElement(myCommercial, null, ownerName,playerEnteredName, buildable, row, col, arrayIndex);
                        break;
                    default:
                        me = new MapElement(null, null, ownerName,playerEnteredName, buildable, row, col, arrayIndex);
                        break;
                }
            }
            else{
                me = new MapElement(null, null, ownerName,playerEnteredName, buildable, row, col, arrayIndex);
            }

            return me;
        }
    }

}
