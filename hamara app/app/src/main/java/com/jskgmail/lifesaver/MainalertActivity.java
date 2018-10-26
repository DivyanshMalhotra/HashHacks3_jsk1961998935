package com.jskgmail.lifesaver;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainalertActivity extends AppCompatActivity {

    private static final int CAMERA_REQUEST = 50;
    private boolean flashLightStatus = false;
    MediaPlayer alert;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainalert);
        Button yes = (Button) findViewById(R.id.button2);
        Button no = (Button) findViewById(R.id.button3);



        final boolean hasCameraFlash = getPackageManager().
                hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
        boolean isEnabled = ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED;


                ActivityCompat.requestPermissions(MainalertActivity.this, new String[] {android.Manifest.permission.CAMERA}, CAMERA_REQUEST);



        final ImageButton alarm = (ImageButton) findViewById(R.id.imageButton);
      alert  = MediaPlayer.create(MainalertActivity.this, R.raw.siren);
        final int[] i = {120};
        alert.start();
        final TextView time = (TextView) findViewById(R.id.textView12);
      final CountDownTimer countDownTimer=  new CountDownTimer(6000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {


                time.setText(i[0] + "s");
                i[0]--;
                if(i[0]%2==0)
                flashLightOn();
                else if(i[0]>10) flashLightOff(); else flashLightOff();
            }

            @Override
            public void onFinish() {

                if(MainActivity.flood.equals("1"))
                go();
            }
        }.start();
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ambulance();
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef1 = database.getReference("PROBLEMS");
                DatabaseReference myRef=myRef1.push();
                myRef.child("GPS").setValue("28.6909564,77.2143421999992");
                myRef.child("Prob").setValue("App based emergency!");
                myRef.child("Description").setValue("app reported prob!");


            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.flood = "0";
                SharedPreferences.Editor editor=getSharedPreferences("flood",MODE_PRIVATE).edit();
                editor.putString("flood","0");

                editor.apply();
                countDownTimer.cancel();
                alert.stop();
                flashLightOff();


                finish();
            }

        });


        alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (alert.isPlaying()) {
                    alert.stop();
                    alarm.setImageResource(R.drawable.ic_volume_up_black_24dp);
                } else {
                    alert.start();
                    alarm.setImageResource(R.drawable.ic_volume_off_black_24dp);
                }
            }
        });











    }

    void go() {
        MainActivity.flood = "0";
        SharedPreferences.Editor editor = getSharedPreferences("flood", MODE_PRIVATE).edit();
        editor.putString("flood", "0");
        editor.apply();

        SharedPreferences preference = getSharedPreferences("emergency", MODE_PRIVATE);
        String phno = preference.getString("mob", null);
        String numbers = "";

        if (phno != null) {

            String[] no = phno.split(",");
            for (int i = 0; i < no.length; i++) {
                numbers = numbers + no[i] + ";";

            }
        }



        finish();


    }

    void ambulance()
    {String[] em=MainActivity.emergencyno.split(",");
        Uri call=Uri.parse("tel:"+em[0]);
        Intent surf=new Intent(Intent.ACTION_DIAL,call);
        startActivity(surf);
    }





    private void flashLightOn() {
        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);

        try {
            String cameraId = cameraManager.getCameraIdList()[0];
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                cameraManager.setTorchMode(cameraId, true);
            }
            flashLightStatus = true;

        } catch (CameraAccessException e) {
        }
    }

    private void flashLightOff() {
        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);

        try {
            String cameraId = cameraManager.getCameraIdList()[0];
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                cameraManager.setTorchMode(cameraId, false);
            }
            flashLightStatus = false;

        } catch (CameraAccessException e) {
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode) {
            case CAMERA_REQUEST :
                if (grantResults.length > 0  &&  grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    }
                break;
        }
    }









}
