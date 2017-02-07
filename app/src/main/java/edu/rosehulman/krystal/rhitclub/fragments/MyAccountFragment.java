package edu.rosehulman.krystal.rhitclub.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import edu.rosehulman.krystal.rhitclub.R;
import edu.rosehulman.krystal.rhitclub.utils.User;
import edu.rosehulman.krystal.rhitclub.utils.UserAdapter;

public class MyAccountFragment extends Fragment {
    private static final String ARG_USER = "user";

    private User mUser;
    private MyAccountFragment.OnMyAccountSelectedListener mListener;
    private ClubDetailFragment.OnFlingListener mSwipeListener;
    private UserAdapter mClubAdapter;
    private UserAdapter mEventAdapter;
    private UserAdapter mSubsAdapter;
    private GestureDetectorCompat mGest;

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
        mSubsAdapter = new UserAdapter(mListener,getContext(),true);
        mSubsAdapter.setmClubs(mUser.getmSubs());
        mGest = new GestureDetectorCompat(getContext(),new MyAccountFragment.MyGestureDetector());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_account, container, false);
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                mGest.onTouchEvent(motionEvent);
                return true;
            }
        });

        RecyclerView clubRecyclerView = (RecyclerView)view.findViewById(R.id.my_clubs_recycler);
        RecyclerView eventRecyclerView = (RecyclerView)view.findViewById(R.id.my_events_recycler);
        RecyclerView subsRecyclerView = (RecyclerView)view.findViewById(R.id.my_subs_recycler);

        clubRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        clubRecyclerView.setAdapter(mClubAdapter);

        eventRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        eventRecyclerView.setAdapter(mEventAdapter);

        subsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        subsRecyclerView.setAdapter(mSubsAdapter);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnMyAccountSelectedListener) {
            mListener = (OnMyAccountSelectedListener) context;
            mSwipeListener = (ClubDetailFragment.OnFlingListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        mSwipeListener = null;
    }

    public interface OnMyAccountSelectedListener {
        void OnMyAccountSelected(User user);
    }

    class MyGestureDetector extends GestureDetector.SimpleOnGestureListener{

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            mSwipeListener.onSwipe();
            return true;
        }
    }

}
