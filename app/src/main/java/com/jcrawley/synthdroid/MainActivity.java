package com.jcrawley.synthdroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;

public class MainActivity extends AppCompatActivity {


    static {
        System.loadLibrary("native-lib");
    }

    private native void touchEvent(int action);


    private native void setFrequency(int freq);

    private native void startEngine();

    private native void stopEngine();


    private native void enableTremolo(boolean isEnabled);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startEngine();
        enableTremolo(true);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_MOVE){
            int y = (int)event.getY();
            int extraHz = 240 + (2000 - y);
            setFrequency(extraHz);

        }
       touchEvent(event.getAction());
        return super.onTouchEvent(event);
    }

    @Override
    public void onDestroy() {
       stopEngine();
        super.onDestroy();
    }
}
