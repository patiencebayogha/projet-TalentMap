/**
 * Created by PBA3353 on 6/02/2015.
 * modification 1.0: 09/02/2015
 * Modification: 26 and 27.02.2015
 * 23/15/2015
 * 24/04/2015
 * last modif: 27/04/2015
 * Main activity of the project, allows the connection and
 * Afford to enter the profile page later
 */


package com.example.pba3360.projetcouchbase.activities;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.Volley;
import com.example.pba3360.projetcouchbase.R;
import com.example.pba3360.projetcouchbase.data.LoginUser;
import com.example.pba3360.projetcouchbase.gsonRequest.LoginGsonRequest;
import com.example.pba3360.projetcouchbase.preferences.TalentPreferences;
import com.example.pba3360.projetcouchbase.singletons.AppController;
import com.example.pba3360.projetcouchbase.url.UrlTml;

import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends Activity {

    private static final String TAG = LoginActivity.class.getSimpleName();//Log or request TAG
    private final static String REQUEST_SET_COOKIE = "COOKIE"; //header request set cookies
    public static final String EMAIL = "email";          //Used for sharePreference email
    public static final String PASSWORD = "password";       //Used for sharePreference password
    private Button btnconnect;          //button for connect user
    private Button btncreateaccount;                //button for create account
    private EditText email;                         //enter email in display
    private EditText password;                      //enter password in display
    private CheckBox seSouvenirDeMoi;               //ajouter code pour activer ou non checkbox
    private TextView errorLoginPassword;            //if error Login and password show text
    ProgressDialog progressDialog;                  //progress Dialog for connection
    RequestQueue requestQueue;                         //Global request queue for Volley
    /*
    *permit to convert email and password to string
     */
    String emailText;
    String passwordtext;
    TextInputLayout emailTextInputLayout;
    TextInputLayout pwdTextInputLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        Log.i(getClass().getCanonicalName(), "Activity." + "LoginActivity");

        setContentView(R.layout.activity_login_activity);
        hasConnection();
        initView();//recuperate all initialization of view components


        //sharePreferences (getPreference for email)
        String emailSaved = TalentPreferences.getPrefsEmail(LoginActivity.this);
        if (emailSaved != null) {
            email.setText(emailSaved);
        }


        errorLoginPassword = (TextView) findViewById(R.id.iferror);

        /**
         * Checkbox method for remember me
         */
        seSouvenirDeMoi.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                if (seSouvenirDeMoi.isChecked()) {

                    //save sharedPreferences for recuperate email when you click in checkbox
                    String saveEmail = email.getText().toString();
                    TalentPreferences.savePrefsEmail(LoginActivity.this, saveEmail);
                    Log.i("Remember Me", "checked,preference added");

                    Snackbar snackbar = Snackbar.make(v, "Email save",
                            Snackbar.LENGTH_LONG);
                    View snackBarView = snackbar.getView();
                    snackBarView.setBackgroundColor(R.color.cyan_primary);
                    snackbar.show();
                } else {
                    Log.i("Remember Me", "Unchecked, preferences removed");
                }
            }
        });

        //if onClick connect button you verified if email, password valide and load Loader
        btnconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailText = email.getText().toString().toUpperCase();     //used for email
                passwordtext = password.getText().toString();                   //used for password

                /*
                *testing correct email or error invalid email
                 */
                if (!emailText.matches("[A-Z0-9]+([.%+-]?[A-Z0-9-]+)*@[A-Z0-9.-]+\\.[A-Z]{2,4}") && (passwordtext.length() == 0)) {
                    email.requestFocus();
                    email.setBackgroundResource(R.drawable.edit_text_invalid);
                    password.requestFocus();
                    emailTextInputLayout.setError("Veuillez entrer un email valide");
                    pwdTextInputLayout.setError("Veuillez entrer un mot de passe valide");
                    password.setBackgroundResource(R.drawable.edit_text_invalid);
                    errorLoginPassword.setVisibility(View.VISIBLE);
                    errorLoginPassword.setText("Champs email/mot de passe vide(s)");

                } else if (emailText.matches("[A-Z0-9]+([.%+-]?[A-Z0-9-]+)*@[A-Z0-9.-]+\\.[A-Z]{2,4}") && (passwordtext.length() == 0)) {
                    email.requestFocus();
                    email.setBackgroundResource(R.drawable.edit_text_invalid);
                    password.requestFocus();
                    emailTextInputLayout.setError("Veuillez entrer un email valide");
                    pwdTextInputLayout.setError("Veuillez entrer un mot de passe valide");
                    password.setBackgroundResource(R.drawable.edit_text_invalid);
                    errorLoginPassword.setVisibility(View.VISIBLE);
                    errorLoginPassword.setText("Email et/ou mot de passe invalide(s)");

                } else if ((emailText.isEmpty()) || (passwordtext.isEmpty())) {
                    email.requestFocus();
                    emailTextInputLayout.setError("Veuillez entrer un email valide");
                    pwdTextInputLayout.setError("Veuillez entrer un mot de passe valide");
                    email.setBackgroundResource(R.drawable.edit_text_invalid);
                    errorLoginPassword.setVisibility(View.VISIBLE);
                    errorLoginPassword.setText("Champs email incorrect");

                } else {
                    email.setBackgroundResource(R.drawable.edit_text_valid);
                    password.setBackgroundResource(R.drawable.edit_text_valid);
                    errorLoginPassword.setVisibility(View.GONE);
                    postData();
                    showProgressDialog(v);
                }
            }
        });


        //if click in button create account, you go to other view
        btncreateaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, AccountActivity.class);
                startActivity(intent);
            }
        });

    }


    /**
     * Login method post : auth/login
     */
    public void postData() {
        requestQueue = Volley.newRequestQueue(this);

        HashMap<String, String> params = new HashMap<String, String>();
        params.put(LoginActivity.EMAIL, email.getText().toString());
        params.put(LoginActivity.PASSWORD, password.getText().toString());


        Map<String, String> headers = new HashMap<String, String>(3);
        headers.put("Accept", "application/json");
        headers.put("Content-Type", "application/json;charset=utf-8");
        headers.put("connect.sid", REQUEST_SET_COOKIE);


        LoginGsonRequest<LoginUser> postReq = new LoginGsonRequest<LoginUser>(Request.Method.POST, UrlTml.AUTH_LOGIN,
                LoginUser.class, headers, params,
                createMyReqSuccessListener(),
                createMyReqErrorListener());
        AppController.getInstance().addToRequestQueue(postReq);
        Log.i(TAG, "AppController getInstance request queue thing hit.");
    }


    /*
    *if response ok; we can go to next activity
     */
    private Response.Listener<LoginUser> createMyReqSuccessListener() {
        return new Response.Listener<LoginUser>() {
            @Override
            public void onResponse(LoginUser response) {
                Log.i(TAG, "onResponse method hit within JsonObjectRequest");
                dismissProgressDialog();
                Intent u = new Intent(LoginActivity.this, MainActivity.class);
                u.putExtra(LoginActivity.EMAIL, email.getText().toString());
                startActivity(u);


            }

        };
    }

    /*
       *if error; we can see error code
        */
    private Response.ErrorListener createMyReqErrorListener() {
        return new Response.ErrorListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onErrorResponse(VolleyError error) {
                // Do whatever you want to do with error.getMessage();
                Log.e(TAG, "ErrorListener method hit within JsonObjectRequest" + "Error" + error.getMessage());
                VolleyLog.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage() + "==>" + "Reponse.ErrorListener" + "===>" + "erreur durant le chargement", Toast.LENGTH_LONG).show();

                //show background color for error
                errorLoginPassword.setVisibility(View.VISIBLE);
                errorLoginPassword.setText("Email et/ou mot de passe invalide(s)");
                password.setBackgroundResource(R.drawable.edit_text_invalid);
                email.setBackgroundResource(R.drawable.edit_text_invalid);

                if (error instanceof NoConnectionError) {
                    Log.e("NoConnectionError>>>>>>>>>", "NoConnectionError.......");
                    Toast.makeText(getApplicationContext(),
                            error.getMessage() + "==>" + "Erreur de connexion >>>>>>>>>" + "Erreur de connexion.......", Toast.LENGTH_LONG).show();

                } else if (error instanceof AuthFailureError) {
                    Log.e("AuthFailureError>>>>>>>>>", "AuthFailureError.......");
                    Toast.makeText(getApplicationContext(),
                            error.getMessage() + "==>" + "Erreur d'authentification >>>>>>>>>" + "Erreur d'authentification.......", Toast.LENGTH_LONG).show();

                } else if (error instanceof ServerError) {
                    Log.e("ServerError>>>>>>>>>", "ServerError.......");
                    Toast.makeText(getApplicationContext(),
                            error.getMessage() + "==>" + "Erreur serveur >>>>>>>>>" + "Erreur serveur.......", Toast.LENGTH_LONG).show();

                } else if (error instanceof NetworkError) {
                    Log.e("NetworkError>>>>>>>>>", "NetworkError.......");
                    Toast.makeText(getApplicationContext(),
                            error.getMessage() + "==>" + "Erreur réseau >>>>>>>>>" + "Erreur réseau.......", Toast.LENGTH_LONG).show();

                } else if (error instanceof ParseError) {
                    Log.e("ParseError>>>>>>>>>", "ParseError.......");
                    Toast.makeText(getApplicationContext(),
                            error.getMessage() + "==>" + "Erreur d'analyse >>>>>>>>>" + "Erreur d'analyse.......", Toast.LENGTH_LONG).show();

                } else if (error instanceof TimeoutError) {
                    Log.e("TimeoutError>>>>>>>>>", "TimeoutError.......");
                    Toast.makeText(getApplicationContext(),
                            error.getMessage() + "==>" + "Erreur temps >>>>>>>>>" + "Erreur temps.......", Toast.LENGTH_LONG).show();

                }

            }
        };
    }


    /**
     * initialization of view components
     */
    private void initView() {
        emailTextInputLayout = (TextInputLayout) findViewById(R.id.login_text_input_layout);
        emailTextInputLayout.setErrorEnabled(true);

        pwdTextInputLayout = (TextInputLayout) findViewById(R.id.password_text_input_layout);
        pwdTextInputLayout.setErrorEnabled(true);


        email = (EditText) findViewById(R.id.activity_new_login_email); //edit email
        password = (EditText) findViewById(R.id.activity_new_login_password);//edit password
        seSouvenirDeMoi = (CheckBox) findViewById(R.id.activity_new_login_remember_me); //checkbox for save email
        btnconnect = (Button) findViewById(R.id.activity_new_login_connect);   //Button for Connect a User
        btncreateaccount = (Button) findViewById(R.id.activity_new_login_create_account);// Button for CreateNewUsers


    }


    /**
     * Progress Dialog message show
     */

    public void showProgressDialog(View view) {
        /**
         * method for launch Bar Dialog and notified connection== ok
         */
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(LoginActivity.this);
            progressDialog.setMessage("Patientez, Connexion en cours...");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(true);
            new Thread(new Runnable() {

                @Override
                public void run() {
                    try {
                        // Here you should write your time consuming task...
                        // Let the progress ring for 50 seconds...
                        Thread.sleep(5000);
                    } catch (Exception e) {
                    }
                    progressDialog.dismiss();
                }
            }).start();

        }
        progressDialog.show();
    }

    private void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        dismissProgressDialog();
        super.onDestroy();
    }

    /**
     * Checks if the device has Internet connection.
     *
     * @return <code>true</code> if the phone is connected to the Internet.
     */
    public boolean hasConnection() {

        ConnectivityManager cm = (ConnectivityManager) getSystemService(
                Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiNetwork = null;
        NetworkInfo mobileNetwork = null;
        NetworkInfo activeNetwork = null;
        if (wifiNetwork != null || mobileNetwork != null || activeNetwork != null) {

            wifiNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (wifiNetwork != null && wifiNetwork.isConnected()) {
                return true;
            }

            mobileNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (mobileNetwork != null && mobileNetwork.isConnected()) {
                return true;
            }


            activeNetwork = cm.getActiveNetworkInfo();
            if (activeNetwork != null && activeNetwork.isConnected()) {
                return true;
            }

        } else {
            AlertDialog.Builder alert = new AlertDialog.Builder(
                    LoginActivity.this);

            alert.setMessage(
                    "Aucun signal trouvé ou connexion Internet non établie.")
                    .setTitle("erreur")
                    .setNeutralButton("OK",
                            new DialogInterface.OnClickListener() {

                                public void onClick(
                                        DialogInterface dialog,
                                        int which) {
                                }
                            }).show();
        }

        return false;
    }


}
