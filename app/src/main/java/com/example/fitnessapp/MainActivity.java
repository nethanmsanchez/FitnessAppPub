package com.example.fitnessapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import java.util.ArrayList;
import java.util.Arrays;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, ContentFrag.ContentFragComms, TitleFrag.TitleFragComms,
        ScrollingFrag.ScrollingFragComms, AddRoutineFrag.AddRoutineFragComms{
    // fields
    private DBHelper db;
    private Program program;
    private Routine routine;
    private User user;
    private static final String TAG = "MainActivity.java";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //boolean returning = false;
        db = new DBHelper(this);            // get new instance of DBHelper object
        User u = db.getUser(1);                 // getting the first user(version 1 only supports one user)
        this.user = u;
        setContentView(R.layout.activity_main);

        // initialization code that's the same whether user is new or not
        String title_frag_button_names[] = new String[7];
        title_frag_button_names[0] = "Test 1";
        title_frag_button_names[1] = "Test 2";
        title_frag_button_names[2] = "Test 3";
        title_frag_button_names[3] = "Test 4";
        title_frag_button_names[4] = "Test 5";
        title_frag_button_names[5] = "Test 6";
        title_frag_button_names[6] = "Test 7";
        TitleFrag titleFrag = TitleFrag.newInstance(title_frag_button_names);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.LLtitleplaceholder, titleFrag);
        if(u == null){
            // code reaches here if user is new
            Log.e(TAG, "new user, launching register frag");
            ContentFrag contentFrag = ContentFrag.newInstance("register", null);
            fragmentTransaction.add(R.id.LLcontentplaceholder, contentFrag);
            //fragmentTransaction.addToBackStack("register");
            fragmentTransaction.commit();
        } else {
            Log.e(TAG, "returning user, launching appropriate day view");
            // u is the returning user. go to proper screen
            // either pre or post workout
        }
    }

    // ------------------------------      Methods      ----------------------------------


    // ------------------------------ Register ----------------------------------
    public void regIsDone(){
        Log.e(TAG, "register is done, launching create Program");
        //FragmentManager fragmentManager = getSupportFragmentManager();
        launchCreateProgramFrag();
    }
    // --------------------------- end of Register -------------------------------


    // ------------------------------ add Routine ----------------------------------
    public void addRoutine(){
        // replace current frag with addRoutine frag, also create the routine object
        // and store in this.routine field.
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        AddRoutineFrag addRoutineFrag = AddRoutineFrag.newInstance(0, 0, null);
        fragmentTransaction.replace(R.id.LLcontentplaceholder, addRoutineFrag);
        this.routine = new Routine();
        fragmentTransaction.commit();
    }
    public void cancelAddRoutine(){

    }
    public void addRoutineInitDone(int id, int curr_ex, int curr_set, String routine_name,
                                   String ex_name, String reps, String weight, String units){

    }
    public void addRoutineNewSetDone(int id, int curr_ex, int curr_set, String reps, String weight){

    }
    public void addRoutineNewExDone(int id, int curr_ex, int curr_set, String ex_name, String reps, String weight, String units){

    }
    /*public void addRoutine(){
        Log.e(TAG, "starting addRoutine");
        // fragment transaction code
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack("addRoutine back to create program");
        // addRoutine prep
        ArrayList<String> answers = new ArrayList<String>();
        ArrayList<String> prompts = new ArrayList<String>();
        prompts.add("Please enter a workout exercise to add to your routine");
        prompts.add(0,"Please name your new workout routine below");
        // create frag and finish frag transaction
        SlideShowFrag  frag = SlideShowFrag.newInstance( prompts, 0, answers);
        fragmentTransaction.replace(R.id.LLcontentplaceholder, frag);
        // call fragHelper here     (8/10/20 not sure what this comment means yet)
    }
    // -- SlideShowComms --
    // left button is the back button
    public void leftButtonClicked(ArrayList<String> prompts, int curr, ArrayList<String> answers){
        if(curr == 0){
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.popBackStack();
        } else {
            SlideShowFrag newfrag = SlideShowFrag.newInstance(prompts, curr-1, answers);
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.LLcontentplaceholder, newfrag);
        }
    }
    // This method is called when the "done"(middle) button in the addRoutine frag is pressed.
    // it recieves an ArrayList<String> which is
    public void rightButtonClicked(ArrayList<String> answers, int curr){
        Routine routine = new Routine(-1, answers.get(0), curr, null);
        routine.setIntArray(answers, curr+1);
        int fk = db.insertRoutine(routine);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

    }
    // middle button is the forward button
    public void middleButtonClicked(ArrayList<String> prompts, int curr, ArrayList<String> answers){
        SlideShowFrag frag = SlideShowFrag.newInstance(prompts, curr +1, answers);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.LLcontentplaceholder, frag);
    }
    public DBHelper getDbSsf(){
        return this.db;
    }*/
    // -- End of SlideShowComms --
    // --------------------------- end of add Routine -------------------------------

    // --------------------------- Create Workout ----------------------------------
    private void launchCreateProgramFrag(){
        program = new Program();
        Log.e(TAG, "in launchCreateProgramFrag");
        // get all routines
        Routine[] all_routines = db.getAllRoutines();
        String[] routine_names = new String[all_routines.length];
        for(int i = 0; i < all_routines.length; i++){
            routine_names[i] = all_routines[i].getName();
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        ContentFrag contentFrag = ContentFrag.newInstance("create_program", null);
        ContentFrag contentFrag1 = ContentFrag.newInstance("day_frag", null);
        String[] days = new String[1];
        days[0] = "day 1";
        TitleFrag altTitleFrag = TitleFrag.newAltInstance(days);
        ScrollingFrag dayRoutines = ScrollingFrag.newInstance("Day 1", null);
        ScrollingFrag allRoutines = ScrollingFrag.newInstance("All Routines", routine_names);
        fragmentTransaction.replace(R.id.LLcontentplaceholder, contentFrag);
        fragmentTransaction.add(R.id.LL_cwpf_dfholder, contentFrag1);
        fragmentTransaction.add(R.id.LL_cwpf_alfholder, altTitleFrag);
        fragmentTransaction.add(R.id.LL_df_dayroutines, dayRoutines);
        fragmentTransaction.add(R.id.LL_df_allroutines, allRoutines);
        fragmentTransaction.addToBackStack("create_program");
        fragmentTransaction.commit();
    }
    public void createProgramDone(){
        // go to day view

        program = null;
    }

    // -- Title Frag Comms --
    public void addDay(){

    }
    public void delDay(){

    }
    public void showDay(int n){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
    }
    // -- End of Title Frag Comms --
    // -- ScrollingFragComms --
    public void buttonClicked(String string, String title){
        if( title == "All Routines"){
            //Routine routine = db.getRoutineByName(string);
            //if(routine != null){

            //}
        }
    }
    // -- end of Comms frag --


    // -------------------------------- MISC ----------------------------------------

    public DBHelper getDb(){
        return this.db;
    }

    @Override
    public void onAttachFragment(Fragment fragment){
        if(fragment instanceof ContentFrag){
            ContentFrag contentFrag = (ContentFrag) fragment;
            contentFrag.setContentFragComms(this);
        }else if(fragment instanceof TitleFrag){
            TitleFrag titleFrag = (TitleFrag) fragment;
            titleFrag.setTitleFragComms(this);
        }else if(fragment instanceof  AddRoutineFrag){
            AddRoutineFrag addRoutineFrag = (AddRoutineFrag) fragment;
            addRoutineFrag.setAddRoutineFragComms(this);
        }else if(fragment instanceof  ScrollingFrag){
            ScrollingFrag scrollingFrag = (ScrollingFrag) fragment;
            scrollingFrag.setScrollingFragComms(this);
        }
    }

    @Override
    public void onClick(View view){
        switch(view.getId()){

        }
    }
}
