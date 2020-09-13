package com.example.pantrytracker;
import org.json.JSONObject;
import java.util.*;
import org.json.JSONArray;

public class Person {
    private String firstName;
    private String lastName;
    private String username;
    private int passwordHash;
    private ArrayList<String> inventory;

    public Person(String fName, String lName, String uName, int pass, ArrayList<String> p) {
        this.firstName = fName;
        this.lastName = lName;
        this.username = uName;
        this.passwordHash = pass;
        this.inventory = p;
    }

    public Person(JSONObject json) {
        try {
            this.firstName = json.getString("firstname");
            this.lastName = json.getString("lastname");
            this.username = json.getString("username");
            this.passwordHash = json.getInt("passwordHash");
            this.inventory = new ArrayList<String>();
            JSONArray pantryArray = json.getJSONArray("ingredients");
            for (int i = 0; i<pantryArray.length(); i++){
                String ingredient = pantryArray.getString(i);
                inventory.add(ingredient);
            }
        } catch (Exception e) {
            this.username="";
        }
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getUsername() {
        return this.username;
    }

    public int getPasswordHash() {
        return this.passwordHash;
    }

    public ArrayList<String> getInventory() { return this.inventory; }

}
