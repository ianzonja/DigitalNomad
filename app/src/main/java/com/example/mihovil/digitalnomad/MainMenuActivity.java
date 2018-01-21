package com.example.mihovil.digitalnomad;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.map.MapFragment;
import com.example.map.interfaces.OnLocationPicked;
import com.example.mihovil.digitalnomad.Interface.OnImageDownload;
import com.example.mihovil.digitalnomad.files.GetImage;
import com.example.mihovil.digitalnomad.files.ImageSaver;
import com.example.mihovil.digitalnomad.files.UserToJsonFile;
import com.example.mihovil.digitalnomad.fragments.RecyclerViewFragment;
import com.example.mihovil.digitalnomad.fragments.UserProfileFragment;
import com.example.mihovil.digitalnomad.models.AdvancedResult;
import com.example.webservice.interfaces.ServiceResponse;
import com.example.webservice.interfaces.WebServiceCaller;
import com.example.webservice.interfaces.interfaces.OnServiceFinished;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.mihovil.advancedsearch.advancedSearchFragment;
import com.mihovil.advancedsearch.interfaces.OnAdvancedSearch;

import java.util.ArrayList;
import java.util.List;

public class MainMenuActivity extends AppCompatActivity
        implements OnServiceFinished, NavigationView.OnNavigationItemSelectedListener,OnImageDownload, OnLocationPicked, OnAdvancedSearch {
    SharedPreferences preferences;
    List<MenuItem> menuItems;
    NavigationView navigationView;
    ImageView navProfilePicture;
    TextView navName;
    TextView navEmail;
    private int position;
    private double longitude = 0,latitude = 0;
    private int radius = 0;
    private boolean locationIsReady = false;
    private boolean advancedSearchIsReady = false;
    private boolean isAplicationStarted = true;
    private AdvancedResult advancedResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);

        position = 0;

        navProfilePicture = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.MainMenuImageView);
        navName = (TextView) navigationView.getHeaderView(0).findViewById(R.id.MainMenuNameLastName);
        navEmail = (TextView) navigationView.getHeaderView(0).findViewById(R.id.MainMenuEmail);

        Bitmap bitmap =  new ImageSaver(this).
                setFileName("ProfilePic.png").
                setDirectoryName("ProfilePicture").
                load();
        if(bitmap !=null) {
            navProfilePicture.setImageBitmap(GetImage.getRoundedCornerBitmap(bitmap));
        }

        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        if (preferences.contains("Email")) {
            String email = preferences.getString("Email", null);
            WebServiceCaller wsc = new WebServiceCaller(MainMenuActivity.this);
            wsc.GetUserProfile(email);
        }
        displaySelectedFragment(R.id.nav_workspaces);
    }

    @Override
    public void onBackPressed() {

        while (getSupportFragmentManager().getBackStackEntryCount() > 1){
            getSupportFragmentManager().popBackStackImmediate();
        }

        displaySelectedFragment(R.id.nav_workspaces);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void displaySelectedFragment(int id) {
        Fragment fragment = null;
        Bundle valueBundle = new Bundle();

        if(locationIsReady || isAplicationStarted) {
            valueBundle.putString("longitude", Double.toString(longitude));
            valueBundle.putString("latitude", Double.toString(latitude));
            valueBundle.putString("radius", Integer.toString(radius));
        }
        else if(advancedSearchIsReady){
            valueBundle.putString("countryName", advancedResult.getCountry());
            valueBundle.putString("accomodation", Boolean.toString(advancedResult.getAccomodation()));
            valueBundle.putString("food", Boolean.toString(advancedResult.getFood()));
            valueBundle.putString("socialActivities", Boolean.toString(advancedResult.getActivities()));
            valueBundle.putString("wifi", Boolean.toString(advancedResult.getWifi()));
            valueBundle.putString("aZ", Boolean.toString(advancedResult.getaZ()));
        }
        else {
            valueBundle.putString("email", preferences.getString("Email", null));
        }

        switch (id) {
            case R.id.nav_search:
                fragment = new MapFragment();
                break;
            case R.id.nav_advanced_search:
                fragment = new advancedSearchFragment();
                break;
            case R.id.nav_user_profile:
                fragment = new UserProfileFragment();
                break;
            case R.id.nav_workspaces:
                fragment = new RecyclerViewFragment();
                fragment.setArguments(valueBundle);
                break;
            case R.id.nav_logout:
                Logout();
                startActivity(new Intent(getBaseContext(), LoginActivity.class));
                finish();
        }

        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction().addToBackStack(null);
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }
        isAplicationStarted = false;
        locationIsReady = false;

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        getSelectedItemPosition();
        position = menuItems.indexOf(item);
        if(position == 0)
            id = R.id.nav_workspaces;
        displaySelectedFragment(id);
        return true;
    }

    public void getSelectedItemPosition(){
        Menu menu = navigationView.getMenu();
        menuItems=new ArrayList<>();
        for(int i = 0; i<menu.size(); i++){
            menuItems.add(menu.getItem(i));
        }
    }

    private void Logout() {
        preferences.edit().remove("Email").apply();

        if (AccessToken.getCurrentAccessToken() == null) {
            return;
        }
        new GraphRequest(AccessToken.getCurrentAccessToken(), "/me/permissions/", null, HttpMethod.DELETE, new GraphRequest
                .Callback() {
            @Override
            public void onCompleted(GraphResponse graphResponse) {

                LoginManager.getInstance().logOut();
                finish();

            }
        }).executeAsync();
    }

    @Override
    public void onServiceDone(Object response) {

        ServiceResponse user = (ServiceResponse) response;
        UserToJsonFile obj = new UserToJsonFile(user.getName(), user.getEmail(), user.getReponseId(), user.getUrlPicture(),user.getRank(), getBaseContext());

        navName.setText(user.getName());
        navEmail.setText(user.getEmail());
        try {
            obj.makeJSONObject();
            obj.SaveToFile();
        } catch (Exception e) {
            e.printStackTrace();
        }

        GetImage getImage = new GetImage( this);
        getImage.setUrl(user.getUrlPicture());
        getImage.execute();

    }

    @Override
    public void onServiceFail(Object message) {
        Toast.makeText(getBaseContext(), (String) message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onImageDownload(Bitmap image) {
        navProfilePicture.setImageBitmap(GetImage.getRoundedCornerBitmap(image));
    }

    @Override
    public void locationArrived(double longitude, double latitude, int radius) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.radius = radius;

        locationIsReady = true;

        displaySelectedFragment(R.id.nav_workspaces);
    }

    @Override
    public void onAdvancedResult(String countryName, boolean accomodation, boolean food, boolean wifi, boolean socialActivities, boolean aZ) {
        advancedResult = new AdvancedResult(countryName, accomodation,food,socialActivities,wifi,aZ);
        advancedSearchIsReady = true;
        displaySelectedFragment(R.id.nav_workspaces);

    }
}