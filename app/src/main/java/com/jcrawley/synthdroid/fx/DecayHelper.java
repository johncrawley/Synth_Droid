package com.jcrawley.synthdroid.fx;

import com.jcrawley.synthdroid.MainActivity;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DecayHelper {

    private final MainActivity mainActivity;
    private Future<?> future;
    private final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
    private float currentAmplitude;


    public DecayHelper(MainActivity mainActivity){
        this.mainActivity = mainActivity;
    }


    public void onNewNotePressed(){
        mainActivity.setAmplitude(0.3f);
        if(future != null && !future.isCancelled()){
            future.cancel(true);
        }
    }


    public void decayNoteAndStop(){
        currentAmplitude = 0;
        future = executorService.scheduleAtFixedRate(this::reduceVolume, 0, 2, TimeUnit.MILLISECONDS);

    }


    private void reduceVolume(){
        if(currentAmplitude <= 0){
            future.cancel(false);
            mainActivity.setToneOn(false);
            return;
        }
        final float volumeReductionStep = 0.002f;
        currentAmplitude = Math.max(0, currentAmplitude - volumeReductionStep);
        mainActivity.setAmplitude(currentAmplitude);
    }

}
