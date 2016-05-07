package csulb.edu.pickup;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Sarah on 4/29/2016.
 */
public class MapsFragment extends Fragment implements android.location.LocationListener, GoogleMap.OnMarkerClickListener{

    private static final String ETag = "Error Message";
    private static final int CREATE_MAP_EVENT = 1;
    private static final int VIEW_MAP_EVENT = 2;

    private Fragment mapFrame; // Might be null if Google Play services APK is not available.
    private GoogleMap map;
    MapFragment mapFrag;
    private ImageButton button;
    ArrayList<Event> eventList;
    User thisUser;
    View rootView;

    Animation animAlpha;

    private ImageButton plusButton;
    private Button filterButton;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle data = getActivity().getIntent().getExtras();
        Bundle fragData = getArguments();
        thisUser = data.getParcelable("USER");
        //thisUser = new User("Sarah", "Shibley", "sarahshib@hotmail.com","abcd","1994-10-12","female", "");

        rootView=inflater.inflate(R.layout.activity_maps, container, false);
        getActivity().setTitle("Map");


        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        animAlpha = AnimationUtils.loadAnimation(getActivity(), R.anim.anim_alpha);

        setupTopbar();

        FragmentManager fm = getChildFragmentManager();
        mapFrag = (MapFragment) fm.findFragmentById(R.id.map_container);
        mapFrag = (MapFragment) fm.findFragmentById(R.id.map_container);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            map = ((MapFragment) getChildFragmentManager().findFragmentById(R.id.map)).getMap();
        } else {
            map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        }
        if (mapFrag == null) {
            mapFrag = MapFragment.newInstance();
            fm.beginTransaction().replace(R.id.map_container, mapFrag).addToBackStack( "Map" ).commit();
            System.out.println("map frag was null");
        }


        //mapFrame =  getChildFragmentManager().findFragmentById(R.id.map_container);
        // Enabling MyLocation Layer of Google Map



        //super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_maps);
        findViewsById();
        setUpPlusButton();
        setUpFilterButton();




        /**************************************/
        map.setMyLocationEnabled(true);

        super.onResume();
        Log.d("SARAH", "username:" + thisUser.getEmail());
       /* Bundle data = getActivity().getIntent().getExtras();

        if(data.containsKey("EVENT")) {
            Event newEvent = data.getParcelable("EVENT");
            Log.d("SARAH", "PassedEvent " + newEvent.getName());

        }*/
        map.setMyLocationEnabled(true);


        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String bestProvider = locationManager.getBestProvider(criteria, true);
        if (locationManager != null) {
            if (ContextCompat.checkSelfPermission(this.getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(this.getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            }
        }
        if(fragData.containsKey("filterLat") && fragData.containsKey("filterLong")){
            LatLng latLng = new LatLng(data.getDouble("filterLat"), data.getDouble("filterLong"));
            map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            map.animateCamera(CameraUpdateFactory.zoomTo(10));
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



        //Get Location updates from server
        //locationManager.requestLocationUpdates(bestProvider, 20000, 0, this);
        getPositionsFromServer();

        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng point) {
                // Drawing marker on the map
                drawMarker(point);
                //Toast.makeText(getBaseContext(), "Longtitude: " + String.valueOf(point.longitude) + "\n" +
                //        "Latitude: " + String.valueOf(point.latitude), Toast.LENGTH_LONG).show();
            }
        });


        return rootView;
    }


    //Get addresses from the database
    private void getPositionsFromServer() {
        try {
            map.setOnMarkerClickListener(this);
            URLConnection http = new URLConnection();
            Bundle data = getArguments();
            ArrayList<Event> eventList;
            LatLng latLng = new LatLng(0,0);
            String eventName = "";
            String creator = "";
            if(data.containsKey("EVENTS")|| data.getParcelable("EVENTS")!=null){
                eventList = data.getParcelableArrayList("EVENTS");
                ArrayList<Event> preEventList = data.getParcelableArrayList("EVENTS");
                int listSize = preEventList.size();


            }

            else {
                eventList = http.sendGetEvents();
                Log.d("SARAH", "sending event request to server");
            }
            Log.d("SARAH", "listsize:" + eventList.size());
            //ArrayList<Event> preEventList = getActivity().getIntent().getExtras().getParcelableArrayList("EVENTS");
            //Log.d("SARAH", "preEventList"+preEventList.get(1).getName());

            for (int i = 0; i < eventList.size(); i++) {
                System.out.println("looping");
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
    public void onResume() {
        Log.d("SARAH", "onResume ");

        map = mapFrag.getMap();
        map.setMyLocationEnabled(true);

        super.onResume();
        Log.d("SARAH", "username:" + thisUser.getEmail());
        Bundle data = getActivity().getIntent().getExtras();

        if(data.containsKey("EVENT")) {
            Event newEvent = data.getParcelable("EVENT");
            Log.d("SARAH", "PassedEvent " + newEvent.getName());

        }
        map.setMyLocationEnabled(true);


        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String bestProvider = locationManager.getBestProvider(criteria, true);
        if (locationManager != null) {
            if (ContextCompat.checkSelfPermission(this.getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(this.getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            }
        }
        if(data.containsKey("filterLat") && data.containsKey("filterLong")){
            LatLng latLng = new LatLng(data.getDouble("filterLat"), data.getDouble("filterLong"));
            map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            map.animateCamera(CameraUpdateFactory.zoomTo(10));
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



        //Get Location updates from server
        //locationManager.requestLocationUpdates(bestProvider, 20000, 0, this);
        getPositionsFromServer();

        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng point) {
                // Drawing marker on the map
                drawMarker(point);
                //Toast.makeText(getBaseContext(), "Longtitude: " + String.valueOf(point.longitude) + "\n" +
                //        "Latitude: " + String.valueOf(point.latitude), Toast.LENGTH_LONG).show();
            }
        });

    }





    private void setUpPlusButton() {
        plusButton = (ImageButton) rootView.findViewById(R.id.map_plus_button);
        plusButton.requestFocus();
        plusButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                v.startAnimation(animAlpha);
                Bundle args = new Bundle();
                Fragment fragment = new CreateEventFragment();
                args.putString(FragmentTwo.ITEM_NAME, "Create Event");
                fragment.setArguments(args);
                FragmentManager frgManager = getFragmentManager();
                frgManager.beginTransaction().replace(R.id.content_frame, fragment).addToBackStack( "Map" )
                        .commit();            }
        });  }

    private void setUpFilterButton() {

        filterButton = (Button) rootView.findViewById(R.id.filter_btn);
        filterButton.requestFocus();
        filterButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                v.startAnimation(animAlpha);
                Bundle args = new Bundle();
                Fragment fragment = new FilterEventsFragment();
                args.putString(FragmentTwo.ITEM_NAME, "Filter Events");
                fragment.setArguments(args);
                FragmentManager frgManager = getFragmentManager();
                frgManager.beginTransaction().replace(R.id.content_frame, fragment).addToBackStack("Map")
                        .commit();                 }
        });
    }

    private void findViewsById() {

    }

    public void onClick(View view) {

        if (view == plusButton) {
            view.startAnimation(animAlpha);


        } else if (view == filterButton) {
            view.startAnimation(animAlpha);
        }

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
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this.getActivity());
        if (ConnectionResult.SUCCESS == status) {
            return true;
        } else {
            GooglePlayServicesUtil.getErrorDialog(status, this.getActivity(), 0).show();
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
            }
            for (int i = 0; i < list.size(); i++) {

                if (list.get(i).getName()!=null && marker.getTitle()!=null && list.get(i).getName().equals(marker.getTitle())) {
                    //if (marker.getTitle().equals("abc")) {
                    //Event e = list.get(i);

                    Bundle args = new Bundle();
                    Fragment fragment = new ViewEventFragment();
                    args.putString("EventID", list.get(i).getEventID());
                    fragment.setArguments(args);
                    FragmentManager frgManager = getFragmentManager();
                    frgManager.beginTransaction().replace(R.id.content_frame, fragment).addToBackStack("Map")
                            .commit();



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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == CREATE_MAP_EVENT) {
            if(resultCode == getActivity().RESULT_OK){
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
            if (resultCode == getActivity().RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }

        if (requestCode == VIEW_MAP_EVENT) {
            if (resultCode == getActivity().RESULT_OK) {
                String result = data.getStringExtra("result");
                //if the result is edit do nothing
                if (result.equals("edit")) {
                    getActivity().finish();
                    startActivity(getActivity().getIntent());

                }

                //if the result is delete, delete the mark
                else if (result.equals("delete")) {
                    getActivity().finish();
                    startActivity(getActivity().getIntent());


                }
            }

            if (resultCode == getActivity().RESULT_CANCELED) {
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
    /*What should on destroy be for fragments?*/
    @Override
    public void onDestroy() {

        // TODO Auto-generated method stub
        super.onDestroy();
        /*if (LoginManager.getInstance() != null) {
            LoginManager.getInstance().logOut();
        }
        */



    }


}
