package com.example.map;

import com.example.mihovil.digitalnomad.DataLoader;
import com.example.mihovil.digitalnomad.Interface.OnDataDisplay;
import com.example.webservice.interfaces.WebServiceCaller;

/**
 * Created by Ian on 1/26/2018.
 */

public class LocationDataLoader extends DataLoader {

    private LocationResult locationResult = null;

    public LocationDataLoader(OnDataDisplay onDataDisplay) {
        super(onDataDisplay);
    }

    @Override
    public void loadData(Object object){
        this.locationResult = (LocationResult) object;
        WebServiceCaller wsc = new WebServiceCaller(this);
        wsc.getMainMenu(Double.toString(locationResult.getLongitude()), Double.toString(locationResult.getLatitude()), Integer.toString(locationResult.getRadius()));
    }
}
