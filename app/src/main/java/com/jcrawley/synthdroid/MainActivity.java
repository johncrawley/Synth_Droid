package com.jcrawley.synthdroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.SeekBar;

import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private final ScheduledExecutorService tremoloExecutorService = Executors.newSingleThreadScheduledExecutor();
    private int tremoloRateCounter = 100;
    private int initialTremoloRateCounter = 100;
    private ScheduledFuture<?> tremoloFuture;
    private boolean isTremoloEnabled;


    static {
        System.loadLibrary("native-lib");
    }

    private native void touchEvent(int action);


    private native void setFrequency(int freq);

    private native void startEngine();

    private native void stopEngine();


    private native void enableTremolo(boolean isEnabled);


    private native void setTremoloRate(int rate);

    private native void updateTremoloAmplitude();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startEngine();
        setupViews();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        if(action == MotionEvent.ACTION_DOWN){
            startTremolo();
        }
        else if(action == MotionEvent.ACTION_MOVE){
            startTremolo();
            int y = (int)event.getY();
            int extraHz = 240 + (2000 - y);
            setFrequency(extraHz);

        }
        else if(action == MotionEvent.ACTION_UP){
            stopTremolo();
        }
       touchEvent(event.getAction());
        return super.onTouchEvent(event);
    }

    @Override
    public void onDestroy() {
       stopEngine();
        super.onDestroy();
    }

    private void setupViews(){
        SwitchMaterial enableTremoloSwitch = findViewById(R.id.enableTremoloSwitch);
        enableTremoloSwitch.setOnCheckedChangeListener((view, isChecked) ->{
           // enableTremolo(isChecked);
            //enableTrem(isChecked);
            isTremoloEnabled = isChecked;
        });
        setupTremoloRateSeekBar();
    }


    private void setupTremoloRateSeekBar(){
        SeekBar seekBar = findViewById(R.id.tremoloRateSeekBar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                //setTremoloRate(i);
                setTremoloCounter(i);
            }
            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }



    private void setTremoloCounter(int value){
        final int tremoloMaxRate = 101;
        initialTremoloRateCounter = tremoloMaxRate - value;
    }


    private void startTremolo(){
        if(isTremoloEnabled){
            tremoloFuture = tremoloExecutorService.scheduleAtFixedRate(this::adjustTremoloValue, 1, 10, TimeUnit.MILLISECONDS);
        }
    }


    private void stopTremolo(){
        System.out.println("Entered disableTrem!");
        if(tremoloFuture == null || tremoloFuture.isCancelled()) {
            return;
        }
        tremoloFuture.cancel(true);
    }


    private void adjustTremoloValue(){
        tremoloRateCounter--;
        if(tremoloRateCounter <= 0){
            tremoloRateCounter = initialTremoloRateCounter;
           // updateTremoloAmplitude();
            System.out.println("updated tremolo amplitude! initialTremRate: " + initialTremoloRateCounter + " currentCount: " + tremoloRateCounter + " isfuture cancelled? : " + tremoloFuture.isCancelled());
        }
    }

    private void adjustTemp(){
        System.out.println("adjustTemp() is future cancelled ? " + tremoloFuture.isCancelled());
    }
}

