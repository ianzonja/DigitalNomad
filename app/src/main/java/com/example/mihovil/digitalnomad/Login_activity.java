package com.example.mihovil.digitalnomad;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;

import com.example.webservice.interfaces.ServiceResponse;
import com.example.webservice.interfaces.OnServiceFinished;
import com.example.webservice.interfaces.WebServiceCaller;

//facebook
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONObject;

public class Login_activity extends AppCompatActivity implements OnServiceFinished {
    private EditText mail;
    private EditText pass;

    //facebook
    LoginButton loginButton;
    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity);

        //facebook
        loginButton = (LoginButton) findViewById(R.id.login_button);
      //  loginButton.setReadPermissions("email","public_profile");
        callbackManager = CallbackManager.Factory.create();
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                loginButton.setVisibility(View.INVISIBLE);
                startActivity(new Intent(getBaseContext(), MainMenuActivity.class));
                finish();
                Log.d("TAG","success");
            }

            @Override
            public void onCancel() {
                Log.d("TAG","cancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("TAG","error");
            }
        });



        mail = (EditText) findViewById(R.id.email);
        pass = (EditText) findViewById(R.id.pass);
        Button login = (Button) findViewById(R.id.button);
        TextView signUp = (TextView) findViewById(R.id.signup);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckEntry(mail, pass)) {
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
                startActivity(new Intent(getBaseContext(), RegistracijaActivity.class));
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode,resultCode,data);
    }

    @Override
    public void onServiceDone(Object response) {
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
