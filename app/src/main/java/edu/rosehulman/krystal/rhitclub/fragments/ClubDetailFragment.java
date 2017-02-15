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

public class ClubDetailFragment extends Fragment {
    private static final String ARG_CLUB = "club";

    private Club mClub;
    private OnFlingListener mListener;
    private GestureDetectorCompat mGest;

    private TextView mClubName;
    private TextView mClubDes;
    private TextView mClubOff;
    private TextView mClubemail;

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
        mClubName = (TextView) view.findViewById(R.id.club_name);
        mClubDes = (TextView) view.findViewById(R.id.club_description);
        mClubOff = (TextView) view.findViewById(R.id.club_officer);
        mClubemail = (TextView) view.findViewById(R.id.club_officeremail);
        ImageView imageView = (ImageView) view.findViewById(R.id.club_detail_image);
        TextView clubEdit = (TextView)view.findViewById(R.id.club_edit);

        mClubName.setText(mClub.getName());
        mClubDes.setText(mClub.getDescription());
        mClubOff.setText(mClub.getOfficer());
        mClubemail.setText(mClub.getOfficerEmail());
        imageView.setImageResource(mClub.getImage());
        if(MainActivity.getUser().isOfficer()
            // TODO: If the officer is an Officer of this club
            ){
            if(mClub.getName().equals("ISA")){
                clubEdit.setVisibility(View.VISIBLE);
                clubEdit.setClickable(true);
                clubEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showEditDialog();
                    }
                });
            }
        }else{
            clubEdit.setVisibility(View.GONE);
            clubEdit.setClickable(false);
        }
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

    public void showEditDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(R.string.edit_club);
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_club, null);
        builder.setView(view);
        final TextView clubName = (TextView) view.findViewById(R.id.dialog_add_club_name);
        final EditText clubDes = (EditText) view.findViewById(R.id.dialog_add_club_des);
        final EditText clubOfficer = (EditText) view.findViewById(R.id.dialog_add_club_officer);
        final EditText clubOfficerEmail = (EditText) view.findViewById(R.id.dialog_add_club_officer_email);
        clubName.setText(mClub.getName());
        clubDes.setText(mClub.getDescription());
        clubOfficer.setText(mClub.getOfficer());
        clubOfficerEmail.setText(mClub.getOfficerEmail());

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mClub.setDescription(clubDes.getText().toString());
                mClub.setOfficer(clubOfficer.getText().toString());
                mClub.setOfficerEmail(clubOfficerEmail.getText().toString());
                mClubDes.setText(mClub.getDescription());
                mClubOff.setText(mClub.getOfficer()+": "+mClub.getOfficerEmail());

            }
        });
        builder.setNegativeButton(android.R.string.cancel, null);
        builder.create().show();
    }


}
