package edu.rosehulman.krystal.rhitclub.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import edu.rosehulman.krystal.rhitclub.MainActivity;
import edu.rosehulman.krystal.rhitclub.R;
import edu.rosehulman.krystal.rhitclub.utils.Club;
import edu.rosehulman.krystal.rhitclub.utils.Event;

public class EventDetailFragment extends Fragment {
    private static final String ARG_EVENT = "event";

    private Event mEvent;
    private ClubDetailFragment.OnFlingListener mListener;
    private GestureDetectorCompat mGest;

    private TextView mEventName;
    private TextView mEventHolder;
    private TextView mEventOfficer;
    private TextView mEventDes;
    private TextView mEventRoom;

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
        mEventName = (TextView) view.findViewById(R.id.event_name);
        mEventHolder = (TextView) view.findViewById(R.id.event_holder);
        mEventOfficer = (TextView) view.findViewById(R.id.event_officer);
        ImageView eventImg = (ImageView) view.findViewById(R.id.event_detail_image);
        TextView eventedit = (TextView)view.findViewById(R.id.event_edit);

        mEventDes = (TextView) view.findViewById(R.id.event_des);
        mEventRoom = (TextView) view.findViewById(R.id.event_room);

        mEventName.setText(mEvent.getName());
        mEventHolder.setText(mEvent.getHolder());
        //mEventOfficer.setText(mEvent.getHolder().getOfficer()+": "+mEvent.getHolder().getOfficerEmail());
        mEventDes.setText(mEvent.getDes());
        mEventRoom.setText(mEvent.getRoom());

        if(MainActivity.getUser().isOfficer()
            // TODO: If the officer hold this event
                ){
                eventedit.setVisibility(View.VISIBLE);
                eventedit.setClickable(true);
                eventedit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showEditDialog();
                    }
                });

        }else{
            eventedit.setVisibility(View.GONE);
            eventedit.setClickable(false);
        }

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

    public void showEditDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(R.string.edit_event);
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_event, null);
        builder.setView(view);
        final EditText eventName = (EditText) view.findViewById(R.id.dialog_add_event_name);
        final EditText eventHolder = (EditText) view.findViewById(R.id.dialog_add_event_holder);
        final EditText eventRoom = (EditText) view.findViewById(R.id.dialog_add_event_room);
        final EditText eventDes = (EditText) view.findViewById(R.id.dialog_add_event_des);
        eventName.setText(mEvent.getName());
        eventHolder.setText(mEvent.getHolder());
        eventRoom.setText(mEvent.getRoom());
        eventDes.setText(mEvent.getDes());

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MainActivity main = (MainActivity)getActivity();
                //Club holder = main.findClubByName(eventHolder.getText().toString());
                //if(holder!=null) {
                    mEvent.setName(eventName.getText().toString());
                    mEvent.setHolder(eventHolder.getText().toString());
                    mEvent.setDes(eventDes.getText().toString());
                    mEvent.setRoom(eventRoom.getText().toString());
                    mEventName.setText(mEvent.getName());
                    mEventHolder.setText(eventHolder.getText().toString());
                    //mEventOfficer.setText(holder.getOfficer());
                    mEventDes.setText(mEvent.getDes());
                    mEventRoom.setText(mEvent.getRoom());
                //}
                Log.d("Holder is null ","Opps");
            }
        });
        builder.setNegativeButton(android.R.string.cancel, null);
        builder.create().show();
    }
}
