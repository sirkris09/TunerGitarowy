package com.example.tunergitarowy;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;
import android.util.AttributeSet;

public class TunerView extends View {
    private static final int window_size=44100;
    private short[] samples;
    public TunerView(Context context) {
        super(context);
    }

    public TunerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public TunerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    @Override
    protected void onDraw(Canvas canvas){
        // TODO: Rysowanie interfejsu
    }

    private void onSampleChange(){
        // TODO: Przetwarzanie probek
        short[] signal_out;
        double[] spectrum;
        double[] hps;
        Utils.HanningWindow(samples, signal_out, 0, window_size);
        double[] doubles = Arrays.stream(signal_out).asDoubleStream().toArray();
        FFT.fft(doubles, spectrum);
        Utils.calcHarmonicProductSpectrum(spectrum, hps, 0);
        postInvalidate();
    }

    public void transferSamples(short[] data){
        samples = data;
        onSampleChange();
    }
}
