package edu.rosehulman.krystal.rhitclub.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import edu.rosehulman.krystal.rhitclub.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment{

    private OnStartPressedListener mListener;
    private OnLoginListener mLoginListener;
    private boolean mLoggingIn;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLoggingIn = false;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle(R.string.app_name);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_login, container, false);

        //if(savedInstanceState == null){
        Button signInButton = (Button)view.findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginWithRosefire();
            }
        });
        return view;
    }

    private void loginWithRosefire(){
        if(mLoggingIn){
            return;
        }
        mLoggingIn = true;
        mLoginListener.onRosefireLogin();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle(R.string.app_name);
        if (context instanceof OnStartPressedListener) {
            mListener = (OnStartPressedListener) context;
            mLoginListener = (OnLoginListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnStartPressedListener");
        }
    }

    @Override
    public void onDetach(){
        super.onDetach();
        mListener = null;
    }


    public interface OnStartPressedListener {
        public void onStartPressed();
    }

    public interface OnLoginListener {
        void onRosefireLogin();
    }

    public void onLoginError(String message) {
        new AlertDialog.Builder(getActivity())
                .setTitle(getActivity().getString(R.string.login_error))
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, null)
                .create()
                .show();
        mLoggingIn = false;
    }

    public boolean getLogin(){
        return mLoggingIn;
    }

    public OnStartPressedListener getmListener(){
        return mListener;
    }

}
