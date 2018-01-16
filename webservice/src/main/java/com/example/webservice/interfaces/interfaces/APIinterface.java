package com.example.webservice.interfaces.interfaces;

import com.example.webservice.interfaces.ServiceResponse;
import com.example.webservice.interfaces.WorkspaceDetailsResponse;
import com.example.webservice.interfaces.WorkspaceValue;

import java.util.List;

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

    @GET("prikazWorkspacea.php")
    Call<List<WorkspaceValue>> getUserWorkspaces(@Query("email") String email);

    @GET("registracijaWorkspacea.php")
    Call<ServiceResponse> addWorkspaceAndGetConfirmation(@Query("email") String mail, @Query("name") String name, @Query("description") String description, @Query("adress") String adress, @Query("country") String country, @Query("town") String town, @Query("longitude") String longitude, @Query("latitude") String latitude, @Query("accomodation") String accomodation, @Query("food") String food, @Query("wifi") String wifi, @Query("activities") String activities);

    @GET("changePassword.php")
    Call<ServiceResponse> changePassword(@Query("email") String email,@Query("oldPass") String oldPass,@Query("newPass") String newPass);

    @GET("deleteWorkspace.php")
    Call<ServiceResponse> deleteWorkspace(@Query("idWorkspace") String id);

    @GET("updateWorkspace.php")
    Call<ServiceResponse> editWorkspace(@Query("idWorkspace") String id, @Query("name") String name, @Query("description") String description, @Query("adress") String adress, @Query("country") String country, @Query("town") String town, @Query("longitude") String longitude, @Query("latitude") String latitude, @Query("accomodation") String accomodation, @Query("food") String food, @Query("wifi") String wifi, @Query("activities") String activities);

    @FormUrlEncoded
    @POST("pictureUrl_test.php")
    Call<ServiceResponse> uploadImage(@Field("email")String email, @Field("imageURL") String image );

    @GET("reviewWorkspace.php")
    Call<ServiceResponse> uploadRatingAndComments(@Query("email") String email, @Query("idWorkspace") String id, @Query("grade") float grade, @Query("review") String comment);

    @GET("prikazRecenzije.php")
    Call<ServiceResponse> showReviews(@Query("idWorkspace") String id);

    @GET("prikazDetaljaWorkspacea.php")
    Call<WorkspaceDetailsResponse> getWorkspaceDetails(@Query("idworkspace")String id);
}
