package com.example.pba3360.projetcouchbase.activities;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Document;
import com.couchbase.lite.LiveQuery;
import com.example.pba3360.projetcouchbase.R;
import com.example.pba3360.projetcouchbase.couchbase.Application;
import com.example.pba3360.projetcouchbase.couchbase.Presentation;

/**
 * Created by PBA3360 on 06/07/2015.
 */
public class PresentationAdapter extends QueryAdapter {

    public PresentationAdapter(LiveQuery query, Context context) {
        super(query, context);
    }

    @Override
    // Display a Presentation as an Item in the list View
    public View getView(int position, View convertView, ViewGroup parent) {
        // Load the View if not done so already from the view_presentation.xml
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.view_presentation, null);
        }

        // Get the document currently to be displayed
        final Document document = (Document) getItem(position);

        // make sure this is valid
        if (document == null || document.getCurrentRevision() == null) {
            return convertView;
        }

        // Turn the document into a presentation model we can operate on
        final Presentation presentation = Presentation.from(document);



        // Fill in all the view items
        TextView titleView = (TextView) convertView.findViewById(R.id.autocompletion);
        titleView.setText(presentation.getTitle());

        return convertView;

    }
}


