package com.jcrawley.synthdroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.SeekBar;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.jcrawley.synthdroid.fx.tremolo.TremoloRunner;


public class MainActivity extends AppCompatActivity {

    private TremoloRunner tremoloRunner;

    static {
        System.loadLibrary("native-lib");
    }

    private native void touchEvent(int action);


    private native void setFrequency(float freq);

    private native void startEngine();

    private native void stopEngine();

    public native void setAmplitude(float amplitude);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startEngine();
        setupViews();
        tremoloRunner = new TremoloRunner(this);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        if(action == MotionEvent.ACTION_DOWN){
           tremoloRunner.startTremolo();
        }
        else if(action == MotionEvent.ACTION_MOVE){
            tremoloRunner.startTremolo();
            int y = (int)event.getY();
            int extraHz = 240 + (2000 - y);
            setFrequency(extraHz);

        }
        else if(action == MotionEvent.ACTION_UP){
            tremoloRunner.stopTremolo();
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
            tremoloRunner.setEnabled(isChecked);
        });
        setupTremoloRateSeekBar();
    }


    private void setupTremoloRateSeekBar(){
        SeekBar seekBar = findViewById(R.id.tremoloRateSeekBar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                tremoloRunner.setCounter(i);
            }
            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }





}

