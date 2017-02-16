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
import java.util.ArrayList;
import java.util.HashMap;

import edu.rosehulman.krystal.rhitclub.MainActivity;

/**
 * Created by KrystalYang on 2/15/17.
 */

public class GetUser extends AsyncTask<String, Void, User> {

    private GetUser.UserConsumer mUser;

    public GetUser(GetUser.UserConsumer activity){
        this.mUser = activity;
    }

    @Override
    protected User doInBackground(String... urlStrings) {
        String urlString = urlStrings[0];

        User user = new User(MainActivity.username);
        Log.d("TAG",urlString);

        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Cookie", "token=" + MainActivity.token);

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
            Log.d("User Result1",result);
            try {
                JSONObject userinfo = new JSONObject(result);

                user.setmName(userinfo.getString("name"));
                user.setEmail(userinfo.getString("email"));

                JSONArray signedUp = userinfo.getJSONArray("signed_up");
                JSONArray subscribed = userinfo.getJSONArray("subscribed");
                JSONArray managed = userinfo.getJSONArray("manages");
                JSONArray events = userinfo.getJSONArray("events");

                ArrayList<String> clubs = new ArrayList<>();
                ArrayList<String> subs = new ArrayList<>();
                HashMap<String,String> title = new HashMap<>();
                for(int i = 0; i < signedUp.length();i++){
                    clubs.add(signedUp.getString(i));
                }
                for(int i = 0; i < subscribed.length();i++){
                    subs.add(subscribed.getString(i));
                }
                for(int i = 0; i < managed.length();i++){
                    JSONObject cur = managed.getJSONObject(i);
                    title.put(cur.getString("club_name"),cur.getString("title"));
                }
                ArrayList<String> eves = new ArrayList<>();
                for(int i = 0; i<events.length();i++){
                    if(!eves.contains(events.getString(i))) {
                        eves.add(events.getString(i));
                    }
                }

                user.setmClubs(clubs);
                user.setmEvents(eves);
                user.setmSubs(subs);
                user.setTitle(title);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            in.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
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
