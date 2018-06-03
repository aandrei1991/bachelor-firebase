package demo.app.chat.firechat;

import java.util.ArrayList;

public class User {

    private String name;
    private String UID;
    private String email;
    private String location;
    private ArrayList<String> requestedLangs;
    private ArrayList<String> spokenLangs;

    public User() {

    }

    User(String name, String UID, String email, String location, ArrayList<String> requestedLangs, ArrayList<String> spokenLangs) {
        this.name = name;
        this.UID = UID;
        this.email = email;
        this.location = location;
        this.requestedLangs = requestedLangs;
        this.spokenLangs = spokenLangs;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public ArrayList<String> getRequestedLangs() {
        return requestedLangs;
    }

    public void setRequestedLangs(ArrayList<String> requestedLangs) {
        this.requestedLangs = requestedLangs;
    }

    public ArrayList<String> getSpokenLangs() {
        return spokenLangs;
    }

    public void setSpokenLangs(ArrayList<String> spokenLangs) {
        this.spokenLangs = spokenLangs;
    }
}
