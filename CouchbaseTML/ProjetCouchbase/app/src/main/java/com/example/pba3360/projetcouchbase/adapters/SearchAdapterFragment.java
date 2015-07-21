package com.example.pba3360.projetcouchbase.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.example.pba3360.projetcouchbase.R;
import com.example.pba3360.projetcouchbase.activities.MainActivity;
import com.example.pba3360.projetcouchbase.data.SearchSkillsUsers;
import com.example.pba3360.projetcouchbase.data.SearchUser;
import com.example.pba3360.projetcouchbase.singletons.AppController;
import com.example.pba3360.projetcouchbase.utils.CircleImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by patiencebayogha on 01/06/15.
 */
public class SearchAdapterFragment extends BaseAdapter {

    //Log or request TAG
    private static final String TAG = SearchAdapterFragment.class.getSimpleName();
    Activity context;
    List<SearchUser> searchUser;
    ImageLoader imageLoader;
    ViewHolder holder;
    LayoutInflater mInflater;


    public SearchAdapterFragment(Activity context) {
        this.context = context;
        this.searchUser = new ArrayList<>();
        imageLoader = AppController.getInstance().getImageLoader();
        mInflater = LayoutInflater.from(context);

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


    public View getView(int position, View convertView, ViewGroup parent) {
        if (mInflater == null)
            mInflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.search_adapter_fragment_listview_item_, null);
            holder = new ViewHolder();

            if (imageLoader == null) {
                imageLoader = AppController.getInstance().getImageLoader();
            }
            holder.photo = (CircleImageView) convertView.findViewById(R.id.search_list_item_image);

            holder.txtName = (TextView) convertView.findViewById(R.id.search_list_item_name);
            holder.surname = (TextView) convertView.findViewById(R.id.search_list_item_surname);


            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        // getting user data for the row
        SearchUser rowItem = searchUser.get(position);

        //   On récupère les informations depuis le JSONObject et on les relie aux vues
        //name and surname
        holder.txtName.setText(rowItem.getName());
        holder.surname.setText((rowItem.getSurname()));

        //image
        holder.photo.setImageUrl(rowItem.getPhoto(), imageLoader);
        holder.photo.setDefaultImageResId(R.drawable.default_profile_img);

        return convertView;
    }

    public void updateData(ArrayList<SearchUser> searchUserArrayList) {
        if (searchUserArrayList != null) {
            searchUser = searchUserArrayList;
        }
        ((MainActivity) context).showResults();
        notifyDataSetChanged();
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
        CircleImageView photo;
        TextView txtName;
        TextView surname;
    }



}
