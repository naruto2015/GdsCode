package com.gds.app.view.nestscrollview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.NestedScrollingParentHelper;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;

import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;


/**
 * Created by gaodesong on 17/12/19.
 */

public class MyNestedScrollParent extends FrameLayout implements NestedScrollingParent{

    private static final String TAG = "NestScrollingLayout";

    private  NestedScrollingParentHelper mParentHelper;


    public MyNestedScrollParent(Context context) {
        super(context);
        init();
    }

    public MyNestedScrollParent(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyNestedScrollParent(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init(){
        mParentHelper = new NestedScrollingParentHelper(this);

    }


    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        Log.e(TAG, "child==target:" + (child == target));
        Log.e(TAG, "----父布局onStartNestedScroll----------------");

        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onNestedScrollAccepted(View child, View target, int nestedScrollAxes) {

        Log.e(TAG, "----父布局onNestedScrollAccepted---------------");

        mParentHelper.onNestedScrollAccepted(child, target, nestedScrollAxes);
    }

    @Override
    public void onStopNestedScroll(View target) {
        Log.e(TAG, "----父布局onStopNestedScroll----------------");
        mParentHelper.onStopNestedScroll(target);
    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed,
                               int dxUnconsumed, int dyUnconsumed) {
        Log.e(TAG, "----父布局onNestedScroll----------------");
    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        scrollBy(0, -dy);

        consumed[0] = 0;
        consumed[1] = 10; // 把消费的距离放进去
        Log.e(TAG, "----父布局onNestedPreScroll----------------");
    }

    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        Log.e(TAG, "----父布局onNestedFling----------------");
        return true;
    }
    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY)  {
        Log.e(TAG, "----父布局onNestedPreFling----------------");
        return true;
    }
    @Override
    public int getNestedScrollAxes() {
        Log.e(TAG, "----父布局getNestedScrollAxes----------------");
        return mParentHelper.getNestedScrollAxes();
    }





}
