package com.viseo.talentmap.modifications_skills.retriever;

import android.util.Log;

import com.fasterxml.jackson.core.type.TypeReference;
import com.viseo.talentmap.common.exception.TalentMapAuthenticateException;
import com.viseo.talentmap.common.exception.TalentMapException;
import com.viseo.talentmap.common.retriever.AbstractRetriever;
import com.viseo.talentmap.common.service.SkillsService;
import com.viseo.talentmap.common.service.UsersService;
import com.viseo.talentmap.modifications_skills.data.FilterlistSkills;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by patiencebayogha on 13/02/15.
 */
public class SkillsRetriever extends AbstractRetriever {

    protected String CATEGORY = "category";
    protected String EMAIL = "email";
    protected String SKILL = "skill";
    protected String LEVEL = "level";
    protected SkillsService skillsService;
    protected UsersService usersService;

    public SkillsRetriever() {
        skillsService = new SkillsService();
        usersService = new UsersService();
    }


    public ArrayList<FilterlistSkills> get(String category) throws TalentMapException {
        Log.d(getClass().getCanonicalName(), "Skill list");

        InputStream inputStream = skillsService.get(category);
        ArrayList<FilterlistSkills> result = jsonStream.getObject(inputStream, new TypeReference<ArrayList<FilterlistSkills>>() {
        });
        return result;
    }

    public ArrayList<FilterlistSkills> list() throws TalentMapException {
        Log.d(getClass().getCanonicalName(), "Skill list Filter");


        InputStream inputStream = skillsService.list();
        ArrayList<FilterlistSkills> result = jsonStream.getObject(inputStream, new TypeReference<ArrayList<FilterlistSkills>>() {
        });
        return result;

    }

    public boolean addSkill(String email, String skill, int level, String category) throws TalentMapException {

        String parameters = null;

        try {

            JSONObject jsonObject = new JSONObject();
            jsonObject.put(EMAIL, email);
            jsonObject.put(SKILL, skill);
            jsonObject.put(LEVEL, level);
            jsonObject.put(CATEGORY, category);

            parameters = jsonObject.toString();

        } catch (JSONException e) {
            Log.e(getClass().getCanonicalName(), e.getMessage());

            Log.e(getClass().getCanonicalName(), "SkillsRetriever:loadInBackground : Error during loading data ", e);
        }

        try {

            usersService.addSkill(parameters);
        } catch (TalentMapAuthenticateException e) {
            return false;
        }
        return true;

    }


}
