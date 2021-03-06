package com.example.mihovil.digitalnomad.fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.example.mihovil.digitalnomad.files.LoadingData;
import com.example.webservice.interfaces.ServiceResponse;
import com.example.webservice.interfaces.WebServiceCaller;
import com.example.webservice.interfaces.interfaces.OnServiceFinished;

/**
 * Created by Mihovil on 17.11.2017..
 */

public class EditUserProfileFragment extends Fragment implements OnServiceFinished, View.OnClickListener, TextWatcher {
    private SharedPreferences preferences;
    private EditText oldPassword;
    private EditText newPassword;
    private EditText repeatPassword;
    private RelativeLayout relativeLayout;
    private ProgressBar progressBar;
    private Button save;
    private WebServiceCaller wsc;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.user_profile_fragment_edit_profile, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);
        initListeners();
    }

    private void initView(View view) {
        preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        oldPassword = (EditText) view.findViewById(R.id.user_profile_edit_profile_txtEnterPassword);
        newPassword = (EditText) view.findViewById(R.id.user_profile_edit_profile_txtEnterNewPassword);
        repeatPassword = (EditText) view.findViewById(R.id.user_profile_edit_profile_txtRepeatPassword);
        relativeLayout = (RelativeLayout) view.findViewById(R.id.user_profile_fragment_relative_layout);
        progressBar = (ProgressBar) view.findViewById(R.id.user_profile_fragment_progress_bar);
        save = (Button) view.findViewById(R.id.user_profile_edit_user_btnSpremi);
    }

    private void initListeners() {
        save.setOnClickListener(this);
        repeatPassword.addTextChangedListener(this);
        wsc = new WebServiceCaller(EditUserProfileFragment.this);
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
            success = false;
        } else {
            newP.setError(null);
        }
        if (!repeatP.getText().toString().equals(newP.getText().toString())) {
            repeatP.setError("Passwords do not match");
            success = false;
        } else {
            repeatP.setError(null);
        }
        return success;
    }

    private void resetAll() {
        oldPassword.setText(null);
        newPassword.setText(null);
        repeatPassword.setText(null);
    }


    @Override
    public void onServiceDone(Object response) {
        ServiceResponse res = (ServiceResponse) response;
        Toast.makeText(getContext(), res.getResponseMessage(), Toast.LENGTH_LONG).show();
        LoadingData.DisableProgressBar(relativeLayout, progressBar);
    }

    @Override
    public void onServiceFail(Object message) {
        Toast.makeText(getContext(), (String) message, Toast.LENGTH_LONG).show();
        LoadingData.DisableProgressBar(relativeLayout, progressBar);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.user_profile_edit_user_btnSpremi) {
            if (!checkIfEmpty(oldPassword, newPassword, repeatPassword)) {
                Toast.makeText(getActivity(), "Correct errors", Toast.LENGTH_SHORT).show();
            } else {
                String email = preferences.getString("Email", null);
                WebServiceCaller wsc = new WebServiceCaller(EditUserProfileFragment.this);
                wsc.changePassword(email, oldPassword.getText().toString(), newPassword.getText().toString());
                LoadingData.EnableProgressBar(relativeLayout, progressBar);
                resetAll();
            }
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (!repeatPassword.getText().toString().equals(newPassword.getText().toString())) {
            repeatPassword.setError("Passwords do not match");
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
