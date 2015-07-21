package com.example.patiencebayogha.talentmaplitewithvolley.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.example.patiencebayogha.talentmaplitewithvolley.R;
import com.example.patiencebayogha.talentmaplitewithvolley.data.AccountUser;
import com.example.patiencebayogha.talentmaplitewithvolley.gsonRequest.LoginGsonRequest;
import com.example.patiencebayogha.talentmaplitewithvolley.singletons.AppController;
import com.example.patiencebayogha.talentmaplitewithvolley.url.UrlTml;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by patiencebayogha on 28/05/15.
 */
public class AccountActivity extends Activity {

    private EditText emailviseo;
    private EditText password;
    private EditText confirmPassword;
    private Button next;
    private TextView error;
    private TextView errorPswd;
    TextView email;
    TextView pwd;
    TextView confPwd;
    private TextView errorName;
    private TextView nameNewUser;
    private TextView surnameNewuser;
    private static final String TAG = AccountActivity.class.getSimpleName();
    private final static String REQUEST_SET_COOKIE = "COOKIE"; //header request set cookies

    TextInputLayout emailTextInputLayout;
    TextInputLayout pwdTextInputLayout;
    TextInputLayout conPwdTextInputLayout;
    TextInputLayout nameTextInputLayout;
    TextInputLayout lastNameTextInputLayout;

    private void initView() {
        //calls widgets
        next = (Button) findViewById(R.id.activity_new_account_next_step_button);
        emailviseo = (EditText) findViewById(R.id.activity_new_account_email);
        password = (EditText) findViewById(R.id.activity_new_account_password);
        nameNewUser = (TextView) findViewById(R.id.new_name);
        surnameNewuser = (TextView) findViewById(R.id.new_surname);
        confirmPassword = (EditText) findViewById(R.id.activity_new_account_confirm_password);
        error = (TextView) findViewById(R.id.error);
        errorPswd = (TextView) findViewById(R.id.errorPswd);
        errorName = (TextView) findViewById(R.id.errorName);
        email = (TextView) findViewById(R.id.email);
        pwd = (TextView) findViewById(R.id.pswd);
        confPwd = (TextView) findViewById(R.id.confpswd);

        emailTextInputLayout = (TextInputLayout) findViewById(R.id.email_text_input_layout);
        emailTextInputLayout.setErrorEnabled(true);

        pwdTextInputLayout = (TextInputLayout) findViewById(R.id.mpassword_text_input_layout);
        pwdTextInputLayout.setErrorEnabled(true);



        conPwdTextInputLayout = (TextInputLayout) findViewById(R.id.conf_password_text_input_layout);
        conPwdTextInputLayout.setErrorEnabled(true);
        nameTextInputLayout=(TextInputLayout) findViewById(R.id.name_text_input_layout);
        nameTextInputLayout.setErrorEnabled(true);

        lastNameTextInputLayout=(TextInputLayout) findViewById(R.id.lastname_text_input_layout);
        lastNameTextInputLayout.setErrorEnabled(true);

    }

    @Override
    // Initialization activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_account_activity);
        initView();
        final RequestQueue requestQueue = Volley.newRequestQueue(this);
//Button next
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String newText = emailviseo.getText().toString().toUpperCase();
                final String passwd = password.getText().toString();
                final String confPwd = confirmPassword.getText().toString();
                final String name = nameNewUser.getText().toString();
                final String surname = surnameNewuser.getText().toString();

