/**
 * Created by PBA3353 on 9/02/2015.
 * modif:16/02/15
 * 23/04/15
 * This Activity Manages Adding  Expertises/Skills
 */

package com.viseo.talentmap.modifications_skills.activity;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.viseo.talentmap.R;
import com.viseo.talentmap.common.utils.LoaderUtils;
import com.viseo.talentmap.lite.MainActivity;
import com.viseo.talentmap.modifications_skills.adapter.AutocompleteSkillAdapter;
import com.viseo.talentmap.modifications_skills.adapter.CategoriesSpinnerAdapter;
import com.viseo.talentmap.modifications_skills.data.FilterlistSkills;
import com.viseo.talentmap.modifications_skills.loader.AddSkillLoader;

import java.util.ArrayList;

/**
 * Activity handling user skills
 */
public class SkillsActivity extends Activity implements AdapterView.OnItemSelectedListener, LoaderManager.LoaderCallbacks<Boolean> {
    /**
     * add skill loader Id
     */
    private static final int LOADER_ADD_SKILL = LoaderUtils.getInstance().getLoaderId();            //add add skill loader Id
    public Spinner spinnerCategory;                     //spinner for category
    public AutoCompleteTextView skills;                    //autocomplete text   for  skills
    public int selectedLevel = 1;           //for select level (rating bar)
    public ArrayList<FilterlistSkills> list;            //arraylist for list FilterListSkills
    AutocompleteSkillAdapter autocompleteAdapter;       //adapter for autocomplete text skills
    Handler handler;                                    //for activity
    private ImageButton validate;                       //for validation choices and add to profil (new skill by level and category)
    private String email;
    private Toolbar mToolbar;//pass in activity
    private CategoriesSpinnerAdapter categoriesSpinnerAdapter;            //adapter for spinner categories

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_CUSTOM_TITLE); //center Title

        setContentView(R.layout.activity_skills_activity);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);//hide the keyboard everytime the activty starts
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        email = getIntent().getExtras().getString(MainActivity.EMAIL);

        spinnerCategory = (Spinner) findViewById(R.id.spinner);
        validate = (ImageButton) findViewById(R.id.activity_skills_image_button_add);
        autocompleteAdapter = new AutocompleteSkillAdapter(this, handler);
        skills = (AutoCompleteTextView) findViewById(R.id.activity_skills_expertises);
        skills.setAdapter(autocompleteAdapter);

        validate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                /**
                 *Verification if you have enter skill and choice category
                 */
                final String expertise = skills.getText().toString();
                if (expertise.length() == 0) {
                    skills.requestFocus();
                    skills.setBackgroundResource(R.drawable.edit_text_invalid);
                    skills.setError("");
                } else {
                    skills.setBackgroundResource(R.drawable.edit_text_valid);
                    skills.setOnEditorActionListener(new EditText.OnEditorActionListener() {
                        @Override
                        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                            if (actionId == EditorInfo.IME_ACTION_DONE || event.getAction() == KeyEvent.ACTION_DOWN &&
                                    event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {

                                InputMethodManager mr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                mr.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

                                return true;
                            }
                            return false;
                        }
                    });

                }
                String st = spinnerCategory.getSelectedItem().toString();

                if (st.length() == 0) {
                    spinnerCategory.setBackgroundResource(R.drawable.edit_text_invalid);
                    spinnerCategory.getSelectedItem().toString();
                } else {
                    skills.setBackgroundResource(R.drawable.edit_text_valid);
                }


                //For load data
                Bundle bundle = new Bundle();
                bundle.putString(AddSkillLoader.EMAIL, email);
                bundle.putString(AddSkillLoader.SKILL, skills.getText().toString());
                bundle.putInt(String.valueOf(AddSkillLoader.LEVEL), selectedLevel);
                bundle.putString(AddSkillLoader.CATEGORY, spinnerCategory.getSelectedItem().toString());
                if (getLoaderManager().getLoader(LOADER_ADD_SKILL) == null) {
                    getLoaderManager().initLoader(LOADER_ADD_SKILL, bundle, SkillsActivity.this);
                } else {
                    getLoaderManager().restartLoader(LOADER_ADD_SKILL, bundle, SkillsActivity.this);
                }


            }

        });


        //Spinner
        categoriesSpinnerAdapter = new CategoriesSpinnerAdapter(this, handler);

        spinnerCategory.setOnItemSelectedListener(this);
        spinnerCategory.setAdapter(categoriesSpinnerAdapter);
        skills.setAdapter(autocompleteAdapter);
        skills.addTextChangedListener(new TextWatcher() {
            /**
             *
             listener attached to AutocompleteText
             */
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                autocompleteAdapter.getFilter().filter(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        //For Rating bar choice
        final ViewGroup blockLvl0 = (LinearLayout) findViewById(R.id.activity_sign_in_ratingBar_block);
        final ViewGroup blockLvl1 = (LinearLayout) findViewById(R.id.activity_sign_in_rating_bar_lvl_1_block);
        final ViewGroup blockLvl2 = (LinearLayout) findViewById(R.id.activity_sign_in_rating_bar_lvl_2_block);
        final ViewGroup blockLvl3 = (LinearLayout) findViewById(R.id.activity_sign_in_rating_bar_lvl_3_block);
        final ViewGroup blockLvl4 = (LinearLayout) findViewById(R.id.activity_sign_in_rating_bar_lvl_4_block);
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
        autocompleteAdapter.loadDataFilter();

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.spinner) {
            autocompleteAdapter.filterSkill(categoriesSpinnerAdapter.getItem(position));
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public Loader<Boolean> onCreateLoader(int id, Bundle args) {
        return new AddSkillLoader(this, args);
    }

    @Override
    public void onLoadFinished(Loader<Boolean> loader, Boolean data) {

        if (data == null) {
            Log.e(getClass().getCanonicalName(), "error during loading: SkillActivity. Data null");

        } else {
            Intent u = new Intent(SkillsActivity.this, MainActivity.class);
            u.putExtra(MainActivity.EMAIL, email);
            startActivity(u);
        }
    }

    @Override
    public void onLoaderReset(Loader<Boolean> loader) {

    }
}
