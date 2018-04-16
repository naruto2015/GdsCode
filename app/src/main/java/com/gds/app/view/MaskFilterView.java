package com.gds.app.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.gds.app.R;


/**
 * Created by gaodesong on 18/1/18.
 */



public class MaskFilterView extends View {

    private Path path;

    private Paint paint;

    private int first;

    private Context context;

    public MaskFilterView(Context context) {
        this(context,null);
    }

    public MaskFilterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context,attrs);
    }

    public MaskFilterView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    private void initView(Context context,AttributeSet attrs){

        this.context=context;
        first=getResources().getColor(R.color.colorPrimaryDark);
        paint=new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.DKGRAY);

        paint.setMaskFilter(new BlurMaskFilter(10,BlurMaskFilter.Blur.NORMAL));
        setLayerType(LAYER_TYPE_SOFTWARE,null);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);


        Bitmap bitmap= BitmapFactory.decodeResource(context.getResources(),R.mipmap.test);
        Bitmap shadow=bitmap.extractAlpha();


        //canvas.drawRect(new Rect(10,10,getWidth()-10,getHeight()-10),paint);

        canvas.drawBitmap(shadow,10,10,paint);


    }




}