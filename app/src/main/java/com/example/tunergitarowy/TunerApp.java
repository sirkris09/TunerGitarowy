package com.example.tunergitarowy;

import android.app.Application;

import java.util.HashMap;
import java.util.List;

public class TunerApp extends Application{

    private HashMap<String, int[]> profiles;

    public HashMap<String, int[]> getProfiles() {
        return profiles;
    }

    public void setProfiles(HashMap<String, int[]> profiles) {
        this.profiles = profiles;
    }
}
