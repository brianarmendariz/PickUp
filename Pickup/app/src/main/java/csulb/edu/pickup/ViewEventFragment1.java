package csulb.edu.pickup;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


/**
 * Created by Brian on 11/26/2016.
 */
public class ViewEventFragment1 extends Fragment implements View.OnClickListener {

    private Event _event;
    private Context context;

    User thisUser;

    View rootView;

    Animation animAlpha;

    ArrayList<User> rsvpList;

    Button viewEventRSVPButton;
    Button viewEventUNRSVPButton;
    Button viewEventMapButton;
    Button viewEventMapCenterButton;
    Button viewEventEditButton;
    Button viewEventDeleteButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle data = getActivity().getIntent().getExtras();
        thisUser = (User) data.getParcelable("USER");
        //thisUser = new User("Sarah", "Shibley", "sarahshib@hotmail.com","abcd","1994-10-12","female", "");
        getActivity().setTitle("View Event");

        super.onCreate(savedInstanceState);

        animAlpha = AnimationUtils.loadAnimation(this.getActivity(), R.anim.anim_alpha);

        Bundle extras = getArguments();
        String extra = extras.getString("EventID");

        rsvpList = null;
        _event = getEventDetails(Integer.parseInt(extra));

        rootView = inflater.inflate(R.layout.view_event1, container, false);

        createFlyer(_event);

        addCreatorPicture();

        setupButtons();

