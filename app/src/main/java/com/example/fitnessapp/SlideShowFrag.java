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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class SlideShowFrag extends Fragment implements View.OnClickListener {
    private ArrayList<String> prompts;
    int curr;
    private SlideShowComms slideShowComms;
    private ArrayList<String> answers;

    private static final String TAG = "SlideShowFrag.java";

    public static SlideShowFrag newInstance(ArrayList<String> prompts, int curr, ArrayList<String> answers){
        Log.e(TAG, "newInstance being created");
        SlideShowFrag slideShowFrag = new SlideShowFrag();
        Bundle args = new Bundle();
        args.putStringArray("prompts", (String[]) prompts.toArray());
        args.putInt("current_prompt", curr);
        args.putStringArray("answers", (String[]) answers.toArray());
        slideShowFrag.setArguments(args);
        return slideShowFrag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        // converting arrays into ArrayLists
        ArrayList<String> pro = new ArrayList<>();
        ArrayList<String> ans = new ArrayList<>();
        Collections.addAll(pro, getPrompts());
        Collections.addAll(ans, getAnswers());

        // set fields
        this.prompts = pro;
        this.curr = getCurr();
        this.answers = ans;

        // set up frag UI
        View view = inflater.inflate(R.layout.slide_show_frag, container, false);
        TextView prompt = view.findViewById(R.id.TV_ssf_prompt);
        if(this.curr >= this.prompts.size()){
            prompt.setText(this.prompts.get(this.prompts.size()-1)); // if more input that prompts, ask the last question until done
        } else {
            prompt.setText(this.prompts.get(this.curr));
        }
        Button left_button = view.findViewById(R.id.BN_ssf_leftbutton);
        Button right_button = view.findViewById(R.id.BN_ssf_rightbutton);
        Button middle_button = view.findViewById(R.id.BN_ssf_middlebutton);
        if(this.answers.get(this.curr) != "") {
            EditText editText = view.findViewById(R.id.ET_ssf_input);
            editText.setText(getAnswers()[getCurr()]);
        }
        middle_button.setOnClickListener(this);
        left_button.setOnClickListener(this);
        right_button.setOnClickListener(this);
        return view;
    }

    public String[] getPrompts(){
        return getArguments().getStringArray("prompts");
    }
    public int getCurr(){
        return getArguments().getInt("current_prompt");
    }

    public String[] getAnswers(){
        return getArguments().getStringArray("answers");
    }

    public void onClick(View view){
        EditText exercise_name = getActivity().findViewById(R.id.ET_ssf_input);
        DBHelper db;
        switch (view.getId()){
            // back button
            case R.id.BN_ssf_leftbutton:
                this.slideShowComms.leftButtonClicked(this.prompts, curr, this.answers);
                //when going back if text was entered, save it
                String temp = exercise_name.getText().toString();
                if (temp != "" && temp != null) {
                    this.answers.add(temp);
                }
                break;
            // done button
            case R.id.BN_ssf_rightbutton:
                // when done button is pressed add newest exercise and then create and save routine
                db = this.slideShowComms.getDbSsf();
                this.answers.add(exercise_name.getText().toString());
                this.slideShowComms.rightButtonClicked(this.answers, curr);
                break;
            // forward button
            case R.id.BN_ssf_middlebutton:
                // add the newest exercise to ArrayList answers, then get the next one
                    exercise_name = getActivity().findViewById(R.id.ET_ssf_input);
                    this.answers.add(exercise_name.getText().toString());
                    this.slideShowComms.middleButtonClicked(this.prompts, curr, this.answers);
                //}
                break;
            default:
        }
    }

    public void setSlideShowComms(SlideShowComms slideShowComms){
        this.slideShowComms = slideShowComms;
    }

    public interface SlideShowComms{
        void leftButtonClicked(ArrayList<String> prompts, int curr, ArrayList<String> ans);
        void rightButtonClicked(ArrayList<String> ans, int curr);
        void middleButtonClicked(ArrayList<String> prompts, int curr, ArrayList<String> ans);
        DBHelper getDbSsf();
    }
}
