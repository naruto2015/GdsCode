package com.gds.app.view.myselfview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ProgressBar;

/**
 * Created by gaodesong on 18/1/9.
 */

public class HoriProgressView extends ProgressBar{


    private Paint mPaint;//画笔

    public HoriProgressView(Context context) {
        super(context);
    }

    public HoriProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public HoriProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    private void initPaint(){
        this.mPaint=new Paint();
        this.mPaint.setAntiAlias(true);
        this.mPaint.setColor(Color.WHITE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);



    }


}
