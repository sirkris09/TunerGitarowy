package com.example.tunergitarowy;

import java.util.ArrayList;

public class Profile {
    private String name;
    private ArrayList<Integer> tones;

    public Profile(String name) {
        this.name = name;
        this.tones = new ArrayList<Integer>();

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Integer> getTones() {
        return tones;
    }

    public void setTones(ArrayList<Integer> tones) {
        this.tones = tones;
    }

    public void addTone(int tone){
        this.tones.add(tone);
    }

    @Override
    public String toString() {
        return this.name;
    }

}
