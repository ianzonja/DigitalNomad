package com.example.mihovil.digitalnomad;

import android.app.Instrumentation;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;

import com.example.webservice.interfaces.ServiceResponse;
import com.example.webservice.interfaces.interfaces.OnServiceFinished;
import com.example.webservice.interfaces.WebServiceCaller;

//facebook
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class Login_activity extends AppCompatActivity implements OnServiceFinished {
    private EditText mail;
    private EditText pass;

    private ProgressBar progressBar;
    private RelativeLayout relativeLayout;

    //facebook
    LoginButton loginButton;
    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login_activity);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        relativeLayout = (RelativeLayout) findViewById(R.id.RelativeLayout1);
        mail = (EditText) findViewById(R.id.email);
        pass = (EditText) findViewById(R.id.pass);

        TextView signUp = (TextView) findViewById(R.id.signup);
        Button login = (Button) findViewById(R.id.button);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckEntry(mail, pass)) {
                    EnableProgressBar();
                    WebServiceCaller wsc = new WebServiceCaller(Login_activity.this);
                    wsc.Login(mail.getText().toString(), pass.getText().toString());
                } else {
                    Toast.makeText(getBaseContext(), "Correct errors", Toast.LENGTH_SHORT).show();
                }
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(getBaseContext(), RegistracijaActivity.class));
            }
        });

        //facebook
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList(
                "public_profile", "email"));
        callbackManager = CallbackManager.Factory.create();
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
                                    String image_url = "http://graph.facebook.com/" + id + "/picture?type=large";

                                    String email = null;
                                    if (object.has("email")) {
                                        email = object.getString("email");
                                    }
                                    /*
                                    WebServiceCaller wsc = new WebServiceCaller(Login_activity.this);
                                    wsc.FacebookLogin(email,first_name,last_name,image_url);
                                    */

                                    Intent i = new Intent(getBaseContext(), MainMenuActivity.class);
                                    startActivity(i);
                                    finish();
                                    Log.d("TAG", "email i pic \n" + email + "\n" + image_url + "\n" + first_name + "  " + last_name);

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
                Log.d("TAG", "error");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


    private void DisableProgressBar() {
        relativeLayout.setAlpha(1);
        progressBar.setVisibility(View.GONE);
    }

    private void EnableProgressBar() {
        relativeLayout.setAlpha(0.3f);
        progressBar.setVisibility(View.VISIBLE);
    }


    @Override
    public void onServiceDone(Object response) {
        DisableProgressBar();
        ServiceResponse login = (ServiceResponse) response;
        if (login.isPostoji()) {
            startActivity(new Intent(getBaseContext(), MainMenuActivity.class));
        } else {
            Toast.makeText(getBaseContext(), "Invalid email or password", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onServiceFail(Object message) {
        Toast.makeText(this, (String) message, Toast.LENGTH_LONG).show();
        DisableProgressBar();
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
        } else {
            password.setError(null);
        }

        return success;
    }
}
