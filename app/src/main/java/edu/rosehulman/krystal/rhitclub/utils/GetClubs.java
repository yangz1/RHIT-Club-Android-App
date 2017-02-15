package edu.rosehulman.krystal.rhitclub.utils;

import android.os.AsyncTask;
import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import edu.rosehulman.krystal.rhitclub.MainActivity;

import static android.R.id.content;

/**
 * Created by KrystalYang on 1/29/17.
 */

public class GetClubs extends AsyncTask<String, Void, HashMap<String,Club>  > {
    private ClubsConsumer mUser;

    public GetClubs(ClubsConsumer activity){
        this.mUser = activity;
    }

    @Override
    protected HashMap<String,Club> doInBackground(String... urlStrings) {
        String urlString = urlStrings[0];

        //User  user = null;
        HashMap<String,Club> map = new HashMap<>();
        Log.d("TAG",urlString);


        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");
            //conn.setRequestProperty("Cookie", "token=" + MainActivity.token);

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

            int i =18;
            int j =result.length()-2;
            result = result.substring(i,j+1);
            Log.d("Club Result",result);

            JSONArray jsonarray = null;
            try {
                jsonarray = new JSONArray(result);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            for (int t = 0; t < jsonarray.length(); t++) {
                JSONObject jsonobject = null;
                Club club = new Club();
                try {
                    jsonobject = jsonarray.getJSONObject(t);
                    String club_name = jsonobject.getString("club_name");
                    String club_type = jsonobject.getString("club_type");
                    String club_description = jsonobject.getString("club_description");
                    String club_off = jsonobject.getString("managers");
                    JSONArray off = new JSONArray(club_off);
                    JSONObject offi = off.getJSONObject(0);
                    String officer = "Title: "+offi.getString("title")+"--"+offi.getString("name");
                    String officeremail = offi.getString("email");
                    club.setName(club_name);
                    club.setType(club_type);
                    club.setDescription(club_description);
                    club.setOfficer(officer);
                    club.setOfficerEmail(officeremail);
                    Log.d("Club Load",club_name);
                    map.put(club_name,club);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            in.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    @Override
    protected void onPostExecute(HashMap<String,Club>   user) {
        super.onPostExecute(user);
        mUser.onClubsLoaded(user);
    }

    public interface ClubsConsumer{
        public void onClubsLoaded(HashMap<String,Club>   user);
    }
}
