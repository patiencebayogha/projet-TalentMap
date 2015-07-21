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
import com.example.novedia.realmpoc.adapters.ObjectResultAdapter;
import com.example.novedia.realmpoc.model.ModelObject;
import com.example.novedia.realmpoc.model.Person;
import com.example.novedia.realmpoc.services.RelationPOCService;

import java.util.ArrayList;

import activities.InsertNewBookInDBActivity;

/**
 * modified by patiencebayogha on 06/05/15.
 * Permit to show all persons in database (name and age)
 * 12/05/2015
 * 18/05
 */
public class DataBaseObjectsFragments extends SwipedeleteActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    RelationPOCService relationPOCService;
    ObjectResultAdapter objectResultAdapter;
    ArrayList<ModelObject> objectList;
    Button addBook;
    ListView listView;
    private ModelObject object = null;
    View resultView;
    ModelObject value;
    Button deleteListObject;

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
        resultView = inflater.inflate(R.layout.fragment_data_base_objects, container, false);

        initView();
        //object =objectList.get(0);

        relationPOCService = RelationPOCService.getInstance(getActivity());

        //Get first person of the DB and add him Objects
        ArrayList<Person> personList = relationPOCService.getAllPerson();
        objectList = relationPOCService.getAllObjects();
        //start adapter
        objectResultAdapter = new ObjectResultAdapter(getActivity(), objectList);
        ((ListView) resultView.findViewById(R.id.resultListView)).setAdapter(objectResultAdapter);

        listView = (ListView) resultView.findViewById(R.id.resultListView);
        listView.setOnItemClickListener(this);
        listView.setItemsCanFocus(false);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setAdapter(objectResultAdapter);
        listView.setItemChecked(0, true);

        deleteListObject = (Button) resultView.findViewById(R.id.deleteAllBook);
        deleteListObject.setOnClickListener(this);

        addBook = (Button) resultView.findViewById(R.id.insertBook);
        addBook.setOnClickListener(this);
        return resultView;
    }


    private void initView() {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        (resultView.findViewById(R.id.getAllButton)).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.getAllButton:
                objectList = relationPOCService.getAllObjects();
                if (objectList.size() == 0)
                    Toast.makeText(getActivity(), "No elements in DB", Toast.LENGTH_LONG).show();

                break;

            case R.id.insertBook:
                Log.i(getClass().getCanonicalName(), "Insert the Book in DB");
                Intent newObject = new Intent(getActivity(), InsertNewBookInDBActivity.class);
                startActivityForResult(newObject, 2);
                break;

            case R.id.deleteAllBook:
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

                                if (objectList.size() != 0) {
                                    relationPOCService.deleteAllObject();
                                }


                                if (objectList.size() == 0) {
                                    Toast.makeText(getActivity(), "No elements in DB", Toast.LENGTH_LONG).show();
                                }
                                objectResultAdapter.notifyDataSetChanged();
                                objectList = relationPOCService.getAllObjects();
                                objectResultAdapter.reload(objectList);
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

    public ListView getListView() {
        return listView;
    }

    /**
     * Swipe delete Item
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void getSwipeItem(boolean isRight, int position) {
        Toast.makeText(getActivity(),
                "Swipe to " + (isRight ? "right" : "left") + " direction",
                Toast.LENGTH_SHORT).show();

        getActivity().overridePendingTransition(R.anim.animation_interpolator, R.anim.animation_move);
        AlertDialog.Builder adb = new AlertDialog.Builder(
                getActivity());
        adb.setTitle("Are you sure want to delete this item");
        adb.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        if (object != null) {

                            relationPOCService.delete(object);
                        }
                        objectResultAdapter.notifyDataSetChanged();
                        objectList = relationPOCService.getAllObjects();
                        objectResultAdapter.reload(objectList);

                        Toast.makeText(getActivity().getBaseContext(), "you just delete an element", Toast.LENGTH_SHORT).show();
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


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        /**
         *When you selected a person, you can get position and show text if deleted
         */
        view.setSelected(true);

        if (view.isSelected() == true) {
            value = (ModelObject) objectResultAdapter.getItem(position);
            object = value;
            Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.animation_move);
            view.startAnimation(hyperspaceJumpAnimation);

            Toast.makeText(getActivity().getBaseContext(), value.getName().toString(), Toast.LENGTH_SHORT).show();
        }

        objectResultAdapter.notifyDataSetChanged();
    }


}



