package com.example.fitnessapp;

public class User {
    // -- db constants --
    public static final String USER_TABLE_NAME = "user";
    public static final String USER_COL_ID = "id";
    public static final String USER_COL_NAME = "name";
    public static final String USER_COL_HEIGHT = "height";
    public static final String USER_COL_GOALWEIGHT = "goalweight";
    public static final String USER_COL_CURRENTWEIGHT = "currentweight";
    public static final String USER_COL_PROGRAMFKS = "programfks";

    // SQL Query to create table
    public static final String CREATE_TABLE =
            "CREATE TABLE " + USER_TABLE_NAME + "("
                    + USER_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "  // need to take out space after comma
                    + USER_COL_NAME + " TEXT, "
                    + USER_COL_HEIGHT + " INTEGER, "
                    + USER_COL_GOALWEIGHT + " INTEGER, "
                    + USER_COL_CURRENTWEIGHT + " INTEGER, "
                    + USER_COL_PROGRAMFKS + " TEXT," + ")";

    // -- fields --
    private int id;
    private String name;
    private int height;
    private int goalweight;
    private int currentweight;
    private int[] programfks;

    // basic empty constructor
    public User(){

    }

    // ideal constructor
    public User(int id, String name, int h, int gw, int cw){
        this.id = id;
        this.name = name;
        this.height = h;
        this.goalweight = gw;
        this.currentweight = cw;
        this.programfks = null;
    }

    // methods
    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public int getHeight(){
        return height;
    }

    public void setHeight(int height){
        this.height = height;
    }

    public int getGoalWeight(){
        return goalweight;
    }

    public void setGoalWeight(int weight){
        this.goalweight = weight;
    }

    public int getCurrentWeight(){
        return currentweight;
    }

    public void setCurrentWeight(int weight){
        this.currentweight = weight;
    }

    public void setProgramfks(String programfks){
        String[] strings = programfks.split(" ");
        int[] ans = new int[strings.length];
        for(int i = 0; i < strings.length; i++){
            ans[i] = Integer.parseInt(strings[i]);
            this.programfks = ans;
        }
    }

    public int[] getProgramfks(){
        return this.programfks;
    }

}// end of class

