package com.jskgmail.lifesaver;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import ru.dimorinny.floatingtextbutton.FloatingTextButton;

public class MainActivity extends AppCompatActivity
        implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        com.google.android.gms.location.LocationListener, SensorEventListener
    {
    ;
    static    String lastid;

    private static final int RESULT_PICK_CONTACT = 85;
    private ArrayList<String> stringArrayList, stringArrayList1;
    ListViewAdfrlist adapter;
    static String phon="",naam="";
    static String myLocation;
    static double mylocationa=28;
    static double myLocationb=77;int ch=0;
    static String bloodloc;
    public static String hospname="";
        public static String bbname="";
    static String ZIP="N.A.";
   static String hosp="";
    public static String bloodno="";
    String TAG = "taggg";
    static double lat=39.7,longi=-104;
    static String hospp="No Hospital Found",hospp1="N.A.",hospp11="N.A.";
    public static String latlong = "";//the realtime latitude longitude parameter
    private final static String API_KEY = "AIzaSyClHbZ-x92EYceOWKDSgT0NPZEBBEa_wnU";
    private final static String API_KEY1 = "AIzaSyCGZpTkUUlIYjYuJNOZMJKA6Ar4d7fE7Dc";
    double eleva = 0;
    static String latitude="28.620660,77.08127";
    public static double diffelevation = 0;
    File image = null;

    private GoogleApiClient mGoogleApiClient;
    private Location mLocation;
    private LocationManager locationManager;
    private LocationRequest mLocationRequest;

    String mCurrentPhotoPath;
    private Sensor senAccelerometer;
    private SensorManager senSensorManager;
    float lastx=0,lasty=0,lastz=0;
    long lastupdate = System.currentTimeMillis();
    public static String emergencyno="";
    public static String flood="0";
ListView l;
RelativeLayout rl;

    private SensorManager mSensorManager;
    private Sensor TemperatureSensor;
    private String API_KEYpin="AIzaSyBCy3Ghs09Bk0YULL2SmI-F5yXTJ6KJCWg";
    File photoFile = null;
    String responseddd;
        private StorageReference mStorageRef;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

lastid= Settings.Secure.getString(this.getContentResolver(),
                Settings.Secure.ANDROID_ID);



        startService(new Intent(getBaseContext(), MyServicenoti.class));

        startService();

        SharedPreferences prefs1 = getSharedPreferences("app",MODE_PRIVATE);
        String firsttime = prefs1.getString("intro1", "1");
        if (firsttime.equals("1"))
     //   appstarter1();


        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        locationManager = (LocationManager) getSystemService(getApplicationContext().LOCATION_SERVICE);

        int RC_LOCATION = ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION);

        String[] perms = { android.Manifest.permission.ACCESS_FINE_LOCATION,  };
        if(RC_LOCATION!=0)
            if (EasyPermissions.somePermissionPermanentlyDenied(this, Arrays.asList(perms))) {
                new AppSettingsDialog.Builder(this).build().show();
            }





      MediaPlayer  alert = MediaPlayer.create(MainActivity.this, R.raw.beepe);
        alert.reset();
        alert.release();

        MediaPlayer  alert1 = MediaPlayer.create(MainActivity.this, R.raw.beep);
        alert1.reset();
        alert1.release();





















startActivity(new Intent(MainActivity.this,MapsActivity.class));

finish();





    }






    @Override
    public void onConnected(Bundle bundle) {


        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLocation == null) {

            startLocationUpdates1();



        }
        if (mLocation != null) {
            double latitude = mLocation.getLatitude();
            double longitude = mLocation.getLongitude();

            latlong=latitude+","+longitude;
            lat=latitude;
            longi=longitude;
            //startService();
            startLocationUpdates();

        } else {
            Toast.makeText(this, "Location not Detected", Toast.LENGTH_SHORT).show();
        }
    }









    public void startService() {
        startService(new Intent(getBaseContext(), MyService.class));



    }














    protected void startLocationUpdates1() {

        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(100000)
                .setFastestInterval(10000);
        // Request location updates

        if (ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.










            return;
        }

        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, MainActivity.this);
        Log.d("reque", "--->>>>");


        finish();






    }











































































    protected void startLocationUpdates() {
        DatabaseFriend db = new DatabaseFriend(getApplicationContext());
        List<Friends> contacts = db.getAllContacts();
        String myno="98";
        for (Friends cn : contacts) {
            if(!(cn.getName().equals("")))
            {


                myno=cn.getNameDD();
            }

        }






        {


            mLocationRequest = LocationRequest.create()
                    .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                    .setInterval(1000)
                    .setFastestInterval(1000);
            // Request location updates

            if (ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, MainActivity.this);
            Log.d("reque", "--->>>>");




















if (flood.equals("1"))


                        alertmyfriend();





        }




        // Create the location request if person's last location is of disaster

    }
    // sends sms to emergency contacts automaticaaly with all the details of where the phone was last spotted
    private void alertmyfriend() {
  //      Intent ij=new Intent(this,MainalertActivity.class);
    //    startActivity(ij);

    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "Connection Suspended");
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i(TAG, "Connection failed. Error: " + connectionResult.getErrorCode());
    }

    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.e("lococococ", String.valueOf(location.getLatitude()));
        latlong=location.getLatitude()+","+location.getLongitude();
        lat=location.getLatitude();
        longi=location.getLongitude();

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor mySensor = sensorEvent.sensor;


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





















































































