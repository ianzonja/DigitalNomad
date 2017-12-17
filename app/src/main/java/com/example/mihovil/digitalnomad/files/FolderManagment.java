package com.example.mihovil.digitalnomad.files;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.example.mihovil.digitalnomad.Interface.OnImageDownload;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

/**
 * Created by Mihovil on 27.11.2017..
 */

public class FolderManagment extends AsyncTask<Void, Integer, Void> {
    private Context context;
    private String  name;
    private String urlProfile;

    private Bitmap bitmap;


    private OnImageDownload myListener;

    public FolderManagment(String name,String url, Context context, OnImageDownload imageListener) {
        this.name = name;
        this.urlProfile = url;
        this.context = context;
        myListener = imageListener;

    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        if (values.length > 0) {
            if (values[0] == 1) {
                myListener.onImageDownload(bitmap);
            } else {
                //myListener.onImageSaved();
            }
        }
    }

    private void getPicture() throws IOException {

        URL url = new URL(urlProfile);

        bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());

        publishProgress(1);
        // SpremiSliku();
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            getPicture();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
