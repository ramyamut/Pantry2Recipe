package com.example.pantrytracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.Iterator;
import java.util.List;

public class RecipeActivity extends AppCompatActivity{
    private MongoPersonReader pr = new MongoPersonReader();
    private MongoRecipeReader rr = new MongoRecipeReader();
    private Person p;
    private List<String> inventory;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        Spinner spinner = (Spinner) findViewById(R.id.cuisine_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.cuisine_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        username = getIntent().getStringExtra("username");
        rr = new MongoRecipeReader();
        p = pr.getPerson(username);
        inventory = p.getInventory();
    }

    public void searchRecipePantry(View v) { //search for maximum # of pantry items used
        Intent i = new Intent(this, RecipeActivity.class);
        i.putExtra("username", username);
        startActivityForResult(i, 4);
        List<Recipe> allRecipes = rr.getAllRecipes();

        Iterator<Recipe> it = allRecipes.iterator();
        Recipe opt = allRecipes.get(0);
        int maxItems = 0;
        while (it.hasNext()) {
            Recipe r = it.next();
            int itemsUsed = r.pantryMatch(inventory);
            if (itemsUsed>maxItems) {
                maxItems = itemsUsed;
                opt = r;
            }
        }
        String link = opt.getInstructions();
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
        startActivity(browserIntent);
    }

    public void searchRecipeIngredients(View v) { //search for maximum % of ingredients in pantry
        Intent i = new Intent(this, RecipeActivity.class);
        i.putExtra("username", username);
        startActivityForResult(i, 4);
        List<Recipe> allRecipes = rr.getAllRecipes();

        Iterator<Recipe> it = allRecipes.iterator();
        Recipe opt = allRecipes.get(0);
        double maxPercent = 0;
        while (it.hasNext()) {
            Recipe r = it.next();
            double percentUsed = r.pantryMatch(inventory);
            if (percentUsed>maxPercent) {
                maxPercent = percentUsed;
                opt = r;
            }
        }
        String link = opt.getInstructions();
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
        startActivity(browserIntent);
    }
}
