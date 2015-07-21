package com.example.novedia.realmpoc.adapters;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.novedia.realmpoc.R;
import com.example.novedia.realmpoc.model.ModelObject;

import java.util.ArrayList;

/**
 * Created by l.olarte on 14/11/2014.
 */
public class ObjectResultAdapter extends BaseAdapter {
    private static final String TAG = ObjectResultAdapter.class.getCanonicalName();
    private LayoutInflater inflater;
    private ArrayList<ModelObject> objectArrayList;

    public ObjectResultAdapter(Activity activity, ArrayList<ModelObject> objectList) {
        this.objectArrayList = new ArrayList<ModelObject>(objectList);
        inflater = LayoutInflater.from(activity);
    }

    @Override
    public int getCount() {
        return objectArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return objectArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        Holder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.book_item, null);
            holder = new Holder();
            holder.title = (TextView) convertView.findViewById(R.id.book_title);
            holder.price= (TextView) convertView.findViewById(R.id.book_price);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        ModelObject modelObject = (ModelObject) getItem(i);
        holder.title.setText(modelObject.getName());
        holder.price.setText("" + modelObject.getPrice());

        return convertView;
    }

    public void reload(ArrayList<ModelObject> modelObjects) {
        objectArrayList.clear();
        if (modelObjects != null)
            objectArrayList.addAll(modelObjects);
        else
            Log.i(getClass().getCanonicalName(), "No objects in the list");
        notifyDataSetChanged();
    }

    private class Holder {
        TextView title, price;
    }
}
