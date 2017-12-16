package com.example.mihovil.digitalnomad.fragments;

import android.app.Dialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
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

import java.io.Serializable;


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
        switch(view.getId()){
            case R.id.edit:
                Fragment fragment = new EnterWorkspaceFragment();
                Bundle bundle = new Bundle();
                bundle.putString("record", getArguments().getString("record"));
                fragment.setArguments(bundle);
                android.support.v4.app.FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.content_frame, fragment);
                ft.commit();
                dismiss();
                break;
            case R.id.delete:
                Toast.makeText(getActivity(), "Uneseno", Toast.LENGTH_LONG).show();
                wsc = new WebServiceCaller(PopUpWorkspacesMessage.this);
                wsc.deleteWorkspace(workspace.id);
                System.out.println("delete");
                serviceStatus = "delete";
                dismiss();
                break;
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