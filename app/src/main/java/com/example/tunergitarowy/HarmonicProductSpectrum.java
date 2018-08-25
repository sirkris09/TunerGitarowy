package com.example.tunergitarowy;


import android.util.Log;

public final class HarmonicProductSpectrum {
    private static final int window_size = 32768;
    private int maxIndex;
    private FFT fft;

    public HarmonicProductSpectrum(){
        this.fft = new FFT(window_size);
    }

    public double CalculateHPS(short[] data) {
        short[] samples;
        samples = data;
        short[] signal_out = new short[window_size];
        double[] spectrum = new double[window_size];
        double[] hps = new double[window_size];
        Utils.HanningWindow(samples, signal_out, 0, window_size);
        double[] doubles = Utils.copyFromShortArray(signal_out);
        fft.fft(doubles, spectrum);

        for (int i = 0; i < spectrum.length; i++) {
            doubles[i] = Math.abs(spectrum[i]);
        }
        Utils.calcHarmonicProductSpectrum(doubles, hps, 1);
        int maxIndex = 0;
        for (int i = 1; i < hps.length; i++) {
            if (hps[maxIndex] < hps[i])
                maxIndex = i;
        }

        double freq = ((double) maxIndex / (double) samples.length) * 44100;


        return freq;
    }
}
