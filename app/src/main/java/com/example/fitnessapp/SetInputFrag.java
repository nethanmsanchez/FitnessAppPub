package com.example.fitnessapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;

public class SetInputFrag extends Fragment {
    // Fields
    private int size;
    private String left_label;
    private String right_label;

    public static SetInputFrag newInstance(int size, String left_label, String right_label) {
        Bundle args = new Bundle();
        args.putInt("size", size);
        args.putString("left_label", left_label);
        args.putString("right_label", right_label);
        SetInputFrag fragment = new SetInputFrag();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View OnCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.set_input_frag, );
    }



    // Field methods
    public int getSize(){
        return this.size;
    }
    public void setSize(int size){
        this.size = size;
    }
    public String getLeft_label(){
        return this.left_label;
    }
    public void setLeft_label(String left_label){
        this.left_label = left_label;
    }
    public String getRight_label(){
        return this.right_label;
    }
    public void setRight_label(String right_label){
        this.right_label = right_label;
    }
}
