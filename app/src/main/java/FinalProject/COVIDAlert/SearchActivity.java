package FinalProject.COVIDAlert;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.LinkedList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    String username;
    String search;
    MongoPersonReader r = new MongoPersonReader();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        username = getIntent().getStringExtra("username");
        search = getIntent().getStringExtra("search");
        List<Person> matches = getMatches(search);
        addButtons(matches);
    }

    private List<Person> getMatches(String keyword) {
        List<Person> ret = new LinkedList<Person>();
        List<Person> allPeople = r.getAllPeople();
        for (Person person: allPeople) {
            String fullName = person.getFirstName() + " " + person.getLastName();
            if (fullName.equalsIgnoreCase(keyword.trim())) {
                ret.add(person);
            }
        }
        return ret;
    }

    //helper method to add Doctor Buttons
    private void addButtons(List<Person> people) {
        RelativeLayout rl = (RelativeLayout) findViewById(R.id.rl);
        ScrollView sv = new ScrollView(this);
        sv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        LinearLayout ll = new LinearLayout(this);
        ll.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        ll.setOrientation(LinearLayout.VERTICAL);
        sv.addView(ll);
        for(int i = 0; i < people.size(); i++) {
            Button button = new Button(this);
            Person p = people.get(i);
            String testPos="";
            if(p.isTestPos()) {
                testPos = "\nTested positive";
            } else {
                testPos = "";
            }
            String buttonStr = p.getFirstName() + " " + p.getLastName()
                        + "\nPhone Number: " + p.getPhoneNum() +
                    "\nRegion: " + p.getRegion() + "\nId: " + p.getId() + testPos;
            button.setText(buttonStr);
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Button b = (Button) v;
                    String bStr = b.getText().toString();
                    /*int ind1 = bStr.indexOf("Id: ");
                    bStr = bStr.substring(ind1);
                    int ind2 = bStr.indexOf("\n");
                    bStr = bStr.substring(4, ind2); */
                    Intent intent = new Intent(SearchActivity.this, PersonInfoActivity.class);
                    intent.putExtra("username", username);
                    intent.putExtra("personinfo", bStr);
                    SearchActivity.this.startActivityForResult(intent, 4);
                }
            });
            button.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            ll.addView(button);
        }
        if (people.size()==0) {
            TextView tv = new TextView(this);
            tv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            tv.setText("Sorry, no people matched your search");
            ll.addView(tv);
        }
        rl.addView(sv);
    }
}
