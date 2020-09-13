package com.example.pantrytracker;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class MongoPersonReader {

    private String url;

    public MongoPersonReader() {
        this.url = "http://10.0.2.2:3000";
    }

    public boolean createPerson(String firstName, String lastName, String username, int pass) {
        String urlStr = this.url + "/createPerson?firstname=" + firstName
                +"&lastname=" + lastName + "&username=" + username + "&passwordHash=" + pass;
        try {
            URL urlObj = new URL(urlStr);
            CreatePersonTask task = new CreatePersonTask();
            task.execute(urlObj);
            boolean success = task.get();
            return success;
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    class CreatePersonTask extends AsyncTask<URL, String, Boolean> {

        @Override
        protected Boolean doInBackground(URL... urls) {
            try {
                URL url = urls[0];
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                conn.setRequestMethod("POST");
                conn.connect();
                Scanner in = new Scanner(url.openStream());
                String success = "";
                while (in.hasNext()) {
                    String line = in.nextLine();
                    JSONObject data = new JSONObject(line);
                    success = data.getString("status");
                }
                if(success.equalsIgnoreCase("success")) {
                    return true;
                } else {
                    return false;
                }

            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    class AddIngredientTask extends AsyncTask<URL, String, Boolean> {

        @Override
        protected Boolean doInBackground(URL... urls) {
            try {
                URL url = urls[0];
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                conn.setRequestMethod("POST");
                conn.connect();
                Scanner in = new Scanner(url.openStream());
                String success = "";
                while (in.hasNext()) {
                    String line = in.nextLine();
                    JSONObject data = new JSONObject(line);
                    success = data.getString("status");
                }
                if(success.equalsIgnoreCase("success")) {
                    return true;
                } else {
                    return false;
                }

            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    class RemoveIngredientTask extends AsyncTask<URL, String, Boolean> {

        @Override
        protected Boolean doInBackground(URL... urls) {
            try {
                URL url = urls[0];
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                conn.setRequestMethod("POST");
                conn.connect();
                Scanner in = new Scanner(url.openStream());
                String success = "";
                while (in.hasNext()) {
                    String line = in.nextLine();
                    JSONObject data = new JSONObject(line);
                    success = data.getString("status");
                }
                if(success.equalsIgnoreCase("success")) {
                    return true;
                } else {
                    return false;
                }

            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
    }


    public Person getPerson(String username) {
        String urlStr = this.url + "/getPerson?username=" + username;
        try {
            URL urlObj = new URL(urlStr);
            GetPersonTask task = new GetPersonTask();
            task.execute(urlObj);
            Person person = task.get();
            return person;
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    class GetPersonTask extends AsyncTask<URL, String, Person> {

        @Override
        protected Person doInBackground(URL... urls) {
            try {
                URL url = urls[0];
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                conn.setRequestMethod("GET");
                conn.connect();
                Scanner in = new Scanner(url.openStream());
                Person ret=null;
                while (in.hasNext()) {
                    String line = in.nextLine();
                    JSONObject data = new JSONObject(line);
                    ret = new Person(data);
                }
                if(ret == null || ret.getUsername().equalsIgnoreCase("")) {
                    return null;
                } else {
                    return ret;
                }

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public boolean addIngredient(String username, String ingred){

        String urlStr = this.url + "/addIngredient?username=" + username + "&ingredient=" + ingred;

        try {
            URL urlObj = new URL(urlStr);
            AddIngredientTask task = new AddIngredientTask();
            task.execute(urlObj);
            boolean success = task.get();
            return success;
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean removeIngredient(String username, String ingred){

        String urlStr = this.url + "/addIngredient?username=" + username + "&ingredient=" + ingred;

        try {
            URL urlObj = new URL(urlStr);
            RemoveIngredientTask task = new RemoveIngredientTask();
            task.execute(urlObj);
            boolean success = task.get();
            return success;
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
