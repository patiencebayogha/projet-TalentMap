package com.viseo.talentmap.createaccount.manager;

import android.app.Activity;

import com.viseo.talentmap.common.exception.TalentMapException;
import com.viseo.talentmap.createaccount.retriever.AccountRetriever;

/**
 * Created by patiencebayogha on 10/02/15.
 * modification 1.0: 11/02/15
 * manages the display et la DTO create account
 */
public class AccountManager extends Activity {

    protected AccountRetriever accountRetriever;


    public AccountManager() {
        accountRetriever = new AccountRetriever();
    }

    //method use to register a endpoint
    public boolean create(String email, String password, String name, String surname) throws TalentMapException {


        //test if we do not enter  login or/and password
        if (email == null || password == null) {
            return false;
        }


        if (email.isEmpty() || password.isEmpty()) {
            return false;
        }

        if (!email.toUpperCase().matches("[A-Z0-9]+([.%+-]?[A-Z0-9-]+)*@[A-Z0-9.-]+\\.[A-Z]{2,4}")) {
            return false;
        }


        return accountRetriever.create(email, password, name, surname);


    }

    public boolean activate(String email, String activate) throws TalentMapException {

        return accountRetriever.activate(email, activate);
    }
}
