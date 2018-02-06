package com.example.mihovil.digitalnomad.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mihovil.digitalnomad.Interface.ClickListener;
import com.example.mihovil.digitalnomad.Interface.LongPressListener;
import com.example.mihovil.digitalnomad.R;
import com.example.mihovil.digitalnomad.controller.RecyclerViewAdapter;
import com.example.mihovil.digitalnomad.models.Workspace;
import com.example.webservice.interfaces.WorkspaceValue;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewFragment extends Fragment implements LongPressListener, ClickListener{

    public static String returningValue;
    List<Workspace> workspacesList;
    private RecyclerView rv;
    View view;
    List<WorkspaceValue> jsonResponse;
    Bundle valueBundle;
    FloatingActionButton fab;
    ArrayList<Bitmap> workspaceBitmapList;
    List<Workspace> workspaces;

    public RecyclerViewFragment(List<Workspace> workspaces){
        this.workspaces = workspaces;
    }
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
        workspacesList = new ArrayList<Workspace>();
        workspaceBitmapList = new ArrayList<>();
        fab = (FloatingActionButton) view.findViewById(R.id.add_new_fab);
        if(getArguments().getBoolean("showFab") == false)
            fab.setVisibility(view.GONE);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(workspaces, RecyclerViewFragment.this, RecyclerViewFragment.this);
        rv.setAdapter(adapter);

        fab.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Fragment fragment = new EnterWorkspaceFragment();
                Bundle valueBundle = new Bundle();
                valueBundle.putString("Email", getArguments().getString("Email"));
                fragment.setArguments(valueBundle);
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, fragment);
                ft.commit();
            }
        });
        ArrayList<String> urls = new ArrayList<>();
        for(int i = 0; i< workspaces.size(); i++) {
            urls.add(workspaces.get(i).pictureUrl);
        };
        adapter = new RecyclerViewAdapter(workspaces, RecyclerViewFragment.this, RecyclerViewFragment.this);
        rv.setAdapter(adapter);
    }

    @Override
    public void onLongPressAction(int position) {
        if(getArguments().getString("Email") != null){
            System.out.println("uso u longpress");
            FragmentManager fm = getFragmentManager();
            valueBundle = new Bundle();
            valueBundle.putString("id", workspaces.get(position).id);
                        valueBundle.putString("name", workspaces.get(position).name);
            valueBundle.putString("country", workspaces.get(position).country);
            valueBundle.putString("town", workspaces.get(position).town);
            valueBundle.putString("adress", workspaces.get(position).adress);
            valueBundle.putString("description", workspaces.get(position).description);
            valueBundle.putString("email", getArguments().getString("Email"));
            PopUpWorkspacesMessage dialog = new PopUpWorkspacesMessage();
            dialog.setArguments(valueBundle);
            dialog.show(getFragmentManager(), "sdfdsf");
            fm.executePendingTransactions();
        }
    }

    @Override
    public void onPressAction(int position){
        System.out.println("eee klik!");
        valueBundle = new Bundle();
        valueBundle.putString("id", workspaces.get(position).id);
        Fragment fragment = new WorkspaceDetailsFragment();
        fragment.setArguments(valueBundle);
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, fragment);
        ft.commit();
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }
}
