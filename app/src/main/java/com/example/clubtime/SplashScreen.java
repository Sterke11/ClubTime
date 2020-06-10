package com.example.clubtime;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;

public class SplashScreen extends AppCompatActivity {

    ImageView ivLogo;
    ObjectAnimator a1, a2, a3;
    ValueAnimator a4;
    AnimatorSet bouncer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        //Ocultar la barra de accion, por que queda feo jiji
        getSupportActionBar().hide();

        ivLogo = findViewById(R.id.ivLogo);
        bouncer = new AnimatorSet();


        //OBTENEMOS DIMESIONES DE LA PANTALLA
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int ancho = metrics.widthPixels; // ancho absoluto en pixels
        int alto = metrics.heightPixels; // alto absoluto en pixels

        a1 = ObjectAnimator.ofFloat(ivLogo, "translationY", (float) (alto + alto/12));
        a1.setDuration(1500);

        a2 = ObjectAnimator.ofFloat(ivLogo, "translationY", (float) (alto - alto/24));
        a2.setDuration(1000);

        a4 = ObjectAnimator.ofFloat(ivLogo, "alpha", 1f, 0f);
        a4.setDuration(2000);


        bouncer.play(a1);
        bouncer.play(a2).after(a1);
        bouncer.play(a4).after(a2);

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent intent=new Intent(SplashScreen.this,LogIn.class);
//                startActivity(intent);
//                finish();
//
//            }
//        },2000);

    }
    @Override
    protected void onStart() {
        super.onStart();
        bouncer.start();
        bouncer.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                Intent intent = new Intent(SplashScreen.this,LogIn.class);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(SplashScreen.this).toBundle());
                //startActivity(intent);
                //finish();
            }

            @Override
            public void onAnimationRepeat(Animator animation) { super.onAnimationRepeat(animation); }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }

            @Override
            public void onAnimationPause(Animator animation) {
                super.onAnimationPause(animation);
            }

            @Override
            public void onAnimationResume(Animator animation) {
                super.onAnimationResume(animation);
            }
        });
    }
}