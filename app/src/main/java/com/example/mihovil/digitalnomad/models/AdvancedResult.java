package com.example.mihovil.digitalnomad.models;

/**
 * Created by Mihovil on 21.1.2018..
 */

public class AdvancedResult {
    private String country;
    private boolean accomodation;
    private boolean food;
    private boolean activities;
    private boolean wifi;
    private boolean aZ;

    public AdvancedResult(String country, boolean accomodation, boolean food, boolean activities, boolean wifi, boolean aZ) {
        this.country = country;
        this.accomodation = accomodation;
        this.food = food;
        this.activities = activities;
        this.wifi = wifi;
        this.aZ = aZ;
    }

    public String getCountry() {
        return country;
    }

    public boolean getAccomodation() {
        return accomodation;
    }

    public boolean getFood() {
        return food;
    }

    public boolean getActivities() {
        return activities;
    }

    public boolean getWifi() {
        return wifi;
    }

    public boolean getaZ() {
        return aZ;
    }
}
