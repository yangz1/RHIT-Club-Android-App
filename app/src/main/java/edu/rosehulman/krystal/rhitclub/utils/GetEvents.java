package edu.rosehulman.krystal.rhitclub.utils;

import android.os.AsyncTask;
import android.util.Log;

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

import edu.rosehulman.krystal.rhitclub.MainActivity;

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
            Log.d("Event Result1",result);

            JSONObject res = null;
            try {
                res = new JSONObject(result);
                JSONArray events = res.getJSONArray("eventInfo");
                Log.d("Event Result2",events.toString());

                for (int t = 0; t < events.length(); t++) {
                    JSONObject jsonobject = null;
                    Event event = new Event();
                    jsonobject = events.getJSONObject(t);

                    event.setId(jsonobject.getString("event_id"));
                    event.setName(jsonobject.getString("event_title"));
                    event.setDes(jsonobject.getString("event_description"));
                    event.setHolder(jsonobject.getString("club_name"));

                    Log.d("Event Load",event.getName());

                    map.put(event.getName(),event);
                }
            } catch (JSONException e) {
                e.printStackTrace();
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
