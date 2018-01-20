package com.example.mihovil.digitalnomad.fragments;

import android.content.DialogInterface;
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
import com.example.mihovil.digitalnomad.Interface.OnPicturesRecived;
import com.example.mihovil.digitalnomad.R;
import com.example.mihovil.digitalnomad.controller.RecyclerViewAdapter;
import com.example.mihovil.digitalnomad.files.GetImage;
import com.example.mihovil.digitalnomad.models.Workspace;
import com.example.webservice.interfaces.WebServiceCaller;
import com.example.webservice.interfaces.WorkspaceValue;
import com.example.webservice.interfaces.interfaces.OnServiceFinished;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewFragment extends Fragment implements OnServiceFinished, LongPressListener, ClickListener, OnPicturesRecived{

    public static String returningValue;
    List<Workspace> workspaces;
    private RecyclerView rv;
    View view;
    List<WorkspaceValue> jsonResponse;
    Bundle valueBundle;
    FloatingActionButton fab;
    ArrayList<Bitmap> workspaceBitmapList;
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
        workspaceBitmapList = new ArrayList<>();
        fab = (FloatingActionButton) view.findViewById(R.id.add_new_fab);
        WebServiceCaller wsc = new WebServiceCaller(RecyclerViewFragment.this);
        if(getArguments().get("email") != null){
            wsc.GetClientWorkspaces(getArguments().getString("email"));
            fab.setVisibility(view.VISIBLE);
        }
        else{
            wsc.GetClientWorkspaces(getArguments().getString("email")); //ubuduce napravit novi upit ovisno o lokaciji
            fab.setVisibility(view.GONE);
        }

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(workspaces, RecyclerViewFragment.this, RecyclerViewFragment.this);
        rv.setAdapter(adapter);

        fab.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Fragment fragment = new EnterWorkspaceFragment();
                Bundle valueBundle = new Bundle();
                valueBundle.putString("email", getArguments().getString("email"));
                fragment.setArguments(valueBundle);
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null);
                ft.replace(R.id.content_frame, fragment);
                ft.commit();
            }
        });
    }

    @Override
    public void onServiceDone(Object response) {
        System.out.println("prazan response");
        jsonResponse = (List<WorkspaceValue>) response;
        workspaces.clear();
        ArrayList<String> urls = new ArrayList<>();
        for(int i=0; i<jsonResponse.size(); i++){
            urls.add("http://jospudjaatfoi.000webhostapp.com/Pictures/Workspaces/Screenshot_1.png");
        }
        GetImage getImage = new GetImage(urls, this);
        getImage.execute();
    }

    @Override
    public void onServiceFail(Object message) {
        System.out.print("nope");
    }

    @Override
    public void onLongPressAction(int position) {
        FragmentManager fm = getFragmentManager();
        valueBundle = new Bundle();
        valueBundle.putString("record", new Gson().toJson(workspaces.get(position)));
        PopUpWorkspacesMessage dialog = new PopUpWorkspacesMessage();
        dialog.setArguments(valueBundle);
        dialog.show(getFragmentManager(), "sdfdsf");
        fm.executePendingTransactions();
        dialog.getDialog().setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                WebServiceCaller wsc = new WebServiceCaller(RecyclerViewFragment.this);
                wsc.GetClientWorkspaces(getArguments().getString("email"));
            }
        });
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
    public void picturesReceived(Bitmap[] bitmap) {
        System.out.println("on image download");
        for(int i=0; i<jsonResponse.size(); i++){
            workspaces.add(new Workspace(jsonResponse.get(i).getIdworkspace(), jsonResponse.get(i).getName(), jsonResponse.get(i).getDescription(), jsonResponse.get(i).getAdress(), jsonResponse.get(i).getCountry(), jsonResponse.get(i).getTown(), jsonResponse.get(i).getLongitude(), jsonResponse.get(i).getLatitude(), jsonResponse.get(i).getPath(), bitmap[i]));
        }
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(workspaces,  RecyclerViewFragment.this, RecyclerViewFragment.this);
        rv.setAdapter(adapter);
    }

}
