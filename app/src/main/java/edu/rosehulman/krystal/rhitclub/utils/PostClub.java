package edu.rosehulman.krystal.rhitclub.utils;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;

import edu.rosehulman.krystal.rhitclub.MainActivity;

/**
 * Created by KrystalYang on 2/15/17.
 */

public class PostClub extends AsyncTask<String, Void, Void  > {
    private PostClub.ChangeConsumer mUser;

    public PostClub(ChangeConsumer activity){
        this.mUser = activity;
    }

    @Override
    protected Void doInBackground(String... strings) {
        String urlString = strings[0];
        String flag = strings[1];
        String mode = strings[2];
        Log.d("TAG",urlString+"  "+flag+"  "+mode);

        URL url = null;
        try {
            url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            if(mode == "post"){
                conn.setRequestMethod("POST");
            }else{
                conn.setRequestMethod("DELETE");
            }
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Cookie", "token=" + MainActivity.token);
            conn.setDoOutput(true);

            OutputStream outputPost = new BufferedOutputStream(conn.getOutputStream());

            JSONObject ob = new JSONObject();
            if(flag == "sign"){
                ob.put("type","sign_up");
            }else if (flag == "subscribe"){
                ob.put("type","subscribe");
            }
            outputPost.write(ob.toString().getBytes("UTF-8"));
            outputPost.flush();
            outputPost.close();

            int responseCode = conn.getResponseCode();
            Log.d("Response Message",conn.getResponseMessage());
            Log.d("ResponseCode",""+responseCode);


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        mUser.onClubChangeLoaded();
    }

    public interface ChangeConsumer{
        public void onClubChangeLoaded();
    }
}
