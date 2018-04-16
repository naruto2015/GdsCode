package com.gds.app.ui.mvp;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.gds.app.R;
import com.gds.app.bean.UserBean;
import com.hannesdorfmann.mosby3.mvp.MvpActivity;


public class LoginMvpActivity extends MvpActivity<LoginView,LoginPresenter>  implements LoginView{


    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_mvp);

    }


    @NonNull
    @Override
    public LoginPresenter createPresenter() {
        return new LoginPresenter();
    }


    @Override
    public void onLoginStart() {
        progressDialog=new ProgressDialog(LoginMvpActivity.this);
        progressDialog.show();
    }

    @Override
    public void onLoadUserComplete(UserBean userBean) {
        progressDialog.dismiss();
        Log.e("onLoadUserComplete",userBean.getName());

    }

    public void play(View view) {
        getPresenter().getfromNet();
    }
}
