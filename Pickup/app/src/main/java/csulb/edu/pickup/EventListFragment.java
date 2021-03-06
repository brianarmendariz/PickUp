package csulb.edu.pickup;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Brain on 9/15/2016.
 */
public class EventListFragment extends Fragment
{
    ArrayList<Event> eventList;
    User thisUser;
    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        Bundle data = getActivity().getIntent().getExtras();
        thisUser = (User) data.getParcelable("USER");
        //thisUser = new User("ln", "em", "pw", "bday", "gend", "useRate", "a");

        super.onCreate(savedInstanceState);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        rootView = inflater.inflate(R.layout.event_list_container, container, false);
        ListView listView = (ListView)rootView.findViewById(R.id.listView);
        getActivity().setTitle("Event List");

        listView = getEventsAndDistancesFromServer(listView);

        setOnClickListeners(listView);

        return rootView;
    }

    private ListView getEventsAndDistancesFromServer(ListView listView)
    {
        // MAKE INTO FUNCTION
        URLConnection http = new URLConnection();
        try
        {
            LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
            Criteria criteria = new Criteria();
            String bestProvider = locationManager.getBestProvider(criteria, true);
            if (locationManager != null) {
                if (ContextCompat.checkSelfPermission(this.getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                        || ContextCompat.checkSelfPermission(this.getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                }
            }
            Location location = locationManager.getLastKnownLocation(bestProvider);
            String latitude = location.getLatitude() + "";
            String longitude = location.getLongitude() + "";
            eventList = http.sendGetEventsFromDistance(latitude, longitude, "50");
            BaseAdapter adapter = new EventListAdapter<String>(getActivity(), R.layout.event_list, eventList, getActivity(), "list");

            listView = (ListView) rootView.findViewById(R.id.event_list);
            listView.setAdapter(adapter);
        } catch (IOException e) {
            Log.e("ERROR", "EVENT LIST ERROR");
        } catch(Exception e)
        {
            e.printStackTrace();
        }
        return listView;
    }

    private void setOnClickListeners(ListView listView)
    {
        if(listView != null)
        {
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Bundle b = new Bundle();
                    b.putParcelable("USER", thisUser);
                    b.putString("EventID", eventList.get(position).getEventID() + "");
                    Fragment fragment = new ViewEventFragment1();
                    fragment.setArguments(b);
                    FragmentManager frgManager = getFragmentManager();
                    frgManager.beginTransaction().replace(R.id.content_frame, fragment).addToBackStack("EventListFragment")
                            .commit();

                    Intent thisIntent = new Intent(getActivity().getBaseContext(), MainActivity.class);
                    thisIntent.putExtras(b);

                }
            });
        }
    }
}
