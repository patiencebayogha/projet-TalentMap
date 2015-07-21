package com.viseo.talentmap.lite.search.manager;

import android.util.Log;

import com.viseo.talentmap.common.exception.TalentMapException;
import com.viseo.talentmap.lite.search.data.SearchUser;
import com.viseo.talentmap.lite.search.retriever.SearchRetriever;

import java.util.ArrayList;

/**
 * Created by patiencebayogha on 24/02/15.
 * 05.03.2015
 * 23/04/15
 * retreiver to searchRetriever
 */
public class SearchManager {
    protected SearchRetriever searchRetriever;


    public SearchManager() {
        Log.d(getClass().getCanonicalName(), "create Manager");
        searchRetriever = new SearchRetriever();
    }


    public ArrayList<SearchUser> list() throws TalentMapException {
        Log.d(getClass().getCanonicalName(), "search Manager");
        return searchRetriever.list();

    }


    public SearchUser get(String email) throws TalentMapException {
        Log.d(getClass().getCanonicalName(), "Skill Manager ");
        return searchRetriever.get(email);

    }


}
