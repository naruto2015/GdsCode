package com.gds.app.ui.myselfmvp;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by gaodesong on 18/1/29.
 */

public abstract class MvpFragment<V,P extends BasePresent<V>> extends BaseFragment{


    private P presenter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter=getPresenter();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detach();

    }


    @Override
    public void onResume() {
        super.onResume();
        presenter.attach((V) this);

    }


    public abstract P initPresenter();


    public P getPresenter() {
        return presenter;
    }

    public void setPresenter(P presenter) {
        this.presenter = presenter;
    }




}
