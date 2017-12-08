package com.example.webservice.interfaces;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Mihovil on 28.10.2017..
 */

public class ServiceResponse {
    private Boolean postoji;
    public boolean isPostoji() { return postoji; }


    private String returnValue;
    public String getReturnValue() {
        return returnValue;
    }

    public JSONArray getPosts() {
        return posts;
    }

    private JSONArray posts;

    private int reponseId;
    private String email;
    private String name;
    private String urlPicture;
    private String responseMessage;


    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public int getReponseId() {
        return reponseId;
    }

    public void setReponseId(int reponseId) {
        this.reponseId = reponseId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrlPicture() {
        return urlPicture;
    }

    public void setUrlPicture(String urlPicture) {
        this.urlPicture = urlPicture;
    }
}


