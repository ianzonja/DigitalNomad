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

import com.example.map.LocationGetWorkspaceData;
import com.example.mihovil.digitalnomad.Interface.OnDataDisplay;
import com.example.mihovil.digitalnomad.Interface.OnImageDownload;
import com.example.mihovil.digitalnomad.files.GetImage;
import com.example.mihovil.digitalnomad.files.ImageSaver;
import com.example.mihovil.digitalnomad.files.UserToJsonFile;
import com.example.mihovil.digitalnomad.fragments.RecyclerViewFragment;
import com.example.mihovil.digitalnomad.fragments.UserProfileFragment;
import com.example.mihovil.digitalnomad.loaders.UserWorkspacesGetWorkspaceData;
import com.example.mihovil.digitalnomad.models.Workspace;
import com.example.webservice.interfaces.ServiceResponse;
import com.example.webservice.interfaces.WebServiceCaller;
import com.example.webservice.interfaces.WorkspaceValue;
import com.example.webservice.interfaces.interfaces.OnServiceFinished;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.mihovil.advancedsearch.AdvancedSearchGetWorkspaceData;

import java.util.ArrayList;
import java.util.List;

public class MainMenuActivity extends AppCompatActivity
        implements OnServiceFinished, NavigationView.OnNavigationItemSelectedListener,OnImageDownload, OnDataDisplay {
    SharedPreferences preferences;
    List<MenuItem> menuItems;
    NavigationView navigationView;
    ImageView navProfilePicture;
    TextView navName;
    TextView navEmail;
    List<Workspace> workspacesArray;
    Bitmap[] workspaceBitmapArray;
    String fabView;
    private int position;
    private double longitude = 0,latitude = 0;
    private int radius = 0;
    List<WorkspaceValue> workspaceResponse = null;
    List<Workspace> userWorkspaces;

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
    }

    @Override
    public void onBackPressed() {

        while (getSupportFragmentManager().getBackStackEntryCount() > 0){
            getSupportFragmentManager().popBackStackImmediate();
        }

        displaySelectedFragment(R.id.nav_workspaces);
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
        valueBundle.putString("Email", preferences.getString("Email", null));
        getWorkspaceData dl = null;

        switch (id){
            case R.id.nav_search:
                dl = new LocationGetWorkspaceData(this);
                fragment = dl.getFragment(dl);
                break;
            case R.id.nav_advanced_search:
                dl = new AdvancedSearchGetWorkspaceData(this);
                dl.getFragment();
                break;
            case R.id.nav_user_profile:
                fragment = new UserProfileFragment();
                break;
            case R.id.nav_workspaces:
                fragment = new RecyclerViewFragment(workspacesArray);
                fragment.setArguments(valueBundle);
                break;
            case R.id.nav_logout:
                Logout();
                startActivity(new Intent(getBaseContext(), LoginActivity.class));
                finish();
        }

        if(fragment != null){
            System.out.println("i sad bi jos trebo dat fragent");
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
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
        if(position == 4 || position == 0){
            DoUserWorkspaceCall();
        }
        else
            displaySelectedFragment(id);
        return true;
    }

    private void DoUserWorkspaceCall() {
        System.out.println("uso u workspaces call");
        getWorkspaceData userWorkspaces = new UserWorkspacesGetWorkspaceData(this);
        userWorkspaces.loadData(preferences.getString("Email", null));
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
        try {
            ServiceResponse user  = (ServiceResponse) response;
            if(user != null){
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
                getImage.execute();
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
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
    public void onDataDisplay(List<Workspace> workspaces) {
        System.out.println("uso u onDataDisplay");
        workspacesArray = workspaces;
        displaySelectedFragment(R.id.nav_workspaces);
    }
}