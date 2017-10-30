package com.example.webservice.interfaces;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by Mihovil on 29.10.2017..
 */

public interface APIinterface {
    @GET("webservis.php")
    Call<ServiceResponse> authenticate(@Query("email") String email, @Query("password") String password);

    //@FormUrlEncoded
    @GET("insert.php")
    Call<ServiceResponse> registration(@Query("email") String email, @Query("password") String password, @Query("name") String name, @Query("last_name") String lastName);
}
