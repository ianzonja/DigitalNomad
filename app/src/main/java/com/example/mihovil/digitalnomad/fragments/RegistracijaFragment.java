package com.example.mihovil.digitalnomad.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
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

import com.example.mihovil.digitalnomad.Interface.OnServiceCalled;
import com.example.mihovil.digitalnomad.files.LoadingData;
import com.example.mihovil.digitalnomad.MainMenuActivity;
import com.example.mihovil.digitalnomad.R;
import com.example.webservice.interfaces.ServiceResponse;
import com.example.webservice.interfaces.interfaces.OnServiceFinished;
import com.example.webservice.interfaces.WebServiceCaller;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;


public class RegistracijaFragment extends Fragment implements OnServiceFinished {
    private EditText name, lastName, password, email, repeatPass;
    private ProgressBar progressBar;
    private RelativeLayout relativeLayout;
    private SharedPreferences preferences;
    private Button register;
    private OnServiceCalled loader;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loader = new LoadingData();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.registracija_fragment, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        FlowManager.init(new FlowConfig.Builder(getContext()).build());
        preferences= PreferenceManager.getDefaultSharedPreferences(getContext());
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar2);
        relativeLayout = (RelativeLayout) view.findViewById(R.id.RelativeLayout2);
        name = (EditText) view.findViewById(R.id.name);
        lastName = (EditText) view.findViewById(R.id.lastName);
        email = (EditText) view.findViewById(R.id.email);
        password = (EditText) view.findViewById(R.id.password);
        register = (Button) view.findViewById(R.id.registracija);
        OnRegistracijaClick();
        repeatPass = (EditText) view.findViewById(R.id.passwordcheck);
        OnTextChanged();
    }

    private void OnRegistracijaClick(){
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckEntry(email, password, name, lastName, repeatPass)) {
                    loader.EnableProgressBar(relativeLayout,progressBar);
                    WebServiceCaller wsc = new WebServiceCaller(RegistracijaFragment.this);
                    wsc.Registrate(email.getText().toString(), password.getText().toString(), name.getText().toString(), lastName.getText().toString());
                } else {
                    Toast.makeText(getContext(), "Correct errors", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void OnTextChanged(){
        repeatPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!password.getText().toString().equals(repeatPass.getText().toString())) {
                    repeatPass.setError("Passwords do not match");
                } else {
                    repeatPass.setError(null);
                }
            }
        });
    }


    @Override
    public void onServiceDone(Object response) {
        loader.DisableProgressBar(relativeLayout,progressBar);
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
        loader.DisableProgressBar(relativeLayout,progressBar);
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
        preferences= PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("Email", email);
        editor.apply();
    }


}

