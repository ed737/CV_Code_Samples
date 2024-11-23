package com.example.citybuilder;

public class DBSchema {

    public static class CityBuilderSchema {
        public static class GameDataTable {
            public static final String NAME = "gameData";
            public static final class COLS {
                public static final String PLAYER_NAME = "player_name";
                public static final String MAP_WIDTH = "map_width";
                public static final String MAP_HEIGHT = "map_height";
                public static final String START_MONEY = "start_money";
                public static final String FAMILY_SIZE = "family_size";
                public static final String SHOP_SIZE = "shop_size";
                public static final String SALARY = "salary";
                public static final String TAX_RATE = "tax_rate";
                public static final String SERVICE_COST = "service_cost";
                public static final String HOUSE_COST = "house_cost";
                public static final String COMMERCIAL_COST = "commercial_cost";
                public static final String ROAD_COST = "road_cost";
                public static final String CITY_NAME = "city_name";
                public static final String CURRENT_MONEY = "current_money";
                public static final String CURRENT_YEAR_EXPENDITURE = "current_year_expenditure";
                public static final String GAME_MONTH = "game_month";
                public static final String GAME_YEAR = "game_year";
                public static final String POPULATION = "population";
                public static final String INCOME = "income";
                public static final String EMPLOYMENT = "employment";
                public static final String TEMPERATURE = "temperature";
            }
        }

        public static class MapTable {
            public static final String NAME = "map";
            public static final class COLS {
                public static final String TYPE = "type";
                public static final String ROW_NUMBER = "row_number";
                public static final String COLUMN_NUMBER = "col_number";
                public static final String IMAGE = "image";
                public static final String BUILDABLE = "buildable";
                public static final String OWNER_NAME = "ownerName";
                public static final String IMAGE_ID = "image_id";
                public static final String ARRAY_INDEX = "array_index";
                public static final String PLAYER_ENTERED_NAME = "player_entered_name";
                public static final String COST = "cost";
            }
        }

    }
}

