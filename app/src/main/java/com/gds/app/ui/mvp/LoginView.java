package com.gds.app.ui.mvp;

import com.gds.app.bean.UserBean;
import com.hannesdorfmann.mosby3.mvp.MvpView;

/**
 * Created by gaodesong on 18/1/12.
 */

public interface LoginView extends MvpView{

    void onLoginStart();

    void onLoadUserComplete(UserBean userBean);
}
