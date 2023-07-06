package com.jcrawley.synthdroid.fx.chorus;

import com.jcrawley.synthdroid.MainActivity;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ChorusRunner {


    private final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
    private int tremoloRateCounter = 100;
    private int initialTremoloRateCounter = 100;
    private Future<?> future;
    private boolean isChorusEnabled;
    private final float maxValue = 0.3f;
    private final float minFrequency = 0.01f;
    private final float valueStep = 0.02f;
    private boolean isValueDecreasing = true;
    private float currentFrequency = 0.3f;
    private boolean hasChorusStarted = false;
    private final MainActivity mainActivity;

    private float chorusDepth = 10.0f;



    public ChorusRunner(MainActivity mainActivity){
        this.mainActivity = mainActivity;
    }


    public void startChorus(int initialRate){
        if(isChorusEnabled && !hasChorusStarted){
            setRateCounter(initialRate);
            hasChorusStarted = true;
            currentFrequency = 0.3f; // default value
            future = executorService.scheduleAtFixedRate(this::adjustValue, 0, 1, TimeUnit.MILLISECONDS);
        }
    }


    public void setRateCounter(int value){
        final int maxInterval = 101;
        initialTremoloRateCounter = maxInterval - value;
    }


    public void stopChorus(){
        if(future == null || future.isCancelled()) {
            return;
        }
        future.cancel(true);
        hasChorusStarted = false;
    }


    public void setEnabled(boolean isEnabled){
        isChorusEnabled = isEnabled;
    }


    private void adjustValue(){
        tremoloRateCounter-=1;
        if(tremoloRateCounter <= 0){
            updateChorusFrequency();
            mainActivity.setChorusFrequency(currentFrequency);
            tremoloRateCounter = initialTremoloRateCounter;
        }
    }


    void updateChorusFrequency(){
        if(isValueDecreasing){
            reduceValue();
            if(currentFrequency <= minFrequency){
                switchDirection();
            }
            return;
        }
        increaseValue();
        if(currentFrequency >= maxValue){
            switchDirection();
        }
    }


    void reduceValue(){
        if(currentFrequency > minFrequency){
            currentFrequency -= valueStep;
        }
    }


    void increaseValue(){
        if(currentFrequency < maxValue){
            currentFrequency += valueStep;
        }
    }


    void switchDirection(){
        isValueDecreasing = !isValueDecreasing;
    }

}
