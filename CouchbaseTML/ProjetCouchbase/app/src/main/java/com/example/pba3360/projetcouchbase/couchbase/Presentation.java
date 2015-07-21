package com.example.pba3360.projetcouchbase.couchbase;

import android.util.Log;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.Document;
import com.couchbase.lite.Emitter;
import com.couchbase.lite.Mapper;
import com.couchbase.lite.Query;
import com.couchbase.lite.View;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class Presentation {
    // HINT create a property in the presentations so you can find them among the objects
    public static final String TYPE = Presentation.class.getName();

    public static final String TYPE_TAG        = "type";
    public static final String CREATEED_AT_TAG = "created_at";
    public static final String TITLE_TAG       = "title";


    private Date createdAt;
    private String title;
    private Document sourceDocument;
    private Database database;

    public static Query findAll(Database database) {
        if (null == database) return null;

        View view = database.getView(TYPE);

        if (view.getMap() == null) {

            view.setMap(new Mapper() {
                @Override
                public void map(Map<String, Object> document, Emitter emitter) {
                    String presentationType = (String) document.get(TYPE_TAG);
                    if (presentationType.equals(TYPE)){
                        emitter.emit(document.get(CREATEED_AT_TAG), document);
                    }
                }
            }, Application.dbversion);
        }

        return view.createQuery();
    }

    public static Presentation from(Document document) {

        Presentation presentation = null;

        if (null != document && null != document.getDatabase()){

            presentation = new Presentation(document.getDatabase());


            long createdAtL = 0;
            Object createdAt = document.getProperty(CREATEED_AT_TAG);
            if (createdAt instanceof Double){
                createdAtL = ((Double) createdAt).longValue();
            }
            if (createdAt instanceof Long){
                createdAtL = (Long) createdAt;
            }
            presentation.setCreatedAt(new Date(createdAtL));

            Object title = document.getProperty(TITLE_TAG);
            if (null != title)  presentation.setTitle((String) title);

        }

        presentation.setSourceDocument(document);
        return presentation;
    }

    public Presentation(Database database) {
        this.database = database;
        this.createdAt = new Date();
        this.title = "";
    }

    public void save() throws CouchbaseLiteException {

        Map<String, Object> docMap = new HashMap<String, Object>();
        Document document;

        if (sourceDocument == null) {
            document = database.createDocument();
        } else {
            document = sourceDocument;
            docMap.putAll(sourceDocument.getProperties());
        }

        docMap.put(TYPE_TAG, TYPE);
        docMap.put(TITLE_TAG, getTitle());
        docMap.put(CREATEED_AT_TAG, getCreatedAt());

        document.putProperties(docMap);

        Log.d(TYPE, document.getProperties().toString());
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Document getSourceDocument() {
        return sourceDocument;
    }

    public void setSourceDocument(Document sourceDocument) {
        this.sourceDocument = sourceDocument;
    }
}
