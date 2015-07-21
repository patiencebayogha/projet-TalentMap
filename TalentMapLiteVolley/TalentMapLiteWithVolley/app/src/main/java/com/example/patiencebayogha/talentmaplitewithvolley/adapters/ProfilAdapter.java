package com.example.patiencebayogha.talentmaplitewithvolley.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.RatingBar;
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
import com.example.patiencebayogha.talentmaplitewithvolley.activities.LoginActivity;
import com.example.patiencebayogha.talentmaplitewithvolley.data.SkillUserProfil;
import com.example.patiencebayogha.talentmaplitewithvolley.gsonRequest.LoginGsonRequest;
import com.example.patiencebayogha.talentmaplitewithvolley.service.CategoryManager;
import com.example.patiencebayogha.talentmaplitewithvolley.singletons.AppController;
import com.example.patiencebayogha.talentmaplitewithvolley.url.UrlTml;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

/**
 * Created by patiencebayogha on 01/06/15.
 */
public class ProfilAdapter extends BaseAdapter {


    private final static String REQUEST_SET_COOKIE = "COOKIE"; //header request set cookies
    private static final String TAG = ProfilAdapter.class.getSimpleName();
    private static final int TYPE_SEPARATOR = 1;                                    //add separator for listView
    private static final int TYPE_MAX_COUNT = TYPE_SEPARATOR + 1;                   //add separator for listView
    private static final int TYPE_ITEM = 0;                                         //add separator for listView item
    private final int INVALID = -1;                                                 //for invalid delete position
    private ArrayList<SkillUserProfil> pSkills;  //arrayList for DATA SkillUserProfil (categoy; level; skill)
    protected int DELETE_POS = -1;                                                  //for delete position skill
    ViewHolder holder;                                                              //Viewholder for recycle view
    SkillUserProfil rowItem;                                                        // row Item SkillUserProfil
    private String email;                                                           //email pass to activity
    private Activity myActivity = null;
    boolean isModificationDone = true;           //Use if you modified Loader
    private TreeSet mSeparatorsSet = new TreeSet(); //For order Ctaegories


    public ProfilAdapter(Activity myContext, String email) {
        this.myActivity = myContext;
        this.pSkills = new ArrayList<>();
        this.email = email;

    }

    public int getItemViewType(int position) {
        /**
         * Get the type of View that will be created by getView(int, View, ViewGroup) for the specified item.
         */
        return mSeparatorsSet.contains(position) ? TYPE_SEPARATOR
                : TYPE_ITEM;
    }


