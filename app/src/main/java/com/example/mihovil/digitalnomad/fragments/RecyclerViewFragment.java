package com.example.mihovil.digitalnomad.fragments;

import android.app.Activity;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mihovil.digitalnomad.R;
import com.example.mihovil.digitalnomad.controller.RecyclerViewAdapter;
import com.example.mihovil.digitalnomad.models.Workspace;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewFragment extends Fragment {

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
        Workspace ws = new Workspace("qweqwe", "44");
        Workspace ws2 = new Workspace("adasdsad", "44");
        workspaces = new ArrayList<Workspace>();
        workspaces.add(ws);
        workspaces.add(ws2);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(workspaces);
        rv.setAdapter(adapter);
    }
}
