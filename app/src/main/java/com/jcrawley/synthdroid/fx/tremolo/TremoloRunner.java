package com.jcrawley.synthdroid.fx.tremolo;

import com.jcrawley.synthdroid.MainActivity;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TremoloRunner {

    private final ScheduledExecutorService tremoloExecutorService = Executors.newSingleThreadScheduledExecutor();
    private int tremoloRateCounter = 100;
    private int initialTremoloRateCounter = 100;
    private Future<?> tremoloFuture;
    private boolean isTremoloEnabled;
    private final float maxAmplitude = 0.3f;
    private final float minAmplitude = 0.01f;
    private final float amplitudeStep = 0.02f;
    private boolean isTremoloAmplitudeDecreasing = true;
    private float currentAmplitude = 0.3f;
    private boolean isTremoloStarted = false;
    private final MainActivity mainActivity;


    public TremoloRunner(MainActivity mainActivity){
        this.mainActivity = mainActivity;
    }


    public void setRateCounter(int value){
        final int tremoloMaxRate = 101;
        initialTremoloRateCounter = tremoloMaxRate - value;
    }


    public void startTremolo(int initialRate){
        if(isTremoloEnabled && !isTremoloStarted){
            setRateCounter(initialRate);
            isTremoloStarted = true;
            currentAmplitude = 0.3f; // default value
            tremoloFuture = tremoloExecutorService.scheduleAtFixedRate(this::adjustTremoloValue, 0, 1, TimeUnit.MILLISECONDS);
        }
    }


    public void stopTremolo(){
        if(tremoloFuture == null || tremoloFuture.isCancelled()) {
            return;
        }
        tremoloFuture.cancel(true);
        isTremoloStarted = false;
    }


    public void setEnabled(boolean isEnabled){
        isTremoloEnabled = isEnabled;
    }


    private void adjustTremoloValue(){
        tremoloRateCounter-=2;
        if(tremoloRateCounter <= 0){
            updateAmplitude();
            mainActivity.setAmplitude(currentAmplitude);
            tremoloRateCounter = initialTremoloRateCounter;
        }
    }


    void updateAmplitude(){
        if(isTremoloAmplitudeDecreasing){
            reduceAmplitude();
            if(currentAmplitude <= minAmplitude){
                switchTremoloDirection();
            }
            return;
        }
        increaseAmplitude();
        if(currentAmplitude >= maxAmplitude){
            switchTremoloDirection();
        }
    }


    void reduceAmplitude(){
        if(currentAmplitude > minAmplitude){
            currentAmplitude -= amplitudeStep;
        }
    }


    void increaseAmplitude(){
        if(currentAmplitude < maxAmplitude){
            currentAmplitude += amplitudeStep;
        }
    }


    void switchTremoloDirection(){
        isTremoloAmplitudeDecreasing = !isTremoloAmplitudeDecreasing;
    }

}