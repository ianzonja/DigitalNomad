package com.example.mihovil.digitalnomad.fragments;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mihovil.digitalnomad.Interface.OnImageDownload;
import com.example.mihovil.digitalnomad.R;
import com.example.mihovil.digitalnomad.files.GetImage;
import com.example.mihovil.digitalnomad.models.Workspace;
import com.example.webservice.interfaces.Path;
import com.example.webservice.interfaces.WebServiceCaller;
import com.example.webservice.interfaces.WorkspaceDetailsResponse;
import com.example.webservice.interfaces.interfaces.OnServiceFinished;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class WorkspaceDetailsFragment extends Fragment implements OnServiceFinished, OnImageDownload{
    Workspace workspace = null;
    WorkspaceDetailsResponse serviceResponse = null;
    TextView workspaceName;
    TextView workspaceCountry;
    TextView workspaceCity;
    TextView workspaceAdress;
    TextView workspaceAccomodation;
    TextView workspaceFood;
    TextView workspaceInternet;
    TextView workspaceActivities;
    TextView workspaceRating;
    TextView workspaceDescription;
    Button workspaceReviews;
    ImageView workspaceImage;
    String id;
    List<Path> paths;

    String worskpaceUserEmail;

    public WorkspaceDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (getArguments().getString("id") != null) {
            id = getArguments().getString("id");
        }
        System.out.println("id: " + id);
        WebServiceCaller wsc = new WebServiceCaller(WorkspaceDetailsFragment.this);
        wsc.getWorkspaceDetails(id);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_workspace_details, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        workspaceName = (TextView) view.findViewById(R.id.workspace_name_detail);
        workspaceCountry = (TextView) view.findViewById(R.id.workspace_country_detail);
        workspaceCity = (TextView) view.findViewById(R.id.workspace_city_detail);
        workspaceAdress = (TextView) view.findViewById(R.id.workspace_address_detail);
        workspaceAccomodation = (TextView) view.findViewById(R.id.workspace_accommodation);
        workspaceFood = (TextView) view.findViewById(R.id.workspace_food);
        workspaceInternet = (TextView) view.findViewById(R.id.workspace_wifi);
        workspaceActivities = (TextView) view.findViewById(R.id.workspace_social_activities);
        workspaceRating = (TextView) view.findViewById(R.id.workspace_rating);
        workspaceDescription = (TextView) view.findViewById(R.id.workspace_description_detail);
        workspaceReviews = (Button) view.findViewById(R.id.workspace_reviews_detail);
        workspaceImage = (ImageView) view.findViewById(R.id.workspace_image);
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.button_reservation);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeReservation();

            }
        });
        workspaceReviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new ShowReviewsFragment();
                Bundle valueBundle = new Bundle();
                valueBundle.putString("idWorkspace", id);
                fragment.setArguments(valueBundle);
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, fragment);
                ft.commit();
            }
        });
        workspaceImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new ShowWorkspaceGallery();
                Bundle valueBundle = new Bundle();
                ArrayList<String> urls = new ArrayList<String>();
                for(int i = 0; i<paths.size(); i++){
                    urls.add(paths.get(i).getPath());
                }
                valueBundle.putStringArrayList("urls", urls);
                fragment.setArguments(valueBundle);
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, fragment);
                ft.commit();
            }
        });
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onServiceDone(Object response) {

        serviceResponse = (WorkspaceDetailsResponse) response;
        Log.d("TAG","name" +serviceResponse.getDetails().getName());
        Log.d("TAG","country"+serviceResponse.getDetails().getCountry());
        Log.d("TAG","town"+serviceResponse.getDetails().getTown());
        Log.d("TAG","adress"+serviceResponse.getDetails().getTown());
        Log.d("TAG","avg"+serviceResponse.getDetails().getAveragegrade());
        Log.d("TAG","description" +serviceResponse.getDetails().getDescription());
        Log.d("TAG","email" +serviceResponse.getDetails().getEmail());

        workspaceName.setText(serviceResponse.getDetails().getName());
        workspaceCountry.setText(serviceResponse.getDetails().getCountry());
        workspaceCity.setText(serviceResponse.getDetails().getTown());
        workspaceAdress.setText(serviceResponse.getDetails().getAdress());
        workspaceRating.setText(serviceResponse.getDetails().getAveragegrade());
        workspaceDescription.setText(serviceResponse.getDetails().getDescription());
        worskpaceUserEmail = serviceResponse.getDetails().getEmail();
        paths = serviceResponse.getPaths();
        if(paths.get(0).getPath().equals("NULL")){
            System.out.println("null vridnost!");
        }
        else{
            GetImage getImage = new GetImage(this);
            getImage.setUrl(paths.get(0).getPath().replace("https", "http"));
            getImage.execute();
            System.out.println("url: " + paths.get(0).getPath().replace("https", "http"));
        }

        if(serviceResponse.getServices().getWifi())
            workspaceInternet.setBackgroundColor(getResources().getColor(R.color.green));
        else
            workspaceInternet.setBackgroundColor(getResources().getColor(R.color.red));

        if (serviceResponse.getServices().getAccomodation())
            workspaceAccomodation.setBackgroundColor(getResources().getColor(R.color.green));
        else
            workspaceAccomodation.setBackgroundColor(getResources().getColor(R.color.red));

        if(serviceResponse.getServices().getFood())
            workspaceFood.setBackgroundColor(getResources().getColor(R.color.green));
        else
            workspaceFood.setBackgroundColor(getResources().getColor(R.color.red));

        if(serviceResponse.getServices().getActivities())
            workspaceActivities.setBackgroundColor(getResources().getColor(R.color.green));
        else
            workspaceActivities.setBackgroundColor(getResources().getColor(R.color.red));
    }

    @Override
    public void onServiceFail(Object message) {
        Log.d("TAG","tu");
    }

    //metoda koja otvara gmail racun i prosljeduje odredene parametre
    private void makeReservation(){
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", worskpaceUserEmail, null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Digital Nomad");
        emailIntent.putExtra(Intent.EXTRA_TEXT,"I would like to make a reservation on Your Workspace");
        startActivity(Intent.createChooser(emailIntent, null));
    }


    @Override
    public void onImageDownload(Bitmap image) {
        System.out.println("skinuo sliku");
        workspaceImage.setImageBitmap(image);
    }
}
