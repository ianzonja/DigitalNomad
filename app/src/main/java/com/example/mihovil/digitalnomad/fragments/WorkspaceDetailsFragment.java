package com.example.mihovil.digitalnomad.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mihovil.digitalnomad.R;
import com.example.mihovil.digitalnomad.models.Workspace;
import com.example.webservice.interfaces.WebServiceCaller;
import com.example.webservice.interfaces.WorkspaceDetailsResponse;
import com.example.webservice.interfaces.interfaces.OnServiceFinished;
import com.google.gson.Gson;

/**
 * A simple {@link Fragment} subclass.
 */
public class WorkspaceDetailsFragment extends Fragment implements OnServiceFinished {
    Workspace workspace = null;
    WorkspaceDetailsResponse serviceResponse = null;
    TextView workspaceName;
    TextView workspaceCountry;
    TextView workspaceCity;
    TextView workspaceAdress;
    TextView workspaceAccomodation;
    TextView workspaceFood;
    TextView workspaceInternet;
    TextView workspaceActivities;
    TextView workspaceRating;
    TextView workspaceDescription;

    public WorkspaceDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (getArguments().getString("record") != null) {
            workspace = new Gson().fromJson(getArguments().getString("record"), Workspace.class);
        }
        System.out.println("id: " + workspace.id);
        WebServiceCaller wsc = new WebServiceCaller(WorkspaceDetailsFragment.this);
        wsc.getWorkspaceDetails(workspace.id);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_workspace_details, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        workspaceName = (TextView) view.findViewById(R.id.workspace_name_detail);
        workspaceCountry = (TextView) view.findViewById(R.id.workspace_country_detail);
        workspaceCity = (TextView) view.findViewById(R.id.workspace_city_detail);
        workspaceAdress = (TextView) view.findViewById(R.id.workspace_address_detail);
        workspaceAccomodation = (TextView) view.findViewById(R.id.workspace_accommodation);
        workspaceFood = (TextView) view.findViewById(R.id.workspace_food);
        workspaceInternet = (TextView) view.findViewById(R.id.workspace_wifi);
        workspaceActivities = (TextView) view.findViewById(R.id.workspace_social_activities);
        workspaceRating = (TextView) view.findViewById(R.id.workspace_rating);
        workspaceDescription = (TextView) view.findViewById(R.id.workspace_description_detail);
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onServiceDone(Object response) {
        serviceResponse = (WorkspaceDetailsResponse) response;
        System.out.println(serviceResponse.getDetails().getName());
        workspaceName.setText(serviceResponse.getDetails().getName());
        workspaceCountry.setText(serviceResponse.getDetails().getCountry());
        workspaceCity.setText(serviceResponse.getDetails().getTown());
        workspaceAdress.setText(serviceResponse.getDetails().getAdress());
        workspaceRating.setText("sadasd");
        workspaceDescription.setText(serviceResponse.getDetails().getDescription());
        if (serviceResponse.getServices().getAccomodation() == true)
            workspaceAccomodation.setBackgroundColor(getResources().getColor(R.color.green));
       // else
         //   workspaceAccomodation.setBackgroundColor(R.color.red);
       // if(serviceResponse.getServices().getWifi() == true)
        //    workspaceFood.setBackgroundColor(R.color.green);
        //else
          //  workspaceFood.setBackgroundColor(R.color.red);
    }

    @Override
    public void onServiceFail(Object message) {

    }
}
