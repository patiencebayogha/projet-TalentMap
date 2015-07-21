package com.viseo.talentmap.lite.profil.manager;

import android.util.Log;

import com.viseo.talentmap.common.exception.TalentMapException;
import com.viseo.talentmap.lite.profil.data.ProfilUser;
import com.viseo.talentmap.lite.profil.retriever.ProfilRetriever;

/**
 * Created by patiencebayogha on 23/02/15.
 * last revision:23/04/2015
 * Tests and retrieve profilretriever
 */
public class ProfilManager {

    protected ProfilRetriever profilRetriever;


    public ProfilManager() {

        profilRetriever = new ProfilRetriever();
    }

    //getMe
    public ProfilUser getMe() throws TalentMapException {

        return profilRetriever.getMe();

    }


    //update_name
    public boolean updateName(String email, String name, String surname) throws TalentMapException {


        //test if we do not enter  login or/and password
        if (email == null) {
            return false;
        }


        if (email.isEmpty()) {
            return false;
        }

        if (!email.toUpperCase().matches("[A-Z0-9]+([.%+-]?[A-Z0-9-]+)*@[A-Z0-9.-]+\\.[A-Z]{2,4}")) {
            return false;
        }


        Log.d(getClass().getCanonicalName(), " inputstream manager");
        return profilRetriever.updateName(email, name, surname);

    }

//delete Skill


    //update_name
    public boolean deleteSkill(String email, String skill) throws TalentMapException {

        return profilRetriever.deleteSkill(email, skill);


    }

    public boolean updateSkill(String email, String skill, int level) throws TalentMapException {

        return profilRetriever.updateSkill(email, skill, level);
    }

    public boolean updatePhoto(String photo) throws TalentMapException {

        return profilRetriever.updatePhoto(photo);
    }

}