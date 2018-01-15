package com.example.mihovil.digitalnomad.fragments;

import android.app.Fragment;
import android.app.LauncherActivity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mihovil.digitalnomad.R;
import com.example.mihovil.digitalnomad.controller.ReviewAdapter;
import com.example.mihovil.digitalnomad.models.Review;
import com.example.webservice.interfaces.WebServiceCaller;
import com.example.webservice.interfaces.interfaces.OnServiceFinished;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Davor on 14.1.2018..
 */

public class ShowReviewsFragment extends Fragment implements OnServiceFinished {

    List<Review> reviews;
    private RecyclerView rv;
    View view;
    Bundle valueBundle;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.activity_recycler_view, container, false);
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        view = getView();
        rv = (RecyclerView) view.findViewById(R.id.rv);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);

        reviews = new ArrayList<Review>();
        WebServiceCaller wsc = new WebServiceCaller(ShowReviewsFragment.this);
        wsc.showReviews(getArguments().getString("idWorkspace"));
    }

    public void onServiceDone(Object response){
        System.out.print("Error!");
    }

    public void onServiceFail(Object message){

    }
}
