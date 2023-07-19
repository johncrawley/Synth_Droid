package com.jcrawley.synthdroid.view;

import android.graphics.Color;
import android.graphics.Paint;

public class NoteItemFactory {

    private TransparentView transparentView;
    private Paint unselectedPaint, selectedPaint;

    public NoteItemFactory(TransparentView transparentView){
        this.transparentView = transparentView;
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
            transparentView.addItem(noteItem);
        }
    }

}
