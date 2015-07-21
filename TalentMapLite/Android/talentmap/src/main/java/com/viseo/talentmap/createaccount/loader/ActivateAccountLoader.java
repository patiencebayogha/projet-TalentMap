package com.viseo.talentmap.createaccount.loader;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.viseo.talentmap.common.exception.TalentMapException;
import com.viseo.talentmap.common.loader.AbstractLoader;
import com.viseo.talentmap.createaccount.manager.AccountManager;

/**
 * Created by patiencebayogha on 27/04/15.
 */
public class ActivateAccountLoader  extends AbstractLoader<Boolean> {


    public static final String ACTIVATE = "activationId";
    //For email
    public static final String EMAIL = "email";


    protected AccountManager accountManager;
    //declare Bundle
    private Bundle bundle;


    public ActivateAccountLoader(Activity activity, Bundle bundle) {

        super(activity);
        this.accountManager = new AccountManager();
        this.bundle = bundle;
    }


    @Override
    public Boolean loadInBackground() {

        //Called on a thread to perform the actual load and return a result of load operation
        String email = bundle.getString(EMAIL);
        String activate = bundle.getString(ACTIVATE);

        try {
            result = accountManager.activate(email,activate);
        } catch (TalentMapException e) {
            Log.e(getClass().getCanonicalName(), "error during loading: AccountLoader.");
        }

        return result;
    }

}
