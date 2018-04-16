package com.gds.app.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

import com.gds.app.R;


/**
 * Created by gaodesong on 18/1/18.
 */



public class HeartRectView extends View {

    private Path path;

    private Paint paint;

    private int first;

    private int x,y;


    public HeartRectView(Context context) {
        this(context,null);
    }

    public HeartRectView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context,attrs);
    }

    public HeartRectView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    private void initView(Context context,AttributeSet attrs){

        first=getResources().getColor(R.color.colorPrimaryDark);
        paint = new Paint();
        paint.setStrokeWidth(1);
        paint.setColor(first);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        path=new Path();

    }


    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);

        if(x>getWidth()/2){
            y=getHeight()/2;
        }
        path.moveTo(0,getHeight()/2);
        path.lineTo(x,y);
        canvas.drawPath(path,paint);
        canvas.translate(x,0);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                x+=10;
                if(x>getWidth()/2){
                    y=(int) (Math.random()*getHeight()/2);
                }

                postInvalidate();
            }
        },200);


    }







}