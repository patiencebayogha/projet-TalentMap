package com.example.patiencebayogha.talentmaplitewithvolley.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TextInputLayout;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.example.patiencebayogha.talentmaplitewithvolley.R;
import com.example.patiencebayogha.talentmaplitewithvolley.adapters.AutocompleteSkillAdapter;
import com.example.patiencebayogha.talentmaplitewithvolley.adapters.CategoriesSpinnerAdapter;
import com.example.patiencebayogha.talentmaplitewithvolley.data.FilterListSkills;
import com.example.patiencebayogha.talentmaplitewithvolley.data.SkillUserProfil;
import com.example.patiencebayogha.talentmaplitewithvolley.gsonRequest.LoginGsonRequest;
import com.example.patiencebayogha.talentmaplitewithvolley.singletons.AppController;
import com.example.patiencebayogha.talentmaplitewithvolley.url.UrlTml;
import com.example.patiencebayogha.talentmaplitewithvolley.utils.LoaderUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by patiencebayogha on 01/06/15.
 */
public class SkillsActivity extends Activity implements AdapterView.OnItemSelectedListener {

    /**
     * add skill loader Id
     */
    private static final int LOADER_ADD_SKILL = LoaderUtils.getInstance().getLoaderId();            //add add skill loader Id
    public Spinner spinnerCategory;                     //spinner for category
    public AutoCompleteTextView skills;                    //autocomplete text   for  skills
    public int selectedLevel = 1;           //for select level (rating bar)
    public ArrayList<FilterListSkills> list;            //arraylist for list FilterListSkills
    AutocompleteSkillAdapter autocompleteAdapter;       //adapter for autocomplete text skills
    Handler handler;                                    //for activity
    private ImageView validate;                       //for validation choices and add to profil (new skill by level and category)
    private String email;
    private Toolbar mToolbar;//pass in activity
    private CategoriesSpinnerAdapter categoriesSpinnerAdapter;            //adapter for spinner categories
    private final static String REQUEST_SET_COOKIE = "connect.sid\ts%3AZh9jGtdn5Y22XqJqXOo95i9JNjnrKRQ8.JA8JsU1HSCrTJpgSM%2FCSKwp2Y2AyaPI%2BQn18IBozHOc\ttml.hubi.org\t/\t3 juin 2015 16:10:03 UTC+2\t95 o\t\t"; //header request set cookies


    private static final String TAG = SkillsActivity.class.getSimpleName();
    //Users json url
    private static final String urlAddSkill = "http://tml.hubi.org:80/api/v1/users/add-skill";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_CUSTOM_TITLE); //center Title

        setContentView(R.layout.activity_skills_activity);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);//hide the keyboard everytime the activty starts
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        email = getIntent().getExtras().getString(MainActivity.EMAIL);

        spinnerCategory = (Spinner) findViewById(R.id.spinner);

        validate = (ImageView) findViewById(R.id.activity_skills_image_button_add);

        autocompleteAdapter = new AutocompleteSkillAdapter(this);



        skills = (AutoCompleteTextView) findViewById(R.id.activity_skills_expertises);
        skills.setAdapter(autocompleteAdapter);

        validate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                /**
                 *Verification if you have enter skill and choice category
                 */
                final String expertise = skills.getText().toString();
                String st = spinnerCategory.getSelectedItem().toString();
                if (expertise.length() == 0 || st.length() == 0) {
                    skills.requestFocus();
                    skills.setBackgroundResource(R.drawable.edit_text_invalid);
                    skills.setError("Veuillez entrer une compétence");

                    spinnerCategory.setBackgroundResource(R.drawable.edit_text_invalid);
                    spinnerCategory.getSelectedItem().toString();
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

                    addSkill();
                    Intent u = new Intent(SkillsActivity.this, MainActivity.class);
                    u.putExtra(MainActivity.EMAIL, email);
                    startActivity(u);

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

        showRatinBar();


    }

    private void showRatinBar() {

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
        autocompleteAdapter.setData();

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

    public void addSkill() {

        HashMap<String, String> params = new HashMap<String, String>();
        params.put(LoginActivity.EMAIL, email);
        params.put("skill", skills.getText().toString());
        params.put("level", String.valueOf(selectedLevel));
        params.put("category", spinnerCategory.getSelectedItem().toString());
        Log.w("TAG", "email" + params);


        Map<String, String> headers = new HashMap<String, String>(3);
        headers.put("Accept", "application/json");
        headers.put("Content-Type", "application/json;charset=utf-8");
        headers.put("connect.sid", REQUEST_SET_COOKIE);


        LoginGsonRequest<SkillUserProfil> postReq = new LoginGsonRequest<SkillUserProfil>(Request.Method.POST, UrlTml.USERS_ADD_SKILL,
                SkillUserProfil.class, headers, params,
                createMyReqSuccessListener(),
                createMyReqErrorListener());


        postReq.setRetryPolicy(
                new DefaultRetryPolicy(
                        DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        AppController.getInstance().addToRequestQueue(postReq);
        Log.w("DEBUG", "AppController getInstance request queue thing hit.");
    }


    private Response.Listener<SkillUserProfil> createMyReqSuccessListener() {
        return new Response.Listener<SkillUserProfil>() {
            @Override
            public void onResponse(SkillUserProfil response) {
                Log.w("DEBUG", "onResponse method hit within JsonObjectRequest for delete skill");
                VolleyLog.d(TAG, "Delete skill: " + "" + response.toString());

                if (response != null && response.length() > 0) {
                    Log.d(TAG, response.toString());

                }
            }

        };
    }


    private Response.ErrorListener createMyReqErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Do whatever you want to do with error.getMessage();
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
        };
    }


}