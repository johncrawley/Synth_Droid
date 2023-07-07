package com.jcrawley.synthdroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.SeekBar;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.jcrawley.synthdroid.fx.chorus.ChorusRunner;
import com.jcrawley.synthdroid.fx.tremolo.TremoloRunner;


public class MainActivity extends AppCompatActivity {

    private TremoloRunner tremoloRunner;
    private MainViewModel viewModel;
    private final FrequencyHelper frequencyHelper = new FrequencyHelper();
    private ChorusRunner chorusRunner;


    static {
        System.loadLibrary("native-lib");
    }

    private native void touchEvent(int action);


    private native void setFrequency(float freq);

    public native void setChorusFrequency(float frequency);

    private native void startEngine();

    private native void stopEngine();

    public native void setAmplitude(float amplitude);




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupViewModel();
        startEngine();
        setupViews();
        tremoloRunner = new TremoloRunner(this);
        chorusRunner = new ChorusRunner(this);
    }

    private void setupViewModel(){
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        if(viewModel.tremoloRate < 0){
            viewModel.tremoloRate = getResources().getInteger(R.integer.tremolo_rate_seekbar_default);
        }
    }


    @SuppressLint("ClickableViewAccessibility")
    public void setupInputView() {
        View inputView = findViewById(R.id.inputView);
        int chorusRate = 100;

        inputView.setOnTouchListener((view, motionEvent) -> {
            int action = motionEvent.getAction();
            for (int i = 0; i < motionEvent.getPointerCount(); i++) {
                float x = motionEvent.getX(i);
                float y = motionEvent.getY(i);
                log("pointer x,y for " +  i + " = " + x + "," + y + " event: " + motionEvent.getAction());
            }
            if (action == MotionEvent.ACTION_DOWN) {

                tremoloRunner.startTremolo(viewModel.tremoloRate);
                chorusRunner.startChorus(chorusRate);
                log("Down Touch Registered!");
            } else if (action == MotionEvent.ACTION_MOVE) {
                tremoloRunner.startTremolo(viewModel.tremoloRate);
                chorusRunner.startChorus(chorusRate);
                int y = (int) motionEvent.getY();
                int extraHz = 240 + (2000 - y);
                float noteFrequency = frequencyHelper.getNoteFrequencyFor((float)extraHz);
                setFrequency(noteFrequency);

            } else if (action == MotionEvent.ACTION_UP) {
                tremoloRunner.stopTremolo();
                chorusRunner.stopChorus();
            }
            touchEvent(motionEvent.getAction());
            return false;
        });
    }

    private void log(String msg){
        System.out.println("^^^ MainActivity: " + msg);
    }

    @Override
    public void onDestroy() {
       stopEngine();
        super.onDestroy();
    }


    private void setupViews(){
        setupTremoloSettings();
        setupChorusSettings();
        setupInputView();
    }


    private void setupChorusSettings(){
        SwitchMaterial enableChorusSwitch = findViewById(R.id.enableChorusSwitch);
        enableChorusSwitch.setOnCheckedChangeListener((view, isChecked) ->{
            chorusRunner.setEnabled(isChecked);
        });
    }

    private void setupTremoloSettings(){
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
                viewModel.tremoloRate = i;
                tremoloRunner.setRateCounter(i);
            }
            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }





}

