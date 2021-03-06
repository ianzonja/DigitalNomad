package com.example.mihovil.digitalnomad.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mihovil.digitalnomad.R;
import com.example.mihovil.digitalnomad.files.LoadingData;
import com.example.mihovil.digitalnomad.models.Workspace;
import com.example.webservice.interfaces.WebServiceCaller;
import com.example.webservice.interfaces.interfaces.OnServiceFinished;
import com.google.gson.Gson;

/**
 * A simple {@link Fragment} subclass.
 */
public class EnterWorkspaceFragment extends Fragment implements OnServiceFinished {
    String userMail = "";
    Workspace workspace = null;
    private RelativeLayout relativeLayout;
    private ProgressBar progressBar;

    public EnterWorkspaceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(getArguments().getString("email") != null)
            userMail = getArguments().getString("email");
        return inflater.inflate(R.layout.fragment_enter_workspace, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressBar = (ProgressBar) view.findViewById(R.id.progressBar_enter_workspace);
        relativeLayout = (RelativeLayout) view.findViewById(R.id.relative_layout_enter_workspace);

        final EditText workspaceName = (EditText) view.findViewById(R.id.add_workspace_name);
        final EditText workspaceCountry = (EditText) view.findViewById(R.id.add_workspace_country);
        final EditText workspaceCity = (EditText) view.findViewById(R.id.add_workspace_city);
        final EditText workspaceAdress = (EditText) view.findViewById(R.id.add_workspace_adress);
        final Button addWorkspaceButton = (Button) view.findViewById(R.id.add_Workspace_button);
        final EditText workspaceDescription = (EditText) view.findViewById(R.id.add_workspace_description);
        final CheckBox foodCheckbox = (CheckBox) view.findViewById(R.id.food_checkbox);
        final CheckBox accomodationCheckbox = (CheckBox) view.findViewById(R.id.acc_checkbox);
        final CheckBox wifiCkeckbox = (CheckBox) view.findViewById(R.id.wifi_checkbox);
        final CheckBox activityCheckbox = (CheckBox) view.findViewById(R.id.act_checkbox);

        if(getArguments().getString("description") != null && getArguments().getString("id") != null){
            workspaceName.setText(getArguments().getString("name"), TextView.BufferType.EDITABLE);
            workspaceCountry.setText(getArguments().getString("country"), TextView.BufferType.EDITABLE);
            workspaceCity.setText(getArguments().getString("town"), TextView.BufferType.EDITABLE);
            workspaceAdress.setText(getArguments().getString("adress"), TextView.BufferType.EDITABLE);
            workspaceDescription.setText(getArguments().getString("description"), TextView.BufferType.EDITABLE);
            addWorkspaceButton.setText("EDIT");
        }

        addWorkspaceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckEntry(workspaceName, workspaceCity, workspaceCountry, workspaceAdress)) {
                    WebServiceCaller wsc = new WebServiceCaller(EnterWorkspaceFragment.this);
                    String description = workspaceDescription.getText().toString();
                    String longitude = "0";
                    String latitude = "0";
                    System.out.println("unos:" + userMail + ", " + workspaceName.getText().toString() + ", " + description + "," + workspaceAdress.getText().toString() + "," + workspaceCountry.getText().toString() + "," + workspaceCity.getText().toString() + "," + longitude + "," + latitude);
                    if(getArguments().getString("description") != null && getArguments().getString("id") != null)
                        wsc.editWorkspace(getArguments().getString("id"), workspaceName.getText().toString(), workspaceDescription.getText().toString(), workspaceAdress.getText().toString(), workspaceCountry.getText().toString(), workspaceCity.getText().toString(), longitude, latitude, String.valueOf(accomodationCheckbox.isChecked()), String.valueOf(foodCheckbox.isChecked()), String.valueOf(wifiCkeckbox.isChecked()), String.valueOf(activityCheckbox.isChecked()));
                    else
                        wsc.addWorkspaceAsUser(userMail, workspaceName.getText().toString(), description, workspaceAdress.getText().toString(), workspaceCountry.getText().toString(), workspaceCity.getText().toString(), longitude, latitude, String.valueOf(accomodationCheckbox.isChecked()), String.valueOf(foodCheckbox.isChecked()), String.valueOf(wifiCkeckbox.isChecked()), String.valueOf(activityCheckbox.isChecked()));
                    LoadingData.EnableProgressBar(relativeLayout, progressBar);
                }else{
                    Toast.makeText(getActivity(), "Correct errors", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onServiceDone(Object response) {
        LoadingData.DisableProgressBar(relativeLayout, progressBar);
        Toast.makeText(getActivity(), "Saved", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onServiceFail(Object message) {
        LoadingData.DisableProgressBar(relativeLayout, progressBar);
        Toast.makeText(getActivity(), "Greska sa servisom", Toast.LENGTH_LONG).show();
    }

    private boolean CheckEntry(EditText workspaceName, EditText workspaceCity, EditText workspaceCountry, EditText workspaceAdress) {
        boolean success = true;

        if (workspaceName.getText().toString().isEmpty()) {
            workspaceName.setError("Enter workspace name");
            success = false;
        } else {
            workspaceName.setError(null);
        }
        if (workspaceCity.getText().toString().isEmpty()) {
            workspaceCity.setError("Enter workspace city");
            success = false;
        } else {
            workspaceCity.setError(null);
        }
        if (workspaceCountry.getText().toString().isEmpty()) {
            workspaceCountry.setError("Enter workspace country");
            success = false;
        } else {
            workspaceCountry.setError(null);
        }
        if (workspaceAdress.getText().toString().isEmpty()) {
            workspaceAdress.setError("Enter workspace adress");
            success = false;
        } else {
            workspaceAdress.setError(null);
        }

        return success;
    }
}