package activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.novedia.realmpoc.R;
import com.example.novedia.realmpoc.model.ModelObject;
import com.example.novedia.realmpoc.model.Person;
import com.example.novedia.realmpoc.services.RelationPOCService;

import java.util.ArrayList;

/**
 * Created by patiencebayogha on 18/05/15.
 * This class permit to insert a book to a person : title and price (add object to person)
 */
public class AddBookToPersonActivity extends Activity {
    private RelationPOCService relationPOCService;
    private EditText mBook;
    private EditText mPrice;
    private Button save;
    ModelObject modelObject;
    public static String POSITION_OF_PEOPLE = "Position";
    ArrayList<ModelObject> objects;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_book_to_person);

        Intent intent=getIntent();
        final int position= intent.getExtras().getInt(POSITION_OF_PEOPLE);
        save = (Button) findViewById(R.id.activity_save_book);
        mBook = (EditText) findViewById(R.id.add_book);
        mPrice = (EditText) findViewById(R.id.add_price);
        //Get DB instance
        relationPOCService = RelationPOCService.getInstance(this);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String myBook = mBook.getText().toString();
                String priceString = mPrice.getText().toString();
                ArrayList<Person> personList = relationPOCService.getAllPerson();
                if (personList.size() > 0) {
                    Person selectedPerson = personList.get(position);
                    Person person = selectedPerson;  //ne pas faire un for car ajoute a toute les personnes
                    if (!priceString.isEmpty()) {
                        try {
                            final int myAge = Integer.parseInt(priceString);
                            modelObject = new ModelObject();
                            modelObject.setName(myBook);
                            modelObject.setPrice(myAge);
                            Toast.makeText(getApplicationContext(), "you added a new book in database", Toast.LENGTH_LONG).show();
                            modelObject = relationPOCService.addBookToPerson(person, modelObject);
                            finish();
                        } catch (Exception e) {
                            Log.e(getClass().getCanonicalName(), "error format");
                        }
                    }
                }

            }
            //}
        });
    }

}
