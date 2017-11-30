package com.example.mihovil.digitalnomad.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.mihovil.digitalnomad.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class EnterWorkspaceFragment extends Fragment {


    public EnterWorkspaceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
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
                //implement further logic to call webservice, and add workspace
            }
        });
    }
}
