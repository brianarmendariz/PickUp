package csulb.edu.pickup;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
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

        animAlpha = AnimationUtils.loadAnimation(this.getActivity(),R.anim.anim_alpha);

        Log.i(TAG, "onCreate");

        Bundle extras = getArguments();
        String extra = extras.getString("EventID");

        _event = getEventDetails(Integer.parseInt(extra));

        rootView = inflater.inflate(R.layout.view_event, container, false);


        LinearLayout ll = (LinearLayout) rootView.findViewById(R.id.view_event_buttons);
        if(thisUser.getEmail().equals(_event.getCreatorEmail()))
        {
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
            String[][] RSVPList = http.sendGetRSVPList(_event.getEventID() + "");
            boolean hasRSVPd = false;
            for (int i = 0; i < RSVPList.length; i++)
            {
                Log.d("SARAH", "name"+RSVPList[i][0]);
                if (RSVPList[i][1].equals(thisUser.getEmail())) {
                    hasRSVPd = true;
                }
            }
//            if(!hasRSVPd && RSVPList.length>=Integer.parseInt(_event.getMaxNumberPpl())) {
//
//            }
//            else {
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
                else if (RSVPList.length < _event.getTotalHeadCount()) {
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
//            }
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
        String date = event.getEventStartDate();
        String time = event.getEventStartTime();
        String gender = event.getGender();
        String ageMin = event.getAgeMin() + "";
        String ageMax = event.getAgeMax() + "";
        String maxNumPpl = event.getTotalHeadCount() + "";
        String minUserRating = event.getMinUserRating() + "";

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
    //share on facebook button
    public void setUpShareOnFBButton() {

        shareDialog = new ShareDialog(this);
        _shareOnFBButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                if (ShareDialog.canShow(SharePhotoContent.class)) {
                    SharePhoto photo = new SharePhoto.Builder()
                            .setBitmap(convertTextToImage())
                            .build();
                    SharePhotoContent content = new SharePhotoContent.Builder()
                            .addPhoto(photo)
                            .build();
                    shareDialog.show(content);
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

            args.putString("EventID", _event.getEventID() + "");
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
                http.sendDeleteEvent(_event.getEventID());
            } catch(IOException e)
            {
                e.printStackTrace();
            } finally
            {

                //Go to edit activity
                Bundle args = new Bundle();
                Fragment fragment = new MapsFragment();

                args.putString("result", "delete");

                args.putString("EventID", _event.getEventID() + "");
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

            fragment.setArguments(args);
            FragmentManager frgManager = getFragmentManager();
            frgManager.beginTransaction().replace(R.id.content_frame, fragment).addToBackStack( "View Event" )
                    .commit();


        }
        else if(view == _RSVPEventButton){
            URLConnection http = new URLConnection();
            try {
                http.sendRSVP(thisUser.getEmail(), _event.getEventID() + "");
            } catch (IOException e) {
                e.printStackTrace();
            }

            //_event is the event that was clicked
            if(Integer.parseInt(thisUser.getUserRating()) >= _event.getMinUserRating()) {
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
        else if(view == _UnRSVPEventButton){
            URLConnection http = new URLConnection();
            try {
                http.sendUnRSVP(thisUser.getEmail(), _event.getEventID() + "");
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

            args.putString("EventID", extra);
            fragment.setArguments(args);
            FragmentManager frgManager = getFragmentManager();
            frgManager.beginTransaction().replace(R.id.content_frame, fragment).addToBackStack( "View Event" )
                    .commit();


        }
    }


    //convert event to bitmap to be posted on Facebook

    private Bitmap convertTextToImage() {
        Paint paint = new Paint();

        //set the text size
        paint.setTextSize(15);
        paint.setTextAlign(Align.LEFT);

        float baseline = -paint.ascent();
        //create an image
        Bitmap image;
        //if the characters are very long in a line, make the bitmap bigger.
        if (_event.getName().length() > 40 || _event.getCreatorName().length() > 40 || _event.getAddress().length() > 40 ) {
            image = Bitmap.createBitmap(500,200, Config.ARGB_8888);
        }
        else {

            image = Bitmap.createBitmap(300, 200, Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(image);

        paint.setColor(Color.YELLOW);

        canvas.drawText("Event Name: " + _event.getName(), 0, baseline, paint);
        canvas.drawText("Creator: " + _event.getCreatorName(), 0, baseline + 30, paint);
        canvas.drawText("Sport: " + _event.getSport(), 0, baseline + 60, paint);
        canvas.drawText("Location: " + _event.getAddress() , 0, baseline + 90, paint);
        canvas.drawText("Date: " + _event.getEventStartDate(), 0, baseline +120, paint);
        canvas.drawText("Time: " + _event.getEventStartTime() , 0, baseline + 150, paint);
        canvas.drawText("Gender: " + _event.getGender(), 0, baseline + 180, paint);


        return image;
    }



}