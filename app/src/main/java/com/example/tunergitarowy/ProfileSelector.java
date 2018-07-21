package com.example.tunergitarowy;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ProfileSelector extends AppCompatActivity {

    private String currProfileName;
    private int[] currProfilePitchIndexes;
    private int currSelectedPitchIndex;

    public static final String EXTRA_INT = "com.example.tunergitarowy.INT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_selector);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Set<String> profileNames = ((TunerApp) this.getApplication()).getProfiles().keySet();

        final Spinner profileSpinner = (Spinner) findViewById(R.id.spinner);
        final Spinner stringSpinner = (Spinner) findViewById(R.id.spinner);
        String[] arrayProfileNames = profileNames.toArray(new String[profileNames.size()]);

        final ArrayAdapter<String> stringArrayAdapter;
        stringArrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item,
                new String[]{"---"});

        final ArrayAdapter<String> profileArrayAdapter;
        profileArrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item,
                arrayProfileNames);
        profileSpinner.setAdapter(profileArrayAdapter);
        profileSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loadProfile(profileSpinner.getSelectedItem().toString());

                List<String> spinnerSelectorStrings = new ArrayList<String>();
                for (int s : currProfilePitchIndexes){
                   spinnerSelectorStrings.add(new String(Utils.pitchLetterFromIndex(s)));
                }

                String[] stringSpinnerOptions = spinnerSelectorStrings.toArray(new String[spinnerSelectorStrings.size()]);

                stringArrayAdapter.clear();
                stringArrayAdapter.addAll(stringSpinnerOptions);
                stringSpinner.setAdapter(stringArrayAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                stringArrayAdapter.clear();
                stringArrayAdapter.addAll(new String[] {"---"});
                stringSpinner.setAdapter(stringArrayAdapter);
            }
        });


        stringSpinner.setAdapter(stringArrayAdapter);
        stringSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currSelectedPitchIndex = currProfilePitchIndexes[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                currSelectedPitchIndex = -1;
            }
        });

        Button tunerButton = this.findViewById(R.id.tunerButton);

        tunerButton = this.findViewById(R.id.record);
        tunerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTuner(currSelectedPitchIndex);
            }
        });

    }

    protected void loadProfile (String name){
        this.currProfilePitchIndexes = ((TunerApp) this.getApplication()).getProfiles().get(name);
        this.currProfileName = name;
    }

    private void startTuner(int pitchIndex){
        Intent intent = new Intent(this, TunerActivity.class);
        intent.putExtra(this.EXTRA_INT, pitchIndex);
        startActivity(intent);
    }

}
