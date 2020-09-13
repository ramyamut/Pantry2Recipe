package com.example.pantrytracker;

import android.os.Bundle;
import android.widget.EditText;
import android.view.View;
import android.widget.Toast;


import java.util.List;
import androidx.appcompat.app.AppCompatActivity;

public class AddInventoryActivity extends AppCompatActivity{

    private String username;
    private Person p;
    private MongoPersonReader r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        r = new MongoPersonReader();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);
        username = getIntent().getStringExtra("username");
    }

    public void addIngredient(View v){
        EditText item = (EditText) findViewById(R.id.item);
        String ingred = item.getText().toString();
        boolean valid = r.addIngredient(username, ingred);
        if (!valid){
            Toast.makeText(v.getContext(), "there was an error in your request", Toast.LENGTH_LONG).show();
        } else {
            finish();
            startActivity(getIntent());
        }
    }
}
