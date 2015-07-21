package com.viseo.talentmap.lite.profil.manager;

import android.content.Context;

import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

/**
 * Created by patiencebayogha on 19/03/15.
 * Permit to convert reference to String name
 * and recuperate xml categories
 */
public class CategoryManager {

    private static CategoryManager mInstance = null;
    Context myContext;
    private HashMap<String, String> categoriesMap;
    private Properties properties;

    /* Private Constructor prevents any other class from instantiating */
    CategoryManager(Context context) {
        myContext = context;
        categoriesMap = new HashMap<>();
        properties = new Properties();

        try {
            properties.loadFromXML(myContext.getAssets().open("categories.xml"));

        } catch (IOException e) {
            e.printStackTrace();
        }

        for (String a : properties.stringPropertyNames()) {
            categoriesMap.put(a, properties.getProperty(a));

        }


    }

    /* Lazy initialization, creating object on first use */
    public static CategoryManager getInstance(Context context) {
        if (mInstance == null)  //first call
        {
            mInstance = new CategoryManager(context);
        }
        return mInstance;
    }

    public String getCategory(String key) {
        return categoriesMap.get(key);
    }

}
