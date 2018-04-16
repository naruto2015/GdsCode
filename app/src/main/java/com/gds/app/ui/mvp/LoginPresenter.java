package com.gds.app.ui.mvp;

import com.gds.app.bean.UserBean;
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import java.util.logging.Handler;

/**
 * Created by gaodesong on 18/1/12.
 */

public class LoginPresenter extends MvpBasePresenter<LoginView>{



    public void getfromNet(){

        final LoginView loginView=getView();
        if(loginView!=null){
            loginView.onLoginStart();
            new android.os.Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    UserBean userBean=new UserBean();
                    userBean.setName("gds");
                    loginView.onLoadUserComplete(userBean);

                }
            },5000);
        }





    }
}
