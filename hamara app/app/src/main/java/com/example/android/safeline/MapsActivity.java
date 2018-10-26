package com.example.android.safeline;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomButtons.TextOutsideCircleButton;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.ButtonEnum;
import com.nightonke.boommenu.Piece.PiecePlaceEnum;
import com.victor.loading.rotate.RotateLoading;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    String TAG="MAPSREPORT";
    private ArrayList<String> GPS;
    private ArrayList<String> PROB;
    private ArrayList<String> DESC;
    private BoomMenuButton bmb;

    //   private ArrayList<String> ATTENDANCE;
    private ArrayList<String> FIRED;
    private ArrayList<String> GPSS;



    private RotateLoading rotateLoading;
    int pos=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Button emergency = (Button) findViewById(R.id.but);

        rotateLoading=findViewById(R.id.rotateloading);
        rotateLoading.setLoadingColor(R.color.colorPrimary);

        sendNotification();

       // startService(new Intent(getBaseContext(), MyServicenoti.class));


        final RelativeLayout rl=findViewById(R.id.rl);

        emergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent smsIntent = new Intent(android.content.Intent.ACTION_VIEW);
                smsIntent.setType("vnd.android-dir/mms-sms");
                smsIntent.putExtra("address", "8860400706");
                smsIntent.putExtra("sms_body", "your desired message");
                startActivity(smsIntent);

            }
        });

