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


package com.viseo.talentmap.login.activity;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.LoaderManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.viseo.talentmap.R;
import com.viseo.talentmap.common.utils.LoaderUtils;
import com.viseo.talentmap.createaccount.activity.AccountActivity;
import com.viseo.talentmap.lite.MainActivity;
import com.viseo.talentmap.login.loader.LoginLoader;
import com.viseo.talentmap.preferences.TalentPreferences;


public class LoginActivity extends Activity implements LoaderManager.LoaderCallbacks<Boolean> {

    public static final int LOADER_CONNECTION = LoaderUtils.getInstance().getLoaderId();                //add connection loader id
    public static final String CHECK = "CHECK";         //Used for sharePreference checkbox
    public static final String EMAIL = "EMAIL";          //Used for sharePreference email
    public static final String PASSWORD = "PASSWORD";       //Used for sharePreference password
    private Button btnconnect;          //button for connect user
    private Button btncreateaccount;                //button for create account
    private EditText email;                         //enter email in display
    private EditText password;                      //enter password in display
    private CheckBox seSouvenirDeMoi;               //ajouter code pour activer ou non checkbox
    private TextView errorLoginPassword;            //if error Login and password show text

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity);
        hasConnection();
        initView();//recuperate all initialization of view components


        String emailSaved = TalentPreferences.getPrefsEmail(LoginActivity.this);
        if (emailSaved != null) {
            email.setText(emailSaved);}


            //if onClick connect button you verified if email, password valide and load Loader
        btnconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //save sharedPreferences for recuperate email when you click in checkbox


                /**
                 * Checkbox method for remember me
                 */
                seSouvenirDeMoi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (seSouvenirDeMoi.isChecked()) {

                            //save sharedPreferences for recuperate email when you click in checkbox

                            String saveEmail = email.getText().toString();
                            TalentPreferences.savePrefsEmail(LoginActivity.this, saveEmail);
                            Log.i("Remember Me", "checked,preference added");
                            Toast.makeText(LoginActivity.this, "Email save" + "" + "" + saveEmail, Toast.LENGTH_LONG).show();

                        } else {
                            Log.i("Remember Me", "Unchecked, preferences removed");
                        }
                    }
                });
                final String newText = email.getText().toString().toUpperCase();     //used for email
                final String pwd = password.getText().toString();                   //used for password

                //testing correct email or error invalid email
                if (!newText.matches("[A-Z0-9]+([.%+-]?[A-Z0-9-]+)*@[A-Z0-9.-]+\\.[A-Z]{2,4}") && (pwd.length() == 0)) {
                    email.requestFocus();
                    email.setBackgroundResource(R.drawable.edit_text_invalid);
                    password.requestFocus();
                    password.setBackgroundResource(R.drawable.edit_text_invalid);
                    errorLoginPassword.setVisibility(View.VISIBLE);

                    errorLoginPassword.setText("Email et/ou mot de passe invalide(s)");
                } else if (newText.matches("[A-Z0-9]+([.%+-]?[A-Z0-9-]+)*@[A-Z0-9.-]+\\.[A-Z]{2,4}") && (pwd.length() == 0)) {
                    email.requestFocus();
                    email.setBackgroundResource(R.drawable.edit_text_invalid);
                    password.requestFocus();
                    password.setBackgroundResource(R.drawable.edit_text_invalid);
                    errorLoginPassword.setVisibility(View.VISIBLE);
                    errorLoginPassword.setText("Email et/ou mot de passe invalide(s)");

                } else if ((newText.isEmpty()) || (pwd.isEmpty())) {
                    email.requestFocus();
                    email.setBackgroundResource(R.drawable.edit_text_invalid);
                    password.requestFocus();
                    password.setBackgroundResource(R.drawable.edit_text_invalid);
                    errorLoginPassword.setVisibility(View.VISIBLE);
                    errorLoginPassword.setText("Champs email et/ou mot de passe vide(s)");

                } else {
                    email.setBackgroundResource(R.drawable.edit_text_valid);
                    password.setBackgroundResource(R.drawable.edit_text_valid);
                    errorLoginPassword.setVisibility(View.GONE);
                    launchBarDialog(v);
                    Bundle bundle = new Bundle();
                    // Storing data into bundle
                    bundle.putString(LoginLoader.LOGIN, email.getText().toString());
                    bundle.putString(LoginLoader.PASSWORD, password.getText().toString());
                    if (getLoaderManager().getLoader(LOADER_CONNECTION) == null) {
                        getLoaderManager().initLoader(LOADER_CONNECTION, bundle, LoginActivity.this);

                    } else {
                        getLoaderManager().restartLoader(LOADER_CONNECTION, bundle, LoginActivity.this);
                    }
                }


                // Creating Bundle object

            }
        });


        //if click in button create account, you go to other view
        btncreateaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, AccountActivity.class);
                startActivity(i);

            }
        });


    }


    private void initView() {
        /**
         * initialization of view components
         */
        email = (EditText) findViewById(R.id.activity_new_login_email); //edit email
        password = (EditText) findViewById(R.id.activity_new_login_password);//edit password
        seSouvenirDeMoi = (CheckBox) findViewById(R.id.activity_new_login_remember_me); //checkbox for save email
        btnconnect = (Button) findViewById(R.id.activity_new_login_connect);   //Button for Connect a User
        btncreateaccount = (Button) findViewById(R.id.activity_new_login_create_account);// Button for CreateNewUsers
        errorLoginPassword = (TextView) findViewById(R.id.iferror);     //textview for error login or password


    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        //Instantiate and return a new Loader for the given ID
        return new LoginLoader(this, args);
    }

    @Override
    public void onLoadFinished(Loader loader, Boolean data) {
        //Called when a previously created loader has finished its load.
        if (data != null) {

            if (data == true) {
                Intent u = new Intent(LoginActivity.this, MainActivity.class);
                u.putExtra(MainActivity.EMAIL, email.getText().toString());
                startActivity(u);

            } else {
                errorLoginPassword.setVisibility(View.VISIBLE);
                errorLoginPassword.setText("Email et/ou mot de passe invalide(s)");
                password.setBackgroundResource(R.drawable.edit_text_invalid);
                email.setBackgroundResource(R.drawable.edit_text_invalid);
            }
            return;
        }
        errorLoginPassword.setVisibility(View.VISIBLE);
        errorLoginPassword.setText("Email et/ou mot de passe invalide(s)");
        password.setBackgroundResource(R.drawable.edit_text_invalid);
        email.setBackgroundResource(R.drawable.edit_text_invalid);

    }

    @Override
    public void onLoaderReset(Loader loader) {
        //called when a previously created loader is being reset, and thus making its data unavailable.
    }



    public void launchBarDialog(View view) {
        /**
         * method for launch Bar Dialog and notified connection== ok
         */
        final ProgressDialog ringProgressDialog = ProgressDialog.show(LoginActivity.this, "Please wait ...", "Connexion en cours...", true);
        ringProgressDialog.setCancelable(true);

        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    // Here you should write your time consuming task...
                    // Let the progress ring for 30 seconds...
                    Thread.sleep(3000);
                } catch (Exception e) {
                }
                ringProgressDialog.dismiss();
            }
        }).start();


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
        NetworkInfo mobileNetwork=null;
        NetworkInfo activeNetwork=null;
        if (wifiNetwork != null || mobileNetwork!=null || activeNetwork!=null) {

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
                    "No signal found or Internet connection is not connected.")
                    .setTitle("Error")
                    .setNeutralButton("OK",
                            new DialogInterface.OnClickListener() {

                                public void onClick(
                                        DialogInterface dialog,
                                        int which) {
                                    // TODO Auto-generated method
                                    // stub

                                }
                            }).show();
        }

        return false;
    }



}
