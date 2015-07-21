package com.example.novedia.realmpoc.fragments;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.novedia.realmpoc.R;
import com.example.novedia.realmpoc.SwipeDeleteActivity.SwipedeleteActivity;
import com.example.novedia.realmpoc.adapters.PersonResultAdapter;
import com.example.novedia.realmpoc.model.Person;
import com.example.novedia.realmpoc.services.RealmPOCService;

import java.io.Serializable;
import java.util.ArrayList;

import activities.InsertNewPersonInDBActivity;

/**
 * Fragment Acttivity for RealmPoc
 * modified by P.BAYOGHA on 5-7/05/2015.
 * 11/05/2015
 * 12/05/15
 * 18/05
 * Permit to show all books in database (titles and prices)
 */

public class DataBasePersonsFragment extends SwipedeleteActivity implements View.OnClickListener, AdapterView.OnItemClickListener, Serializable {


    public static final String TAG = DataBasePersonsFragment.class.getCanonicalName();
    private RealmPOCService realmPOCService;
    private PersonResultAdapter personResultAdapter;
    private ArrayList<Person> personList;
    private Person selectedPerson = null;
    ListView listView;
    Button addPerson;
    Button deleteList;
    Person value;
    View resultView;


    public DataBasePersonsFragment() {
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

        resultView = inflater.inflate(R.layout.fragment_data_base_person, container, false);


        initView();
        //Get DB instance
        realmPOCService = RealmPOCService.getInstance(getActivity());
        //Load all Elements of the list
        personList = realmPOCService.getAllPerson();
        updateSelectedPerson();
        personResultAdapter = new PersonResultAdapter(getActivity(), personList);

        listView = (ListView) resultView.findViewById(R.id.resultListView);
        listView.setOnItemClickListener(this);
        listView.setItemsCanFocus(false);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setAdapter(personResultAdapter);
        listView.setItemChecked(0, true);

        deleteList = (Button) resultView.findViewById(R.id.deleteAllButton);
        deleteList.setOnClickListener(this);


        addPerson = (Button) resultView.findViewById(R.id.insertPerson);
        addPerson.setOnClickListener(this);


        return resultView;
    }

    /**
     * Update the label on view and select the person. By default the first item if exists, otherwise null.
     */
    private void updateSelectedPerson() {
        if (personList.size() > 0) {
            selectedPerson = personList.get(0);
        } else {
            selectedPerson = null;
            Toast.makeText(getActivity().getApplicationContext(), "No person in DataBase", Toast.LENGTH_LONG).show();

        }
    }

    /**
     * Innit view components and set onClick Listeners
     */
    private void initView() {


        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        (resultView.findViewById(R.id.getAllButton)).setOnClickListener(this);
    }
    //Truncate the parent's scroll view

    @Override
    public void onClick(View view) {

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

            case R.id.insertPerson:
                /**modification 7/05/2015
                 * Insert new person in database
                 */
                Log.i(getClass().getCanonicalName(), "Insert the person in DB");
                Intent newPerson = new Intent(getActivity(), InsertNewPersonInDBActivity.class);
                startActivityForResult(newPerson, 1);
                break;

            case R.id.deleteAllButton:
                /**
                 * Delete all DataBase
                 **/
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        getActivity());
                // set title
                alertDialogBuilder.setTitle("would you like to delete the entire database?");
                // set dialog message
                alertDialogBuilder
                        .setMessage("Click yes to delete")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Log.i(getClass().getCanonicalName(), "Delete all people in DB");

                                if (personList.size() != 0) {
                                    realmPOCService.deleteAllPerson();
                                }


                                if (personList.size() == 0) {
                                    Toast.makeText(getActivity(), "No elements in DB", Toast.LENGTH_LONG).show();
                                }
                                personResultAdapter.notifyDataSetChanged();
                                personList = realmPOCService.getAllPerson();
                                personResultAdapter.reload(personList);
                            }

                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // if this button is clicked, just close
                                // the dialog box and do nothing
                                dialog.cancel();
                            }
                        });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();
                // show it
                alertDialog.show();
                break;

        }


    }

    // @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        /**
         *When you selected a person, you can get position and show text if deleted
         */
        view.setSelected(true);

        if (view.isSelected() == true) {
            value = (Person) personResultAdapter.getItem(position);
            selectedPerson = value;
            Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.animation_move);
            view.startAnimation(hyperspaceJumpAnimation);
            Toast.makeText(getActivity().getBaseContext(), value.getName().toString(), Toast.LENGTH_SHORT).show();
        }

        personResultAdapter.notifyDataSetChanged();
    }


    public ListView getListView() {
        return listView;
    }

    /**
     * Swipe delete Item
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void getSwipeItem(boolean isRight, int position) {
        Toast.makeText(getActivity(),
                "Swipe delete to " + (isRight ? "right" : "left") + " direction",
                Toast.LENGTH_SHORT).show();
        AlertDialog.Builder adb = new AlertDialog.Builder(
                getActivity());
        adb.setTitle("Are you sure want to delete this item");
        adb.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        if (selectedPerson != null) {

                            realmPOCService.delete(selectedPerson);

                        }
                        personResultAdapter.notifyDataSetChanged();
                        personList = realmPOCService.getAllPerson();
                        personResultAdapter.reload(personList);
                    }

                });
        adb.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        // TODO Auto-generated method stub
                        dialog.dismiss();

                    }
                });
        adb.show();

    }

    @Override
    public void onItemClickListener(ListAdapter adapter, int position) {

    }


}
