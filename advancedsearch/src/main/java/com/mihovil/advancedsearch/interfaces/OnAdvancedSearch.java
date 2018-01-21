package com.mihovil.advancedsearch.interfaces;

import java.io.Serializable;

/**
 * Created by Mihovil on 20.1.2018..
 */

public interface OnAdvancedSearch {
    void onAdvancedResult(String countryName,boolean accomodation, boolean food, boolean wifi, boolean socialActivities, boolean aZ);
}
