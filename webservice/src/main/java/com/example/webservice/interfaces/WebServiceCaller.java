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
    private Call<WorkspaceDetailsResponse> workspaceCall;

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

    public void UploadImage(String email, String image){
        CreateCaller();
        call = serviceCaller.uploadImage(email,image);
        CheckCall();
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

    public void addWorkspaceAsUser(String mail, String name, String desc, String  adress, String country, String city, String longi, String lati, String accomodation, String food, String wifi, String activities){
        CreateCaller();
        call = serviceCaller.addWorkspaceAndGetConfirmation(mail, name, desc, adress, country, city, longi, lati, accomodation, food, wifi, activities);
        CheckCall();
    }

    public void changePassword(String email, String oldPass, String newPass){
        CreateCaller();
        call = serviceCaller.changePassword(email,oldPass,newPass);
        CheckCall();
    }

    public void deleteWorkspace(String id){
        CreateCaller();
        call = serviceCaller.deleteWorkspace(id);
        CheckCall();
    }

    public void editWorkspace(String id, String name, String description, String adress, String country, String town, String longitude, String latitude, String accomodation, String food, String wifi, String activities){
        CreateCaller();
        call = serviceCaller.editWorkspace(id, name, description, adress, country, town, longitude, latitude, accomodation, food, wifi, activities);
        CheckCall();
    }
    public void getWorkspaceDetails(String id) {
        CreateCaller();
        workspaceCall = serviceCaller.getWorkspaceDetails(id);
        CheckWorkspaceDetailsCall();
    }

    public void uploadRatingAndComments(String email, String id, float grade, String comment){
        CreateCaller();
        call = serviceCaller.uploadRatingAndComments(email,id,grade,comment);
        CheckCall();
    }

    public void showReviews(String id){
        CreateCaller();
        call = serviceCaller.showReviews(id);
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
                    listener.onServiceFail("Check your internet connection!");
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
                System.out.println("nevalja!!!!!");
            }
        });
    }

    public void CheckWorkspaceDetailsCall(){
        workspaceCall.enqueue(new Callback<WorkspaceDetailsResponse>() {
            @Override
            public void onResponse(Response<WorkspaceDetailsResponse> response, Retrofit retrofit) {
                listener.onServiceDone(response.body());
            }

            @Override
            public void onFailure(Throwable t) {
                System.out.println("zesci fejl!!!!!");
            }
        });
    }
}
