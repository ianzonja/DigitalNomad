package com.example.mihovil.digitalnomad.staticClass;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import entities.User;

/**
 * Created by Mihovil on 27.11.2017..
 */

public class FolderManagment {

    public static void DohvatiSliku(String urlUser,String name ) throws IOException {
        URL url = new URL(urlUser);
        InputStream in = new BufferedInputStream(url.openStream());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        int n = 0;
        while (-1!=(n=in.read(buf)))
        {
            out.write(buf, 0, n);
        }
        out.close();
        in.close();
        byte[] response = out.toByteArray();
        SpremiSliku(response, name);
    }
    private static  void SpremiSliku(byte[] response, String name) throws IOException {
        FileOutputStream fos = new FileOutputStream(name+".jpg");
        fos.write(response);
        fos.close();
    }

    public static Bitmap PostaviSliku(String name){
        Log.d("TAG","tu sam\n"+name);
        return BitmapFactory.decodeFile(name+".jpg") ;
    }
}
