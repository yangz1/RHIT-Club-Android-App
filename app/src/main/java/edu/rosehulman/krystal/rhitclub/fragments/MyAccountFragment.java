package edu.rosehulman.krystal.rhitclub.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import edu.rosehulman.krystal.rhitclub.R;
import edu.rosehulman.krystal.rhitclub.utils.Event;
import edu.rosehulman.krystal.rhitclub.utils.EventAdapter;
import edu.rosehulman.krystal.rhitclub.utils.User;
import edu.rosehulman.krystal.rhitclub.utils.UserAdapter;

public class MyAccountFragment extends Fragment {
    private static final String ARG_USER = "user";

    private User mUser;
    private MyAccountFragment.OnMyAccountSelectedListener mListener;
    private UserAdapter mClubAdapter;
    private UserAdapter mEventAdapter;

    public MyAccountFragment() {
        // Required empty public constructor
    }

    public static MyAccountFragment newInstance(User user) {
        MyAccountFragment fragment = new MyAccountFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_USER, user);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUser = getArguments().getParcelable(ARG_USER);
        }
        mClubAdapter = new UserAdapter(mListener,getContext(),true);
        mClubAdapter.setmClubs(mUser.getmClubs());
        mEventAdapter = new UserAdapter(mListener,getContext(),false);
        mEventAdapter.setmEvents(mUser.getmEvents());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_account, container, false);
        RecyclerView clubRecyclerView = (RecyclerView)view.findViewById(R.id.my_clubs_recycler);
        RecyclerView eventRecyclerView = (RecyclerView)view.findViewById(R.id.my_events_recycler);
        clubRecyclerView.setHasFixedSize(true);
        clubRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        clubRecyclerView.setAdapter(mClubAdapter);

        eventRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        eventRecyclerView.setAdapter(mEventAdapter);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnMyAccountSelectedListener) {
            mListener = (OnMyAccountSelectedListener) context;
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

    public interface OnMyAccountSelectedListener {
        void OnMyAccountSelected(User user);
    }

}
