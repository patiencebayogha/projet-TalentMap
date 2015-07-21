package com.viseo.talentmap.lite.search.adapter;

/**
 * Created by patiencebayogha on 16/04/15.
 * last revision:23/04/15
 * This activity will display to recuperate skill textview in spinner
 * Adapter  Spinner for SearchFilterActivity
 */

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.viseo.talentmap.R;
import com.viseo.talentmap.common.utils.LoaderUtils;
import com.viseo.talentmap.lite.search.activity.SearchFilterActivity;
import com.viseo.talentmap.modifications_skills.data.FilterlistSkills;
import com.viseo.talentmap.modifications_skills.loader.SkillFilterLoader;

import java.util.ArrayList;
import java.util.List;


public class SpinnerSkillAdapter extends BaseAdapter implements LoaderManager.LoaderCallbacks<ArrayList<FilterlistSkills>> {
    private static final int LOADER__FILTER_LIST_SKILL = LoaderUtils.getInstance().getLoaderId();
    public ViewHolder holder;
    protected List<FilterlistSkills> filterList;
    Activity myContext;
    List<FilterlistSkills> list;
    String skill;


    public SpinnerSkillAdapter(Activity context, String skill) {

        myContext = context;
        list = new ArrayList<>();
        filterList = new ArrayList<>();
        loadDataFilter();
        this.skill = skill;
    }


    public void loadDataFilter() {
        if (myContext.getLoaderManager().getLoader(LOADER__FILTER_LIST_SKILL) == null) {
            myContext.getLoaderManager().initLoader(LOADER__FILTER_LIST_SKILL, null, this);
        } else {
            myContext.getLoaderManager().restartLoader(LOADER__FILTER_LIST_SKILL, null, this);
        }
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
    public FilterlistSkills getItem(int position) {
        return filterList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return filterList.indexOf(getItem(position));
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        return new SkillFilterLoader(myContext);

    }

    @Override
    public void onLoadFinished(Loader<ArrayList<FilterlistSkills>> loader, ArrayList<FilterlistSkills> data) {
        if (data != null) {
            filterList = data;
            list = filterList;
            if (skill != null)
                ((SearchFilterActivity) myContext).modifFilterResultSearch(findPosition(skill));
            notifyDataSetChanged();
        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<FilterlistSkills>> loader) {

    }

    public void filterSkill(String categoryId) {
        filterList = new ArrayList<>();
        if (categoryId.equalsIgnoreCase("")) {
            filterList = list;
        } else for (FilterlistSkills filterListSkills : list) {
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
