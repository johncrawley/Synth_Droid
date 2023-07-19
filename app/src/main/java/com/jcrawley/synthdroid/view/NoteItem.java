package com.jcrawley.synthdroid.view;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class NoteItem implements SimpleDrawableItem{

    private final String noteStr;
    private final Rect rect, borderRect;
    private final int textX, textY;
    private final Paint unselectedPaint, selectedPaint, borderPaint;
    private boolean isSelected;

    public NoteItem(String noteStr, int x, int y, int width, int height, Paint unselectedPaint, Paint selectedPaint){
        this.noteStr = noteStr;
        borderRect = new Rect(x,y , x + width, y + height);
        rect = new Rect(x+ 1,y + 1 , (x + width) - 1, (y + height) - 1);
        textX = x + 10;
        textY = y + 10;
        this.unselectedPaint = unselectedPaint;
        this.selectedPaint = selectedPaint;
        borderPaint = new Paint();
    }


    @Override
    public void draw(Canvas canvas, Paint paint) {
        Paint drawPaint = isSelected ? selectedPaint : unselectedPaint;
        canvas.drawRect(borderRect, borderPaint);
        canvas.drawRect(rect, drawPaint);
        canvas.drawText(noteStr, textX, textY, paint);
    }


}
