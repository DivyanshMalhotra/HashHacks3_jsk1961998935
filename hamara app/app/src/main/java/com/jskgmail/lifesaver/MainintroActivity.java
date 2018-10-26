package com.jskgmail.lifesaver;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

public class MainintroActivity extends AppIntro {

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        SharedPreferences prefs = getSharedPreferences("app",MODE_PRIVATE);
        String firsttime = prefs.getString("intro", "1");
        if (firsttime.equals("2")) {
            Intent i = new Intent(MainintroActivity.this, Main22Activity.class);
            startActivity(i);
        }
        else
        { askForPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 2); // OR
      //  askForPermissions(new String[]{Manifest.permission.SEND_SMS, Manifest.permission.CALL_PHONE, Manifest.permission.READ_CONTACTS}, 3);
        // Note here that we DO NOT use setContentView();

        // Add your slide fragments here.
        // AppIntro will automatically generate the dots indicator and buttons.
setFadeAnimation();

        // Instead of fragments, you can also use our default slide
        // Just set a title, description, background and image. AppIntro will do the rest.
        addSlide(AppIntroFragment.newInstance("WELCOME !", "The 24/7 bodyguard that protects and saves you from any mishap ", R.drawable.shield, Color.parseColor("#10a7ff")));
            addSlide(AppIntroFragment.newInstance("Empowering Women", "Just shake the phone during danger after enabling the mode", R.drawable.women, Color.parseColor("#10a7ff")));

        // OPTIONAL METHODS        // Override bar/separator color.

        setBarColor(Color.parseColor("#3F51B5"));
        setSeparatorColor(Color.parseColor("#2196F3"));
setBackButtonVisibilityWithDone(true);
        // Hide Skip/Done button.
        showSkipButton(true);
        setProgressButtonEnabled(true);
setNextArrowColor(R.color.colorred);
setGoBackLock(false);

        // Turn vibration on and set intensity.
        // NOTE: you will probably need to ask VIBRATE permission in Manifest.
        setVibrate(true);
        setVibrateIntensity(30);

        // OPTIONAL METHODS

    }


    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        // Do something when users tap on Skip button.
        Intent i=new Intent(MainintroActivity.this,Main22Activity.class);
        startActivity(i);
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);

        Intent i=new Intent(MainintroActivity.this,Main22Activity.class);
        startActivity(i);
        SharedPreferences.Editor editor= getSharedPreferences("app",MODE_PRIVATE).edit();
        editor.putString("intro","2");

        editor.apply();


        // Do something when users tap on Done button.
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
        // Do something when the slide changes.
    }





}
