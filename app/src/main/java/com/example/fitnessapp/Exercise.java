package com.example.fitnessapp;

// need to test this file

public class Exercise {
    // db constants
    public static final String EXERCISES_TABLE_NAME = "exercises";
    public static final String EXERCISES_COL_ID = "id";
    public static final String EXERCISES_COL_NAME = "name";
    public static final String EXERCISES_COL_WORKOUTFK = "workoutfk";
    public static final String EXERCISES_COL_DATA = "data";


    // SQL Query to create table
    public static final String CREATE_TABLE =
            "CREATE TABLE " + EXERCISES_TABLE_NAME + "("
                    + EXERCISES_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "  // may need to take out space after comma
                    + EXERCISES_COL_NAME + " TEXT, "
                    + EXERCISES_COL_WORKOUTFK + " INTEGER, "
                    + EXERCISES_COL_DATA + " TEXT, " + ")";

    // -- fields --
    private int id;
    private String name;
    private int workoutfk;
    private int num_reps;
    private int num_sets;
    private int[] reps; // holds number of reps per set
    private int[] weight; // holds the weight of each set
    private String units;

    // basic empty constructor
    public Exercise(){

    }

    // preferred constructor
    public Exercise(String name, int workoutfk, int num_reps, int num_sets, int[] reps , int[] weight, String units){
        this.name = name;
        this.workoutfk = workoutfk;
        this.num_reps = num_reps;
        this.num_sets = num_sets;
        this.reps = reps;
        this.weight = weight;
        this.units = units;

    }

    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return this.id;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }
    public void setWorkoutfk(int workoutfk){
        this.workoutfk = workoutfk;
    }
    public int getWorkoutfk(){
        return this.workoutfk;
    }
    public void setNum_sets(int num_sets){
        this.num_sets = num_sets;
    }
    public int getNum_sets(){
        return this.num_sets;
    }

    public void setReps(int[] reps){
        this.reps = reps;
    }
    public int[] getReps(){
        return this.reps;
    }
    public void setWeight(int[] weight){
        this.weight = weight;
    }
    public int[] getWeight(){
        return this.weight;
    }
    public void setUnits(String units){
        this.units = units;
    }
    public String getUnits(){
        return this.units;
    }

    public String toString(){
        String answer = new String();
        answer = num_sets + " ";
        for(int i = 0; i < num_sets; i++){
            answer = answer + reps[i] + " " + weight[i] + " ";
        }
        answer = answer + " " + units;
        return answer;
    }

    public void toObject(String data){

        String[] tokens = data.split(" ");
        this.num_sets = Integer.getInteger(tokens[0]);
        for(int i = 0; i < num_sets; i++){
            this.reps[i] = Integer.getInteger(tokens[(i*2) + 1]);
            this.weight[i] = Integer.getInteger(tokens[(i*2) + 2]);
        }
        this.units = tokens[tokens.length-1];
    }
}
