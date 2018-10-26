package com.example.android.safeline;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MyServicenoti extends Service {

    public Context context = this;
    public Handler handler = null;
    public static Runnable runnable = null;

    static boolean stop=false;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        //Toast.makeText(this, "Service created!", Toast.LENGTH_LONG).show();

        handler = new Handler();
        runnable = new Runnable() {
            public void run() {
                //      Toast.makeText(context, "Service is still running", Toast.LENGTH_LONG).show();
                check();
                handler.postDelayed(runnable, 10000);
            }
        };

        handler.postDelayed(runnable, 15000);
    }

    @Override
    public void onDestroy() {
        /* IF YOU WANT THIS SERVICE KILLED WITH THE APP THEN UNCOMMENT THE FOLLOWING LINE */
        //handler.removeCallbacks(runnable);
        //  Toast.makeText(this, "Service stopped", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStart(Intent intent, int startid) {
        //  Toast.makeText(this, "Service started by user.", Toast.LENGTH_LONG).show();
    }


    void check() {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("notify");
        // DatabaseReference myRef1 = myRef.child("cs");


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String msgid=dataSnapshot.getValue(String.class);
                SharedPreferences prefs = getSharedPreferences("msg",MODE_PRIVATE);
                String stopp = prefs.getString("stop", "false");

                assert msgid != null;

                {

                    SharedPreferences.Editor editor= getSharedPreferences("msg",MODE_PRIVATE).edit();
                    editor.putString("lastmsgid",msgid);
                    editor.apply();
                    editor.putString("stop","true");
                    editor.apply();



                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("ss", "Failed to read value.", error.toException());
            }
        });


    }







}





























