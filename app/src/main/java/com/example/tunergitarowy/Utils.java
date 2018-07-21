package com.example.tunergitarowy;

import android.util.Log;
import java.util.List;

public class Utils {
    private static final String LOG_TAG = RecordingThread.class.getSimpleName();
    private static final float CONCERT_PITCH = 440.0f;

    public static void calcHarmonicProductSpectrum(double[] mag, double[] hps, int order) {
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


    public static void HanningWindow(short[] signal_in, short[] signal_out, int pos, int size)
    {
        for (int i = pos; i < pos + size; i++)
        {
            int j = i - pos; // j = index into Hann window function
            signal_out[i] = (short) (signal_in[i] * 0.5 * (1.0 - Math.cos(2.0 * Math.PI * j / size)));
        }
    }

    public static double[] copyFromShortArray(short[] source) {
        double[] dest = new double[source.length];
        for(int i=0; i<source.length; i++) {
            dest[i] = source[i];
        }
        return dest;
    }

    /**
     * converts a frequency (float) into an pitch index ( 0 is A0, 1 is A0#, 2 is B0, 3 is C1, ...).
     * This will round the frequency to the closest pitch index.
     *
     * @param frequency		frequency in Hz
     * @return pitch index
     */
    public static int frequencyToPitchIndex(float frequency) {
        float A1 = CONCERT_PITCH / 8;
        return Math.round((float) (12 * Math.log(frequency / A1) / Math.log(2)));
    }

    /**
     * returns the corresponding frequency (in Hz) for a given pitch index
     * @param index			pitch index ( 0 is A0, 1 is A0#, 2 is B0, 3 is C1, ...)
     * @return frequency in Hz
     */
    public static float pitchIndexToFrequency(int index) {
        float A1 = CONCERT_PITCH / 8;
        return (float) (A1 * Math.pow(2, index/12f));
    }
    public static String pitchLetterFromIndex(int index) {
        String letters;
        int octaveNumber = ((index+9) / 12) + 1;
        switch(index%12) {
            case 0:  letters = "a" + octaveNumber; break;
            case 1:  letters = "a" + octaveNumber + "#"; break;
            case 2:  letters = "h" + octaveNumber; break;
            case 3:  letters = "c" + octaveNumber; break;
            case 4:  letters = "c" + octaveNumber + "#"; break;
            case 5:  letters = "d" + octaveNumber; break;
            case 6:  letters = "d" + octaveNumber + "#"; break;
            case 7:  letters = "e" + octaveNumber; break;
            case 8:  letters = "f" + octaveNumber; break;
            case 9:  letters = "f" + octaveNumber + "#"; break;
            case 10: letters = "g" + octaveNumber; break;
            case 11: letters = "g" + octaveNumber + "#"; break;
            default: letters = "err";
        }
        return letters;
    }

}
