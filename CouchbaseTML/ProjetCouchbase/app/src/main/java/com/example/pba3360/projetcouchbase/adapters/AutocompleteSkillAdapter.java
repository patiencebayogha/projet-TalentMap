package com.example.pba3360.projetcouchbase.adapters;

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
import com.couchbase.lite.android.AndroidContext;
import com.couchbase.lite.replicator.Replication;
import com.example.pba3360.projetcouchbase.R;
import com.example.pba3360.projetcouchbase.data.FilterListSkills;
import com.example.pba3360.projetcouchbase.singletons.AppController;
import com.example.pba3360.projetcouchbase.url.UrlTml;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
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

    public static final String DB_NAME = "default";
    private Database database = null;
    private Manager manager;


    public AutocompleteSkillAdapter(Activity context) {

        myContext = context;
        list = new ArrayList<>();

    }

    public void couchBase() {

        couchbaseDL();
        try {
            startReplications();
        } catch (CouchbaseLiteException e) {
            e.printStackTrace();
        }
    }


    @Override
    /*
    This class extends android.widget.Filter and have 2 methods performFiltering(CharSequence constraint)
    publishResults (CharSequence constraint, FilterResults results)and
     */
    public Filter getFilter() {
        if (filter == null)
            filter = new AutocompleteFilter();
        return filter;
    }

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

    public void setData() {
        /**
         * Method to make json object request where json response starts wtih [
         */
        JsonArrayRequest jsonObjReq = new JsonArrayRequest(
                UrlTml.SKILL_LIST, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG, response.toString());
                try {


                    for (int i = 0; i < response.length(); i++) {
                        JSONObject name = (JSONObject) response.get(i);
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

    //Class for filter
    private class AutocompleteFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();

            if (constraint == null || constraint.length() == 0) {
                // No filter implemented we return all the list
                filterResults.values = filterList;
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

    private class ViewHolder {

        public TextView autocompletete;


    }

    /*
    Add couchbase
     */
    private void couchbaseDL() {

        try {

            manager = new Manager(new AndroidContext(myContext), Manager.DEFAULT_OPTIONS);
            Log.d(TAG, "Manager created");

            if (!Manager.isValidDatabaseName(DB_NAME)) {
                Log.e(TAG, "Bad database name");
                return;
            }

            database = manager.getDatabase(DB_NAME);
            Log.d(TAG, "Database created");
        } catch (CouchbaseLiteException e) {
            Log.e(TAG, "Cannot get database");
            return;
        } catch (IOException e) {
            Log.e(TAG, "Cannot create manager object");
            return;
        }

        //creation of document
        String document = createDocument(database);
        //contents
        outputContents(database, document);
        updateDoc(database, document);
        addAttachment(database, document);
        outputContentsWithAttachment(database, document);
    }

    private void addAttachment(Database database, String document) {
    }

    private void outputContentsWithAttachment(Database database, String document) {
    }

    private void outputContents(Database database, String document) {
    }


    //update document created
    private void updateDoc(Database database, String doc) {
        Document document = database.getDocument(doc);
        try {
            // Update the document with more data
            Map<String, Object> updatedProperties = new HashMap<String, Object>();
            updatedProperties.putAll(document.getProperties());
            updatedProperties.put("name", "java");
            // Save to the Couchbase local Couchbase Lite DB
            document.putProperties(updatedProperties);
        } catch (CouchbaseLiteException e) {
            Log.e(TAG, "Error putting", e);
        }
    }


    //singleton DB and Manager
    public Database getDatabaseInstance() throws CouchbaseLiteException {
        if ((this.database == null) & (this.manager != null)) {
            this.database = manager.getDatabase(DB_NAME);
        }
        return database;
    }

    public Manager getManagerInstance() throws IOException {
        if (manager == null) {
            manager = new Manager(new AndroidContext(myContext), Manager.DEFAULT_OPTIONS);
        }
        return manager;
    }

    private String createDocument(Database database) {
        // Create a new document and add data
        Document document = database.createDocument();
        String documentId = document.getId();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", "java");
        // retrieve the document from the database
        Document retrievedDocument = database.getDocument(documentId);
// display the retrieved document
        Log.d(TAG, "retrievedDocument=" + String.valueOf(retrievedDocument.getProperties()));

        try {
            // Save the properties to the document
            document.putProperties(map);
        } catch (CouchbaseLiteException e) {
            Log.e(TAG, "Error putting", e);
        }
        return documentId;
    }


    private URL createSyncURL(boolean isEncrypted) {
        URL syncURL = null;
        String hostname = "https://127.0.0.1";
        String port = "8091";
        try {
            syncURL = new URL(hostname + ":" + port + "/" + DB_NAME);
        } catch (MalformedURLException me) {
            Log.e("error", "error", me);
        }
        return syncURL;
    }

    private void startReplications() throws CouchbaseLiteException {
        Replication pull = this.getDatabaseInstance().createPullReplication(this.createSyncURL(false));
        Replication push = this.getDatabaseInstance().createPushReplication(this.createSyncURL(false));
        pull.setContinuous(true);
        push.setContinuous(true);
        pull.start();
        push.start();
    }


}