//hospital




























    public void pickContact(View v)
    {
        Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
        startActivityForResult(contactPickerIntent, RESULT_PICK_CONTACT);
    }
    @Override
    public void onBackPressed(){
        //Intent a = new Intent(Intent.ACTION_MAIN);
        //a.addCategory(Intent.CATEGORY_HOME);
        //a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //startActivity(a);
        super.onBackPressed();
    }







    private void contactPicked(Intent data) {

        Cursor cursor = null;
        try {
            String phoneNo = null ;
            String name = null;
// getData() method will have the Content Uri of the selected contact
            Uri uri = data.getData();
//Query the content uri
            cursor = getContentResolver().query(uri, null, null, null, null);
            cursor.moveToFirst();
// column index of the phone number
            int phoneIndex =cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
// column index of the contact name
            int nameIndex =cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
            phoneNo = cursor.getString(phoneIndex);
            name = cursor.getString(nameIndex);
            Log.d("name",name);
            Log.d("phno",phoneNo);
            naam=naam+name+",";
            phon=phon+phoneNo+",";
            SharedPreferences.Editor editor=getSharedPreferences("emergency",MODE_PRIVATE).edit();
            editor.putString("mob",phon);
            editor.putString("nam",naam);
            editor.apply();
            stringArrayList.add(name);
            stringArrayList1.add(phoneNo);




            adapter=new ListViewAdfrlist(MainActivity.this,stringArrayList,stringArrayList1);
            l.setAdapter(adapter);
// Set the value to the textviews
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {

            case RESULT_PICK_CONTACT: {
                contactPicked(data);
            break;
            }
            case 111:
                if (resultCode == RESULT_OK) {
                    //find();
                  //  uploadfirebase(photoURI);

                }


        }
    }




















/*
        public interface FileUploadService {
            @Multipart
            @POST("search")
            Call<ResponseBody> upload(
                    @Part("api_key") RequestBody description,
                    @Part("api_secret") RequestBody description1,
                    @Part ("image_url") RequestBody description3,
                    @Part("faceset_token") RequestBody description2
            );
        }

        private void uploadFile(String fireurl) {

            FileUploadService service =
                    ServiceGenerator.createService(FileUploadService.class);



            // add another part within the multipart request

            RequestBody description =
                    RequestBody.create(
                            okhttp3.MultipartBody.FORM, "8eXIfwPbVhLUXV4xt9eW2xRSxWt74Fki");
            RequestBody description1 =
                    RequestBody.create(
                            okhttp3.MultipartBody.FORM, "9xyBX7iWUUWu4msZbaAm6_XTRN9OiT5b");
            // MultipartBody.Part is used to send also the actual file name
            RequestBody body =
                    RequestBody.create(
                            okhttp3.MultipartBody.FORM, "https://firebasestorage.googleapis.com/v0/b/lifesaver-18f28.appspot.com/o/images%2Fname.jpg?alt=media&token=f3909169-4461-401f-81ab-4a0f9d30ae6f");

            RequestBody description2 =
                    RequestBody.create(
                            okhttp3.MultipartBody.FORM, "537c2b49a9a160655b9a3c707555af4b");

            // finally, execute the request
            Call<ResponseBody> call = service.upload(description,description1, body,description2);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call,
                                       Response<ResponseBody> response) {





                    Log.e("Uploaddd", "success");
                    Log.e("Uploadd", response.toString());
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.e("Upload error:", t.getMessage());
                }
            });
        }











*/


    public static float dpToPixels(int dp, Context context) {
        return dp * (context.getResources().getDisplayMetrics().density);
    }











void emergencycontact()
{
    stringArrayList=new ArrayList<>();
    stringArrayList1=new ArrayList<>();
    LayoutInflater inflater = getLayoutInflater();
    View alertLayout = inflater.inflate(R.layout.layoutemergency, null);

    AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);

    // this is set the view from XML inside AlertDialog
    alert.setView(alertLayout);
    // disallow cancel of AlertDialog on click of back button and outside touch
    alert.setTitle("Emergency Contacts ");
    alert.setIcon(R.drawable.ic_contacts_black_24dp);
    l=alertLayout.findViewById(R.id.listname);
    FloatingTextButton fab11=alertLayout.findViewById(R.id.floatingActionButton);
    final TextView textView=alertLayout.findViewById(R.id.text);
    fab11.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            textView.setVisibility(View.INVISIBLE);
            Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
            startActivityForResult(intent, RESULT_PICK_CONTACT);




        }
    });


    SharedPreferences preferenceflood=getSharedPreferences("flood",MODE_PRIVATE);
    flood=preferenceflood.getString("flood","0");

    SharedPreferences preference=getSharedPreferences("emergency",MODE_PRIVATE);
    phon=preference.getString("mob","");
    naam=preference.getString("nam","");
    if(naam!="") {

        textView.setVisibility(View.INVISIBLE);
        String[] name=naam.split(",");
        String[] no=phon.split(",");
        for(int i=0;i<name.length;i++) {
            stringArrayList.add(name[i]);
            stringArrayList1.add(no[i]);
        }




        ListViewAdfrlist adapterj = new ListViewAdfrlist(MainActivity.this, stringArrayList, stringArrayList1);
        l.setAdapter(adapterj);

    }
    else {

    }
    l.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

            LayoutInflater inflater = getLayoutInflater();
            View alertLayout = inflater.inflate(R.layout.layoutdelete, null);

            AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);

            // this is set the view from XML inside AlertDialog
            alert.setView(alertLayout);
            // disallow cancel of AlertDialog on click of back button and outside touch
            alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    String n=stringArrayList.get(position);
                    String p=stringArrayList1.get(position);
                    stringArrayList.remove(position);
                    stringArrayList1.remove(position);

                    ListViewAdfrlist adapterj = new ListViewAdfrlist(MainActivity.this, stringArrayList, stringArrayList1);
                    l.setAdapter(adapterj);
                    SharedPreferences.Editor editor=getSharedPreferences("emergency",MODE_PRIVATE).edit();
                    editor.putString("mob",phon.replace(p+",",""));
                    editor.putString("nam",naam.replace(n+",",""));
                    editor.apply();




                }
            });
            alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog dialog = alert.create();
            dialog.show();















            return false;
        }
    });

    alert.setPositiveButton("Done", new DialogInterface.OnClickListener() {


        @Override
        public void onClick(DialogInterface dialog, int which) {


        }
    });
    AlertDialog dialog = alert.create();
    dialog.show();







    }







