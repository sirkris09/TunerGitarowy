//package com.example.tunergitarowy;
//
//import android.content.Context;
//import android.graphics.Canvas;
//import android.os.Bundle;
//import android.provider.ContactsContract;
//import android.support.v4.content.ContextCompat;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.Toolbar;
//import android.view.View;
//import android.util.AttributeSet;
//import android.util.Log;
//import android.graphics.Paint;
//import android.text.TextPaint;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.TextView;
//import pl.pawelkleczkowski.customgauge.CustomGauge;
//
//import static com.example.tunergitarowy.Utils.pitchLetterFromIndex;
//
//public class TunerView extends AppCompatActivity {
//    private static final int window_size = 32768;
//
//    private static final String LOG_TAG = RecordingThread.class.getSimpleName();
//
//    private HarmonicProductSpectrum hps;
//    private short[] samples;
//    private double freq;
//    private int pitchIndex = 0;
//
////    private Paint mTextPaint;
////    private Paint mCircleGoodPaint;
////    private Paint mCircleBadPaint;
//
//    public TunerView() {
//        hps = new HarmonicProductSpectrum();
//        samples = new short[window_size];
//    }
//
//    public TunerView(Context context) {
//        init(context);
//    }
//
//    public TunerView(Context context, AttributeSet attrs) {
//
//        init(context);
//    }
//
//    public TunerView(Context context, AttributeSet attrs, int defStyle) {
//
//        init(context);
//    }
//
//    public void init(Context context) {
//        hps = new HarmonicProductSpectrum();
//        samples = new short[window_size];
//        /*mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
//        mTextPaint.setColor(ContextCompat.getColor(context, R.color.colorPrimary));
//        mTextPaint.setTextSize(50);
//        mCircleBadPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        mCircleBadPaint.setColor(ContextCompat.getColor(context, R.color.colorRed));
//        mCircleGoodPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        mCircleGoodPaint.setColor(ContextCompat.getColor(context, R.color.colorGreen));*/
//
//        Log.e(LOG_TAG, "TunerView class initialized!");
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        // TODO: Rysowanie interfejsu
//
//
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_tuner);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
////        CustomGauge gauge1 =  findViewById(R.id.gauge1);
////        TextView gaugeText = (TextView) findViewById(R.id.textView1);
////        TextView infoText1 = (TextView) findViewById(R.id.textView4);
////        TextView infoText2 = (TextView) findViewById(R.id.textView5);
////
////        float range = Utils.pitchIndexToFrequency(pitchIndex);
////        gauge1.setStartValue(0);
////        gauge1.setEndValue((100));
////        ImageView circleIndicator = (ImageView) findViewById(R.id.imageView1);
////
////
////        if (100 * freq > range * 105) {
////            gauge1.setValue(100);
////        } else if (100 * freq < range * 95) {
////            gauge1.setValue(0);
////        } else {
////            gauge1.setValue(50 - (1000 - ((int) ((freq / range) * 1000)))); // YAY!
////        }
////        if (pitchIndex == 0) {
////            gaugeText.setText(Utils.pitchLetterFromIndex(Utils.frequencyToPitchIndex((float) freq)));
////            range = Utils.pitchIndexToFrequency(Utils.frequencyToPitchIndex((float) freq));
////            gauge1.setValue(50 - (1000 - ((int) ((freq / range) * 1000))));
////            infoText1.setText(String.format("Obecna częstotliwość: %f Hz", freq));
////            infoText2.setText(String.format("Częstotliwość docelowa dźwięku: %f Hz", range));
////        }
////        else{
////                gaugeText.setText(pitchLetterFromIndex(pitchIndex));
////            infoText1.setText(String.format("Obecna częstotliwość: %f Hz", freq));
////            infoText2.setText(String.format("Częstotliwość docelowa dźwięku: %f Hz", range));
////
////
////            if (freq < range * 1.05 && freq > range * 0.95) {
////                circleIndicator.setImageResource(R.drawable.green);
////               // canvas.drawCircle(40, 470, 25, this.mCircleGoodPaint);
////            } else {
////                circleIndicator.setImageResource(R.drawable.red);
////                //canvas.drawCircle(40, 470, 25, this.mCircleBadPaint);
////                if (freq > range) {
////                    infoText1.setText(String.format("Za wysoka częstotliwość"));
////                } else {
////                    infoText1.setText(String.format("Za niska częstotliwość"));
////                }
////            }
////
////            }
//    }
//
//    public void setPitchIndex(int pitchIndex) {
//        this.pitchIndex = pitchIndex;
//    }
//
//    private void onSampleChange(short[] data) {
//
//        // TODO przenieść do osobnej klasy
//        // done
//        freq = hps.CalculateHPS(data);
//
//        Log.i(LOG_TAG, String.format("maxIndex: HZ: %f", freq));
//
//    }
//
//    public void transferSamples(short[] data) {
//        if (data.length != window_size) {
//            Log.e(LOG_TAG, "Incoming data doesn't match the window size");
//            Log.e(LOG_TAG, String.format("data length: %d, window_size: %d", data.length, window_size));
//            return;
//        }
//        System.arraycopy(data, 0, samples, 0, window_size);
//        onSampleChange(data);
//    }
//}
