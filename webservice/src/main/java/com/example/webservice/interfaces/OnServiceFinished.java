package com.example.webservice.interfaces;

/**
 * Created by Mihovil on 29.10.2017..
 */

public interface OnServiceFinished {
    void onServiceDone(Object response);
    void onServiceFail(Object message);
}
