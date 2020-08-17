package com.example.fitnessapp;

public class Workout {
    // -- db constants --
    public static final String WORKOUT_TABLE_NAME = "workouts";
    public static final String COL_WORKOUT_PRIMARY_ID = "workout_primary_id";
    //public static final String COL_WORKOUT_DATE = "workout_date";
    public static final String COL_WORKOUT_DAY = "workout_day";
    public static  final String COL_WORKOUT_MONTH = "workout_month";
    public static final String COL_WORKOUT_YEAR = "workout_year";
    public static final String COL_ROUTINE_FOREIGN_ID = "routine_foreign_Id";
    /*public static final String COL_EX1_DATA = "ex1_data";
    public static final String COL_EX2_DATA = "ex2_data";
    public static final String COL_EX3_DATA = "ex3_data";
    public static final String COL_EX4_DATA = "ex4_data";
    public static final String COL_EX5_DATA = "ex5_data";
    public static final String COL_EX6_DATA = "ex6_data";
    public static final String COL_EX7_DATA = "ex7_data";
    public static final String COL_EX8_DATA = "ex8_data ";
    public static final String COL_EX9_DATA = "ex9_data";
    public static final String COL_EX10_DATA = "ex10_data";
    public static final String COL_EX11_DATA = "ex11_data";
    public static final String COL_EX12_DATA = "ex12_data";
    public static final String COL_EX13_DATA = "ex13_data";
    public static final String COL_EX14_DATA = "ex14_data";
    public static final String COL_EX15_DATA = "ex15_data";
    public static final String COL_EX16_DATA = "ex16_data";
    public static final String COL_EX17_DATA = "ex17_data";
    public static final String COL_EX18_DATA = "ex18_data";
    public static final String COL_EX19_DATA = "ex19_data";
    public static final String COL_EX20_DATA = "ex20_data";
    public static final String COL_EX21_DATA = "ex21_data";
    public static final String COL_EX22_DATA = "ex22_data";
    public static final String COL_EX23_DATA = "ex23_data";
    public static final String COL_EX24_DATA = "ex24_data";
    public static final String COL_EX25_DATA = "ex25_data";*/

    // create workout table sql query
    public static final String CREATE_WORKOUT_TABLE =
            "CREATE_TABLE " + WORKOUT_TABLE_NAME + "("
                    + COL_WORKOUT_PRIMARY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    //+ COL_WORKOUT_DATE + " TEXT,"
                    + COL_ROUTINE_FOREIGN_ID + " INTEGER,"
                    /*+ COL_DATA_UNITS + " TEXT,"
                    + COL_EX1_DATA + " INTEGER,"
                    + COL_EX2_DATA + " INTEGER,"
                    + COL_EX3_DATA + " INTEGER,"
                    + COL_EX4_DATA + " INTEGER,"
                    + COL_EX5_DATA + " INTEGER,"
                    + COL_EX6_DATA + " INTEGER,"
                    + COL_EX7_DATA + " INTEGER,"
                    + COL_EX8_DATA + " INTEGER,"
                    + COL_EX9_DATA + " INTEGER,"
                    + COL_EX10_DATA + " INTEGER,"
                    + COL_EX11_DATA + " INTEGER,"
                    + COL_EX12_DATA + " INTEGER,"
                    + COL_EX13_DATA + " INTEGER,"
                    + COL_EX14_DATA + " INTEGER,"
                    + COL_EX15_DATA + " INTEGER,"
                    + COL_EX16_DATA + " INTEGER,"
                    + COL_EX17_DATA + " INTEGER,"
                    + COL_EX18_DATA + " INTEGER,"
                    + COL_EX19_DATA + " INTEGER,"
                    + COL_EX20_DATA + " INTEGER,"
                    + COL_EX21_DATA + " INTEGER,"
                    + COL_EX22_DATA + " INTEGER,"
                    + COL_EX23_DATA + " INTEGER,"
                    + COL_EX24_DATA + " INTEGER,"
                    + COL_EX25_DATA + " INTEGER," + ")";*/
                    + COL_WORKOUT_DAY + " INTEGER,"
                    + COL_WORKOUT_MONTH + " INTEGER,"
                    + COL_WORKOUT_YEAR + " INTEGER," + ")";

    // -- fields --
    private int id;
    private int[] date;
    private int routine_foreign_id;

    // basic empty constructor
    public Workout(){

    }

    // ideal constructor
    public Workout(int id, int routine_foreign_id, int[] date){
        this.id = id;
        this.routine_foreign_id = routine_foreign_id;
        this.date = date;
    }

    // ---- methods ----
    public void setId(int id){
        this.id = id;
    }

    public int getId(){
        return this.id;
    }

    public int getDay(){
        return this.date[0];
    }

    public int getMonth(){
        return this.date[1];
    }

    public int getYear(){
        return this.date[2];
    }

    public void setDate(int[] date){
        this.date = date;
    }

    public int getRoutineForeignId(){
        return this.routine_foreign_id;
    }

    public void setRoutineForeignId(int routine_foreign_id){
        this.routine_foreign_id = routine_foreign_id;
    }

    /*public String getUnits(){
        return this.units;
    }

    public void setUnits(String units){
        this.units = units;
    }

    public int[] getData(){
        return this.data;
    }

    public void setData(int[] data){
        this.data = data;
    }*/
}
