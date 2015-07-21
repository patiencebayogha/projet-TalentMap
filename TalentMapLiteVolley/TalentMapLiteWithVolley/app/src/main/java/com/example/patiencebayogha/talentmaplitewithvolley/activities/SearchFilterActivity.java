package com.example.patiencebayogha.talentmaplitewithvolley.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.patiencebayogha.talentmaplitewithvolley.R;
import com.example.patiencebayogha.talentmaplitewithvolley.adapters.CategoriesSpinnerAdapter;
import com.example.patiencebayogha.talentmaplitewithvolley.adapters.SpinnerSkillAdapter;
import com.example.patiencebayogha.talentmaplitewithvolley.data.FilterListSkills;
import com.example.patiencebayogha.talentmaplitewithvolley.singletons.AppController;
import com.example.patiencebayogha.talentmaplitewithvolley.url.UrlTml;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by patiencebayogha on 01/06/15.
 */
public class SearchFilterActivity extends Activity implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    public Spinner spinnerCategory;         //Spinner for Category
    public Spinner skillsSpinner;           //spinner for skills
    public int selectedLevel = 1;           //used for selected  level (rating bar)
    public ArrayList<FilterListSkills> list;        //arraylist for FilterListSkills
    String skill;                           //pass to activity
    int level;                              //pass to activity
    TextView choiceSkill;                   //Textview for skill
    TextView choiceCategory;                    //textview for Category
    SpinnerSkillAdapter spinnerSkillAdapter;             //adapter for spinner skill
    Handler handler;                        //for activity
    private ImageView validate;           //image button for tick and go in skillActivity
    private CategoriesSpinnerAdapter categoriesSpinnerAdapter;      //adapter for spinner categories
    List<FilterListSkills> filter;
    private static String TAG = SearchFilterActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();
        if (getIntent().getExtras() != null) {
            skill = extras.getString("skill");
            level = extras.getInt("level");
        }

        setContentView(R.layout.activity_search_filter);



        choiceSkill = (TextView) findViewById(R.id.choix_competence);
        choiceCategory = (TextView) findViewById(R.id.choix_categorie);

        spinnerCategory = (Spinner) findViewById(R.id.spinner_filter);
        skillsSpinner = (Spinner) findViewById(R.id.activity_filter_expertises);

        validate = (ImageView) findViewById(R.id.activity_filter_image_button_add);

        list = new ArrayList<>();

        spinnerSkillAdapter = new SpinnerSkillAdapter(this, skill);
        skillsSpinner.setAdapter(spinnerSkillAdapter);
        skillsSpinner.setOnItemSelectedListener(this);

        //Spinner
        categoriesSpinnerAdapter = new CategoriesSpinnerAdapter(this, handler);
        spinnerCategory.setOnItemSelectedListener(this);
        spinnerCategory.setAdapter(categoriesSpinnerAdapter);

        validate.setOnClickListener(this);

        ratingBar();
        loadDataFilter();

    }

    private void ratingBar() {
        //For Rating bar choice
        final ViewGroup blockLvl0 = (LinearLayout) findViewById(R.id.activity_filter_ratingBar_block);
        final ViewGroup blockLvl1 = (LinearLayout) findViewById(R.id.activity_filter_rating_bar_lvl_1_block);
        final ViewGroup blockLvl2 = (LinearLayout) findViewById(R.id.activity_filter_rating_bar_lvl_2_block);
        final ViewGroup blockLvl3 = (LinearLayout) findViewById(R.id.activity_filter_rating_bar_lvl_3_block);
        final ViewGroup blockLvl4 = (LinearLayout) findViewById(R.id.activity_filter_rating_bar_lvl_4_block);
        blockLvl0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedLevel != 1) {
                    switchChildState(blockLvl1, false);
                    switchChildState(blockLvl2, false);
                    switchChildState(blockLvl3, false);
                    switchChildState(blockLvl4, false);
                    selectedLevel = 1;
                }
            }
        });
        blockLvl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedLevel != 2) {
                    switchChildState(blockLvl1, true);
                    switchChildState(blockLvl2, false);
                    switchChildState(blockLvl3, false);
                    switchChildState(blockLvl4, false);
                    selectedLevel = 2;
                }
            }
        });

        blockLvl2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedLevel != 3) {
                    switchChildState(blockLvl1, true);
                    switchChildState(blockLvl2, true);
                    switchChildState(blockLvl3, false);
                    switchChildState(blockLvl4, false);
                    selectedLevel = 3;
                }
            }
        });

        blockLvl3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedLevel != 4) {
                    switchChildState(blockLvl1, true);
                    switchChildState(blockLvl2, true);
                    switchChildState(blockLvl3, true);
                    switchChildState(blockLvl4, false);
                    selectedLevel = 4;
                }
            }
        });

        blockLvl4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedLevel != 5) {
                    switchChildState(blockLvl1, true);
                    switchChildState(blockLvl2, true);
                    switchChildState(blockLvl3, true);
                    switchChildState(blockLvl4, true);
                    selectedLevel = 5;
                }
            }
        });
    }


    /**
     * Method to make json array request where response starts with [
     * for show all users in listview
     */
    public void loadDataFilter() {
        JsonArrayRequest req = new JsonArrayRequest(UrlTml.SKILL_LIST,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());

                        try {
                            // Parsing json array response
                            // loop through each json object

                            for (int i = 0; i < response.length(); i++) {

                                JSONObject skillName = (JSONObject) response.get(i);
                                FilterListSkills m_FilterListSkill = new FilterListSkills();

                                m_FilterListSkill.setName(skillName.getString("name"));
                                m_FilterListSkill.setIsVolatile(skillName.getString("volatile"));
                                m_FilterListSkill.setCategory(skillName.getString("category"));
                                list.add(m_FilterListSkill);
                            }

                            spinnerSkillAdapter.updateData(list);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),
                                    "Error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req);
    }

    public void switchChildState(ViewGroup vg, boolean isSelected) {
        //Rating bar choice isSelected
        int nbChild = vg.getChildCount();

        for (int i = 0; i < nbChild; i++) {
            View view = vg.getChildAt(i);
            view.setSelected(isSelected);
        }
    }


    @Override
    public void onStart() {
        super.onStart();

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.spinner_filter) {
            spinnerSkillAdapter.filterSkill(categoriesSpinnerAdapter.getItem(position));
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        intent.putExtra("skill", ((FilterListSkills) skillsSpinner.getSelectedItem()).getName());
        intent.putExtra("level", selectedLevel);
        setResult(1, intent);
        finish();


    }


    public void modifFilterResultSearch(int position) {
        //for modified resultat if the user does a search
        if (position == -1) {
            Log.e(getClass().getCanonicalName(), "error during loading: SearchFilterActivity. Data null");
        } else {
            skillsSpinner.setSelection(position);
        }
    }


}
