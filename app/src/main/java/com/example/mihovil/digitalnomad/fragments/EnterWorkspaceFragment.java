package com.example.mihovil.digitalnomad.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mihovil.digitalnomad.R;
import com.example.webservice.interfaces.ServiceResponse;
import com.example.webservice.interfaces.WebServiceCaller;
import com.example.webservice.interfaces.interfaces.OnServiceFinished;

/**
 * A simple {@link Fragment} subclass.
 */
public class EnterWorkspaceFragment extends Fragment implements OnServiceFinished {
    String userMail = "";

    public EnterWorkspaceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(getArguments() != null)
            userMail = getArguments().getString("email");
        return inflater.inflate(R.layout.fragment_enter_workspace, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final EditText workspaceName = (EditText)  view.findViewById(R.id.add_workspace_name);
        final EditText workspaceCountry = (EditText) view.findViewById(R.id.add_workspace_country);
        final EditText workspaceCity = (EditText) view.findViewById(R.id.add_workspace_city);
        final EditText workspaceAdress = (EditText) view.findViewById(R.id.add_workspace_adress);
        final Button addWorkspaceButton = (Button) view.findViewById(R.id.add_Workspace_button);

        addWorkspaceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WebServiceCaller wsc = new WebServiceCaller(EnterWorkspaceFragment.this);
                String description = "qwert";
                String longitude = "0";
                String latitude = "0";
                System.out.println("unos:" + userMail + ", " + workspaceName.getText().toString() + ", " + description + "," + workspaceAdress.getText().toString() + "," + workspaceCountry.getText().toString() + "," + workspaceCity.getText().toString() + "," + longitude + "," + latitude);
                wsc.addWorkspaceAsUser(userMail, workspaceName.getText().toString(), description, workspaceAdress.getText().toString(), workspaceCountry.getText().toString(), workspaceCity.getText().toString(), longitude, latitude);
            }
        });
    }

    @Override
    public void onServiceDone(Object response) {
        ServiceResponse addWorkspace = (ServiceResponse) response;
        Toast.makeText(getActivity(), "Uneseno", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onServiceFail(Object message) {
        Toast.makeText(getActivity(), "Greska sa servisom", Toast.LENGTH_LONG).show();
    }
}
