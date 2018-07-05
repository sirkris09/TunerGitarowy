package com.example.tunergitarowy;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.util.AttributeSet;
import android.util.Log;
import android.graphics.Paint;
import android.text.TextPaint;
import android.text.TextUtils;

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
    private double freq;
    private int maxIndex;

    private Paint mTextPaint;


    public TunerView(Context context) {
        super(context);
        init(context);
    }

    public TunerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
    public TunerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public void init(Context context){
        fft = new FFT(window_size);
        samples = new short[window_size];
        mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(ContextCompat.getColor(context, R.color.colorPrimary));
        mTextPaint.setTextSize(100);
        Log.e(LOG_TAG, "TunerView class initialized!");
    }


    @Override
    protected void onDraw(Canvas canvas){
        // TODO: Rysowanie interfejsu
        super.onDraw(canvas);


        canvas.drawText(String.format("maxIndex: %d ", maxIndex), 20,100, this.mTextPaint);
        canvas.drawText(String.format("HZ: %f", freq), 20, 220, this.mTextPaint);


    }

    private void onSampleChange(){
        // DONE!

        short[] signal_out = new short[window_size];
        double[] spectrum = new double[window_size];
        double[] hps = new double[window_size];
        Utils.HanningWindow(samples, signal_out, 0, window_size);
        double[] doubles = Utils.copyFromShortArray(signal_out);
        fft.fft(doubles, spectrum);

        for (int i=0; i < spectrum.length; i++){
            doubles[i] = Math.abs(spectrum[i]);
        }
        Utils.calcHarmonicProductSpectrum(doubles, hps, 1);
        int maxIndex = 0;;
        for (int i = 1; i < hps.length; i++) {
            if(hps[maxIndex] < hps[i])
                maxIndex = i;
        }

        double freq = ((double)maxIndex / (double)samples.length) * 44100;

        Log.i(LOG_TAG, String.format("maxIndex: %d, HZ: %f", maxIndex, freq));
        this.freq = freq;
        this.maxIndex = maxIndex;
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
