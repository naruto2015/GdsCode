package com.gds.app.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.Rect;
import android.support.v4.view.ViewConfigurationCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Scroller;

import com.gds.app.R;


/**
 * Created by gaodesong on 18/1/18.
 */



public class HeartRateView extends View {

    private Path mPath;

    private Paint mPaint;

    private PathEffect pathEffect;

    private int index=0;

    private int width;


    public HeartRateView(Context context) {
        this(context,null);
    }

    public HeartRateView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context,attrs);
    }

    public HeartRateView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    private void initView(Context context,AttributeSet attrs){

        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(1);
        mPaint.setColor(Color.DKGRAY);
        mPaint.setAntiAlias(true);
        mPath = new Path();

        pathEffect=new CornerPathEffect(10);




    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);

        width=getWidth();
        mPath.moveTo(0, getHeight()/2);
        //mPath.lineTo(width,getHeight()/2);

        mPaint.setPathEffect(pathEffect);
        canvas.drawPath(mPath,mPaint);


    }


}