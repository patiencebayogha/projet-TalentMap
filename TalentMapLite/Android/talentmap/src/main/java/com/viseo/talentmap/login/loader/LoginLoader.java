/**
 * Created by patiencebayogha on 9/02/2015.
 * modification 1.0: 10/02/2015
 * allows loading in background
 *
 */

package com.viseo.talentmap.login.loader;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.viseo.talentmap.common.exception.TalentMapException;
import com.viseo.talentmap.common.loader.AbstractLoader;
import com.viseo.talentmap.login.manager.LoginManager;


public class LoginLoader extends AbstractLoader<Boolean> {

    //variables declaration

    //Used to render a login field for a String property.
    public static final String LOGIN = "login";

    //Used to render a password field for a String property.
    public static final String PASSWORD = "password";
    //to recoup the result

    //for call LoginManager.java
    protected LoginManager loginManager;
    //declare Bundle
    private Bundle bundle;

    public LoginLoader(Activity activity, Bundle bundle) {

        super(activity);
        this.loginManager = new LoginManager();
        this.bundle = bundle;
    }


    @Override
    public Boolean loadInBackground() {

        //Called on a thread to perform the actual load and return a result of load operation
        String login = bundle.getString(LOGIN);
        String password = bundle.getString(PASSWORD);
        try {
            result = loginManager.login(login, password);
        } catch (TalentMapException e) {
            Log.e(getClass().getCanonicalName(), "LoginLoader:loadInBackground : Error during loading login ", e);
        }

        return result;
    }
}
