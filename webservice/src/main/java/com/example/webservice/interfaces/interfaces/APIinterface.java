package com.example.webservice.interfaces.interfaces;

import com.example.webservice.interfaces.ServiceResponse;

import retrofit.Call;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by Mihovil on 29.10.2017..
 */

public interface APIinterface {
    @GET("prijavaKorisnika.php")
    Call<ServiceResponse> authenticate(@Query("email") String email, @Query("password") String password);


    @GET("registracijaKorisnika.php")
    Call<ServiceResponse> registration(@Query("email") String email, @Query("password") String password, @Query("name") String name, @Query("last_name") String lastName);

    @GET("facebookLogin.php")
    Call<ServiceResponse> facebookRegistration(@Query("email") String email, @Query("name") String name, @Query("last_name") String lastName, @Query("url") String url);


    @GET("userProfile.php")
    Call<ServiceResponse> getUserProfile(@Query("email") String email);

    @GET("imeservisa.php")
    Call<ServiceResponse> getUserWorkspaces(@Query("user_id") int id);
}
