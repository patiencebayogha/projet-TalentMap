package com.example.pba3360.projetcouchbase.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
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
import com.example.pba3360.projetcouchbase.R;
import com.example.pba3360.projetcouchbase.activities.LoginActivity;
import com.example.pba3360.projetcouchbase.activities.MainActivity;
import com.example.pba3360.projetcouchbase.activities.MultipartRequest;
import com.example.pba3360.projetcouchbase.activities.SkillsActivity;
import com.example.pba3360.projetcouchbase.adapters.ProfilAdapter;
import com.example.pba3360.projetcouchbase.data.ProfilUser;
import com.example.pba3360.projetcouchbase.data.SkillUserProfil;
import com.example.pba3360.projetcouchbase.gsonRequest.GsonRequestGet;
import com.example.pba3360.projetcouchbase.gsonRequest.LoginGsonRequest;
import com.example.pba3360.projetcouchbase.singletons.AppController;
import com.example.pba3360.projetcouchbase.url.UrlTml;
import com.example.pba3360.projetcouchbase.utils.CircleImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by patiencebayogha on 01/06/15.
 */
public class ProfilFragment extends Fragment implements View.OnClickListener {

    ProgressDialog progressDialog;              //Progress Dialog
    private static final String TAG = ProfilFragment.class.getSimpleName();//Log or request TAG
    private static final int SELECT_PICTURE = 1;          //selected picture in gallery
    TextView choiceSkill;                   //Textview for appear skill il listview
    TextView choiceCategory;                 //Textview for appear category il listview
    private ProfilAdapter profilAdapter;          //adapter for recuperate in listview categories,skills,level of user
    private EditText name;                     //EditText for name
    private EditText lastName;                  //EditText for lastname
    private ListView list;                      //listView for recuperate list of users profil (categories, levels, skills)
    private CircleImageView networkImageView;              //for show networkImageView profil user
    private FloatingActionButton add;                     // add a new skill
    private String email;                           //recuperate email in login
    Uri selectedImage;                              //for selected image
    private final static String REQUEST_SET_COOKIE = "connect.sid\ts%3AZh9jGtdn5Y22XqJqXOo95i9JNjnrKRQ8.JA8JsU1HSCrTJpgSM%2FCSKwp2Y2AyaPI%2BQn18IBozHOc\ttml.hubi.org\t/\t3 juin 2015 16:10:03 UTC+2\t95 o\t\t"; //header request set cookies
    String imgPath;                                 //for recuperate image
    ImageLoader imageLoader;                        //manage the display
    private static final String BOUNDARY = "---------------------------24895933015025\n";
    private static final String CONTENT_TYPE_MULTIPART = "multipart/form-data; boundary=" + BOUNDARY;
    private TextInputLayout layoutName, layoutLastname;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        email = getActivity().getIntent().getExtras().getString(MainActivity.EMAIL);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);//hide the keyboard everytime the activty starts
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(getClass().getCanonicalName(), "Activity." + "ProfilFragment");


        View resultView = inflater.inflate(R.layout.fragment_profil, container, false);

        layoutLastname = (TextInputLayout) resultView.findViewById(R.id.lNameLayout);
        layoutLastname.setErrorEnabled(true);


        layoutName = (TextInputLayout) resultView.findViewById(R.id.nameLayout);
        layoutName.setErrorEnabled(true);

        //adapter
        profilAdapter = new ProfilAdapter(getActivity(), email);

        //ListView
        list = (ListView) resultView.findViewById(R.id.swipedelete);
        list.setAdapter(profilAdapter);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            list.setNestedScrollingEnabled(true);
        }
        // TextView
        choiceSkill = (TextView) resultView.findViewById(R.id.choix_competence);
        choiceCategory = (TextView) resultView.findViewById(R.id.choix_categorie);


        networkImageView = (CircleImageView) resultView.findViewById(R.id.image_users);
        networkImageView.setOnClickListener(this);

        name = (EditText) resultView.findViewById(R.id.name_users);
        name.setBackgroundResource(R.color.cyan_primary);
        name.setFocusable(true);

        /**
         * For Show and hide Keyboard for name and mofify profil
         */
        ModifName();


        lastName = (EditText) resultView.findViewById(R.id.lastName_users);
        lastName.setBackgroundResource(R.color.cyan_primary);
        lastName.setFocusable(true);
        /**
         * For Show and hide Keyboard for lastname and mofify profil
         */
        ModifLastName();
        imageLoader = AppController.getInstance().getImageLoader();


        //Button for add Skills in profil User, go to next activity (skillActivity)
        add = (FloatingActionButton) resultView.findViewById(R.id.activity_skills_image_button_plus);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SkillsActivity.class);
                intent.putExtra(MainActivity.EMAIL, email);
                startActivity(intent);
            }
        });


        getUserProfilTalentMap();    //// making json object request
        return resultView;
    }


    private void ModifLastName() {
        //Modification Last Name
        lastName.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || event.getAction() == KeyEvent.ACTION_DOWN &&
                        event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {

                    try {
                        updateNameUserTalentMap(); //modification Lastname of User

                        Snackbar snackbar = Snackbar.make(v, "vous avez modifié le prénom",
                                Snackbar.LENGTH_LONG);
                        View snackBarView = snackbar.getView();
                        snackBarView.setBackgroundColor(R.color.cyan_primary);
                        snackbar.show();
                        InputMethodManager mr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        mr.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                    } catch (Exception e) {
                        e.getStackTrace();
                    }
                    return true;
                }
                return false;
            }
        });

    }

    private void ModifName() {
//Modification Name
        name.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || event.getAction() == KeyEvent.ACTION_DOWN &&
                        event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {

                    try {
                        updateNameUserTalentMap();  //modification Name of User
                        Snackbar snackbar = Snackbar.make(v, "vous avez modifié le nom",
                                Snackbar.LENGTH_LONG);
                        View snackBarView = snackbar.getView();
                        snackBarView.setBackgroundColor(R.color.cyan_primary);
                        snackbar.show();

                        InputMethodManager mr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        mr.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                    } catch (Exception e) {
                        e.getStackTrace();
                    }
                    return true;
                }
                return false;
            }
        });
    }

    /**
     * Method to make json object request where json response starts`with {
     */
    private void getUserProfilTalentMap() {


        GsonRequestGet jsonObjReq = new GsonRequestGet(Request.Method.GET,
                UrlTml.AUTH_GET_ME, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.i(TAG, "onResponse method hit within JsonObjectRequest" + "profil Response get user profil: " + response.toString());

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

                    // looping through All skills
                    for (int i = 0; i < skillsArray.length(); i++) {
                        JSONObject feedObj = skillsArray.getJSONObject(i);
                        SkillUserProfil userProfil = new SkillUserProfil();

                        userProfil.setCategory(feedObj.getString("category"));
                        userProfil.setLevel((Integer) feedObj.get("level"));
                        userProfil.setSkill(feedObj.getString("skill"));

                        skillUserProfil.add(userProfil);
                    }

                    // notify data changes to list adapater
                    profilAdapter.setData(skillUserProfil);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity().getApplicationContext(),
                            e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {

            @SuppressLint("LongLogTag")
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "ErrorListener method hit within JsonObjectRequest" + "Error" + error.getMessage());
                VolleyLog.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(getActivity().getApplicationContext(),
                        error.getMessage() + "==>" + "Reponse.ErrorListener" + "===>" + "erreur durant le chargement", Toast.LENGTH_LONG).show();

                if (error instanceof NoConnectionError) {
                    Log.e("NoConnectionError>>>>>>>>>", "NoConnectionError.......");
                    Toast.makeText(getActivity().getApplicationContext(),
                            error.getMessage() + "==>" + "Erreur de connexion >>>>>>>>>" + "Erreur de connexion.......", Toast.LENGTH_LONG).show();

                } else if (error instanceof AuthFailureError) {
                    Log.e("AuthFailureError>>>>>>>>>", "AuthFailureError.......");
                    Toast.makeText(getActivity().getApplicationContext(),
                            error.getMessage() + "==>" + "Erreur d'authentification >>>>>>>>>" + "Erreur d'authentification.......", Toast.LENGTH_LONG).show();

                } else if (error instanceof ServerError) {
                    Log.e("ServerError>>>>>>>>>", "ServerError.......");
                    Toast.makeText(getActivity().getApplicationContext(),
                            error.getMessage() + "==>" + "Erreur serveur >>>>>>>>>" + "Erreur serveur.......", Toast.LENGTH_LONG).show();

                } else if (error instanceof NetworkError) {
                    Log.e("NetworkError>>>>>>>>>", "NetworkError.......");
                    Toast.makeText(getActivity().getApplicationContext(),
                            error.getMessage() + "==>" + "Erreur réseau >>>>>>>>>" + "Erreur réseau.......", Toast.LENGTH_LONG).show();

                } else if (error instanceof ParseError) {
                    Log.e("ParseError>>>>>>>>>", "ParseError.......");
                    Toast.makeText(getActivity().getApplicationContext(),
                            error.getMessage() + "==>" + "Erreur d'analyse >>>>>>>>>" + "Erreur d'analyse.......", Toast.LENGTH_LONG).show();

                } else if (error instanceof TimeoutError) {
                    Log.e("TimeoutError>>>>>>>>>", "TimeoutError.......");
                    Toast.makeText(getActivity().getApplicationContext(),
                            error.getMessage() + "==>" + "Erreur temps >>>>>>>>>" + "Erreur temps.......", Toast.LENGTH_LONG).show();

                }
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }


    public ListView getListView() {
        //return ListView
        return list;
    }

    /*
    Change networkImageView if is clicked
     */
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.image_users:

                /**
                 * Change picture if you click
                 * */
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, SELECT_PICTURE);
        }

    }


    /**
     * Retrives the result returned from selecting networkImageView, by invoking the method
     */
    @Override
     /*
 * Called by system when an Activity passes a return Intent back to this Activity.
 */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        // Ensure we are handling the right request and the response was :+1:
        if (requestCode == SELECT_PICTURE && resultCode == Activity.RESULT_OK && null != data) {
            // Image captured and saved to fileUri specified in the Intent
            // Get (file) URI of the image from the return Intent's data
            selectedImage = data.getData();
            imgPath = getPath(selectedImage);
            Log.i("DEBUG", "Choose: " + imgPath);

            updateImge();

        }
    }


    private String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA, MediaStore.Images.Media.SIZE};
        Cursor cursor = getActivity().getContentResolver().query(uri, projection, null, null, null);
        getActivity().startManagingCursor(cursor);    // manages Cursors, whereas the LoaderManager manages Loader<D> objects
        int column_index = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }


    /*
    * Show progress Dialog
     */
    protected void onPreExecute() {
        progressDialog.setMessage("Chargement...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    /*
    * Recuperate name, surname, Image and adapter for show in the view
     */
    private void setUserProfil(ProfilUser user) {
        name.setText(user.getName());
        lastName.setText(user.getSurname());
        networkImageView.setImageUrl(user.getPhoto(), imageLoader);
    }


    /*
        *LOADER_UPDATE_NAME
       *method of recuperate modification of name and lastname users in your profil
        */
    public void updateNameUserTalentMap() {

        // Parameters to send
        HashMap<String, String> params = new HashMap<String, String>();
        params.put(LoginActivity.EMAIL, email);
        params.put("name", name.getText().toString());
        params.put("surname", lastName.getText().toString());

        //headers
        Map<String, String> headers = new HashMap<String, String>(3);
        headers.put("Accept", "application/json");
        headers.put("connect.sid", REQUEST_SET_COOKIE);
        headers.put("Accept", "application/json, text/plain, /\n" +
                "Accept-Language: fr,fr-FR;q=0.8,en-US;q=0.5,en;q=0.3\n" +
                "Accept-Encoding: gzip, deflate\n");
        headers.put("Content-Type", CONTENT_TYPE_MULTIPART);

        LoginGsonRequest<ProfilUser> postReq = new LoginGsonRequest<ProfilUser>(Request.Method.POST, UrlTml.USERS_UPDATE_NAME,
                ProfilUser.class, headers, params,
                createMyReqSuccessListener(),
                createMyReqErrorListener());
        AppController.getInstance().addToRequestQueue(postReq);
        Log.i("DEBUG", "AppController getInstance request queue thing hit.");

    }


    private Response.Listener<ProfilUser> createMyReqSuccessListener() {
        return new Response.Listener<ProfilUser>() {
            @Override
            public void onResponse(ProfilUser response) {
                Log.i("DEBUG", "onResponse method hit within JsonObjectRequest" + "profil Response update name: " + response.toString());
            }

        };
    }


    private Response.ErrorListener createMyReqErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Do whatever you want to do with error.getMessage();
                Log.e(TAG, "ErrorListener method hit within JsonObjectRequest" + "Error" + error.getMessage());
                VolleyLog.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(getActivity().getApplicationContext(),
                        error.getMessage() + "==>" + "Reponse.ErrorListener" + "===>" + "erreur durant le chargement", Toast.LENGTH_LONG).show();

                //show background color for error


                if (error instanceof NoConnectionError) {
                    Log.e("NoConnectionError>>>>>>>>>", "NoConnectionError.......");
                    Toast.makeText(getActivity().getApplicationContext(),
                            error.getMessage() + "==>" + "Erreur de connexion >>>>>>>>>" + "Erreur de connexion.......", Toast.LENGTH_LONG).show();

                } else if (error instanceof AuthFailureError) {
                    Log.e("AuthFailureError>>>>>>>>>", "AuthFailureError.......");
                    Toast.makeText(getActivity().getApplicationContext(),
                            error.getMessage() + "==>" + "Erreur d'authentification >>>>>>>>>" + "Erreur d'authentification.......", Toast.LENGTH_LONG).show();

                } else if (error instanceof ServerError) {
                    Log.e("ServerError>>>>>>>>>", "ServerError.......");
                    Toast.makeText(getActivity().getApplicationContext(),
                            error.getMessage() + "==>" + "Erreur serveur >>>>>>>>>" + "Erreur serveur.......", Toast.LENGTH_LONG).show();

                } else if (error instanceof NetworkError) {
                    Log.e("NetworkError>>>>>>>>>", "NetworkError.......");
                    Toast.makeText(getActivity().getApplicationContext(),
                            error.getMessage() + "==>" + "Erreur réseau >>>>>>>>>" + "Erreur réseau.......", Toast.LENGTH_LONG).show();

                } else if (error instanceof ParseError) {
                    Log.e("ParseError>>>>>>>>>", "ParseError.......");
                    Toast.makeText(getActivity().getApplicationContext(),
                            error.getMessage() + "==>" + "Erreur d'analyse >>>>>>>>>" + "Erreur d'analyse.......", Toast.LENGTH_LONG).show();

                } else if (error instanceof TimeoutError) {
                    Log.e("TimeoutError>>>>>>>>>", "TimeoutError.......");
                    Toast.makeText(getActivity().getApplicationContext(),
                            error.getMessage() + "==>" + "Erreur temps >>>>>>>>>" + "Erreur temps.......", Toast.LENGTH_LONG).show();

                }

            }
        };
    }

    public void updateImge() {

        File f = new File("path");
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("photo", networkImageView.toString());
        headers.put("Accept", "application/json");
        headers.put("connect.sid", REQUEST_SET_COOKIE);
        headers.put("Content-Type", "application/json;charset=utf-8");


        HashMap<String, String> params = new HashMap<String, String>();
        params.put("photo", "value");

        String url = "http://tml.hubi.org/api/v1/users/update-photo/";
        MultipartRequest mPR = new MultipartRequest(url, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError arg0) {
                // TODO Auto-generated method stub
                Toast.makeText(getActivity(), "error " + arg0.toString(), Toast.LENGTH_LONG).show();
            }
        }, new Response.Listener<String>() {
            @Override
            public void onResponse(String arg0) {
                // TODO Auto-generated method stub
                Log.d("Success", arg0.toString());
                networkImageView.setImageURI(selectedImage);
            }
        }, f, "photo", params, headers);

        mPR.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        AppController.getInstance().addToRequestQueue(mPR,
                "request tag ");
    }



}





