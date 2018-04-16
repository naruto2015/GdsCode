package com.gds.app.ui.mvp;

import com.gds.app.bean.UserBean;
import com.hannesdorfmann.mosby3.mvp.MvpView;

import java.util.List;

/**
 * Created by gaodesong on 18/1/12.
 */

public interface HomeFragmentView extends MvpView {


    void onLoadStart();

    void onLoadListComplete(List<UserBean> userBeans);

}
