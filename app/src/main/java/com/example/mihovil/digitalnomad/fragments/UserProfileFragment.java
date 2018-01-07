package com.example.mihovil.digitalnomad.fragments;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mihovil.digitalnomad.MainMenuActivity;
import com.example.mihovil.digitalnomad.files.ImageSaver;
import com.example.mihovil.digitalnomad.files.UserToJsonFile;
import com.example.mihovil.digitalnomad.Interface.OnImageDownload;
import com.example.mihovil.digitalnomad.R;
import com.example.mihovil.digitalnomad.files.GetImage;
import com.example.webservice.interfaces.interfaces.OnServiceFinished;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Mihovil on 17.11.2017..
 */

public class UserProfileFragment extends Fragment implements OnImageDownload, OnServiceFinished, DialogInterface.OnClickListener {

    ImageView profilePicture;
    String name;
    String email;
    String url;
    OnImageDownload listener;

    private static final int PICK_IMAGE = 282;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.user_profile_fragment, container, false);
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity mainMenu = (Activity) context;
        listener = (OnImageDownload) mainMenu;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        View changePassword = view.findViewById(R.id.user_profile_txtChangePassword);
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new EditUserProfileFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, fragment);
                ft.commit();
            }
        });
        listener = (OnImageDownload) getActivity();
        profilePicture = (ImageView) view.findViewById(R.id.profilePic);
        Bitmap profileBitmap = new ImageSaver(getContext()).
                setFileName("ProfilePic.png").
                setDirectoryName("ProfilePicture").
                load();
        profilePicture.setImageBitmap(profileBitmap);

        profilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogInterface.OnClickListener dialogClickListener = UserProfileFragment.this;

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Select image from gallery?").setPositiveButton("Yes",dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();

            }
        });


        EditText txtName = (EditText) view.findViewById(R.id.user_profile_txtName);
        EditText txtEmail = (EditText) view.findViewById(R.id.user_profile_txtEmail);

        JSONObject object = null;
        String jsonString = UserToJsonFile.ReadFromFile(getContext());
        try {
            object = new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            name = object.getString("name");
            email = object.getString("email");
            url = object.getString("url");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        txtEmail.setText(email);
        txtName.setText(name);

        GetImage getImage = new GetImage(url, this);
        getImage.execute();

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
                    profilePicture.setImageBitmap(profileBitmap);
                    new ImageSaver(getContext()).
                            setFileName("ProfilePic.png").
                            setDirectoryName("ProfilePicture").
                            save(profileBitmap);

                    listener.onImageDownload(profileBitmap);
                 /*   WebServiceCaller wsc = new WebServiceCaller(UserProfileFragment.this);
                    wsc.UploadImage(getEncoded64ImageStringFromBitmap(bitmap), email);*/
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            Toast.makeText(getContext(), "No image selected.", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onImageDownload(Bitmap image) {
        if (profilePicture != null) {
            profilePicture.setImageBitmap(image);
            listener.onImageDownload(image);
            try {
                new ImageSaver(getContext()).
                        setFileName("ProfilePic.png").
                        setDirectoryName("ProfilePicture").
                        save(image);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onServiceDone(Object response) {
        Toast.makeText(getContext(), "Picture uploaded", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onServiceFail(Object message) {
        Toast.makeText(getContext(), "Picture failed to upload", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which){
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
}
