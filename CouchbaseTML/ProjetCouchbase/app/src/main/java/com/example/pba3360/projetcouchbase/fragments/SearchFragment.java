package com.example.pba3360.projetcouchbase.fragments;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
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
import com.example.pba3360.projetcouchbase.R;
import com.example.pba3360.projetcouchbase.activities.LoginActivity;
import com.example.pba3360.projetcouchbase.activities.SearchFilterActivity;
import com.example.pba3360.projetcouchbase.activities.SearchGetProfilUsersActivity;
import com.example.pba3360.projetcouchbase.adapters.SearchAdapterFragment;
import com.example.pba3360.projetcouchbase.data.SearchSkillsUsers;
import com.example.pba3360.projetcouchbase.data.SearchUser;
import com.example.pba3360.projetcouchbase.singletons.AppController;
import com.example.pba3360.projetcouchbase.singletons.AppController;
import com.example.pba3360.projetcouchbase.url.UrlTml;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by patiencebayogha on 01/06/15.
 */
public class SearchFragment extends Fragment implements AdapterView.OnItemClickListener {

    //listview
    protected ListView listView;

    //button for go o fitlers activity
    private ImageView filter;

    //adapter for show all elements of listView
    private SearchAdapterFragment searchAdapter;

    //show result in listview
    private TextView result;

    //show result after filters
    private TextView resultfilter;
    //Button for delete filters
    private ImageButton delete_filter;

    //list users
    private ArrayList<SearchUser> allPeople;

    //Log or request TAG
    private static final String TAG = SearchFragment.class.getSimpleName();


    public SearchFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.i(getClass().getCanonicalName(), "Activity." + "SearchFragment");

        // Inflate the layout for this fragment
        View resultView = inflater.inflate(R.layout.fragment_search, container, false);


        searchAdapter = new SearchAdapterFragment(getActivity());
        allPeople = new ArrayList<>();

        resultfilter = (TextView) resultView.findViewById(R.id.resultfilter);
        result = (TextView) resultView.findViewById(R.id.result);

        listView = (ListView) resultView.findViewById(R.id.listUser);
        listView.setAdapter(searchAdapter);
        listView.setOnItemClickListener(this);

        delete_filter = (ImageButton) resultView.findViewById(R.id.delete_filter);


