package com.example.tunergitarowy;

import android.Manifest;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button recordButton;
    private TextView helloView;
    private static final int REQUEST_RECORD_AUDIO = 13;
    private static final String[] ranges = new String[] {"E","B","G","D","A","E"};
    private Integer choice;

    public static final String EXTRA_INT = "com.example.tunergitarowy.INT";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item,
                ranges);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               choice = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                choice = 0;
            }
        });

        recordButton = this.findViewById(R.id.record);
        recordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTuner(choice);
            }
        });
    }

    private void startTuner(int range_selector){
        Intent intent = new Intent(this, TunerActivity.class);
        intent.putExtra(this.EXTRA_INT, range_selector);
        startActivity(intent);
    }
}