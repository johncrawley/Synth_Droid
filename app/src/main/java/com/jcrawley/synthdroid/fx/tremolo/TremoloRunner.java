package com.jcrawley.synthdroid.fx.tremolo;

import com.jcrawley.synthdroid.MainActivity;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TremoloRunner {

    private final ScheduledExecutorService tremoloExecutorService = Executors.newSingleThreadScheduledExecutor();
    private int tremoloRateCounter = 100;
    private int initialTremoloRateCounter = 10;
    private Future<?> tremoloFuture;
    private boolean isTremoloEnabled;
    private final float maxAmplitude = 0.3f;
    private final float minAmplitude = 0.005f;
    private final float amplitudeStep = 0.001f;
    private boolean isTremoloAmplitudeDecreasing = true;
    private float initialAmplitude = 0.3f;
    private float currentAmplitude = 0.3f;
    private boolean isTremoloStarted = false;
    private final MainActivity mainActivity;


    public TremoloRunner(MainActivity mainActivity){
        this.mainActivity = mainActivity;
    }


    public void setRateCounter(int value){
        final int tremoloMaxInterval = 101;
        initialTremoloRateCounter = (tremoloMaxInterval - value) /8;
    }


    public void startTremolo(int initialRate){
        if(isTremoloEnabled && !isTremoloStarted){
            setRateCounter(initialRate);
            isTremoloStarted = true;
            currentAmplitude = initialAmplitude;
            tremoloFuture = tremoloExecutorService.scheduleAtFixedRate(this::adjustTremoloValue, 0, 1, TimeUnit.MILLISECONDS);
        }
    }


    public float getCurrentAmplitude(){
        return currentAmplitude;
    }


    public void stopTremolo(){
        if(tremoloFuture == null || tremoloFuture.isCancelled()) {
            return;
        }
        tremoloFuture.cancel(true);
        isTremoloStarted = false;
        currentAmplitude = initialAmplitude;
    }


    public void setEnabled(boolean isEnabled){
        isTremoloEnabled = isEnabled;
    }


    private void adjustTremoloValue(){
            updateAmplitudeSaw();
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


    void updateAmplitudeSquare(){
        if(isTremoloAmplitudeDecreasing){
            currentAmplitude = 0.01f;
            switchTremoloDirection();
            return;
        }
        currentAmplitude = 0.3f;
        switchTremoloDirection();
    }


    void updateAmplitudeSaw(){
        if(isTremoloAmplitudeDecreasing){
            currentAmplitude -= amplitudeStep;
            mainActivity.setAmplitude(currentAmplitude);
            if(currentAmplitude <= 0.005f) {
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
        tremoloRateCounter-=1;
        if(tremoloRateCounter <= 0){
            if(currentAmplitude < maxAmplitude){
                currentAmplitude += amplitudeStep;
            }
            mainActivity.setAmplitude(currentAmplitude);
            tremoloRateCounter = initialTremoloRateCounter;
        }
    }


    void switchTremoloDirection(){
        isTremoloAmplitudeDecreasing = !isTremoloAmplitudeDecreasing;
    }

}
