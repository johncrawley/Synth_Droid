package com.jcrawley.synthdroid.view;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import com.jcrawley.synthdroid.MusicNote;

public class NoteItem implements SimpleDrawableItem{

    private final String noteStr;
    private final MusicNote musicNote;
    private RectF rect, borderRect;
    private int textX, textY;
    private Paint unselectedPaint, selectedPaint, borderPaint, textPaint;
    private boolean isSelected;
    private final NoteItemManager noteItemManager;

    public NoteItem(MusicNote musicNote, int x, float y, int width, float height, NoteItemManager noteItemManager){
        this.musicNote = musicNote;
        this.noteStr = musicNote.getDisplayName();
        this.noteItemManager = noteItemManager;
        createRectangles(x,y,width, height);
        setupTextDimensions(x,y,height);
        setupPaints();
    }


    private void createRectangles(int x, float y, int width, float height){
        borderRect = new RectF(x,y , x + width, y + height);
        rect = new RectF(x+ 1,y + 1 , (x + width) - 1, (y + height) - 1);
    }


    private void setupTextDimensions(int x, float y, float height){
        textX = x + 50;
        textY = (int)y + ((int)height/2) + 5;
        textPaint = new Paint();
        textPaint.setTextSize(38);
    }


    private void setupPaints(){
        unselectedPaint = new Paint();
        unselectedPaint.setColor(Color.GRAY);

        selectedPaint = new Paint();
        selectedPaint.setARGB(255, 130, 130, 100);

        borderPaint = new Paint();
    }


    @Override
    public void draw(Canvas canvas, Paint paint) {
        Paint drawPaint = isSelected ? selectedPaint : unselectedPaint;
        canvas.drawRect(borderRect, borderPaint);
        canvas.drawRect(rect, drawPaint);
        canvas.drawText(noteStr, textX, textY, textPaint);
    }


    public void onMotion(int x, int y, boolean isDownOrMoveAction){
       isSelected = isDownOrMoveAction && borderRect.contains(x,y);
       if(isSelected){
           noteItemManager.playNote(musicNote);
       }
    }


}
