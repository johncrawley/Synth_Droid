package com.jcrawley.synthdroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.ViewTreeObserver;
import android.widget.SeekBar;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.jcrawley.synthdroid.fx.DecayHelper;
import com.jcrawley.synthdroid.fx.chorus.ChorusRunner;
import com.jcrawley.synthdroid.fx.tremolo.TremoloRunner;
import com.jcrawley.synthdroid.view.NoteItemManager;
import com.jcrawley.synthdroid.view.TransparentView;


public class MainActivity extends AppCompatActivity {

    private TremoloRunner tremoloRunner;
    private MainViewModel viewModel;
    private final FrequencyHelper frequencyHelper = new FrequencyHelper();
    private ChorusRunner chorusRunner;
    private DecayHelper decayHelper;
    private final int chorusRate = 100;
    private TransparentView inputView;
    private NoteItemManager noteItemManager;



    static {
        System.loadLibrary("native-lib");
    }

    private native void touchEvent(int action);

    public native void setFrequency(float freq);

    public native void setChorusFrequency(float frequency);

    private native void startEngine();

    private native void stopEngine();

    public native void setAmplitude(float amplitude);

    public native void enableChorus(boolean isEnabled);

    public native void setToneOn(boolean isToneOn);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupViewModel();
        startEngine();
        setupViews();
        decayHelper = new DecayHelper(this);
        tremoloRunner = new TremoloRunner(this);
        chorusRunner = new ChorusRunner(this);
    }


    @Override
    protected void onStart() {
        super.onStart();

    }


    private void setupViewModel(){
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        if(viewModel.tremoloRate < 0){
            viewModel.tremoloRate = getResources().getInteger(R.integer.tremolo_rate_seekbar_default);
        }
    }


    public TremoloRunner getTremoloRunner(){
        return tremoloRunner;
    }


    private void setupKeyInputs(){
        noteItemManager = new NoteItemManager(this, inputView);
        inputView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                inputView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                noteItemManager.addNotes(24);
                inputView.invalidate();
            }
        });
    }


    @SuppressLint("ClickableViewAccessibility")
    public void setupInputView() {
        inputView = findViewById(R.id.inputView);
        setupKeyInputs();

        inputView.setOnTouchListener((view, motionEvent) -> {
            int action = motionEvent.getAction();
           // log("*********************");
            for (int i = 0; i < motionEvent.getPointerCount(); i++) {
                float x = motionEvent.getX(i);
                float y = motionEvent.getY(i);
              //  log("pointer x,y for " +  i + " = " + x + "," + y + " event: " + motionEvent.getAction());
            }
            noteItemManager.motion((int)motionEvent.getX(), (int)motionEvent.getY(), action);
            if (action == MotionEvent.ACTION_DOWN) {
                onDown(motionEvent);
            }
            else if (action == MotionEvent.ACTION_MOVE) {
              onMove(motionEvent);
            }
            else if (action == MotionEvent.ACTION_UP) {
                onUp();
            }
            return false;
        });
    }




    private void onDown(MotionEvent motionEvent){
        tremoloRunner.startTremolo(viewModel.tremoloRate);
        chorusRunner.startChorus(chorusRate);
       // assignFrequencyFromMotionEvent(motionEvent);
        decayHelper.onNewNotePressed();
        setToneOn(true);
    }


    private void onMove(MotionEvent motionEvent){
        tremoloRunner.startTremolo(viewModel.tremoloRate);
        chorusRunner.startChorus(chorusRate);
       // assignFrequencyFromMotionEvent(motionEvent);
    }


    private void onUp(){
        tremoloRunner.stopTremolo();
        chorusRunner.stopChorus();
        decayHelper.decayNoteAndStop();
    }


    private void assignFrequencyFromMotionEvent(MotionEvent motionEvent){
        setFrequency(getFrequencyFrom(motionEvent));
    }


    private float getFrequencyFrom(MotionEvent motionEvent){
        int y = (int) motionEvent.getY();
        float baseFrequency = 240 + (2000 - y);
        return  frequencyHelper.getNoteFrequencyFor(baseFrequency);
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
            enableChorus(isChecked);
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

