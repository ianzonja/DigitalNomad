package com.example.mihovil.digitalnomad.fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.mihovil.digitalnomad.R;
import com.example.webservice.interfaces.ServiceResponse;
import com.example.webservice.interfaces.WebServiceCaller;
import com.example.webservice.interfaces.interfaces.OnServiceFinished;

/**
 * Created by Mihovil on 17.11.2017..
 */

public class EditUserProfileFragment extends Fragment implements OnServiceFinished {
    private SharedPreferences preferences;
    private EditText oldPassword;
    private EditText newPassword;
    private EditText repeatPassword;
    //private RelativeLayout relativeLayout;
    //private ProgressBar progressBar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.user_profile_fragment_edit_profile, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        oldPassword = (EditText) view.findViewById(R.id.user_profile_edit_profile_txtEnterPassword);
        newPassword = (EditText) view.findViewById(R.id.user_profile_edit_profile_txtEnterNewPassword);
        repeatPassword = (EditText) view.findViewById(R.id.user_profile_edit_profile_txtRepeatPassword);
        // relativeLayout = (RelativeLayout) findViewById(R.id.user_profile_fragment_relative_layout);
        // progressBar = (ProgressBar) findViewById(R.id.user_profile_fragment_progress_bar);

        final Button save = (Button) view.findViewById(R.id.user_profile_edit_user_btnSpremi);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkIfEmpty(oldPassword, newPassword, repeatPassword)) {
                    Toast.makeText(getActivity(), "Correct errors", Toast.LENGTH_SHORT).show();
                } else {
                    preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                    String email = preferences.getString("Email", null);
                    //   WebServiceCaller wsc = new WebServiceCaller(EditUserProfileFragment.this);
                    //  wsc.changePassword(email,oldPassword.getText().toString(),newPassword.getText().toString());
                    resetAll();
                }
            }
        });

        repeatPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!repeatPassword.getText().toString().equals(newPassword.getText().toString())) {
                    repeatPassword.setError("Passwords do not match");
                }

            }
        });


    }

    private boolean checkIfEmpty(EditText oldP, EditText newP, EditText repeatP) {
        boolean success = true;
        if (oldP.getText().toString().isEmpty()) {
            oldP.setError("Enter password");
            success = false;
        } else {
            oldP.setError(null);
        }
        if (newP.getText().toString().isEmpty()) {
            newP.setError("Enter new password");
        } else {
            newP.setError(null);
        }
        if (!repeatP.getText().toString().equals(newP.getText().toString())) {
            repeatP.setError("Passwords do not match");
        } else {
            repeatP.setError(null);
        }
        return success;
    }

    private void resetAll() {
        oldPassword.setText(null);
        newPassword.setText(null);
        oldPassword.setText(null);
    }
 /*   private void DisableProgressBar() {
        relativeLayout.setAlpha(1);
        progressBar.setVisibility(View.GONE);
    }

    private void EnableProgressBar() {
        relativeLayout.setAlpha(0.3f);
        progressBar.setVisibility(View.VISIBLE);
    }*/


    @Override
    public void onServiceDone(Object response) {
        ServiceResponse res = (ServiceResponse) response;
        if (res.isPostoji()) {
            Toast.makeText(getContext(), "Password changed", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onServiceFail(Object message) {
        Toast.makeText(getContext(), (String) message, Toast.LENGTH_LONG).show();
    }
}
