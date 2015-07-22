package com.example.patiencebayogha.talentmapwithCouchbase.adapters;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.Document;
import com.couchbase.lite.Manager;
import com.couchbase.lite.android.AndroidContext;
import com.example.patiencebayogha.talentmapwithCouchbase.R;
import com.example.patiencebayogha.talentmapwithCouchbase.data.FilterListSkills;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by patiencebayogha on 02/06/15.
 */
public class CategoriesSpinnerAdapter extends BaseAdapter {

    public static ViewHolder holder;
    protected Handler handler;
    Activity myContext;
    private Properties properties;
    private ArrayList<String> categoriesIds;
    private static final String TAG = CategoriesSpinnerAdapter.class.getSimpleName();

    private Database mDatabase;
    private Manager mManager;

    public static final String INFO_DOCUMENT_ID = "6ecbeaf0-fae5-11e4-b939-0800200c9a66";
    public static final String FIELD_CATEGORY = "category";

    public CategoriesSpinnerAdapter(Activity context, Handler mHandle) {

        myContext = context;
        handler = mHandle;
        properties = new Properties();

        try {
            properties.loadFromXML(context.getAssets().open("categories.xml"));

        } catch (IOException e) {
            e.printStackTrace();
        }
        categoriesIds = new ArrayList<>();
        for (String a : properties.stringPropertyNames()) {
            categoriesIds.add(a);

        }
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater mInflater = (LayoutInflater)
                myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.categories_spinner_adapter, null);
            holder = new ViewHolder();
            holder.categoryName = (TextView) convertView.findViewById(R.id.category);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        String rowItem = (String) properties.get(getItem(position));

        holder.categoryName.setText(rowItem);
//        saveCategory(holder.categoryName.getText().toString());

        return convertView;
    }

    @Override
    public int getCount() {
        return categoriesIds.size();
    }

    @Override
    public String getItem(int position) {
        return categoriesIds.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder {
        TextView categoryName;
    }


 /* *//*  public void couchbaseSpinner() {
        try {
            mManager = new Manager(new AndroidContext(myContext.getApplicationContext()), Manager.DEFAULT_OPTIONS);
            mDatabase = mManager.getDatabase("icetea09-database");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CouchbaseLiteException e) {
            e.printStackTrace();
        }

        mAddSkill = getContactInfo();
        if (mAddSkill != null) {
      holder.categoryName.setText(mAddSkill.getName());
          //  saveCategory(holder.categoryName.getText().toString());
        }
    }*//*

    private FilterListSkills getContactInfo() {
        try {
            Document doc = mDatabase.getDocument(INFO_DOCUMENT_ID);
            String category = doc.getProperty(FIELD_CATEGORY).toString();

       return new FilterListSkills("",category);
        } catch (Exception ex) {
            Log.e(TAG, "Cannot get category info", ex);
        }
        return null;
    }

    private boolean saveCategory(String category) {
        Document document = mDatabase.getDocument(INFO_DOCUMENT_ID);

        Map<String, Object> properties = new HashMap();
        properties.put(FIELD_CATEGORY, category);
        try {
            document.putProperties(properties);
            return true;
        } catch (CouchbaseLiteException e) {
            Log.e(TAG, "Cannot save document", e);
            return false;
        }

    }

    private boolean updateCategory(String firstName, String lastName, String phoneNumber) {
        Document document = mDatabase.getDocument(INFO_DOCUMENT_ID);

        Map<String, Object> properties = new HashMap();
        properties.putAll(document.getProperties());
        properties.put(FIELD_CATEGORY, firstName);
        try {
            document.putProperties(properties);
            return true;
        } catch (CouchbaseLiteException e) {
            Log.e(TAG, "Cannot save document", e);
            return false;
        }

    }

    private boolean deleteCategory() {
        Document document = mDatabase.getDocument(INFO_DOCUMENT_ID);
        try {
            document.delete();
            return true;
        } catch (CouchbaseLiteException e) {
            Log.e(TAG, "Cannot delete document", e);
            return false;
        }
    }*/


}
