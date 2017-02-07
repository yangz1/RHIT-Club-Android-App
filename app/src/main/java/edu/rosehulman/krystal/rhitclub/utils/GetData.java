package edu.rosehulman.krystal.rhitclub.utils;

import android.os.AsyncTask;
import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by KrystalYang on 1/29/17.
 */

public class GetData extends AsyncTask<String, Void, User  > {
    private UserConsumer mUser;

    public GetData(UserConsumer activity){
        this.mUser = activity;
    }

    @Override
    protected User doInBackground(String... urlStrings) {
        String urlString = urlStrings[0];
        User  user = null;
        Log.d("TAG",urlString);
        try {
            user = new ObjectMapper().readValue(new URL(urlString), User.class);
        }catch (IOException e){
            Log.d("Tag","Error: cannot mapper");
        }
        return user;
    }

    @Override
    protected void onPostExecute(User   user) {
        super.onPostExecute(user);
        mUser.onUserLoaded(user);
    }

    public interface UserConsumer{
        public void onUserLoaded(User   user);
    }
}
