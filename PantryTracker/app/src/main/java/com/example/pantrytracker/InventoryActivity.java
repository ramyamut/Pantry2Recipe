package com.example.pantrytracker;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import java.util.*;

public class InventoryActivity extends AppCompatActivity {

    private String username;
    private MongoPersonReader r = new MongoPersonReader();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);
        username = getIntent().getStringExtra("username");
        List<String> inventory = getInventory();
        addButtons(inventory);
    }

    private List<String> getInventory() {
        Person p = r.getPerson(username);
        return p.getInventory();
    }

    //helper method to add Doctor Buttons
    private void addButtons(List<String> inventory) {
        RelativeLayout rl = (RelativeLayout) findViewById(R.id.rl);
        ScrollView sv = new ScrollView(this);
        sv.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        LinearLayout ll = new LinearLayout(this);
        ll.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        ll.setOrientation(LinearLayout.VERTICAL);
        sv.addView(ll);
        for (int i = 0; i < inventory.size(); i++) {
            LinearLayout newLayout = new LinearLayout(this);
            newLayout.setOrientation(LinearLayout.HORIZONTAL);
            newLayout.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));


            Button item = new Button(this);
            String buttonStr = inventory.get(i);
            item.setText(buttonStr);
            item.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            newLayout.addView(item);

            Button delete = new Button(this);
            delete.setText("X");
            delete.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Button b = (Button) v;
                    boolean valid = r.removeIngredient(username, b.getText().toString());
                    if (!valid) {
                        Toast.makeText(v.getContext(), "There was an error in your request.", Toast.LENGTH_LONG).show();
                    } else {
                        finish();
                        startActivity(getIntent());
                    }


                }
            });
            newLayout.addView(delete);

            ll.addView(newLayout);

        }
        if (inventory.size() == 0) {
            TextView tv = new TextView(this);
            tv.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            tv.setText("You have nothing in your pantry");
            ll.addView(tv);
        }
        rl.addView(sv);
    }
}

