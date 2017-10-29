package com.example.webservice.interfaces;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by Mihovil on 29.10.2017..
 */

public class WebServiceCaller {
    OnServiceFinished listener;

    Retrofit retrofit;

    private final String baseUrl = "http://jospudja.heliohost.org/";

    public WebServiceCaller(OnServiceFinished listener) {
        this.listener=listener;

        OkHttpClient client = new OkHttpClient();

        this.retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }

    // get all records from a web service
    public void Registrate(String email, String password, String name, String last_name) {
        APIinterface serviceCaller = retrofit.create(APIinterface.class);
        Call<Login> call = serviceCaller.registration(email, password, name, last_name);
        if (call != null) {
            call.enqueue(new Callback<Login>() {
                @Override
                public void onResponse(Response<Login> response, Retrofit retrofit) {
                    try {
                        if (response.isSuccess()){
                            listener.onServiceDone(response.body());
                        }else{
                            listener.onServiceFail(response.errorBody());
                        }

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    Log.i("stiglo", "failioure");
                    t.printStackTrace();
                }
            });
        }
    }
}
