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

    private float[] ranges = new float [] {329.63f,246.94f,196.00f,146.83f,110.00f,82.41f};

    private int pitchIndex = 0;

    private Paint mTextPaint;
    private Paint mCircleGoodPaint;
    private Paint mCircleBadPaint;


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
        mTextPaint.setTextSize(50);
        mCircleBadPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCircleBadPaint.setColor(ContextCompat.getColor(context, R.color.colorRed));
        mCircleGoodPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCircleGoodPaint.setColor(ContextCompat.getColor(context, R.color.colorGreen));

        Log.e(LOG_TAG, "TunerView class initialized!");
    }


    @Override
    protected void onDraw(Canvas canvas){
        // TODO: Rysowanie interfejsu
        super.onDraw(canvas);

        float range = Utils.pitchIndexToFrequency(pitchIndex);

        canvas.drawText(String.format("Targeted range: %f ", range), 20,100, this.mTextPaint);
        canvas.drawText(String.format("HZ: %f", freq), 20, 220, this.mTextPaint);
        if (freq  > range ){
            canvas.drawText(String.format("Frequency too high"), 20,450, this.mTextPaint);
        }
        else {
            canvas.drawText(String.format("Frequency too low"), 20, 450, this.mTextPaint);
        }
            if (freq  < range * 1.05 && freq > range * 0.95 ){
            canvas.drawCircle(40, 320, 25, this.mCircleGoodPaint);
        }else{
            canvas.drawCircle(40, 320, 25, this.mCircleBadPaint);
        }

    }

    public void setPitchIndex(int pitchIndex){
        this.pitchIndex = pitchIndex;
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
