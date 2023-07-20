package com.jcrawley.synthdroid.view;

import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.List;

public class NoteItemManager {

    private TransparentView transparentView;
    private Paint unselectedPaint, selectedPaint;
    private List<NoteItem> noteItems;

    public NoteItemManager(TransparentView transparentView){
        this.transparentView = transparentView;
        noteItems = new ArrayList<>(100);
        setupPaints();
    }


    private void setupPaints(){
        unselectedPaint = new Paint();
        unselectedPaint.setColor(Color.GRAY);
        selectedPaint = new Paint();
        selectedPaint.setColor(Color.YELLOW);
    }


    public void addNotes(int numberOfNotes){
        int itemWidth = transparentView.getMeasuredWidth();
        int itemHeight = transparentView.getMeasuredHeight() / numberOfNotes;
        for(int i = 0; i < numberOfNotes; i++){
            NoteItem noteItem = new NoteItem("note", 0, itemHeight * i, itemWidth, itemHeight, unselectedPaint, selectedPaint);
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
