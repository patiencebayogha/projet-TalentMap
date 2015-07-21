package com.example.patiencebayogha.talentmaplitewithvolley.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
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
import com.example.patiencebayogha.talentmaplitewithvolley.data.ActivateAccount;
import com.example.patiencebayogha.talentmaplitewithvolley.gsonRequest.LoginGsonRequest;
import com.example.patiencebayogha.talentmaplitewithvolley.singletons.AppController;
import com.example.patiencebayogha.talentmaplitewithvolley.url.UrlTml;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by patiencebayogha on 12/03/15.
 *
 *  last modif: 27/04/2015
 * * permit to confirm the creation of account
 * A page with a text message appears for confirm that account is in waiting the confirmation
 */
public class ActivationActivity extends Activity {
    TextView response;
    private TextView email;
    public static String EMAIL_ACCOUNT = "email";
    private static final String TAG = ActivationActivity.class.getSimpleName();
    private final static String REQUEST_SET_COOKIE = "COOKIE"; //header request set cookies


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activation_new_account);



        response = (TextView) findViewById(R.id.response);
        email = (TextView) findViewById(R.id.email);



        RequestQueue requestQueue = Volley.newRequestQueue(this);
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("email", email.getText().toString());
        params.put("activaionId", response.getText().toString());



        Map<String, String> headers = new HashMap<String, String>(3);
        headers.put("Accept", "application/json");
        headers.put("Content-Type", "application/json;charset=utf-8");
        headers.put("connect.sid", REQUEST_SET_COOKIE);

        // making fresh volley request and getting json
        LoginGsonRequest<ActivateAccount> postReq = new LoginGsonRequest<ActivateAccount>(Request.Method.POST, UrlTml.USERS_ACTIVATE,
                ActivateAccount.class, headers, params,
                createMyReqSuccessListener(),
                createMyReqErrorListener());
        AppController.getInstance().addToRequestQueue(postReq);
        Log.w("DEBUG", "AppController getInstance request queue thing hit.");
    }


    private Response.Listener<ActivateAccount> createMyReqSuccessListener() {
        return new Response.Listener<ActivateAccount>() {
            @Override
            public void onResponse(ActivateAccount response) {
                Log.w("DEBUG", "onResponse method hit within JsonObjectRequest");
                VolleyLog.d(TAG, "create account Response: " + response.toString());



            }

        };
    }


    private Response.ErrorListener createMyReqErrorListener() {
        return new Response.ErrorListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onErrorResponse(VolleyError error) {
                // Do whatever you want to do with error.getMessage();
                Log.w("DEBUG", "ErrorListener method hit within JsonObjectRequest");
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage() + "==>" + "Response.ErrorListener" + "===>" + "erreur data loading", Toast.LENGTH_LONG).show();

                if (error instanceof NoConnectionError) {
                    Log.d("NoConnectionError>>>>>>>>>", "NoConnectionError.......");

                } else if (error instanceof AuthFailureError) {
                    Log.d("AuthFailureError>>>>>>>>>", "AuthFailureError.......");

                } else if (error instanceof ServerError) {
                    Log.d("ServerError>>>>>>>>>", "ServerError.......");

                } else if (error instanceof NetworkError) {
                    Log.d("NetworkError>>>>>>>>>", "NetworkError.......");

                } else if (error instanceof ParseError) {
                    Log.d("ParseError>>>>>>>>>", "ParseError.......");

                }else if (error instanceof TimeoutError) {
                    Log.d("TimeoutError>>>>>>>>>", "TimeoutError.......");

                }

            }
        };
    }



}