    public void setData(ArrayList<SkillUserProfil> skills) {
        /**
         * method For recuperate Data and  to Profilfragment
         */

        pSkills = skills;
        sortSkills();
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {

        return (pSkills == null) ? 0 : pSkills.size();
    }

    @Override
    public Object getItem(int position) {
        return pSkills.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public void deleteItem(int pos) {
        /**
         *method  For delete skill
         */

        initDelete(pos);
        pSkills.remove(pSkills.get(pos));
        DELETE_POS = INVALID;
        notifyDataSetChanged();
    }


    /*
    *For delete Skill
     */
    private void initDelete(int position) {
        //  RequestQueue requestQueue = Volley.newRequestQueue(myActivity);

        HashMap<String, String> params = new HashMap<String, String>();
        params.put(LoginActivity.EMAIL, email);
        params.put("skill", pSkills.get(position).getSkill());

        Log.w("TAG", "email" + params);


        Map<String, String> headers = new HashMap<String, String>(3);
        headers.put("Accept", "application/json");
        headers.put("Content-Type", "application/json;charset=utf-8");
        headers.put("connect.sid", REQUEST_SET_COOKIE);


        LoginGsonRequest<SkillUserProfil> postReq = new LoginGsonRequest<SkillUserProfil>(Request.Method.POST, UrlTml.USERS_DELETE_SKILL,
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
            @SuppressLint("LongLogTag")
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


    @Override
    public boolean isEnabled(int position) {
        return mSeparatorsSet.contains(position) ? false : true;
    }

    public int getViewTypeCount() {
        /**
         * Returns the number of types of Views that will be created by getView
         */
        return TYPE_MAX_COUNT;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) myActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.profil_adapter_list_item_, parent, false);
            holder = new ViewHolder();
            holder.category = (TextView) convertView.findViewById(R.id.category);
            holder.skill = (TextView) convertView.findViewById(R.id.skill);
            holder.level = (RatingBar) convertView.findViewById(R.id.skill_level);
            holder.delete = (ImageButton) convertView.findViewById(R.id.delete);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder adb = new AlertDialog.Builder(
                        myActivity);
                adb.setTitle("Voulez-vous supprimer cette compétence?");
                adb.setPositiveButton("Oui",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {

                                deleteItem(position);
                            }

                        });
                adb.setNegativeButton("Non",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {

                                dialog.dismiss();

                            }
                        });
                adb.show();

            }

        });

        rowItem = (SkillUserProfil) getItem(position);


        holder.skill.setText(rowItem.getSkill());
        holder.level.setRating(rowItem.getLevel());


        holder.level.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    float touchPositionX = event.getX();
                    int width = holder.level.getWidth();
                    int starsf = (int) ((touchPositionX / width) * 5);
                    int stars = (int) starsf + 1;
                   // ratingBar.setRating(stars);
                    if (pSkills.get(position).getLevel() != stars) {
                        showDialog(stars, position);
                    }
                    v.setPressed(false);
                }
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    v.setPressed(true);
                }

                if (event.getAction() == MotionEvent.ACTION_CANCEL) {
                    v.setPressed(false);
                }


                return true;
            }
        });


        holder.category.setText(CategoryManager.getInstance(myActivity).getCategory(rowItem.getCategory()));

        return convertView;
    }

    private void sortSkills() {
        /**
         *  For ordonner by Categorie
         *  */
        Collections.sort(pSkills, new Comparator<SkillUserProfil>() {

            public int compare(SkillUserProfil p1, SkillUserProfil p2) {
                if (p1 == null) {
                    if (p2 != null)
                        return -1;
                }

                if (p2 == null) return 1;
                int result = p1.getCategory().compareTo(p2.getCategory());

                return result;
            }
        });

        String previousCategory = null;
        for (int i = 0; i < pSkills.size(); i++) {

            String currentCategory = pSkills.get(i).getCategory();  //the first attribute is recovered
            if (currentCategory.equalsIgnoreCase(previousCategory)) {

                pSkills.get(i).setCategory(null);
            } else {
                previousCategory = pSkills.get(i).getCategory();   //the last attribute is recovered
            }
        }
    }


    public void launchBarDialog(View view, int position) {
        /**
         * method for launch Bar Dialog and notified modification
         */
        final ProgressDialog ringProgressDialog = ProgressDialog.show(myActivity, "patientez ...", "vous avez modifié un niveau de compétence" + ":" + pSkills.get(position).getSkill(), true);
        ringProgressDialog.setCancelable(true);

        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    // Here you should write your time consuming task...
                    // Let the progress ring for 20 seconds...
                    Thread.sleep(2000);
                } catch (Exception e) {
                }
                ringProgressDialog.dismiss();
            }
        }).start();


    }

    private class ViewHolder {
        /**
         * Declarations of parameters
         */
        protected TextView category;
        protected RatingBar level;
        protected TextView skill;
        protected ImageButton delete;

    }

    public void updateSkill(float level, int position) {

        if (isModificationDone) {


            HashMap<String, String> params = new HashMap<String, String>();
            params.put(LoginActivity.EMAIL, email);
            params.put("level", String.valueOf(level));
            params.put("skill", pSkills.get(position).getSkill());
            Log.w("TAG", "email" + params);


            Map<String, String> headers = new HashMap<String, String>(3);
            headers.put("Accept", "application/json");
            headers.put("Content-Type", "application/json;charset=utf-8");
            headers.put("connect.sid", REQUEST_SET_COOKIE);


            LoginGsonRequest<SkillUserProfil> postReq = new LoginGsonRequest<SkillUserProfil>(Request.Method.POST, UrlTml.USERS_UPDATE_SKILL,
                    SkillUserProfil.class, headers, params,
                    createMyRequestSuccessListener(),
                    createMyRequestErrorListener());


            AppController.getInstance().addToRequestQueue(postReq);
            Log.w("DEBUG", "AppController getInstance request queue thing hit.");
        }
    }


    private Response.Listener<SkillUserProfil> createMyRequestSuccessListener() {
        return new Response.Listener<SkillUserProfil>() {
            @Override
            public void onResponse(SkillUserProfil response) {
                Log.w("DEBUG", "onResponse method hit within JsonObjectRequest for delete skill");
                VolleyLog.d(TAG, "Delete skill: " + "" + response.toString());

                if (response != null && response.length() > 0) {
                    Log.d(TAG, response.toString());
                    isModificationDone = true;
                }
            }

        };
    }


    private Response.ErrorListener createMyRequestErrorListener() {
        return new Response.ErrorListener() {
            @SuppressLint("LongLogTag")
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

    public void showDialog(final float rating, final int position) {
        final AlertDialog.Builder popDialog = new AlertDialog.Builder(myActivity);
        popDialog.setTitle("Modification du niveau de compétence");
        // Button OK
        popDialog.setPositiveButton(android.R.string.ok,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        updateSkill(rating, position); //modif skills
                        pSkills.get(position).setLevel((int) rating);
                        launchBarDialog(holder.level, position);
                        notifyDataSetChanged();
                        dialog.dismiss();
                    }
                })

                // Button Cancel
                .setNegativeButton("Annuler",
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        }
                );
        popDialog.create();
        popDialog.show();
    }

}
