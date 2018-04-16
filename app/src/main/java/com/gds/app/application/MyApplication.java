package com.gds.app.application;

import android.app.Application;

import com.gds.app.greendao.DBHelper;
import com.greendao.gen.DaoMaster;
import com.greendao.gen.DaoSession;

import org.greenrobot.greendao.AbstractDaoMaster;

/**
 * Created by gaodesong on 18/1/23.
 */

public class MyApplication extends Application{



    public static MyApplication instances;

    public DaoSession mDaoSession;

    @Override
    public void onCreate() {
        super.onCreate();

        instances = this;
        DBHelper devOpenHelper = new DBHelper(this);
        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDb());
        mDaoSession = daoMaster.newSession();




    }



}
