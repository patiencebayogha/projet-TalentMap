package com.viseo.talentmap.createaccount.loader;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.viseo.talentmap.common.exception.TalentMapException;
import com.viseo.talentmap.common.loader.AbstractLoader;
import com.viseo.talentmap.createaccount.manager.AccountManager;

/**
 * Created by patiencebayogha on 10/02/15.
 * modification 1.0: 11/02/2015
 * * allows loading in background
 */
public class AccountLoader extends AbstractLoader<Boolean> {

    public static final String NAME = "name";

    public static final String SURNAME = "surname";
    //For email
    public static final String EMAIL = "email";


    //Used to render a password field for a String property.
    public static final String PASSWORD = "password";
    protected AccountManager accountManager;
    //declare Bundle
    private Bundle bundle;


    public AccountLoader(Activity activity, Bundle bundle) {

        super(activity);
        this.accountManager = new AccountManager();
        this.bundle = bundle;
    }


    @Override
    public Boolean loadInBackground() {

        //Called on a thread to perform the actual load and return a result of load operation
        String email = bundle.getString(EMAIL);
        String name = bundle.getString(NAME);
        String surname = bundle.getString(SURNAME);
        String password = bundle.getString(PASSWORD);

        try {
            result = accountManager.create(email, password, name, surname);
        } catch (TalentMapException e) {
            Log.e(getClass().getCanonicalName(), "error during loading: AccountLoader.");
        }

        return result;
    }


}