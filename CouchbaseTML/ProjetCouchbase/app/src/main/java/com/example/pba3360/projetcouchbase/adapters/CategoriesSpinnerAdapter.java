package com.example.pba3360.projetcouchbase.adapters;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.pba3360.projetcouchbase.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

/**
 * Created by patiencebayogha on 02/06/15.
 */
public class CategoriesSpinnerAdapter extends BaseAdapter {

    public static ViewHolder holder;
    protected Handler handler;
    Activity myContext;
    private Properties properties;
    private ArrayList<String> categoriesIds;

    public CategoriesSpinnerAdapter(Activity context, Handler mHandle) {

        myContext = context;
        handler = mHandle;
        properties = new Properties();

        try {
            properties.loadFromXML(context.getAssets().open("categories.xml"));

        } catch (IOException e) {
            e.printStackTrace();
        }
        categoriesIds = new ArrayList<>();
        for (String a : properties.stringPropertyNames()) {
            categoriesIds.add(a);

        }


    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater mInflater = (LayoutInflater)
                myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.categories_spinner_adapter, null);
            holder = new ViewHolder();
            holder.categoryName = (TextView) convertView.findViewById(R.id.category);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        String rowItem = (String) properties.get(getItem(position));

        holder.categoryName.setText(rowItem);

        return convertView;
    }

    @Override
    public int getCount() {
        return categoriesIds.size();
    }

    @Override
    public String getItem(int position) {
        return categoriesIds.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder {
        TextView categoryName;
    }
}
