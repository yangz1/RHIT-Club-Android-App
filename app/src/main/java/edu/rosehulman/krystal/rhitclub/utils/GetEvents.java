package edu.rosehulman.krystal.rhitclub.utils;

import android.os.AsyncTask;
import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

/**
 * Created by KrystalYang on 2/15/17.
 */

public class GetEvents extends AsyncTask<String, Void, HashMap<String,Event>  > {
    private GetEvents.EventsConsumer mUser;

    public GetEvents(GetEvents.EventsConsumer activity){
        this.mUser = activity;
    }

    @Override
    protected HashMap<String,Event> doInBackground(String... urlStrings) {
        String urlString = urlStrings[0];

        //User  user = null;
        HashMap<String,Event> map = new HashMap<>();
        Log.d("TAG",urlString);


        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");
            //conn.setDoOutput(true);

//            String body = "{\"token\":"+ "\""+MainActivity.token+"\"}";
//            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
//            wr.write(body.getBytes("UTF-8"));

            ObjectMapper mapper = new ObjectMapper();
//            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());

//            Log.d("JSON: ",body);
//            byte[] outputInBytes = body.getBytes("UTF-8");
//            wr.write(outputInBytes);
//            wr.flush();
//            wr.close();

            int responseCode = conn.getResponseCode();
            Log.d("Response Message",conn.getResponseMessage());
            Log.d("ResponseCode",""+responseCode);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            String result = response.toString();
            Log.d("result",result);
            int i =0;
            int j = 0;
            for(int k =0;k<result.length();k++){
                if(result.charAt(k) == '['){
                    i = k;
                }
                if(result.charAt(k) == ']'){
                    j = k;
                }
            }
                result = result.substring(i, j + 1);

                JSONArray jsonarray = null;
                try {
                    jsonarray = new JSONArray(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                for (int t = 0; t < jsonarray.length(); t++) {
                    JSONObject jsonobject = null;
                    Event event = new Event();
                    try {
                        jsonobject = jsonarray.getJSONObject(t);
                        String club_name = jsonobject.getString("event_id");
                        String club_type = jsonobject.getString("event_title");
                        String club_description = jsonobject.getString("event_description");
                        event.setName(club_type);
                        event.setDes(club_description);
                        map.put(club_name,event);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            in.close();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            Log.d("Cannot Map",e.getMessage());
            e.printStackTrace();
        }
        return map;
    }

    @Override
    protected void onPostExecute(HashMap<String,Event>   user) {
        super.onPostExecute(user);
        mUser.onEventsLoaded(user);
    }

    public interface EventsConsumer{
        public void onEventsLoaded(HashMap<String,Event>   user);
    }
}
