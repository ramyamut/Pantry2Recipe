package FinalProject.COVIDAlert;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    MongoWorkerReader r = new MongoWorkerReader();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onResetButtonClick(View v) {
        EditText usernameB = (EditText)findViewById(R.id.username);
        EditText passwordB = (EditText)findViewById(R.id.password);
        usernameB.setText("");
        passwordB.setText("");
    }

    public void onSignUpButtonClick(View v) {
        Intent i = new Intent(this, SignUpActivity.class);
        startActivityForResult(i, 1);
    }

    public void onLoginButtonClick(View v) {
        EditText usernameB = (EditText)findViewById(R.id.username);
        String username = usernameB.getText().toString();
        Worker worker = r.getWorker(username);
        EditText passwordB = (EditText)findViewById(R.id.password);
        int passCode = passwordB.getText().toString().hashCode();
        if(worker==null || passCode != worker.getPasswordHash()) {
            Toast.makeText(getApplicationContext(), "There was an error," +
                    " either your username or password is wrong", Toast.LENGTH_LONG).show();
        } else {
            Intent i = new Intent(this, HomeScreenActivity.class);
            i.putExtra("username", username);
            startActivityForResult(i, 3);
        }
    }
}
