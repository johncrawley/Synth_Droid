package com.jcrawley.synthdroid.view;

import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.jcrawley.synthdroid.MainActivity;
import com.jcrawley.synthdroid.MusicNote;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NoteItemManager {

    private final TransparentView transparentView;
    private Paint unselectedPaint, selectedPaint;
    private final List<NoteItem> noteItems;
    private final MainActivity activity;
    private float previousFreq;


    public NoteItemManager(MainActivity mainActivity, TransparentView transparentView){
        this.activity = mainActivity;
        this.transparentView = transparentView;
        noteItems = new ArrayList<>(100);
        setupPaints();
    }


    private void setupPaints() {
        unselectedPaint = new Paint();
        unselectedPaint.setColor(Color.GRAY);
        selectedPaint = new Paint();
        selectedPaint.setColor(Color.YELLOW);
    }


    void playNote(MusicNote musicNote){
        float frequency = musicNote.getFrequency();
        if(frequency == previousFreq){
            return;
        }
        activity.setFrequency(frequency);
        previousFreq = frequency;
    }


    public void addNotes(int numberOfNotes){
        int itemWidth = transparentView.getMeasuredWidth();
        int itemHeight = transparentView.getMeasuredHeight() / numberOfNotes;
        MusicNote[] musicNotes = MusicNote.values();
        MusicNote[] musicNotes2 = Arrays.copyOfRange(musicNotes, 48, 72);
        for(int i = 0; i < numberOfNotes; i++){
            MusicNote musicNote = musicNotes2[i];
            NoteItem noteItem = new NoteItem(musicNote, 0, itemHeight * i, itemWidth, itemHeight, unselectedPaint, selectedPaint, this);
            noteItems.add(noteItem);
            transparentView.addItem(noteItem);
        }
    }

    public void motion(int x, int y, int action){
        for(NoteItem noteItem : noteItems){
            noteItem.onMotion(x,y, action!= MotionEvent.ACTION_UP);
        }
        transparentView.invalidate();

    }

}
