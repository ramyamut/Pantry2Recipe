package com.example.pantrytracker;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class HomeScreenActivity extends AppCompatActivity{
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = getIntent().getStringExtra("username");
    }

    public void viewInventory(View v) {
        Intent i = new Intent(this, InventoryActivity.class);
        i.putExtra("username", username);
        startActivityForResult(i, 4);
    }

    public void findRecipe(View v) {
        Intent i = new Intent(this, RecipeActivity.class);
        i.putExtra("username", username);
        startActivityForResult(i, 4);
    }

    public void addInventory(View v) {
        Intent i = new Intent(this, AddInventoryActivity.class);
        i.putExtra("username", username);
        startActivityForResult(i, 4);
    }

}
