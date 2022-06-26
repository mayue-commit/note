package com.example.testrun;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

public class SimpleColorChangeTextView extends AppCompatTextView {
    private String mText = "ABgI";//成员变量
    private int mPercent = 0;

    Paint paint;
    private float textX;
    private float textY;
    private float textWidth;
    Paint.FontMetrics fontMetrics;
    int remainColor ;
    int bgColor ;
    int foreColor;

    public SimpleColorChangeTextView(Context context) {
        super(context);
        initPaint();
    }

    public SimpleColorChangeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public SimpleColorChangeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint() {
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        paint.setTextSize(50);
        paint.setTextAlign(Paint.Align.LEFT);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawPaintBg(canvas);
        drawRemainBg(canvas);
        drawEndArea(canvas);
        if (getWidth() * mPercent / 100 > textX) {
            drawPainArea(canvas);
        }
    }


    private void drawPaintBg(final Canvas canvas) {
        canvas.save();
        paint.setColor(remainColor);
        canvas.drawRect(getX(), getY(),
                getWidth() * mPercent / 100, getTop() + getHeight(), paint);
        canvas.restore();
    }

    private void drawRemainBg(final Canvas canvas) {
        canvas.save();
        paint.setColor(bgColor);
        canvas.drawRect(getWidth() * mPercent / 100, getY(),
                getRight(), getTop() + getHeight(), paint);
        canvas.restore();
    }

    private void drawPainArea(final Canvas canvas) {
        canvas.save();
        fontMetrics = paint.getFontMetrics();
        textWidth = paint.measureText(mText);
        textX = getMeasuredWidth() / 2 - textWidth / 2;
        textY = getMeasuredHeight() / 2 - (fontMetrics.descent + fontMetrics.ascent) / 2;
        paint.setColor(foreColor);
        float endX = getWidth() * mPercent / 100;
        Rect rect = new Rect((int) textX, 0, (int) endX, getHeight());
        canvas.clipRect(rect);
        canvas.drawText(mText, textX, textY, paint);
        canvas.restore();
    }


    private void drawEndArea(final Canvas canvas) {
        canvas.save();
        fontMetrics = paint.getFontMetrics();
        textWidth = paint.measureText(mText);
        textX = getMeasuredWidth() / 2 - textWidth / 2;
        textY = getMeasuredHeight() / 2 - (fontMetrics.descent + fontMetrics.ascent) / 2;
        paint.setColor(remainColor);
        Rect rect = new Rect((int) textX, 0, getWidth(), getHeight());
        canvas.clipRect(rect);
        canvas.drawText(mText, textX, textY, paint);
        canvas.restore();
    }


    public void setPercent(int mPercent) {
        this.mPercent = mPercent;
        this.mText = mPercent + "%";
        invalidate();
    }

    public int getPercent() {
        return mPercent;
    }

    public void setTextStyle(int bgColor, int remainColor,  int foreColor, int size) {
        this.remainColor =getContext().getResources().getColor(remainColor);
        this.bgColor = getContext().getResources().getColor(bgColor);
        this.foreColor = getContext().getResources().getColor(foreColor);
        paint.setTextSize(size);
    }

    public void setText(String text) {
        this.mText = text;
    }
}
