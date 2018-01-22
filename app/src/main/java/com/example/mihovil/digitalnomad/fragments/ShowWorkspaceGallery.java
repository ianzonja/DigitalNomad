package com.example.mihovil.digitalnomad.fragments;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mihovil.digitalnomad.Interface.OnPicturesRecived;
import com.example.mihovil.digitalnomad.R;
import com.example.mihovil.digitalnomad.controller.GalleryAdapter;
import com.example.mihovil.digitalnomad.files.GetImage;
import com.example.webservice.interfaces.Review;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShowWorkspaceGallery extends Fragment implements OnPicturesRecived{

    List<Review> reviews;
    private RecyclerView rv;
    View view;
    Bundle valueBundle;
    FloatingActionButton fab;
    ArrayList<String> urls;

    public ShowWorkspaceGallery() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_recycler_view, container, false);
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        view = getView();
        rv = (RecyclerView) view.findViewById(R.id.rv);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);
        urls = new ArrayList<String>();
        urls = getArguments().getStringArrayList("urls");
        GetImage getImage = new GetImage(urls, this);
        getImage.execute();
        fab = (FloatingActionButton) view.findViewById(R.id.add_new_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //implementirati dodavanje slike
            }
        });
    }
    @Override
    public void picturesReceived(Bitmap[] bitmap) {
        System.out.println("size: " + bitmap.length);
        GalleryAdapter ga = new GalleryAdapter(bitmap);
        rv.setAdapter(ga);
    }
}
