package com.jcrawley.synthdroid.view;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.jcrawley.synthdroid.MusicNote;

public class NoteItem implements SimpleDrawableItem{

    private final String noteStr;
    private final MusicNote musicNote;
    private final Rect rect, borderRect;
    private final int textX, textY;
    private final Paint unselectedPaint, selectedPaint, borderPaint, textPaint;
    private boolean isSelected;
    private final NoteItemManager noteItemManager;

    public NoteItem(MusicNote musicNote, int x, int y, int width, int height, Paint unselectedPaint, Paint selectedPaint, NoteItemManager noteItemManager){
        this.musicNote = musicNote;
        this.noteStr = musicNote.getDisplayName();
        borderRect = new Rect(x,y , x + width, y + height);
        rect = new Rect(x+ 1,y + 1 , (x + width) - 1, (y + height) - 1);
        textX = x + 50;
        textY = y + 100;
        this.unselectedPaint = unselectedPaint;
        this.selectedPaint = selectedPaint;
        this.noteItemManager = noteItemManager;
        borderPaint = new Paint();
        textPaint = new Paint();
        textPaint.setTextSize(38);
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
