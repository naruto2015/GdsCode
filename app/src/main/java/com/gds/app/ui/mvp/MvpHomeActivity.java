package com.gds.app.ui.mvp;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.gds.app.R;

public class MvpHomeActivity extends FragmentActivity{


    private HomeFragment homeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvp_home);

        FragmentManager fm=getSupportFragmentManager();
        homeFragment= (HomeFragment) fm.findFragmentById(R.id.framelayout);

        if(homeFragment==null){
            homeFragment = new HomeFragment();
            fm.beginTransaction().add(R.id.framelayout,homeFragment).commit();
        }





    }



}
