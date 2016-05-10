package csulb.edu.pickup;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.os.StrictMode;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;

import java.io.IOException;

/**
 * Created by Brain on 2/23/2016.
 */
public class ViewEventFragment extends Fragment implements View.OnClickListener {

    private final String TAG = "brainsMessages";
    private Button _editEventButton;
    private Button _deleteEventButton;
    private Button _cancelEventButton;
    private Button _RSVPEventButton;
    private Button _UnRSVPEventButton;
    private Button _shareOnFBButton;
    private Button _MMButton;
    private ShareDialog shareDialog;


    private Event _event;
    private Context context;

    User thisUser;

    View rootView;

    Animation animAlpha;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle data = getActivity().getIntent().getExtras();
        thisUser = (User) data.getParcelable("USER");
        //thisUser = new User("Sarah", "Shibley", "sarahshib@hotmail.com","abcd","1994-10-12","female", "");
        getActivity().setTitle("View Event");

        super.onCreate(savedInstanceState);
        //FacebookSdk.sdkInitialize(getApplicationContext());
       // StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
       // StrictMode.setThreadPolicy(policy);
        //setContentView(R.layout.view_event);
        //context = this;
        animAlpha = AnimationUtils.loadAnimation(this.getActivity(),R.anim.anim_alpha);

        Log.i(TAG, "onCreate");

        Bundle extras = getArguments();
        String extra = extras.getString("EventID");

        //String extra = "5";
        _event = getEventDetails(Integer.parseInt(extra));


        //Log.d("VIEW EVENT ID", extra);

        rootView = inflater.inflate(R.layout.view_event, container, false);


        LinearLayout ll = (LinearLayout) rootView.findViewById(R.id.view_event_buttons);
        if(thisUser.getEmail().equals(_event.getCreatorEmail())) {
            Button editButton = new Button(getActivity());
            editButton.setText("Edit");
            editButton.setTag("event_edit_btn");
            editButton.setGravity(Gravity.CENTER);
            editButton.setTextColor(Color.parseColor("#008000"));
            editButton.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(AbsListView.LayoutParams.WRAP_CONTENT, AbsListView.LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.CENTER;
            editButton.setLayoutParams(params);
            ll.addView(editButton);
            _editEventButton = (Button) ll.findViewWithTag("event_edit_btn");
            _editEventButton.requestFocus();
            setupEditEventButton();


            Button deleteButton = new Button(this.getActivity());
            deleteButton.setText("Delete");
            deleteButton.setTag("event_delete_btn");
            deleteButton.setGravity(Gravity.CENTER);
            deleteButton.setTextColor(Color.parseColor("#008000"));
            deleteButton.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(AbsListView.LayoutParams.WRAP_CONTENT, AbsListView.LayoutParams.WRAP_CONTENT);
            params2.gravity = Gravity.CENTER;
            deleteButton.setLayoutParams(params2);
            ll.addView(deleteButton);
            _deleteEventButton = (Button) ll.findViewWithTag("event_delete_btn");
            _deleteEventButton.requestFocus();
            setupDeleteEventButton();

        }
        URLConnection http = new URLConnection();
        try {
            String[][] RSVPList = http.sendGetRSVPList(Integer.parseInt(_event.getEventID()));
            boolean hasRSVPd = false;
            for (int i = 0; i < RSVPList.length; i++) {
                Log.d("SARAH", "name"+RSVPList[i][0]);
                if (RSVPList[i][1].equals(thisUser.getEmail())) {
                    hasRSVPd = true;
                }
            }
            if(!hasRSVPd && RSVPList.length>=Integer.parseInt(_event.getMaxNumberPpl())) {

            }
            else {
                if (hasRSVPd) {
                    Button unRSVPButton = new Button(this.getActivity());
                    unRSVPButton.setText("unRSVP");
                    unRSVPButton.setTag("event_unrsvp_btn");
                    unRSVPButton.setGravity(Gravity.CENTER);
                    unRSVPButton.setTextColor(Color.parseColor("#008000"));
                    unRSVPButton.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                    ll.addView(unRSVPButton);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(AbsListView.LayoutParams.WRAP_CONTENT, AbsListView.LayoutParams.WRAP_CONTENT);
                    params.gravity = Gravity.CENTER;
                    unRSVPButton.setLayoutParams(params);
                    _UnRSVPEventButton = (Button) ll.findViewWithTag("event_unrsvp_btn");
                    _UnRSVPEventButton.requestFocus();
                    setupUnRSVPButton();
                }
                else if (RSVPList.length < Integer.parseInt(_event.getMaxNumberPpl())) {
                    Button RSVPButton = new Button(this.getActivity());
                    RSVPButton.setText("RSVP");
                    RSVPButton.setTag("event_rsvp_btn");
                    RSVPButton.setGravity(Gravity.CENTER);
                    RSVPButton.setTextColor(Color.parseColor("#008000"));
                    RSVPButton.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(AbsListView.LayoutParams.WRAP_CONTENT, AbsListView.LayoutParams.WRAP_CONTENT);
                    params.gravity = Gravity.CENTER;
                    RSVPButton.setLayoutParams(params);
                    ll.addView(RSVPButton);
                    _RSVPEventButton = (Button) ll.findViewWithTag("event_rsvp_btn");
                    _RSVPEventButton.requestFocus();
                    setupRSVPButton();
                }
            }
        }catch(IOException ie){
            ie.printStackTrace();
        }


        findViewsById();
        setUpShareOnFBButton();
        setUpMMButton();
        setupCancelEventButton();


        //Toast.makeText(getApplicationContext(), event.getAddress(), Toast.LENGTH_SHORT).show();
        putEventDetailsToForm(_event);
        return rootView;

    }



