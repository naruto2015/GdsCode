package com.gds.app.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.gds.app.R;
import com.gds.app.application.MyApplication;
import com.gds.app.bean.UserBean;
import com.greendao.gen.UserBeanDao;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class HeartLineActivity extends AppCompatActivity {


    private UserBeanDao userBeanDao;

    private int i=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heart_line);

        ButterKnife.bind(this);
        userBeanDao=MyApplication.instances.mDaoSession.getUserBeanDao();


    }



    @OnClick(R.id.insert)
    public void insert(){
        try {
            UserBean userBean=new UserBean();
            userBean.setId(i);
            userBean.setSex("男");
            userBean.setTestUni("111");
            i++;
            userBeanDao.insert(userBean);
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    @OnClick(R.id.query)
    public void query(){
      List<UserBean> userBeans=userBeanDao.loadAll();
    }

}
