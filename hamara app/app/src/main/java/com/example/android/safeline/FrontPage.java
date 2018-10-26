package com.example.android.safeline;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class FrontPage extends AppCompatActivity {
    ImageView imageView;
    Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_front_page);
        imageView= findViewById(R.id.imagew);
        animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.alpha);

        imageView.setAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                finish();
                startActivity(new Intent(getApplicationContext(),MapsActivity.class));
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }
}