        filter = (ImageView) resultView.findViewById(R.id.add_filter);
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchFilterActivity.class);
                startActivityForResult(intent, 1);

            }
        });

        makeJsonArrayRequest();  // making json array request

        delete_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultfilter.setVisibility(View.GONE);
                delete_filter.setVisibility(View.GONE);
                allPeople.clear();
                makeJsonArrayRequest();
            }
        });
        return resultView;
    }


    /**
     * Method to make json array request where response starts with [
     * for show all users in listview
     */
    private void makeJsonArrayRequest() {

        JsonArrayRequest req = new JsonArrayRequest(UrlTml.USERS_LIST,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, "response user list" + response.toString());

                        try {
                            // Parsing json array response
                            // loop through each json object

                            for (int i = 0; i < response.length(); i++) {

                                JSONObject person = (JSONObject) response.get(i);
                                SearchUser searchUser = new SearchUser();

                                searchUser.setName(person.getString("name"));
                                searchUser.setEmail(person.getString("email"));
                                searchUser.setPhoto(person.getString("photo"));
                                searchUser.setSurname(person.getString("surname"));
                                searchUser.setActive(Boolean.valueOf(person.getString("active")));

                                ArrayList<SearchSkillsUsers> searchSkillsUsers = new ArrayList<>();

                                JSONArray skillsArray = person.getJSONArray("skills");

                                for (int j = 0; j < skillsArray.length(); j++) {

                                    if(searchUser.setEmail(person.getString(LoginActivity.EMAIL))){

                                    }

                                    SearchSkillsUsers skillResponse = new SearchSkillsUsers();
                                    JSONObject skills = skillsArray.getJSONObject(j);


                                    skillResponse.setSkill(skills.getString("skill"));
                                    skillResponse.setCategory(skills.getString("category"));
                                    skillResponse.setLevel((int) skills.get("level"));
                                    searchSkillsUsers.add(skillResponse);

                                }
                                searchUser.setSkills(searchSkillsUsers);
                                allPeople.add(searchUser);


                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getActivity().getApplicationContext(), e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                        searchAdapter.updateData(allPeople);
                        searchAdapter.notifyDataSetChanged();


                    }

                }, new Response.ErrorListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "ErrorListener method hit within JsonObjectRequest" + "Error" + error.getMessage());
                VolleyLog.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(getActivity().getApplicationContext(),
                        error.getMessage() + "==>" + "Reponse.ErrorListener" + "===>" + "erreur durant le chargement", Toast.LENGTH_LONG).show();

                if (error instanceof NoConnectionError) {
                    Log.e("NoConnectionError>>>>>>>>>", "NoConnectionError.......");
                    Toast.makeText(getActivity().getApplicationContext(),
                            error.getMessage() + "==>" + "Erreur de connexion >>>>>>>>>" + "Erreur de connexion.......", Toast.LENGTH_LONG).show();

                } else if (error instanceof AuthFailureError) {
                    Log.e("AuthFailureError>>>>>>>>>", "AuthFailureError.......");
                    Toast.makeText(getActivity().getApplicationContext(),
                            error.getMessage() + "==>" + "Erreur d'authentification >>>>>>>>>" + "Erreur d'authentification.......", Toast.LENGTH_LONG).show();

                } else if (error instanceof ServerError) {
                    Log.e("ServerError>>>>>>>>>", "ServerError.......");
                    Toast.makeText(getActivity().getApplicationContext(),
                            error.getMessage() + "==>" + "Erreur serveur >>>>>>>>>" + "Erreur serveur.......", Toast.LENGTH_LONG).show();

                } else if (error instanceof NetworkError) {
                    Log.e("NetworkError>>>>>>>>>", "NetworkError.......");
                    Toast.makeText(getActivity().getApplicationContext(),
                            error.getMessage() + "==>" + "Erreur réseau >>>>>>>>>" + "Erreur réseau.......", Toast.LENGTH_LONG).show();

                } else if (error instanceof ParseError) {
                    Log.e("ParseError>>>>>>>>>", "ParseError.......");
                    Toast.makeText(getActivity().getApplicationContext(),
                            error.getMessage() + "==>" + "Erreur d'analyse >>>>>>>>>" + "Erreur d'analyse.......", Toast.LENGTH_LONG).show();

                } else if (error instanceof TimeoutError) {
                    Log.e("TimeoutError>>>>>>>>>", "TimeoutError.......");
                    Toast.makeText(getActivity().getApplicationContext(),
                            error.getMessage() + "==>" + "Erreur temps >>>>>>>>>" + "Erreur temps.......", Toast.LENGTH_LONG).show();

                }
            }
        });


        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req);
    }


    //show number of persons in listview
    public void afficheResult() {
        result.setText((searchAdapter.getCount()) + " " + " " + "résultat(s) trouvé(s). ");
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        Log.d(getClass().getCanonicalName(), "Select item." + position);

        Intent getMe = new Intent(getActivity(), SearchGetProfilUsersActivity.class);
        getMe.putExtra(SearchGetProfilUsersActivity.EMAIL_TO_SEARCH, searchAdapter.getItem(position).getEmail());

        startActivity(getMe);

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }


        afficheResult();

        final String skill = data.getStringExtra("skill");
        final int level = data.getIntExtra("level", 0);
        searchAdapter.filterListUsers(skill, level);

        resultfilter.setVisibility(View.VISIBLE);
        resultfilter.setText(skill + " " + "-" + level);
        resultfilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), SearchFilterActivity.class);
                intent.putExtra("skill", skill);
                intent.putExtra("level", level);
                startActivityForResult(intent, 1);
            }
        });

        delete_filter.setVisibility(View.VISIBLE);

    }


}
