package com.gds.app.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gaodesong on 18/1/18.
 */

public class RectangulerPulseView extends View{


    //脉冲个数
    private int pulses;
    //每个脉冲的点数，用于画path
    private int pointsOfEachPulse;
    //心率线每移动一个点距所需的时间
    private int speed;
    private Paint paint;

    private int[] pointsHeight;
    private int points;
    private boolean isInit=false;
    private int count;

    private ArrayList<Position> positions;

    public RectangulerPulseView(Context context) {
        this(context,null);

    }

    public RectangulerPulseView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);

    }

    public RectangulerPulseView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    public void init(Context context){

        paint=new Paint();
        paint.setColor(Color.RED);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(1f);
        paint.setStyle(Paint.Style.STROKE);

        pulses=2;
        pointsOfEachPulse=10;
        speed=100;

        //在view可视区内的点数
        points=pulses*pointsOfEachPulse;
        //+pointsOfEathPulse是增加在View的非可视区内的点数（即在非可视区加载一个脉冲），
        //+1是为了数组前后依次赋值时避免到最后一个点赋值越界的情况
        pointsHeight=new int[points+pointsOfEachPulse+1];

        positions=new ArrayList<>();


    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //点与点之间的间隔
        int w=getWidth()/(pulses*(pointsOfEachPulse-3)+pulses-1);
        int h=getHeight();

        //初始化所有点的高，使之全部等于高的一半
        if(!isInit){
//            for(int i=0; i<pointsHeight.length; i++){
//                pointsHeight[i]=getHeight();
//            }

            for(int i=0;i<(points+pointsOfEachPulse+1);i++){
                if(i<=9){
                    if(i==0 || i==1){
                        positions.add(new Position(0,h));
                    } else if(i==2 || i==3){
                        positions.add(new Position(w,h));
                    } else {
                        positions.add(new Position((i-2)*w,h));
                    }

                }else if(i<=19){
                    if(i==10 || i==11){
                        positions.add(new Position(8*w,h));
                    } else if(i==12 || i==13){
                        positions.add(new Position(9*w,h));
                    }else {
                        positions.add(new Position((i-4)*w,h));
                    }
                }else if(i<=29){
                    if(i==20 || i==21){
                        positions.add(new Position(16*w,h));
                    } else if(i==22 || i==23){
                        positions.add(new Position(17*w,h));
                    }else {
                        positions.add(new Position((i-6)*w,h));
                    }
                }else if(i==30){
                    positions.add(new Position(24*w,h));
                }



            }



            isInit=true;
        }

        //创建一条路径，并按点与点的间隔递增和poitsHeight数据得到点的位置
        Path path=new Path();
        path.moveTo(0, getHeight());
        for(int i=0; i<positions.size(); i++){
            path.lineTo(positions.get(i).x, positions.get(i).y);
        }


        //画路径
        canvas.drawPath(path, paint);

        postDelayed(new Runnable() {
            @Override
            public void run() {
//                for(int i=0; i<points+pointsOfEachPulse; i++){
//                    pointsHeight[i]=pointsHeight[i+1];
//                }
                for(int i=0;i<points+pointsOfEachPulse;i++){
                    positions.get(i).x= positions.get(i+1).x;
                    positions.get(i).y= positions.get(i+1).y;
                }
                if(count==pointsOfEachPulse){
                    //随机一个高度以内的数值
                    //int height=(int) (Math.random()*getHeight()/2);
                    //在pointsOfEachPulse这几个点之内画好你想要的pulse形状
                    //这里实例只是简单地上下放一个随机高度而已
                    //这里要确保pointsOfEachPulse>3,否则越界
                    for(int i=0; i<pointsOfEachPulse; i++){
                        if(i==1){
                            //pointsHeight[points+i]=10;
                            positions.get(points+1).y=10;
                        }else if(i==2){
                           // pointsHeight[points+i]=10;
                            positions.get(points+1).y=10;
                        }else{
                            positions.get(points+1).y=getHeight();
                            //pointsHeight[points+i]=getHeight();
                        }
                    }
                    count=0;
                }
                count++;
                invalidate();

            }
        },speed);


    }


    class Position{

        public Position(float x, float y) {
            this.x = x;
            this.y = y;
        }

        public float x;

        public float y;



    }






}
