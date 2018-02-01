package com.mihovil.advancedsearch;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.mihovil.digitalnomad.getWorkspaceData;
import com.example.mihovil.digitalnomad.Interface.OnDataLoaded;
import com.example.mihovil.digitalnomad.models.Workspace;

import java.util.ArrayList;

/**
 * Created by Mihovil on 20.1.2018..
 */

public class advancedSearchFragment extends Fragment implements View.OnClickListener, OnDataLoaded{

    private EditText countryName;
    private CheckBox accomodation;
    private CheckBox food;
    private CheckBox wifi;
    private CheckBox socialActivities;
    private CheckBox aZ;
    private Button filterOut;
    private getWorkspaceData dl;
    private Object activityObject;

    public advancedSearchFragment(getWorkspaceData dl) {
        this.dl = dl;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity mainMenu = (Activity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.napredno_pretrazivanjeee, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);
        filterOut.setOnClickListener(this);
    }

    private void initView(View view) {
        accomodation = (CheckBox) view.findViewById(R.id.checkbox_accommodation);
        food = (CheckBox) view.findViewById(R.id.checkbox_food);
        wifi = (CheckBox) view.findViewById(R.id.checkbox_wifi);
        socialActivities = (CheckBox) view.findViewById(R.id.checkbox_social_activities);
        aZ = (CheckBox) view.findViewById(R.id.checkbox_az);
        countryName = (EditText) view.findViewById(R.id.name_of_country);
        filterOut = (Button) view.findViewById(R.id.btnSearch);
    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btnSearch) {
            if(countryName.getText().toString().equals(""))
                countryName.setText("nodata");
            System.out.println("sta rece: " + countryName.getText().toString());
            AdvancedResult advancedResult = new AdvancedResult(countryName.getText().toString(),accomodation.isChecked(),food.isChecked(),wifi.isChecked(),socialActivities.isChecked(),aZ.isChecked());
            dl.loadData((Object) advancedResult);
        }
    }

    @Override
    public void onDataLoaded(ArrayList<Workspace> workspaces) {
        dl.displayData();
    }


}
