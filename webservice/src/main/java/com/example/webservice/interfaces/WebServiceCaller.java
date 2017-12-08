package com.example.webservice.interfaces;

import com.example.webservice.interfaces.interfaces.APIinterface;
import com.example.webservice.interfaces.interfaces.OnServiceFinished;

import com.squareup.okhttp.OkHttpClient;

import java.util.List;
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
    private Call<List<WorkspaceValue>> callWorkspaces;

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

    public void GetClientWorkspaces(String mail){
        CreateCaller();
        callWorkspaces = serviceCaller.getUserWorkspaces(mail);
        CheckWorkspaceCall();
    }

    public void addWorkspaceAsUser(String mail, String name, String desc, String  adress, String country, String city, String longi, String lati){
        CreateCaller();
        call = serviceCaller.addWorkspaceAndGetConfirmation(mail, name, desc, adress, country, city, longi, lati);
        CheckCall();
    }

    public void changePassword(String email, String oldPass, String newPass){
        CreateCaller();
        call = serviceCaller.changePassword(email,oldPass,newPass);
        CheckCall();
    }

    private void CheckCall(){
        if (call != null) {
            System.out.println(call.toString());
            call.enqueue(new Callback<ServiceResponse>() {
                @Override
                public void onResponse(Response<ServiceResponse> response, Retrofit retrofit) {
                    System.out.println("uso u response");
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
                    listener.onServiceFail("Check your internet connection uso u call");
                    System.out.println("nisam uso u response");
                }
            });
        }
    }

    private void CheckWorkspaceCall(){
        callWorkspaces.enqueue(new Callback<List<WorkspaceValue>>() {
            @Override
            public void onResponse(Response<List<WorkspaceValue>> response, Retrofit retrofit) {
                if(response.isSuccess())
                    listener.onServiceDone(response.body());
            }

            @Override
            public void onFailure(Throwable t) {
                System.out.println("kurcina");
            }
        });
    }
}
