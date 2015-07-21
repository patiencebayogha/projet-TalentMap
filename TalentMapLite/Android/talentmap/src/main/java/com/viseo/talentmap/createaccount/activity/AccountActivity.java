/**
 * Created by PBA3353 on 6/02/2015.
 * modification 1.0: 11/02/2015
 *
 *  last modif: 27/04/2015
 * Activity allows the registration off a new user
 *
 */

package com.viseo.talentmap.createaccount.activity;

import android.app.Activity;
import android.app.LoaderManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.viseo.talentmap.R;
import com.viseo.talentmap.common.utils.LoaderUtils;
import com.viseo.talentmap.createaccount.loader.AccountLoader;

/**
 * Created by patiencebayogha on 10/02/15.
 * modification 1.0: 25/03/2015
 * * permit to acces to create account account
 * The user add email and password and if validate
 * registration a confirmation message is sent with a link for validation create account
 * If no  valid email or if password different of confirm password a error message appears
 */

public class AccountActivity extends Activity implements LoaderManager.LoaderCallbacks<Boolean> {

    //variables declaration

    private static final int LOADER_CREATE_ACCOUNT = LoaderUtils.getInstance().getLoaderId();
    private EditText emailviseo;
    private EditText password;
    private EditText confirmPassword;
    private Button next;
    private TextView error;
    private TextView errorPswd;
    private TextView email;
    private TextView pwd;
    private TextView confPwd;
    private TextView errorName;
    private TextView nameNewUser;
    private TextView surnameNewuser;


    @Override
    // Initialization activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_account_activity);

        //Calls widgets
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
                    // Creating Bundle object
                    Bundle bundle = new Bundle();
                    // Storing data into bundle
                    bundle.putString(AccountLoader.EMAIL, emailviseo.getText().toString());
                    bundle.putString(AccountLoader.PASSWORD, password.getText().toString());
                    bundle.putString(AccountLoader.NAME, nameNewUser.getText().toString());
                    bundle.putString(AccountLoader.SURNAME, surnameNewuser.getText().toString());
                    if (getLoaderManager().getLoader(LOADER_CREATE_ACCOUNT) == null) {
                        getLoaderManager().initLoader(LOADER_CREATE_ACCOUNT, bundle, AccountActivity.this);

                    } else {
                        getLoaderManager().restartLoader(LOADER_CREATE_ACCOUNT, bundle, AccountActivity.this);
                    }
                }

            }

        });


    }


    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        //Instantiate and return a new Loader for the given ID
        return new AccountLoader(this, args);
    }

    @Override
    //Called when a previously created loader has finished its load.
    public void onLoadFinished(Loader loader, Boolean data) {
        if (data != null) {
            if (data == true) {
                Intent u = new Intent(AccountActivity.this, ActivationActivity.class);
                startActivity(u);
            } else {

                error.setVisibility(View.VISIBLE);
                emailviseo.setBackgroundResource(R.drawable.edit_text_invalid);
                nameNewUser.setBackgroundResource(R.drawable.edit_text_invalid);
                surnameNewuser.setBackgroundResource(R.drawable.edit_text_invalid);
                password.setBackgroundResource(R.drawable.edit_text_invalid);
                confirmPassword.setBackgroundResource(R.drawable.edit_text_invalid);
                error.setText("L'email doit être une adresse Viseo ou Novedia. ");

            }

            return;
        }
        error.setVisibility(View.VISIBLE);
        error.setText("L'email doit être une adresse Viseo ou Novedia. ");
        errorPswd.setVisibility(View.VISIBLE);

        nameNewUser.setBackgroundResource(R.drawable.edit_text_invalid);
        surnameNewuser.setBackgroundResource(R.drawable.edit_text_invalid);
        emailviseo.setBackgroundResource(R.drawable.edit_text_invalid);
        password.setBackgroundResource(R.drawable.edit_text_invalid);
        confirmPassword.setBackgroundResource(R.drawable.edit_text_invalid);


    }

    @Override
    //called when a previously created loader is being reset, and thus making its data unavailable.
    public void onLoaderReset(Loader loader) {

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



