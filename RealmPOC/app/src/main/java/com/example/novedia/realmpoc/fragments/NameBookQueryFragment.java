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

import com.example.novedia.realmpoc.adapters.ObjectResultAdapter;
import com.example.novedia.realmpoc.R;
import com.example.novedia.realmpoc.model.ModelObject;
import com.example.novedia.realmpoc.model.Person;
import com.example.novedia.realmpoc.services.RelationPOCService;

import java.util.ArrayList;

/**
 * Created by patiencebayogha on 13/05/15.
 * this class allows to make filtering by title of book in database
 */
public class NameBookQueryFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = NameBookQueryFragment.class.getCanonicalName();
    private RelationPOCService relationPOCService;
    private ObjectResultAdapter objectResultAdapter;
    ArrayList<ModelObject> objectList;
     View resultView;

    public NameBookQueryFragment() {
        super();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        relationPOCService = RelationPOCService.getInstance(getActivity());
        objectList.clear();
        //Load all Elements of the list
        objectList = relationPOCService.getAllObjects();
        objectResultAdapter.reload(objectList);
        objectResultAdapter.notifyDataSetChanged();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        resultView = inflater.inflate(R.layout.fragment_name_book_query, container, false);


        initView();
        //Get DB instance
        relationPOCService = RelationPOCService.getInstance(getActivity());
        //Get first person of the DB and add him Objects
        ArrayList<Person> personList = relationPOCService.getAllPerson();

        ArrayList<ModelObject> modelObjects = new ArrayList<ModelObject>();
        ModelObject modelObject = new ModelObject();
        modelObject.getName();
        modelObject.getPrice();
        modelObjects.add(modelObject);

        objectList = relationPOCService.getAllObjects();
        //start adapter
        objectResultAdapter = new ObjectResultAdapter(getActivity(), objectList);
        ((ListView) resultView.findViewById(R.id.resultListView)).setAdapter(objectResultAdapter);
        return resultView;
    }


    /**
     * Innit view components and set onClick Listeners
     */
    private void initView() {


        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        (resultView.findViewById(R.id.getAllButton)).setOnClickListener(this);
        (resultView.findViewById(R.id.NameBookQueryButton)).setOnClickListener(this);
    }

    //Truncate the parent's scroll view

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.NameBookQueryButton) {
            switch (view.getId()) {
                case R.id.NameBookQueryButton:
                    try {
                        Log.i(getClass().getCanonicalName(), "Making a Query: Search by Age");
                        objectList = relationPOCService.getByName(((EditText) resultView.findViewById(R.id.nameBookQueryText)).getText().toString());

                    } catch (NumberFormatException e) {
                        Log.e(getClass().getCanonicalName(), "error format");
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
                    Log.i(getClass().getCanonicalName(), "Get all people in DB");
                    if (objectList.size() == 0)
                        Toast.makeText(getActivity(), "No elements in DB", Toast.LENGTH_LONG).show();
                    break;

            }

            objectList = relationPOCService.getAllObjects();

        }
        objectResultAdapter.reload(objectList);

    }
}
