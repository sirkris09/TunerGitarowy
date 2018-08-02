package com.example.tunergitarowy;

import android.app.Application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class TunerApp extends Application{

    // TODO:: zamienic to na List<Profiles>
    private ArrayList<Profile> profiles;

    public ArrayList<Profile> getProfiles() {
        return profiles;
    }

    public void setProfiles(ArrayList<Profile> profiles) {
        this.profiles = profiles;
    }

    public ArrayList<String> getProfilesNames() {
        ArrayList<String> profilesNames = new ArrayList<String>();

        for (int i = 0; i<profiles.size(); i++) {
           profilesNames.add(profiles.get(i).getName());
        }
        return profilesNames;
    }

    public Profile findProfile(String name) {
        for (int i = 0; i<profiles.size(); i++) {
            if(profiles.get(i).getName() == name) {
                return profiles.get(i);
            }
        }
        return null;
    }

    //TODO: dodac class Profile -> klasa ktora bedzie przechowywala (String) nazwe i List<Integer> pitch indexy
}
