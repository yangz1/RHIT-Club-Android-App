package edu.rosehulman.krystal.rhitclub.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import edu.rosehulman.krystal.rhitclub.R;
import edu.rosehulman.krystal.rhitclub.utils.Club;

public class ClubDetailFragment extends Fragment {
    private static final String ARG_CLUB = "club";

    private Club mClub;
    private OnFlingListener mListener;
    private GestureDetectorCompat mGest;

    public ClubDetailFragment() {
        // Required empty public constructor
    }

    public static ClubDetailFragment newInstance(Club club) {
        ClubDetailFragment fragment = new ClubDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_CLUB, club);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mClub = getArguments().getParcelable(ARG_CLUB);
        }
        mGest = new GestureDetectorCompat(getContext(),new MyGestureDetector());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle(mClub.getName());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_club_detail, container, false);
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                mGest.onTouchEvent(motionEvent);
                return true;
            }
        });
        ImageView imageView = (ImageView) view.findViewById(R.id.club_detail_image);
        imageView.setImageResource(mClub.getImage());
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFlingListener) {
            mListener = (OnFlingListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFlingListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFlingListener {
        void onSwipe();
    }


    class MyGestureDetector extends GestureDetector.SimpleOnGestureListener{

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            mListener.onSwipe();
            return true;
        }
    }
}
