package com.mihovil.advancedsearch;

import com.example.mihovil.digitalnomad.DataLoader;
import com.example.mihovil.digitalnomad.Interface.OnDataDisplay;
import com.example.webservice.interfaces.WebServiceCaller;

/**
 * Created by Ian on 1/26/2018.
 */

public class AdvancedSearchDataLoader extends DataLoader {

    AdvancedResult result = null;

    public AdvancedSearchDataLoader(OnDataDisplay onDataDisplay) {
        super(onDataDisplay);
    }

    @Override
    public void loadData(Object object){
        result = (AdvancedResult) object;
        WebServiceCaller wsc = new WebServiceCaller(this);
        wsc.advancedSearch(result.getCountry().toString(), Boolean.toString(result.getAccomodation()), Boolean.toString(result.getFood()), Boolean.toString(result.getWifi()), Boolean.toString(result.getActivities()), Boolean.toString(result.getaZ()));
    }
}
