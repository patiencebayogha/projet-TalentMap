package com.viseo.talentmap.lite.profil.loader;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.viseo.talentmap.common.exception.TalentMapException;
import com.viseo.talentmap.common.loader.AbstractLoader;
import com.viseo.talentmap.lite.profil.manager.ProfilManager;

/**
 * Created by patiencebayogha on 30/03/15.
 * Loader for delete Skill of user
 */
public class DeleteSkillLoader extends AbstractLoader<Boolean> {


    //variables declaration

    //Used to render a login field for a String property.
    public static final String EMAIL = "email";

    public static final String SKILL = "skill";


    //for call LoginManager.java
    protected ProfilManager profilManager;
    //declare Bundle
    private Bundle bundle;


    public DeleteSkillLoader(Activity myActivity, Bundle bundle) {


        super(myActivity);
        this.profilManager = new ProfilManager();
        this.bundle = bundle;
    }


    @Override
    public Boolean loadInBackground() {


        //Called on a thread to perform the actual load and return a result of load operation
        String email = bundle.getString(EMAIL);
        String skill = bundle.getString(SKILL);
        try {
            result = profilManager.deleteSkill(email, skill);
        } catch (TalentMapException e) {

            Log.e(getClass().getCanonicalName(), "error during loading: DeleteSkillLoader.");
        }

        return result;
    }


}