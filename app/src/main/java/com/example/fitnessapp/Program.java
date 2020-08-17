package com.example.fitnessapp;

public class Program {

    //-- db constants --
    public static final String PROGRAM_TABLE_NAME = "programs";
    public static final String PROGRAM_PRIMARY_ID = "program_primary_id";
    public static final String COL_PROGRAM_DAY_IN_CYCLE = "program_day_in_cycle";
    public static final String COL_PROGRAM_NAME = "program_name";
    public static final String COL_USER_FOREIGN_KEY = "user_foreign_id";
    public static final String COL_CYCLE_LENGTH = "cycle_length";
    public static final String COL_PROGRAM_WEEKS = "program_weeks";

    //-- create program table sql query --
    public static final String CREATE_PROGRAM_TABLE =
            "CREATE TABLE " + PROGRAM_TABLE_NAME + "("
            + PROGRAM_PRIMARY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COL_PROGRAM_DAY_IN_CYCLE + " TEXT,"
            + COL_PROGRAM_NAME + " TEXT,"
            + COL_USER_FOREIGN_KEY + " INTEGER,"
            + COL_CYCLE_LENGTH + " INTEGER,"
            + COL_PROGRAM_WEEKS + "TEXT,"+ ")";

    //-- fields --
    private int id;
    private String name;
    private int day_in_cycle;
    private int user_foreign_key;
    private int cycle_length;
    private Week[] weeks;
    private String weekfks;

    // empty basic constructor
    public Program(){
    }

    // ideal constructor
    public Program(int id, String name, int day_in_cycle, int user_foreign_key, int cycle_length, String weekfks){
        this.name = name;
        this.day_in_cycle = day_in_cycle;
        this.user_foreign_key = user_foreign_key;
        this.cycle_length = cycle_length;
        this.weeks = null;
        this.weekfks = weekfks;
    }

    // ---- methods ----
    public int getId(){
        return this.id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public int getDay_in_cycle(){
        return this.day_in_cycle;
    }

    public void setDay_in_cycle(int day_in_cycle){
        this.day_in_cycle = day_in_cycle;
    }

    public int getUserForeignKey(){
        return this.user_foreign_key;
    }

    public void setUserForeignKey(int user_foreign_key){
        this.user_foreign_key = user_foreign_key;
    }

    public int getCycleLength(){
        return this.cycle_length;
    }

    public void setCycleLength(int cycle_length){
        this.cycle_length = cycle_length;
    }

    public Week[] getWeeks(){
        return this.weeks;
    }

    /*public void setWeeks(Week[] weeks){
        this.weeks = weeks;
    }*/

    public String getWeekfks(){
        return this.weekfks;
    }

    public void setWeekfks(String weekfks){
        this.weekfks = weekfks;
    }

    public String toString(){
        String ans = "";
        for(int i = 0; i < this.weeks.length; i++){
            ans = ans + " " + weeks[i].getId();
        }
        return ans;
    }

}
