package com.example.pba3360.projetcouchbase.couchbase;

import android.util.Log;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.LiveQuery;
import com.couchbase.lite.Manager;
import com.couchbase.lite.android.AndroidContext;
import com.couchbase.lite.replicator.Replication;
import com.example.pba3360.projetcouchbase.activities.SkillsActivity;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class Application extends android.app.Application {
    public static String mTAG = "talentMaplite";
    // Reference to the sync gateway to use, this should be your local one unless you are running it in the "cloud"
    private static final String SYNC_URL_HTTP = "127.0.0.1:4984/default";

    public static final String dbversion = "1";
    private Database database = null;
    private Manager manager;
    private String dbname = "database_tml";

    private Replication pull;
    private Replication push;

    public Application(SkillsActivity skillsActivity) {
    }


    @Override
    public void onCreate() {
        setupDataBase();
        setupSync();
        super.onCreate();
    }

    public Database getDatabase() {
        if (null == database) setupDataBase();
        return database;
    }


    //Open synchronisation
    private void setupSync() {
        URL url;
        try {
            url = new URL(SYNC_URL_HTTP);
        } catch (MalformedURLException e) {
            Log.e(Application.mTAG, "Sync URL is invalid, setting up sync failed");
            return;
        }

        if (null == database) return;

        pull = database.createPullReplication(url);
        push = database.createPushReplication(url);

        if (pull == null || push == null) return;

        pull.setContinuous(true);
        push.setContinuous(true);

        pull.addChangeListener(getReplicationChangeListener());
        push.addChangeListener(getReplicationChangeListener());

        toggleOnSync();
    }

    //Open database
    private void setupDataBase() {

        try {

            manager = new Manager(new AndroidContext(this), Manager.DEFAULT_OPTIONS);
            Log.d(mTAG, "Manager created");

            if (!Manager.isValidDatabaseName(dbname)) {
                Log.e(mTAG, "Bad database name");
                return;
            }

            database = manager.getDatabase(dbname);
            Log.d(mTAG, "Database created");
        } catch (CouchbaseLiteException e) {
            Log.e(mTAG, "Cannot get database");
            return;
        } catch (IOException e) {
            Log.e(mTAG, "Cannot create manager object");
            return;
        }

    }



    public void toggleOffSync() {
        pull.stop();
        push.stop();
    }

    public void toggleOnSync() {
        pull.start();
        push.start();
    }

    public boolean isSyncOn() {
        return pull.isRunning();
    }


    // print out errors and see what is going on
    @SuppressWarnings("ThrowableInstanceNeverThrown")
    private Replication.ChangeListener getReplicationChangeListener() {
        return new Replication.ChangeListener() {
            @Override
            public void changed(Replication.ChangeEvent event) {
                Replication replication = event.getSource();
                if (replication.getLastError() != null) {
                    Throwable lastError = replication.getLastError();
                    if (lastError.getMessage().contains("existing change tracker")) {
                        Log.e("Replication Event", String.format("Sync error: %s:", lastError.getMessage()));
                    }
                }
                Log.d(mTAG, event.toString());
                Log.d(mTAG, "Completed: " + replication.getCompletedChangesCount() + " of " + replication.getChangesCount());
            }
        };
    }

}
