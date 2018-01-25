package com.example.air.core;

import android.graphics.Bitmap;

import com.example.mihovil.digitalnomad.models.Workspace;
import com.example.webservice.interfaces.interfaces.OnServiceFinished;

import java.util.ArrayList;

/**
 * Created by Ian on 1/25/2018.
 */

public abstract class DataLoader implements OnServiceFinished{
    public ArrayList<Workspace> workspaces = new ArrayList<>();
    public Bitmap[] workspaceBitmaps;
    protected OnDataLoaded listener;

    public void loadData(OnDataLoaded dataLoaded){
        this.listener = dataLoaded;
    }

    public void getResponse(){
        listener.onDataLoaded(workspaces, workspaceBitmaps);
    }
}
