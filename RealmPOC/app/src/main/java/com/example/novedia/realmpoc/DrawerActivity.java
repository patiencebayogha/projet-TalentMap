package com.example.novedia.realmpoc;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.novedia.realmpoc.fragments.AgeQueryFragment;
import com.example.novedia.realmpoc.fragments.DataBaseObjectsFragments;
import com.example.novedia.realmpoc.fragments.DataBasePersonsFragment;
import com.example.novedia.realmpoc.fragments.FilterBookByPersonFragment;
import com.example.novedia.realmpoc.fragments.NameBookQueryFragment;
import com.example.novedia.realmpoc.fragments.NameQueryFragment;
import com.example.novedia.realmpoc.fragments.PriceBookQueryFragment;

/**
 * Created by patiencebayogha on 06/05/15.
 * Navigation Drawer for RealmPoc and Relation
 * 12/05/2015
 */
public class DrawerActivity extends Activity {

    public static String AGE = "age";
    public static String NAME = "name";
    Fragment objFragment = null;                       //for choice Fragment used
    private DrawerLayout mDrawerLayout;                 //for navigation drawer
    private ListView mDrawerList;                         //recup list name in navifgation drawee
    private ActionBarDrawerToggle mDrawerToggle;        //for have a Drawer Toggle
    private CharSequence mDrawerTitle;                  //Title in drawer
    private CharSequence mTitle;                        //for have title
    private String[] mTitles;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_drawer);


        mTitle = mDrawerTitle = getTitle();
        mTitles = getResources().getStringArray(R.array.title_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);


        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        // set up the drawer's list view with items and click listener
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item_drawer_activity, mTitles));

        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());


        // enable ActionBar app icon to behave as action to toggle nav drawer
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
        ) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                super.onDrawerClosed(view);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                super.onDrawerClosed(drawerView);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            selectItem(0);
        }
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action buttons
        switch (item.getItemId()) {

            case R.id.main_fragment:

            case R.id.age_fragment:

            case R.id.name_fragment:

            case R.id.relation_fragment:

            case R.id.filter_book_fragment:

            case R.id.filter_price_book_fragment:

            case R.id.filter_name_book_fragment:

        }
        return super.onOptionsItemSelected(item);
    }


    private void selectItem(int position) {
        // update the main content by replacing fragments
        Fragment fragment = new TitleFragment();
        Bundle args = new Bundle();
        args.putInt(TitleFragment.ARG_TITLE_NUMBER, position);
        fragment.setArguments(args);

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();


        switch (position) {
            case 0:
                objFragment = new DataBasePersonsFragment();

                break;


            case 1:
                objFragment = new AgeQueryFragment();

                break;

            case 2:
                objFragment = new NameQueryFragment();

                break;

            case 3:
                objFragment = new DataBaseObjectsFragments();

                break;


            case 4:
                objFragment = new FilterBookByPersonFragment();

                break;

            case 5:
                objFragment = new PriceBookQueryFragment();

                break;

            case 6:
                objFragment = new NameBookQueryFragment();

                break;


        }
        // update the main content by replacing fragments
        //  FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, objFragment)
                .commit();

        // update selected item and title, then close the drawer
        mDrawerList.setItemChecked(position, true);
        setTitle(mTitles[position]);
        mDrawerLayout.closeDrawer(mDrawerList);
    }


    public void onSectionAttached(int number) {
        switch (number) {
            case 0:
                mTitle = getString(R.string.drawer_activity_mainactivity);
                break;

            case 1:
                mTitle = getString(R.string.drawer_activity_search_by_age);
                break;

            case 2:
                mTitle = getString(R.string.drawer_activity_search_by_name);
                break;

            case 3:
                mTitle = getString(R.string.drawer_activity_relationactivity);
                break;


            case 4:
                mTitle = getString(R.string.drawer_activity_get_filter_book_by_person);
                break;

            case 5:
                mTitle = getString(R.string.drawer_activity_get_filter_price_book);
                break;

            case 6:
                mTitle = getString(R.string.drawer_activity_get_filter_name_book);
                break;
        }

    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);

    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }


    /**
     * Fragment that appears in the "content_frame", shows a title
     */
    public static class TitleFragment extends Fragment {
        public static final String ARG_TITLE_NUMBER = "title_number";

        public TitleFragment() {
            // Empty constructor required for fragment subclasses

        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static TitleFragment newInstance(int sectionNumber) {
            TitleFragment fragment = new TitleFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_TITLE_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_data_base_person, container, false);
            int i = getArguments().getInt(ARG_TITLE_NUMBER);
            String title = getResources().getStringArray(R.array.title_array)[i];

            getActivity().setTitle(title);

            return rootView;
        }
    }

    /* The click listner for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }

    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == 1) {
            if (resultCode != 0) {
                int age = data.getExtras().getInt(AGE);
                String name = data.getExtras().getString(NAME);
            }
        }

        if (requestCode == 2) {
            if (resultCode != 0) {
                int age = data.getExtras().getInt(AGE);
                String name = data.getExtras().getString(NAME);
            }
        }

    }

}

