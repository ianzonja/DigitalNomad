package com.example.webservice.interfaces;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Ian on 1/16/2018.
 */

public class WorkspaceDetailsResponse {
    @SerializedName("details")
    @Expose
    private Details details;
    @SerializedName("services")
    @Expose
    private Services services;
    @SerializedName("paths")
    @Expose
    private List<Path> paths = null;

    public Details getDetails() {
        return details;
    }

    public void setDetails(Details details) {
        this.details = details;
    }

    public Services getServices() {
        return services;
    }

    public void setServices(Services services) {
        this.services = services;
    }

    public List<Path> getPaths() {
        return paths;
    }

    public void setPaths(List<Path> paths) {
        this.paths = paths;
    }
}
