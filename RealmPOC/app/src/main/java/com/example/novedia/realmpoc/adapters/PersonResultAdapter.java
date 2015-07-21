package com.example.novedia.realmpoc.adapters;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.novedia.realmpoc.R;
import com.example.novedia.realmpoc.model.Person;

import java.util.ArrayList;

import activities.AddBookToPersonActivity;
import activities.SelectedBookForPersonActivity;

/**
 * Created by l.olarte on 13/11/2014.
 */
public class PersonResultAdapter extends BaseAdapter {
    private static final String TAG = PersonResultAdapter.class.getCanonicalName();
    private LayoutInflater inflater;
    public static String POSITION_OF_PEOPLE = "Position";
    private ArrayList<Person> personArrayList;
    private Activity context;
    Holder holder = null;

    public PersonResultAdapter(Activity activity, ArrayList<Person> personList) {
        this.personArrayList = new ArrayList<Person>(personList);
        inflater = LayoutInflater.from(activity);
        this.context = activity;
    }

    @Override
    public int getCount() {
        return (personArrayList != null) ? personArrayList.size() : 0;
    }

    @Override
    public Object getItem(int i) {
        return personArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View convertView, ViewGroup viewGroup) {

        Log.e(TAG, "position=" + i);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.person_item, null);
            holder = new Holder();
            holder.name = (TextView) convertView.findViewById(R.id.person_name);
            holder.age = (TextView) convertView.findViewById(R.id.person_age);
            holder.addBook = (TextView) convertView.findViewById(R.id.addBook);
            holder.choiceBook = (TextView) convertView.findViewById(R.id.choiceBook);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        holder.choiceBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SelectedBookForPersonActivity.class);
                //TODO: test recuperation position
                intent.putExtra(POSITION_OF_PEOPLE, i);
                context.startActivity(intent);
            }
        });

        holder.addBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, AddBookToPersonActivity.class);
                //TODO: test recuperation position
                intent.putExtra(POSITION_OF_PEOPLE, i);
                context.startActivity(intent);

            }
        });
        Person person = (Person) getItem(i);
        holder.name.setText(person.getName());
        holder.age.setText("" + person.getAge());

        return convertView;
    }

    public void reload(ArrayList<Person> personList) {
        personArrayList.clear();
        if (personList != null)
            personArrayList.addAll(personList);
        else
            Log.i(getClass().getCanonicalName(), "No person in the list");
        notifyDataSetChanged();
    }


    private class Holder {
        TextView name, age;
        TextView addBook;
        TextView choiceBook;
    }



}
