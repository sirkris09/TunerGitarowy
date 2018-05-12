package com.example.tunergitarowy;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;
import android.util.AttributeSet;

public class TunerView extends View {

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
        postInvalidate();
    }

    public void transferSamples(short[] data){
        samples = data;
        onSampleChange();
    }
}
