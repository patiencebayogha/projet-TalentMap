package com.example.patiencebayogha.talentmapwithCouchbase.preferences;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by patiencebayogha on 08/06/15.
 * Preferences class
 */
public class TalentPreferences {

    private static final String PREFERENCES_NAME = "talent_map_prefs";
    private static final String PREF_EMAIL = "prefs_email";

    public static void savePrefsEmail(Context context, String email) {
        //using for save SharePreference when you click in checkbox
        SharedPreferences prefs = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = prefs.edit();
        edit.putString(PREF_EMAIL, email);
        edit.apply();
    }

    public static String getPrefsEmail(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        return prefs.getString(PREF_EMAIL, null);
    }
}
