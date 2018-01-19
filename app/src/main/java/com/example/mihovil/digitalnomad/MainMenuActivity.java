package com.example.mihovil.digitalnomad;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.map.MapFragment;
import com.example.mihovil.digitalnomad.Interface.OnImageDownload;
import com.example.mihovil.digitalnomad.files.GetImage;
import com.example.mihovil.digitalnomad.files.ImageSaver;
import com.example.mihovil.digitalnomad.files.UserToJsonFile;
import com.example.mihovil.digitalnomad.fragments.EnterWorkspaceFragment;
import com.example.mihovil.digitalnomad.fragments.RecyclerViewFragment;
import com.example.mihovil.digitalnomad.fragments.UserProfileFragment;
import com.example.webservice.interfaces.ServiceResponse;
import com.example.webservice.interfaces.WebServiceCaller;
import com.example.webservice.interfaces.interfaces.OnServiceFinished;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;
import java.util.List;

import entities.Workspace;

public class MainMenuActivity extends AppCompatActivity
        implements OnServiceFinished, NavigationView.OnNavigationItemSelectedListener,OnImageDownload {
    SharedPreferences preferences;
    List<MenuItem> menuItems;
    NavigationView navigationView;
    ImageView navProfilePicture;
    TextView navName;
    TextView navEmail;
    int position;

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

        FlowManager.init(new FlowConfig.Builder(this).build());

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
        //ToDo: usporediti podatke na serveru i lokalno

        if (preferences.contains("Email")) {
            String email = preferences.getString("Email", null);
            WebServiceCaller wsc = new WebServiceCaller(MainMenuActivity.this);
            wsc.GetUserProfile(email);
        }
        displaySelectedFragment(R.id.nav_workspaces);
    }

    @Override
    public void onBackPressed() {

        displaySelectedFragment(R.id.nav_workspaces);

        while (getSupportFragmentManager().getBackStackEntryCount() > 1){
            getSupportFragmentManager().popBackStackImmediate();
        }

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
        if(position != 0)
            valueBundle.putString("email", preferences.getString("Email", null));
        else
            valueBundle.putString("email", preferences.getString("Email", null)); //zasad ista stvar kao i kod workspaceova usera, ali prominiti da salje trenutnu lokaciju

        switch (id) {
            case R.id.nav_search:
                fragment = new MapFragment();
                break;
            case R.id.nav_user_profile:
                fragment = new UserProfileFragment();
                break;
            case R.id.nav_workspaces:
                fragment = new RecyclerViewFragment();
                fragment.setArguments(valueBundle);
                break;
            case R.id.nav_add_workspace:
                fragment = new EnterWorkspaceFragment();
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

        GetImage getImage = new GetImage(user.getUrlPicture(), this);
        getImage.execute();

    }

    @Override
    public void onServiceFail(Object message) {
        Toast.makeText(getBaseContext(), (String) message, Toast.LENGTH_LONG).show();
    }

    public void getAllWorkspaces() {
        final List<Workspace> workspace;
        workspace = SQLite.select().from(Workspace.class).queryList();
        String name = workspace.get(0).getName();
        String country = workspace.get(0).getCountry();
        String town = workspace.get(0).getCity();
        String address = workspace.get(0).getAddress();
    }

    @Override
    public void onImageDownload(Bitmap image) {
        navProfilePicture.setImageBitmap(GetImage.getRoundedCornerBitmap(image));
    }
}