package com.example.patiencebayogha.talentmapwithCouchbase.fragments;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.Document;
import com.couchbase.lite.Emitter;
import com.couchbase.lite.Manager;
import com.couchbase.lite.Mapper;
import com.couchbase.lite.Query;
import com.couchbase.lite.QueryEnumerator;
import com.couchbase.lite.QueryRow;
import com.couchbase.lite.View;
import com.couchbase.lite.android.AndroidContext;
import com.example.patiencebayogha.talentmapwithCouchbase.R;
import com.example.patiencebayogha.talentmapwithCouchbase.activities.SkillsActivity;
import com.example.patiencebayogha.talentmapwithCouchbase.adapters.AutocompleteSkillAdapter;
import com.example.patiencebayogha.talentmapwithCouchbase.adapters.CategoriesSpinnerAdapter;
import com.example.patiencebayogha.talentmapwithCouchbase.data.FilterListSkills;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by PBA3360 on 17/07/2015.
 * For DB of CouchBase
 * ADD Skills
 */
public class ViewdataBase  extends ActionBarActivity{
    private static final String VIEW_SKILL = "VIEW_SKILL";
    private ListView mLvSkill;

    private Database mDatabase;
    private Manager mManager;
    private View mPhoneView;
    private Query mQuery;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_database);

        mLvSkill = (ListView)findViewById(R.id.lv_category);

        try {
            mManager = new Manager(new AndroidContext(getApplicationContext()), Manager.DEFAULT_OPTIONS);
            mDatabase = mManager.getDatabase("icetea09-database");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CouchbaseLiteException e) {
            e.printStackTrace();
        }

        //Create dummy data
        saveInfo("dev","java");

        mPhoneView = mDatabase.getView(VIEW_SKILL);
        mPhoneView.setMap(new Mapper() {
            @Override
            public void map(Map<String, Object> document, Emitter emitter) {
                String skill = (String)document.get(SkillsActivity.FIELD_CATEGORY);
                emitter.emit(skill, document.get(SkillsActivity.FIELD_NAME));
            }
        }, "1");


        mQuery = mDatabase.getView(VIEW_SKILL).createQuery();
        mQuery.setDescending(true);
        mQuery.setLimit(50);

        List<String> values = new ArrayList<>();

        try{
            QueryEnumerator result = mQuery.run();
            for (Iterator<QueryRow> it = result; it.hasNext(); ) {
                QueryRow row = it.next();
                values.add(row.getValue().toString() + " - " + row.getKey().toString());
            }
        }
        catch (CouchbaseLiteException e){
            e.printStackTrace();
        }

        // Define a new Adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);
        mLvSkill.setAdapter(adapter);

    }

    private boolean saveInfo(String name,String category) {
        Document document = mDatabase.createDocument();
        Map<String, Object> properties = new HashMap();
        properties.put(SkillsActivity.FIELD_NAME, name);
        properties.put(SkillsActivity.FIELD_CATEGORY, category);
        try {
            document.putProperties(properties);
            return true;
        } catch (CouchbaseLiteException e) {
            e.printStackTrace();
            return false;
        }

    }

}
