package FinalProject.COVIDAlert;

import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import static java.lang.Integer.parseInt;

public class PersonInfoActivity extends AppCompatActivity {

    String username;
    String personInfo;
    MongoPersonReader r = new MongoPersonReader();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_info);
        username = getIntent().getStringExtra("username");
        personInfo = getIntent().getStringExtra("personinfo");
        TextView tv = (TextView) findViewById(R.id.personInfo);
        tv.setText(personInfo);
    }

    public void onAlertButtonClick(View v) {
        int id = getIdFromInfo(personInfo);
        boolean success = false;
        if(id != -1) {
            success = r.alert(id);
        }
        if(success) {
            Toast.makeText(getApplicationContext(), "You successfully reported" +
                    " a positive test!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), "There was an error" +
                    " in your request", Toast.LENGTH_LONG).show();
        }
    }

    public void onIndividualButtonClickPos(View v) {
        int id = getIdFromInfo(personInfo);
        boolean success = false;
        if(id != -1) {
            success = r.individualAlertPos(id);
        }
        if(success) {
            Toast.makeText(getApplicationContext(), "You successfully reported" +
                    " a positive test!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), "There was an error" +
                    " in your request", Toast.LENGTH_LONG).show();
        }
    }

    public void onIndividualButtonClickNeg(View v) {
        int id = getIdFromInfo(personInfo);
        boolean success = false;
        if(id != -1) {
            success = r.individualAlertNeg(id);
        }
        if(success) {
            Toast.makeText(getApplicationContext(), "You successfully reported" +
                    " a negative test!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), "There was an error" +
                    " in your request", Toast.LENGTH_LONG).show();
        }
    }

    private int getIdFromInfo(String info) {
        int ind1 = info.indexOf("Id: ");
        info = info.substring(ind1);
        int ind2 = info.indexOf("\n");
        info = info.substring(4, ind2);
        try {
            int ret = Integer.parseInt(info);
            return ret;
        } catch (Exception e) {
            return -1;
        }
    }



}
