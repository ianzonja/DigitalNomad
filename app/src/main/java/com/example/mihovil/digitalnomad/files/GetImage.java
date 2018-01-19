package com.example.mihovil.digitalnomad.files;

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

import com.example.mihovil.digitalnomad.Interface.OnImageDownload;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Mihovil on 27.11.2017..
 */

public class GetImage extends AsyncTask<Void, Integer, Void> {
    private String urlProfile;

    private Bitmap[] bitmap;
    ArrayList<String> urls;
    Boolean isGettingSingleUrl;
    Boolean isReadyToReturnImages = false;
    int i=0;

    private OnImageDownload myListener;

    public GetImage(String url, OnImageDownload imageListener) {
        this.urlProfile = url;
        myListener = imageListener;
        isGettingSingleUrl = true;
        bitmap = new Bitmap[1];
        this.urls = new ArrayList<String>();
        this.urls.add(url);
    }

    public GetImage(ArrayList<String> urls, OnImageDownload imageListener) {
        this.urls = urls;
        myListener = imageListener;
        isGettingSingleUrl = false;
        bitmap = new Bitmap[urls.size()];
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        if (values.length > 0) {
            System.out.println("on progress update" + i + "/" + urls.size());
            System.out.println("daje li" + isReadyToReturnImages.toString());
            if (values[0] == 1) {
                i++;
                if (urls.size() == i) {
                    myListener.onImageDownload(bitmap);
                }
            }
            } else {
                //
            }
        }

    private void getPicture() throws IOException {

        URL url = new URL(urls.get(0));

        Bitmap bm = BitmapFactory.decodeStream(url.openConnection().getInputStream());

        publishProgress(1);
    }

    private void getPictures() throws IOException{
        for(int i = 0; i<urls.size(); i++){
            URL url = new URL(urls.get(i));

            Bitmap bm = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            bitmap[i] = bm;
            publishProgress(1);
        }
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            if(bitmap.length == 1)
                getPicture();
            else
                getPictures();
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