        return rootView;
    }

    public Event getEventDetails(int eventID)
    {
        URLConnection http = new URLConnection();
        Event event = null;
        try {
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

            event = http.sendGetEventFromDistance(eventID, latitude, longitude);
        } catch(IOException e)
        {
            Toast.makeText(getActivity().getApplicationContext(), "404: Event Not Found",
                    Toast.LENGTH_LONG).show();
        }
        return event;
    }

    public void createFlyer(Event event)
    {
        TextView textView_name = (TextView)rootView.findViewById(R.id.view_event_event_name);
        textView_name.setText(event.getName());

        TextView textView_sport = (TextView)rootView.findViewById(R.id.view_event_sport);
        textView_sport.setText(event.getSport());

        TextView textView_playerAmount = (TextView)rootView.findViewById(R.id.view_event_player_amount);
        String playerAmountStr = getPlayerAmountForEvent(event.getEventID()) + " RSVP";
        textView_playerAmount.setText(playerAmountStr);

        TextView textView_eventDistance = (TextView)rootView.findViewById(R.id.view_event_distance_away);
        textView_eventDistance.setText(event.getDistance() + " mi.");

        TextView textView_eventAge = (TextView)rootView.findViewById(R.id.view_event_age);
        textView_eventAge.setText(event.getAgeMin() + "-" + event.getAgeMax() + " years old");

        TextView textView_category = (TextView)rootView.findViewById(R.id.view_event_category);
        textView_category.setText(event.getCategory());

        TextView textView_skill = (TextView)rootView.findViewById(R.id.view_event_skill);
        textView_skill.setText(event.getSkill());

        TextView textView_environment = (TextView)rootView.findViewById(R.id.view_event_environment_terrain);
        textView_environment.setText(event.getEnvironment() + " - " + event.getTerrain());

        TextView textView_eventLocation = (TextView)rootView.findViewById(R.id.view_event_location);
        textView_eventLocation.setText(event.getAddress());

        TextView textView_startDate = (TextView)rootView.findViewById(R.id.view_event_start_date_time);
        String fStartDate = getFormattedDate(event.getEventStartDate(), event.getEventStartTime());
        textView_startDate.setText(fStartDate);

        TextView textView_endDate = (TextView)rootView.findViewById(R.id.view_event_end_date_time);
        String fEndDate = getFormattedDate(event.getEventEndDate(), event.getEventEndTime());
        textView_endDate.setText(fEndDate);
    }

    public int getPlayerAmountForEvent(int eventID)
    {
        // get number of people RSVP'd to event
        URLConnection http = new URLConnection();
        try {
            rsvpList = http.sendGetRSVPList(eventID);
        } catch(IOException e)
        {
            e.printStackTrace();
        }

        return rsvpList.size();
    }

    public static String getFormattedDate(String a_dateStr, String a_timeStr)
    {
        String[] dateSplit = a_dateStr.split("-");
        int year = Integer.parseInt(dateSplit[0]);
        int month = Integer.parseInt(dateSplit[1]);
        int day = Integer.parseInt(dateSplit[2]);

        String[] timeSplit = a_timeStr.split(":");
        int hour = Integer.parseInt(timeSplit[0]);
        int minute = Integer.parseInt(timeSplit[1]);
        int second = Integer.parseInt(timeSplit[2]);

        // month - 1 b/c 0 index
        Calendar cal = new GregorianCalendar(year, month-1, day, hour, minute, second);
        cal.add(Calendar.DATE, 0);
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, MMMM d, yyyy 'at' hh:mm a");
        String formatted = sdf.format(cal.getTime()); // Output "Wed Sep 26 14:23:28 EST 2012"

        return formatted;
    }

    public void addCreatorPicture()
    {
        String creatorPicture = getUserPicturePath(_event.getCreatorEmail());

        //ImageView Setup
        ImageView creatorPicImageView = (ImageView)rootView.findViewById(R.id.view_event_profile_pic);

        // use default if empty
        if(creatorPicture.equals("") || creatorPicture == null)
        {
            //setting image resource
            Bitmap bm = BitmapFactory.decodeResource(getResources(),
                    R.drawable.com_facebook_profile_picture_blank_portrait);
            Bitmap resized = Bitmap.createScaledBitmap(bm, 150, 150, true);
            Bitmap conv_bm = BitmapHelper.getRoundedRectBitmap(resized, 150);
            creatorPicImageView.setImageBitmap(conv_bm);
        }
        else // use User's profile picture
        {
            byte[] byteArray = Base64.decode(creatorPicture, Base64.DEFAULT);
            Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

            // set the picture
            creatorPicImageView.setImageBitmap(bmp);

            DisplayMetrics dm = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);

            creatorPicImageView.setMinimumHeight(dm.heightPixels);
            creatorPicImageView.setMinimumWidth(dm.widthPixels);
        }
    }

    private String getUserPicturePath(String creatorEmail)
    {
        URLConnection http = new URLConnection();

        String creatorPicture = "";
        try
        {
            creatorPicture = http.sendGetProfilePicture(creatorEmail);
        } catch(IOException e)
        {

        }
        return creatorPicture;
    }

    public void setupButtons()
    {
        // edit and delete buttons should only be shown to the creator of the event
        if(thisUser.getEmail().equals(_event.getCreatorEmail()))
        {
            // find the buttons
            viewEventMapCenterButton = (Button)rootView.findViewById(R.id.view_event_map_center_btn);
            viewEventEditButton = (Button)rootView.findViewById(R.id.view_event_edit_btn);
            viewEventDeleteButton = (Button)rootView.findViewById(R.id.view_event_delete_btn);

            // add onclicklisteners to each button
            viewEventMapCenterButton.setOnClickListener(this);
            viewEventEditButton.setOnClickListener(this);
            viewEventDeleteButton.setOnClickListener(this);

            viewEventMapCenterButton.setVisibility(View.VISIBLE);
            viewEventEditButton.setVisibility(View.VISIBLE);
            viewEventDeleteButton.setVisibility(View.VISIBLE);
        }
        else
        {
            // find the buttons
            viewEventRSVPButton = (Button)rootView.findViewById(R.id.view_event_rsvp_btn);
            viewEventUNRSVPButton = (Button)rootView.findViewById(R.id.view_event_unrsvp_btn);
            viewEventMapButton = (Button)rootView.findViewById(R.id.view_event_map_btn);

            // add onclicklisteners to each button
            viewEventRSVPButton.setOnClickListener(this);
            viewEventUNRSVPButton.setOnClickListener(this);
            viewEventMapButton.setOnClickListener(this);

            String rsvpStr = checkRSVP(thisUser.getEmail());

            if(rsvpStr.equals("true"))
            {
                viewEventUNRSVPButton.setVisibility(View.VISIBLE);
            }
            else
            {
                viewEventRSVPButton.setVisibility(View.VISIBLE);
            }
            viewEventMapButton.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View view)
    {
        if(view == viewEventMapButton || view == viewEventMapCenterButton)
        {
            Bundle args = new Bundle();
            Fragment fragment = new MapsFragment();
            fragment.setArguments(args);
            FragmentManager frgManager = getFragmentManager();
            frgManager.beginTransaction().replace(R.id.content_frame, fragment)
                    .addToBackStack( "ViewEventFragment1" )
                    .commit();
        }
        else if(view == viewEventRSVPButton){
            URLConnection http = new URLConnection();
            try {
                http.sendRSVP(thisUser.getEmail(), _event.getEventID() + "");
            } catch (IOException e) {
                e.printStackTrace();
            }

            //_event is the event that was clicked
            if(Integer.parseInt(thisUser.getUserRating()) >= _event.getMinUserRating())
            {
                viewEventRSVPButton.setVisibility(View.INVISIBLE);
                viewEventUNRSVPButton.setVisibility(View.VISIBLE);
            }
            else {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("You cannot RSVP to this event because your user rating " +
                        "is lower than the event's minimum user rating.");
                builder.setCancelable(true);

                builder.setPositiveButton(
                        "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert = builder.create();
                alert.show();
            }

        }
        else if(view == viewEventUNRSVPButton){
            URLConnection http = new URLConnection();
            try {
                http.sendUnRSVP(thisUser.getEmail(), _event.getEventID() + "");
            } catch (IOException e) {
                e.printStackTrace();
            }

            viewEventUNRSVPButton.setVisibility(View.INVISIBLE);
            viewEventRSVPButton.setVisibility(View.VISIBLE);
        }
        if(view == viewEventEditButton)
        {
            //Go to edit activity
            Bundle args = new Bundle();
            Fragment fragment = new EditEventFragment();

            args.putString("EventID", _event.getEventID() + "");
            fragment.setArguments(args);
            FragmentManager frgManager = getFragmentManager();
            frgManager.beginTransaction().replace(R.id.content_frame, fragment).addToBackStack( "View Event" )
                    .commit();
        }
        else if(view == viewEventDeleteButton)
        {
//            Toast.makeText(getApplicationContext(), "delete", Toast.LENGTH_SHORT).show();
            String response = "";
            try {
                URLConnection http = new URLConnection();
                response = http.sendDeleteEvent(_event.getEventID());
            } catch(IOException e)
            {
                e.printStackTrace();
            } finally
            {
                if(response.equals("true"))
                {
                    String message = _event.getName() + " has been deleted.";
                    Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();

                    Bundle args = new Bundle();
                    Fragment fragment = new MapsFragment();

                    args.putString("result", "delete");

                    args.putString("EventID", _event.getEventID() + "");
                    fragment.setArguments(args);
                    FragmentManager frgManager = getFragmentManager();
                    frgManager.beginTransaction().replace(R.id.content_frame, fragment).addToBackStack("Map")
                            .commit();
                }
                else
                {
                    String message = _event.getName() + " could not be deleted.";
                    Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private String checkRSVP(String username)
    {
        URLConnection http = new URLConnection();

        String response = "";
        try
        {
            response = http.sendCheckRSVP(_event.getEventID(), username);
        } catch(IOException e)
        {

        }

        return response;
    }
}

