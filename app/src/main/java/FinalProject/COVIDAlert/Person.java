package FinalProject.COVIDAlert;

import org.json.JSONObject;

public class Person {

    int id;
    String firstName;
    String lastName;
    String phoneNum;
    String region;
    boolean testPos;

    public Person(int idNum, String fName, String lName, String phone, String reg, boolean pos) {
        this.id = idNum;
        this.firstName = fName;
        this.lastName = lName;
        this.phoneNum = phone;
        this.region = reg;
        this.testPos = pos;
    }

    public Person(JSONObject json) {
        try {
            this.id = Integer.parseInt(json.getString("id"));
            this.firstName = json.getString("firstname");
            this.lastName = json.getString("lastname");
            this.phoneNum = json.getString("phonenumber");
            this.region = json.getString("region");
            this.testPos = json.getBoolean("testpos");
        } catch(Exception e) {
            this.id = 0;
            e.printStackTrace();
        }
    }

    public int getId() {
        return this.id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getPhoneNum() {
        return this.phoneNum;
    }

    public String getRegion() {
        return this.region;
    }

    public boolean isTestPos() {
        return this.testPos;
    }
}
