package com.example.tunergitarowy;

import android.Manifest;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button recordButton;
    private TextView helloView;
    private static final int REQUEST_RECORD_AUDIO = 13;

    public static final String EXTRA_INT = "com.example.tunergitarowy.INT";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recordButton = this.findViewById(R.id.record);
        recordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTuner(0);
            }
        });
    }

    private void startTuner(int range_selector){
        Intent intent = new Intent(this, TunerActivity.class);
        intent.putExtra(this.EXTRA_INT, range_selector);
        startActivity(intent);
    }
}