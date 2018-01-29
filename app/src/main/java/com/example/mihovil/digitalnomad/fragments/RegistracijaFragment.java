package com.example.mihovil.digitalnomad.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

import com.example.mihovil.digitalnomad.MockData.RegistracijaMockData;
import com.example.mihovil.digitalnomad.files.LoadingData;
import com.example.mihovil.digitalnomad.MainMenuActivity;
import com.example.mihovil.digitalnomad.R;
import com.example.webservice.interfaces.ServiceResponse;
import com.example.webservice.interfaces.interfaces.OnServiceFinished;
import com.example.webservice.interfaces.WebServiceCaller;



public class RegistracijaFragment extends Fragment implements OnServiceFinished, View.OnClickListener, TextWatcher {
    private EditText name, lastName, password, email, repeatPass;
    private ProgressBar progressBar;
    private RelativeLayout relativeLayout;
    private SharedPreferences preferences;
    private Button register;
    WebServiceCaller wsc;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        View rootView = inflater.inflate(R.layout.registracija_fragment, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        initView(view);
        initListeners();

    }

    private void initView(View view) {
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar2);
        relativeLayout = (RelativeLayout) view.findViewById(R.id.RelativeLayout2);
        name = (EditText) view.findViewById(R.id.name);
        lastName = (EditText) view.findViewById(R.id.lastName);
        email = (EditText) view.findViewById(R.id.email);
        password = (EditText) view.findViewById(R.id.password);
        register = (Button) view.findViewById(R.id.registracija);
        repeatPass = (EditText) view.findViewById(R.id.passwordcheck);
    }

    private void initListeners() {
        register.setOnClickListener(this);
        repeatPass.addTextChangedListener(this);
        wsc = new WebServiceCaller(this);
    }

    @Override
    public void onServiceDone(Object response) {
        LoadingData.DisableProgressBar(relativeLayout, progressBar);
        ServiceResponse login = (ServiceResponse) response;

        if (login.getReturnValue().equals("1")) {
            SetLoginSession(email.getText().toString());
            startActivity(new Intent(getContext(), MainMenuActivity.class));
            getActivity().finish();
        } else {
            Toast.makeText(getContext(), "Registracija neuspjesna", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onServiceFail(Object message) {
        LoadingData.DisableProgressBar(relativeLayout, progressBar);
        Toast.makeText(getContext(), (String) message, Toast.LENGTH_LONG).show();
    }

    private boolean CheckIndividualEntry(EditText check, String error) {
        if (check.getText().toString().isEmpty()) {
            check.setError(error);
            return false;
        } else {
            check.setError(null);
        }
        return true;
    }

    private boolean CheckEntry(EditText email, EditText password, EditText name, EditText lastName, EditText repeatPass) {
        boolean success[] = new boolean[4];

        success[0] = CheckIndividualEntry(name, "Enter name");
        success[1] = CheckIndividualEntry(lastName, "Enter last name");

        if (success[2] = CheckIndividualEntry(email, "Enter email")) {
            if (!email.getText().toString().contains("@")) {
                email.setError("Email must contain @");
                success[2] = false;
            }
        }
        if (success[3] = CheckIndividualEntry(password, "Enter password")) {
            if (!password.getText().toString().equals(repeatPass.getText().toString())) {
                repeatPass.setError("Passwords do not match");
                success[3] = false;
            }
        }
        for (boolean b : success) if (!b) return false;
        return true;
    }

    private void SetLoginSession(String email) {
        preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("Email", email);
        editor.apply();
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (!password.getText().toString().equals(repeatPass.getText().toString())) {
            repeatPass.setError("Passwords do not match");
        } else {
            repeatPass.setError(null);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onClick(View v) {

        int i = v.getId();
        if (i == R.id.registracija) {
            if (CheckEntry(email, password, name, lastName, repeatPass)) {

                //MockData za potrebe testiranja Registracije
                if (name.getText().toString().equals(RegistracijaMockData.RegistracijanNme) &&
                        lastName.getText().toString().equals(RegistracijaMockData.RegistracijaLastName) &&
                        email.getText().toString().equals(RegistracijaMockData.RegistracijaEmail) &&
                        password.getText().toString().equals(RegistracijaMockData.RegistracijaPassword) &&
                        repeatPass.getText().toString().equals(RegistracijaMockData.RegistracijaRepeatPassword)) {
                    startActivity(new Intent(getContext(), MainMenuActivity.class));
                    getActivity().finish();

                } else {
                    LoadingData.EnableProgressBar(relativeLayout, progressBar);
                    wsc.Registrate(email.getText().toString(), password.getText().toString(), name.getText().toString(), lastName.getText().toString());
                }
            } else {
                Toast.makeText(getContext(), "Correct errors", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