//      emergency.setOnClickListener(new View.OnClickListener() {
//          @Override
//          public void onClick(View view) {
//              Uri uri = Uri.parse("sms to: 8860400706");
//              Intent it = new Intent(Intent.ACTION_SENDTO, uri);
//              it.putExtra("sms_body", "The SMS text");
//              startActivity(it);
//
//          }
//      });
//



        rotateLoading.start();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);







        bmb = (BoomMenuButton) findViewById(R.id.bmb);
        assert bmb != null;
        bmb.setButtonEnum(ButtonEnum.TextOutsideCircle);
        bmb.setPiecePlaceEnum(PiecePlaceEnum.DOT_4_2);
        bmb.setButtonPlaceEnum(ButtonPlaceEnum.SC_4_2);
        Log.e("ppp", ""+bmb.getPiecePlaceEnum().pieceNumber());

        final Uri[] call = new Uri[1];
        for (int i = 0; i < bmb.getPiecePlaceEnum().pieceNumber(); i++) {
            TextOutsideCircleButton.Builder builder = new TextOutsideCircleButton.Builder()
                    .listener(new OnBMClickListener() {
                        @Override
                        public void onBoomButtonClick(int index) {
                            // When the boom-button corresponding this builder is clicked.
                            if(index==0)
                                call[0] =Uri.parse("tel:"+"100");
                            else if (index==1)call[0] =Uri.parse("tel:"+"102");
                            else if (index==2)call[0] =Uri.parse("tel:"+"8860400706");
                            else if (index==3)call[0] =Uri.parse("tel:"+"181");

                            Intent surf=new Intent(Intent.ACTION_DIAL, call[0]);
                            startActivity(surf);

                        }
                    })

                    .normalImageRes(BuilderManager.getImageResource())
                    .normalText(BuilderManager.getImageText())
                    .pieceColor(Color.WHITE).normalColor(Color.WHITE);


            bmb.addBuilder(builder);
        }











    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;





        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("PROBLEMS");

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GPS=new ArrayList<>();
                PROB=new ArrayList<>();
                DESC=new ArrayList<>();
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                {  for (DataSnapshot dataSnapshot2:dataSnapshot1.getChildren())
                {if (dataSnapshot2.getKey().equals("GPS")) {
                    Log.e("GPSSSSSSS", String.valueOf(dataSnapshot2.getValue()));
                    GPS.add(String.valueOf(dataSnapshot2.getValue()));


                }
                    if (dataSnapshot2.getKey().equals("Prob")) {
                        Log.e("GPSSSSSSS", String.valueOf(dataSnapshot2.getValue()));
                        PROB.add(String.valueOf(dataSnapshot2.getValue()));


                    }
                    if (dataSnapshot2.getKey().equals("Description")) {
                        Log.e("GPSSSSSSS", String.valueOf(dataSnapshot2.getValue()));
                        DESC.add(String.valueOf(dataSnapshot2.getValue()));


                    }
                }

                }
                for (int i=0;i<GPS.size();i++)
                {  String latlong[]=GPS.get(i).split(",");
                    double lat= Double.parseDouble(latlong[0]);
                    double longi= Double.parseDouble(latlong[1]);
                    LatLng sydney = new LatLng(lat,longi);

                    mMap.addMarker(new MarkerOptions().position(sydney).title(PROB.get(i)).snippet(DESC.get(i)).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_stat_name)));
                }
                rotateLoading.stop();

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });















        // Add a marker in Sydney and move the camera
        //   LatLng sydney1 = new LatLng(MainActivity.lat, MainActivity.longi);
        LatLng sydney1 = new LatLng(28.6644,77.2323);

        //      LatLng sydney2 = new LatLng(18.5512821,73.8229848);
        LatLng sydney3 = new LatLng(28.6775104,77.1329534);
        LatLng sydney4 = new LatLng(28.6795104,77.1429534);
        LatLng sydney5 = new LatLng(28.6815104,77.1529534);
        LatLng sydney6 = new LatLng(28.6735104,77.1629534);
        LatLng sydney7 = new LatLng(28.6785104,77.1729534);
        LatLng sydney8 = new LatLng(28.6755804,77.1829534);
        LatLng sydney9 = new LatLng(40.741897,-73.989309);
        LatLng sydney61 = new LatLng(40.741891,-73.989304);
        LatLng sydney71 = new LatLng(40.741895,-73.989308);
        LatLng sydney81 = new LatLng(28.6755804,77.2229534);
        LatLng sydney91 = new LatLng(28.6955104,77.2329534);
        LatLng sydney611 = new LatLng(28.7735104,77.1929534);
        LatLng sydney711 = new LatLng(28.7185104,77.1829534);
        LatLng sydney811 = new LatLng(28.7255804,77.1229534);
        LatLng sydney911 = new LatLng(28.7355104,77.1329534);
        LatLng sydney6111 = new LatLng(28.7435104,77.1029534);
        LatLng sydney7111 = new LatLng(28.7585104,77.1129534);
        LatLng sydney8111 = new LatLng(28.7655804,77.1929534);
        LatLng sydney9111 = new LatLng(28.7855104,77.1929534);

        //      mMap.addMarker(new MarkerOptions().position(sydney2).title("PROBLEM").snippet("PROBLEM").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_stat_name)));
        mMap.addMarker(new MarkerOptions().position(sydney3).title("PROBLEM").snippet("PROBLEM").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_stat_name)));
        mMap.addMarker(new MarkerOptions().position(sydney4).title("PROBLEM").snippet("PROBLEM").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_stat_name)));
        mMap.addMarker(new MarkerOptions().position(sydney5).title("PROBLEM").snippet("PROBLEM").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_stat_name)));
        mMap.addMarker(new MarkerOptions().position(sydney6).title("PROBLEM").snippet("PROBLEM").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_stat_name)));
        mMap.addMarker(new MarkerOptions().position(sydney7).title("PROBLEM").snippet("PROBLEM").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_stat_name)));
        mMap.addMarker(new MarkerOptions().position(sydney8).title("PROBLEM").snippet("PROBLEM").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_stat_name)));
        mMap.addMarker(new MarkerOptions().position(sydney9).title("PROBLEM").snippet("PROBLEM").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_stat_name)));
        mMap.addMarker(new MarkerOptions().position(sydney61).title("PROBLEM").snippet("PROBLEM").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_stat_name)));
        mMap.addMarker(new MarkerOptions().position(sydney71).title("PROBLEM").snippet("PROBLEM").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_stat_name)));
        mMap.addMarker(new MarkerOptions().position(sydney81).title("PROBLEM").snippet("PROBLEM").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_stat_name)));
        mMap.addMarker(new MarkerOptions().position(sydney91).title("PROBLEM").snippet("PROBLEM").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_stat_name)));
        mMap.addMarker(new MarkerOptions().position(sydney611).title("PROBLEM").snippet("PROBLEM").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_stat_name)));
        mMap.addMarker(new MarkerOptions().position(sydney711).title("PROBLEM").snippet("PROBLEM").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_stat_name)));
        mMap.addMarker(new MarkerOptions().position(sydney811).title("PROBLEM").snippet("PROBLEM").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_stat_name)));
        mMap.addMarker(new MarkerOptions().position(sydney911).title("PROBLEM").snippet("PROBLEM").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_stat_name)));
        mMap.addMarker(new MarkerOptions().position(sydney6111).title("PROBLEM").snippet("PROBLEM").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_stat_name)));
        mMap.addMarker(new MarkerOptions().position(sydney7111).title("PROBLEM").snippet("PROBLEM").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_stat_name)));
        mMap.addMarker(new MarkerOptions().position(sydney8111).title("PROBLEM").snippet("PROBLEM").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_stat_name)));
        mMap.addMarker(new MarkerOptions().position(sydney9111).title("PROBLEM").snippet("PROBLEM").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_stat_name)));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney1,15));
    }




    private void sendNotification() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        String channelId = getString(R.string.default_notification_channel_id);
        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.drawable.w)
                        .setContentTitle("Emergency detected")
                        .setContentText("The problem is detected near your location. You should help!")
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            assert notificationManager != null;
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
        //stop=true;
    }








}

