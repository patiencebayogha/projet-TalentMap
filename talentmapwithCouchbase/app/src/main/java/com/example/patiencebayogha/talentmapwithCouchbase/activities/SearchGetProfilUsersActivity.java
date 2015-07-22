package com.example.patiencebayogha.talentmapwithCouchbase.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
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
import com.example.patiencebayogha.talentmapwithCouchbase.R;
import com.example.patiencebayogha.talentmapwithCouchbase.adapters.SearchAdapterGetProfilUsers;
import com.example.patiencebayogha.talentmapwithCouchbase.data.SearchSkillsUsers;
import com.example.patiencebayogha.talentmapwithCouchbase.data.SearchUser;
import com.example.patiencebayogha.talentmapwithCouchbase.singletons.AppController;
import com.example.patiencebayogha.talentmapwithCouchbase.url.UrlTml;
import com.example.patiencebayogha.talentmapwithCouchbase.utils.CircleImageView;
import com.example.patiencebayogha.talentmapwithCouchbase.utils.LoaderUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by patiencebayogha on 01/06/15.
 */
public class SearchGetProfilUsersActivity extends Activity {
    public static final String EMAIL_TO_SEARCH = "email_to_search";             //pass to activity
    private static final int LOADER_LIST = LoaderUtils.getInstance().getLoaderId();         //add list others users loader Id

    private TextView getNameSearch;                  //Textview for name other user
    private TextView getsurnameSearch;               //Textview for lastname other user
    private ListView getList;                       //listview for get list others users
    private CircleImageView getImage;              //recup distant image of other user
    private SearchAdapterGetProfilUsers searchAdapterGetProfilUsers;      //adapter for get other users in display
    private String email;                               //pass to activity
    protected ArrayList<SearchSkillsUsers> pSkills;
    private static final String TAG = SearchGetProfilUsersActivity.class.getSimpleName();
    //Users json url

    ImageLoader imageLoader;
    private final static String REQUEST_SET_COOKIE = "connect.sid	s%3AZh9jGtdn5Y22XqJqXOo95i9JNjnrKRQ8.JA8JsU1HSCrTJpgSM%2FCSKwp2Y2AyaPI%2BQn18IBozHOc"; //header request set cookies
    private final static String SETTING_COOKIE = "set-cookie";


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_get_profil_users_activity);

        email = getIntent().getExtras().getString(EMAIL_TO_SEARCH);

        searchAdapterGetProfilUsers = new SearchAdapterGetProfilUsers(this, email);

        pSkills = new ArrayList<>();

        getList = (ListView) findViewById(R.id.swipedelete);
        getList.setAdapter(searchAdapterGetProfilUsers);

        getImage = (CircleImageView) findViewById(R.id.image_users);

        getNameSearch = (TextView) findViewById(R.id.name_users);
        getsurnameSearch = (TextView) findViewById(R.id.surname_users);

        //visible cursor set is true
        getNameSearch.setFocusableInTouchMode(false);
        getsurnameSearch.setFocusableInTouchMode(false);

        imageLoader = AppController.getInstance().getImageLoader();

        makeJsonObjectRequest();
    }

    /**
     * Method to make json object request where json response starts wtih {
     */
    private void makeJsonObjectRequest() {


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                UrlTml.USERS_GET_EMAIL + Uri.encode(email), null,new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());
                try {

                    SearchUser jsonResponse = new SearchUser();

                    jsonResponse.setName(response.getString("name"));
                    jsonResponse.setEmail(response.getString("email"));
                    jsonResponse.setPhoto(response.getString("photo"));
                    jsonResponse.setSurname(response.getString("surname"));
                    jsonResponse.setActive(Boolean.valueOf(response.getString("active")));

                    setUserProfil(jsonResponse);

                    ArrayList<SearchSkillsUsers> skillUserProfil = new ArrayList<>();
                    // Getting JSON Array node
                    JSONArray skillsArray = response.getJSONArray("skills");

                    // looping through All skills
                    for (int i = 0; i < skillsArray.length(); i++) {
                        JSONObject feedObj = skillsArray.getJSONObject(i);
                        SearchSkillsUsers userProfil = new SearchSkillsUsers();

                        userProfil.setCategory(feedObj.getString("category"));
                        userProfil.setLevel((Integer) feedObj.get("level"));
                        userProfil.setSkill(feedObj.getString("skill"));

                        skillUserProfil.add(userProfil);
                    }

                    // notify data changes to list adapater
                    searchAdapterGetProfilUsers.setData(skillUserProfil);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),
                            e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {

            @SuppressLint("LongLogTag")
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Log.w("DEBUG", "onErrorResponse method hit within JsonObjectRequest" + error.getMessage());

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
        }) {

            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
                params.put("Accept", "application/json");
                params.put("Content-Type", "application/json;charset=utf-8");
                params.put("connect.sid", REQUEST_SET_COOKIE);
                Log.d(getClass().getCanonicalName(), REQUEST_SET_COOKIE + " : " + REQUEST_SET_COOKIE);
                VolleyLog.d(getClass().getCanonicalName(), SETTING_COOKIE + " : " + SETTING_COOKIE);

                return params;
            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }


    public ListView getListView() {
        //recup Listview
        return getList;
    }


    private void setUserProfil(SearchUser user) {
        getNameSearch.setText(user.getName());
        getsurnameSearch.setText(user.getSurname());
        getImage.setImageUrl(user.getPhoto(), imageLoader);
        getImage.setDefaultImageResId(R.drawable.default_profile_img);

    }

}
