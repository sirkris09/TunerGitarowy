package com.example.tunergitarowy;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.lang.String;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Arrays;

import org.json.JSONArray;
import org.json.JSONObject;

public class ConfigLoader {

    private static final String LOG_TAG = ConfigLoader.class.getSimpleName();

    public static void loadConfig(Context context) throws Exception
    {
        File directory = context.getFilesDir();
        File config = new File(directory, "config.json");

        if (!config.exists()) {
            InputStream inputStream = context.getResources().openRawResource(R.raw.config);
            String jsonString = loadJSONFromAsset(inputStream);
            JSONObject json = new JSONObject(jsonString);

            JSONArray profilesArray = json.getJSONArray("profiles");
            for ( int i = 0; i < profilesArray.length(); i++)
            {
                JSONObject jsonObject = profilesArray.getJSONObject(i);

                String profileName = jsonObject.getString("name");
                Log.i(LOG_TAG, "Profile name: " + profileName);
                JSONArray pithIndexes = jsonObject.getJSONArray("pitchIndexes");

                int[] indexes = new int[pithIndexes.length()];

                for (int j = 0; j < pithIndexes.length(); ++j) {
                    indexes[j] = pithIndexes.optInt(j);
                }
                Log.i(LOG_TAG, "Pitch indexes: " + Arrays.toString(indexes));
            }
            Log.i(LOG_TAG,"Config loaded: " + jsonString);
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
}
