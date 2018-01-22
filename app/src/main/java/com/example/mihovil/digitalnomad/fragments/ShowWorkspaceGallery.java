package com.example.mihovil.digitalnomad.fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mihovil.digitalnomad.Interface.OnPicturesRecived;
import com.example.mihovil.digitalnomad.R;
import com.example.mihovil.digitalnomad.controller.GalleryAdapter;
import com.example.mihovil.digitalnomad.files.GetImage;
import com.example.webservice.interfaces.Review;
import com.example.webservice.interfaces.ServiceResponse;
import com.example.webservice.interfaces.WebServiceCaller;
import com.example.webservice.interfaces.interfaces.OnServiceFinished;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShowWorkspaceGallery extends Fragment implements OnPicturesRecived, DialogInterface.OnClickListener, OnServiceFinished{

    List<Review> reviews;
    private RecyclerView rv;
    View view;
    Bundle valueBundle;
    FloatingActionButton fab;
    ArrayList<String> urls;
    private static final int PICK_IMAGE = 989;


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
                DialogInterface.OnClickListener dialogClickListener = ShowWorkspaceGallery.this;

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Select image from gallery?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
            }
        });
    }
    @Override
    public void picturesReceived(Bitmap[] bitmap) {
        System.out.println("size: " + bitmap.length);
        GalleryAdapter ga = new GalleryAdapter(bitmap);
        rv.setAdapter(ga);
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK) {
            if (data == null) {
                Toast.makeText(getContext(), "No image selected.", Toast.LENGTH_LONG).show();
            } else {
                Uri selectedImageUri = data.getData();
                try {
                    Bitmap profileBitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImageUri);
                    String newBitmap = GetImage.getEncoded64ImageStringFromBitmap(profileBitmap);


                    WebServiceCaller wsc = new WebServiceCaller(ShowWorkspaceGallery.this);
                    wsc.uploadWorkspaceImage(getArguments().getString("id"), newBitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            Toast.makeText(getContext(), "No image selected.", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        switch (i) {
            case DialogInterface.BUTTON_POSITIVE:
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
                break;

            case DialogInterface.BUTTON_NEGATIVE:
                Toast.makeText(getContext(), "Request canceled", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onServiceDone(Object response) {
            Toast.makeText(getContext(), "Image uploaded to server", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onServiceFail(Object message) {
        Toast.makeText(getContext(), "Image failed to upload", Toast.LENGTH_LONG).show();
    }
}
