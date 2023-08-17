package com.jcrawley.synthdroid.fx;

import com.jcrawley.synthdroid.Interval;
import com.jcrawley.synthdroid.MainActivity;
import com.jcrawley.synthdroid.MusicNote;
import com.jcrawley.synthdroid.NoteCalculator;
import com.jcrawley.synthdroid.view.NoteItem;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Arpeggiator {

    private int rate = 100;
    private boolean isEnabled = false;
    private ScheduledFuture<?> future;
    private final ScheduledExecutorService executorService;
    private List<MusicNote> notes;
    private int currentNoteIndex;
    private final NoteCalculator noteCalculator;
    private final MainActivity activity;


    public Arpeggiator(MainActivity mainActivity){
        executorService = Executors.newSingleThreadScheduledExecutor();
        notes = new ArrayList<>();
        noteCalculator = new NoteCalculator();
        this.activity = mainActivity;
    }


    public void setEnabled(boolean isEnabled){
        this.isEnabled = isEnabled;
    }


    public void setRate(int rate){
        this.rate = rate;
    }


    public void stop(){
        log("Entered stop()");
        if(future != null && !future.isCancelled()){
            future.cancel(false);
        }
    }


    public void start(MusicNote note){
        setNoteFrequency(note);
        if(!isEnabled){
            return;
        }
        currentNoteIndex = 0;
        calculateIntervals(note);
        if(future == null || future.isCancelled() ) {
            future = executorService.scheduleAtFixedRate(this::changeNote, rate, rate, TimeUnit.MILLISECONDS);
        }
    }


    private void calculateIntervals(MusicNote note){
        List<MusicNote> updatedNotes = new ArrayList<>();
        updatedNotes.add(note);
        updatedNotes.add(noteCalculator.addIntervalUp(note, Interval.MAJOR_THIRD));
        updatedNotes.add(noteCalculator.addIntervalUp(note, Interval.PERFECT_FIFTH));
        notes = new ArrayList<>(updatedNotes);
    }


    private int printCounter = 0;

    private void changeNote(){
        logOnInterval("Entered changeNote()");
        incrementCurrentNoteIndex();
        setNoteFrequency(notes.get(currentNoteIndex));
    }


    private void logOnInterval(String msg){
        final int printRate = 5;
        printCounter++;
        if(printCounter >= printRate){
            printCounter = 0;
            log(msg);
        }
    }

    private void log(String msg){
        System.out.println("^^^ Arpeggiator: " + msg);
    }


    private void setNoteFrequency(MusicNote musicNote){
        activity.setFrequency(musicNote.getFrequency());
    }


    private void incrementCurrentNoteIndex(){
        currentNoteIndex = (currentNoteIndex + 1) % notes.size();
    }

}
