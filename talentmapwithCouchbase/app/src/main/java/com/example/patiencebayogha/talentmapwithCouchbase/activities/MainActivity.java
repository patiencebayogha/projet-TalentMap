package com.example.patiencebayogha.talentmapwithCouchbase.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.example.patiencebayogha.talentmapwithCouchbase.R;
import com.example.patiencebayogha.talentmapwithCouchbase.data.ProfilUser;
import com.example.patiencebayogha.talentmapwithCouchbase.data.SkillUserProfil;
import com.example.patiencebayogha.talentmapwithCouchbase.fragments.ProfilFragment;
import com.example.patiencebayogha.talentmapwithCouchbase.fragments.SearchFragment;
import com.example.patiencebayogha.talentmapwithCouchbase.singletons.AppController;
import com.example.patiencebayogha.talentmapwithCouchbase.url.UrlTml;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    NetworkImageView image;

    ImageLoader imageLoader;
    TextView nameUser, lastnameUser;
    private Toolbar toolbar;
    NavigationView navigationView;
    ActionBar actionBar;

    private DrawerLayout drawerLayout;   //for navigation drawer
    private DrawerLayout dlDrawer;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    Fragment fragment = null;
    public static final String EMAIL = "email";
    String email;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainactivity);


        imageLoader = AppController.getInstance().getImageLoader();
        image = (NetworkImageView) findViewById(R.id.profile_image);
        imageGet();

        nameUser = (TextView) findViewById(R.id.nameUser);
        lastnameUser = (TextView) findViewById(R.id.lastnameUser);

        // Set a Toolbar to replace the ActionBar.
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Find our drawer view
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        // Find our drawer view
        navigationView = (NavigationView) findViewById(R.id.nvView);
        // Setup drawer view
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }


// Find our drawer view
        dlDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = setupDrawerToggle();

        // Tie DrawerLayout events to the ActionBarToggle
        dlDrawer.setDrawerListener(actionBarDrawerToggle);

        email = getIntent().getExtras().getString(EMAIL);


        // Set the menu icon instead of the launcher icon.
        actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_36dp);
        actionBar.setDisplayHomeAsUpEnabled(true);

        fragment =new ProfilFragment();
        getIntent().putExtra(MainActivity.EMAIL, email);
        injectFragment();
    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        return new ActionBarDrawerToggle(this, dlDrawer, toolbar, R.string.drawer_open, R.string.drawer_close);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }


    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the planet to show based on
        // position


        switch (menuItem.getItemId()) {

            case R.id.fragment_profil_user:  //profil
                fragment =new ProfilFragment();
                getIntent().putExtra(MainActivity.EMAIL, email);
                break;

            case R.id.fragment_search_all:   //search
                fragment =new SearchFragment();
                break;

            default:

                break;
        }

        injectFragment();
        // Insert the fragment by replacing any existing fragment

        // Highlight the selected item, update the title, and close the drawer
        menuItem.setChecked(true);
        setTitle(menuItem.getTitle());
        dlDrawer.closeDrawers();
    }

    private void injectFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        // Uncomment to inflate menu items to Action Bar
        // inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }


    public void showResults() {
        ((SearchFragment) fragment).afficheResult();
    }

    public void imageGet() {


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                UrlTml.AUTH_GET_ME, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.i("TAG", "onResponse method hit within JsonObjectRequest" + "profil Response get user profil: " + response.toString());


                try {
                    // Parsing json object response
                    // response will be a json object
                    ProfilUser jsonResponse = new ProfilUser();

                    jsonResponse.setName(response.getString("name"));
                    jsonResponse.setEmail(response.getString("email"));
                    jsonResponse.setPhoto(response.getString("photo"));
                    jsonResponse.setSurname(response.getString("surname"));
                    jsonResponse.setActive(Boolean.valueOf(response.getString("active")));

                    setUserProfil(jsonResponse);

                    ArrayList<SkillUserProfil> skillUserProfil = new ArrayList<>();
                    // Getting JSON Array node
                    JSONArray skillsArray = response.getJSONArray("skills");


                    // notify data changes to list adapater


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),
                            e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", "ErrorListener method hit within JsonObjectRequest" + "Error" + error.getMessage());
                VolleyLog.e("TAG", "Error: " + error.getMessage());

                if (error instanceof NoConnectionError) {
                    Log.e("NoConnectionError", "No Connection.......");
                    Toast.makeText(getApplicationContext(),
                            error.getMessage() + ", " + " " + "Erreur de connexion.......", Toast.LENGTH_LONG).show();

                } else if (error instanceof AuthFailureError) {
                    Log.e("AuthFailureError", "Authification Failure Error.......");
                    Toast.makeText(getApplicationContext(),
                            error.getMessage() + ", " + " " + "Erreur d'authentification.......", Toast.LENGTH_LONG).show();

                } else if (error instanceof ServerError) {
                    Log.e("ServerError", "Server Error.......");
                    Toast.makeText(getApplicationContext(),
                            error.getMessage() + ", " + "  " + "Erreur serveur.......", Toast.LENGTH_LONG).show();

                } else if (error instanceof NetworkError) {
                    Log.e("NetworkError>", "Network Error.......");
                    Toast.makeText(getApplicationContext(),
                            error.getMessage() + ", " + " " + "Erreur reseau.......", Toast.LENGTH_LONG).show();

                } else if (error instanceof ParseError) {
                    Log.e("ParseError", "Parse Error.......");
                    Toast.makeText(getApplicationContext(),
                            error.getMessage() + ", " + " " + "Erreur d'analyse.......", Toast.LENGTH_LONG).show();

                } else if (error instanceof TimeoutError) {
                    Log.e("TimeoutError", "Timeout Error.......");
                    Toast.makeText(getApplicationContext(),
                            error.getMessage() + ", " + " " + "Erreur temps.......", Toast.LENGTH_LONG).show();

                }
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }


    /*
   * Recuperate name, surname, Image and adapter for show in the view
    */
    private void setUserProfil(ProfilUser user) {
        image.setImageUrl(user.getPhoto(), imageLoader);
        nameUser.setText(user.getName());
        lastnameUser.setText(user.getSurname());
    }


}