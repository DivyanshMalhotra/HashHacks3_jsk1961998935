package com.jskgmail.lifesaver;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.jskgmail.lifesaver.beaconreference.BeaconTransmitterActivity1;

/**
 * Created by TutorialsPoint7 on 8/23/2016.
 */

public class MyService extends Service implements SensorEventListener {

    private Sensor senAccelerometer;
    private Sensor TemperatureSensor;
    private SensorManager senSensorManager;
    float lastx=0,lasty=0,lastz=0;
    static float myaccelerometer=4000;
    long lastupdate = System.currentTimeMillis();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    public MyService()
    {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Let it continue running until it is stopped.
        Log.e("myservice","Ongoing");


        final String TAG="service";



        senSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        senAccelerometer = senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        senSensorManager.registerListener(this, senAccelerometer , SensorManager.SENSOR_DELAY_NORMAL);
        TemperatureSensor = senSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        senSensorManager.registerListener(this,
                TemperatureSensor,
                SensorManager.SENSOR_DELAY_NORMAL);











        SharedPreferences prefs = getSharedPreferences("flood",MODE_PRIVATE);
        String emer = prefs.getString("flood", "");


        if(emer.equals("1"))
        {    Intent i=new Intent(MyService.this,MainalertActivity.class);
            startActivity(i);
            onDestroy();}






        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


















    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor mySensor = sensorEvent.sensor;

        if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];

            long curTime = System.currentTimeMillis();

            long diffTime = (curTime - lastupdate);

            if ((curTime - lastupdate) > 100) {



                float speed = Math.abs(x + y + z - lastx - lasty - lastz)/ diffTime * 10000;
                Log.v("speeed",speed+"" );

                if (speed > myaccelerometer) {
//TODO alert
                    MainActivity.flood = "1";
                    SharedPreferences.Editor editor = getSharedPreferences("flood", MODE_PRIVATE).edit();
                    editor.putString("flood", "1");
                    editor.apply();
                    Intent intent=new Intent(MyService.this,BeaconTransmitterActivity1.class);
                    startActivity(intent);
                }

                lastx = x;
                lasty = y;
                lastz = z;




                lastupdate = curTime;
            }
        }

        if(sensorEvent.sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE){
            Log.e("temp",sensorEvent.values[0]+"");
            if (sensorEvent.values[0]>50){ MainActivity.flood = "1";
                SharedPreferences.Editor editor = getSharedPreferences("flood", MODE_PRIVATE).edit();
                editor.putString("flood", "1");
                editor.apply();}
        }










    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        Log.e("accuracyyy",""+accuracy);
    }

























}