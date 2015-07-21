package com.viseo.talentmap.lite.search.manager;

import android.util.Log;

import com.viseo.talentmap.common.exception.TalentMapException;
import com.viseo.talentmap.lite.search.data.SearchSkillsUsers;
import com.viseo.talentmap.lite.search.retriever.SearchGetSkillRetreiver;

/**
 * Created by patiencebayogha on 17/04/15.
 * 23/04/15
 * retreiver to searchGetSkillRetreiver
 */
public class SearchGetSkillManager {

    protected SearchGetSkillRetreiver searchGetSkillRetreiver;

    public SearchSkillsUsers get(String skill) throws TalentMapException {
        searchGetSkillRetreiver = new SearchGetSkillRetreiver();
        Log.d(getClass().getCanonicalName(), "Skill Manager ");
        return searchGetSkillRetreiver.get(skill);

    }

}
