package com.example.mihovil.digitalnomad.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mihovil.digitalnomad.R;
import com.example.mihovil.digitalnomad.models.Workspace;
import com.google.gson.Gson;

/**
 * A simple {@link Fragment} subclass.
 */
public class WorkspaceDetailsFragment extends Fragment {
    Workspace workspace = null;

    public WorkspaceDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (getArguments().getString("record") != null) {
            workspace = new Gson().fromJson(getArguments().getString("record"), Workspace.class);
        }
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_workspace_details, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        final TextView workspaceName = (TextView) view.findViewById(R.id.workspace_name_detail);
        final TextView workspaceCountry = (TextView) view.findViewById(R.id.workspace_country_detail);
        final TextView workspaceCity = (TextView) view.findViewById(R.id.workspace_city_detail);
        final TextView workspaceAdress = (TextView) view.findViewById(R.id.workspace_address_detail);
        final TextView workspaceAccomodation = (TextView) view.findViewById(R.id.workspace_accommodation);
        final TextView workspaceFood = (TextView) view.findViewById(R.id.workspace_food);
        final TextView workspaceInternet = (TextView) view.findViewById(R.id.workspace_wifi);
        final TextView workspaceActivities = (TextView) view.findViewById(R.id.workspace_social_activities);
        if(workspace != null){
            workspaceName.setText(workspace.name);
            workspaceCountry.setText(workspace.country);
            workspaceAdress.setText(workspace.adress);
            workspaceCity.setText(workspace.town);
        }
        super.onViewCreated(view, savedInstanceState);
    }
}
