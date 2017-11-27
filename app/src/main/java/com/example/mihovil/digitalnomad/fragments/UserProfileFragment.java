package com.example.mihovil.digitalnomad.fragments;


import android.media.Image;
import android.os.Bundle;
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

import com.example.mihovil.digitalnomad.R;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

import entities.User;

/**
 * Created by Mihovil on 17.11.2017..
 */

public class UserProfileFragment extends Fragment {


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

        ImageView profilePicture = (ImageView) view.findViewById(R.id.profilePic);
        EditText txtName = (EditText) view.findViewById(R.id.user_profile_txtName);
        EditText txtEmail = (EditText) view.findViewById(R.id.user_profile_txtEmail);

        if (SQLite.select().from(User.class).queryList().isEmpty()) {
            User user = new User(0, "miho", "miho@.com", "neki url");
            user.save();
            txtName.setText(user.getName());
            txtEmail.setText(user.getEmail());

        }else {
            final List<User> user =SQLite.select().from(User.class).queryList();
            txtName.setText(user.get(0).getName());
            txtEmail.setText(user.get(0).getEmail());
            Log.d("TAG","broj:  "+ user.size());
        }
    }


}
