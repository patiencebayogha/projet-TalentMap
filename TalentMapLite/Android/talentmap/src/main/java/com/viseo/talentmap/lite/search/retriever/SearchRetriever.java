package com.viseo.talentmap.lite.search.retriever;

import android.util.Log;

import com.fasterxml.jackson.core.type.TypeReference;
import com.viseo.talentmap.common.exception.TalentMapException;
import com.viseo.talentmap.common.retriever.AbstractRetriever;
import com.viseo.talentmap.common.service.SkillsService;
import com.viseo.talentmap.common.service.UsersService;
import com.viseo.talentmap.lite.search.data.SearchUser;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by patiencebayogha on 24/02/15.
 * 11/03/2015
 */
public class SearchRetriever extends AbstractRetriever {


    protected UsersService usersService;
    protected SkillsService skillsService;

    public SearchRetriever() {
        usersService = new UsersService();
    }


    public ArrayList<SearchUser> list() throws TalentMapException {
        Log.d(getClass().getCanonicalName(), "Search list");

        InputStream inputStream = usersService.list();
        ArrayList<SearchUser> result = jsonStream.getObject(inputStream, new TypeReference<ArrayList<SearchUser>>() {
        });

        return result;
    }

    public SearchUser get(String email) throws TalentMapException {
        Log.d(getClass().getCanonicalName(), "search Users ");

        InputStream inputStream = usersService.get(email);
        SearchUser result = jsonStream.getObject(inputStream, new TypeReference<SearchUser>() {
        });
        return result;
    }


}
