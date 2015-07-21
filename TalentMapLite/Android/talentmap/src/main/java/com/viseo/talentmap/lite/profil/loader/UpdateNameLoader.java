package com.viseo.talentmap.lite.profil.loader;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.viseo.talentmap.common.exception.TalentMapException;
import com.viseo.talentmap.common.loader.AbstractLoader;
import com.viseo.talentmap.lite.profil.manager.ProfilManager;

/**
 * Created by patiencebayogha on 26/03/15.
 * Name and surname to update for user
 */
public class UpdateNameLoader extends AbstractLoader<Boolean> {


    //variables declaration

    //Used to render a login field for a String property.
    public static final String EMAIL = "email";

    //Used to render a password field for a String property.
    public static final String NAME = "name";


    //Used to render a password field for a String property.
    public static final String SURNAME = "surname";

    //for call LoginManager.java
    protected ProfilManager profilManager;
    //declare Bundle
    private Bundle bundle;


    public UpdateNameLoader(Activity myActivity, Bundle bundle) {


        super(myActivity);
        this.profilManager = new ProfilManager();
        this.bundle = bundle;
    }


    @Override
    public Boolean loadInBackground() {


        //Called on a thread to perform the actual load and return a result of load operation
        if (bundle != null) {
            String email = bundle.getString(EMAIL);
            String name = bundle.getString(NAME);
            String surname = bundle.getString(SURNAME);
            try {
                result = profilManager.updateName(email, name, surname);
            } catch (TalentMapException e) {
                Log.e("TAG", "UpdateProfilLoader:loadInBackground : error during loading update name", e);
            }
        }

        return result;
    }
}
