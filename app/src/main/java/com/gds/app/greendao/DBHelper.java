package com.gds.app.greendao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.greendao.gen.DaoMaster;

import org.greenrobot.greendao.AbstractDaoMaster;
import org.greenrobot.greendao.database.Database;

/**
 * Created by gaodesong on 18/1/23.
 */

public class DBHelper extends DaoMaster.DevOpenHelper{

    public static final String DBNAME = "gds_test.db";

    public DBHelper(Context context) {
        super(context, DBNAME,null);
    }


    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
    }
}
