package edu.rosehulman.krystal.rhitclub.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by KrystalYang on 2/16/17.
 */

public class GetImage extends AsyncTask<String, Void, Bitmap> {
    private GetImage.ImageConsumer mImageConsumer;

    public GetImage(GetImage.ImageConsumer activity){
        this.mImageConsumer = activity;
    }

    @Override
    protected Bitmap doInBackground(String... urlStrings) {
        String urlString = urlStrings[0].replaceAll("\\/","/");
        Bitmap bitmap = null;
        try {
            InputStream in = new java.net.URL(urlString).openStream();
            bitmap = BitmapFactory.decodeStream(in);
        } catch (IOException e) {
            Log.d("Tag", "ERROR:" + e.getMessage());
        }
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        mImageConsumer.onImageLoaded(bitmap);
    }

    public interface ImageConsumer {
        public void onImageLoaded(Bitmap bitmap);
    }

}

