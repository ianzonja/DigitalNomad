package com.example.mihovil.digitalnomad.loaders;

import com.example.air.core.DataLoader;
import com.example.air.core.OnDataLoaded;
import com.example.mihovil.digitalnomad.models.AdvancedResult;
import com.example.mihovil.digitalnomad.models.LocationResult;
import com.example.mihovil.digitalnomad.models.Workspace;
import com.example.webservice.interfaces.WebServiceCaller;
import com.example.webservice.interfaces.WorkspaceValue;

import java.util.ArrayList;

/**
 * Created by Ian on 1/25/2018.
 */

public class WsDataLoader extends DataLoader {

    private String email;
    private AdvancedResult result = null;
    private LocationResult locationResult = null;

    public WsDataLoader(String email) {
        this.email = email;
    }

    public WsDataLoader(AdvancedResult advancedResult) {
        this.result = advancedResult;
    }

    public WsDataLoader(LocationResult result) {
        this.locationResult = result;
    }

    @Override
    public void loadData(OnDataLoaded dataloaded){
        super.loadData(dataloaded);
        WebServiceCaller wsc = new WebServiceCaller(this);
        if(result != null)
            wsc.advancedSearch(result.getCountry().toString(), Boolean.toString(result.getAccomodation()), Boolean.toString(result.getFood()), Boolean.toString(result.getWifi()), Boolean.toString(result.getActivities()), Boolean.toString(result.getaZ()));
        else if(locationResult != null)
            wsc.getMainMenu(Double.toString(locationResult.getLongitude()), Double.toString(locationResult.getLatitude()), Integer.toString(locationResult.getRadius()));
        else
            wsc.GetClientWorkspaces(email);
    }

    @Override
    public void onServiceDone(Object response) {
        ArrayList<WorkspaceValue> workspaceResponse = (ArrayList<WorkspaceValue>)response;
        System.out.println("velicina onservicedone; " + workspaceResponse.size());
        for(int i = 0; i<workspaceResponse.size(); i++){
            workspaces.add(new Workspace(workspaceResponse.get(i).getIdworkspace(), workspaceResponse.get(i).getName(), workspaceResponse.get(i).getDescription(), workspaceResponse.get(i).getAdress(), workspaceResponse.get(i).getCountry(), workspaceResponse.get(i).getTown(), workspaceResponse.get(i).getLongitude(), workspaceResponse.get(i).getLatitude(), "http://jospudjaatfoi.000webhostapp.com/Pictures/Workspaces/Screenshot_1.png"));
        }
        getResponse();
    }

    @Override
    public void onServiceFail(Object message) {
        System.out.println("Something not working");
    }
}
