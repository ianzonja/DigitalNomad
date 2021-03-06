package com.example.mihovil.digitalnomad;


import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.mihovil.digitalnomad.fragments.LoginFragment;



public class LoginActivity extends AppCompatActivity{


    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setContentView(R.layout.login_content_frame);

        preferences=PreferenceManager.getDefaultSharedPreferences(this);

        if (preferences.contains("Email")) {
            Intent i = new Intent(getBaseContext(), MainMenuActivity.class);
            startActivity(i);
            finish();
        }

        Fragment fragment = new LoginFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.login_content, fragment);
        ft.commit();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                while (getSupportFragmentManager().getBackStackEntryCount() > 0){
                    getSupportFragmentManager().popBackStackImmediate();
                }

                Fragment fragment = new LoginFragment();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.login_content, fragment);
                ft.commit();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