    public Event getEventDetails(int eventID)
    {
        Map<String, String> eventDetailMap = new HashMap<String, String>();

        URLConnection http = new URLConnection();
        Event event = null;
        try {
            event = http.sendGetEvent(eventID);
        } catch(IOException e)
        {
            Toast.makeText(getActivity().getApplicationContext(), "404: Event Not Found",
                    Toast.LENGTH_LONG).show();
        }
        return event;
    }

    public void putEventDetailsToForm(Event event)
    {
        String author = event.getCreatorName();
        String name = event.getName();
        String sport = event.getSport();
        String address = event.getAddress();
        String date = event.getEventDate();
        String time = event.getEventTime();
        String gender = event.getGender();
        String ageMin = event.getAgeMin();
        String ageMax = event.getAgeMax();
        String maxNumPpl = event.getMaxNumberPpl();
        String minUserRating = event.getMinUserRating();

        TextView eventViewName = (TextView) rootView.findViewById(R.id.event_view_name);
        if(name.length() > 10)
            eventViewName.setMovementMethod(new ScrollingMovementMethod());
        eventViewName.setText(name);

        TextView eventViewCreator = (TextView) rootView.findViewById(R.id.event_view_creator);
        eventViewCreator.setText(author);

        TextView eventViewSport = (TextView) rootView.findViewById(R.id.event_view_sport);
        eventViewSport.setText(sport);

        TextView eventViewLocation = (TextView) rootView.findViewById(R.id.event_view_location);
        eventViewLocation.setText(address);

        TextView eventViewDate = (TextView) rootView.findViewById(R.id.event_view_date);
        eventViewDate.setText(date);

        TextView eventViewTime = (TextView) rootView.findViewById(R.id.event_view_time);
        eventViewTime.setText(time);

        TextView eventViewGender = (TextView) rootView.findViewById(R.id.event_view_gender);
        eventViewGender.setText(gender);

        TextView eventViewAgeGroup = (TextView) rootView.findViewById(R.id.event_view_age_group);
        eventViewAgeGroup.setText(ageMin + " " + ageMax);

        TextView eventViewMaxNumPpl = (TextView) rootView.findViewById(R.id.event_view_max_num_ppl);
        eventViewMaxNumPpl.setText(maxNumPpl);

        TextView eventViewMinUserRating = (TextView) rootView.findViewById(R.id.event_view_min_user_rating);
        eventViewMinUserRating.setText(minUserRating);

    }

