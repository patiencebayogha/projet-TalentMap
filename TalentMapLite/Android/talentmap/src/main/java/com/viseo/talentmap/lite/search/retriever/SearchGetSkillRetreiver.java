package com.viseo.talentmap.lite.search.retriever;

import android.util.Log;

import com.fasterxml.jackson.core.type.TypeReference;
import com.viseo.talentmap.common.exception.TalentMapException;
import com.viseo.talentmap.common.retriever.AbstractRetriever;
import com.viseo.talentmap.common.service.SkillsService;
import com.viseo.talentmap.lite.search.data.SearchSkillsUsers;

import java.io.InputStream;

/**
 * Created by patiencebayogha on 17/04/15.
 */
public class SearchGetSkillRetreiver extends AbstractRetriever {

    protected SkillsService skillsService;

    public SearchGetSkillRetreiver() {
        skillsService = new SkillsService();
    }

    public SearchSkillsUsers get(String skill) throws TalentMapException {
        Log.d(getClass().getCanonicalName(), "search Skill users");

        InputStream inputStream = skillsService.get(skill);
        SearchSkillsUsers result = jsonStream.getObject(inputStream, new TypeReference<SearchSkillsUsers>() {
        });
        return result;
    }

}
