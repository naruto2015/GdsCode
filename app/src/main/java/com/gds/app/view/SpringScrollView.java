package com.gds.app.view;

import android.content.Context;
import android.support.animation.SpringAnimation;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by gaodesong on 17/12/19.
 */

public class SpringScrollView extends NestedScrollView{

    private float startDragY;
    
    private SpringAnimation springAnim;

    public SpringScrollView(Context context) {
        this(context,null);
    }

    public SpringScrollView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SpringScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        
        init();
        
    }

    private void init() {

        springAnim=new SpringAnimation(this,SpringAnimation.TRANSLATION_Y,0);

        springAnim.getSpring().setStiffness(800.0f);

        springAnim.getSpring().setDampingRatio(0.50f);



    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        switch (ev.getAction()){

            case MotionEvent.ACTION_MOVE:

                if(getScrollY() <= 0){
                    //顶部下拉
                    if(startDragY == 0){
                        startDragY = ev.getRawY();
                    }

                    if(ev.getRawY() - startDragY >0){
                        setTranslationY((ev.getRawY()-startDragY)/3);
                        return true;
                    }else {
                        springAnim.cancel();
                        setTranslationY(0);
                    }




                }else if(getScrollY() + getHeight() >= getChildAt(0).getMeasuredHeight()){
                    //顶部上啦
                    if(startDragY == 0){
                        startDragY = ev.getRawY();

                    }

                    if(ev.getRawY() - startDragY<0){
                        setTranslationY((ev.getRawY() - startDragY)/3);
                        return  true;
                    }else {
                        springAnim.cancel();
                        setTranslationY(0);
                    }


                }

                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:

                if(getTranslationY() != 0){
                    springAnim.start();
                }

                startDragY = 0;

                break;

        }

        return super.onTouchEvent(ev);
    }
}
