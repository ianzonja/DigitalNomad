package com.example.webservice.interfaces;

import com.squareup.okhttp.OkHttpClient;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by Mihovil on 29.10.2017..
 */

public class WebServiceCaller {

    Retrofit retrofit;

    private final String baseUrl = "http://jospudja.heliohost.org/";

    public WebServiceCaller() {

        OkHttpClient client = new OkHttpClient();


        this.retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

    }

    // get all records from a web service
    public void Registrate(String email, String password, String name, String last_name) {
        System.out.println("stop2");
        APIinterface serviceCaller = retrofit.create(APIinterface.class);
        System.out.println("stop1");
        Call<Login> call = serviceCaller.registration(email, password, name, last_name);
        if (call != null) {
            call.enqueue(new Callback<Login>() {
                @Override
                public void onResponse(Response<Login> response, Retrofit retrofit) {
                    try {
                        Login login = response.body();
                        if (response.isSuccess()) {
                            if (login.equals("true")) {
                                System.out.println("uspjesna registracija");
                            } else {
                                System.out.println("neuspjesna registracija");
                            }
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    t.printStackTrace();
                }
            });
        }
    }
}
