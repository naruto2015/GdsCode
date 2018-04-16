package com.gds.app.view;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import com.gds.app.utils.BrightnessUtils;
import com.gds.app.utils.IjkPlayerManager;
import com.gds.app.R;
import com.gds.app.utils.ScreenUtils;

import java.io.IOException;

import tv.danmaku.ijk.media.player.IjkMediaPlayer;

/**
 * Created by gaodesong on 17/12/15.
 */

public class SampleVideoView extends FrameLayout implements View.OnClickListener, View.OnTouchListener {


    //正常
    public static final int CURRENT_STATE_NORMAL = 0;
    //准备中
    public static final int CURRENT_STATE_PREPAREING = 1;
    //播放中
    public static final int CURRENT_STATE_PLAYING = 2;
    //开始缓冲
    public static final int CURRENT_STATE_PLAYING_BUFFERING_START = 3;
    //暂停
    public static final int CURRENT_STATE_PAUSE = 5;
    //自动播放结束
    public static final int CURRENT_STATE_AUTO_COMPLETE = 6;
    //错误状态
    public static final int CURRENT_STATE_ERROR = 7;

    //当前的播放状态
    protected int mCurrentState = -1;


    private TextureView textureView;

    public   IjkMediaPlayer player = null;

    private String playUrl="";

    private Context context;

    private ImageView play,fullscreen;

    private RelativeLayout container;

    private SeekBar timeline;

    private LinearLayout layout_bottom;

    private GestureDetector mGestureDetector;

    private AudioManager mAudioManager;
    /** 最大声音 */
    private int mMaxVolume;
    /** 当前声音 */
    private int mVolume = -1;
    /** 当前亮度 */
    private float mBrightness = -1f;

    public SampleVideoView(@NonNull Context context) {
        this(context,null);

    }

    public SampleVideoView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SampleVideoView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }


    public void initView(Context context){
        IjkMediaPlayer.loadLibrariesOnce(null);
        IjkMediaPlayer.native_profileBegin("libijkplayer.so");
        this.context=context;
        View.inflate(context, R.layout.sample_videoview,this);
        textureView=findViewById(R.id.textureview);
        fullscreen=findViewById(R.id.fullscreen);
        play=findViewById(R.id.play);
        container=findViewById(R.id.container);
        timeline=findViewById(R.id.timeline);
        layout_bottom=findViewById(R.id.layout_bottom);


        timeline.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {


            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                player.seekTo(player.getDuration()*seekBar.getProgress()/seekBar.getMax());
            }



        });


        mAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        mMaxVolume = mAudioManager
                .getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        Log.e("mMaxVolume",mMaxVolume+"");
        setOnTouchListener(this);

        mGestureDetector = new GestureDetector(getContext(),new MyGestureListener());

        play.setOnClickListener(this);
        fullscreen.setOnClickListener(this);
        //container.setOnClickListener(this);
        player = new IjkMediaPlayer();

        textureView.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {
            @Override
            public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i1) {
                player.setSurface(new Surface(surfaceTexture));
            }

            @Override
            public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i1) {

            }

            @Override
            public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
                surfaceTexture=null;
                return false;
            }

            @Override
            public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {

            }
        });

    }






    @Override
    public boolean onTouchEvent(MotionEvent event) {
       // Log.e("onTouch","x"+event.getX()+"rawx"+event.getRawX());

        return super.onTouchEvent(event);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        if(mGestureDetector.onTouchEvent(motionEvent)){
            return true;
        }

        return false;
    }


    private float distance=0;

    private float totalDis=400;

    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener {


        @Override
        public boolean onDoubleTap(MotionEvent e) {

            setPlayStatus();
            Log.e("onDoubleTap","");
            return super.onDoubleTap(e);
        }


        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

            //Log.e("e1","e1:"+e1.getX()+"e2:"+e2.getX()+"distanceX"+distanceX);
            Log.e("e1","e1:"+e1.getY()+"e2:"+e2.getY()+"distanceY"+distanceY);

            WindowManager wm = activity.getWindowManager();
            int width = wm.getDefaultDisplay().getWidth();
            int height = wm.getDefaultDisplay().getHeight();

            //表示左边滑动
           if(e1.getX()<width/2){
                //向上增大音量
               onVolumeSlide(distanceY);
            }else if(e1.getX()>=width/2){
               //右滑动改变亮度

               onBrightnessSlide(distanceY);

           }

            return super.onScroll(e1, e2, distanceX, distanceY);
        }


        @Override
        public boolean onDown(MotionEvent e) {
            Log.e("onDown","onDown"+e.getX());
            distance=0;
            return true;
        }
    }


    private void onBrightnessSlide(float distanceY){


            distance+=distanceY;
            int a= (int) (255*distance/totalDis);
            int systemBrightness = BrightnessUtils.getScreenBrightness(context);
            int index=systemBrightness+a;

            if(index<=0){
                index=0;
            }else if (index>=255){
                index=255;
            }

        BrightnessUtils.setCurWindowBrightness(context,index);


    }

    private void onVolumeSlide(float distanceY){

        mVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        distance+=distanceY;
        int a= (int) (mMaxVolume*distance/totalDis);
        int index=mVolume+a;
        Log.e("onVolumeSlide",index+"");
        if(index<=0){
            index=0;
        }else if (index>=mMaxVolume){
            index=mMaxVolume;
        }
         mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, index, 0);

