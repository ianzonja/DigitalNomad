package com.example.mihovil.digitalnomad.Interface;

import android.support.v4.app.Fragment;

/**
 * Created by ivysl on 1.2.2018..
 */

public interface OnSelectedModule {
    Fragment getFragment();
    void loadData(Object object);
    void setReturnPoint(OnDataDisplay onDataDisplay);
}
