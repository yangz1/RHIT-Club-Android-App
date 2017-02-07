package edu.rosehulman.krystal.rhitclub.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

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
        TextView clubName = (TextView) view.findViewById(R.id.club_name);
        TextView clubDescription = (TextView) view.findViewById(R.id.club_description);
        TextView clubOfficer = (TextView) view.findViewById(R.id.club_officer);
        ImageView imageView = (ImageView) view.findViewById(R.id.club_detail_image);

        clubName.setText(mClub.getName());
        clubDescription.setText(mClub.getDescription());
        clubOfficer.setText(mClub.getOfficer());
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

    private void showEditDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(R.string.edit);
        View view = getActivity().getLayoutInflater().inflate(R.layout.edit_dialog,null,false);
        builder.setView(view);
//        final EditText titleEdit = (EditText)view.findViewById(R.id.addon_title);
//        final EditText contentEdit = (EditText) view.findViewById(R.id.addon_content);
//        if (item!=null){
//            titleEdit.setText(item.getTitle());
//            contentEdit.setText(item.getContent());
//        }
//
//
//        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                if (item!=null){
//                    if (titleEdit.getText().toString().length()==0 || contentEdit.getText().toString().length()==0){
//                        Item newitem = GetRandomString.getRandomItem();
//                        myAdapter.update(item,newitem.getTitle(),newitem.getContent());
//                    }else
//                        myAdapter.update(item,titleEdit.getText().toString(),contentEdit.getText().toString());
//                }
//                else {
//                    Item item=null;
//                    if (titleEdit.getText().toString().length()==0 || contentEdit.getText().toString().length()==0){
//                        item =GetRandomString.getRandomItem();}
//                    else
//                        item = new Item(titleEdit.getText().toString(), contentEdit.getText().toString());
//                    item.setUid(user.getUid());
//                    myAdapter.add(item);
//                }
//
//            }
//        });
//        builder.setNegativeButton(android.R.string.cancel, null);
//        if (item!=null) builder.setNeutralButton(R.string.dialog_del, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                myAdapter.remove(item);
//
//            }
//        });

        builder.create().show();

    }


}
