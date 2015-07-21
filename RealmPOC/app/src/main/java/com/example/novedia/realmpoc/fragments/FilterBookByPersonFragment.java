package com.example.novedia.realmpoc.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.novedia.realmpoc.R;
import com.example.novedia.realmpoc.adapters.ObjectResultAdapter;
import com.example.novedia.realmpoc.model.ModelObject;
import com.example.novedia.realmpoc.model.Person;
import com.example.novedia.realmpoc.services.RelationPOCService;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by patiencebayogha on 12/05/15.
 * this class allows to make the filtering of books attributed to a person
 */
public class FilterBookByPersonFragment extends Fragment implements View.OnClickListener {
    RelationPOCService relationPOCService;
    ObjectResultAdapter objectResultAdapter;
    ArrayList<ModelObject> objectList;
    private Person selectedPerson = null;
    View resultView;

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
        resultView = inflater.inflate(R.layout.fragment_filter_book_by_person, container, false);

        initView();
        relationPOCService = RelationPOCService.getInstance(getActivity());

        //Get first person of the DB and add him Objects
        ArrayList<Person> personList = relationPOCService.getAllPerson();
        if (personList.size() > 0) {
            selectedPerson = personList.get(0);//By default the first item if exists, otherwise null.
            for (Person person : personList) {
                int random = (new Random()).nextInt(personList.size());

                if ( person.getObjects() != null && person.getObjects().size() == 0) {
                    ArrayList<ModelObject> modelObjects = new ArrayList<ModelObject>();

                    relationPOCService.addArrayBooksToPerson(selectedPerson, modelObjects);
                }
            }
        } else {
            Toast.makeText(getActivity().getApplicationContext(), "No selected person", Toast.LENGTH_LONG).show();
        }
        objectList = relationPOCService.getAllObjects();
        //start adapter
        objectResultAdapter = new ObjectResultAdapter(getActivity(), objectList);
        ((ListView) resultView.findViewById(R.id.resultListView)).setAdapter(objectResultAdapter);
        return resultView;
    }

    private void initView() {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        (resultView.findViewById(R.id.getAllButton)).setOnClickListener(this);
        (resultView.findViewById(R.id.searchByCriteria)).setOnClickListener(this);

        (resultView.findViewById(R.id.resultListView)).setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.getAllButton:
                objectList = relationPOCService.getAllObjects();
                break;

            case R.id.searchByCriteria:
                try {
                    objectList = relationPOCService.getObjectsByCriteria(
                            ((EditText) resultView.findViewById(R.id.usernameQueryText)).getText().toString(),
                            ((EditText) resultView.findViewById(R.id.bookQueryText)).getText().toString(),
                            ((EditText) resultView.findViewById(R.id.priceQueryText)).getText().toString());
                } catch (NumberFormatException e) {
                    Toast.makeText(getActivity(), "Not valid Number", Toast.LENGTH_LONG).show();
                }
                break;

        }
        objectResultAdapter.reload(objectList);
    }
}
