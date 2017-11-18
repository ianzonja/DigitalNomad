package com.example.mihovil.digitalnomad;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.mihovil.digitalnomad.controller.RecyclerViewAdapter;
import com.example.mihovil.digitalnomad.models.Workspace;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewActivity extends Activity {

    List<Workspace> workspaces;
    private RecyclerView rv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        rv = (RecyclerView)findViewById(R.id.rv);
            LinearLayoutManager llm = new LinearLayoutManager(this);
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
