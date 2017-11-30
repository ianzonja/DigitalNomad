package com.example.webservice.interfaces;

import com.example.webservice.interfaces.interfaces.APIinterface;
import com.example.webservice.interfaces.interfaces.OnServiceFinished;

import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by Mihovil on 29.10.2017..
 */

public class WebServiceCaller {
    private OnServiceFinished listener;

    private Retrofit retrofit;
    private APIinterface serviceCaller;
    private Call<ServiceResponse> call;

    public WebServiceCaller(OnServiceFinished listener) {
        final String baseUrl = "http://jospudjaatfoi.000webhostapp.com/";
        this.listener=listener;

        final OkHttpClient client = new OkHttpClient();
        client.setReadTimeout(60, TimeUnit.SECONDS);
        client.setConnectTimeout(60, TimeUnit.SECONDS);


        this.retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }

    private void CreateCaller(){
        serviceCaller = retrofit.create(APIinterface.class);
    }

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

    public void FacebookLogin(String email, String name, String lastName, String url)  {
        CreateCaller();
        call = serviceCaller.facebookRegistration(email,name,lastName,url);
        call.enqueue(new Callback<ServiceResponse>() {
            @Override
            public void onResponse(Response<ServiceResponse> response, Retrofit retrofit) {
            }

            @Override
            public void onFailure(Throwable t) {
            }
        });
    }

    public void GetUserProfile(String email){
        CreateCaller();
        call = serviceCaller.getUserProfile(email);
        CheckCall();
    }

    public void GetClientWorkspaces(int id){
        CreateCaller();
        call = serviceCaller.getUserWorkspaces(id);
        CheckCall();
    }

    public void addWorkspaceAsUser(String name, String country, String city, String  adress){
        CreateCaller();
        call = serviceCaller.addWorkspaceAndGetConfirmation(name, country, city, adress);
        CheckCall();
    }

    private void CheckCall(){
        if (call != null) {
            call.enqueue(new Callback<ServiceResponse>() {
                @Override
                public void onResponse(Response<ServiceResponse> response, Retrofit retrofit) {
                    try {
                        if (response.isSuccess()){
                            listener.onServiceDone(response.body());
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    t.printStackTrace();
                    listener.onServiceFail("Check your internet connection");
                }
            });
        }
    }
}
