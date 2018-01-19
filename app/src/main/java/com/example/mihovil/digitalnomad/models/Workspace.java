package com.example.mihovil.digitalnomad.models;

import android.graphics.Bitmap;

/**
 * Created by Ian on 11/17/2017.
 */

public class Workspace {
    public String id;
    public String name;
    public String description;
    public String adress;
    public String country;
    public String town;
    public String longitude;
    public String latitude;
    public String pictureUrl;
    public Bitmap workspaceImage;

    public Workspace(String id, String name, String description, String adress, String country, String town, String longitude, String latitude, String url, Bitmap image){
        this.name = name;
        this.id = id;
        this.description = description;
        this.adress = adress;
        this.country = country;
        this.longitude = longitude;
        this.latitude = latitude;
        this.pictureUrl = url;
        this.town = town;
        this.workspaceImage = image;
    }

    public Workspace(String name, String country){
        this.name = name;
        this.country = country;
    }
}
