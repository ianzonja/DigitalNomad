package com.example.mihovil.digitalnomad.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.example.mihovil.digitalnomad.R;


public class PopUpWorkspacesMessage extends DialogFragment implements View.OnClickListener{

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
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
                System.out.println("edit");
                Toast.makeText(getActivity(), "Uneseno", Toast.LENGTH_LONG).show();
                RecyclerViewFragment.returningValue = "edit";
                dismiss();
                break;
            case R.id.delete:
                Toast.makeText(getActivity(), "Uneseno", Toast.LENGTH_LONG).show();
                System.out.println("delete");
                RecyclerViewFragment.returningValue = "delete";
                dismiss();
                break;
        }
    }
}