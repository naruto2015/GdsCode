package com.gds.app.view;

import android.content.Context;
import android.graphics.Bitmap;
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
import android.widget.Toast;

import com.gds.app.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by gaodesong on 18/1/18.
 */



public class PageTurnView extends View {


    private List<Bitmap> mBitmaps;
    private Paint mTextPaint;
    private int mTextSizeLarger;
    private int mViewWidth;
    private int mViewHeight;
    private int mTextSizeNormal;
    private int mClipX;
    private float autoAreaLeft;
    private float autoAreaRight;
    private float mAutoAreaLeft, mAutoAreaRight;// 控件左侧和右侧自动吸附的区域
    private boolean isLastPage;
    private int pageIndex;
    private Context context;
    private boolean isNextPage;
    private float mCurPointX;// 指尖触碰屏幕时点X的坐标值
    private float mMoveValid;// 移动事件的有效距离


    public PageTurnView(Context context) {
        this(context,null);
    }

    public PageTurnView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context,attrs);
    }

    public PageTurnView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    private void initView(Context context,AttributeSet attrs){

        this.context=context;
        mTextPaint=new Paint();
        mTextPaint.setAntiAlias(true);
        mTextSizeLarger=20;
        mTextSizeNormal=20;
        mBitmaps=new ArrayList<>();


    }


    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);
        Log.e("PageTurnView","onDraw");
      /*
         * 如果数据为空则显示默认提示文本
         */
        if (null == mBitmaps || mBitmaps.size() == 0) {
            defaultDisplay(canvas);
            return;
        }

        // 绘制位图
        drawBtimaps(canvas);


    }


    /**
     * 设置位图数据 
     *
     * @param mBitmaps
     *            位图数据列表 
     */
    public synchronized void setBitmaps(List<Bitmap> mBitmaps) {  
        /* 
         * 如果数据为空则抛出异常 
         */
        if (null == mBitmaps || mBitmaps.size() == 0)
            throw new IllegalArgumentException("no bitmap to display");  
      
        /* 
         * 如果数据长度小于2则GG思密达 
         */
        if (mBitmaps.size() < 2)
            throw new IllegalArgumentException("fuck you and fuck to use imageview");

        this.mBitmaps = mBitmaps;


        invalidate();
    }


    /**
     * 默认显示
     *
     * @param canvas
     *            Canvas对象
     */
    private void defaultDisplay(Canvas canvas) {
        // 绘制底色
        canvas.drawColor(Color.WHITE);

        // 绘制标题文本
        mTextPaint.setTextSize(mTextSizeLarger);
        mTextPaint.setColor(Color.RED);
        canvas.drawText("FBI WARNING", mViewWidth / 2, mViewHeight / 4, mTextPaint);

        // 绘制提示文本
        mTextPaint.setTextSize(mTextSizeNormal);
        mTextPaint.setColor(Color.BLACK);
        canvas.drawText("Please set data use setBitmaps method", mViewWidth / 2, mViewHeight / 3, mTextPaint);
    }


    /**
     * 初始化位图数据
     * 缩放位图尺寸与屏幕匹配
     */
    private void initBitmaps() {
        Log.e("PageTurnView","initBitmaps");

        List<Bitmap> temp = new ArrayList<Bitmap>();
        for (int i = mBitmaps.size() - 1; i >= 0; i--) {
            Bitmap bitmap = Bitmap.createScaledBitmap(mBitmaps.get(i), mViewWidth, mViewHeight, true);
            temp.add(bitmap);
        }
        mBitmaps = temp;
    }


    /**
     * 绘制位图
     *
     * @param canvas
     *            Canvas对象
     */
    private void drawBtimaps(Canvas canvas) {


        isLastPage = false;
        // 限制pageIndex的值范围
        pageIndex = pageIndex < 0 ? 0 : pageIndex;
        pageIndex = pageIndex > mBitmaps.size() ? mBitmaps.size() : pageIndex;

        // 计算数据起始位置
        int start = mBitmaps.size() - 2 - pageIndex;
        int end = mBitmaps.size() - pageIndex;


        if(start<0){

            isLastPage=true;
            Toast.makeText(context,"last",Toast.LENGTH_SHORT).show();;

            start=0;
            end=1;

        }

        for (int i = 0; i < mBitmaps.size(); i++) {
            canvas.save();

            /*
             * 仅裁剪位于最顶层的画布区域
             * 如果到了末页则不在执行裁剪
             */
            if(!isLastPage && i==end-1){
                canvas.clipRect(0,0,mClipX,mViewHeight);
            }
            canvas.drawBitmap(mBitmaps.get(i), 0, 0, null);

            canvas.restore();
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        isNextPage = true;
        switch (event.getAction()){


            case MotionEvent.ACTION_DOWN:

                mCurPointX=event.getX();
                if(mCurPointX < autoAreaLeft){
                    isNextPage = false;
                    pageIndex--;
                    mClipX= (int) mCurPointX;
                    invalidate();
                }

                break;

            case MotionEvent.ACTION_MOVE:
                float SlideDis = mCurPointX - event.getX();
                if (Math.abs(SlideDis) > mMoveValid) {
                    // 获取触摸点的x坐标
                    mClipX = (int) event.getX();
                    invalidate();
                }

                break;

            case MotionEvent.ACTION_UP:
                // 判断是否需要自动滑动
                judgeSlideAuto();

                if (!isLastPage && isNextPage && mClipX <= 0) {
                    pageIndex++;
                    mClipX = mViewWidth;
                    invalidate();
                }

                break;


        }

        return true;
    }


    /**
     * 判断是否需要自动滑动
     * 根据参数的当前值判断绘制
     */
    private void judgeSlideAuto() {

        if(mClipX < autoAreaLeft){
            while (mClipX>0){
                mClipX--;
                invalidate();
            }
        }

        if (mClipX > autoAreaRight) {
            while (mClipX < mViewWidth) {
                mClipX++;
                invalidate();
            }
        }



    }

        @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.e("PageTurnView","onSizeChanged");

        mViewWidth=w;

        mViewHeight=h;

        mClipX = mViewWidth;

       // 计算控件左侧和右侧自动吸附的区域

            autoAreaLeft = mViewWidth * 1 / 5F;
            autoAreaRight = mViewWidth * 4 / 5F;
            // 计算一度的有效距离
            mMoveValid = mViewWidth * 1 / 100F;
            initBitmaps();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.e("PageTurnView","onMeasure");


    }
}