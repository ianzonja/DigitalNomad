package com.example.webservice.interfaces;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ian on 12/5/2017.
 */

public class WorkspaceValue {
    public String getIdworkspace() {
        return idworkspace;
    }

    public void setIdworkspace(String idworkspace) {
        this.idworkspace = idworkspace;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @SerializedName("idworkspace")
    @Expose
    private String idworkspace;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("adress")
    @Expose
    private String adress;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("town")
    @Expose
    private String town;
    @SerializedName("longitude")
    @Expose
    private String longitude;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    @SerializedName("latitude")
    @Expose
    private String latitude;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @SerializedName("path")
    @Expose
    private String path;
}
