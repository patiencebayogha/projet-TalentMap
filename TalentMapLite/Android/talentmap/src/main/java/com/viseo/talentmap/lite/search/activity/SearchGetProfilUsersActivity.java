package com.viseo.talentmap.lite.search.activity;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.viseo.talentmap.R;
import com.viseo.talentmap.common.utils.LoaderUtils;
import com.viseo.talentmap.lite.search.adapter.SearchAdapterGetProfilUsers;
import com.viseo.talentmap.lite.search.data.SearchUser;
import com.viseo.talentmap.lite.search.loader.SearchGetProfilUsersLoader;
import com.viseo.talentmap.view.DistantImageView;

/**
 * Created by patiencebayogha on 31/03/15.
 * last revision:23/04/2015
 * This activity will display the desired user profile
 */
public class SearchGetProfilUsersActivity extends Activity implements LoaderManager.LoaderCallbacks<SearchUser> {
    public static final String EMAIL_TO_SEARCH = "email_to_search";             //pass to activity
    private static final int LOADER_LIST = LoaderUtils.getInstance().getLoaderId();         //add list others users loader Id
    protected Handler myHandler;            //for activity
    SearchUser searchUsers = new SearchUser();      //for recuperate data SearchUser
    private TextView getNameSearch;                  //Textview for name other user
    private TextView getsurnameSearch;               //Textview for lastname other user
    private ListView getList;                       //listview for get list others users
    private DistantImageView getImage;              //recup distant image of other user
    private SearchAdapterGetProfilUsers myAdapter;      //adapter for get other users in display
    private String email;                               //pass to activity

    @Override
    public void onCreate(Bundle savedInstanceState) {
        myHandler = new Handler();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_get_profil_users_activity);

        email = getIntent().getExtras().getString(EMAIL_TO_SEARCH);
        initLoader();       //recuperate Loader (list users)

        getList = (ListView) findViewById(R.id.swipedelete);
        myAdapter = new SearchAdapterGetProfilUsers(this, myHandler, email);
        getList.setAdapter(myAdapter);
        getImage = (DistantImageView) findViewById(R.id.image_users);
        getNameSearch = (TextView) findViewById(R.id.name_users);


        getsurnameSearch = (TextView) findViewById(R.id.surname_users);

        //visible cursor set is true
        getNameSearch.setFocusableInTouchMode(false);
        getsurnameSearch.setFocusableInTouchMode(false);

    }

    public void initLoader() {
        //method for recuperate List users Loader
        if (getLoaderManager().getLoader(LOADER_LIST) == null) {
            getLoaderManager().initLoader(LOADER_LIST, null, SearchGetProfilUsersActivity.this);
        } else {
            getLoaderManager().restartLoader(LOADER_LIST, null, SearchGetProfilUsersActivity.this);
        }
    }


    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        return new SearchGetProfilUsersLoader(this, email);
    }

    @Override
    public void onLoadFinished(Loader<SearchUser> loader, SearchUser data) {
        if (LOADER_LIST == loader.getId()) {
            if (data != null) {
                searchUsers = data;
                getImage.setHandler(myHandler);
                getNameSearch.setText(searchUsers.getName());
                getImage.setHandler(myHandler);
                getImage.setUrl(searchUsers.getPhoto());
                getImage.setImageDrawable(getResources().getDrawable(R.drawable.default_profile_img));
                getsurnameSearch.setText(searchUsers.getSurname());
                myAdapter.setData(searchUsers.getSkills());
            }
        } else {
            Log.e(getClass().getCanonicalName(), "error during loading: SearchProfilUsersActivity. Data null");
        }
    }

    @Override
    public void onLoaderReset(Loader<SearchUser> loader) {
    }


    public ListView getListView() {
        //recup Listview
        return getList;
    }
}
