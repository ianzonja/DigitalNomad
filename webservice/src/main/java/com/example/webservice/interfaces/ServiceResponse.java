package com.example.webservice.interfaces;

/**
 * Created by Mihovil on 28.10.2017..
 */

public class ServiceResponse {
    public boolean isPostoji() {
        return postoji;
    }

    public void setPostoji(boolean postoji) {
        this.postoji = postoji;
    }

    public boolean postoji;

    private String returnValue;

    public String getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(String returnValue) {
        this.returnValue = returnValue;
    }
}

