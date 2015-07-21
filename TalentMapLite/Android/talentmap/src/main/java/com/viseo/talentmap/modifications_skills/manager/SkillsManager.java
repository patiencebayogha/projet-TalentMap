package com.viseo.talentmap.modifications_skills.manager;

import android.util.Log;

import com.viseo.talentmap.common.exception.TalentMapException;
import com.viseo.talentmap.modifications_skills.data.FilterlistSkills;
import com.viseo.talentmap.modifications_skills.retriever.SkillsRetriever;

import java.util.ArrayList;

/**
 * Created by patiencebayogha on 13/02/15.
 */
public class SkillsManager {
    protected SkillsRetriever skillsRetriever;


    public SkillsManager() {
        Log.d(getClass().getCanonicalName(), "create Manager Skill");
        skillsRetriever = new SkillsRetriever();
    }


    public ArrayList<FilterlistSkills> get(String category) throws TalentMapException {
        Log.d(getClass().getCanonicalName(), "Skill Manager ");
        return skillsRetriever.get(category);

    }

    public ArrayList<FilterlistSkills> list() throws TalentMapException {
        Log.d(getClass().getCanonicalName(), "Skill Manager ");
        return skillsRetriever.list();

    }


    //test add skill
    public boolean addSkill(String email, String skill, int level, String category) throws TalentMapException {

        return skillsRetriever.addSkill(email, skill, level, category);


    }

}
