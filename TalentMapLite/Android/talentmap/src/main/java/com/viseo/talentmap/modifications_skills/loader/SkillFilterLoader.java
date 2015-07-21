package com.viseo.talentmap.modifications_skills.loader;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.viseo.talentmap.common.exception.TalentMapException;
import com.viseo.talentmap.common.loader.AbstractLoader;
import com.viseo.talentmap.modifications_skills.data.FilterlistSkills;
import com.viseo.talentmap.modifications_skills.manager.SkillsManager;

import java.util.ArrayList;

/**
 * Created by patiencebayogha on 02/04/15.
 * show all skills by category
 * Filter skills by category
 * 23/04/15
 */
public class SkillFilterLoader extends AbstractLoader<ArrayList<FilterlistSkills>> {

    public static final String CATEGORY = "category";
    protected SkillsManager skillsManager;
    private Bundle bundle;

    /**
     * Constructeur par défaut.
     *
     * @param activity Le contexte
     */


    public SkillFilterLoader(Activity activity) {
        super(activity);
        this.skillsManager = new SkillsManager();

    }


    @Override
    public ArrayList<FilterlistSkills> loadInBackground() {
        try {
            result = skillsManager.list();
        } catch (TalentMapException e) {
            Log.e(getClass().getCanonicalName(), "SkillFilterLoader:loadInBackground : Error during loading users list ", e);
        }
        return result;
    }


}

