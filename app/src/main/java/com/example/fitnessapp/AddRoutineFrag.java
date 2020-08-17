package com.example.fitnessapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import java.util.ArrayList;

public class AddRoutineFrag extends Fragment implements View.OnClickListener {
    // Fields
    private int curr_set;
    private int curr_exercise;
    private ArrayList<String> routine_info; // [0] = routine name
    private AddRoutineFragComms comms;

    private static final String TAG = "AddRoutineFrag.java";

    // newInstance is used here because you are not allowed to pass parameters into a fragment.
    // So this is basically the fragment constructor
    public static AddRoutineFrag newInstance(int curr_exercise, int curr_set, ArrayList<String> routine_info) {
        Bundle args = new Bundle();
        args.putInt("current_exercise", curr_exercise);
        args.putInt("current_set", curr_set);
        args.putStringArrayList("routine_info", routine_info);
        AddRoutineFrag fragment = new AddRoutineFrag();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        // set fields
        this.curr_set = getCurr_set();
        this.curr_exercise = getCurr_exercise();
        this.routine_info = getRoutine_info();

        // message for debugging
        Log.e(TAG, "onCreateView curr_set = " + curr_set + " curr_ex = " + curr_exercise);

        View view;
        Button back;
        Button new_set;
        Button new_ex;
        Button done;

        // These conditionals determine tell which button was pressed, and what needs to be displayed
        if(curr_exercise == 0 && curr_set == 0){ // (this means the first fragment view that asks
            // ui initialization code               for the Routine's name should be displayed)
            view = inflater.inflate(R.layout.add_routine_frag, container, false);
            TextView current_set = view.findViewById(R.id.TV_arf_currset);
            current_set.setText("Set 1");
            // buttons code
            back = view.findViewById(R.id.BN_arf_back);
            new_set = view.findViewById(R.id.BN_arf_newset);
            new_ex = view.findViewById(R.id.BN_arf_newexercise);
            done = view.findViewById(R.id.BN_arf_done);
            back.setOnClickListener(this);
            new_set.setOnClickListener(this);
            new_ex.setOnClickListener(this);
            done.setOnClickListener(this);
        }else if(curr_exercise != 0 && curr_set == 0){// (this means new exercise was pressed)
            // ui init code
            view = inflater.inflate(R.layout.new_exercise_frag, container, false);
            TextView set1 = view.findViewById(R.id.TV_nef_currset);
            set1.setText("Set 1");
            // buttons code
            back = view.findViewById(R.id.BN_nef_back);
            new_set = view.findViewById(R.id.BN_nef_newset);
            new_ex = view.findViewById(R.id.BN_nef_newexercise);
            done = view.findViewById(R.id.BN_nef_done);
            back.setOnClickListener(this);
            new_set.setOnClickListener(this);
            new_ex.setOnClickListener(this);
            done.setOnClickListener(this);
        }else if(curr_set != 0){// (this means new set was pressed)
            // ui initialization code
            view = inflater.inflate(R.layout.new_set_frag, container, false);
            TextView exercise_name = view.findViewById(R.id.TV_nsf_exercisename);
            exercise_name.setText(routine_info.get(0));
            TextView curr_set = view.findViewById(R.id.TV_nsf_currset);
            curr_set.setText(this.curr_set);
            // buttons code
            back = view.findViewById(R.id.BN_nsf_back);
            new_set = view.findViewById(R.id.BN_nsf_newset);
            new_ex = view.findViewById(R.id.BN_nsf_newexercise);
            done = view.findViewById(R.id.BN_nsf_done);
            back.setOnClickListener(this);
            new_set.setOnClickListener(this);
            new_ex.setOnClickListener(this);
            done.setOnClickListener(this);
        } else{// (should never reach this else)
            view = null;
        }
        return view;
    }

    // onClickListener code
    @Override
    public void onClick(View view){
        EditText ex_name;
        EditText reps;
        EditText weight;
        EditText units;
        switch( view.getId() ) {
            case R.id.BN_arf_back:// when the back button is pressed from the initial addRoutineFrag
                this.comms.cancelAddRoutine();
                break;
            case R.id.BN_arf_newset://      all three of these cases need the same behavior,
            case R.id.BN_arf_newexercise:// and are reached when any button from the ARF are
            case R.id.BN_arf_done:    //    pressed.
                EditText routine_name = view.findViewById(R.id.ET_arf_routinename);
                ex_name = view.findViewById(R.id.ET_arf_exercisename);
                reps = view.findViewById(R.id.ET_arf_repsinput);
                weight = view.findViewById(R.id.ET_arf_weightinput);
                units = view.findViewById(R.id.ET_arf_units);
                this.comms.addRoutineInitDone(view.getId(), this.curr_exercise, this.curr_set,
                        routine_name.getText().toString(), ex_name.getText().toString(),
                        reps.getText().toString(), weight.getText().toString(), units.getText().toString());
                break;
            case R.id.BN_nsf_back://        These cases are reached when any button is pressed from
            case R.id.BN_nsf_newexercise:// the new set view of ARF.
            case R.id.BN_nsf_newset:
            case R.id.BN_nsf_done:
                reps = view.findViewById(R.id.ET_nsf_repsinput);
                weight = view.findViewById(R.id.ET_nsf_weightinput);
                this.comms.addRoutineNewSetDone(view.getId(), curr_exercise, curr_set,
                        reps.getText().toString(), weight.getText().toString());
                break;
            case R.id.BN_nef_back://        These cases are reached when any button is pressed from
            case R.id.BN_nef_done://        the new exercise frag
            case R.id.BN_nef_newexercise:
            case R.id.BN_nef_newset:
                ex_name = view.findViewById(R.id.ET_nef_exercisename);
                reps = view.findViewById(R.id.ET_nef_repsinput);
                weight = view.findViewById(R.id.ET_nef_weightinput);
                units = view.findViewById(R.id.ET_nef_units);
                this.comms.addRoutineNewExDone(view.getId(), curr_exercise, curr_set, reps.getText().toString(),
                        ex_name.getText().toString(), weight.getText().toString(), units.getText().toString());
            default:
        }
    }

    // interface code
    public interface AddRoutineFragComms{
        void cancelAddRoutine();
        void addRoutineInitDone(int id, int curr_ex, int curr_set, String routine_name,
                                String ex_name, String reps, String weight, String units);
        void addRoutineNewSetDone(int id, int curr_ex, int curr_set, String reps, String weight);
        void addRoutineNewExDone(int id, int curr_ex, int curr_set, String ex_name, String reps, String weight, String units);
    }

    public void setAddRoutineFragComms(AddRoutineFragComms comms){
        this.comms = comms;
    }

    // get data from bundle to initialize fields
    public int getCurr_set(){
        return getArguments().getInt("curr_set");
    }
    public int getCurr_exercise(){
        return getArguments().getInt("curr_exercise");
    }
    public ArrayList<String> getRoutine_info(){
        return getArguments().getStringArrayList("routine_infor");
    }
}
