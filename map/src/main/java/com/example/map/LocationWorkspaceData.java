package com.example.map;

import android.support.v4.app.Fragment;

import com.example.mihovil.digitalnomad.WorkspaceData;
import com.example.mihovil.digitalnomad.Interface.OnDataDisplay;
import com.example.webservice.interfaces.WebServiceCaller;

/**
 * Created by Ian on 1/26/2018.
 */

public class LocationWorkspaceData extends WorkspaceData {

    private LocationResult locationResult = null;
    private MapFragment fragment;

    public LocationWorkspaceData() {
        fragment = new MapFragment();
        fragment.getController(this);
    }

    @Override
    public Fragment getFragment() {
        return  fragment;
    }

    @Override
    public void loadData(Object object){
        this.locationResult = (LocationResult) object;
        WebServiceCaller wsc = new WebServiceCaller(this);
        wsc.getMainMenu(Double.toString(locationResult.getLongitude()), Double.toString(locationResult.getLatitude()), Integer.toString(locationResult.getRadius()));
    }
}
