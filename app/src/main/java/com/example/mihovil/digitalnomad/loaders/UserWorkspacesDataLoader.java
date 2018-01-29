package com.example.mihovil.digitalnomad.loaders;

import com.example.mihovil.digitalnomad.DataLoader;
import com.example.mihovil.digitalnomad.Interface.OnDataDisplay;
import com.example.webservice.interfaces.WebServiceCaller;

/**
 * Created by Ian on 1/29/2018.
 */

public class UserWorkspacesDataLoader extends DataLoader {
    String mail = null;

    public UserWorkspacesDataLoader(OnDataDisplay onDataDisplay) {
        super(onDataDisplay);
    }

    @Override
    public void loadData(Object object){
        mail = (String) object;
        WebServiceCaller wsc = new WebServiceCaller(this);
        wsc.GetClientWorkspaces(mail);
    }
}
