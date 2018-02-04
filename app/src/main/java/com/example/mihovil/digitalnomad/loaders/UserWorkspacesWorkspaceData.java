package com.example.mihovil.digitalnomad.loaders;

import android.support.v4.app.Fragment;

import com.example.mihovil.digitalnomad.WorkspaceData;
import com.example.mihovil.digitalnomad.Interface.OnDataDisplay;
import com.example.webservice.interfaces.WebServiceCaller;

/**
 * Created by Ian on 1/29/2018.
 */

public class UserWorkspacesWorkspaceData extends WorkspaceData {
    String mail = null;

    @Override
    public Fragment getFragment() {
        return null;
    }

    @Override
    public void loadData(Object object){
        mail = (String) object;
        WebServiceCaller wsc = new WebServiceCaller(this);
        wsc.GetClientWorkspaces(mail);
    }
}
