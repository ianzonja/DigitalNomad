package com.example.mihovil.digitalnomad;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.webservice.interfaces.ServiceResponse;
import com.example.webservice.interfaces.interfaces.OnServiceFinished;
import com.example.webservice.interfaces.WebServiceCaller;


public class RegistracijaActivity extends AppCompatActivity implements OnServiceFinished {
    private EditText name, lastName, password, email, repeatPass;
    private ProgressBar progressBar;
    private RelativeLayout relativeLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registracija);

        progressBar = (ProgressBar) findViewById(R.id.progressBar2);
        relativeLayout = (RelativeLayout) findViewById(R.id.RelativeLayout2);

        Button register = (Button) findViewById(R.id.registracija);
        name = (EditText) findViewById(R.id.name);
        lastName = (EditText) findViewById(R.id.lastName);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        repeatPass = (EditText) findViewById(R.id.passwordcheck);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckEntry(email, password, name, lastName, repeatPass)) {
                    EnableProgressBar();
                    WebServiceCaller wsc = new WebServiceCaller(RegistracijaActivity.this);
                    wsc.Registrate(email.getText().toString(), password.getText().toString(), name.getText().toString(), lastName.getText().toString());
                } else {
                    Toast.makeText(getBaseContext(), "Correct errors", Toast.LENGTH_SHORT).show();
                }

            }
        });
        repeatPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!password.getText().toString().equals(repeatPass.getText().toString())) {
                    repeatPass.setError("Passwords do not match");
                } else {
                    repeatPass.setError(null);
                }
            }
        });
    }
    private void DisableProgressBar(){
        relativeLayout.setAlpha(1);
        progressBar.setVisibility(View.GONE);
    }
    private void EnableProgressBar(){
        relativeLayout.setAlpha(0.3f);
        progressBar.setVisibility(View.VISIBLE);
    }


    @Override
    public void onServiceDone(Object response) {
        DisableProgressBar();
        ServiceResponse login = (ServiceResponse) response;
        Log.d("TAG", login.getReturnValue());

        if (login.getReturnValue().equals("1")) {
            finish();
            startActivity(new Intent(getBaseContext(), MainMenuActivity.class));
        } else {
            Toast.makeText(this, "Registracija neuspjesna", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onServiceFail(Object message) {
        DisableProgressBar();
        Toast.makeText(this, (String) message, Toast.LENGTH_LONG).show();
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
}

