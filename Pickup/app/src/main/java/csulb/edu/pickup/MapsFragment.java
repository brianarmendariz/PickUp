package csulb.edu.pickup;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
public class MapsFragment extends Fragment implements android.location.LocationListener, GoogleMap.OnMarkerClickListener,
        SearchView.OnQueryTextListener
{

    private static final String ETag = "Error Message";
    private static final int CREATE_MAP_EVENT = 1;
    private static final int VIEW_MAP_EVENT = 2;


    private GoogleMap map;// Might be null if Google Play services APK is not available.
    private MapFragment mapFrag;
    private ImageButton button;
    private ArrayList<Event> eventList = null;
    private User thisUser;
    private static View rootView;

    private Animation animAlpha;

    private ImageButton plusButton;
    private Button filterButton;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu (Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu_main_search, menu); // removed to not double the menu items
        MenuItem item = menu.findItem(R.id.menu_search);
        SearchView sv = new SearchView(((MainActivity) getActivity()).getSupportActionBar().getThemedContext());
        MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);
        MenuItemCompat.setActionView(item, sv);
        sv.setOnQueryTextListener(this);
        sv.setIconifiedByDefault(false);
        sv.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        MenuItemCompat.setOnActionExpandListener(item, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                // Do something when collapsed
                return true;  // Return true to collapse action view
            }

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                // Do something when expanded
                return true;  // Return true to expand action view
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }

    private void viewEvent(Event event)
    {
        Bundle b = new Bundle();
        //add current user
        b.putParcelable("USER", thisUser);
        String eventId = event.getEventID();
        if (eventId != null) {
            System.out.println("putting the event string");
            b.putString("EventID", eventId);
        } else {
            System.out.println("not putting the event string");
        }
        Fragment fragment = new ViewEventFragment();
        fragment.setArguments(b);
        FragmentManager frgManager = getFragmentManager();
        frgManager.beginTransaction().replace(R.id.content_frame, fragment).addToBackStack("Map")
                .commit();


//        Intent thisIntent = new Intent(getActivity().getBaseContext(), MainActivity.class);
//        thisIntent.putExtras(b);
    }

    private void popUpAlertDialog(String eventName)
    {
        Bundle b = new Bundle();
        b.putParcelable("USER", thisUser);
        Intent thisIntent = new Intent(getActivity().getBaseContext(), MainActivity.class);
        thisIntent.putExtras(b);
        b.putString("TITLE", "Event Not Found!");
        b.putString("EVENTNAME", eventName);
        MyDialogFragment fragment = new MyDialogFragment();
        fragment.setArguments(b);
        FragmentManager frgManager = getFragmentManager();
        fragment.show(frgManager, "MyDialog");
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        // see if requested event is a made
        boolean isEvent = false;
        Event event = null;

        if(!eventList.isEmpty())
        {
            for (int i = 0; i < eventList.size(); i++)
            {
                // if user is member break from loop
                if (query.equals(eventList.get(i).getName()))
                {
                    System.out.println("loop");
                    isEvent = true;
                    event = eventList.get(i);
                    break;
                }
            }
        }

        // view the requested users profile
        if(isEvent)
        {
            viewEvent(event);
        }
        else // alert that user does not exist
        {
            popUpAlertDialog(query);
        }

        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /* Get objects from MainActivity */
        Bundle data = getActivity().getIntent().getExtras();
        Bundle fragData = getArguments();
        thisUser = data.getParcelable("USER");

        //thisUser below is used for testing
        //thisUser = new User("Sarah", "Shibley", "sarahshib@outlook.com","abcd","1994-10-12","female", "");

        /*Kit Kat and below require that the rootView be inflated every time it returns to map */
        if(rootView==null || Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            rootView = inflater.inflate(R.layout.activity_maps, container, false);

            // adding permission checking at runtime
            hasPermissions();
        }


        getActivity().setTitle("Map");

        super.onCreate(savedInstanceState);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        /* animation for filter and add buttons */
        animAlpha = AnimationUtils.loadAnimation(getActivity(), R.anim.anim_alpha);

        setupTopbar();

        super.onResume();

        FragmentManager fm = getChildFragmentManager();
        mapFrag = (MapFragment) fm.findFragmentById(R.id.map_container);

        /* KitKat and below do not use getChildFragmentManager */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            map = ((MapFragment) getChildFragmentManager().findFragmentById(R.id.map)).getMap();
        } else {
            map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        }
        /*only create the map if it has not already been created*/
        if (mapFrag == null) {
            mapFrag = MapFragment.newInstance();
            fm.beginTransaction().replace(R.id.map_container, mapFrag).addToBackStack( "Map" ).commit();
        }

        /* Set up user interface */
        findViewsById();
        setUpPlusButton();
        setUpFilterButton();

        /* handle Google Maps location settings */
        map.setMyLocationEnabled(true);
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String bestProvider = locationManager.getBestProvider(criteria, true);
        if (locationManager != null) {
            if (ContextCompat.checkSelfPermission(this.getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(this.getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            }
        }

        /* filterLat and filterLong specify a new location for the map to navigate to */
        if(fragData.containsKey("filterLat") && fragData.containsKey("filterLong")){
            LatLng latLng = new LatLng(data.getDouble("filterLat"), data.getDouble("filterLong"));
            map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            map.animateCamera(CameraUpdateFactory.zoomTo(10));
        }
        /* if no latitude and longitude specified, use last known location*/
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
                //drawMarker(point);
                //Toast.makeText(getBaseContext(), "Longtitude: " + String.valueOf(point.longitude) + "\n" +
                //        "Latitude: " + String.valueOf(point.latitude), Toast.LENGTH_LONG).show();
            }
        });

        /*fragments require that a view is returned in OnCreateView*/
        return rootView;
    }

    private void hasPermissions()
    {
        ArrayList<String> permissionList = new ArrayList<String>();
        int reqCode = 0;
        if(ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            // if we do not have permission, request it
            permissionList.add(android.Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if(ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            // if we do not have permission, request it
            permissionList.add(android.Manifest.permission.ACCESS_COARSE_LOCATION);
        }

        // if permissions need to be requested they will be in the list
        if(permissionList.size() > 0)
        {
            requestPermission((String[])permissionList.toArray(), reqCode);
        }
    }

    private void requestPermission(String[] permissions, int reqCode)
    {
        ActivityCompat.requestPermissions(getActivity(), permissions, reqCode);
    }

    //Get addresses from the database
    private void getPositionsFromServer() {
        try
        {
            map.setOnMarkerClickListener(this);
            URLConnection http = new URLConnection();
            Bundle data = getArguments();
            LatLng latLng = new LatLng(0,0);
            String eventName = "";
            String creator = "";
            /* EVENTS is an ArrayList of filtered events returned from FilterEventsFragment */
            if(data.containsKey("EVENTS")|| data.getParcelable("EVENTS")!=null){
                eventList = data.getParcelableArrayList("EVENTS");
                ArrayList<Event> preEventList = data.getParcelableArrayList("EVENTS");
                int listSize = preEventList.size();


            }
            /*if not coming from FilterEventsFragment, send request to server */
            else {
                eventList = http.sendGetEvents();
            }
            //ArrayList<Event> preEventList = getActivity().getIntent().getExtras().getParcelableArrayList("EVENTS");
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
    public void onResume() {

        super.onResume();
        //map = mapFrag.getMap();
    }





    private void setUpPlusButton() {
        plusButton = (ImageButton) rootView.findViewById(R.id.map_plus_button);
        plusButton.requestFocus();
        plusButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                v.startAnimation(animAlpha);
                Bundle args = new Bundle();
                Fragment fragment = new CreateEventFragment();
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


                    Bundle args = new Bundle();
                    Fragment fragment = new ViewEventFragment();
                    args.putString("EventID", list.get(i).getEventID());

                    //args.putString("MapURL",map.toUrlValue());
                    fragment.setArguments(args);
                    FragmentManager frgManager = getFragmentManager();
                    frgManager.beginTransaction().replace(R.id.content_frame, fragment).addToBackStack("Map")
                            .commit();

                    return true;
                }
            }
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
    public void onDestroyView() {

        super.onDestroyView();
        Fragment f = getChildFragmentManager().findFragmentById(R.id.map);
        if (f != null)
            getChildFragmentManager().beginTransaction().remove(f).commit();

       /* Fragment f2 = getFragmentManager().findFragmentById(R.id.map_container);
        if (f2 != null)
            getFragmentManager().beginTransaction().remove(f2).commit();
        */
    }

}
