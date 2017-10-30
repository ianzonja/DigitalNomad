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
    APIinterface serviceCaller;
    Call<ServiceResponse> call;

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

    public void CreateCaller(){
        serviceCaller = retrofit.create(APIinterface.class);
    }

    // get all records from a web service
    public void Registrate(String email, String password, String name, String last_name) {
        CreateCaller();
        call = serviceCaller.registration(email, password, name, last_name);
        CheckCall();
    }

    public void Login(String email, String password){
        CreateCaller();
        call = serviceCaller.authenticate(email, password);
        CheckCall();
    }

    public void CheckCall(){
        if (call != null) {
            call.enqueue(new Callback<ServiceResponse>() {
                @Override
                public void onResponse(Response<ServiceResponse> response, Retrofit retrofit) {
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
