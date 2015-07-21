package com.viseo.talentmap.lite.search.adapter;

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
import android.widget.TextView;

import com.viseo.talentmap.R;
import com.viseo.talentmap.common.utils.LoaderUtils;
import com.viseo.talentmap.lite.MainActivity;
import com.viseo.talentmap.lite.search.data.SearchSkillsUsers;
import com.viseo.talentmap.lite.search.data.SearchUser;
import com.viseo.talentmap.lite.search.loader.SearchFragmentLoader;
import com.viseo.talentmap.view.DistantImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by patiencebayogha on 09/03/15.
 * modification
 * last revision:23/04/2015
 * Adapter for recuperate name, surname, picture of users in search
 * 10 and 11/03/2015
 */
public class SearchAdapterFragment extends BaseAdapter implements LoaderManager.LoaderCallbacks<ArrayList<SearchUser>> {

    private final int LOADER_SEARCH = LoaderUtils.getInstance().getLoaderId();
    Activity context;
    List<SearchUser> searchUser;
    Handler handler;
    ViewHolder holder;

    public SearchAdapterFragment(Activity context, Handler handle) {

        this.context = context;
        searchUser = new ArrayList<SearchUser>();
        handler = handle;

    }


    //Chargement de la liste a faire obligatoirement
    public void loadData() {

        if (context.getLoaderManager().getLoader(LOADER_SEARCH) == null) {
            context.getLoaderManager().initLoader(LOADER_SEARCH, null, this);
        } else {
            context.getLoaderManager().restartLoader(LOADER_SEARCH, null, this);
        }


    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater mInflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.search_adapter_fragment_listview_item_, null);
            holder = new ViewHolder();
            holder.txtName = (TextView) convertView.findViewById(R.id.search_list_item_name);
            holder.surname = (TextView) convertView.findViewById(R.id.search_list_item_surname);
            holder.photo = (DistantImageView) convertView.findViewById(R.id.search_list_item_image);
            holder.photo.setHandler(handler);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        SearchUser rowItem = (SearchUser) getItem(position);

        holder.txtName.setText(rowItem.getName());

        holder.photo.setUrl(rowItem.getPhoto());
        holder.photo.setDefaultImage(context.getResources().getDrawable(R.drawable.default_profile_img));
        holder.surname.setText((rowItem.getSurname()));


        return convertView;
    }

    @Override
    public int getCount() {
        return searchUser.size()-1;
    }

    @Override
    public SearchUser getItem(int position) {
        return searchUser.get(position);
    }

    @Override
    public long getItemId(int position) {
        return searchUser.indexOf(getItem(position));
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        //Instantiate and return a new Loader for the given ID
        return new SearchFragmentLoader(context);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<SearchUser>> loader, ArrayList<SearchUser> data) {


        searchUser = data;
        if (data != null) {
            searchUser = data;
        }
        ((MainActivity) context).showResults();
        notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<SearchUser>> loader) {

    }

    public void filterListUsers(String skills, int level) {
        ArrayList<SearchUser> filterUsers = new ArrayList<>();
        for (SearchUser user : searchUser) {
            for (SearchSkillsUsers skill : user.getSkills()) {
                if (skill.getSkill().equalsIgnoreCase(skills) && (skill.getLevel() >= level)) {
                    filterUsers.add(user);
                }
            }
        }
        searchUser.clear();
        searchUser.addAll(filterUsers);
        ((MainActivity) context).showResults();
        notifyDataSetChanged();
    }

    /*private view holder class*/
    private class ViewHolder {
        DistantImageView photo;
        TextView txtName;
        TextView surname;
    }

}