                if ((passwd.length() != confPwd.length())) {
                    password.requestFocus();
                    confirmPassword.setBackgroundResource(R.drawable.edit_text_invalid);
                    confirmPassword.requestFocus();
                    password.setBackgroundResource(R.drawable.edit_text_invalid);
                    errorPswd.setVisibility(View.VISIBLE);
                    lastNameTextInputLayout.setError("Veuillez entrer votre prenom");
                    nameTextInputLayout.setError("Veuillez entrer votre nom");
                    conPwdTextInputLayout.setError("Veuillez entrer un mot de passe identique");
                    pwdTextInputLayout.setError("Veuillez entrer un mot de passe valide");
                    emailTextInputLayout.setError("Veuillez entrer un email valide");
                    errorPswd.setText("La confirmation et le mot de passe ne sont pas identiques.");

                } else if ((name.isEmpty() || surname.isEmpty()) || (passwd.length() == 0 || confPwd.length() == 0) || (!newText.matches("[A-Z0-9]+([.%+-]?[A-Z0-9-]+)*@[A-Z0-9.-]+\\.[A-Z]{2,4}"))) {
                    emailviseo.requestFocus();
                    emailviseo.setBackgroundResource(R.drawable.edit_text_invalid);
                    error.setVisibility(View.VISIBLE);
                    error.setText("L'email doit être une adresse Viseo ou Novedia. ");
                    nameNewUser.requestFocus();
                    nameNewUser.setBackgroundResource(R.drawable.edit_text_invalid);
                    surnameNewuser.requestFocus();
                    surnameNewuser.setBackgroundResource(R.drawable.edit_text_invalid);
                    errorName.setVisibility(View.VISIBLE);
                    password.requestFocus();
                    confirmPassword.setBackgroundResource(R.drawable.edit_text_invalid);
                    confirmPassword.requestFocus();
                    password.setBackgroundResource(R.drawable.edit_text_invalid);

                    lastNameTextInputLayout.setError("Veuillez entrer votre prenom");
                    nameTextInputLayout.setError("Veuillez entrer votre nom");
                    conPwdTextInputLayout.setError("Veuillez entrer un mot de passe identique");
                    pwdTextInputLayout.setError("Veuillez entrer un mot de passe valide");
                    emailTextInputLayout.setError("Veuillez entrer un email valide");
                    errorName.setText("Veuillez remplir correctement tous les champs ");


                } else {

                    emailviseo.setBackgroundResource(R.drawable.edit_text_valid);
                    password.setBackgroundResource(R.drawable.edit_text_valid);
                    nameNewUser.setBackgroundResource(R.drawable.edit_text_valid);
                    surnameNewuser.setBackgroundResource(R.drawable.edit_text_valid);
                    confirmPassword.setBackgroundResource(R.drawable.edit_text_valid);

                    errorName.setVisibility(View.GONE);
                    errorPswd.setVisibility(View.GONE);
                    error.setVisibility(View.GONE);

                    launchBarDialog(v);
                    postData();
                }
            }
        });
    }

    public void postData() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);


        HashMap<String, String> params = new HashMap<String, String>();
        params.put("email", emailviseo.getText().toString());
        params.put("password", password.getText().toString());
        params.put("name", nameNewUser.getText().toString());
        params.put("surname", surnameNewuser.getText().toString());


        Map<String, String> headers = new HashMap<String, String>(3);
        headers.put("Accept", "application/json");
        headers.put("Content-Type", "application/json;charset=utf-8");
        headers.put("connect.sid", REQUEST_SET_COOKIE);


        LoginGsonRequest<AccountUser> postReq = new LoginGsonRequest<AccountUser>(Request.Method.POST, UrlTml.USERS_CREATE,
                AccountUser.class, headers, params,
                createMyReqSuccessListener(),
                createMyReqErrorListener());
        AppController.getInstance().addToRequestQueue(postReq);
        Log.w(TAG, "AppController getInstance request queue thing hit.");
    }


    private Response.Listener<AccountUser> createMyReqSuccessListener() {
        return new Response.Listener<AccountUser>() {
            @Override
            public void onResponse(AccountUser response) {
                Log.w("DEBUG", "onResponse method hit within JsonObjectRequest");
                VolleyLog.d(TAG, "create account Response: " + response.toString());

                Intent u = new Intent(AccountActivity.this, ActivationActivity.class);
                startActivity(u);


            }

        };
    }


    private Response.ErrorListener createMyReqErrorListener() {
        return new Response.ErrorListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onErrorResponse(VolleyError e) {
                // Do whatever you want to do with error.getMessage();
                Log.w("DEBUG", "ErrorListener method hit within JsonObjectRequest");
                VolleyLog.d(TAG, "Error: " + e.getMessage());
                Toast.makeText(getApplicationContext(),
                        e.getMessage() + "==>" + "Response.ErrorListener" + "===>" + "erreur data loading", Toast.LENGTH_LONG).show();

                error.setVisibility(View.VISIBLE);
                emailviseo.setBackgroundResource(R.drawable.edit_text_invalid);
                nameNewUser.setBackgroundResource(R.drawable.edit_text_invalid);
                surnameNewuser.setBackgroundResource(R.drawable.edit_text_invalid);
                password.setBackgroundResource(R.drawable.edit_text_invalid);
                confirmPassword.setBackgroundResource(R.drawable.edit_text_invalid);
                error.setText("L'email doit être une adresse Viseo ou Novedia. ");

                if (e instanceof NoConnectionError) {
                    Log.d("NoConnectionError>>>>>>>>>", "NoConnectionError.......");

                } else if (e instanceof AuthFailureError) {
                    Log.d("AuthFailureError>>>>>>>>>", "AuthFailureError.......");

                } else if (e instanceof ServerError) {
                    Log.d("ServerError>>>>>>>>>", "ServerError.......");

                } else if (e instanceof NetworkError) {
                    Log.d("NetworkError>>>>>>>>>", "NetworkError.......");

                } else if (e instanceof ParseError) {
                    Log.d("ParseError>>>>>>>>>", "ParseError.......");

                } else if (e instanceof TimeoutError) {
                    Log.d("TimeoutError>>>>>>>>>", "TimeoutError.......");

                }

            }
        };
    }


    public void launchBarDialog(View view) {
        /**
         * method for launch Bar Dialog and notified connection== ok
         */
        final ProgressDialog ringProgressDialog = ProgressDialog.show(AccountActivity.this, "Please wait ...", "Creation de compte...", true);
        ringProgressDialog.setCancelable(true);

        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    // Here you should write your time consuming task...
                    // Let the progress ring for 30 seconds...
                    Thread.sleep(5000);
                } catch (Exception e) {
                }
                ringProgressDialog.dismiss();
            }
        }).start();


    }


}