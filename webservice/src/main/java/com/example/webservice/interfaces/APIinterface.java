package com.example.webservice.interfaces;

import retrofit.Call;
import retrofit.http.Field;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by Mihovil on 29.10.2017..
 */

public interface APIinterface {
    @GET("http://jospudja.heliohost.org/webservis.php")
    Call<Login> authenticate(@Field("email") String email, @Field("password") String password);


    @GET("insert.php/{email}/{password}/{name}{last_name}")
    Call<Login> registration(@Path("email") String email, @Path("password") String password, @Path("name") String name, @Path("last_name") String lastName);
}
