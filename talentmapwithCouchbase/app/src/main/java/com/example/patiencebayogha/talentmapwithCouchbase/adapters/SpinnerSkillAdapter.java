package com.example.patiencebayogha.talentmapwithCouchbase.adapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.Document;
import com.couchbase.lite.Manager;
import com.couchbase.lite.android.AndroidContext;
import com.example.patiencebayogha.talentmapwithCouchbase.R;
import com.example.patiencebayogha.talentmapwithCouchbase.activities.SearchFilterActivity;
import com.example.patiencebayogha.talentmapwithCouchbase.data.FilterListSkills;
import com.example.patiencebayogha.talentmapwithCouchbase.utils.LoaderUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by patiencebayogha on 02/06/15.
 */

public class SpinnerSkillAdapter extends BaseAdapter {
    private static final int LOADER__FILTER_LIST_SKILL = LoaderUtils.getInstance().getLoaderId();
    public ViewHolder holder;
    protected List<FilterListSkills> filterList;
    Activity myContext;
    List<FilterListSkills> list;
    String skill;

    private List<FilterListSkills> mList;




    public SpinnerSkillAdapter(Activity context, String skill) {

        myContext = context;
        list = new ArrayList<>();
        filterList = new ArrayList<>();
        this.skill = skill;
        mList = new ArrayList<>();
    }


    public void updateData(List<FilterListSkills> myFiltList) {
        this.filterList = myFiltList;
        if (myFiltList != null) {
            filterList = myFiltList;
            list = filterList;
            if (skill != null)
                ((SearchFilterActivity) myContext).modifFilterResultSearch(findPosition(skill));
            notifyDataSetChanged();
        }
        notifyDataSetChanged();
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater mInflater = (LayoutInflater)
                myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.spinner_skill_adapter_get_skill, null);
            holder = new ViewHolder();
            holder.ChoiceSkill = (TextView) convertView.findViewById(R.id.autocompletion);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        String rowItem = getItem(position).getName();
        holder.ChoiceSkill.setText(rowItem);
        return convertView;
    }

    @Override
    public int getCount() {
        return filterList.size();
    }

    @Override
    public FilterListSkills getItem(int position) {
        return filterList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return filterList.indexOf(getItem(position));
    }


    public void filterSkill(String categoryId) {
        filterList = new ArrayList<>();
        if (categoryId.equalsIgnoreCase("")) {
            filterList = list;
        } else for (FilterListSkills filterListSkills : list) {
            if (filterListSkills.getCategory().equalsIgnoreCase(categoryId)) {
                filterList.add(filterListSkills);
            }
        }

        notifyDataSetChanged();
    }

    public int findPosition(String skill) {
        for (int i = 0; i < list.size(); i++) {
            if (skill.equalsIgnoreCase(list.get(i).getName())) {
                return i;
            }

        }
        return -1; //si on ne trouve pas le skill
    }

    private class ViewHolder {
        public TextView ChoiceSkill;
    }


}


