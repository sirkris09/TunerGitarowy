package com.example.tunergitarowy;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;
import android.util.AttributeSet;
import android.util.Log;
import java.util.List;
import com.example.tunergitarowy.Utils;

import java.util.ArrayList;
import java.util.Collections;

import java.util.Arrays;

public class TunerView extends View {
    private static final int window_size=32768;

    private static final String LOG_TAG = RecordingThread.class.getSimpleName();

    private short[] samples;
    private FFT fft;


    public TunerView(Context context) {
        super(context);
        init();
    }

    public TunerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    public TunerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public void init(){
        fft = new FFT(window_size);
        samples = new short[window_size];
        Log.e(LOG_TAG, "TunerView class initialized!");
    }


    @Override
    protected void onDraw(Canvas canvas){
        // TODO: Rysowanie interfejsu
    }

    private void onSampleChange(){
        // TODO: Przetwarzanie probek
        short[] signal_out = new short[window_size];
        double[] spectrum = new double[window_size];
        double[] hps = new double[window_size];
        Utils.HanningWindow(samples, signal_out, 0, window_size);
        double[] doubles = Utils.copyFromShortArray(signal_out);
        fft.fft(doubles, spectrum);
        Utils.calcHarmonicProductSpectrum(spectrum, hps, 0);
        double max = Utils.max(hps);
        Log.i(LOG_TAG, String.format("Max HPS: %f", max));
        postInvalidate();
    }

    public void transferSamples(short[] data){
        if (data.length != window_size){
            Log.e(LOG_TAG, "Incoming data doesn't match the window size");
            Log.e(LOG_TAG, String.format("data length: %d, window_size: %d", data.length, window_size));
            return;
        }
        System.arraycopy(data, 0, samples, 0, window_size);
        onSampleChange();
    }
}
