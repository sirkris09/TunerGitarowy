package com.example.tunergitarowy;

import android.Manifest;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button recordButton;
    private TextView helloView;
    private static final int REQUEST_RECORD_AUDIO = 13;
    private static final String[] ranges = new String[] {"E4","B3","G3","D3","A2","E2"};
    private Integer choice;

    public static final String EXTRA_INT = "com.example.tunergitarowy.INT";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton imageButton = this.findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open ProfileSelectorActivity
                startProfileSelector();
            }
        });

        ImageButton imageButton2 = this.findViewById(R.id.imageButton2);
        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startProfileEdit();
            }
        });

        ImageButton imageButton3 = this.findViewById(R.id.imageButton3);
        imageButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTuner();
            }
        });
        // TODO ZMIENIĆ POKAZYWANE WARTOŚCI


        // Load config
        try {
            ((TunerApp) this.getApplication()).setProfiles(ConfigLoader.loadConfig(this.getApplicationContext()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        ConfigLoader.saveProfilesToJSON(((TunerApp) this.getApplication()).getProfiles(), this.getApplicationContext());
        Log.i("MainActivity", "Settings check: " + ((TunerApp) this.getApplication()).getProfiles().toString());



    }

    private void startProfileSelector(){
        Intent intent = new Intent(this, ProfileSelector.class);
        startActivity(intent);
    }


    private void startTuner(){
        Intent intent = new Intent(this, TunerActivity.class);
        intent.putExtra(this.EXTRA_INT, 0);
        startActivity(intent);
    }

    private void startProfileEdit(){
        Intent intent = new Intent(this, profileListActivity.class);
        startActivity(intent);
    }

}