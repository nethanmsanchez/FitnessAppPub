package com.example.fitnessapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import java.util.Calendar;


public class ContentFrag extends Fragment{
    // fields
    private ContentFragComms comms;
    private View.OnClickListener regSubmitButtonClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v){
            regSubmitButtonClicked();
        }
    };
    private View.OnClickListener addRoutineButtonClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View view){
            addRoutineButtonClicked();
        }
    };
    private View.OnClickListener submitProgramClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View view){
            submitProgramButtonClicked();
        }
    };
    private static final String TAG = "ContentFrag.java";

    public static ContentFrag newInstance(String type, String message){
        // type will tell me which type of ContentFrag to create
        // ex. register, dailyviews, createworkout...
        ContentFrag frag = new ContentFrag();
        Bundle args = new Bundle();
        if(message != null){
            args.putString("message", message);
        }
        args.putString("type", type);
        frag.setArguments(args);
        return frag;
    }

    public String getType(){
        String ans = getArguments().getString("type");
        return ans;
    }
    public String getMessage(){
        String ans = getArguments().getString("message");
        return ans;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return viewCreator(inflater, container, getType());
    }

    // ------------ My helper methods ------------

    // public View viewCreator(LayoutInflater inflater, ViewGroup container, String type)
    // This method takes in the inflater, container, and type and then uses these to
    // create and return the correct view.
    public View viewCreator(LayoutInflater inflater, ViewGroup container, String type){
        switch(type){
            case "register":

                Log.e(TAG, "in register contentFrag");
                View view = inflater.inflate(R.layout.reg_content_frag, container, false);
                Button b = view.findViewById(R.id.Bregsubmit);
                b.setOnClickListener(regSubmitButtonClickListener);
                view.setId(R.id.reg_content_frag);
                return view;
            case "preworkout":
                View view2 = inflater.inflate(R.layout.preworkout_content_frag, container, false);
                // set Todays workout text view here once that part is done
                String[] date = getDate();
                TextView tvday = view2.findViewById(R.id.TV_pre_day);
                TextView tvdate = view2.findViewById(R.id.TV_pre_date);
                TextView tvquote = view2.findViewById(R.id.TV_pre_inspquote);
                if(getMessage() == "new user"){
                    // set tvquote to some message telling the new user to make a workout

                }
                TextView tvworkout = view2.findViewById(R.id.TV_pre_workout);
                tvday.setText(date[0]);
                tvdate.setText((date[1]));
                // add inspirational quote or something else
                // add reminder stuff
                view2.setId(R.id.preworkout_content_frag);
                return view2;
            case "create_program":
                View view3 = inflater.inflate(R.layout.create_workout_program_frag, container, false);
                Button submit = view3.findViewById(R.id.BN_cwpf_submit);
                EditText project_name = view3.findViewById(R.id.ET_cwpf_projectname);
                submit.setOnClickListener(submitProgramClickListener);
                return view3;
            case "day_frag":
                View view4 = inflater.inflate(R.layout.day_frag, container, false);
                Button add_routine = view4.findViewById(R.id.BN_df_addroutine);
                add_routine.setOnClickListener(addRoutineButtonClickListener);
                TextView day = view4.findViewById(R.id.TV_df_day);
                day.setText(getMessage());
                return view4;
            default:
                return null;
        }
    }


    // ---- comms methods ----

    public interface ContentFragComms{
        void regIsDone();
        void createProgramDone();
        DBHelper getDb();
        void addRoutine();
    }

    public void setContentFragComms(ContentFragComms comms){
        this.comms = comms;
    }

    // ---- end of comms methods ----


    // ---- register methods ----

    private void regSubmitButtonClicked(){
        Log.e(TAG, "leaving register contentFrag");
        // Getting the EditText objects holding the user input
        EditText etname = getActivity().findViewById(R.id.ETreginput_name);
        EditText etheight = getActivity().findViewById(R.id.ETreginput_height);
        EditText etcurr_weight = getActivity().findViewById(R.id.ETreginput_cw);
        EditText etgoal_weight = getActivity().findViewById(R.id.ETreginput_gw);
        // Converting input to required formats. (may need to fix)
        String name = etname.getText().toString();
        int height = Integer.parseInt(etheight.getText().toString());
        int curr_weight = Integer.parseInt(etcurr_weight.toString());
        int goal_weight = Integer.parseInt(etgoal_weight.toString());
        // Getting instance of db, and inserting user data.
        DBHelper db = comms.getDb();
        db.insertUser(name, height, goal_weight, curr_weight, "");
        comms.regIsDone();
    }

    // ---- end of register methods ----

    // ---- create program methods ----

    private void addRoutineButtonClicked(){
        this.comms.addRoutine();
    }

    private void submitProgramButtonClicked(){
        // figure out what to do when program is done
        this.comms.createProgramDone();
    }
    // ---- end of create program methods ----

    // ---- misc helper functions ----
    // This is a helper function that takes in zero parameters,
    // and returns the date in a string array.
    // input:
    // output: ans[0] = Mon-Sun
    // output: ans[1] = "month/day/year"
    private String[] getDate(){
        String ans[] = new String[2];
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        Integer dayofmonth = calendar.get(Calendar.DAY_OF_MONTH);
        Integer month = calendar.get(Calendar.MONTH);
        Integer year = calendar.get(Calendar.YEAR);
        if(day == 0){ ans[0] = "Sunday";}
        if(day == 1){ ans[0] = "Monday";}
        if(day == 2){ ans[0] = "Tuesday";}
        if(day == 3){ ans[0] = "Wednesday";}
        if(day == 4){ ans[0] = "Thursday";}
        if(day == 5){ ans[0] = "Friday";}
        if(day == 6){ ans[0] = "Saturday";}
        ans[1] = month.toString() + "/" + String.valueOf(dayofmonth) + "/" + year.toString();
        return ans;
    }

}// end of class
