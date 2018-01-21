package com.example.mihovil.digitalnomad.fragments;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mihovil.digitalnomad.MockData.LoginMockData;
import com.example.mihovil.digitalnomad.files.LoadingData;
import com.example.mihovil.digitalnomad.MainMenuActivity;
import com.example.mihovil.digitalnomad.R;
import com.example.webservice.interfaces.ServiceResponse;
import com.example.webservice.interfaces.WebServiceCaller;
import com.example.webservice.interfaces.interfaces.OnServiceFinished;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

/**
 * Created by Mihovil on 4.12.2017..
 */

public class LoginFragment extends Fragment implements OnServiceFinished,View.OnClickListener,FacebookCallback<LoginResult>{
    private EditText mail;
    private EditText pass;
    private ProgressBar progressBar;
    private RelativeLayout relativeLayout;
    private SharedPreferences preferences;
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private TextView signUp;
    private Button login;
    private WebServiceCaller wsc;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        View rootView = inflater.inflate(R.layout.login_fragment, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        initView(view);
        initListeners();

        //facebook permissions
        loginButton.setReadPermissions(Arrays.asList(
                "public_profile", "email"));

    }
    private void initView(View view){
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        relativeLayout = (RelativeLayout) view.findViewById(R.id.RelativeLayout1);
        mail = (EditText) view.findViewById(R.id.email);
        pass = (EditText) view.findViewById(R.id.pass);
        signUp = (TextView) view.findViewById(R.id.signup);
        login = (Button) view.findViewById(R.id.button);

        //facebook login button
        loginButton = (LoginButton) view.findViewById(R.id.login_button);
    }

    private void initListeners(){
        signUp.setOnClickListener( this);
        login.setOnClickListener(this);
        loginButton.registerCallback(callbackManager,  this);
        loginButton.setFragment(this);
        wsc = new WebServiceCaller(this);
    }

    //metoda za provjeru unosa vrijednosti u polje.
    private boolean CheckEntry(EditText email, EditText password) {
        boolean success = true;

        if (email.getText().toString().isEmpty()) {
            email.setError("Enter email");
            success = false;
        } else {
            email.setError(null);
        }
        if (password.getText().toString().isEmpty()) {
            password.setError("Enter password");
            success = false;
        } else {
            password.setError(null);
        }

        return success;
    }

    //OnServiceFinished implementacija start
    //odgovor od servisa koji ukoliko su uneseni tocni podaci prosljeduje korisnika u glavni izbornik
    @Override
    public void onServiceDone(Object response) {
        LoadingData.DisableProgressBar(relativeLayout,progressBar);
        ServiceResponse login = (ServiceResponse) response;
        if (login.isPostoji()) {
            SetLoginSession(mail.getText().toString());
            startActivity(new Intent(getContext(), MainMenuActivity.class));
            getActivity().finish();
        } else {
            Toast.makeText(getActivity(), "Invalid email or password", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onServiceFail(Object message) {
        Toast.makeText(getActivity(), (String) message, Toast.LENGTH_LONG).show();
        LoadingData.DisableProgressBar(relativeLayout,progressBar);
    }
    //OnServiceFinished implementacija end

    //spremanje korisnikovog emaila u shared preferences
    private void SetLoginSession(String email) {
        preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("Email", email);
        editor.apply();
    }

    //button click interface implementation
    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.signup:
                Fragment fragment = new RegistracijaFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction().addToBackStack("LoginFragment");
                ft.replace(R.id.login_content, fragment);
                ft.commit();
                break;
            case R.id.button:
                if (CheckEntry(mail, pass)) {
                    //Za potrebe testiranja koristenje mock data
                    if(mail.getText().toString().equals( LoginMockData.loginMail) && pass.getText().toString().equals(LoginMockData.LoginPassword)){
                        Intent i = new Intent(getContext(), MainMenuActivity.class);
                        startActivity(i);
                        getActivity().finish();

                    }else {
                        LoadingData.EnableProgressBar(relativeLayout, progressBar);
                        wsc.Login(mail.getText().toString(), pass.getText().toString());
                    }
                } else {
                    Toast.makeText(getActivity(), "Correct errors", Toast.LENGTH_SHORT).show();
                }
        }
    }

    //Facebook callback interface implementation start
    @Override
    public void onSuccess(LoginResult loginResult) {

        GraphRequest request = GraphRequest.newMeRequest(
                loginResult.getAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        try {
                            String id = object.getString("id");
                            String first_name = object.getString("first_name");
                            String last_name = object.getString("last_name");
                            String image_url = "https://graph.facebook.com/" + id + "/picture?type=large";


                            String email = null;
                            if (object.has("email")) {
                                email = object.getString("email");
                                SetLoginSession(email);
                            }

                            WebServiceCaller wsc = new WebServiceCaller(LoginFragment.this);
                            wsc.FacebookLogin(email, first_name, last_name, image_url);

                            Intent i = new Intent(getContext(), MainMenuActivity.class);
                            startActivity(i);
                            getActivity().finish();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,first_name,last_name,email");
        request.setParameters(parameters);
        request.executeAsync();

    }

    @Override
    public void onCancel() {

    }

    @Override
    public void onError(FacebookException error) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
    //Facebook interface implementation end


}
