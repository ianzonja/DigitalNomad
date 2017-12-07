package com.example.mihovil.digitalnomad.fragments;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mihovil.digitalnomad.Interface.OnServiceCalled;
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

public class LoginFragment extends Fragment implements OnServiceFinished {
    private EditText mail;
    private EditText pass;
    private ProgressBar progressBar;
    private RelativeLayout relativeLayout;
    private SharedPreferences preferences;
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private TextView signUp;
    private OnServiceCalled loader;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.login_fragment, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        relativeLayout = (RelativeLayout) view.findViewById(R.id.RelativeLayout1);
        loader = new LoadingData(relativeLayout,progressBar);
        mail = (EditText) view.findViewById(R.id.email);
        pass = (EditText) view.findViewById(R.id.pass);


        signUp = (TextView) view.findViewById(R.id.signup);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new RegistracijaFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.login_content, fragment);
                ft.commit();

            }
        });

        Button login = (Button) view.findViewById(R.id.button);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckEntry(mail, pass)) {
                    loader.EnableProgressBar();
                    WebServiceCaller wsc = new WebServiceCaller(LoginFragment.this);
                    wsc.Login(mail.getText().toString(), pass.getText().toString());
                } else {
                    Toast.makeText(getActivity(), "Correct errors", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //facebook
        loginButton = (LoginButton) view.findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList(
                "public_profile", "email"));
        loginButton.setFragment(this);
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
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
                LoginManager.getInstance().logOut();
            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

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

    @Override
    public void onServiceDone(Object response) {
        loader.DisableProgressBar();
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
        loader.DisableProgressBar();
    }

    private void SetLoginSession(String email) {
        preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("Email", email);
        editor.apply();
    }

}
