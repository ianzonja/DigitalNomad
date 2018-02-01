package com.example.mihovil.digitalnomad.Interface;

import android.support.v4.app.Fragment;

import com.example.mihovil.digitalnomad.getWorkspaceData;

/**
 * Created by ivysl on 1.2.2018..
 */

public interface getWorkspaces {
    Fragment getFragment(getWorkspaceData dl);
    void loadData(Object object);
    void displayData();
}
