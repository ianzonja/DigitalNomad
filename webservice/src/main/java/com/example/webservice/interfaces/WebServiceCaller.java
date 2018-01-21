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
    private Call<List<Review>> reviewsCall;

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
        serviceCaller = retrofit.create(APIinterface.class);
    }

    public void Registrate(String email, String password, String name, String last_name) {
        call = serviceCaller.registration(email, password, name, last_name);
        CheckCall();
    }

    public void Login(String email, String password){
        call = serviceCaller.authenticate(email, password);
        CheckCall();
    }

    public void FacebookLogin(String email, String name, String lastName, String url)  {
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
        call = serviceCaller.uploadImage(email,image);
        CheckCall();
    }

    public void GetUserProfile(String email){
        call = serviceCaller.getUserProfile(email);
        CheckCall();
    }

    public void GetClientWorkspaces(String mail){
        callWorkspaces = serviceCaller.getUserWorkspaces(mail);
        CheckWorkspaceCall();
    }

    public void addWorkspaceAsUser(String mail, String name, String desc, String  adress, String country, String city, String longi, String lati, String accomodation, String food, String wifi, String activities){
        call = serviceCaller.addWorkspaceAndGetConfirmation(mail, name, desc, adress, country, city, longi, lati, accomodation, food, wifi, activities);
        CheckCall();
    }

    public void changePassword(String email, String oldPass, String newPass){
        call = serviceCaller.changePassword(email,oldPass,newPass);
        CheckCall();
    }

    public void deleteWorkspace(String id){
        call = serviceCaller.deleteWorkspace(id);
        CheckCall();
    }

    public void editWorkspace(String id, String name, String description, String adress, String country, String town, String longitude, String latitude, String accomodation, String food, String wifi, String activities){
        call = serviceCaller.editWorkspace(id, name, description, adress, country, town, longitude, latitude, accomodation, food, wifi, activities);
        CheckCall();
    }
    public void getWorkspaceDetails(String id) {
        workspaceCall = serviceCaller.getWorkspaceDetails(id);
        CheckWorkspaceDetailsCall();
    }

    public void uploadRatingAndComments(String email, String id, float grade, String comment){
        call = serviceCaller.uploadRatingAndComments(email,id,grade,comment);
        CheckCall();
    }

    public void showReviews(String id){
        reviewsCall = serviceCaller.showReviews(id);
        CheckReviewsCall();
    }

    public void getMainMenu(String longitude, String latitude, String radius){
        callWorkspaces = serviceCaller.getMainMenu(longitude,latitude,radius);
        CheckWorkspaceCall();
    }

    public void advancedSearch(String country, String accomodation, String food, String wifi, String activities,String aZ){
        callWorkspaces = serviceCaller.advanceSearch(country,accomodation,food,wifi,activities,aZ);
        CheckWorkspaceCall();
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
                System.out.println("uso u onResponse");
                if(response.isSuccess()) {
                    System.out.println("success!!");
                    listener.onServiceDone(response.body());
                }
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

    private void CheckReviewsCall() {
        reviewsCall.enqueue(new Callback<List<Review>>() {
            @Override
            public void onResponse(Response<List<Review>> response, Retrofit retrofit) {
                System.out.println("jel usa tu?");
                if(response.isSuccess())
                    listener.onServiceDone(response.body());
            }

            @Override
            public void onFailure(Throwable t) {
                System.out.println("nevalja!!!!!");
            }
        });
    }
}
