package com.mihovil.advancedsearch;

import android.support.v4.app.Fragment;

import com.example.mihovil.digitalnomad.Interface.OnDataDisplay;
import com.example.mihovil.digitalnomad.GetWorkspaceData;
import com.example.webservice.interfaces.WebServiceCaller;

/**
 * Created by Ian on 1/26/2018.
 */

public class AdvancedSearchGetWorkspaceData extends GetWorkspaceData {

    AdvancedResult result = null;
    private advancedSearchFragment fragment;

    public AdvancedSearchGetWorkspaceData(OnDataDisplay onDataDisplay) {
        super(onDataDisplay);
        fragment = new advancedSearchFragment();
        fragment.getController(this);
    }

    @Override
    public Fragment getFragment() {
        return fragment;
    }

    @Override
    public void loadData(Object object){
        result = (AdvancedResult) object;
        WebServiceCaller wsc = new WebServiceCaller(this);
        wsc.advancedSearch(result.getCountry().toString(), Boolean.toString(result.getAccomodation()), Boolean.toString(result.getFood()), Boolean.toString(result.getWifi()), Boolean.toString(result.getActivities()), Boolean.toString(result.getaZ()));
    }

}
