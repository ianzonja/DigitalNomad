package com.example.webservice.interfaces;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ian on 1/16/2018.
 */

public class Services {
    @SerializedName("accomodation")
    @Expose
    private Boolean accomodation;
    @SerializedName("wifi")
    @Expose
    private Boolean wifi;

    public Boolean getAccomodation() {
        return accomodation;
    }

    public void setAccomodation(Boolean accomodation) {
        this.accomodation = accomodation;
    }

    public Boolean getWifi() {
        return wifi;
    }

    public void setWifi(Boolean wifi) {
        this.wifi = wifi;
    }

    public Boolean getFood() {
        return food;
    }

    public void setFood(Boolean food) {
        this.food = food;
    }

    @SerializedName("food")
    @Expose
    private Boolean food;

    public Boolean getActivities() {
        return activities;
    }

    public void setActivities(Boolean activities) {
        this.activities = activities;
    }

    @SerializedName("activities")
    @Expose
    private Boolean activities;
}
