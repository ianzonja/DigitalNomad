package com.example.mihovil.digitalnomad;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.webservice.interfaces.APIinterface;
import com.example.webservice.interfaces.Login;
import com.example.webservice.interfaces.WebServiceCaller;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class Login_activity extends AppCompatActivity {
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
        userService = WebServiceCaller.getUserService();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("button clicked");
                String mailText = mail.getText().toString();
                String passwordText = pass.getText().toString();
                if(mailText != "" && passwordText != ""){
                    DoLogin(mailText, passwordText);
                }
            }
        });
    }

    private void DoLogin(String mail, String pass){
        System.out.println("prije calla");
        try {
            Call<Login> call = userService.authenticate(mail, pass);
        System.out.println("call pozvan");
            call.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Response<Login> response, Retrofit retrofit) {
                if(response.isSuccess()){
                    System.out.println("call success");
                    System.out.println(response.headers().toString());
                    System.out.println("kraj hedera");
                    System.out.println(response.body().toString());
                    System.out.println("kraj bodya");
                    System.out.println(String.valueOf(response.body().postoji));
                }
            }

            @Override
            public void onFailure(Throwable t) {
                textview.setText(t.getLocalizedMessage());
                System.out.println("call not success");
            }
        });
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }
}
