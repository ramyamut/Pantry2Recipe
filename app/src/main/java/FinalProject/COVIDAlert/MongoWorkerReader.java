package FinalProject.COVIDAlert;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class MongoWorkerReader {

    private String url;

    public MongoWorkerReader() {
        this.url = "http://10.0.2.2:3000";
    }

    public boolean createWorker(String firstName, String lastName, String username, int pass) {
        String urlStr = this.url + "/createWorker?firstname=" + firstName
                +"&lastname=" + lastName + "&username=" + username + "&passwordHash=" + pass;
        try {
            URL urlObj = new URL(urlStr);
            CreateWorkerTask task = new CreateWorkerTask();
            task.execute(urlObj);
            boolean success = task.get();
            return success;
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    class CreateWorkerTask extends AsyncTask<URL, String, Boolean> {

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

    public Worker getWorker(String username) {
        String urlStr = this.url + "/getWorker?username=" + username;
        try {
            URL urlObj = new URL(urlStr);
            GetWorkerTask task = new GetWorkerTask();
            task.execute(urlObj);
            Worker worker = task.get();
            return worker;
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    class GetWorkerTask extends AsyncTask<URL, String, Worker> {

        @Override
        protected Worker doInBackground(URL... urls) {
            try {
                URL url = urls[0];
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                conn.setRequestMethod("GET");
                conn.connect();
                Scanner in = new Scanner(url.openStream());
                Worker ret=null;
                while (in.hasNext()) {
                    String line = in.nextLine();
                    JSONObject data = new JSONObject(line);
                    ret = new Worker(data);
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
}
