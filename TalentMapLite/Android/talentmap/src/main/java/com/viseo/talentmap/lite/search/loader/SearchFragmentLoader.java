package com.viseo.talentmap.lite.search.loader;

import android.app.Activity;
import android.util.Log;

import com.viseo.talentmap.common.exception.TalentMapException;
import com.viseo.talentmap.common.loader.AbstractLoader;
import com.viseo.talentmap.lite.search.data.SearchUser;
import com.viseo.talentmap.lite.search.manager.SearchManager;

import java.util.ArrayList;

/**
 * Created by patiencebayogha on 24/02/15.
 * 05.03.2015
 * 23/04/15
 * recuperate list users in search profil
 */
public class SearchFragmentLoader extends AbstractLoader<ArrayList<SearchUser>> {


    protected SearchManager searchManager;


    /**
     * Constructeur par défaut.
     *
     * @param activity Le contexte
     */
    public SearchFragmentLoader(Activity activity) {
        super(activity);
        this.searchManager = new SearchManager();

    }

    @Override
    public ArrayList<SearchUser> loadInBackground() {
        try {
            result = searchManager.list();
        } catch (TalentMapException e) {
            Log.e(getClass().getCanonicalName(), "SearchFragmentLoader:loadInBackground : Error loading users list", e);

        }
        return result;
    }


}
