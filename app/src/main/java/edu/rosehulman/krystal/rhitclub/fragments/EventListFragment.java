package edu.rosehulman.krystal.rhitclub.fragments;


import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import edu.rosehulman.krystal.rhitclub.MainActivity;
import edu.rosehulman.krystal.rhitclub.R;
import edu.rosehulman.krystal.rhitclub.utils.Club;
import edu.rosehulman.krystal.rhitclub.utils.Event;
import edu.rosehulman.krystal.rhitclub.utils.EventAdapter;

public class EventListFragment extends Fragment {

    private EventListFragment.OnEventSelectedListener mListener;
    private EventAdapter mAdapter;

    public EventListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new EventAdapter(mListener,getContext(),((MainActivity)getActivity()).getEvents());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_list, container, false);
        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.event_list_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(mAdapter);

        TextView eventadd = (TextView)view.findViewById(R.id.event_list_add);

        if(MainActivity.getUser().isOfficer()
                // TODO: Check if the officer try to hold event of his club.
                ){
            eventadd.setVisibility(View.VISIBLE);
            eventadd.setClickable(true);
            eventadd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showAddDialog();
                }
            });
        }else{
            eventadd.setVisibility(View.GONE);
            eventadd.setClickable(false);
        }
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle(R.string.event);
        if (context instanceof EventListFragment.OnEventSelectedListener) {
            mListener = (EventListFragment.OnEventSelectedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnEventSelectedListener {
        void onEventSelected(Event event);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle(R.string.event);
    }

    public void showAddDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(R.string.add_event);
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_event, null);
        builder.setView(view);
        final EditText eventName = (EditText) view.findViewById(R.id.dialog_add_event_name);
        final EditText eventHolder = (EditText) view.findViewById(R.id.dialog_add_event_holder);
        final EditText eventRoom = (EditText) view.findViewById(R.id.dialog_add_event_room);
        final EditText eventDes = (EditText) view.findViewById(R.id.dialog_add_event_des);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MainActivity main = (MainActivity)getActivity();
                Club holder = main.findClubByName(eventHolder.getText().toString());
                //if(holder!=null) {
                    Log.d("Holder!=null ",holder.getName());
                    Event e = new Event(eventName.getText().toString(), holder);
                    e.setRoom(eventRoom.getText().toString());
                    e.setDes(eventDes.getText().toString());
                    mAdapter.addNewEvent(e);
//                }else {
//                    Log.d("Holder is null ", "Opps");
//                }
            }
        });
        builder.setNegativeButton(android.R.string.cancel, null);
        builder.create().show();
    }
}
