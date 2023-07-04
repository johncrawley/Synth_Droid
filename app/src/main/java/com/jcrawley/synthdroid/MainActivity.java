package com.jcrawley.synthdroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.SeekBar;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.jcrawley.synthdroid.fx.tremolo.TremoloRunner;


public class MainActivity extends AppCompatActivity {

    private TremoloRunner tremoloRunner;
    private MainViewModel viewModel;
    private final FrequencyHelper frequencyHelper = new FrequencyHelper();


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
        setupViewModel();
        startEngine();
        setupViews();
        tremoloRunner = new TremoloRunner(this);
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
        inputView.setOnTouchListener((view, motionEvent) -> {
            int action = motionEvent.getAction();
            if (action == MotionEvent.ACTION_DOWN) {
                tremoloRunner.startTremolo(viewModel.tremoloRate);
            } else if (action == MotionEvent.ACTION_MOVE) {
                tremoloRunner.startTremolo(viewModel.tremoloRate);
                int y = (int) motionEvent.getY();
                int extraHz = 240 + (2000 - y);
                float noteFrequency = frequencyHelper.getNoteFrequencyFor((float)extraHz);
                setFrequency(noteFrequency);

            } else if (action == MotionEvent.ACTION_UP) {
                tremoloRunner.stopTremolo();
            }
            touchEvent(motionEvent.getAction());
            return false;
        });
    }


    @Override
    public void onDestroy() {
       stopEngine();
        super.onDestroy();
    }


    private void setupViews(){
        setupTremoloSettings();
        setupInputView();
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

