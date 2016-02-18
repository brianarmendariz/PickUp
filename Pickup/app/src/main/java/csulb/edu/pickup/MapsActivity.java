package csulb.edu.pickup;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity {

    private GoogleMap map; // Might be null if Google Play services APK is not available.
    private ImageButton button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        // Enabling MyLocation Layer of Google Map
        map.setMyLocationEnabled(true);
        map.setOnMapClickListener(new OnMapClickListener() {
            @Override
            public void onMapClick(LatLng point) {
                // Drawing marker on the map
                drawMarker(point);

                Log.d(String.valueOf(point.longitude), "this is longtitude");
                Log.d(String.valueOf(point.latitude), "this is latitude");
                Toast.makeText(getBaseContext(), "Longtitude: " + String.valueOf(point.longitude) + "\n" +
                        "Latitude: " + String.valueOf(point.latitude), Toast.LENGTH_LONG).show();
            }
        });
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


}
