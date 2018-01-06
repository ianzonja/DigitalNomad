package com.example.mihovil.digitalnomad.fragments;


import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mihovil.digitalnomad.files.UserToJsonFile;
import com.example.mihovil.digitalnomad.Interface.OnImageDownload;
import com.example.mihovil.digitalnomad.R;
import com.example.mihovil.digitalnomad.files.GetImage;


import org.json.JSONException;
import org.json.JSONObject;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Mihovil on 17.11.2017..
 */

public class UserProfileFragment extends Fragment implements OnImageDownload {

    ImageView profilePicture;
    String name;
    String email;
    String url;

    private static final int PICK_IMAGE = 282;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.user_profile_fragment, container, false);
        return rootView;
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

        profilePicture = (ImageView) view.findViewById(R.id.profilePic);
        profilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
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
                Toast.makeText(getContext(),"No image selected.",Toast.LENGTH_LONG).show();
            }
            else{
                Uri selectedImageUri = data.getData();
                profilePicture.setImageURI(selectedImageUri);
                }
        }
        else{
            Toast.makeText(getContext(),"No image selected.",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onImageDownload(Bitmap image) {
        if (profilePicture != null) {
            profilePicture.setImageBitmap(image);
        }
    }

}
