package com.viseo.talentmap.lite.profil.adapter;

import android.app.Activity;
import android.app.LoaderManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Loader;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;

import com.viseo.talentmap.R;
import com.viseo.talentmap.common.utils.LoaderUtils;
import com.viseo.talentmap.lite.profil.data.SkillUserProfil;
import com.viseo.talentmap.lite.profil.loader.DeleteSkillLoader;
import com.viseo.talentmap.lite.profil.loader.UpdateSkillLoader;
import com.viseo.talentmap.lite.profil.manager.CategoryManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by patiencebayogha on 16/03/15.
 * 30-1/04/15
 * last revision:23/04/2015
 * Adapter for access to the data items and for delete skill.
 * For recuperate List sill users (categories,skills,level)
 */
public class ProfilAdapter extends BaseAdapter implements LoaderManager.LoaderCallbacks<Object> {
    public static final int LOADER_DELETE_SKILL = LoaderUtils.getInstance().getLoaderId();      //add delete skill loader Id
    private static final int LOADER_UPDATE_SKILL = LoaderUtils.getInstance().getLoaderId();     //add update skill loader Id
    private static final int TYPE_SEPARATOR = 1;                    //add separator for listView
    private static final int TYPE_MAX_COUNT = TYPE_SEPARATOR + 1;  //add separator for listView
    private static final int TYPE_ITEM = 0;         //add separator for listView item
    private final int INVALID = -1;                    //for invalid delete position
    protected Handler handler;                      //handle for activity
    protected ArrayList<SkillUserProfil> pSkills;  //arrayList for DATA SkillUserProfil (categoy; level; skill)
    protected int DELETE_POS = -1;                //for delete position skill
    boolean isModificationDone = true;           //Use if you modified Loader
    ViewHolder holder;                          //Viewholder for recycle view
    SkillUserProfil rowItem;                   // row Item SkillUserProfil
    private String email;                      //email pass to activity
    private Activity myActivity = null;
    private TreeSet mSeparatorsSet = new TreeSet(); //For order Ctaegories
    private Boolean isEditable = false;         //for appear image delete in view

    public ProfilAdapter(Activity myContext, Handler myHandle, String email) {

        myActivity = myContext;
        handler = myHandle;
        pSkills = new ArrayList<>();
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
        return pSkills.size();
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
    public Loader<Object> onCreateLoader(int id, Bundle args) {
        Loader result = null;
        if (id == LOADER_DELETE_SKILL) {
            result = new DeleteSkillLoader(myActivity, args);
        } else if (LOADER_UPDATE_SKILL == id) {
            result = new UpdateSkillLoader(myActivity, args);
        }
        return result;
    }


    @Override
    public void onLoadFinished(Loader<Object> loader, Object data) {
        if (LOADER_DELETE_SKILL == loader.getId()) {
            if (data != null && data instanceof SkillUserProfil) {
                SkillUserProfil users = (SkillUserProfil) data;
                holder.skill.setText(users.getSkill());
            }
        } else if (LOADER_UPDATE_SKILL == loader.getId()) {
            if (data != null && data instanceof Boolean) {
                Log.d(getClass().getCanonicalName(), "Update Profil Skill background task launch.");
            } else {
                Log.e(getClass().getCanonicalName(), "error during loading: MyProfilAdapter.");
            }
            isModificationDone = true;
        }
        notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<Object> loader) {

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
                deleteItem(position);
            }
        });

        if (isEditable) {
            holder.level.setEnabled(true);
            //for add delete ans rating bar clickable in edit profil mode
            holder.delete.setVisibility(View.VISIBLE);
            holder.delete.setFocusable(true);
        }

        rowItem = (SkillUserProfil) getItem(position);

        holder.skill.setText(rowItem.getSkill());
        holder.level.setRating(rowItem.getLevel());
        holder.level.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if (pSkills.get(position).getLevel() != rating) {
                    updateSkill(rating, position); //modif skills
                    pSkills.get(position).setLevel((int) rating);
                    launchBarDialog(holder.level, position);
                    notifyDataSetChanged();
                }
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

            Set<SkillUserProfil> skillUserProfils = new TreeSet<SkillUserProfil>();             // List of Category skill  trying selon compareTo Skill

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

    public void initDelete(int position) {
        /**
         *
         method for delete skill
         */
        Bundle bundle = new Bundle();

        bundle.putString(DeleteSkillLoader.EMAIL, email);
        bundle.putString(DeleteSkillLoader.SKILL, pSkills.get(position).getSkill());
        if (myActivity.getLoaderManager().getLoader(LOADER_DELETE_SKILL) == null) {
            myActivity.getLoaderManager().initLoader(LOADER_DELETE_SKILL, bundle, this);
        } else {
            myActivity.getLoaderManager().restartLoader(LOADER_DELETE_SKILL, bundle, this);
        }

    }

    public void updateSkill(float level, int position) {
        /**
         *
         method for update skill
         */
        if (isModificationDone) {
            Bundle bundle = new Bundle();
            bundle.putString(UpdateSkillLoader.EMAIL, email);
            bundle.putInt(UpdateSkillLoader.LEVEL, (int) level);
            bundle.putString(UpdateSkillLoader.SKILL, pSkills.get(position).getSkill());
            if (myActivity.getLoaderManager().getLoader(LOADER_UPDATE_SKILL) == null) {
                myActivity.getLoaderManager().initLoader(LOADER_UPDATE_SKILL, bundle, this);
            } else {
                myActivity.getLoaderManager().restartLoader(LOADER_UPDATE_SKILL, bundle, this);
            }
            isModificationDone = false;
        }

    }

    public void launchBarDialog(View view, int position) {
        /**
         * method for launch Bar Dialog and notified modification
         */
        final ProgressDialog ringProgressDialog = ProgressDialog.show(myActivity, "Please wait ...", "vous avez modifié un niveau de compétence" + ":" + pSkills.get(position).getSkill(), true);
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


}

