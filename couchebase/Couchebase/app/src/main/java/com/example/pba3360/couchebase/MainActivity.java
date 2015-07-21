package com.example.pba3360.couchebase;

import android.provider.DocumentsContract;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.Document;
import com.couchbase.lite.Manager;
import com.couchbase.lite.UnsavedRevision;
import com.couchbase.lite.android.AndroidContext;
import com.couchbase.lite.util.Log;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static android.provider.DocumentsContract.createDocument;


public class MainActivity extends ActionBarActivity {


    public static final String TAG = "MainActivity";
    public static final String DB_NAME = "couchbase";
    Manager manager = null;
    Database database = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "Begin Couchbase Events App");
        Log.d(TAG, "End Couchbase Events App");

        helloCBL();  //call method helloCBL
        //retrieve doc in DB
        Document retrivedDoc= database.getDocument(docId);

    }

    private void helloCBL() {


        try {
            manager = new Manager(new AndroidContext(this), Manager.DEFAULT_OPTIONS);  //requierd for pass to the constructor
            database = manager.getDatabase(DB_NAME);
        } catch (Exception e) {
            Log.d(TAG, "Error getting database", e);
            return;
        }


        //Creation of document
        String docId = createDocument(database);
        //Get and output the contents
        outputContents(database, docId);
        //update document and add attachment
        updateDoc(database, docId);
        //add attachment
        addAttachment(database, docId);
        //output the contents with attachment
        outputContentsWithAttachment(database, docId);


    }



    //method for create document
    private String createDocument(Database database) {
//new document and add data
        Document doc = database.createDocument();
        String docId = doc.getId();
        Map<String, Object> map = new HashMap<>();
        map.put("name", "Bellanger");
        map.put("adresse", "Paris");
        try {
            //save propreties to the doc
            doc.putProperties(map);
        } catch (Exception d) {
            Log.d(TAG, "Error during put", d);
        }
        return docId;
    }

    private void updateDoc(Database database, String docId) {

        Document document = database.getDocument(docId);
        try {
            // Update the document with more data
            Map<String, Object> mapProprerties = new HashMap<String, Object>();
            mapProprerties.putAll(document.getProperties());
            mapProprerties.put("eventDescription", "Come on!");
            mapProprerties.put("address", "rue de l'Android");
            // Save to the Couchbase local Couchbase Lite DB
            document.putProperties(mapProprerties);
        } catch (CouchbaseLiteException e) {
            Log.e(TAG, "Error during put", e);
        }
    }

    private void outputContentsWithAttachment(Database database, String docId) {
    }

    private void addAttachment(Database database, String docId) {
        Document document = database.getDocument(docId);
        try {
            ByteArrayInputStream inputStream= new ByteArrayInputStream(new byte[]{0,0,0,0});
            UnsavedRevision unsavedRevision=document.getCurrentRevision().createRevision();
            unsavedRevision.setAttachment("binaryData", "application/octet-stream");
            unsavedRevision.save();
        } catch (CouchbaseLiteException e) {
            Log.e(TAG, "Error during put", e);
        }

    }

    private void outputContents(Database database, String docId) {
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
