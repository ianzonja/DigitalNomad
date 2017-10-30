package com.example.mihovil.digitalnomad;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.webservice.interfaces.ServiceResponse;
import com.example.webservice.interfaces.OnServiceFinished;
import com.example.webservice.interfaces.WebServiceCaller;


public class RegistracijaActivity extends AppCompatActivity implements OnServiceFinished {
    Button register;
    EditText name, lastName, password, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registracija);

       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        register = (Button) findViewById(R.id.registracija);
        name = (EditText) findViewById(R.id.name);
        lastName = (EditText) findViewById(R.id.lastName);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WebServiceCaller wsc = new WebServiceCaller(RegistracijaActivity.this);
                wsc.Registrate(email.getText().toString(), password.getText().toString(), name.getText().toString(), lastName.getText().toString());
            }
        });
    }


    @Override
    public void onServiceDone(Object response) {
        ServiceResponse login = (ServiceResponse) response;
        Log.d("TAG",login.getReturnValue());

        if (login.getReturnValue().trim().equals("1")){
            //startActivity();
            Toast.makeText(this, "Registracija uspjesna",Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Registracija neuspjesna",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onServiceFail(Object message) {
        Toast.makeText(this, (String)message,Toast.LENGTH_LONG).show();
    }
}

