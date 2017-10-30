package com.example.webservice.interfaces;

/**
 * Created by Mihovil on 28.10.2017..
 */

public class Login {
    public boolean isPostoji() {
        return postoji;
    }
    private String returnValue;

    public void setPostoji(boolean postoji) {
        this.postoji = postoji;
    }
    public String getReturnValue() {
        return returnValue;
    }

    public boolean postoji;
    public void setReturnValue(String returnValue) {
        this.returnValue = returnValue;
    }
}

