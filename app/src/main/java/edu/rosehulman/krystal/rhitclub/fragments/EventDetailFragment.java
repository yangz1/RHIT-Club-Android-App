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
import android.widget.TextView;

import edu.rosehulman.krystal.rhitclub.R;
import edu.rosehulman.krystal.rhitclub.utils.Club;
import edu.rosehulman.krystal.rhitclub.utils.Event;

public class EventDetailFragment extends Fragment {
    private static final String ARG_EVENT = "event";

    private Event mEvent;
    private ClubDetailFragment.OnFlingListener mListener;
    private GestureDetectorCompat mGest;

    public EventDetailFragment() {
        // Required empty public constructor
    }

    public static EventDetailFragment newInstance(Event event) {
        EventDetailFragment fragment = new EventDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_EVENT, event);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mEvent = getArguments().getParcelable(ARG_EVENT);
        }
        mGest = new GestureDetectorCompat(getContext(),new EventDetailFragment.MyGestureDetector());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle(mEvent.getName());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_detail, container, false);
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                mGest.onTouchEvent(motionEvent);
                return true;
            }
        });
        TextView eventName = (TextView) view.findViewById(R.id.event_name);
        TextView eventHolder = (TextView) view.findViewById(R.id.event_holder);
        TextView eventOfficer = (TextView) view.findViewById(R.id.event_officer);
        ImageView eventImg = (ImageView) view.findViewById(R.id.event_detail_image);

        eventName.setText(mEvent.getName());
        eventHolder.setText(mEvent.getHolder().getName());
        eventOfficer.setText(mEvent.getHolder().getOfficer());

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ClubDetailFragment.OnFlingListener) {
            mListener = (ClubDetailFragment.OnFlingListener) context;
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


    class MyGestureDetector extends GestureDetector.SimpleOnGestureListener{

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            mListener.onSwipe();
            return true;
        }
    }
}
