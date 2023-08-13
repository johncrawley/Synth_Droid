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
    private boolean isEnabled = true;
    private ScheduledFuture<?> future;
    private final ScheduledExecutorService executorService;
    private final List<MusicNote> notes;
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


    public void stop(){
        if(future == null || future.isCancelled()){
            return;
        }
        future.cancel(false);
    }


    public void start(MusicNote note){
        setNoteFrequency(note);
        if(!isEnabled){
            return;
        }
        currentNoteIndex = 0;
        calculateIntervals(note);
        future = executorService.scheduleAtFixedRate(this::changeNote, rate , rate, TimeUnit.MILLISECONDS);
    }


    private void calculateIntervals(MusicNote note){
        notes.clear();
        notes.add(note);
        notes.add(noteCalculator.addIntervalUp(note, Interval.MAJOR_THIRD));
        notes.add(noteCalculator.addIntervalUp(note, Interval.PERFECT_FIFTH));
    }


    private void changeNote(){
        incrementCurrentNoteIndex();
        setNoteFrequency(notes.get(currentNoteIndex));
    }


    private void setNoteFrequency(MusicNote musicNote){
        activity.setFrequency(musicNote.getFrequency());
    }


    private void incrementCurrentNoteIndex(){
        currentNoteIndex = (currentNoteIndex + 1) % notes.size();
    }

}
