package com.example.mihovil.digitalnomad.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.example.mihovil.digitalnomad.R;
import com.example.mihovil.digitalnomad.models.Workspace;
import com.example.webservice.interfaces.ServiceResponse;
import com.example.webservice.interfaces.WebServiceCaller;
import com.example.webservice.interfaces.interfaces.OnServiceFinished;
import com.google.gson.Gson;


public class PopUpWorkspacesMessage extends DialogFragment implements View.OnClickListener, OnServiceFinished {

    WebServiceCaller wsc;
    String serviceStatus = "";
    Workspace workspace;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        workspace = new Gson().fromJson(getArguments().getString("record"), Workspace.class);
        View view = inflater.inflate(R.layout.fragment_pop_up_workspaces_message,null);
        view.findViewById(R.id.edit).setOnClickListener(this);
        view.findViewById(R.id.delete).setOnClickListener(this);
        builder.setView(view);
        return builder.create();
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.edit) {
            Fragment fragment = new EnterWorkspaceFragment();
            Bundle bundle = new Bundle();
            bundle.putString("email", getArguments().getString("email"));
            bundle.putString("id", getArguments().getString("id"));
            bundle.putString("name", getArguments().getString("name"));
            bundle.putString("country", getArguments().getString("country"));
            bundle.putString("town", getArguments().getString("town"));
            bundle.putString("adress", getArguments().getString("adress"));
            bundle.putString("description", getArguments().getString("description"));
            fragment.setArguments(bundle);
            android.support.v4.app.FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
            dismiss();

        } else if (i == R.id.delete) {
            Toast.makeText(getActivity(), "Uneseno", Toast.LENGTH_LONG).show();
            wsc = new WebServiceCaller(PopUpWorkspacesMessage.this);
            wsc.deleteWorkspace(getArguments().getString("id", getArguments().getString("id")));
            System.out.println("delete");
            serviceStatus = "delete";
            dismiss();

        }
    }

    @Override
    public void onServiceDone(Object response) {
        ServiceResponse returnValue = (ServiceResponse) response;
        if(serviceStatus == "delete"){
            if(returnValue.getReturnValue() == "1")
                RecyclerViewFragment.returningValue = "Record deleted succesfully";
            else
                RecyclerViewFragment.returningValue = "Record not deleted, try again!";
        }
        else{
            if(returnValue.getReturnValue() == "1")
                RecyclerViewFragment.returningValue = "Record edited successfully";
            else
                RecyclerViewFragment.returningValue = "Record not edited! Try again.";
        }
    }

    @Override
    public void onServiceFail(Object message) {
        RecyclerViewFragment.returningValue = "There was a problem with a web service. Please try again";
    }
}