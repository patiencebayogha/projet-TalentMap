package activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.novedia.realmpoc.R;
import com.example.novedia.realmpoc.adapters.ObjectResultAdapter;
import com.example.novedia.realmpoc.model.ModelObject;
import com.example.novedia.realmpoc.model.Person;
import com.example.novedia.realmpoc.services.RelationPOCService;

import java.util.ArrayList;

/**
 * Created by patiencebayogha on 19/05/15.
 * This class permit to select a book in database of book and add automatically to a person
 */
public class SelectedBookForPersonActivity extends Activity implements AdapterView.OnItemClickListener {
    ArrayList<ModelObject> objectList;

    private RelationPOCService relationPOCService;
    ListView listView;
    ModelObject modelObject;
    public static String POSITION_OF_PEOPLE = "Position";
    Button save;
    ModelObject objects;
    ObjectResultAdapter objectResultAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_select_book_in_database);


        relationPOCService = RelationPOCService.getInstance(this);
        objectList = relationPOCService.getAllObjects();


        listView = (ListView) findViewById(R.id.resultListView);
        listView.setOnItemClickListener(this);
        listView.setItemsCanFocus(false);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setItemChecked(0, true);

        //start adapter
        objectResultAdapter = new ObjectResultAdapter(this, objectList);
        listView.setAdapter(objectResultAdapter);


        save = (Button) findViewById(R.id.activity_save_book);


        objectList = relationPOCService.getAllObjects();
        //Get DB instance

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

       view.setSelected(true);

        if (view.isSelected() == true) {
            modelObject = (ModelObject) objectResultAdapter.getItem(position);

            Toast.makeText(this.getBaseContext(), modelObject.getName().toString(), Toast.LENGTH_SHORT).show();
        }

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                final int state = intent.getExtras().getInt(POSITION_OF_PEOPLE);

                ArrayList<Person> personList = relationPOCService.getAllPerson();
                if (personList.size() != 0) {
                    Person selectedPerson = personList.get(position);

                    relationPOCService.addBookToPerson(selectedPerson, modelObject);
                    finish();
                }
            }
        });
    }


}
