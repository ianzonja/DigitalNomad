package com.example.mihovil.digitalnomad.files;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import com.example.mihovil.digitalnomad.Interface.OnImageDownload;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

/**
 * Created by Mihovil on 27.11.2017..
 */

public class GetImage extends AsyncTask<Void, Integer, Void> {
    private String urlProfile;

    private Bitmap bitmap;


    private OnImageDownload myListener;

    public GetImage(String url, OnImageDownload imageListener) {
        this.urlProfile = url;
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

    public static String getEncoded64ImageStringFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteFormat = stream.toByteArray();
        return Base64.encodeToString(byteFormat, Base64.NO_WRAP);
    }
    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = 1000;

        paint.setAntiAlias(true);

        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

}
