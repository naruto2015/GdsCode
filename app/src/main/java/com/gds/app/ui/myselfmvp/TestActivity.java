package com.gds.app.ui.myselfmvp;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;

import com.gds.app.R;
import com.gds.app.view.PageTurnView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TestActivity extends MvpActivity<HomeView,HomePresenter> implements HomeView{


    @BindView(R.id.page)
    PageTurnView page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);
        getPresenter().getFromNet();

        List<Bitmap> mBitmaps=new ArrayList<>();
        mBitmaps.add(BitmapFactory.decodeResource(getResources(),R.mipmap.timg));
        mBitmaps.add(BitmapFactory.decodeResource(getResources(),R.mipmap.timg2));
        mBitmaps.add(BitmapFactory.decodeResource(getResources(),R.mipmap.test));

        page.setBitmaps(mBitmaps);


    }

    @Override
    public HomePresenter initPresenter() {
        return new HomePresenter();
    }


    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void log(String text) {

        Log.e("log",text);
    }
}