    public void findViewsById()
    {
        LinearLayout ll = (LinearLayout) rootView.findViewById(R.id.view_event_buttons);

        if(ll.findViewWithTag("event_edit_btn")!=null) {
            _editEventButton = (Button) ll.findViewWithTag("event_edit_btn");
            _editEventButton.requestFocus();
        }


        if(ll.findViewWithTag("event_delete_btn")!=null) {
            _deleteEventButton = (Button) ll.findViewWithTag("event_delete_btn");
            _deleteEventButton.requestFocus();
        }
        if(ll.findViewWithTag("event_rsvp_btn")!=null) {
            _RSVPEventButton = (Button) ll.findViewWithTag("event_rsvp_btn");
            _RSVPEventButton.requestFocus();
        }

        if(ll.findViewWithTag("event_unrsvp_btn")!=null) {
            _UnRSVPEventButton = (Button) ll.findViewWithTag("event_unrsvp_btn");
            _UnRSVPEventButton.requestFocus();
        }
        _shareOnFBButton = (Button) rootView.findViewById(R.id.share_on_facebook);
        _MMButton = (Button) rootView.findViewById(R.id.MMButton);
        _cancelEventButton = (Button) rootView.findViewById(R.id.view_cancel_btn);
        _cancelEventButton.requestFocus();

    }

    public void setupEditEventButton()
    {
        _editEventButton.setOnClickListener(this);
    }

    public void setupDeleteEventButton()
    {
        _deleteEventButton.setOnClickListener(this);
    }

