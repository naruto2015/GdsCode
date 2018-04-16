package com.gds.app.ui;

import android.support.animation.SpringAnimation;
import android.support.animation.SpringForce;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.gds.app.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {



    @BindView(R.id.img)
    ImageView img;

    SpringAnimation anim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        SpringForce springForce=new SpringForce(0)
                .setDampingRatio(0.05f)
                .setStiffness(SpringForce.STIFFNESS_LOW);
        anim=new SpringAnimation(img,SpringAnimation.TRANSLATION_Y,0)
                .setSpring(springForce);


    }




    @Override
    protected void onStop() {
        super.onStop();

    }


    public void play(View view) {

        anim.cancel();
        anim.setStartValue(-700);
        anim.start();

    }



}
