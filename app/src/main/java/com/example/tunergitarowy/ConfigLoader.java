package com.example.tunergitarowy;

import java.io.File;
import java.lang.String;
import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;



public class ConfigLoader {
    public static void loadConfig() throws Exception

    {
        File directory = context.getFilesDir();
        File config = new File(directory, "config.json");

        if (config.exists()) {
            InputStream inputStream = getResources().openRawResource(R.raw.config);
            JSONObject json = new JSONObject("json");
            String content = json.getString(String.valueOf(inputStream));
            System.out.println(content);
        }
    }
}
