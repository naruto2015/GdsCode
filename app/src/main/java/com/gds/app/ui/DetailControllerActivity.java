package com.gds.app.ui;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import com.gds.app.R;
import com.gds.app.view.SampleVideoView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailControllerActivity extends AppCompatActivity {


    @BindView(R.id.sampleView)
    SampleVideoView sampleView;

    private String source_url = "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4";


    private boolean isPause=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_controller);

        ButterKnife.bind(this);

        sampleView.setDataResource(source_url);
        sampleView.setActivity(this);

    }


    private void loadImage(){


        MediaMetadataRetriever mmr=new MediaMetadataRetriever();
        mmr.setDataSource(source_url);
        Bitmap bitmap=mmr.getFrameAtTime();






    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.e("onConfigurationChanged",""+newConfig.orientation);
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            WindowManager.LayoutParams attrs = getWindow().getAttributes();
            attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
            getWindow().setAttributes(attrs);
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        } else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            WindowManager.LayoutParams attrs = getWindow().getAttributes();
            attrs.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().setAttributes(attrs);
            getWindow().clearFlags(
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        sampleView.player.pause();
        isPause=true;
    }


    @Override
    protected void onResume() {
        super.onResume();

        if(isPause){
            sampleView.player.start();
            isPause=false;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sampleView.release();
    }





    public  void setFullScreen(){

        if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//强制为横屏
        }else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏
        }


    }





}
