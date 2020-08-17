package com.example.fitnessapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "app.db";
    private static final int DB_VERSION = 1;

    // constructor
    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // onCreate is called when the user is using the app for the first time
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(User.CREATE_TABLE);
        db.execSQL(Program.CREATE_PROGRAM_TABLE);
        db.execSQL(Routine.CREATE_ROUTINE_TABLE);
        db.execSQL(Week.CREATE_WEEK_TABLE);
        db.execSQL(Workout.CREATE_WORKOUT_TABLE);
    }

    // onUprgrade is called if the database version is changed, may be another
    // condition before it is called not sure.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // still don't have an active db so no need to worry about versions
        //

        // drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + User.USER_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Program.PROGRAM_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Routine.ROUTINE_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Week.WEEK_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Workout.WORKOUT_TABLE_NAME);

        // create tables again
        onCreate(db);
    }



    // ------------------------------------------------------------------------------

    //                    user table functions

    // ------------------------------------------------------------------------------

    // ----- int insertUser(String name, int height, int gWeight, int cWeight) ------
    //  Parameters: A String, name, which is the name of the user to be stored.
    //              An int, height, which is the user's height in inches.
    //              An int, gWeight, which is the user's goal weight in pounds
    //              An int, cWeight, which is the user's current weight in pounds.
    //  Output:     An int with the index of the user that was updated, should be 0.
    // ------------------------------------------------------------------------------
    public int insertUser(String name, int h, int gw, int cw, String programfks){
        // get writable db, since we obv want to write this data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` will be inserted automatically
        values.put(User.USER_COL_NAME, name);
        values.put(User.USER_COL_HEIGHT, h);
        values.put(User.USER_COL_GOALWEIGHT, gw);
        values.put(User.USER_COL_CURRENTWEIGHT, cw);
        values.put(User.USER_COL_PROGRAMFKS, programfks);

        // insert row
        long id = db.insert(User.USER_TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        int userid = (int) id;
        return userid;
    }

    // ----------------- int getUser(int id) ---------------------
    //  Parameters: An int, id, which is the id of the user who's data will be updated.
    //  Output:     A User object, which has all of the data about the user.
    // ------------------------------------------------------------------------------
    public User getUser(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(User.USER_TABLE_NAME, new String[]{User.USER_COL_ID,
                 User.USER_COL_NAME, User.USER_COL_HEIGHT, User.USER_COL_GOALWEIGHT,
                 User.USER_COL_CURRENTWEIGHT}, User.USER_COL_ID + "=?",
                 new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null && cursor.getCount()>0){
            cursor.moveToFirst();
        }else{              // may need to change eventually
            return null;
        }
        // prepare User object
        User user = new User(
                cursor.getInt(cursor.getColumnIndex(User.USER_COL_ID)),
                cursor.getString(cursor.getColumnIndex(User.USER_COL_NAME)),
                cursor.getInt(cursor.getColumnIndex(User.USER_COL_HEIGHT)),
                cursor.getInt(cursor.getColumnIndex(User.USER_COL_GOALWEIGHT)),
                cursor.getInt(cursor.getColumnIndex(User.USER_COL_CURRENTWEIGHT))
        );
        user.setProgramfks(cursor.getString(cursor.getColumnIndex(User.USER_COL_PROGRAMFKS)));

        // close the db connection
        cursor.close();

        return user;
    }

    // -------------- int updateUserName(int id, String name) -----------------------
    //  Parameters: An int, id, which is the id of the user who's data will be updated.
    //              A string, name, which is the new name we are saving in the db.
    //  Output:     An int with the index of the user that was updated, should be 0.
    // ------------------------------------------------------------------------------
    public int updateUserName(int id, String name){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(User.USER_COL_NAME, name);

        // updating row
        return db.update(User.USER_TABLE_NAME, values, User.USER_COL_ID + " = ?",
                new String[]{String.valueOf(id)});
    }

    // ---------------- int updateUserHeight(int id, int h) ---------------------
    //  Parameters: An int, id, which is the id of the user(row) who's data will be updated.
    //              An int, h, new height to save.
    //  Output:     An int with the index of the user that was updated, should be 0.
    // ----------------------------------------------------------------------
    public int updateUserHeight(int id, int h){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(User.USER_COL_HEIGHT, h);

        // updating row
        return db.update(User.USER_TABLE_NAME, values, User.USER_COL_ID + " = ?",
                new String[]{String.valueOf(id)});
    }

    // ----------------- int updateUserGoalWeight(int id, int w) ------------------------
    //  Parameters: An int, id, which is the id of the user(row) who's data will be updated.
    //              An int, w, which is the new goal weight to save.
    //  Output:     An int with the index of the user that was updated, should be 0.
    // ------------------------------------------------------------------------------
    public int updateUserGoalWeight(int id, int w){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(User.USER_COL_GOALWEIGHT, w);

        // updating row
        return db.update(User.USER_TABLE_NAME, values, User.USER_COL_ID + " = ?",
                new String[]{String.valueOf(id)});
    }

    // ----------------- int updateUserCurrentWeight(int id, int w) --------------------------
    //  Parameters: An int, id, which is the id of the user(row) who's data will be updated.
    //              An int, w, which is the new current weight to save.
    //  Output:     An int with the index of the user that was updated, should be 0.
    // ------------------------------------------------------------------------------
    public int updateUserCurrentWeight(int id, int w){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(User.USER_COL_CURRENTWEIGHT, w);

        // updating row
        return db.update(User.USER_TABLE_NAME, values, User.USER_COL_ID + " = ?",
                new String[]{String.valueOf(id)});
    }

    // -------------- int updateProgramfks(int id, String programfks) ---------------
    //  Parameters: An int, id, which is the id of the user(row) who's data will be updated.
    //              An int, w, which is the new current weight to save.
    //  Output:     An int with the index of the user that was updated, should be 0.
    // ------------------------------------------------------------------------------
    public int updateProgramfks(int id, String programfks){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(User.USER_COL_PROGRAMFKS, programfks);

        // updating row
        return db.update(User.USER_TABLE_NAME, values, User.USER_COL_ID + " = ?",
                new String[]{String.valueOf(id)});
    }

    // ------------------ void deleteUser(User user) --------------------------------
    // Parameters: a User object, user, that will be deleted from the db.
    // Output:     No output
    // ------------------------------------------------------------------------------
    public void deleteUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(User.USER_TABLE_NAME, User.USER_COL_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
        db.close();
    }
    // ------------------------------------------------------------------------------

    //                        End of User Table Methods

    // ------------------------------------------------------------------------------



    // ------------------------------------------------------------------------------

    //                         Program Table Functions

    // ------------------------------------------------------------------------------

    // ---------------------- int insertProgram(program) ----------------------------
    // Parameters: a Program object, program, that will be inserted into the db
    // Output:     an int, id, which is the programs id.
    // ------------------------------------------------------------------------------
    public int insertProgram(Program program){
        // get instance of db
        SQLiteDatabase db = this.getWritableDatabase();

        // put program data to save into ContentValues object
        ContentValues values = new ContentValues();
        values.put(Program.COL_PROGRAM_DAY_IN_CYCLE, program.getDay_in_cycle());
        values.put(Program.COL_USER_FOREIGN_KEY, program.getUserForeignKey());
        values.put(Program.COL_CYCLE_LENGTH, program.getCycleLength());
        values.put(Program.COL_PROGRAM_NAME, program.getName());
        values.put(Program.COL_PROGRAM_WEEKS, program.toString());

        // insert row into db
        long id = db.insert(program.PROGRAM_TABLE_NAME, null, values);

        db.close();

        return (int) id;
    }

    // ----------------------- int getProgram(int index) ----------------------------
    // Parameters: an int, index, which is the id of the user who owns the program
    //  (right now only supports 1, since i get the program based on id, not userforeign key)
    // Output:     an int, id, which is the programs id.
    // ------------------------------------------------------------------------------
    public Program getProgram(int index){
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + Program.PROGRAM_TABLE_NAME + " WHERE "
                + Program.PROGRAM_PRIMARY_ID + " = " + index;

        Cursor c = db.rawQuery(selectQuery, null);

        if( c != null){
            c.moveToFirst();
        }else{
            return null;
        }

        Program program = new Program();
        program.setId(index);
        program.setUserForeignKey(c.getInt(c.getColumnIndex(Program.COL_USER_FOREIGN_KEY)));
        program.setCycleLength(c.getInt(c.getColumnIndex(Program.COL_CYCLE_LENGTH)));
        program.setDay_in_cycle(c.getInt(c.getColumnIndex(Program.COL_PROGRAM_DAY_IN_CYCLE)));
        program.setName(c.getString(c.getColumnIndex(Program.COL_PROGRAM_NAME)));
        program.setWeekfks(c.getString(c.getColumnIndex(Program.COL_PROGRAM_WEEKS)));
        c.close();

        return program;
    }

    // ---------------- int updateProgramName(int id, String name) -------------------
    // Parameters: an int, id, which is the id of the program to update.
    //             a String, name, which is the new name for the program.
    // Output:     an int, which is the id of the program that was updated.
    // ------------------------------------------------------------------------------
    public int updateProgramName(int id, String name){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Program.COL_PROGRAM_NAME, name);

        return db.update(Program.PROGRAM_TABLE_NAME, values,
                Program.PROGRAM_PRIMARY_ID + " = ?",
                new String[] { String.valueOf(id) });
    }

    // ----------- int updateProgramStartDate(int id, String date) --------------
    // Parameters: an int, id, which is the id of the program to update.
    //             a String, date,  which is the new start date for the program.
    // Output:     an int, which is the id of the program that was updated.
    // ------------------------------------------------------------------------------
    public int updateDay_in_cycle(int id, int day_in_cycle){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Program.COL_PROGRAM_DAY_IN_CYCLE, day_in_cycle);

        return db.update(Program.PROGRAM_TABLE_NAME, values,
                Program.PROGRAM_PRIMARY_ID + " = ?",
                new String[] { String.valueOf(id) });
    }

    // ----------------- int updateProgramUserForeignKey(int id, int key) ---------------------
    // Parameters: an int, id, which is the id of the program to update.
    //             an int, key, which is the new foreign key for the program.
    // Output:     an int, which is the id of the program that was updated.
    // ------------------------------------------------------------------------------
    public int updateProgramUserForeignKey(int id, int key){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Program.COL_USER_FOREIGN_KEY, key);

        return db.update(Program.PROGRAM_TABLE_NAME, values,
                Program.PROGRAM_PRIMARY_ID + " = ?",
                new String[] { String.valueOf(id) });
    }

    // ----------------- int updateProgramCycleLength(int id, int cycle) ---------------------
    // Parameters: an int, id, which is the id of the program to update.
    //             an int, cycle, which is the new cycle length for the program.
    // Output:     an int, which is the id of the program that was updated.
    // ------------------------------------------------------------------------------
    public int updateProgramCycleLength(int id, int cycle){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Program.COL_CYCLE_LENGTH, cycle);

        return db.update(Program.PROGRAM_TABLE_NAME, values,
                Program.PROGRAM_PRIMARY_ID + " = ?",
                new String[] { String.valueOf(id) });
    }

    // ---------------- int updateWeekfks(int id, String weekfks) -------------------
    // Parameters: an int, id, which is the id of the program to update.
    //             an int, cycle, which is the new cycle length for the program.
    // Output:     an int, which is the id of the program that was updated.
    // ------------------------------------------------------------------------------
    public int updateWeekfks(int id, int fks){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Program.COL_PROGRAM_WEEKS, fks);

        return db.update(Program.PROGRAM_TABLE_NAME, values,
                Program.PROGRAM_PRIMARY_ID + " = ?",
                new String[] { String.valueOf(id) });
    }

    // -------------------- void deleteProgram(int id) ------------------------------
    // Parameters: an int, id, which is the id of the program to update.
    //             an int, key, which is the new foreign key for the program.
    // Output:     an int, which is the id of the program that was updated.
    // ------------------------------------------------------------------------------
    public void deleteProgram(int id){
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(Program.PROGRAM_TABLE_NAME, Program.COL_USER_FOREIGN_KEY + " = ?",
                new String[] { String.valueOf(id) });
    }

    // ------------------------------------------------------------------------------

    //                      End of Program Table Functions

    // ------------------------------------------------------------------------------


    // ------------------------------------------------------------------------------

    //                         Routine Table Functions

    // ------------------------------------------------------------------------------
    // ------------------ void insertRoutine(Routine routine) -----------------------
    // Parameters: a Routine, routine, object that will be stored.
    // Output:     an int, which is the id of the program that was updated.
    // ------------------------------------------------------------------------------
    public int insertRoutine(Routine routine){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Routine.COL_ROUTINE_NAME, routine.getName());
        values.put(Routine.COL_ROUTINE_LENGTH, routine.getLength());
        values.put(Routine.COL_ROUTINE_EXERCISE_FKS, routine.toString());
        /*
        if(routine.getLength() > 0){
            values.put(Routine.COL_EX1, s[0]);
        }
        if(routine.getLength() > 1){
            values.put(Routine.COL_EX2, s[1]);
        }
        if(routine.getLength() > 2){
            values.put(Routine.COL_EX3, s[2]);
        }
        if(routine.getLength() > 3){
            values.put(Routine.COL_EX4, s[3]);
        }
        if(routine.getLength() > 4){
            values.put(Routine.COL_EX5, s[4]);
        }
        if(routine.getLength() > 5){
            values.put(Routine.COL_EX6, s[5]);
        }
        if(routine.getLength() > 6){
            values.put(Routine.COL_EX7, s[6]);
        }
        if(routine.getLength() > 7){
            values.put(Routine.COL_EX8, s[7]);
        }
        if(routine.getLength() > 8){
            values.put(Routine.COL_EX9, s[8]);
        }
        if(routine.getLength() > 9){
            values.put(Routine.COL_EX10, s[9]);
        }
        if(routine.getLength() > 10){
            values.put(Routine.COL_EX11, s[10]);
        }
        if(routine.getLength() > 11){
            values.put(Routine.COL_EX12, s[11]);
        }
        if(routine.getLength() > 12){
            values.put(Routine.COL_EX13, s[12]);
        }
        if(routine.getLength() > 13){
            values.put(Routine.COL_EX12, s[13]);
        }
        if(routine.getLength() > 14){
            values.put(Routine.COL_EX15, s[14]);
        }
        if(routine.getLength() > 15){
            values.put(Routine.COL_EX16, s[15]);
        }
        if(routine.getLength() > 16){
            values.put(Routine.COL_EX17, s[16]);
        }
        if(routine.getLength() > 17){
            values.put(Routine.COL_EX18, s[17]);
        }
        if(routine.getLength() > 18){
            values.put(Routine.COL_EX19, s[18]);
        }
        if(routine.getLength() > 19){
            values.put(Routine.COL_EX20, s[19]);
        }
        if(routine.getLength() > 20){
            values.put(Routine.COL_EX21, s[20]);
        }
        if(routine.getLength() > 21){
            values.put(Routine.COL_EX22, s[21]);
        }
        if(routine.getLength() > 22){
            values.put(Routine.COL_EX23, s[22]);
        }
        if(routine.getLength() > 23){
            values.put(Routine.COL_EX24, s[23]);
        }
        if(routine.getLength() > 24){
            values.put(Routine.COL_EX25, s[24]);
        }*/

        long ans = db.insert(Routine.ROUTINE_TABLE_NAME, null, values);

        return (int) ans;
    }

    // -------------------- Routine getRoutine(int id) ------------------------------
    // Parameters: an int, id, which is the id of the routine to get.
    // Output:     a Routine, object from the db
    // ------------------------------------------------------------------------------
    public Routine getRoutine(int id) {
        SQLiteDatabase db = getReadableDatabase();

        String selectQuery = "SELECT * FROM " + Routine.ROUTINE_TABLE_NAME + " WHERE "
                + Routine.COL_ROUTINE_ID + " = " + id;

        Cursor c = db.rawQuery(selectQuery, null);

        if(c!=null){
            c.moveToFirst();
        } else{
            return null;
        }

        String name = c.getString(c.getColumnIndex(Routine.COL_ROUTINE_NAME));
        int length = c.getInt(c.getColumnIndex(Routine.COL_ROUTINE_LENGTH));
        String exercises = c.getString(c.getColumnIndex(Routine.COL_ROUTINE_EXERCISE_FKS));
        /*exercises[0] = c.getString(c.getColumnIndex(Routine.COL_EX1));
        exercises[1] = c.getString(c.getColumnIndex(Routine.COL_EX2));
        exercises[2] = c.getString(c.getColumnIndex(Routine.COL_EX3));
        exercises[3] = c.getString(c.getColumnIndex(Routine.COL_EX4));
        exercises[4] = c.getString(c.getColumnIndex(Routine.COL_EX5));
        exercises[5] = c.getString(c.getColumnIndex(Routine.COL_EX6));
        exercises[6] = c.getString(c.getColumnIndex(Routine.COL_EX7));
        exercises[7] = c.getString(c.getColumnIndex(Routine.COL_EX8));
        exercises[8] = c.getString(c.getColumnIndex(Routine.COL_EX9));
        exercises[9] = c.getString(c.getColumnIndex(Routine.COL_EX10));
        exercises[10] = c.getString(c.getColumnIndex(Routine.COL_EX11));
        exercises[11] = c.getString(c.getColumnIndex(Routine.COL_EX12));
        exercises[12] = c.getString(c.getColumnIndex(Routine.COL_EX13));
        exercises[13] = c.getString(c.getColumnIndex(Routine.COL_EX14));
        exercises[14] = c.getString(c.getColumnIndex(Routine.COL_EX15));
        exercises[15] = c.getString(c.getColumnIndex(Routine.COL_EX16));
        exercises[16] = c.getString(c.getColumnIndex(Routine.COL_EX17));
        exercises[17] = c.getString(c.getColumnIndex(Routine.COL_EX18));
        exercises[18] = c.getString(c.getColumnIndex(Routine.COL_EX19));
        exercises[19] = c.getString(c.getColumnIndex(Routine.COL_EX20));
        exercises[20] = c.getString(c.getColumnIndex(Routine.COL_EX21));
        exercises[21] = c.getString(c.getColumnIndex(Routine.COL_EX22));
        exercises[22] = c.getString(c.getColumnIndex(Routine.COL_EX23));
        exercises[23] = c.getString(c.getColumnIndex(Routine.COL_EX24));
        exercises[24] = c.getString(c.getColumnIndex(Routine.COL_EX25));*/
        Routine ans = new Routine();
        ans.setId(id);
        ans.setName(name);
        ans.setLength(length);
        ans.toObject(exercises);
        return ans;
    }

    // -------------------- Routine getRoutineByName(int id) ------------------------------
    // Parameters: an int, id, which is the id of the routine to get.
    // Output:     a Routine, object from the db
    // ------------------------------------------------------------------------------
    /*public Routine getRoutineByName(String name){
        SQLiteDatabase db = getReadableDatabase();

        String selectQuery = "SELECT * FROM " + Routine.ROUTINE_TABLE_NAME + " WHERE "
                + Routine.COL_ROUTINE_NAME + " = " + name;

        Cursor c = db.rawQuery(selectQuery, null);

        if(c!=null){
            c.moveToFirst();
        } else{
            return null;
        }

        int id = c.getInt(c.getColumnIndex(Routine.COL_ROUTINE_ID));
        int length = c.getInt(c.getColumnIndex(Routine.COL_ROUTINE_LENGTH));
        String[] exercises = new String[length];
        exercises[0] = c.getString(c.getColumnIndex(Routine.COL_EX1));
        exercises[1] = c.getString(c.getColumnIndex(Routine.COL_EX2));
        exercises[2] = c.getString(c.getColumnIndex(Routine.COL_EX3));
        exercises[3] = c.getString(c.getColumnIndex(Routine.COL_EX4));
        exercises[4] = c.getString(c.getColumnIndex(Routine.COL_EX5));
        exercises[5] = c.getString(c.getColumnIndex(Routine.COL_EX6));
        exercises[6] = c.getString(c.getColumnIndex(Routine.COL_EX7));
        exercises[7] = c.getString(c.getColumnIndex(Routine.COL_EX8));
        exercises[8] = c.getString(c.getColumnIndex(Routine.COL_EX9));
        exercises[9] = c.getString(c.getColumnIndex(Routine.COL_EX10));
        exercises[10] = c.getString(c.getColumnIndex(Routine.COL_EX11));
        exercises[11] = c.getString(c.getColumnIndex(Routine.COL_EX12));
        exercises[12] = c.getString(c.getColumnIndex(Routine.COL_EX13));
        exercises[13] = c.getString(c.getColumnIndex(Routine.COL_EX14));
        exercises[14] = c.getString(c.getColumnIndex(Routine.COL_EX15));
        exercises[15] = c.getString(c.getColumnIndex(Routine.COL_EX16));
        exercises[16] = c.getString(c.getColumnIndex(Routine.COL_EX17));
        exercises[17] = c.getString(c.getColumnIndex(Routine.COL_EX18));
        exercises[18] = c.getString(c.getColumnIndex(Routine.COL_EX19));
        exercises[19] = c.getString(c.getColumnIndex(Routine.COL_EX20));
        exercises[20] = c.getString(c.getColumnIndex(Routine.COL_EX21));
        exercises[21] = c.getString(c.getColumnIndex(Routine.COL_EX22));
        exercises[22] = c.getString(c.getColumnIndex(Routine.COL_EX23));
        exercises[23] = c.getString(c.getColumnIndex(Routine.COL_EX24));
        exercises[24] = c.getString(c.getColumnIndex(Routine.COL_EX25));
        return (new Routine(id, name, length, exercises));
    }*/

    // ----------------------- Routine getAllRoutines() -----------------------------
    // Parameters: N/A
    // Output:     an array with all the routine objects.
    // ------------------------------------------------------------------------------
    public Routine[] getAllRoutines(){
        String query = "SELECT  * FROM " + Routine.ROUTINE_TABLE_NAME;

        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(query, null);
        int count = 0;
        if(c != null){
            c.moveToFirst();
            count = 1;
            while(c.moveToNext()){
                count++;
            }
        }else {
            return null;
        }
        c.moveToFirst();
        Routine[] routines = new Routine[count];
        int id = c.getInt(c.getColumnIndex(Routine.COL_ROUTINE_ID));
        String name = c.getString(c.getColumnIndex(Routine.COL_ROUTINE_NAME));
        int length = c.getInt(c.getColumnIndex(Routine.COL_ROUTINE_LENGTH));
        String exercises = c.getString(c.getColumnIndex(Routine.COL_ROUTINE_EXERCISE_FKS));
        /*exercises[0] = c.getString(c.getColumnIndex(Routine.COL_EX1));
        exercises[1] = c.getString(c.getColumnIndex(Routine.COL_EX2));
        exercises[2] = c.getString(c.getColumnIndex(Routine.COL_EX3));
        exercises[3] = c.getString(c.getColumnIndex(Routine.COL_EX4));
        exercises[4] = c.getString(c.getColumnIndex(Routine.COL_EX5));
        exercises[5] = c.getString(c.getColumnIndex(Routine.COL_EX6));
        exercises[6] = c.getString(c.getColumnIndex(Routine.COL_EX7));
        exercises[7] = c.getString(c.getColumnIndex(Routine.COL_EX8));
        exercises[8] = c.getString(c.getColumnIndex(Routine.COL_EX9));
        exercises[9] = c.getString(c.getColumnIndex(Routine.COL_EX10));
        exercises[10] = c.getString(c.getColumnIndex(Routine.COL_EX11));
        exercises[11] = c.getString(c.getColumnIndex(Routine.COL_EX12));
        exercises[12] = c.getString(c.getColumnIndex(Routine.COL_EX13));
        exercises[13] = c.getString(c.getColumnIndex(Routine.COL_EX14));
        exercises[14] = c.getString(c.getColumnIndex(Routine.COL_EX15));
        exercises[15] = c.getString(c.getColumnIndex(Routine.COL_EX16));
        exercises[16] = c.getString(c.getColumnIndex(Routine.COL_EX17));
        exercises[17] = c.getString(c.getColumnIndex(Routine.COL_EX18));
        exercises[18] = c.getString(c.getColumnIndex(Routine.COL_EX19));
        exercises[19] = c.getString(c.getColumnIndex(Routine.COL_EX20));
        exercises[20] = c.getString(c.getColumnIndex(Routine.COL_EX21));
        exercises[21] = c.getString(c.getColumnIndex(Routine.COL_EX22));
        exercises[22] = c.getString(c.getColumnIndex(Routine.COL_EX23));
        exercises[23] = c.getString(c.getColumnIndex(Routine.COL_EX24));
        exercises[24] = c.getString(c.getColumnIndex(Routine.COL_EX25));*/
        routines[0] = new Routine();
        routines[0].setId(id);
        routines[0].setLength(length);
        routines[0].setName(name);
        routines[0].toObject(exercises);

        for(int i = 1; i<count; i++){
            c.moveToNext();
            String name1 = c.getString(c.getColumnIndex(Routine.COL_ROUTINE_NAME));
            int length1 = c.getInt(c.getColumnIndex(Routine.COL_ROUTINE_LENGTH));
            String exercises1 = c.getString(c.getColumnIndex(Routine.COL_ROUTINE_EXERCISE_FKS));
            int id1 = c.getInt(c.getColumnIndex(Routine.COL_ROUTINE_ID));
            /*exercises1[0] = c.getString(c.getColumnIndex(Routine.COL_EX1));
            exercises1[1] = c.getString(c.getColumnIndex(Routine.COL_EX2));
            exercises1[2] = c.getString(c.getColumnIndex(Routine.COL_EX3));
            exercises1[3] = c.getString(c.getColumnIndex(Routine.COL_EX4));
            exercises1[4] = c.getString(c.getColumnIndex(Routine.COL_EX5));
            exercises1[5] = c.getString(c.getColumnIndex(Routine.COL_EX6));
            exercises1[6] = c.getString(c.getColumnIndex(Routine.COL_EX7));
            exercises1[7] = c.getString(c.getColumnIndex(Routine.COL_EX8));
            exercises1[8] = c.getString(c.getColumnIndex(Routine.COL_EX9));
            exercises1[9] = c.getString(c.getColumnIndex(Routine.COL_EX10));
            exercises1[10] = c.getString(c.getColumnIndex(Routine.COL_EX11));
            exercises1[11] = c.getString(c.getColumnIndex(Routine.COL_EX12));
            exercises1[12] = c.getString(c.getColumnIndex(Routine.COL_EX13));
            exercises1[13] = c.getString(c.getColumnIndex(Routine.COL_EX14));
            exercises1[14] = c.getString(c.getColumnIndex(Routine.COL_EX15));
            exercises1[15] = c.getString(c.getColumnIndex(Routine.COL_EX16));
            exercises1[16] = c.getString(c.getColumnIndex(Routine.COL_EX17));
            exercises1[17] = c.getString(c.getColumnIndex(Routine.COL_EX18));
            exercises1[18] = c.getString(c.getColumnIndex(Routine.COL_EX19));
            exercises1[19] = c.getString(c.getColumnIndex(Routine.COL_EX20));
            exercises1[20] = c.getString(c.getColumnIndex(Routine.COL_EX21));
            exercises1[21] = c.getString(c.getColumnIndex(Routine.COL_EX22));
            exercises1[22] = c.getString(c.getColumnIndex(Routine.COL_EX23));
            exercises1[23] = c.getString(c.getColumnIndex(Routine.COL_EX24));
            exercises1[24] = c.getString(c.getColumnIndex(Routine.COL_EX25));*/
            routines[i] = new Routine();
            routines[i].setId(id1);
            routines[i].setName(name1);
            routines[i].setLength(length1);
            routines[i].toObject(exercises1);
        }
        return routines;
    }

    // ---------------- int updateRoutineName(int id, String name) ------------------
    // Parameters: an int, id, which is the id of the routine to be updated.
    //             an int, name, which is the new name to be updated in the db
    // Output:     a Routine, object from the db
    // ------------------------------------------------------------------------------
    public int updateRoutineName(int id, String name){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Routine.COL_ROUTINE_NAME, name);
        return db.update(Routine.ROUTINE_TABLE_NAME, contentValues, Routine.COL_ROUTINE_ID
                + " = ?", new String[]{ String.valueOf(id)});
    }

    // --------------- int updateRoutineLength(int id, int length) ------------------
    // Parameters: an int, id, which is the id of the routine to be updated.
    //             an int, length, which is the new length to be updated in the db
    // Output:     in the id of the Routine updated
    // ------------------------------------------------------------------------------
    public int updateRoutineLength(int id, int length){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Routine.COL_ROUTINE_LENGTH, length);
        return db.update(Routine.ROUTINE_TABLE_NAME, contentValues, Routine.COL_ROUTINE_ID
                + " = ?", new String[]{ String.valueOf(id)});
    }

    // ------------ int updateRoutineExercises(int id, String name) -----------------
    // Parameters: an int, id, which is the id of the routine to be updated.
    //             an int, name, which is the new name to be updated in the db
    // Output:     a Routine, object from the db
    // ------------------------------------------------------------------------------
    public int updateRoutineExercises(int id, String exercises){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Routine.COL_ROUTINE_EXERCISE_FKS, exercises);
        /*contentValues.put(Routine.COL_EX1, exercises[0]);
        contentValues.put(Routine.COL_EX2, exercises[1]);
        contentValues.put(Routine.COL_EX3, exercises[2]);
        contentValues.put(Routine.COL_EX4, exercises[3]);
        contentValues.put(Routine.COL_EX5, exercises[4]);
        contentValues.put(Routine.COL_EX6, exercises[5]);
        contentValues.put(Routine.COL_EX7, exercises[6]);
        contentValues.put(Routine.COL_EX8, exercises[7]);
        contentValues.put(Routine.COL_EX9, exercises[8]);
        contentValues.put(Routine.COL_EX10, exercises[9]);
        contentValues.put(Routine.COL_EX11, exercises[10]);
        contentValues.put(Routine.COL_EX12, exercises[11]);
        contentValues.put(Routine.COL_EX13, exercises[12]);
        contentValues.put(Routine.COL_EX14, exercises[13]);
        contentValues.put(Routine.COL_EX15, exercises[14]);
        contentValues.put(Routine.COL_EX16, exercises[15]);
        contentValues.put(Routine.COL_EX17, exercises[16]);
        contentValues.put(Routine.COL_EX18, exercises[17]);
        contentValues.put(Routine.COL_EX19, exercises[18]);
        contentValues.put(Routine.COL_EX20, exercises[19]);
        contentValues.put(Routine.COL_EX21, exercises[20]);
        contentValues.put(Routine.COL_EX22, exercises[21]);
        contentValues.put(Routine.COL_EX23, exercises[22]);
        contentValues.put(Routine.COL_EX24, exercises[23]);
        contentValues.put(Routine.COL_EX25, exercises[24]);*/
        return db.update(Routine.ROUTINE_TABLE_NAME, contentValues, Routine.COL_ROUTINE_ID
                + " = ?", new String[]{ String.valueOf(id)});
    }

    // ----------------------- void deleteRoutine(int id) ---------------------------
    // Parameters: an int, id, which is the id of the routine to be deleted.
    // Output:     n/a
    // ------------------------------------------------------------------------------
    public void deleteRoutine(int id){
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(Routine.ROUTINE_TABLE_NAME, Routine.COL_ROUTINE_ID
        + " = ?", new String[] {String.valueOf(id)});
    }

    // ------------------------------------------------------------------------------

    //                      End of Routine Table Functions

    // ------------------------------------------------------------------------------


    // ------------------------------------------------------------------------------

    //                         Week Table Functions

    // ------------------------------------------------------------------------------

    // ----------------------- int insertWeek(Week week) ----------------------------
    // Parameters: a Week, week, which is the Week to be saved in the db.
    // Output:     the index of the newly saved Week.
    // ------------------------------------------------------------------------------
    public int insertWeek(Week week){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Week.COL_PROGRAM_FOREIGN_ID, week.getProgramForeignId());
        contentValues.put(Week.COL_DAY1, week.getDays()[0]);
        contentValues.put(Week.COL_DAY2, week.getDays()[1]);
        contentValues.put(Week.COL_DAY3, week.getDays()[2]);
        contentValues.put(Week.COL_DAY4, week.getDays()[3]);
        contentValues.put(Week.COL_DAY5, week.getDays()[4]);
        contentValues.put(Week.COL_DAY6, week.getDays()[5]);
        contentValues.put(Week.COL_DAY7, week.getDays()[6]);

        return (int) db.insert(Week.WEEK_TABLE_NAME, null, contentValues);
    }

    // ----------------- Week getWeek(int id, int cycleLength) ----------------------
    // Parameters: an int, id, which is program_foreign_id of the Week to get.
    //             and int, cycleLength, that tells how many weeks needed.
    // Output:     An array of Week objects with program_foreign_id == id.
    // ------------------------------------------------------------------------------
    public Week[] getWeeks(int id, int cycleLength) {
        int numOfWeeks = cycleLength / 7;
        if(cycleLength % 7 != 0){
            numOfWeeks++;
        }
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + Week.WEEK_TABLE_NAME +
                " WHERE " + Week.COL_PROGRAM_FOREIGN_ID + " = " + id;

        Cursor c = db.rawQuery(selectQuery, null);

        Week[] weeks = new Week[numOfWeeks];
        String[] days = new String[7];
        int primaryId;
        if (c != null) {
            c.moveToFirst();
            primaryId = c.getInt(c.getColumnIndex(Week.WEEK_PRIMARY_ID));
            days[0] = c.getString(c.getColumnIndex(Week.COL_DAY1));
            days[1] = c.getString(c.getColumnIndex(Week.COL_DAY2));
            days[2] = c.getString(c.getColumnIndex(Week.COL_DAY3));
            days[3] = c.getString(c.getColumnIndex(Week.COL_DAY4));
            days[4] = c.getString(c.getColumnIndex(Week.COL_DAY5));
            days[5] = c.getString(c.getColumnIndex(Week.COL_DAY6));
            weeks[0] = new Week(id, days);
            weeks[0].setId(primaryId);
        }else{
            return null;
        }
        int counter = 1;
        while(c.moveToNext()){
            primaryId = c.getInt(c.getColumnIndex(Week.WEEK_PRIMARY_ID));
            days[0] = c.getString(c.getColumnIndex(Week.COL_DAY1));
            days[1] = c.getString(c.getColumnIndex(Week.COL_DAY2));
            days[2] = c.getString(c.getColumnIndex(Week.COL_DAY3));
            days[3] = c.getString(c.getColumnIndex(Week.COL_DAY4));
            days[4] = c.getString(c.getColumnIndex(Week.COL_DAY5));
            days[5] = c.getString(c.getColumnIndex(Week.COL_DAY6));
            days[6] = c.getString(c.getColumnIndex(Week.COL_DAY7));
            weeks[counter] = new Week(id, days);
            weeks[counter].setId(primaryId);
        }

        c.close();

        return weeks;
    }

    // ------------- void updateWeekProgramForeignKey(int id, int fkey) -------------
    // Parameters: an int, id, which is id of the Week to be updated.
    //             an int, fkey, which is the new foreign key to be saved.
    // Output:     the id of the week updated.
    // ------------------------------------------------------------------------------
    public int updateWeekProgramForeignKey(int id, int fkey){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(Week.COL_PROGRAM_FOREIGN_ID, fkey);

        return db.update(Week.WEEK_TABLE_NAME, cv, Week.WEEK_PRIMARY_ID +
                " = ?", new String[] { String.valueOf(id)});
    }

    // ---------------- void updateWeekDays(int id, int[] days) ---------------------
    // Parameters: an int, id, which is id of the week to be updated.
    //             an int array, days, which has the new days to save.
    // Output:     The Id of the updated week.
    // ------------------------------------------------------------------------------
    public int updateWeekDays(int id, int[] days){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(Week.COL_DAY1, days[0]);
        cv.put(Week.COL_DAY2, days[1]);
        cv.put(Week.COL_DAY3, days[2]);
        cv.put(Week.COL_DAY4, days[3]);
        cv.put(Week.COL_DAY5, days[4]);
        cv.put(Week.COL_DAY6, days[5]);
        cv.put(Week.COL_DAY7, days[6]);

        return db.update(Week.WEEK_TABLE_NAME, cv, Week.WEEK_PRIMARY_ID +
                " = ?", new String[] { String.valueOf(id)});
    }

    // ----------------------- void deleteWeek(int id) ------------------------------
    // Parameters: an int, id, which is id of the week to be deleted.
    // Output:     N/A
    // ------------------------------------------------------------------------------
    public void deleteWeek(int id){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(Week.WEEK_TABLE_NAME, Week.WEEK_PRIMARY_ID +
                " = ?", new String[] {String.valueOf(id)});
    }

    // ------------------------------------------------------------------------------

    //                      End of Week Table Functions

    // ------------------------------------------------------------------------------


    // ------------------------------------------------------------------------------

    //                         Workout Table Functions

    // ------------------------------------------------------------------------------
    // ------------------- int insertWorkout(Workout workout) -----------------------
    // Parameters: a Workout object, workout, which is the workout to be saved.
    // Output:     the id of the newly inserted workout.
    // ------------------------------------------------------------------------------
    public int insertWorkout(Workout workout){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Workout.COL_ROUTINE_FOREIGN_ID, workout.getRoutineForeignId());
        values.put(Workout.COL_WORKOUT_DAY, workout.getDay());
        values.put(Workout.COL_WORKOUT_MONTH, workout.getMonth());
        values.put(Workout.COL_WORKOUT_YEAR, workout.getYear());
        /*values.put(Workout.COL_DATA_UNITS, workout.getUnits());
        values.put(Workout.COL_EX1_DATA, workout.getData()[0]);
        values.put(Workout.COL_EX2_DATA, workout.getData()[0]);
        values.put(Workout.COL_EX3_DATA, workout.getData()[0]);
        values.put(Workout.COL_EX4_DATA, workout.getData()[0]);
        values.put(Workout.COL_EX5_DATA, workout.getData()[0]);
        values.put(Workout.COL_EX6_DATA, workout.getData()[0]);
        values.put(Workout.COL_EX7_DATA, workout.getData()[0]);
        values.put(Workout.COL_EX8_DATA, workout.getData()[0]);
        values.put(Workout.COL_EX9_DATA, workout.getData()[0]);
        values.put(Workout.COL_EX10_DATA, workout.getData()[0]);
        values.put(Workout.COL_EX11_DATA, workout.getData()[0]);
        values.put(Workout.COL_EX12_DATA, workout.getData()[0]);
        values.put(Workout.COL_EX13_DATA, workout.getData()[0]);
        values.put(Workout.COL_EX14_DATA, workout.getData()[0]);
        values.put(Workout.COL_EX15_DATA, workout.getData()[0]);
        values.put(Workout.COL_EX16_DATA, workout.getData()[0]);
        values.put(Workout.COL_EX17_DATA, workout.getData()[0]);
        values.put(Workout.COL_EX18_DATA, workout.getData()[0]);
        values.put(Workout.COL_EX19_DATA, workout.getData()[0]);
        values.put(Workout.COL_EX20_DATA, workout.getData()[0]);
        values.put(Workout.COL_EX21_DATA, workout.getData()[0]);
        values.put(Workout.COL_EX22_DATA, workout.getData()[0]);
        values.put(Workout.COL_EX23_DATA, workout.getData()[0]);
        values.put(Workout.COL_EX24_DATA, workout.getData()[0]);
        values.put(Workout.COL_EX25_DATA, workout.getData()[0]);*/

        return (int) db.insert(Workout.WORKOUT_TABLE_NAME, null, values);
    }

    // ----------------------- int getWorkoutbyId(int id) ---------------------------
    // Parameters: an int, id, which is the  id of the workout to be returned.
    // Output:     the workout with a primary id == id.
    // ------------------------------------------------------------------------------
    public Workout getWorkoutById(int id){
        SQLiteDatabase db = getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + Workout.WORKOUT_TABLE_NAME +
                " WHERE " + Workout.COL_WORKOUT_PRIMARY_ID + " = " + id;

        Cursor c = db.rawQuery(selectQuery, null);

        if(c != null){
            c.moveToFirst();
        }else{
            return null;
        }

        Workout workout = new Workout();
        workout.setId(c.getInt(c.getColumnIndex(Workout.COL_WORKOUT_PRIMARY_ID)));
        int[] date = new int[3];
        date[0] = c.getInt(c.getColumnIndex(Workout.COL_WORKOUT_DAY));
        date[1] = c.getInt(c.getColumnIndex(Workout.COL_WORKOUT_MONTH));
        date[2] = c.getInt(c.getColumnIndex(Workout.COL_WORKOUT_YEAR));
        workout.setDate(date);
        workout.setRoutineForeignId(c.getInt(c.getColumnIndex(Workout.COL_ROUTINE_FOREIGN_ID)));
        /*int[] data = new int[25];
        data[0] = c.getInt(c.getColumnIndex(Workout.COL_EX1_DATA));
        data[1] = c.getInt(c.getColumnIndex(Workout.COL_EX2_DATA));
        data[2] = c.getInt(c.getColumnIndex(Workout.COL_EX3_DATA));
        data[3] = c.getInt(c.getColumnIndex(Workout.COL_EX4_DATA));
        data[4] = c.getInt(c.getColumnIndex(Workout.COL_EX5_DATA));
        data[5] = c.getInt(c.getColumnIndex(Workout.COL_EX6_DATA));
        data[6] = c.getInt(c.getColumnIndex(Workout.COL_EX7_DATA));
        data[7] = c.getInt(c.getColumnIndex(Workout.COL_EX8_DATA));
        data[8] = c.getInt(c.getColumnIndex(Workout.COL_EX9_DATA));
        data[9] = c.getInt(c.getColumnIndex(Workout.COL_EX10_DATA));
        data[10] = c.getInt(c.getColumnIndex(Workout.COL_EX11_DATA));
        data[11] = c.getInt(c.getColumnIndex(Workout.COL_EX12_DATA));
        data[12] = c.getInt(c.getColumnIndex(Workout.COL_EX13_DATA));
        data[13] = c.getInt(c.getColumnIndex(Workout.COL_EX14_DATA));
        data[14] = c.getInt(c.getColumnIndex(Workout.COL_EX15_DATA));
        data[15] = c.getInt(c.getColumnIndex(Workout.COL_EX16_DATA));
        data[16] = c.getInt(c.getColumnIndex(Workout.COL_EX17_DATA));
        data[17] = c.getInt(c.getColumnIndex(Workout.COL_EX18_DATA));
        data[18] = c.getInt(c.getColumnIndex(Workout.COL_EX19_DATA));
        data[19] = c.getInt(c.getColumnIndex(Workout.COL_EX20_DATA));
        data[20] = c.getInt(c.getColumnIndex(Workout.COL_EX21_DATA));
        data[21] = c.getInt(c.getColumnIndex(Workout.COL_EX22_DATA));
        data[22] = c.getInt(c.getColumnIndex(Workout.COL_EX23_DATA));
        data[23] = c.getInt(c.getColumnIndex(Workout.COL_EX24_DATA));
        data[24] = c.getInt(c.getColumnIndex(Workout.COL_EX25_DATA));
        workout.setData(data);*/
        return workout;
    }

    // -------------------- int getWorkoutbyDate(String date) -----------------------
    // Parameters: a String, date, which is the date of the workout to be returned.
    // Output:     the workout with a primary id == id.
    // ------------------------------------------------------------------------------
    public Workout getWorkoutByDate(int[] date){
        SQLiteDatabase db = getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + Workout.WORKOUT_TABLE_NAME +
                " WHERE " + Workout.COL_WORKOUT_DAY + " = " + date[0] + " AND " // may need to fix later
                + Workout.COL_WORKOUT_MONTH + " = " + date[1] + " AND "
                + Workout.COL_WORKOUT_YEAR + " = " + date[2];

        Cursor c = db.rawQuery(selectQuery, null);

        if(c != null){
            c.moveToFirst();
        }else{
            return null;
        }

        Workout workout = new Workout();
        workout.setId(c.getInt(c.getColumnIndex(Workout.COL_WORKOUT_PRIMARY_ID)));
        int[] date2 = new int[3];
        date[0] = c.getInt(c.getColumnIndex(Workout.COL_WORKOUT_DAY));
        date[1] = c.getInt(c.getColumnIndex(Workout.COL_WORKOUT_MONTH));
        date[2] = c.getInt(c.getColumnIndex(Workout.COL_WORKOUT_YEAR));
        workout.setDate(date2);
        workout.setRoutineForeignId(c.getInt(c.getColumnIndex(Workout.COL_ROUTINE_FOREIGN_ID)));
        /*workout.setUnits(c.getString(c.getColumnIndex(Workout.COL_DATA_UNITS)));
        int[] data = new int[25];
        data[0] = c.getInt(c.getColumnIndex(Workout.COL_EX1_DATA));
        data[1] = c.getInt(c.getColumnIndex(Workout.COL_EX2_DATA));
        data[2] = c.getInt(c.getColumnIndex(Workout.COL_EX3_DATA));
        data[3] = c.getInt(c.getColumnIndex(Workout.COL_EX4_DATA));
        data[4] = c.getInt(c.getColumnIndex(Workout.COL_EX5_DATA));
        data[5] = c.getInt(c.getColumnIndex(Workout.COL_EX6_DATA));
        data[6] = c.getInt(c.getColumnIndex(Workout.COL_EX7_DATA));
        data[7] = c.getInt(c.getColumnIndex(Workout.COL_EX8_DATA));
        data[8] = c.getInt(c.getColumnIndex(Workout.COL_EX9_DATA));
        data[9] = c.getInt(c.getColumnIndex(Workout.COL_EX10_DATA));
        data[10] = c.getInt(c.getColumnIndex(Workout.COL_EX11_DATA));
        data[11] = c.getInt(c.getColumnIndex(Workout.COL_EX12_DATA));
        data[12] = c.getInt(c.getColumnIndex(Workout.COL_EX13_DATA));
        data[13] = c.getInt(c.getColumnIndex(Workout.COL_EX14_DATA));
        data[14] = c.getInt(c.getColumnIndex(Workout.COL_EX15_DATA));
        data[15] = c.getInt(c.getColumnIndex(Workout.COL_EX16_DATA));
        data[16] = c.getInt(c.getColumnIndex(Workout.COL_EX17_DATA));
        data[17] = c.getInt(c.getColumnIndex(Workout.COL_EX18_DATA));
        data[18] = c.getInt(c.getColumnIndex(Workout.COL_EX19_DATA));
        data[19] = c.getInt(c.getColumnIndex(Workout.COL_EX20_DATA));
        data[20] = c.getInt(c.getColumnIndex(Workout.COL_EX21_DATA));
        data[21] = c.getInt(c.getColumnIndex(Workout.COL_EX22_DATA));
        data[22] = c.getInt(c.getColumnIndex(Workout.COL_EX23_DATA));
        data[23] = c.getInt(c.getColumnIndex(Workout.COL_EX24_DATA));
        data[24] = c.getInt(c.getColumnIndex(Workout.COL_EX25_DATA));
        workout.setData(data);*/
        return workout;
    }

    // ------------------- int updateDate(int id, String date) ----------------------
    // Parameters: a String, date, which is the new date to update.
    //             an int, id, which is the id of the Workout to update.
    // Output:     the workout with a primary id == id.
    // ------------------------------------------------------------------------------
    public int updateDate(int id, int[] date){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(Workout.COL_WORKOUT_DAY, date[0]);
        cv.put(Workout.COL_WORKOUT_MONTH, date[1]);
        cv.put(Workout.COL_WORKOUT_YEAR, date[2]);

        return db.update(Workout.WORKOUT_TABLE_NAME, cv, Workout.COL_WORKOUT_PRIMARY_ID
                + " = ?", new String[]{ String.valueOf(id)});
    }

    // --------------------- int updateDate(int id, int fkey) -----------------------
    // Parameters: an int, fkey, which is the new foreign key to update.
    //             an int, id, which is the id of the Workout to update.
    // Output:     the workout with a primary id == id.
    // ------------------------------------------------------------------------------
    public int updateRoutineForeignKey(int id, int fkey){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(Workout.COL_ROUTINE_FOREIGN_ID, fkey);

        return db.update(Workout.WORKOUT_TABLE_NAME, cv, Workout.COL_WORKOUT_PRIMARY_ID
                + " = ?", new String[]{ String.valueOf(id)});
    }

    // ------------------ int updateUnits(int id, String units) ---------------------
    // Parameters: a String, units, which is the new date to update.
    //             an int, id, which is the id of the Workout to update.
    // Output:     the workout with a primary id == id.
    // ------------------------------------------------------------------------------
    /*public int updateUnits(int id, String units){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(Workout.COL_DATA_UNITS, units);

        return db.update(Workout.WORKOUT_TABLE_NAME, cv, Workout.COL_WORKOUT_PRIMARY_ID
                + " = ?", new String[]{ String.valueOf(id)});
    }*/

    // -------------------- int updateData(int id, int data) ------------------------
    // Parameters: a int, data, which is the new data to update.
    //             an int, id, which is the id of the Workout to update.
    // Output:     the workout with a primary id == id.
    // ------------------------------------------------------------------------------
    /*public int updateData(int id, int[] data){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(Workout.COL_EX1_DATA, data[0]);
        cv.put(Workout.COL_EX2_DATA, data[1]);
        cv.put(Workout.COL_EX3_DATA, data[2]);
        cv.put(Workout.COL_EX4_DATA, data[3]);
        cv.put(Workout.COL_EX5_DATA, data[4]);
        cv.put(Workout.COL_EX6_DATA, data[5]);
        cv.put(Workout.COL_EX7_DATA, data[6]);
        cv.put(Workout.COL_EX8_DATA, data[7]);
        cv.put(Workout.COL_EX9_DATA, data[8]);
        cv.put(Workout.COL_EX10_DATA, data[9]);
        cv.put(Workout.COL_EX11_DATA, data[10]);
        cv.put(Workout.COL_EX12_DATA, data[11]);
        cv.put(Workout.COL_EX13_DATA, data[12]);
        cv.put(Workout.COL_EX14_DATA, data[13]);
        cv.put(Workout.COL_EX15_DATA, data[14]);
        cv.put(Workout.COL_EX16_DATA, data[15]);
        cv.put(Workout.COL_EX17_DATA, data[16]);
        cv.put(Workout.COL_EX18_DATA, data[17]);
        cv.put(Workout.COL_EX19_DATA, data[18]);
        cv.put(Workout.COL_EX20_DATA, data[19]);
        cv.put(Workout.COL_EX21_DATA, data[20]);
        cv.put(Workout.COL_EX22_DATA, data[21]);
        cv.put(Workout.COL_EX23_DATA, data[22]);
        cv.put(Workout.COL_EX24_DATA, data[23]);
        cv.put(Workout.COL_EX25_DATA, data[24]);

        return db.update(Workout.WORKOUT_TABLE_NAME, cv, Workout.COL_WORKOUT_PRIMARY_ID
                + " = ?", new String[]{ String.valueOf(id)});
    }*/

    public void deleteWorkout(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Workout.WORKOUT_TABLE_NAME, Workout.COL_WORKOUT_PRIMARY_ID +
                " = ?", new String[]{ String.valueOf(id)});
    }

    // ------------------------------------------------------------------------------

    //                      End of Workout Table Functions

    // ------------------------------------------------------------------------------


    // ------------------------------------------------------------------------------

    //                         Exercise Table Functions

    // ------------------------------------------------------------------------------

    public int insertExercise(Exercise exercise){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(exercise.EXERCISES_COL_NAME, exercise.getName());
        contentValues.put(exercise.EXERCISES_COL_WORKOUTFK, exercise.getWorkoutfk());
        contentValues.put(exercise.EXERCISES_COL_DATA, exercise.toString());

        return (int) db.insert(exercise.EXERCISES_TABLE_NAME, null, contentValues);
    }

    public Exercise getExerciseById(int id){
        SQLiteDatabase db = getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + Exercise.EXERCISES_TABLE_NAME +
                " WHERE " + Exercise.EXERCISES_COL_ID + " = " + id;

        Cursor c = db.rawQuery(selectQuery, null);

        if(c != null){
            c.moveToFirst();
        }else{
            return null;
        }

        Exercise exercise = new Exercise();
        exercise.setId(c.getInt(c.getColumnIndex(Exercise.EXERCISES_COL_ID)));
        exercise.setName(c.getString(c.getColumnIndex(Exercise.EXERCISES_COL_NAME)));
        exercise.setWorkoutfk(c.getInt(c.getColumnIndex(Exercise.EXERCISES_COL_WORKOUTFK)));
        String data = c.getString(c.getColumnIndex(Exercise.EXERCISES_COL_DATA));
        exercise.toObject(data);
        return exercise;
    }

    public Exercise[] getExercisesByName(String name){
        SQLiteDatabase db = getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + Exercise.EXERCISES_TABLE_NAME +
                " WHERE " + Exercise.EXERCISES_COL_NAME + " = " + name;

        Cursor c = db.rawQuery(selectQuery, null);

        int count = 0;
        if(c != null){
            c.moveToFirst();
            count = 1;
            while(c.moveToNext()){
                count++;
            }
        }else {
            return null;
        }
        c.moveToFirst();
        Exercise[] exercises = new Exercise[count];
        count = 0;
        exercises[0].setId(c.getInt(c.getColumnIndex(Exercise.EXERCISES_COL_ID)));
        exercises[0].setName(c.getString(c.getColumnIndex(Exercise.EXERCISES_COL_NAME)));
        exercises[0].setWorkoutfk(c.getInt(c.getColumnIndex(Exercise.EXERCISES_COL_WORKOUTFK)));
        String data = c.getString(c.getColumnIndex(Exercise.EXERCISES_COL_DATA));
        exercises[0].toObject(data);

        while(c.moveToNext()){
            count++;
            exercises[count].setId(c.getInt(c.getColumnIndex(Exercise.EXERCISES_COL_ID)));
            exercises[count].setName(c.getString(c.getColumnIndex(Exercise.EXERCISES_COL_NAME)));
            exercises[count].setWorkoutfk(c.getInt(c.getColumnIndex(Exercise.EXERCISES_COL_WORKOUTFK)));
            data = c.getString(c.getColumnIndex(Exercise.EXERCISES_COL_DATA));
            exercises[count].toObject(data);
        }
        return exercises;
    }

    public Exercise[] getExercisesByWorkoutfk(int workoutfk){
        SQLiteDatabase db = getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + Exercise.EXERCISES_TABLE_NAME +
                " WHERE " + Exercise.EXERCISES_COL_WORKOUTFK + " = " + workoutfk;

        Cursor c = db.rawQuery(selectQuery, null);

        int count = 0;
        if(c != null){
            c.moveToFirst();
            count = 1;
            while(c.moveToNext()){
                count++;
            }
        }else {
            return null;
        }
        c.moveToFirst();
        Exercise[] exercises = new Exercise[count];
        count = 0;
        exercises[0].setId(c.getInt(c.getColumnIndex(Exercise.EXERCISES_COL_ID)));
        exercises[0].setName(c.getString(c.getColumnIndex(Exercise.EXERCISES_COL_NAME)));
        exercises[0].setWorkoutfk(c.getInt(c.getColumnIndex(Exercise.EXERCISES_COL_WORKOUTFK)));
        String data = c.getString(c.getColumnIndex(Exercise.EXERCISES_COL_DATA));
        exercises[0].toObject(data);

        while(c.moveToNext()){
            count++;
            exercises[count].setId(c.getInt(c.getColumnIndex(Exercise.EXERCISES_COL_ID)));
            exercises[count].setName(c.getString(c.getColumnIndex(Exercise.EXERCISES_COL_NAME)));
            exercises[count].setWorkoutfk(c.getInt(c.getColumnIndex(Exercise.EXERCISES_COL_WORKOUTFK)));
            data = c.getString(c.getColumnIndex(Exercise.EXERCISES_COL_DATA));
            exercises[count].toObject(data);
        }
        return exercises;
    }

    public int updateExercise(Exercise exercise){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(Exercise.EXERCISES_COL_NAME, exercise.getName());
        cv.put(Exercise.EXERCISES_COL_WORKOUTFK, exercise.getWorkoutfk());
        String data = exercise.toString();
        cv.put(Exercise.EXERCISES_COL_DATA, data);


        return db.update(Workout.WORKOUT_TABLE_NAME, cv, Workout.COL_WORKOUT_PRIMARY_ID
                + " = ?", new String[]{ String.valueOf(exercise.getId())});
    }

    public void deleteExercise(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Exercise.EXERCISES_TABLE_NAME, Exercise.EXERCISES_COL_ID +
                " = ?", new String[]{ String.valueOf(id)});
    }

}//-------------------------- End Of Class DBHelper.java ----------------------------
