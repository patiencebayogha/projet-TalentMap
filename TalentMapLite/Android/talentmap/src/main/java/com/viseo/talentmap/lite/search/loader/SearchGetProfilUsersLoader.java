package com.viseo.talentmap.lite.search.loader;

import android.app.Activity;
import android.util.Log;

import com.viseo.talentmap.common.exception.TalentMapException;
import com.viseo.talentmap.common.loader.AbstractLoader;
import com.viseo.talentmap.lite.search.data.SearchUser;
import com.viseo.talentmap.lite.search.manager.SearchManager;

/**
 * Created by patiencebayogha on 17/04/15.
 * show profil user when you go to search profil
 * Email of the user in research
 * 23/04/15
 */
public class SearchGetProfilUsersLoader extends AbstractLoader<SearchUser> {

    public static final String EMAIL = "email";
    protected SearchManager searchManager;

    private String email;

    /**
     * Constructeur par défaut.
     *
     * @param activity Le contexte
     */
    public SearchGetProfilUsersLoader(Activity activity, String email) {
        super(activity);
        this.searchManager = new SearchManager();
        this.email = email;

    }

    @Override
    public SearchUser loadInBackground() {
        try {
            result = searchManager.get(email);
        } catch (TalentMapException e) {
            Log.e(getClass().getCanonicalName(), "SearchProfilGetUsersLoader:loadInBackground : Error updating get/{email}", e);
        }
        return result;
    }
}
