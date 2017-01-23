package edu.rosehulman.krystal.rhitclub.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import edu.rosehulman.krystal.rhitclub.MainActivity;
import edu.rosehulman.krystal.rhitclub.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomePageFragment extends Fragment {
    private OnHomepageSelectedListener mListener;


    public HomePageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_page, container, false);
        LinearLayout club_button = (LinearLayout)view.findViewById(R.id.club_button);
        club_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onClubButtonSelected();
            }
        });
        LinearLayout event_button = (LinearLayout)view.findViewById(R.id.event_button);
        event_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onEventButtonSelected();
            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnHomepageSelectedListener) {
            mListener = (OnHomepageSelectedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnClubButtonSelectedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnHomepageSelectedListener{
        void onClubButtonSelected();
        void onEventButtonSelected();
    }


}
