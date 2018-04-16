package com.gds.app.ui.myselfmvp;

import java.util.logging.Handler;

/**
 * Created by gaodesong on 18/1/29.
 */

public class HomePresenter extends BasePresent<HomeView>{


    public void  getFromNet(){
        if(view!=null){
            view.log("test");
        }

//        new android.os.Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                if(view != null){
//
//                }
//
//            }
//        },200);

    }
}
