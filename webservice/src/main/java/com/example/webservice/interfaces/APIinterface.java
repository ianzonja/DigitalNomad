package com.example.webservice.interfaces;

import retrofit.Call;
import retrofit.http.Field;
import retrofit.http.GET;

/**
 * Created by Mihovil on 29.10.2017..
 */

public interface APIinterface {
    @GET("http://jospudja.heliohost.org/webservis.php")
    Call<Login> authenticate(@Field("email") String email, @Field("password") String password);


    @GET("http://jospudja.heliohost.org/insert.php")
    Call<Login> registration(@Field("email") String email, @Field("password") String password, @Field("name") String name, @Field("last_name") String lastName);
}