void appstarter1()
{
    final TapTargetSequence sequence = new TapTargetSequence(this)
            .targets(
                 //   TapTarget.forView(findViewById(R.id.bmb), "Emergency button","For calling Fire Brigade instantly") .transparentTarget(true) .textTypeface(Typeface.SANS_SERIF)  // Specify a typeface for the text.drawShadow(true) .outerCircleAlpha(0.96f)         ,
                   // TapTarget.forView(findViewById(R.id.ps), "Nearest to you","For finding nearest emergency services") .transparentTarget(true).outerCircleAlpha(0.96f)

            )


            .listener(new TapTargetSequence.Listener() {
                // This listener will tell us when interesting(tm) events happen in regards
                // to the sequence
                @Override
                public void onSequenceFinish() {
                    Snackbar.make(rl,"Congratulations! You're all set to use the app!",Snackbar.LENGTH_LONG).setActionTextColor(Color.BLUE).show();
                    SharedPreferences.Editor editor= getSharedPreferences("app",MODE_PRIVATE).edit();
                    editor.putString("intro1","2");

                    editor.apply();
                }

                @Override
                public void onSequenceStep(TapTarget lastTarget, boolean targetClicked) {
                    Log.d("TapTargetView", "Clicked on " + lastTarget.id());
                }

                @Override
                public void onSequenceCanceled(TapTarget lastTarget) {

                }
            });

    sequence.start();

}

}


