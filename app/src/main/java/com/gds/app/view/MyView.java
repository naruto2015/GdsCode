package com.gds.app.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
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



public class MyView extends View {

    private Path path;

    private Paint paint;

    private int first,second,third;

    private int width;

    private int height;

    private Rect rect=null;

    private Scroller mScroller;

    private int mTouchSlop;

    private float mXDown;

    private float mXLastMove;

    private int leftBorder;

    private int rightBorder;


    /**
     * 手机当时所处的屏幕坐标
     */
    private float mXMove;



    public MyView(Context context) {
        this(context,null);
    }

    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context,attrs);
    }

    public MyView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    private void initView(Context context,AttributeSet attrs){

        first=getResources().getColor(R.color.colorPrimaryDark);
        second=getResources().getColor(R.color.colorAccent);
        third=getResources().getColor(R.color.colorPrimary);
        paint = new Paint();
        paint.setColor(first);
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        mScroller=new Scroller(context);
        ViewConfiguration configuration=ViewConfiguration.get(context);
        mTouchSlop= ViewConfigurationCompat.getScaledPagingTouchSlop(configuration);

    }

    private int dd=0;

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);

        //canvas.translate(dd,0);
        width=getWidth();
        height=getHeight();
        rect=new Rect(0,0,width,height);
        canvas.drawRect(rect,paint);

        rect=new Rect(width,0,width*2,height);
        paint.setColor(second);
        canvas.drawRect(rect,paint);

        rect=new Rect(width*2,0,width*3,height);
        paint.setColor(third);
        canvas.drawRect(rect,paint);

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:

                mXMove=event.getRawX();
                float diff=Math.abs(mXMove-mXDown);
                int scrolledX= (int) (mXDown-mXMove);
                Log.e("tag",scrolledX+";");
                //scrollTo(100,0);
                //mXLastMove=mXMove;
                dd= (int) (mXLastMove-mXMove)+dd;
                Log.e("mXMove",mXMove+";");
                Log.e("dd",dd+";");
                //invalidate();
                scrollTo(dd,0);
                mXLastMove=mXMove;

                break;

            case MotionEvent.ACTION_DOWN:

                mXDown=event.getRawX();
                mXLastMove=mXDown;
                Log.e("tag",getScrollX()+"");
                break;

            case MotionEvent.ACTION_UP:

                break;
        }


        return true;
    }


    @Override
    public void computeScroll() {
        //super.computeScroll();
        if(mScroller.computeScrollOffset()){
            scrollTo(mScroller.getCurrX(),mScroller.getCurrY());
            invalidate();
        }
    }
}