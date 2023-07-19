package com.jcrawley.synthdroid.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class TransparentView extends View {

    private int canvasTranslateX,canvasTranslateY;
    private Paint paint;
    private Canvas canvasBitmap;
    private boolean isViewDrawn = false;

    private List<SimpleDrawableItem> items;


    public TransparentView(Context context) {
        super(context);
    }

    public TransparentView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
        items = new ArrayList<>();
    }


    public TransparentView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
        items = new ArrayList<>();
    }

    public void addItem(SimpleDrawableItem item){
        items.add(item);
    }


    public void clearDrawableItems(){
        this.items.clear();
    }


    public void setTranslateY(int y){
        this.canvasTranslateY = y;
    }

    public void setTranslateX(int x){
        this.canvasTranslateX = x;
    }


    public void translateXToMiddle(){
        this.canvasTranslateX = getWidth() / 2;
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        defaultAttributes();
        isViewDrawn = true;
    }


    //@Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!isViewDrawn) {
            defaultAttributes();
        }
        isViewDrawn = true;
        Bitmap bitmap = createViewBitmap();
        float bitmapX = 0;
        int bitmapY = 0;
        canvas.drawBitmap(bitmap, bitmapX, bitmapY, null);
    }


    private void initPaint(){
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
    }


    private void defaultAttributes() {
    }


    private Bitmap createViewBitmap() {
        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        bitmap.eraseColor(Color.TRANSPARENT);
        canvasBitmap = new Canvas(bitmap);
        canvasBitmap.save();
        canvasBitmap.translate(canvasTranslateX, canvasTranslateY);
        drawItems();
        canvasBitmap.restore();
        return bitmap;
    }


    private void drawItems(){
        for(SimpleDrawableItem item : items){
            item.draw(canvasBitmap, paint);
        }
    }


}