package com.example.mihovil.digitalnomad;

import com.example.map.LocationWorkspaceData;
import com.example.mihovil.digitalnomad.Interface.OnSelectedModule;
import com.example.mihovil.digitalnomad.Interface.OnDataDisplay;
import com.mihovil.advancedsearch.AdvancedSearchWorkspaceData;

/**
 * Created by ivysl on 4.2.2018..
 */

public class SearchFactory {
    public static OnSelectedModule getSearchModule(String choice){
        switch (choice){
            case "advanced":{
                return new AdvancedSearchWorkspaceData();
            }
            case "map":{
                return new LocationWorkspaceData();
            }
        }
        return null;
    }
}
