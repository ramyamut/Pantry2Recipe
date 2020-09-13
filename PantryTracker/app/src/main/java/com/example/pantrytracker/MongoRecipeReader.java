package com.example.pantrytracker;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class MongoRecipeReader {

    private String url;

    static int currentId = 0;


    public MongoRecipeReader() {
        this.url = "http://10.0.2.2:3000";
    }

    public Recipe getRecipe(String instructions) {
        List<Recipe> recipes = getAllRecipes();
        for(Recipe recipe: recipes) {
            if(instructions.equals(recipe.getInstructions())) {
                return recipe;
            }
        }
        return null;
    }


    public List<Recipe> getAllRecipes() {
        String urlStr = this.url + "/getAllRecipes";
        try {
            URL urlObj = new URL(urlStr);
            GetRecipesTask task = new GetRecipesTask();
            task.execute(urlObj);
            List<Recipe> recipes = task.get();
            return recipes;
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    class GetRecipesTask extends AsyncTask<URL, String, List<Recipe>> {

        @Override
        protected List<Recipe> doInBackground(URL... urls) {
            try {
                List<Recipe> recipes = new LinkedList<Recipe>();
                URL url = urls[0];
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                conn.setRequestMethod("GET");
                conn.connect();
                Scanner in = new Scanner(url.openStream());
                while (in.hasNext()) {
                    String line = in.nextLine();
                    JSONArray data = new JSONArray(line);
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject json = data.getJSONObject(i);
                        Recipe recipe = new Recipe(json);
                        recipes.add(recipe);
                    }
                }
                return recipes;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

}
