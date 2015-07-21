package com.viseo.talentmap.lite.search.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.viseo.talentmap.R;
import com.viseo.talentmap.lite.profil.manager.CategoryManager;
import com.viseo.talentmap.lite.search.data.SearchSkillsUsers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by patiencebayogha on 16/04/15.
 * last revision:23/04/2015
 * last revision:23/04/15
 * This activity will display the desired user profile, recuperate skill, category and level
 * Adapter for SearchGetProfilUsersActivity
 */
public class SearchAdapterGetProfilUsers extends BaseAdapter {


    protected Handler handler;
    protected ArrayList<SearchSkillsUsers> pSkills;
    String email;
    ViewHolder holder;
    private Activity myActivity = null;


    public SearchAdapterGetProfilUsers(Activity myContext, Handler myHandle, String email) {

        myActivity = myContext;
        handler = myHandle;
        pSkills = new ArrayList<>();
        this.email = email;


    }


    public void setData(ArrayList<SearchSkillsUsers> skills) {
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

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        holder = new ViewHolder();

        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) myActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.search_adapter_get_profil_users_list, parent, false);
            holder.category = (TextView) convertView.findViewById(R.id.category);
            holder.skill = (TextView) convertView.findViewById(R.id.skill);
            holder.level = (RatingBar) convertView.findViewById(R.id.skill_level);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        SearchSkillsUsers rowItem = (SearchSkillsUsers) getItem(position);
        holder.skill.setText(rowItem.getSkill());
        holder.level.setRating(rowItem.getLevel());

        holder.category.setText(CategoryManager.getInstance(myActivity).getCategory(rowItem.getCategory()));
        return convertView;
    }

    private void sortSkills() {
        /*  For ordonner by Categorie  */
        Collections.sort(pSkills, new Comparator<SearchSkillsUsers>() {
            // List of Category skill  trying selon compareTo Skill
            public int compare(SearchSkillsUsers p1, SearchSkillsUsers p2) {
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

            String currentCategory = pSkills.get(i).getCategory();  //on récupère le premier attribut
            if (currentCategory.equalsIgnoreCase(previousCategory)) {

                pSkills.get(i).setCategory(null);
            } else {
                previousCategory = pSkills.get(i).getCategory();   // on récupère le dernier attribut
            }


        }
    }

    private class ViewHolder {

        protected TextView category;
        protected RatingBar level;
        protected TextView skill;


    }
}



