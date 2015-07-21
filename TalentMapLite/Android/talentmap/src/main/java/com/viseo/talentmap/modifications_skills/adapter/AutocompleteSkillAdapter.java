package com.viseo.talentmap.modifications_skills.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.viseo.talentmap.R;
import com.viseo.talentmap.common.utils.LoaderUtils;
import com.viseo.talentmap.modifications_skills.data.FilterlistSkills;
import com.viseo.talentmap.modifications_skills.loader.SkillFilterLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by patiencebayogha on 02/04/15.
 * 23/04/15
 * this class implements the Filterable interface
 */
@SuppressLint("NewApi")
public class AutocompleteSkillAdapter extends BaseAdapter implements LoaderManager.LoaderCallbacks<ArrayList<FilterlistSkills>>, Filterable {
    private static final int LOADER__FILTER_LIST_SKILL = LoaderUtils.getInstance().getLoaderId();
    public static ViewHolder holder;
    protected Handler handler;
    protected List<FilterlistSkills> filterList;
    protected List<FilterlistSkills> nameList;
    Activity myContext;
    List<FilterlistSkills> list;
    private Filter filter;


    public AutocompleteSkillAdapter(Activity context, Handler mHandle) {

        myContext = context;
        list = new ArrayList<>();
        handler = mHandle;

    }


    public void loadDataFilter() {

        if (myContext.getLoaderManager().getLoader(LOADER__FILTER_LIST_SKILL) == null) {
            myContext.getLoaderManager().initLoader(LOADER__FILTER_LIST_SKILL, null, this);
        } else {
            myContext.getLoaderManager().restartLoader(LOADER__FILTER_LIST_SKILL, null, this);
        }


    }

    @Override
    /*
    This class extends android.widget.Filter and have 2 methods performFiltering(CharSequence constraint)
    publishResults (CharSequence constraint, FilterResults results)and
     */
    public Filter getFilter() {
        if (filter == null)
            filter = new AutocompleteFilter();
        return filter;
    }

    public View getView(int position, View convertView, ViewGroup parent) {


        LayoutInflater mInflater = (LayoutInflater)
                myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.spinner_skill_adapter_get_skill, null);
            holder = new ViewHolder();
            holder.autocompletete = (TextView) convertView.findViewById(R.id.autocompletion);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        String rowItem = getItem(position);


        holder.autocompletete.setText(rowItem);


        return convertView;
    }

    @Override
    public int getCount() {
        return filterList.size();
    }

    @Override
    public String getItem(int position) {
        return filterList.get(position).getName();
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
            list = data;
            filterList = list;

        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<FilterlistSkills>> loader) {

    }

    public void filterSkill(String categoryId) {
        filterList = new ArrayList<>();
        for (FilterlistSkills filterListSkills : list) {
            if (filterListSkills.getCategory().equalsIgnoreCase(categoryId)) {
                filterList.add(filterListSkills);
            }

        }
    }

    //Class for filter
    private class AutocompleteFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();

            if (constraint == null || constraint.length() == 0) {
                // No filter implemented we return all the list
                filterResults.values = filterList;
                filterResults.count = getCount();

            } else {
                nameList = new ArrayList<>();
                //we perform filtering operation
                for (FilterlistSkills namelistSkills : filterList) {
                    // Determine if string starts with letters
                    if (namelistSkills.getName().toUpperCase().startsWith(constraint.toString().toUpperCase())) {
                        nameList.add(namelistSkills);
                    }
                    filterResults.values = nameList;
                    // filterResults.count = getCount();
                    filterResults.count = nameList.size();
                }
                filterList = nameList;
            }
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (results != null && results.count > 0) {

                filterList = (List<FilterlistSkills>) results.values;
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }

        }
    }

    private class ViewHolder {

        public TextView autocompletete;


    }


}
