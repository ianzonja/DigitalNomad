package com.example.mihovil.digitalnomad.fragments;


import android.app.Activity;
import android.app.AlertDialog;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mihovil.digitalnomad.Interface.OnImageDownload;
import com.example.mihovil.digitalnomad.R;
import com.example.mihovil.digitalnomad.files.GetImage;
import com.example.mihovil.digitalnomad.files.ImageSaver;
import com.example.mihovil.digitalnomad.files.UserToJsonFile;
import com.example.webservice.interfaces.ServiceResponse;
import com.example.webservice.interfaces.WebServiceCaller;
import com.example.webservice.interfaces.interfaces.OnServiceFinished;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Mihovil on 17.11.2017..
 */

public class UserProfileFragment extends Fragment implements OnImageDownload, OnServiceFinished, DialogInterface.OnClickListener, View.OnClickListener {

    private ImageView profilePicture;
    private String name;
    private String email;
    private String url;
    private String rank;
    private WebServiceCaller webServiceCallerForActivity;
    private GetImage getImage;

    WebServiceCaller wsc;
    OnImageDownload listener;

    private EditText txtName;
    private EditText txtEmail;
    private TextView userRank;
    private View changedPassword;

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
        webServiceCallerForActivity = new WebServiceCaller((OnServiceFinished) mainMenu);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initFragment(view);

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
                    profilePicture.setImageBitmap(GetImage.getRoundedCornerBitmap(profileBitmap));
                    listener.onImageDownload(profileBitmap);
                    new ImageSaver(getContext()).
                            setFileName("ProfilePic.png").
                            setDirectoryName("ProfilePicture").
                            save(profileBitmap);

                    wsc.UploadImage(email, '"' + newBitmap + '"');
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            Toast.makeText(getContext(), "No image selected.", Toast.LENGTH_LONG).show();
        }
    }

    private void initFragment(View view){
        changedPassword = view.findViewById(R.id.user_profile_txtChangePassword);
        profilePicture = (ImageView) view.findViewById(R.id.profilePic);
        txtName = (EditText) view.findViewById(R.id.user_profile_txtName);
        txtEmail = (EditText) view.findViewById(R.id.user_profile_txtEmail);
        userRank = (TextView) view.findViewById(R.id.userRank);

        initListeners();
    }
    private void initListeners(){
        changedPassword.setOnClickListener(this);
        profilePicture.setOnClickListener(this);
        wsc = new WebServiceCaller(UserProfileFragment.this);
        getImage = new GetImage(this);

        checkUserData();
    }

    private Bitmap loadInternalMemory(){
        Bitmap profileBitmap = new ImageSaver(getContext()).
                setFileName("ProfilePic.png").
                setDirectoryName("ProfilePicture").
                load();
        return profileBitmap;
    }

    private void setView(){
        try {
            profilePicture.setImageBitmap(GetImage.getRoundedCornerBitmap(loadInternalMemory()));
        }catch(Exception e){}
        txtEmail.setText(email);
        txtName.setText(name);
        userRank.setText(rank);
    }

    private void checkUserData(){
        JSONObject object = null;
        String jsonString = UserToJsonFile.ReadFromFile(getContext());
        try {
            object = new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            if (object != null) {
                name = object.getString("name");
                email = object.getString("email");
                url = object.getString("url");
                rank = object.getString("rank");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //dohvacanje slike sa prosljedenog urla
        getImage = new GetImage(this);
        getImage.setUrl(url);
        getImage.execute();

        setView();
    }

    @Override
    public void onImageDownload(Bitmap image) {
        profilePicture.setImageBitmap(GetImage.getRoundedCornerBitmap(image));
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

    @Override
    public void onServiceDone(Object response) {
        ServiceResponse isSuccess = (ServiceResponse) response;
        if (isSuccess.isPostoji()) {
            Toast.makeText(getContext(), "Image uploaded to server", Toast.LENGTH_LONG).show();
            webServiceCallerForActivity.GetUserProfile(email);
        } else {
            Log.d("TAG", "ispostoje = false");
            Toast.makeText(getContext(), "Something went wrong, try again later", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onServiceFail(Object message) {
        Toast.makeText(getContext(), "Picture failed to upload", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.profilePic: {
                DialogInterface.OnClickListener dialogClickListener = UserProfileFragment.this;

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Select image from gallery?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();

                break;

            }
            case R.id.user_profile_txtChangePassword:{
                Fragment fragment = new EditUserProfileFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, fragment);
                ft.commit();
                break;
            }

        }

    }
}
