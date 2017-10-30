package com.example.mihovil.digitalnomad;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.webservice.interfaces.APIinterface;
import com.example.webservice.interfaces.ServiceResponse;
import com.example.webservice.interfaces.OnServiceFinished;
import com.example.webservice.interfaces.WebServiceCaller;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class Login_activity extends AppCompatActivity implements OnServiceFinished {
    EditText mail;
    EditText pass;
    Button login;
    APIinterface userService;
    TextView textview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("activity startan");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity);
        mail = (EditText) findViewById(R.id.email);
        pass = (EditText) findViewById(R.id.pass);
        login = (Button) findViewById(R.id.button);
        textview = (TextView) findViewById(R.id.textView);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("button clicked");
                String mailText = mail.getText().toString();
                String passwordText = pass.getText().toString();
                if(mailText != "" && passwordText != ""){
                    WebServiceCaller wsc = new WebServiceCaller(Login_activity.this);
                    wsc.Login(mailText, passwordText);
                }
            }
        });
    }

    @Override
    public void onServiceDone(Object response) {
        ServiceResponse login = (ServiceResponse) response;
        if (login.postoji == true){
            System.out.println("uspjeh");
            Toast.makeText(this, "Registracija uspjesna",Toast.LENGTH_LONG);
        } else {
            System.out.println("neuspjeh");
            Toast.makeText(this, "Registracija neuspjesna",Toast.LENGTH_LONG);
        }
    }

    @Override
    public void onServiceFail(Object message) {

    }
}
