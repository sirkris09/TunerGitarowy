package com.example.tunergitarowy;

import android.app.Application;

import java.util.HashMap;
import java.util.List;

public class TunerApp extends Application{

    // TODO:: zamienic to na List<Profiles>
    private HashMap<String, int[]> profiles;

    public HashMap<String, int[]> getProfiles() {
        return profiles;
    }

    public void setProfiles(HashMap<String, int[]> profiles) {
        this.profiles = profiles;
    }

    //TODO: dodac class Profile -> klasa ktora bedzie przechowywala (String) nazwe i List<Integer> pitch indexy
}
