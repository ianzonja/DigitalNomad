package com.example.webservice.interfaces;

import retrofit.Call;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by Mihovil on 29.10.2017..
 */

public interface APIinterface {
    @GET("webservis.php")
    Call<Login> authenticate(@Field("email") String email, @Field("password") String password);

    //@FormUrlEncoded
    @GET("insert.php")
    Call<Login> registration(@Query("email") String email, @Query("password") String password, @Query("name") String name, @Query("last_name") String lastName);
}
