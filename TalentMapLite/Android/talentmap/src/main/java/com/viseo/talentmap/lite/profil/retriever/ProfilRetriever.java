package com.viseo.talentmap.lite.profil.retriever;

import android.util.Log;

import com.fasterxml.jackson.core.type.TypeReference;
import com.viseo.talentmap.common.exception.TalentMapAuthenticateException;
import com.viseo.talentmap.common.exception.TalentMapException;
import com.viseo.talentmap.common.retriever.AbstractRetriever;
import com.viseo.talentmap.common.service.AuthService;
import com.viseo.talentmap.common.service.UsersService;
import com.viseo.talentmap.lite.profil.data.ProfilUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;

/**
 * Created by patiencebayogha on 23/02/15.
 * moidification 1/04/2015
 * last revision:23/04/2015
 * Permit to access Webservice
 */
public class ProfilRetriever extends AbstractRetriever {
    private static final String PHOTO = "photo";
    private static final String EMAIL = "email";
    private static final String SKILL = "skill";
    private static final String LEVEL = "level";

    protected AuthService authService;
    protected UsersService usersService;


    public ProfilRetriever() {
        authService = new AuthService();
        usersService = new UsersService();


    }

    public ProfilUser getMe() throws TalentMapException {
        InputStream inputStream = authService.getMe();

        ProfilUser result = jsonStream.getObject(inputStream, new TypeReference<ProfilUser>() {

        });


        return result;

    }


    public boolean updateName(String email, String name, String surname) throws TalentMapException {

        ProfilUser profilUser = new ProfilUser();
        profilUser.setEmail(email);
        profilUser.setSurname(surname);
        profilUser.setName(name);
        String parameters = jsonStream.getJSON(profilUser);

        try {
            usersService.updateName(parameters);
        } catch (TalentMapAuthenticateException e) {
            return false;
        }
        return true;
    }


    public boolean deleteSkill(String email, String skill) throws TalentMapException {

        String parameters = null;

        try {

            JSONObject jsonObject = new JSONObject();
            jsonObject.put(EMAIL, email);
            jsonObject.put(SKILL, skill);

            parameters = jsonObject.toString();

        } catch (JSONException e) {
            Log.e(getClass().getCanonicalName(), "error during loading: ProfilRetriever.");
        }

        try {

            usersService.deleteSkill(parameters);
        } catch (TalentMapAuthenticateException e) {
            return false;
        }
        return true;
    }

    public boolean updateSkill(String email, String skill, int level) throws TalentMapException {

        String parameters = null;

        try {

            JSONObject jsonObject = new JSONObject();
            jsonObject.put(EMAIL, email);
            jsonObject.put(SKILL, skill);
            jsonObject.put(LEVEL, level);

            parameters = jsonObject.toString();

        } catch (JSONException e) {

            Log.e(getClass().getCanonicalName(), "ProfilRetriever:loadInBackground : Error during loading ", e);

        }

        try {

            usersService.updateSkill(parameters);
        } catch (TalentMapAuthenticateException e) {
            return false;
        }
        return true;

    }


    public boolean updatePhoto(String photo) throws TalentMapException {

        String parameters = null;

        try {

            JSONObject jsonObject = new JSONObject();
            jsonObject.put(PHOTO, photo);

            parameters = jsonObject.toString();

        } catch (JSONException e) {
            Log.e(getClass().getCanonicalName(), "error during loading: ProfilRetriever.: update photo");
        }

        try {

            usersService.updatePhoto(parameters);
        } catch (TalentMapAuthenticateException e) {
            return false;
        }
        return true;
    }


}