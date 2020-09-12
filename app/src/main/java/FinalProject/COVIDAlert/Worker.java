package FinalProject.COVIDAlert;

import org.json.JSONObject;

public class Worker {
    String firstName;
    String lastName;
    String username;
    int passwordHash;

    public Worker(String fName, String lName, String uName, int pass) {
        this.firstName = fName;
        this.lastName = lName;
        this.username = uName;
        this.passwordHash = pass;
    }

    public Worker(JSONObject json) {
        try {
            this.firstName = json.getString("firstname");
            this.lastName = json.getString("lastname");
            this.username = json.getString("username");
            this.passwordHash = json.getInt("passwordHash");
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

}
