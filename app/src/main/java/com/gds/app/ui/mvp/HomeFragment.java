package com.gds.app.ui.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.gds.app.R;
import com.gds.app.bean.UserBean;
import com.hannesdorfmann.mosby3.mvp.MvpFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by gaodesong on 18/1/12.
 */

public class HomeFragment extends MvpFragment<HomeFragmentView,HomeFragmentPresenter> implements HomeFragmentView{


    private Unbinder unbinder;

    @BindView(R.id.play)
    Button play;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {



        View view=inflater.inflate(R.layout.home_fragment,container,false);
        unbinder= ButterKnife.bind(this,view);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getPresenter().get();
            }
        });


        return view;
    }



    @Override
    public void onLoadStart() {

    }

    @Override
    public void onLoadListComplete(List<UserBean> userBeans) {
        Log.e("HomeFragment",userBeans.get(0).getName());
    }

    @Override
    public HomeFragmentPresenter createPresenter() {
        return new HomeFragmentPresenter();
    }



}
