package activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.novedia.realmpoc.R;
import com.example.novedia.realmpoc.model.Person;
import com.example.novedia.realmpoc.services.RealmPOCService;

/**
 * Created by patiencebayogha on 06/05/15.
 * Class for add a new person in List /DB
 * Creation of form with name and age of the new person to add in database
 */
public class InsertNewPersonInDBActivity extends Activity {

    private RealmPOCService realmPOCService;
    private EditText mName;
    private EditText mAge;
    private Button send;
    Person person;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_insert_new_person_in_db);

        send = (Button) findViewById(R.id.activity_insert_new_person_button_send);
        mName = (EditText) findViewById(R.id.edit_name);
        mAge = (EditText) findViewById(R.id.edit_age);

        //Get DB instance
        realmPOCService = RealmPOCService.getInstance(this);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String myName = mName.getText().toString();
                String ageString = mAge.getText().toString();

                if (!ageString.isEmpty()) {
                    try {
                        final int myAge = Integer.parseInt(ageString);
                        person = new Person();
                        person.setName(myName);
                        person.setAge(myAge);
                        Toast.makeText(getApplicationContext(), "you added a new person in database", Toast.LENGTH_LONG).show();
                        person = realmPOCService.createPerson(person);
                        finish();
                    } catch (Exception e) {
                        Log.e(getClass().getCanonicalName(), "error format");
                    }
                }

            }
        });
    }

}



