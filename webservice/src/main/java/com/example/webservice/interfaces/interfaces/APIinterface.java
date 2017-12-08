package com.example.webservice.interfaces.interfaces;

import com.example.webservice.interfaces.ServiceResponse;
import com.example.webservice.interfaces.WorkspaceValue;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;
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

    @GET("prikazWorkspacea.php")
    Call<List<WorkspaceValue>> getUserWorkspaces(@Query("email") String email);

    @GET("registracijaWorkspacea.php")
    Call<ServiceResponse> addWorkspaceAndGetConfirmation(@Query("email") String mail, @Query("name") String name, @Query("description") String description, @Query("adress") String adress, @Query("country") String country, @Query("town") String town, @Query("longitude") String longitude, @Query("latitude") String latitude);

    @GET("changePassword.php")
    Call<ServiceResponse> changePassword(@Query("email") String email,@Query("oldPass") String oldPass,@Query("newPass") String newPass);
}
