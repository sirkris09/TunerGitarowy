package com.example.tunergitarowy;

import android.util.Log;

public class Utils {
    private static final String LOG_TAG = RecordingThread.class.getSimpleName();

    public void calcHarmonicProductSpectrum(double[] mag, double[] hps, int order) {
        if(mag.length != hps.length) {
            Log.e(LOG_TAG, "calcHarmonicProductSpectrum: mag[] and hps[] have to be of the same length!");
            throw new IllegalArgumentException("mag[] and hps[] have to be of the same length");
        }

        // initialize the hps array
        int hpsLength = mag.length / (order+1);
        for (int i = 0; i < hps.length; i++) {
            if(i < hpsLength)
                hps[i] = mag[i];
            else
                hps[i] = Float.NEGATIVE_INFINITY;
        }

        // do every harmonic in a big loop:
        for (int harmonic = 1; harmonic <= order; harmonic++) {
            int downsamplingFactor = harmonic + 1;
            for (int index = 0; index < hpsLength; index++) {
                // Calculate the average (downsampling):
                float avg = 0;
                for (int i = 0; i < downsamplingFactor; i++) {
                    avg += mag[index*downsamplingFactor + i];
                }
                hps[index] += avg / downsamplingFactor;
            }
        }
    }


    public void HanningWindow(short[] signal_in, short[] signal_out, int pos, int size)
    {
        for (int i = pos; i < pos + size; i++)
        {
            int j = i - pos; // j = index into Hann window function
            signal_out[i] = (short) (signal_in[i] * 0.5 * (1.0 - Math.cos(2.0 * Math.PI * j / size)));
        }
    }
}
