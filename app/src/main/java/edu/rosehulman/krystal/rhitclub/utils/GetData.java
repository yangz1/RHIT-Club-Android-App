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

public class GetData extends AsyncTask<String, Void, HashMap<String,String>  > {
    private OkConsumer mOk;

    public GetData(OkConsumer activity){
        this.mOk = activity;
    }

    @Override
    protected HashMap<String,String> doInBackground(String... urlStrings) {
        String urlString = urlStrings[0];
        HashMap<String,String>  map = new HashMap<>();
        Log.d("TAG",urlString);
        String jsonString="";
        try {
//            jsonString = new ObjectMapper().writeValueAsString(new URL(urlString));
            map = new ObjectMapper().readValue(new URL(urlString), HashMap.class);
            Log.d("kk loaded:",jsonString+"!");
        }catch (IOException e){
            Log.d("Tag","Error: cannot mapper");
        }
        return map;
    }

    @Override
    protected void onPostExecute(HashMap<String,String>   kk) {
        super.onPostExecute(kk);
        mOk.onOkLoaded(kk);
    }

    public interface OkConsumer{
        public void onOkLoaded(HashMap<String,String>   kk);
    }
}
