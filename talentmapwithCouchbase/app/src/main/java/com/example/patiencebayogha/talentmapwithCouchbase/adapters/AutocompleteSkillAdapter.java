package com.example.patiencebayogha.talentmapwithCouchbase.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.Document;
import com.couchbase.lite.Manager;
import com.couchbase.lite.QueryRow;
import com.couchbase.lite.UnsavedRevision;
import com.couchbase.lite.android.AndroidContext;
import com.example.patiencebayogha.talentmapwithCouchbase.R;
import com.example.patiencebayogha.talentmapwithCouchbase.activities.SkillsActivity;
import com.example.patiencebayogha.talentmapwithCouchbase.data.FilterListSkills;
import com.example.patiencebayogha.talentmapwithCouchbase.singletons.AppController;
import com.example.patiencebayogha.talentmapwithCouchbase.url.UrlTml;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by patiencebayogha on 02/04/15.
 * 23/04/15
 * this class implements the Filterable interface
 */
public class AutocompleteSkillAdapter extends BaseAdapter implements Filterable {
    public static ViewHolder holder;
    protected List<FilterListSkills> filterList;
    protected List<FilterListSkills> nameList;
    Activity myContext;
    List<FilterListSkills> list;
    private Filter filter;

    private static final String TAG = AutocompleteSkillAdapter.class.getSimpleName();
    public static final String FIELD_NAME = "name";
    public static final String INFO_DOCUMENT_ID = "6ecbeaf0-fae5-11e4-b939-0800200c9a66";
    public static final String FIELD_CATEGORY = "category";
    private Database mDatabase;
    private Manager mManager;
    private FilterListSkills mAddSkill;

    public AutocompleteSkillAdapter(Activity context) {
        myContext = context;
        list = new ArrayList<>();
    }

    private class ViewHolder {
        public TextView autocompletete;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater mInflater = (LayoutInflater)
                myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.spinner_skill_adapter_get_skill, null);
            holder = new ViewHolder();
            holder.autocompletete = (TextView) convertView.findViewById(R.id.autocompletion);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        String rowItem = getItem(position);
        holder.autocompletete.setText(rowItem);
        return convertView;
    }

    @Override
    public int getCount() {
        return filterList.size();
    }

    @Override
    public String getItem(int position) {
        return filterList.get(position).getName();
    }

    @Override
    public long getItemId(int position) {
        return filterList.indexOf(getItem(position));
    }


    public void filterSkill(String categoryId) {
        filterList = new ArrayList<>();
        for (FilterListSkills filterListSkills : list) {
            if (filterListSkills.getCategory().equalsIgnoreCase(categoryId)) {
                filterList.add(filterListSkills);
            }

        }
    }

    /**
     * ************************
     * volley Request
     * *************************
     */
    public void setData() {
        /**
         * Method to make json object request where json response starts wtih [
         */
        JsonArrayRequest jsonObjReq = new JsonArrayRequest(
                UrlTml.SKILL_LIST, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG, response.toString());
                JSONObject name = null;
                try {


                    for (int i = 0; i < response.length(); i++) {
                        name = (JSONObject) response.get(i);
                        FilterListSkills jsonResponse = new FilterListSkills();
                        jsonResponse.setName(name.getString("name"));

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(myContext.getApplicationContext(),
                            e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {

            @SuppressLint("LongLogTag")
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Log.w("DEBUG", "onErrorResponse method hit within JsonObjectRequest" + error.getMessage());
                if (error instanceof NoConnectionError) {
                    Log.d("NoConnectionError>>>>>>>>>", "NoConnectionError......." + error.getMessage());

                } else if (error instanceof AuthFailureError) {
                    Log.d("AuthFailureError>>>>>>>>>", "AuthFailureError......." + error.getMessage());

                } else if (error instanceof ServerError) {
                    Log.d("ServerError>>>>>>>>>", "ServerError message......." + error.getMessage() + error.networkResponse.statusCode
                            + ">>" + error.networkResponse.data
                            + ">>" + error.getCause());

                } else if (error instanceof NetworkError) {
                    Log.d("NetworkError>>>>>>>>>", "NetworkError......." + error.getMessage()
                            + ">>" + error.networkResponse.statusCode
                            + ">>" + error.networkResponse.data
                            + ">>" + error.getCause());

                } else if (error instanceof ParseError) {
                    Log.d("ParseError>>>>>>>>>", "ParseError......." + error.getMessage());

                } else if (error instanceof TimeoutError) {
                    Log.d("TimeoutError>>>>>>>>>", "TimeoutError......." + error.getMessage());

                }
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq);


    }


    /**
     * ************************
     * filter
     * *************************
     */
    @Override
    /* This class extends android.widget.Filter and have 2 methods performFiltering(CharSequence constraint)
    publishResults (CharSequence constraint, FilterResults results)and
     */
    public Filter getFilter() {
        if (filter == null)
            filter = new AutocompleteFilter();
        return filter;
    }

    //Class for filter
    private class AutocompleteFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            List<FilterListSkills> myFilteredAppList = new ArrayList<FilterListSkills>();

            if (constraint == null || constraint.length() == 0) {
                // No filter implemented we return all the list
                filterResults.values = filterList;
                myFilteredAppList.addAll(filterList);
                filterResults.count = getCount();

            } else {
                nameList = new ArrayList<>();
                //we perform filtering operation
                for (FilterListSkills namelistSkills : filterList) {
                    // Determine if string starts with letters
                    if (namelistSkills.getName().toUpperCase().startsWith(constraint.toString().toUpperCase())) {
                        nameList.add(namelistSkills);
                    }
                    filterResults.values = nameList;
                    // filterResults.count = getCount();
                    filterResults.count = nameList.size();
                }
                filterList = nameList;
            }
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (results != null && results.count > 0) {

                filterList = (List<FilterListSkills>) results.values;
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }

        }
    }



}
