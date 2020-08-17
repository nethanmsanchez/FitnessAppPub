package com.example.fitnessapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class ScrollingFrag extends Fragment implements View.OnClickListener{
    // fields
    private ScrollingFragComms comms;
    private String title;
    private String[] buttons;




    public static ScrollingFrag newInstance(String title, String[] labels){
        ScrollingFrag scrollingFrag = new ScrollingFrag();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putStringArray("buttons", labels);
        scrollingFrag.setArguments(args);
        return scrollingFrag;
    }

    public String getTitle(){
        this.title = this.getArguments().getString("title");
        return title;
    }
    public String[] getButtons(){
        this.buttons = getArguments().getStringArray("buttons");
        return buttons;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.scrolling_frag, container, false);
        String[] buttons = getButtons();
        TextView tv = view.findViewById(R.id.TV_sf_title);
        tv.setText(getTitle());
        ScrollView scrollView = view.findViewById(R.id.SV_sf_container);
        LinearLayout ll = new LinearLayout(view.getContext());
        for (int i = 0; i < buttons.length; i++) {
            Button b = new Button(view.getContext());
            b.setText(buttons[i]);
            b.setTag(i);
            ll.addView(b);
        }
        scrollView.addView(ll);

        return view;
    }



    // Methods

    // not sure if I understand how this is supposed to work, may need to set
    // OnClickListeners to each button and define differently
    @Override
    public void onClick(View view){
        switch (view.getId()){
            default:
                for(int i = 0; i < buttons.length; i++){
                    if(view.getTag() == i){
                        comms.buttonClicked(buttons[i], this.title);
                    }
                }
        }
    }

    public interface ScrollingFragComms{
        void buttonClicked(String name, String message);
    }

    public void setScrollingFragComms(ScrollingFragComms scrollingFragComms){
        this.comms = scrollingFragComms;
    }

}
