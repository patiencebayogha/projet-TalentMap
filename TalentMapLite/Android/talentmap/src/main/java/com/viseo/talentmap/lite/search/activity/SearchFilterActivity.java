package com.viseo.talentmap.lite.search.activity;

/**
 * Created by patiencebayogha on 16/04/15.
 */
/**
 * Created by PBA3353 on 9/02/2015.
 * modif:16/02/15
 * modif:mars 2015
 * last revision:23/04/2015
 * This Activity Manages Adding  Expertises/Skills
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.viseo.talentmap.R;
import com.viseo.talentmap.lite.search.adapter.SpinnerSkillAdapter;
import com.viseo.talentmap.modifications_skills.adapter.CategoriesSpinnerAdapter;
import com.viseo.talentmap.modifications_skills.data.FilterlistSkills;

import java.util.ArrayList;


public class SearchFilterActivity extends Activity implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    public Spinner spinnerCategory;         //Spinner for Category
    public Spinner skillsSpinner;           //spinner for skills
    public int selectedLevel = 1;           //used for selected  level (rating bar)
    public ArrayList<FilterlistSkills> list;        //arraylist for FilterListSkills
    String skill;                           //pass to activity
    int level;                              //pass to activity
    TextView choiceSkill;                   //Textview for skill
    TextView choiceCategory;                    //textview for Category
    SpinnerSkillAdapter spinnerSkillAdapter;             //adapter for spinner skill
    Handler handler;                        //for activity
    private ImageButton validate;           //image button for tick and go in skillActivity
    private CategoriesSpinnerAdapter categoriesSpinnerAdapter;      //adapter for spinner categories

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
        validate = (ImageButton) findViewById(R.id.activity_filter_image_button_add);

        spinnerSkillAdapter = new SpinnerSkillAdapter(this, skill);
        skillsSpinner.setAdapter(spinnerSkillAdapter);
        skillsSpinner.setOnItemSelectedListener(this);

        //Spinner
        categoriesSpinnerAdapter = new CategoriesSpinnerAdapter(this, handler);
        spinnerCategory.setOnItemSelectedListener(this);
        spinnerCategory.setAdapter(categoriesSpinnerAdapter);

        validate.setOnClickListener(this);


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
        spinnerSkillAdapter.loadDataFilter();
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
        intent.putExtra("skill", ((FilterlistSkills) skillsSpinner.getSelectedItem()).getName());
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
