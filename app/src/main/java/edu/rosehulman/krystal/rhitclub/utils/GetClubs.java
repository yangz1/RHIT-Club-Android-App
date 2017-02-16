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
import java.util.ArrayList;
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
            Log.d("Club Result1",result);
            try {
                JSONObject res = new JSONObject(result);
                JSONArray clubs = res.getJSONArray("clubs");
                Log.d("Club Result2",clubs.toString());

                for (int t = 0; t < clubs.length(); t++) {
                    JSONObject jsonobject = null;
                    Club club = new Club();
                    jsonobject = clubs.getJSONObject(t);
                    String club_name = jsonobject.getString("club_name");
                    String club_type = jsonobject.getString("club_type");
                    String club_description = jsonobject.getString("club_description");
                    JSONArray members = jsonobject.getJSONArray("members");
                    JSONArray subscribers = jsonobject.getJSONArray("subscribers");
                    boolean isOff = jsonobject.getString("officer").equals("true");
                    JSONArray managers = jsonobject.getJSONArray("managers");
                    JSONArray files = jsonobject.getJSONArray("club_files");

                    ArrayList<String> memb = new ArrayList<>();
                    ArrayList<String> subs = new ArrayList<>();
                    for(int i =0;i<members.length();i++){
                        memb.add(members.getString(i));
                    }
                    for(int i =0;i<subscribers.length();i++){
                        subs.add(subscribers.getString(i));
                    }
                    ArrayList<User> mang = new ArrayList<>();
                    for(int i =0;i<managers.length();i++){
                        mang.add(new User(managers.getJSONObject(i).getString("rose_username"),
                                managers.getJSONObject(i).getString("name"),
                                club_name,
                                managers.getJSONObject(i).getString("title")));
                    }

                    club.setName(club_name);
                    club.setType(club_type);
                    club.setDescription(club_description);
                    if(mang.size()!=0){
                        club.setOfficer(mang.get(0).getmName());
                        club.setOfficerEmail(mang.get(0).getEmail());
                    }
                    club.setisOfficer(isOff);
                    club.setMembers(memb);
                    club.setSubscribers(subs);
                    club.setManagers(mang);
                    if(files.length()!=0){
                        club.setImage(files.getString(0));
                    }

                    Log.d("Club Load",club_name);

                    map.put(club_name,club);
                }


            } catch (JSONException e) {
                e.printStackTrace();
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