    public void setupCancelEventButton()
    {
        _cancelEventButton.setOnClickListener(this);
    }
    public void setupRSVPButton() {
        _RSVPEventButton.setOnClickListener(this);
    }
    public void setupUnRSVPButton(){
        _UnRSVPEventButton.setOnClickListener(this);
    }
    public void setUpShareOnFBButton() {
        shareDialog = new ShareDialog(this);
        _shareOnFBButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                if (ShareDialog.canShow(ShareLinkContent.class)) {
                    TextToImage t2i = new TextToImage();
                   // t2i.createImage(_event); // creates image with transparent bkg from text
                	//t2i.createFlyer(_event); // adds text to image to create a flyer
                    Bitmap image = BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.myevent_pevent_flyer);
                    SharePhoto photo = new SharePhoto.Builder()
                            .setBitmap(image)
                            .build();
                    SharePhotoContent content = new SharePhotoContent.Builder()
                            .addPhoto(photo)
                            .build();

                    /*ShareLinkContent linkContent = new ShareLinkContent.Builder()
                            .setContentTitle("Events")
                            .setContentDescription(
                                    "Sport Event")

                                   /* .setImageUrl(Uri.parse("https://maps.googleapis.com/maps/api/staticmap?center=Brooklyn+Bridge,New+York,NY&zoom=13&size=600x300&maptype=roadmap\n" +
                                            "&markers=color:blue%7Clabel:S%7C40.702147,-74.015794&markers=color:green%7Clabel:G%7C40.711614,-74.012318\n" +
                                            "&markers=color:red%7Clabel:C%7C40.718217,-73.998284\n" +
                                            "&key=" + "AIzaSyAxuoNpmK7MCWSy6pFqo - FDIsFCubO5JBg")).
                                    setContentUrl(Uri.parse("https://maps.googleapis.com/maps/api/staticmap?center=Brooklyn+Bridge,New+York,NY&zoom=13&size=600x300&maptype=roadmap\n" +
                                            "&markers=color:blue%7Clabel:S%7C40.702147,-74.015794&markers=color:green%7Clabel:G%7C40.711614,-74.012318\n" +
                                            "&markers=color:red%7Clabel:C%7C40.718217,-73.998284\n" +
                                            "&key=" + "AIzaSyAxuoNpmK7MCWSy6pFqo - FDIsFCubO5JBg"))
                                    .build();*/



                    shareDialog.show(content);  // Show facebook ShareDialog
                }

            }
        });
    }
    public void setUpMMButton() {_MMButton.setOnClickListener(this);}

    @Override
    public void onClick(View view) {
        if(view == _editEventButton)
        {
            //Go to edit activity
            Bundle args = new Bundle();
            Fragment fragment = new EditEventFragment();
            args.putString(FragmentOne.ITEM_NAME, new DrawerItem("Edit Event", 0)
                    .getItemName());
            args.putString("EventID", _event.getEventID());
            fragment.setArguments(args);
            FragmentManager frgManager = getFragmentManager();
            frgManager.beginTransaction().replace(R.id.content_frame, fragment).addToBackStack( "View Event" )
                    .commit();


        }
        else if(view == _deleteEventButton)
        {
//            Toast.makeText(getApplicationContext(), "delete", Toast.LENGTH_SHORT).show();

            try {
                URLConnection http = new URLConnection();
                http.sendDeleteEvent(Integer.parseInt(_event.getEventID()));
            } catch(IOException e)
            {
                e.printStackTrace();
            } finally
            {

                //Go to edit activity
                Bundle args = new Bundle();
                Fragment fragment = new MapsFragment();
                args.putString(FragmentOne.ITEM_NAME, new DrawerItem("Map", 0)
                        .getItemName());
                args.putString("result", "delete");

                args.putString("EventID", _event.getEventID());
                fragment.setArguments(args);
                FragmentManager frgManager = getFragmentManager();
                frgManager.beginTransaction().replace(R.id.content_frame, fragment).addToBackStack( "Map" )
                        .commit();

            }
        }
        else if(view == _cancelEventButton)
        {
            Bundle args = new Bundle();
            Fragment fragment = new MapsFragment();
            args.putString(FragmentOne.ITEM_NAME, new DrawerItem("Map", 0)
                    .getItemName());
            fragment.setArguments(args);
            FragmentManager frgManager = getFragmentManager();
            frgManager.beginTransaction().replace(R.id.content_frame, fragment).addToBackStack( "View Event" )
                    .commit();


        }
        else if(view == _RSVPEventButton){
            URLConnection http = new URLConnection();
            try {
                http.sendRSVP(thisUser.getEmail(), _event.getEventID());
            } catch (IOException e) {
                e.printStackTrace();
            }

            //_event is the event that was clicked
            if(Integer.parseInt(thisUser.getUserRating()) >= Integer.parseInt(_event.getMinUserRating())) {
                LinearLayout ll = (LinearLayout) rootView.findViewById(R.id.view_event_buttons);
                ll.removeView(_RSVPEventButton);
                Button unRSVPButton = new Button(this.getActivity());
                unRSVPButton.setText("unRSVP");
                unRSVPButton.setTag("event_unrsvp_btn");
                unRSVPButton.setGravity(Gravity.CENTER);
                unRSVPButton.setTextColor(Color.parseColor("#008000"));
                unRSVPButton.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                ll.addView(unRSVPButton);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(AbsListView.LayoutParams.WRAP_CONTENT, AbsListView.LayoutParams.WRAP_CONTENT);
                params.gravity = Gravity.CENTER;
                unRSVPButton.setLayoutParams(params);
                _UnRSVPEventButton = (Button) ll.findViewWithTag("event_unrsvp_btn");
                _UnRSVPEventButton.requestFocus();
                setupUnRSVPButton();
            }
            else {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
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
        else if(view == _UnRSVPEventButton){
            URLConnection http = new URLConnection();
            try {
                http.sendUnRSVP(thisUser.getEmail(), _event.getEventID());
            } catch (IOException e) {
                e.printStackTrace();
            }
            LinearLayout ll = (LinearLayout) rootView.findViewById(R.id.view_event_buttons);
            ll.removeView(_UnRSVPEventButton);
            Button RSVPButton = new Button(this.getActivity());
            RSVPButton.setText("RSVP");
            RSVPButton.setTag("event_rsvp_btn");
            RSVPButton.setGravity(Gravity.CENTER);
            RSVPButton.setTextColor(Color.parseColor("#008000"));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(AbsListView.LayoutParams.WRAP_CONTENT, AbsListView.LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.CENTER;
            RSVPButton.setLayoutParams(params);
            ll.addView(RSVPButton);
            _RSVPEventButton = (Button) ll.findViewWithTag("event_rsvp_btn");
            _RSVPEventButton.requestFocus();
            setupRSVPButton();

        }

        else if (view == _MMButton) {
            view.startAnimation(animAlpha);
            Intent intent = getActivity().getIntent();
            //Intent MMIntent = new Intent(getActivity().getBaseContext(), MainActivity.class);
           // String extra = intent.getStringExtra("EventID");
            String extra = getArguments().getString("EventID");
            Bundle args = new Bundle();
            Fragment fragment = new MMFragment();
            args.putString(FragmentOne.ITEM_NAME, new DrawerItem("MatchMaking", 0)
                    .getItemName());
            args.putString("EventID", extra);
            fragment.setArguments(args);
            FragmentManager frgManager = getFragmentManager();
            frgManager.beginTransaction().replace(R.id.content_frame, fragment).addToBackStack( "View Event" )
                    .commit();


        }
    }

}