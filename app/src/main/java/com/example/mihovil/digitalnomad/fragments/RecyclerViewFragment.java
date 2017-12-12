package com.example.mihovil.digitalnomad.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mihovil.digitalnomad.Interface.LongPressListener;
import com.example.mihovil.digitalnomad.R;
import com.example.mihovil.digitalnomad.controller.RecyclerViewAdapter;
import com.example.mihovil.digitalnomad.models.Workspace;
import com.example.webservice.interfaces.WebServiceCaller;
import com.example.webservice.interfaces.WorkspaceValue;
import com.example.webservice.interfaces.interfaces.OnServiceFinished;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewFragment extends Fragment implements OnServiceFinished, LongPressListener{

    public static String returningValue;
    List<Workspace> workspaces;
    private RecyclerView rv;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_recycler_view, container, false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        view = getView();
        rv = (RecyclerView) view.findViewById(R.id.rv);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);
        workspaces = new ArrayList<Workspace>();
        WebServiceCaller wsc = new WebServiceCaller(RecyclerViewFragment.this);
        wsc.GetClientWorkspaces(getArguments().getString("email"));
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(workspaces, RecyclerViewFragment.this);
        rv.setAdapter(adapter);
    }

    @Override
    public void onServiceDone(Object response) {
        System.out.println("prazan response");
        List<WorkspaceValue> jsonResponse = (List<WorkspaceValue>) response;
        for(int i=0; i<jsonResponse.size(); i++){
            workspaces.add(new Workspace(jsonResponse.get(i).getName(), jsonResponse.get(i).getCountry()));
        }
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(workspaces,  RecyclerViewFragment.this);
        rv.setAdapter(adapter);
    }

    @Override
    public void onServiceFail(Object message) {
        System.out.print("nope");
    }

    @Override
    public void onLongPressAction(int position) {
        FragmentManager fm = getFragmentManager();
        PopUpWorkspacesMessage dialog = new PopUpWorkspacesMessage();
        dialog.show(getFragmentManager(), "sdfdsf");
        fm.executePendingTransactions();
        dialog.getDialog().setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                System.out.println("izaso i vratio vrijednost:" + returningValue);
            }
        });
    }
}
