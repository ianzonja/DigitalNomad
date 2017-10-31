package com.example.mihovil.digitalnomad;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.webservice.interfaces.ServiceResponse;
import com.example.webservice.interfaces.OnServiceFinished;
import com.example.webservice.interfaces.WebServiceCaller;

public class Login_activity extends AppCompatActivity implements OnServiceFinished {
    private EditText mail;
    private EditText pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity);
        mail = (EditText) findViewById(R.id.email);
        pass = (EditText) findViewById(R.id.pass);
        Button login = (Button) findViewById(R.id.button);
        TextView signUp = (TextView) findViewById(R.id.signup);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mailText = mail.getText().toString();
                String passwordText =pass.getText().toString();
                if(!mailText.isEmpty()  && !passwordText.isEmpty()){
                    WebServiceCaller wsc = new WebServiceCaller(Login_activity.this);
                    wsc.Login(mailText, passwordText);
                }
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getBaseContext(),RegistracijaActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    public void onServiceDone(Object response) {
        ServiceResponse login = (ServiceResponse) response;
        if (login.isPostoji()){
            startActivity(new Intent(getBaseContext(),MainMenuActivity.class));
        } else {
          //ToDo
        }
    }

    @Override
    public void onServiceFail(Object message) {
        Toast.makeText(this, (String)message,Toast.LENGTH_LONG).show();
    }
}
