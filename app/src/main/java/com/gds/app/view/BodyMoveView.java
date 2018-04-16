package com.gds.app.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.gds.app.R;
import com.gds.app.utils.ScreenUtils;

/**
 * Created by Administrator on 2018/3/16.
 */

public class BodyMoveView extends View{



    private Paint paint;

    Path path;

    private int rectCount=5;

    private int rectWidth;

    private int intervalWidth;

    private RectF rectF=null;

    private int TranX=0;

    private int width=0;

    private int x1,x2;

    private int[] pointsHeight;

    public BodyMoveView(Context context) {
        this(context,null);
    }

    public BodyMoveView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);

    }

    public BodyMoveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        paint=new Paint();
        paint.setColor(getResources().getColor(R.color.colorYellow));
        paint.setAntiAlias(true);
        paint.setStrokeWidth(1f);
        paint.setStyle(Paint.Style.FILL);
        //在view可视区内的点数
        path=new Path();
        rectWidth= ScreenUtils.dip2px(context,10);

        pointsHeight= new int[]{40, 10, 10, 10, 10, 10, 10, 10, 40, 10, 10};

    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width=w;
        intervalWidth=(w-rectWidth*rectCount)/6;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //intervalWidth=(getWidth()-rectWidth*rectCount)/6;


        x1=intervalWidth+TranX;
        x2=x1+rectWidth;
        rectF=new RectF(x1,10,x2,getHeight()-10);
        canvas.drawRoundRect(rectF,10,10,paint);

        for(int i=0;i<pointsHeight.length;i++){
            x1=x2+intervalWidth;
            x2=x1+rectWidth;
            rectF=new RectF(x1,40,x2,pointsHeight[i]);
            canvas.drawRoundRect(rectF,10,10,paint);
        }



        /*
        x1=intervalWidth+TranX;
        x2=x1+rectWidth;
        rectF=new RectF(x1,10,x2,getHeight()-10);
        canvas.drawRoundRect(rectF,10,10,paint);


        x1=x2+intervalWidth;
        x2=x1+rectWidth;
        rectF=new RectF(x1,40,x2,getHeight()-10);
        canvas.drawRoundRect(rectF,10,10,paint);


        x1=x2+intervalWidth;
        x2=x1+rectWidth;
        rectF=new RectF(x1,40,x2,getHeight()-10);
        canvas.drawRoundRect(rectF,10,10,paint);


        x1=x2+intervalWidth;
        x2=x1+rectWidth;
        rectF=new RectF(x1,40,x2,getHeight()-10);
        canvas.drawRoundRect(rectF,10,10,paint);


        x1=x2+intervalWidth;
        x2=x1+rectWidth;
        rectF=new RectF(x1,40,x2,getHeight()-10);
        canvas.drawRoundRect(rectF,10,10,paint);


        x1=x2+intervalWidth;
        x2=x1+rectWidth;
        rectF=new RectF(x1,10,x2,getHeight()-10);
        canvas.drawRoundRect(rectF,10,10,paint);


        x1=x2+intervalWidth;
        x2=x1+rectWidth;
        rectF=new RectF(x1,40,x2,getHeight()-10);
        canvas.drawRoundRect(rectF,10,10,paint);


        x1=x2+intervalWidth;
        x2=x1+rectWidth;
        rectF=new RectF(x1,40,x2,getHeight()-10);
        canvas.drawRoundRect(rectF,10,10,paint);


        x1=x2+intervalWidth;
        x2=x1+rectWidth;
        rectF=new RectF(x1,40,x2,getHeight()-10);
        canvas.drawRoundRect(rectF,10,10,paint);

        x1=x2+intervalWidth;
        x2=x1+rectWidth;
        rectF=new RectF(x1,40,x2,getHeight()-10);
        canvas.drawRoundRect(rectF,10,10,paint);

*/


//        canvas.drawRoundRect(rectF,10,10,paint);
//
//        canvas.translate(intervalWidth,0);
//        canvas.drawRoundRect(rectF,10,10,paint);
//
//        canvas.translate(intervalWidth,0);
//        canvas.drawRoundRect(rectF,10,10,paint);
//
//        canvas.translate(intervalWidth,0);
//        canvas.drawRoundRect(rectF,10,10,paint);
//
//
//        canvas.translate(intervalWidth,0);
//        canvas.drawRoundRect(rectF,10,10,paint);
//
//        canvas.translate(intervalWidth,0);
//        canvas.drawRoundRect(rectF,10,10,paint);
//
//        canvas.translate(intervalWidth,0);
//        canvas.drawRoundRect(rectF,10,10,paint);
//
//        canvas.translate(intervalWidth,0);
//        canvas.drawRoundRect(rectF,10,10,paint);
//
//        canvas.translate(intervalWidth,0);
//        canvas.drawRoundRect(rectF,10,10,paint);


        //canvas.translate(x,0);

        /*
        postDelayed(new Runnable() {
            @Override
            public void run() {
                TranX-=10;
                if(TranX<=-width){
                    TranX=0;
                }
                postInvalidate();
            }
        },200);
*/


    }









}
