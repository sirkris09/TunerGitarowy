package com.example.tunergitarowy;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.String;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ConfigLoader {

    private static final String LOG_TAG = ConfigLoader.class.getSimpleName();

    public static ArrayList<Profile> loadConfig(Context context) throws Exception
    {
        File directory = context.getFilesDir();
        File config = new File(directory, "config.json");

        if (!config.exists()) {
            InputStream inputStream = context.getResources().openRawResource(R.raw.config);
            OutputStream outputStream = new FileOutputStream(config);
            copyConfigFromResource(inputStream,outputStream);
            inputStream.close();
            outputStream.close();
        }

        InputStream configInputStream = new FileInputStream(config);
        String jsonString = loadJSONFromAsset(configInputStream);
        return loadProfilesFromJSON(jsonString);
    }

    private static ArrayList<Profile> loadProfilesFromJSON(String jsonString){
        try {
            JSONObject json = new JSONObject(jsonString);

            ArrayList<Profile> profiles = new ArrayList<Profile>();

            JSONArray profilesArray = json.getJSONArray("profiles");
            for (int i = 0; i < profilesArray.length(); i++) {
                JSONObject jsonObject = profilesArray.getJSONObject(i);

                String profileName = jsonObject.getString("name");
                Log.i(LOG_TAG, "Profile name: " + profileName);
                JSONArray pithIndexes = jsonObject.getJSONArray("pitchIndexes");
                Profile profile = new Profile(profileName);
                int[] indexes = new int[pithIndexes.length()];

                for (int j = 0; j < pithIndexes.length(); ++j) {
                    indexes[j] = pithIndexes.optInt(j);
                    profile.addTone(indexes[j]);
                }
                Log.i(LOG_TAG, "Pitch indexes: " + Arrays.toString(indexes));
                profiles.add(profile);
            }
            Log.i(LOG_TAG, "Config loaded: " + jsonString);
            return profiles;
        } catch (JSONException je){
            je.printStackTrace();
            return null;
        }
    }

    private static void copyConfigFromResource(InputStream in, OutputStream out){
        try {
            int size = in.available();
            byte[] buffer = new byte[size]; // or other buffer size
            int read;

            in.read(buffer);
            out.write(buffer);

            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private static String loadJSONFromAsset(InputStream is) {
        String json = null;
        try {
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    private static void saveProfilesToJSON(){
        //TODO: todooo toodooo tooodooo tooodoo todoooooooooooooooooooo
        //TODO: https://www.youtube.com/watch?v=9OPc7MRm4Y8 WFT :D
    }
}
