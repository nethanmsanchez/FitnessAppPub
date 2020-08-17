package com.example.fitnessapp;

import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class Routine {
    // -- db constants --
    public static final String ROUTINE_TABLE_NAME = "routines";
    public static final String COL_ROUTINE_NAME = "routine_name";
    public static final String COL_ROUTINE_ID = "routine_id";
    public static final String COL_ROUTINE_LENGTH = "routine_length";
    public static final String COL_ROUTINE_EXERCISE_FKS = "routine_exercise_fk";
    /*public static final String COL_EX1 = "ex1";
    public static final String COL_EX2 = "ex2";
    public static final String COL_EX3 = "ex3";
    public static final String COL_EX4 = "ex4";
    public static final String COL_EX5 = "ex5";
    public static final String COL_EX6 = "ex6";
    public static final String COL_EX7 = "ex7";
    public static final String COL_EX8 = "ex8";
    public static final String COL_EX9 = "ex9";
    public static final String COL_EX10 = "ex10";
    public static final String COL_EX11 = "ex11";
    public static final String COL_EX12 = "ex12";
    public static final String COL_EX13 = "ex13";
    public static final String COL_EX14 = "ex14";
    public static final String COL_EX15 = "ex15";
    public static final String COL_EX16 = "ex16";
    public static final String COL_EX17 = "ex17";
    public static final String COL_EX18 = "ex18";
    public static final String COL_EX19 = "ex19";
    public static final String COL_EX20 = "ex20";
    public static final String COL_EX21 = "ex21";
    public static final String COL_EX22 = "ex22";
    public static final String COL_EX23 = "ex23";
    public static final String COL_EX24 = "ex24";
    public static final String COL_EX25 = "ex25";*/

    // -- create routine table sql query --
    public static final String CREATE_ROUTINE_TABLE =
            "CREATE TABLE " + ROUTINE_TABLE_NAME + "("
                    + COL_ROUTINE_ID + " INTEGER PRIMARY KEY AUTOINCREMEMENT,"
                    + COL_ROUTINE_NAME + " TEXT,"
                    + COL_ROUTINE_LENGTH + " INTEGER,"
                    + COL_ROUTINE_EXERCISE_FKS + " TEXT,"
                    /*+ COL_EX1 + " TEXT,"
                    + COL_EX2 + " TEXT,"
                    + COL_EX3 + " TEXT,"
                    + COL_EX4 + " TEXT,"
                    + COL_EX5 + " TEXT,"
                    + COL_EX6 + " TEXT,"
                    + COL_EX7 + " TEXT,"
                    + COL_EX8 + " TEXT,"
                    + COL_EX9 + " TEXT,"
                    + COL_EX10 + " TEXT,"
                    + COL_EX11 + " TEXT,"
                    + COL_EX12 + " TEXT,"
                    + COL_EX13 + " TEXT,"
                    + COL_EX14 + " TEXT,"
                    + COL_EX15 + " TEXT,"
                    + COL_EX16 + " TEXT,"
                    + COL_EX17 + " TEXT,"
                    + COL_EX18 + " TEXT,"
                    + COL_EX19 + " TEXT,"
                    + COL_EX20 + " TEXT,"
                    + COL_EX21 + " TEXT,"
                    + COL_EX22 + " TEXT,"
                    + COL_EX23 + " TEXT,"
                    + COL_EX24 + " TEXT,"
                    + COL_EX25 + " TEXT,"*/
                    + ")";

    // -- fields --
    private int id;
    private String name;
    private int length;
    private int[] exercisefks;
    //private Object[] fkholder;

    // -- empty base constructor --
    public Routine(){
    }

    //-- ideal constructor --
    public Routine(int id, String name, int length, int[] exercisefks){
        this.id = id;
        this.name = name;
        this.length = length;
        this.exercisefks = exercisefks;
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

    public int getLength(){
        return this.length;
    }

    public void setLength(int length){
        this.length = length;
    }

    public int[] getExercises(){
        return this.exercisefks;
    }

    public void setExercises(int[] exercisefks){
        this.exercisefks = exercisefks;
    }

    public String toString(){
        String ans = "";
        for(int i = 0; i < exercisefks.length; i++){
            ans = ans + exercisefks[i] + " ";
        }
        return ans;
    }

    public void toObject(String exercise_names){
        String[] tokens = exercise_names.split(" ");
        int[] ans = new int[tokens.length];
        for(int i = 0; i < tokens.length; i++){
            ans[i] = Integer.parseInt(tokens[i]);
        }
        this.exercisefks = ans;
    }

    public void setIntArray(ArrayList<String> fks, int curr){
        int[] ans = new int[fks.size()];
        for(int i = 0; i < curr; i++){
            ans[i] = Integer.parseInt(fks.get(i));
        }
        this.exercisefks = ans;
    }
}
