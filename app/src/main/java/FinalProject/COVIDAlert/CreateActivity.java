package FinalProject.COVIDAlert;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CreateActivity extends AppCompatActivity {

    String username;
    MongoPersonReader pr = new MongoPersonReader();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        username = getIntent().getStringExtra("username");
        Spinner spinner = (Spinner) findViewById(R.id.test_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.result_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    public void onFinishButtonClick(View v) {
        EditText fNameET = (EditText) findViewById(R.id.person_firstname);
        EditText lNameET = (EditText) findViewById(R.id.person_lastname);
        EditText phoneET = (EditText) findViewById(R.id.cell_number);
        EditText regionET = (EditText) findViewById(R.id.region);
        Spinner spinner = (Spinner) findViewById(R.id.test_spinner);
        String firstName = fNameET.getText().toString();
        String lastName = lNameET.getText().toString();
        String phoneNum = phoneET.getText().toString();
        String region = regionET.getText().toString();
        String spinnerVal = spinner.getSelectedItem().toString();
        boolean testPos = false;
        if(spinnerVal.equalsIgnoreCase("tested positive")) {
            testPos = true;
        }
        boolean success = pr.createPerson(firstName, lastName, phoneNum, region, testPos);
        TextView errorET = (TextView) findViewById(R.id.errorText2);
        if(success) {
            errorET.setText("You have successfully created a patient!");
        } else {
            errorET.setText("There was an error in your request!");
        }
    }
}
