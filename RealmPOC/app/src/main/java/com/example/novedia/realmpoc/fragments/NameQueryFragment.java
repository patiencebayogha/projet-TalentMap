package com.example.novedia.realmpoc.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.novedia.realmpoc.R;
import com.example.novedia.realmpoc.adapters.PersonResultAdapter;
import com.example.novedia.realmpoc.model.Person;
import com.example.novedia.realmpoc.services.RealmPOCService;

import java.util.ArrayList;

/**
 * Created by patiencebayogha on 12/05/15.
 * this class allows to make filtering by name of person in database
 */
public class NameQueryFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = NameQueryFragment.class.getCanonicalName();
    private RealmPOCService realmPOCService;
    private PersonResultAdapter personResultAdapter;
    private ArrayList<Person> personList;
    ListView listView;
    View resultView;

    public NameQueryFragment() {
        super();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        realmPOCService = RealmPOCService.getInstance(getActivity());
        personList.clear();
        //Load all Elements of the list
        personList = realmPOCService.getAllPerson();
        personResultAdapter.reload(personList);
        personResultAdapter.notifyDataSetChanged();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        resultView = inflater.inflate(R.layout.fragment_name_query, container, false);


        initView();
        //Get DB instance
        realmPOCService = RealmPOCService.getInstance(getActivity());
        //Load all Elements of the list
        personList = realmPOCService.getAllPerson();
        personResultAdapter = new PersonResultAdapter(getActivity(), personList);

        listView = (ListView) resultView.findViewById(R.id.resultListView);
        listView.setItemsCanFocus(false);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setAdapter(personResultAdapter);
        listView.setItemChecked(0, true);

        return resultView;
    }


    /**
     * Innit view components and set onClick Listeners
     */
    private void initView() {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        (resultView.findViewById(R.id.getAllButton)).setOnClickListener(this);
        (resultView.findViewById(R.id.makeQueryButton)).setOnClickListener(this);
    }
    //Truncate the parent's scroll view

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.makeQueryButton) {
            switch (view.getId()) {
                case R.id.makeQueryButton:
                    try {
                        Log.i(getClass().getCanonicalName(), "Making a Query: Search by Name");
                        personList = realmPOCService.getByName(((EditText) resultView.findViewById(R.id.nameQueryText)).getText().toString());
                        //Intent i = new Intent(MainActivity.this, GetName.class);
                        //i.putExtra(EXTRA_NAME,(Serializable) personList);
                        // startActivity(i);
                    } catch (Exception e) {
                        Log.e(getClass().getCanonicalName(), "cant search by age");
                    }
                    break;
            }
            //rest will return all people
        } else {
            switch (view.getId()) {
                case R.id.getAllButton:
                    /**
                     *show all person in database
                     **/
                    personList = realmPOCService.getAllPerson();
                    Log.i(getClass().getCanonicalName(), "Get all people in DB");
                    if (personList.size() == 0)
                        Toast.makeText(getActivity(), "No elements in DB", Toast.LENGTH_LONG).show();
                    break;

            }
            personList = realmPOCService.getAllPerson();
        }
        personResultAdapter.reload(personList);


    }


}
