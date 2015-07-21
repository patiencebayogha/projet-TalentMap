package com.viseo.talentmap.lite.search.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.viseo.talentmap.R;
import com.viseo.talentmap.lite.search.activity.SearchFilterActivity;
import com.viseo.talentmap.lite.search.activity.SearchGetProfilUsersActivity;
import com.viseo.talentmap.lite.search.adapter.SearchAdapterFragment;

/**
 * Created by patiencebayogha on 20/02/15.
 * 05.03.2015
 * 09/03/15
 * 23/04/15
 * recuperate listview, text for show number result, listview users,
 */
public class SearchFragment extends Fragment implements AdapterView.OnItemClickListener {
    protected Handler mHandler;
    protected ListView listView;
    private ImageButton filter;
    private SearchAdapterFragment searchAdapter;
    private TextView result;
    private TextView resultfilter;
    private ImageView delete_filter;

    public SearchFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment

        View resultView = inflater.inflate(R.layout.fragment_search, container, false);
        mHandler = new Handler();
        resultfilter = (TextView) resultView.findViewById(R.id.resultfilter);
        result = (TextView) resultView.findViewById(R.id.result);
        listView = (ListView) resultView.findViewById(R.id.listUser);
        searchAdapter = new SearchAdapterFragment(getActivity(), mHandler);
        listView.setAdapter(searchAdapter);
        delete_filter = (ImageView) resultView.findViewById(R.id.delete_filter);
        listView.setOnItemClickListener(this);
        filter = (ImageButton) resultView.findViewById(R.id.add_filter);


        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchFilterActivity.class);
                startActivityForResult(intent, 1);

            }
        });
        searchAdapter.loadData();//loading data

        return resultView;

    }

    public void afficheResult() {
        result.setText((searchAdapter.getCount()) + "," + "résultat(s) trouvé(s). ");
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        Log.d(getClass().getCanonicalName(), "Update SearchFragment background task launch.");
        Intent getMe = new Intent(getActivity(), SearchGetProfilUsersActivity.class);
        getMe.putExtra(SearchGetProfilUsersActivity.EMAIL_TO_SEARCH, searchAdapter.getItem(position).getEmail());

        startActivity(getMe);

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }


        afficheResult();

        final String skill = data.getStringExtra("skill");
        final int level = data.getIntExtra("level", 0);
        searchAdapter.filterListUsers(skill, level);

        resultfilter.setVisibility(View.VISIBLE);
        resultfilter.setText(skill + "-" + level);
        resultfilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), SearchFilterActivity.class);
                intent.putExtra("skill", skill);
                intent.putExtra("level", level);
                startActivityForResult(intent, 1);
            }
        });


        delete_filter.setVisibility(View.VISIBLE);
        delete_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultfilter.setVisibility(View.GONE);
                delete_filter.setVisibility(View.GONE);
                searchAdapter.loadData();
            }
        });


    }


}
