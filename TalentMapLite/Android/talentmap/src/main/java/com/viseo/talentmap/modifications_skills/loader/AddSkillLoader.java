package com.viseo.talentmap.modifications_skills.loader;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.viseo.talentmap.common.exception.TalentMapException;
import com.viseo.talentmap.common.loader.AbstractLoader;
import com.viseo.talentmap.modifications_skills.manager.SkillsManager;

/**
 * Created by patiencebayogha on 01/04/15.
 * 23/04/15
 * add New skill
 */
public class AddSkillLoader extends AbstractLoader<Boolean> {


    //variables declaration

    //Used to render a login field for a String property.
    public final static String EMAIL = "email";


    public final static String CATEGORY = "category";

    public final static int LEVEL = 0;

    public final static String SKILL = "skill";

    public final static String ID = "_id";


    //for call LoginManager.java
    protected SkillsManager skillsManager;
    //declare Bundle
    private Bundle bundle;


    public AddSkillLoader(Activity myActivity, Bundle bundle) {


        super(myActivity);
        this.skillsManager = new SkillsManager();
        this.bundle = bundle;
    }


    @Override
    public Boolean loadInBackground() {


        //Called on a thread to perform the actual load and return a result of load operation
        String email = bundle.getString(EMAIL);
        String skill = bundle.getString(SKILL);
        String category = bundle.getString(CATEGORY);
        int level = bundle.getInt(String.valueOf(0));
        try {
            result = skillsManager.addSkill(email, skill, level, category);
        } catch (TalentMapException e) {
            Log.e(getClass().getCanonicalName(), "AddSkillLoader:loadInBackground : Error during update add skill ", e);
        }

        return result;
    }


}

