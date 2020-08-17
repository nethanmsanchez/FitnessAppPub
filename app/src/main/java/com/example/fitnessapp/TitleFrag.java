package com.example.fitnessapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class TitleFrag extends Fragment implements View.OnClickListener {
    // ---- fields ----
    private TitleFragComms comms;
    private boolean isAlt;
    private int length;


    // we need to do this, because a fragment's
    // default constructor shouldn't be altered.
    public static TitleFrag newInstance(String[] names){
        TitleFrag frag = new TitleFrag();

        Bundle args = new Bundle();
        for(int i = 0; i < names.length; i++){
            args.putStringArray("names", names);
        }
        frag.setArguments(args);
        frag.isAlt = false;
        return frag;
    }

    public static TitleFrag newAltInstance(String[] names){
        TitleFrag frag = new TitleFrag();
        Bundle args = new Bundle();
        for(int i = 0; i < names.length; i++){
            args.putStringArray("names", names);
        }
        frag.setArguments(args);
        frag.isAlt = true;
        return frag;
    }

    // used to get the buttons passed in as
    // a bundle in the newInstance() method.
    public Button[] buttons(){
        String[] names = getArguments().getStringArray("names");
        if(names == null){
            return null;
        }
        Button[] b = new Button[names.length];
        for(int i = 0; i < names.length; i++){
            b[i] = new Button(getContext());
            b[i].setText(names[i]);
            b[i].setId(i);
        }
        return b;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = null;
        if (this.isAlt == true){
            view = inflater.inflate(R.layout.alt_title_frag, container, false);
            Button addDay = view.findViewById(R.id.BN_atf_addbutton);
            Button delDay = view.findViewById(R.id.BN_atf_delday);
            addDay.setOnClickListener(this);
            delDay.setOnClickListener(this);
        } else {
            // first get the Title fragment layout
            view = inflater.inflate(R.layout.title_frag, container, false);
            TextView tv = view.findViewById(R.id.logo);
            tv.setText("logo");
        }

        // This is the LinearLayout that the Button objects
        // are in.
        LinearLayout buttons = new LinearLayout(getContext());
        buttons.setOrientation(LinearLayout.HORIZONTAL);
        // This adds the buttons to the LinearLayout
        Button[] b = buttons();
        if ( b == null ){
            return view;
        }
        this.length = b.length;
        for(int i = 0; i < b.length; i++){
            b[i].setTag(i);
            b[i].setOnClickListener(this);
            buttons.addView(b[i]);
        }
        // This is creating the HorizontalScrollView which
        // holds one child View object.
        if(this.isAlt == true){
            HorizontalScrollView hsv = view.findViewById(R.id.HSV_atf_buttons);
            hsv.addView(buttons);
        } else {
            HorizontalScrollView horizontalScrollView = view.findViewById(R.id.HSView);
            horizontalScrollView.addView(buttons);
        }
        return view;
    }

    // ---- misc. methods ----

    // onClickListener used to control button presses,
    // in this case the buttons in the title fragment
    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.BN_atf_addbutton:
                comms.addDay();
                break;
            case R.id.BN_atf_delday:
                comms.delDay();
                break;
            default:
                for(int i = 0; i< this.length; i++){
                    if(v.getTag() == i){
                        comms.showDay(i);
                    }
                }
        }
    }

    // ---- end of misc. methods ----

    // ---- comms methods ----

    public interface TitleFragComms{
        void addDay();
        void delDay();
        void showDay(int day);
    }

    public void setTitleFragComms(TitleFragComms comms){
        this.comms = comms;
    }

    // ---- end of comms methods ----
}// end of TitleFrag
