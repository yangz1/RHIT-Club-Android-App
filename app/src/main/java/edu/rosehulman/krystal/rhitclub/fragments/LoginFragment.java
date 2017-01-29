package edu.rosehulman.krystal.rhitclub.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import edu.rosehulman.krystal.rhitclub.MainActivity;
import edu.rosehulman.krystal.rhitclub.R;
import edu.rosehulman.krystal.rhitclub.utils.GetData;
import edu.rosehulman.krystal.rhitclub.utils.ok;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements GetData.OkConsumer{

    private OnStartPressedListener mListener;
    private HashMap<String,String>  mOk;
    private EditText username;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String urlStringOK = String.format(Locale.US,"https://club-app.csse.rose-hulman.edu:3000");
        (new GetData(this)).execute(urlStringOK);
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
        username = (EditText)view.findViewById(R.id.username_text);
        Button registerButton = (Button)view.findViewById(R.id.register_button);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onStartPressed();
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnStartPressedListener) {
            mListener = (OnStartPressedListener) context;
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

    @Override
    public void onOkLoaded(HashMap<String,String>  kk) {
        Log.d("OK","OK Loaded");
        this.mOk = kk;
        username.setText(mOk.toString());
    }


    public interface OnStartPressedListener {
        public void onStartPressed();
    }
}
