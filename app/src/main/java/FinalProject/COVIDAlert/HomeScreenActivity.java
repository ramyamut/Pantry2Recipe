package FinalProject.COVIDAlert;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class HomeScreenActivity extends AppCompatActivity {

    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        username = getIntent().getStringExtra("username");
    }

    public void onInitialSearchButtonClick(View v) {
        //TextView tv = (TextView) findViewById(R.id.personName);
        //String name = tv.getText().toString();
        Intent i = new Intent(HomeScreenActivity.this, InitialSearchActivity.class);
        //i.putExtra("search", name);
        i.putExtra("username", username);
        startActivityForResult(i, 4);
    }

    public void onCreateButtonClick(View v) {
        Intent i = new Intent(HomeScreenActivity.this, CreateActivity.class);
        i.putExtra("username", username);
        startActivityForResult(i, 5);
    }
}
