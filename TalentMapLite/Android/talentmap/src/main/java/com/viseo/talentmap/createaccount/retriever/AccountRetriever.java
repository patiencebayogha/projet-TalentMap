package com.viseo.talentmap.createaccount.retriever;

import android.util.Log;

import com.viseo.talentmap.common.exception.TalentMapAuthenticateException;
import com.viseo.talentmap.common.exception.TalentMapException;
import com.viseo.talentmap.common.retriever.AbstractRetriever;
import com.viseo.talentmap.common.service.UsersService;
import com.viseo.talentmap.createaccount.data.AccountUser;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by patiencebayogha on 13/02/15.
 * Permit to access webservice and pointer vers json
 */
public class AccountRetriever extends AbstractRetriever {

    protected UsersService usersService;

    public static final String ACTIVATE = "activationId";
    //For email
    public static final String EMAIL = "email";

    public AccountRetriever() {
        usersService = new UsersService();
    }


    public boolean create(String email, String password, String name, String surname) throws TalentMapException {

        AccountUser accountUser = new AccountUser();
        accountUser.setEmail(email);
        accountUser.setName(name);
        accountUser.setSurname(surname);
        accountUser.setPassword(password);
        String param = jsonStream.getJSON(accountUser);

        try {
            usersService.create(param);
        } catch (TalentMapAuthenticateException e) {
            return false;
        }
        return true;
    }


    public boolean activate(String email, String activate) throws TalentMapException {

        String parameters = null;

        try {

            JSONObject jsonObject = new JSONObject();
            jsonObject.put(EMAIL, email);
            jsonObject.put(ACTIVATE, activate);

            parameters = jsonObject.toString();

        } catch (JSONException e) {

            Log.e(getClass().getCanonicalName(), "ProfilRetriever:loadInBackground : Error during loading ", e);

        }

        try {

            usersService.activate(parameters);
        } catch (TalentMapAuthenticateException e) {
            return false;
        }
        return true;

    }



}