package com.example.pantrytracker;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class SignUpActivity extends AppCompatActivity {

    MongoPersonReader r = new MongoPersonReader();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }

    public void onFinishButtonClick(View v) {
        EditText fNameET = (EditText) findViewById(R.id.firstname);
        EditText lNameET = (EditText) findViewById(R.id.lastname);
        EditText uNameET = (EditText) findViewById(R.id.username);
        EditText pWordET = (EditText) findViewById(R.id.password);
        String fName = fNameET.getText().toString();
        String lName = lNameET.getText().toString();
        String uName = uNameET.getText().toString();
        String pWord = pWordET.getText().toString();

        if (uName.equals("") || pWord.equals("") || fName.equals("") || lName.equals("")) {
            TextView et = (TextView) findViewById(R.id.errorText);
            et.setText("Please fill out all the fields!");
            et.setTextColor(Color.RED);
        } else {
            TextView et = (TextView) findViewById(R.id.errorText);
            et.setText("Creating Account");
            et.setTextColor(Color.BLUE);
            int passHash = pWord.hashCode();
            boolean result = r.createPerson(fName, lName, uName, passHash);
            if (result) {
                et = (TextView) findViewById(R.id.errorText);
                //et.setText("true");
                Intent i = new Intent(this, HomeScreenActivity.class);
                i.putExtra("username", uName);
                startActivityForResult(i, 2);
                setResult(RESULT_OK, i);
                finish();
            } else {
                et = (TextView) findViewById(R.id.errorText);
                et.setText("Username Already Taken");
                //et.setText("false");
                et.setTextColor(Color.RED);
            }
        }
    }
}
