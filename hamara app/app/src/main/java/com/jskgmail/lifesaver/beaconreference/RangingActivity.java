package com.jskgmail.lifesaver.beaconreference;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jetradar.desertplaceholder.DesertPlaceholder;
import com.jskgmail.lifesaver.R;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

import java.util.Collection;

public class RangingActivity extends AppCompatActivity implements BeaconConsumer {
    protected static final String TAG = "RangingActivity";
    private BeaconManager beaconManager = BeaconManager.getInstanceForApplication(this);
    MediaPlayer alert;
    float last=0;
    DesertPlaceholder desertPlaceholder;
    RelativeLayout rl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranging);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        actionBar.setDisplayShowHomeEnabled(true);
rl=findViewById(R.id.rl);
         desertPlaceholder= (DesertPlaceholder) findViewById(R.id.placeholder);
        desertPlaceholder.setOnButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // do stuff

            }
        });
desertPlaceholder.setVisibility(View.VISIBLE);
        Intent turnOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(turnOn, 0);
        rl.setVisibility(View.INVISIBLE);
        alert = MediaPlayer.create(RangingActivity.this, R.raw.beep);


        beaconManager.bind(this);


    }

    @Override 
    protected void onDestroy() {
        super.onDestroy();
        beaconManager.unbind(this);

    }



    @Override 
    protected void onResume() {
        super.onResume();
        if (beaconManager.isBound(this)) beaconManager.setBackgroundMode(false);
    }

    @Override
    public void onBeaconServiceConnect() {
        beaconManager.setRangeNotifier(new RangeNotifier() {
           @Override
           public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
              if (beacons.size() > 0) {
                 //EditText editText = (EditText)RangingActivity.this.findViewById(R.id.rangingText);
                 Beacon firstBeacon = beacons.iterator().next();


                  logToDisplay( firstBeacon.toString(), firstBeacon.getDistance());

logToDisplayd(firstBeacon.getDistance());





              }
           }

        });

        try {
            beaconManager.startRangingBeaconsInRegion(new Region("myRangingUniqueId", null, null, null));
        } catch (RemoteException e) {   }
    }

    private void logToDisplay(final String beacon, final double dist) {
        runOnUiThread(new Runnable() {
            public void run() {
                rl.setVisibility(View.VISIBLE);
                TextView id = (TextView) RangingActivity.this.findViewById(R.id.rangingtext);
                TextView dista = (TextView) RangingActivity.this.findViewById(R.id.rangingtext3);
                id.setText(beacon.replace("type altbeacon","").replace("id2:","\nid2:").replace("id3:","\nid3:"));
                dista.setText(""+(float)dist+" metres");

            }
        });
    }
    private void logToDisplayd(final double line) {
        runOnUiThread(new Runnable() {
            public void run() {

                TextView cm=(TextView)RangingActivity.this.findViewById(R.id.cm);
                cm.setText(" "+(float)line +" m ");
              if ((float)line<=last)
              { alert.reset();
                alert.release();
                alert = MediaPlayer.create(RangingActivity.this, R.raw.beep);
                alert.start();}
                else {
                  alert.reset();
                  alert.release();
                  alert = MediaPlayer.create(RangingActivity.this, R.raw.beepe);
                  alert.start();

              }


                last=(float)line;
                desertPlaceholder.setVisibility(View.GONE);

            }
        });



    }

}
