package activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.novedia.realmpoc.R;
import com.example.novedia.realmpoc.model.ModelObject;
import com.example.novedia.realmpoc.services.RelationPOCService;

/**
 * Created by patiencebayogha on 12/05/15.
 * This class permit to insert a book in database : title and price
 */
public class InsertNewBookInDBActivity extends Activity {
    private RelationPOCService relationPOCService;
    private EditText mBook;
    private EditText mPrice;
    private Button validate;
    ModelObject modelObject;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_insert_new_book_in_db);

        validate = (Button) findViewById(R.id.activity_insert_new_object_button);
        mBook = (EditText) findViewById(R.id.edit_book);
        mPrice = (EditText) findViewById(R.id.edit_price);
        //Get DB instance
        relationPOCService = RelationPOCService.getInstance(this);

        validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String myBook = mBook.getText().toString();
                String priceString = mPrice.getText().toString();

                if (!priceString.isEmpty()) {
                    try {
                        final int myAge = Integer.parseInt(priceString);
                        modelObject = new ModelObject();
                        modelObject.setName(myBook);
                        modelObject.setPrice(myAge);
                        Toast.makeText(getApplicationContext(), "you added a new book in database", Toast.LENGTH_LONG).show();
                        modelObject = relationPOCService.createBook(modelObject);
                        finish();
                    } catch (Exception e) {
                        Log.e(getClass().getCanonicalName(), "error format");
                    }
                }

            }
        });
    }

}

