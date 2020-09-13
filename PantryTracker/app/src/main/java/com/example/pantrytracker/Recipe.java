package com.example.pantrytracker;
import org.json.JSONObject;
import java.util.*;
import org.json.JSONArray;

public class Recipe {
    private String instructions;
    private ArrayList<String> ingredients;
    private String title;
    private String cuisine;

    public Recipe(String instruct, ArrayList<String> ingred, String t, String c) {
        this.instructions = instruct;
        this.ingredients = ingred;
        this.title = t;
        this.cuisine = c;
    }

    public Recipe(JSONObject json) {
        try {
            this.instructions = json.getString("instructions");
            this.cuisine = json.getString("cuisine");
            this.title = json.getString("title");
            this.ingredients = new ArrayList<String>();
            JSONArray ingredientArray = json.getJSONArray("ingredients");
            for (int i = 0; i<ingredientArray.length(); i++){
                String ingredient = ingredientArray.getString(i);
                ingredients.add(ingredient);
            }
        } catch (Exception e) {
        }
    }

    public String getCuisine() {
        return cuisine;
    }

    public String getTitle() {
        return title;
    }

    public String getInstructions() {
        return instructions;
    }

    public ArrayList<String> getIngredients() {
        return ingredients;
    }

    // number of pantry items used
    public int pantryMatch(List<String> inventory) {
        int used = 0;

        Iterator<String> it = ingredients.iterator();
        while (it.hasNext()) {
            if (inventory.contains(it.next())){
                used ++;
            }
        }

        return used;
    }

    // fraction of ingredients from the recipe that are in your pantry
    public double recipeMatch(List<String> inventory) {
        double match = 0; //num of matching ingredients

        Iterator<String> it = ingredients.iterator();
        while (it.hasNext()) {
            if (inventory.contains(it.next())){
                match++;
            }
        }

        double frac = match/(ingredients.size());
        return frac;
    }
}
