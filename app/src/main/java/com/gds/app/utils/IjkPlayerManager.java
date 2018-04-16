package com.gds.app.utils;

import android.util.Log;

import com.gds.app.view.SampleVideoView;

import tv.danmaku.ijk.media.player.IMediaPlayer;

/**
 * Created by gaodesong on 17/12/14.
 */

public class IjkPlayerManager implements IMediaPlayer.OnPreparedListener, IMediaPlayer.OnCompletionListener,
        IMediaPlayer.OnBufferingUpdateListener, IMediaPlayer.OnSeekCompleteListener, IMediaPlayer.OnErrorListener,
        IMediaPlayer.OnVideoSizeChangedListener, IMediaPlayer.OnInfoListener {




    private  static IjkPlayerManager ijkPlayerManager;

    private SampleVideoView sampleVideoView;

    public static IjkPlayerManager getIntance(SampleVideoView sampleVideoView){
        if(ijkPlayerManager==null){
            synchronized (IjkPlayerManager.class){
                if(ijkPlayerManager==null){
                    ijkPlayerManager=new IjkPlayerManager(sampleVideoView);
                }
            }
        }


        return ijkPlayerManager;
    }

    public IjkPlayerManager(SampleVideoView sampleVideoView){
        this.sampleVideoView=sampleVideoView;
    }

    public IjkPlayerManager() {

    }


    @Override
    public void onBufferingUpdate(IMediaPlayer iMediaPlayer, int i) {
        Log.e("onBufferingUpdate","getCurrentPosition"+iMediaPlayer.getCurrentPosition()+
                "getDuration"+iMediaPlayer.getDuration());
    }

    @Override
    public void onCompletion(IMediaPlayer iMediaPlayer) {
        Log.e("onCompletion","");
    }

    @Override
    public boolean onError(IMediaPlayer iMediaPlayer, int i, int i1) {
        return false;
    }

    @Override
    public boolean onInfo(IMediaPlayer iMediaPlayer, int i, int i1) {
        return false;
    }

    @Override
    public void onPrepared(IMediaPlayer iMediaPlayer) {
        Log.e("onPrepared","播放准备");
        sampleVideoView.handler.sendEmptyMessageDelayed(SampleVideoView.CURRENT_STATE_PLAYING,1000);

    }

    @Override
    public void onSeekComplete(IMediaPlayer iMediaPlayer) {
        Log.e("onSeekComplete","");
    }

    @Override
    public void onVideoSizeChanged(IMediaPlayer iMediaPlayer, int i, int i1, int i2, int i3) {

    }




}
