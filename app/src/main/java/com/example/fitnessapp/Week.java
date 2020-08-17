package com.example.fitnessapp;

public class Week {
    //-- db constants --
    public static final String WEEK_TABLE_NAME = "weeks";
    public static final String WEEK_PRIMARY_ID = "week_primary_id";
    public static final String COL_PROGRAM_FOREIGN_ID = "program_foreign_id";
    public static final String COL_DAY1 = "day1";
    public static final String COL_DAY2 = "day2";
    public static final String COL_DAY3 = "day3";
    public static final String COL_DAY4 = "day4";
    public static final String COL_DAY5 = "day5";
    public static final String COL_DAY6 = "day6";
    public static final String COL_DAY7 = "day7";

    // create week table sql query
    public static final String CREATE_WEEK_TABLE =
            "CREATE TABLE " + WEEK_TABLE_NAME + "("
                    + COL_PROGRAM_FOREIGN_ID + " INTEGER,"
                    + WEEK_PRIMARY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COL_DAY1 + " TEXT,"
                    + COL_DAY2 + " TEXT,"
                    + COL_DAY3 + " TEXT,"
                    + COL_DAY4 + " TEXT,"
                    + COL_DAY5 + " TEXT,"
                    + COL_DAY6 + " TEXT,"
                    + COL_DAY7 + " TEXT,"
                    + ")";

    // -- fields --
    private int id;
    private int program_foreign_id;
    private String[] days;

    // basic empty constructor
    public Week(){

    }

    // ideal constructor
    public Week(int program_foreign_id, String[] days){
        this.program_foreign_id = program_foreign_id;
        this.days = days;
    }

    // ---- methods ----
    public int getId(){
        return this.id;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getProgramForeignId(){
        return this.program_foreign_id;
    }

    public void setProgram_foreign_id(int id){
        this.program_foreign_id = id;
    }

    public String[] getDays(){
        return this.days;
    }

    public void setDays(String[] days){
        this.days = days;
    }

}
