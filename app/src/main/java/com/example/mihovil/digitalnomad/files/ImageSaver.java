package com.example.mihovil.digitalnomad.files;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by Mihovil on 17.12.2017..
 */

public class ImageSaver {
    private String nameOfFile;
    private final  String path="/data/data/DigitalNomad/app_data/imageDir";
    Bitmap image;

    public ImageSaver(String nameOfFile) {
        this.nameOfFile = nameOfFile;
    }

    public Bitmap getImage() {
        return image;
    }

    public void saveToInternalStorage(Bitmap bitmapImage){
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath=new File(directory,nameOfFile+".jpg");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        loadImageFromStorage();
    }

    private Bitmap loadImageFromStorage()
    {
        try {
            File f=new File(path, nameOfFile+".jpg");
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            Log.d("TAG",b.toString());
            image = b;
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
            return null;
    }
}
