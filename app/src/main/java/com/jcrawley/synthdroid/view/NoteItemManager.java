package com.jcrawley.synthdroid.view;


import android.view.MotionEvent;

import com.jcrawley.synthdroid.MainActivity;
import com.jcrawley.synthdroid.MusicNote;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class NoteItemManager {

    private final TransparentView transparentView;
    private final List<NoteItem> noteItems;
    private final MainActivity activity;
    private float previousFreq;


    public NoteItemManager(MainActivity mainActivity, TransparentView transparentView){
        this.activity = mainActivity;
        this.transparentView = transparentView;
        noteItems = new ArrayList<>(100);
    }


    void playNote(MusicNote musicNote){
        float frequency = musicNote.getFrequency();
        if(frequency == previousFreq){
            return;
        }
       // activity.setFrequency(frequency);
        activity.playNote(musicNote);
        previousFreq = frequency;
    }


    public void addNotes(int numberOfNotes){
        int itemWidth = transparentView.getMeasuredWidth();
        float itemHeight = transparentView.getMeasuredHeight() / (float)numberOfNotes;
        MusicNote[] musicNotes = getSelectionOfNotes();

        for(int i = 0; i < numberOfNotes; i++){
            MusicNote musicNote = musicNotes[i];
            NoteItem noteItem = new NoteItem(musicNote, 0, itemHeight * i, itemWidth, itemHeight, this);
            noteItems.add(noteItem);
            transparentView.addItem(noteItem);
        }
    }


    private MusicNote[] getSelectionOfNotes(){
        MusicNote[] musicNotes = MusicNote.values();
        MusicNote[] musicNotes2 = Arrays.copyOfRange(musicNotes, 48, 72);
        Collections.reverse(Arrays.asList(musicNotes2));
        return musicNotes2;
    }

    public void motion(int x, int y, int action){
        for(NoteItem noteItem : noteItems){
            noteItem.onMotion(x,y, action!= MotionEvent.ACTION_UP);
        }
        transparentView.invalidate();
    }


    public void releaseAll(){
        noteItems.forEach(n -> n.onMotion(-100, -100, false));
    }
}
