package com.mihovil.advancedsearch;

import android.support.v4.app.Fragment;

import com.example.mihovil.digitalnomad.Interface.OnDataDisplay;
import com.example.mihovil.digitalnomad.getWorkspaceData;
import com.example.webservice.interfaces.WebServiceCaller;

/**
 * Created by Ian on 1/26/2018.
 */

public class AdvancedSearchGetWorkspaceData extends getWorkspaceData {

    AdvancedResult result = null;

    public AdvancedSearchGetWorkspaceData(OnDataDisplay onDataDisplay) {
        super(onDataDisplay);
    }

    @Override
    public Fragment getFragment(getWorkspaceData dl) {
        Fragment fragment = new advancedSearchFragment(dl);
        return fragment;
    }

    @Override
    public void loadData(Object object){
        result = (AdvancedResult) object;
        WebServiceCaller wsc = new WebServiceCaller(this);
        wsc.advancedSearch(result.getCountry().toString(), Boolean.toString(result.getAccomodation()), Boolean.toString(result.getFood()), Boolean.toString(result.getWifi()), Boolean.toString(result.getActivities()), Boolean.toString(result.getaZ()));
    }

}
