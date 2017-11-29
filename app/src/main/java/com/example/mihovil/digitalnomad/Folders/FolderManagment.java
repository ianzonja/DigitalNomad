package com.example.mihovil.digitalnomad.Folders;

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

    private void DohvatiSliku() throws IOException {

        URL url = new URL(urlProfile);

        bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());

        publishProgress(1);
        // SpremiSliku();
    }

    private void SpremiSliku() throws IOException {
        ContextWrapper cw = new ContextWrapper(context);
        File directory = cw.getDir("Slike", Context.MODE_PRIVATE);
        File myPath = new File(directory, name + ".jpg");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(myPath);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Bitmap PostaviSliku() {
        ContextWrapper cw = new ContextWrapper(context);
        File directory = cw.getDir("Slike", Context.MODE_PRIVATE);
        directory.mkdirs();
        File myPath = new File(directory, name + ".jpg");
        FileOutputStream fos = null;
        try {
            Log.d("TAG", "isNull " + (myPath != null));
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(myPath));
            return b;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    protected Void doInBackground(Void... params) {
        try {
            DohvatiSliku();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
