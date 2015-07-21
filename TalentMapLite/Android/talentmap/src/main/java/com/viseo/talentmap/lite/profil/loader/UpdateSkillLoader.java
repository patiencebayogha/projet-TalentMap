package com.viseo.talentmap.lite.profil.loader;

/**
 * Created by patiencebayogha on 13/04/15.
 */

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.viseo.talentmap.common.exception.TalentMapException;
import com.viseo.talentmap.common.loader.AbstractLoader;
import com.viseo.talentmap.lite.profil.manager.ProfilManager;

/**
 * Created by patiencebayogha on 01/04/15.
 * Skill to update in profil user
 */
public class UpdateSkillLoader extends AbstractLoader<Boolean> {


    //variables declaration

    //Used to render a login field for a String property.
    public static final String EMAIL = "email";


    public static final String LEVEL = "level";

    public static final String SKILL = "skill";


    //for call LoginManager.java
    protected ProfilManager profilManager;
    //declare Bundle
    private Bundle bundle;


    public UpdateSkillLoader(Activity myActivity, Bundle bundle) {


        super(myActivity);
        this.profilManager = new ProfilManager();
        this.bundle = bundle;
    }

    @Override
    public Boolean loadInBackground() {


        //Called on a thread to perform the actual load and return a result of load operation
        String email = bundle.getString(EMAIL);
        String skill = bundle.getString(SKILL);
        int level = bundle.getInt(LEVEL);
        try {
            result = profilManager.updateSkill(email, skill, level);
        } catch (TalentMapException e) {
            Log.e(getClass().getCanonicalName(), "UpdateSkillLoader:loadInBackground : Error during loading updating skill ", e);
        }

        return result;
    }


}

