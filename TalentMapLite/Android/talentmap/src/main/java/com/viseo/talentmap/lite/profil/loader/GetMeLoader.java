package com.viseo.talentmap.lite.profil.loader;

import android.app.Activity;

import com.viseo.talentmap.common.exception.TalentMapException;
import com.viseo.talentmap.common.loader.AbstractLoader;
import com.viseo.talentmap.lite.profil.data.ProfilUser;
import com.viseo.talentmap.lite.profil.manager.ProfilManager;


/**
 * Created by patiencebayogha on 23/02/15.
 * Return the current user
 */


public class GetMeLoader extends AbstractLoader<ProfilUser> {

    //variables declaration

    public static final String EMAIL = "email";


    //for call LoginManager.java
    protected ProfilManager profilManager;


    public GetMeLoader(Activity activity) {
        super(activity);

        this.profilManager = new ProfilManager();

    }

    @Override
    public ProfilUser loadInBackground() {
        try {
            result = profilManager.getMe();
        } catch (TalentMapException e) {

        }
        return result;
    }


}
