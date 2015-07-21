package com.viseo.talentmap.createaccount.activity;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.viseo.talentmap.R;
import com.viseo.talentmap.common.utils.LoaderUtils;
import com.viseo.talentmap.createaccount.loader.ActivateAccountLoader;

/**
 * Created by patiencebayogha on 12/03/15.
 *
 *  last modif: 27/04/2015
 * * permit to confirm the creation of account
 * A page with a text message appears for confirm that account is in waiting the confirmation
 */
public class ActivationActivity extends Activity implements LoaderManager.LoaderCallbacks<Boolean> {
    TextView response;
    private TextView email;
    public static String EMAIL_ACCOUNT = "email";
    private static final int LOADER_ACTIVATE_ACCOUNT = LoaderUtils.getInstance().getLoaderId();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activation_new_account);



        response = (TextView) findViewById(R.id.response);
        email = (TextView) findViewById(R.id.email);



        Bundle bundle = new Bundle();

        // Storing data into bundle
        bundle.putString(ActivateAccountLoader.EMAIL, email.getText().toString());
        bundle.putString(ActivateAccountLoader.ACTIVATE, response.getText().toString());
        if (getLoaderManager().getLoader(LOADER_ACTIVATE_ACCOUNT) == null) {
            getLoaderManager().initLoader(LOADER_ACTIVATE_ACCOUNT, bundle, ActivationActivity.this);
        } else {
            getLoaderManager().restartLoader(LOADER_ACTIVATE_ACCOUNT, bundle, ActivationActivity.this);
        }



    }


    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        //Instantiate and return a new Loader for the given ID
        return new ActivateAccountLoader(this, args);
    }

    @Override
    //Called when a previously created loader has finished its load.
    public void onLoadFinished(Loader loader, Boolean data) {
        if (data != null) {
            if (data == true) {
                Log.d(getClass().getCanonicalName(), "ActivationActivity:loadInBackground : data true===>ok");

        }else {

                Log.e(getClass().getCanonicalName(), "ActivationActivity:loadInBackground : Error during loading login ");
            }
            return;
        }
    }

    @Override
    //called when a previously created loader is being reset, and thus making its data unavailable.
    public void onLoaderReset(Loader loader) {


    }


}
