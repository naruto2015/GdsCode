package com.gds.app.ui.myselfmvp;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by gaodesong on 18/1/29.
 */

public abstract class MvpActivity<V,P extends BasePresent<V>> extends BaseActivity{


    private P presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = initPresenter();
        presenter.attach((V) this);

    }

    @Override
    protected void onResume() {
        super.onResume();

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detach();
    }

    public abstract P initPresenter();


    public P getPresenter() {
        return presenter;
    }

    public void setPresenter(P presenter) {
        this.presenter = presenter;
    }



}