//        distance+=Math.abs(distanceY);
//        if(distanceY<0){
//            //向下滑动 减小音量
//
//            int a= -(int) (mMaxVolume*distance/totalDis);
//            int index=mVolume+a;
//            mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, index, 0);
//            if(index<=0){
//                mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0);
//            }
//
//
//        }else {
//
//
//
//        }

    }



    public void setDataResource(String playUrl){
        try {
            player.setOnPreparedListener(IjkPlayerManager.getIntance(this));
            player.setDataSource(context, Uri.parse(playUrl));

            player.prepareAsync();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }


//    IMediaPlayer.OnPreparedListener mPreparedListener = new IMediaPlayer.OnPreparedListener() {
//        @Override
//        public void onPrepared(IMediaPlayer mp) {
//
//        }
//    };



    public android.os.Handler handler=new android.os.Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what){

                case CURRENT_STATE_PLAYING:

                    if(player.isPlaying()){
                        //Log.e("getCurrentPosition","getCurrentPosition"+player.getCurrentPosition());
                        timeline.setProgress((int) (100*player.getCurrentPosition()/player.getDuration()));
                        handler.sendEmptyMessageDelayed(CURRENT_STATE_PLAYING,1000);
                    }
                    break;


                case 1001:

                    break;

            }

        }
    };




    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.play:
                play();

                break;

            case R.id.fullscreen:
                setFullScreen();

                break;

            case R.id.replay:
                play();
                break;

            case R.id.container:


                break;
        }
    }


    public void setPlayStatus(){

        if (player.isPlaying()){
            player.pause();
        }

        if(play.getVisibility()==GONE){

            play.setVisibility(VISIBLE);
        }
    }


    public void play(){
        if(player.isPlaying()){
            handler.removeMessages(CURRENT_STATE_PLAYING);
            player.pause();

        }else {
            player.start();
            play.setVisibility(GONE);
            handler.sendEmptyMessage(CURRENT_STATE_PLAYING);

        }
    }


    private Activity activity;

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public  void setFullScreen(){

        if(activity.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//强制为横屏

            //setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
            RelativeLayout.LayoutParams layoutParams= (RelativeLayout.LayoutParams) getLayoutParams();
            layoutParams.height= RelativeLayout.LayoutParams.MATCH_PARENT;
            setLayoutParams(layoutParams);


        }else {
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏

            RelativeLayout.LayoutParams layoutParams= (RelativeLayout.LayoutParams) getLayoutParams();
            layoutParams.height= ScreenUtils.dip2px(context,200);
            setLayoutParams(layoutParams);

        }


    }


    public void release(){

        if(player.isPlaying()){
            player.pause();
        }
        player.release();
        IjkMediaPlayer.native_profileEnd();
        textureView=null;

    }




}
