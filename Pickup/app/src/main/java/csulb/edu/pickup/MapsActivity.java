package csulb.edu.pickup;

import android.Manifest;
import android.content.Context;
import android.content.Intent;

import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;


import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.login.LoginManager;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class MapsActivity extends FragmentActivity implements android.location.LocationListener, OnMarkerClickListener {

    private static final String ETag = "Error Message";
    private static final int CREATE_MAP_EVENT = 1;
    private static final int VIEW_MAP_EVENT = 2;

    private GoogleMap map; // Might be null if Google Play services APK is not available.
    ArrayList<Event> eventList;
    User thisUser;
    private EditText searchFriend;
    Animation animAlpha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle data = getIntent().getExtras();
        thisUser = data.getParcelable("USER");


        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        setContentView(R.layout.activity_maps);
        searchFriend = (EditText) findViewById(R.id.searchFriend);
        searchFriend.setCursorVisible(false);
        searchFriend.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEvent.ACTION_UP == event.getAction()) {
                    searchFriend.setCursorVisible(true);

                }
                else if (MotionEvent.ACTION_CANCEL == event.getAction()) {
                    searchFriend.setCursorVisible(false);
                }
                return false;
            }
        });
        animAlpha = AnimationUtils.loadAnimation(this, R.anim.anim_alpha);

        setupTopbar();
        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        // Enabling MyLocation Layer of Google Map
        map.setMyLocationEnabled(true);

        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String bestProvider = locationManager.getBestProvider(criteria, true);
        if (locationManager != null) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            }
        }
        else {
            Location location = locationManager.getLastKnownLocation(bestProvider);
            if (location != null) {
                onLocationChanged(location);
                //get current location and zoom in
                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                map.animateCamera(CameraUpdateFactory.zoomTo(10));
            }
        }
        if(data.containsKey("filterLat") && data.containsKey("filterLong")){
            LatLng latLng = new LatLng(data.getDouble("filterLat"), data.getDouble("filterLong"));
            map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            map.animateCamera(CameraUpdateFactory.zoomTo(10));
        }




        //Get Location updates from server every 20 seconds
        //locationManager.requestLocationUpdates(bestProvider, 20000, 0, this);
        getPositionsFromServer();

        /*map.setOnMapClickListener(new OnMapClickListener() {
            @Override
            public void onMapClick(LatLng point) {
                // Drawing marker on the map
                drawMarker(point);
                //Toast.makeText(getBaseContext(), "Longtitude: " + String.valueOf(point.longitude) + "\n" +
                //        "Latitude: " + String.valueOf(point.latitude), Toast.LENGTH_LONG).show();
            }
        });*/
    }

    //Get addresses from the database
    private void getPositionsFromServer() {
        try {
            map.setOnMarkerClickListener(this);
            URLConnection http = new URLConnection();
            Bundle data = getIntent().getExtras();
            ArrayList<Event> eventList;
            LatLng latLng = new LatLng(0,0);
            String eventName = "";
            String creator = "";
            if(data.containsKey("EVENTS")|| data.getParcelable("EVENTS")!=null){
                eventList = data.getParcelableArrayList("EVENTS");
                ArrayList<Event> preEventList = data.getParcelableArrayList("EVENTS");
                int listSize = preEventList.size();
                for(int s = 0; s<listSize; s++){
                    System.out.println(preEventList.get(s).getAddress());
                    System.out.println(preEventList.get(s).getName());
                    System.out.println(preEventList.get(s).getCreatorName());
                    System.out.println(preEventList.get(s).getAgeMax());
                    System.out.println(preEventList.get(s).getEventID());
                    System.out.println("new");

                }

            }
            else {
                eventList = http.sendGetEvents();
            }
            Log.d("SARAH", "listsize:"+eventList.size());
            //ArrayList<Event> preEventList = getIntent().getExtras().getParcelableArrayList("EVENTS");
            //Log.d("SARAH", "preEventList"+preEventList.get(1).getName());

            for (int i = 0; i < eventList.size(); i++) {
                //add marker to each position
                latLng = new LatLng(eventList.get(i).getLatitude(), eventList.get(i).getLongitude());
                if (eventList.get(i).getName() != null) {
                    eventName = eventList.get(i).getName();
                    creator = eventList.get(i).getCreatorName();
                    Log.d("SARAH", "eventName"+eventList.get(i).getName());
                    map.addMarker(new MarkerOptions().position(latLng).title(eventName).snippet(creator)).setVisible(true);
                }
            }


        }
        catch (IOException e) {
            Log.e(ETag, "Unable connect to server", e);
        }
    }

    private void drawMarker(LatLng point){
        // Creating an instance of MarkerOptions
        MarkerOptions markerOptions = new MarkerOptions();

        // Setting latitude and longitude for the marker
        markerOptions.position(point);

        // Adding marker on the Google Map
        map.addMarker(markerOptions);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("SARAH", "username:" + thisUser.getEmail());
        Bundle data = getIntent().getExtras();

        if(data.containsKey("EVENT")) {
            Event newEvent = data.getParcelable("EVENT");
            Log.d("SARAH", "PassedEvent "+newEvent.getName());

        }
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String bestProvider = locationManager.getBestProvider(criteria, true);
        if (locationManager != null) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            }
        }

        else {
            Location location = locationManager.getLastKnownLocation(bestProvider);
            if (location != null) {
                onLocationChanged(location);
                //get current location and zoom in
                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                map.animateCamera(CameraUpdateFactory.zoomTo(10));
            }
        }

        if(data.containsKey("filterLat") && data.containsKey("filterLong")){
            LatLng latLng = new LatLng(data.getDouble("filterLat"), data.getDouble("filterLong"));
            map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            map.animateCamera(CameraUpdateFactory.zoomTo(10));
        }

        if(/*data.containsKey("EVENTS")){*/ getIntent().getExtras().getParcelableArrayList("EVENTS")!=null){
            Log.d("SARAH", "EVENTS is set at top");

            eventList = getIntent().getParcelableArrayListExtra("EVENTS");

        }
        getPositionsFromServer();


    }

    public void onClick_Filter(View v) {
        v.startAnimation(animAlpha);
        Bundle b = new Bundle();
        b.putParcelable("USER", thisUser);
        Intent myIntent = new Intent(v.getContext(), FilterEvents.class);
        myIntent.putExtras(b);
        startActivityForResult(myIntent, 1);

    }

    public void onClick_ImageButton(View v) {
        v.startAnimation(animAlpha);
        Bundle b = new Bundle();
        b.putParcelable("USER", thisUser);
        Intent myIntent = new Intent(v.getContext(), CreateEventActivity.class);
        myIntent.putExtras(b);
        startActivityForResult(myIntent, 1);
    }

    //TO DO: implement search friends here
    public void onClick_Search(View v) {

    }

    public void onLocationChanged(Location location) {

            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            LatLng latLng = new LatLng(latitude, longitude);
        // map.addMarker(new MarkerOptions().position(latLng)); // add marker
           map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
           map.animateCamera(CameraUpdateFactory.zoomTo(15));
        //   Log.d(String.valueOf(longitude), "longtitude");
        //   Log.d(String.valueOf(latitude), "latitude");

    }

    public void onProviderDisabled(String provider) {
        // TODO Auto-generated method stub
    }


    public void onProviderEnabled(String provider) {
        // TODO Auto-generated method stub
    }


    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub
    }

    private boolean isGooglePlayServicesAvailable() {
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (ConnectionResult.SUCCESS == status) {
            return true;
        } else {
            GooglePlayServicesUtil.getErrorDialog(status, this, 0).show();
            return false;
        }
    }


    private void setUpMarker() {
        map.setOnMarkerClickListener(this);

    }



    //Click on the marker to view an event

    @Override
    public boolean onMarkerClick(final Marker marker) {

        try {
            String s = marker.getTitle();
            URLConnection http = new URLConnection();
            ArrayList<Event> list = http.sendGetEvents();
            for (int j = 0; j < list.size(); j++) {
                System.out.println("firstListName: " + list.get(j).getName());
            }
            for (int i = 0; i < list.size(); i++) {
                System.out.println("listName: "+list.get(i).getName());
                System.out.println("markerName: "+marker.getTitle());
               if (list.get(i).getName()!=null && marker.getTitle()!=null && list.get(i).getName().equals(marker.getTitle())) {
                //if (marker.getTitle().equals("abc")) {
                        //Event e = list.get(i);

                    Bundle b = new Bundle();
                    b.putParcelable("USER", thisUser);
                    Intent intent = new Intent(getBaseContext(), ViewEventActivity.class);
                    intent.putExtra("EventID", list.get(i).getEventID());
                    intent.putExtras(b);
                    startActivityForResult(intent, VIEW_MAP_EVENT);

                    Log.d("TO GET EVENT", list.get(i).getName());
                    return true;
                }
            }
            Log.d("EVENT" , s);

        }
        catch (IOException e) {
            Log.e(ETag, "Unable connect to server", e);
        }
        catch (Exception e) {
            Log.e(ETag, "Unable to connect to internet",e);
        }
        return true;
    }


    //get the result back


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == CREATE_MAP_EVENT) {
            if(resultCode == MapsActivity.RESULT_OK){
                String result=data.getStringExtra("result");
                Scanner read = new Scanner(result);
                double lat = 0;
                double lon = 0;
                String eventName = "";
                String eventCreator = "";
                lat = read.nextDouble();
                lon = read.nextDouble();
                eventName = read.next();
                LatLng latLng = new LatLng(lat, lon);
                map.addMarker(new MarkerOptions().position(latLng).title(eventName).snippet(eventCreator));
                map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                map.animateCamera(CameraUpdateFactory.zoomTo(10));
            }
            if (resultCode == MapsActivity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }

        if (requestCode == VIEW_MAP_EVENT) {
            if (resultCode == ViewEventActivity.RESULT_OK) {
                String result = data.getStringExtra("result");
                //if the result is edit do nothing
                if (result.equals("edit")) {
                    finish();
                    startActivity(getIntent());

                }

                //if the result is delete, delete the mark
                else if (result.equals("delete")) {
                    finish();
                    startActivity(getIntent());


                }
            }

            if (resultCode == MapsActivity.RESULT_CANCELED) {
                //Do nothing
            }
        }


    }//onActivityResult

    //make topbar transparent
    private void setupTopbar() {
        //Drawable topTextBG = getResources().getDrawable(R.drawable.bluebg);
        //set opacity
        //topTextBG.setAlpha(10);

        // setting the images on the ImageViews


    }

    @Override
    protected void onDestroy() {

        // TODO Auto-generated method stub
        super.onDestroy();
        if (LoginManager.getInstance() != null) {
            LoginManager.getInstance().logOut();
        }

    }

}