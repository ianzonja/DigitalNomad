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
import com.example.mihovil.digitalnomad.Interface.OnPicturesRecived;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Mihovil on 27.11.2017..
 */

public class GetImage extends AsyncTask<Void, Integer, Void> {
    private String urlProfile;
    private ArrayList<String> urlArray = null;
    private Bitmap[] bitmap;
    private Boolean isReadyToReturnImages = false;


    private OnImageDownload myListener;
    private OnPicturesRecived listener;

    public GetImage( OnImageDownload imageListener) {
        myListener = imageListener;
        bitmap = new Bitmap[1];
    }

    public GetImage(ArrayList<String> urls, OnPicturesRecived listener) {
        this.listener = listener;
        this.urlArray = urls;
        bitmap = new Bitmap[urls.size()];
    }

    public void setUrl(String urlProfile) {
        this.urlProfile = urlProfile;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        if (values.length > 0) {
            if (values[0] == 1) {
                if(urlArray == null)
                    myListener.onImageDownload(bitmap[0]);
                else
                    if(isReadyToReturnImages)
                        listener.picturesReceived(bitmap);
            } else {
                //myListener.onImageSaved();
            }
        }
    }

    private void getPicture() throws IOException {

        URL url = new URL(urlProfile);

        bitmap[0] = BitmapFactory.decodeStream(url.openConnection().getInputStream());

        publishProgress(1);
    }

    private void getPictures() throws IOException{
        for(int i = 0; i<urlArray.size(); i++){
            URL url = new URL(urlArray.get(i));

            bitmap[i] = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            if(i == urlArray.size() -1)
                isReadyToReturnImages = true;
            publishProgress(1);
        }
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            if(urlArray == null)
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
