package com.example.tunergitarowy;

import android.content.Context;
import android.os.Bundle;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import pl.pawelkleczkowski.customgauge.CustomGauge;

import static com.example.tunergitarowy.Utils.pitchLetterFromIndex;

public class TunerActivity extends AppCompatActivity {

    private static final int window_size = 32768;
    private static final String LOG_TAG = RecordingThread.class.getSimpleName();
    private RecordingThread recordingThread;
    //private TunerView tunerView;
    private int pitchIndex = 0;
    private HarmonicProductSpectrum hps;
    private short[] samples;
    private double freq;

    private static final int REQUEST_RECORD_AUDIO = 13;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tuner);

//        tunerView = new TunerView();
        hps = new HarmonicProductSpectrum();
        samples = new short[window_size];
        pitchIndex = getIntent().getIntExtra(MainActivity.EXTRA_INT, 0);
        this.setPitchIndex(pitchIndex);
        CustomGauge gauge1 =  findViewById(R.id.gauge1);
        TextView gaugeText = (TextView) findViewById(R.id.textView1);
        TextView infoText1 = (TextView) findViewById(R.id.textView44);
        TextView infoText2 = (TextView) findViewById(R.id.textView55);

        float range = Utils.pitchIndexToFrequency(pitchIndex);
        gauge1.setStartValue(0);
        gauge1.setEndValue((100));
        ImageView circleIndicator = (ImageView) findViewById(R.id.imageView1);


        if (100 * freq > range * 105) {
            gauge1.setValue(100);
        } else if (100 * freq < range * 95) {
            gauge1.setValue(0);
        } else {
            gauge1.setValue(50 - (1000 - ((int) ((freq / range) * 1000)))); // YAY!
        }
        if (pitchIndex == 0) {
            gaugeText.setText(Utils.pitchLetterFromIndex(Utils.frequencyToPitchIndex((float) freq)));
            range = Utils.pitchIndexToFrequency(Utils.frequencyToPitchIndex((float) freq));
            gauge1.setValue(50 - (1000 - ((int) ((freq / range) * 1000))));
            infoText1.setText(Utils.pitchLetterFromIndex(Utils.frequencyToPitchIndex((float) freq)));
            infoText2.setText(String.format("Częstotliwość docelowa dźwięku: %f Hz", range));
        }
        else{
            gaugeText.setText(pitchLetterFromIndex(pitchIndex));
            infoText1.setText(Utils.pitchLetterFromIndex(Utils.frequencyToPitchIndex((float) freq)));
            infoText2.setText(String.format("Częstotliwość docelowa dźwięku: %f Hz", range));


            if (freq < range * 1.05 && freq > range * 0.95) {
                circleIndicator.setImageResource(R.drawable.green);
                // canvas.drawCircle(40, 470, 25, this.mCircleGoodPaint);
            } else {
                circleIndicator.setImageResource(R.drawable.red);
                //canvas.drawCircle(40, 470, 25, this.mCircleBadPaint);
                if (freq > range) {
                    infoText1.setText("Za wysoka częstotliwość");
                } else {
                    infoText1.setText("Za niska częstotliwość");
                }
            }

        }

        recordingThread = new RecordingThread(new AudioDataReceivedListener() {
            @Override
            public void onAudioDataReceived(short[] data) {
                transferSamples(data);
            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();
        startAudioRecordingSafe();
    }

    @Override
    protected void onStop(){
        super.onStop();
        this.recordingThread.stopRecording();
    }

    private void startAudioRecordingSafe() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.RECORD_AUDIO)
                == PackageManager.PERMISSION_GRANTED) {
            recordingThread.startRecording();
        } else {
            requestMicrophonePermission();
        }
    }

    private void requestMicrophonePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.RECORD_AUDIO)) {
            ActivityCompat.requestPermissions(TunerActivity.this, new String[]{
                    android.Manifest.permission.RECORD_AUDIO}, REQUEST_RECORD_AUDIO);
        } else {
            ActivityCompat.requestPermissions(TunerActivity.this, new String[]{
                    android.Manifest.permission.RECORD_AUDIO}, REQUEST_RECORD_AUDIO);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,  String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_RECORD_AUDIO && grantResults.length > 0 &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            recordingThread.stopRecording();
        }
    }

    public void setPitchIndex(int pitchIndex) {
        this.pitchIndex = pitchIndex;
    }
    private void onSampleChange(short[] data) {

        // TODO przenieść do osobnej klasy
        // done
        freq = hps.CalculateHPS(data);

        Log.i(LOG_TAG, String.format("maxIndex: HZ: %f", freq));



    }

    public void transferSamples(short[] data) {
        if (data.length != window_size) {
            Log.e(LOG_TAG, "Incoming data doesn't match the window size");
            Log.e(LOG_TAG, String.format("data length: %d, window_size: %d", data.length, window_size));
            return;
        }
        System.arraycopy(data, 0, samples, 0, window_size);
        onSampleChange(data);
    }

}
