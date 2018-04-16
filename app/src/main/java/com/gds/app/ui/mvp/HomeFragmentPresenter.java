package com.gds.app.ui.mvp;

import com.gds.app.bean.UserBean;
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

/**
 * Created by gaodesong on 18/1/12.
 */

public class HomeFragmentPresenter extends MvpBasePresenter<HomeFragmentView>{



    public void get(){

        final HomeFragmentView homeFragmentView=getView();
        if(homeFragmentView != null){
            homeFragmentView.onLoadStart();
            new android.os.Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    List<UserBean> list=new ArrayList<>();
                    UserBean userBean=new UserBean();
                    userBean.setName("二当家");
                    list.add(userBean);
                    homeFragmentView.onLoadListComplete(list);
                }
            },5000);
        }

    }



}
