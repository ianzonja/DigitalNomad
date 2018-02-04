package com.example.mihovil.digitalnomad;

import android.graphics.Bitmap;

import com.example.mihovil.digitalnomad.Interface.OnDataDisplay;
import com.example.mihovil.digitalnomad.Interface.OnPicturesRecived;
import com.example.mihovil.digitalnomad.Interface.OnSelectedModule;
import com.example.mihovil.digitalnomad.files.GetImage;
import com.example.mihovil.digitalnomad.models.Workspace;
import com.example.webservice.interfaces.WorkspaceValue;
import com.example.webservice.interfaces.interfaces.OnServiceFinished;

import java.util.ArrayList;

/**
 * Created by Ian on 1/25/2018.
 */

public abstract class WorkspaceData implements OnServiceFinished, OnPicturesRecived, OnSelectedModule {
    public ArrayList<Workspace> workspaces = new ArrayList<>();
    protected OnDataDisplay onDataDisplayListener;
    ArrayList<WorkspaceValue> workspaceResponse = null;

    @Override
    public void setReturnPoint(OnDataDisplay onDataDisplay){
        onDataDisplayListener = onDataDisplay;
    }

    @Override
    public void onServiceDone(Object response) {
        workspaceResponse = (ArrayList<WorkspaceValue>)response;
        System.out.println("velicina onservicedone; " + workspaceResponse.size());
        ArrayList<String> urls = new ArrayList<>();
        for(int i = 0; i<workspaceResponse.size(); i++){
            urls.add("http://jospudjaatfoi.000webhostapp.com/Pictures/Workspaces/Screenshot_1.png");
        }
        GetImage getImage = new GetImage(urls, this);
        getImage.execute();
    }

    @Override
    public void onServiceFail(Object message) {
        System.out.println("Something not working");
    }

    @Override
    public void picturesReceived(Bitmap[] bitmap) {
        for(int i = 0; i<workspaceResponse.size(); i++){
            workspaces.add(new Workspace(workspaceResponse.get(i).getIdworkspace(), workspaceResponse.get(i).getName(), workspaceResponse.get(i).getDescription(), workspaceResponse.get(i).getAdress(), workspaceResponse.get(i).getCountry(), workspaceResponse.get(i).getTown(), workspaceResponse.get(i).getLongitude(), workspaceResponse.get(i).getLatitude(), bitmap[i]));
        }
        onDataDisplayListener.onDataDisplay(workspaces);
    }

}
