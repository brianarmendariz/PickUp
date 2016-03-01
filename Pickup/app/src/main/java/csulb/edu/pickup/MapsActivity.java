package csulb.edu.pickup;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;


import android.os.StrictMode;
import android.provider.SyncStateContract.Constants;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

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
import com.google.android.gms.vision.barcode.Barcode.GeoPoint;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class MapsActivity extends FragmentActivity implements android.location.LocationListener, OnMarkerClickListener {

    private static final String ETag = "Error Message";
    private static final int CREATE_MAP_EVENT = 1;
    private static final int VIEW_MAP_EVENT = 2;

    private GoogleMap map; // Might be null if Google Play services APK is not available.
    private ImageButton button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_maps);
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
        Location location = locationManager.getLastKnownLocation(bestProvider);
        if (location != null) {
            onLocationChanged(location);
            //get current location and zoom in
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            map.animateCamera(CameraUpdateFactory.zoomTo(10));
        }



        //Get Location updates from server
        //locationManager.requestLocationUpdates(bestProvider, 20000, 0, this);
        getPositionsFromServer();

        map.setOnMapClickListener(new OnMapClickListener() {
            @Override
            public void onMapClick(LatLng point) {
                // Drawing marker on the map
                drawMarker(point);
                //Toast.makeText(getBaseContext(), "Longtitude: " + String.valueOf(point.longitude) + "\n" +
                //        "Latitude: " + String.valueOf(point.latitude), Toast.LENGTH_LONG).show();
            }
        });
    }

    //Get addresses from the database
    private void getPositionsFromServer() {
        try {
            map.setOnMarkerClickListener(this);
            URLConnection http = new URLConnection();
            ArrayList<Event> list = http.sendGetEvents();

            LatLng latLng = new LatLng(0,0);
            String eventName = "";
            String creator = "";
            for (int i = 0; i < list.size(); i++) {
                //add marker to each position
                latLng = new LatLng(list.get(i).getLatitude(), list.get(i).getLongitude());
                eventName = list.get(i).getName();
                creator = list.get(i).getCreator();

                map.addMarker(new MarkerOptions().position(latLng).title(eventName).snippet(creator)).setVisible(true);
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
    }

    public void onClick_Filter(View v) {

    }

    public void onClick_ImageButton(View v) {
        Intent myIntent = new Intent(v.getContext(), CreateEventActivity.class);
        startActivityForResult(myIntent, 1);
    }


    public void onLocationChanged(Location location) {

        //    double latitude = location.getLatitude();
        //    double longitude = location.getLongitude();
        //    LatLng latLng = new LatLng(latitude, longitude);
        // map.addMarker(new MarkerOptions().position(latLng)); // add marker
        //   map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        //   map.animateCamera(CameraUpdateFactory.zoomTo(15));
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
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getName().equals(marker.getTitle())) {
                    //Event e = list.get(i);
                    Intent intent = new Intent(getBaseContext(), ViewEventActivity.class);
                    intent.putExtra("EventID", list.get(i).getEventID());
                    startActivityForResult(intent,VIEW_MAP_EVENT);

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


}