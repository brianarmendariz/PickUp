package csulb.edu.pickup;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
import com.facebook.share.widget.ShareDialog;

import java.io.IOException;

/**
 * Created by Brain on 2/23/2016.
 */
public class ViewEventActivity extends AppCompatActivity implements View.OnClickListener {

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

    User thisUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Bundle data = getIntent().getExtras();
        thisUser = (User) data.getParcelable("USER");
        //thisUser = new User("Sarah", "Shibley", "sarahshib@hotmail.com","abcd","1994-10-12","female", "");

        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.view_event);


        Log.i(TAG, "onCreate");

        // little sloppy
        Intent intent = getIntent();
        String extra = intent.getStringExtra("EventID");
        _event = getEventDetails(Integer.parseInt(extra));


        Log.d("VIEW EVENT ID", extra);

        LinearLayout ll = (LinearLayout) findViewById(R.id.view_event_buttons);
        if(thisUser.getEmail().equals(_event.getCreatorEmail())) {
            Button editButton = new Button(this);
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


            Button deleteButton = new Button(this);
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
                    Button unRSVPButton = new Button(this);
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
                    Button RSVPButton = new Button(this);
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

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.user_profile) {
            Bundle b = new Bundle();
            b.putParcelable("USER", thisUser);
            Intent userProfileIntent = new Intent(getBaseContext(), UserProfileActivity.class);
            userProfileIntent.putExtras(b);
            startActivity(userProfileIntent);

        }

        if (id == R.id.calendar) {
            Bundle b = new Bundle();
            b.putParcelable("USER", thisUser);
            Intent calendarIntent = new Intent(getBaseContext(), CalendarActivity.class);
            calendarIntent.putExtras(b);
            startActivity(calendarIntent);
        }

        if(id==R.id.map){
            Bundle b = new Bundle();
            b.putParcelable("USER", thisUser);
            Intent mapIntent = new Intent(getBaseContext(), MapsActivity.class);
            mapIntent.putExtras(b);
            startActivity(mapIntent);
        }

        if(id==R.id.edit_settings){
            Bundle b = new Bundle();
            b.putParcelable("USER", thisUser);
            Intent mapIntent = new Intent(getBaseContext(), EditSettingsActivity.class);
            mapIntent.putExtras(b);
            startActivity(mapIntent);
        }

        if (id == R.id.user_logout) {
            LoginManager.getInstance().logOut();
            Intent loginActivityIntent = new Intent(getBaseContext(), LoginActivity.class);

            loginActivityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(loginActivityIntent);
            finish();
        }

        return super.onOptionsItemSelected(item);
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
            Toast.makeText(getApplicationContext(), "404: Event Not Found",
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

        TextView eventViewName = (TextView) findViewById(R.id.event_view_name);
        if(name.length() > 10)
            eventViewName.setMovementMethod(new ScrollingMovementMethod());
        eventViewName.setText(name);

        TextView eventViewCreator = (TextView) findViewById(R.id.event_view_creator);
        eventViewCreator.setText(author);

        TextView eventViewSport = (TextView) findViewById(R.id.event_view_sport);
        eventViewSport.setText(sport);

        TextView eventViewLocation = (TextView) findViewById(R.id.event_view_location);
        eventViewLocation.setText(address);

        TextView eventViewDate = (TextView) findViewById(R.id.event_view_date);
        eventViewDate.setText(date);

        TextView eventViewTime = (TextView) findViewById(R.id.event_view_time);
        eventViewTime.setText(time);

        TextView eventViewGender = (TextView) findViewById(R.id.event_view_gender);
        eventViewGender.setText(gender);

        TextView eventViewAgeGroup = (TextView) findViewById(R.id.event_view_age_group);
        eventViewAgeGroup.setText(ageMin + " " + ageMax);

        TextView eventViewMaxNumPpl = (TextView) findViewById(R.id.event_view_max_num_ppl);
        eventViewMaxNumPpl.setText(maxNumPpl);

        TextView eventViewMinUserRating = (TextView) findViewById(R.id.event_view_min_user_rating);
        eventViewMinUserRating.setText(minUserRating);

    }

    public void findViewsById()
    {
        LinearLayout ll = (LinearLayout) findViewById(R.id.view_event_buttons);

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
        _shareOnFBButton = (Button) findViewById(R.id.share_on_facebook);
        _MMButton = (Button) findViewById(R.id.MMButton);
        _cancelEventButton = (Button) findViewById(R.id.view_cancel_btn);
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
                    ShareLinkContent linkContent = new ShareLinkContent.Builder()
                            .setContentTitle("Events")
                            .setContentDescription(
                                    "simple event")
                            .build();

                    shareDialog.show(linkContent);  // Show facebook ShareDialog
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

            Bundle b = new Bundle();
            b.putParcelable("USER", thisUser);
            Intent startIntent = new Intent(getBaseContext(),EditEventActivity.class);
            startIntent.putExtras(b);
            startIntent.putExtra("EventID", _event.getEventID());
            startActivity(startIntent);
            finish();

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
                Bundle b = new Bundle();
                b.putParcelable("USER", thisUser);
                Intent returnIntent = new Intent(view.getContext(), MapsActivity.class);
                returnIntent.putExtras(b);
                returnIntent.putExtra("result", "delete");
                setResult(MapsActivity.RESULT_OK, returnIntent);
                finish();
            }
        }
        else if(view == _cancelEventButton)
        {
            Bundle b = new Bundle();
            b.putParcelable("USER", thisUser);
            Intent returnIntent = new Intent();
            returnIntent.putExtras(b);
            setResult(MapsActivity.RESULT_CANCELED, returnIntent);
            finish();

        }
        else if(view == _RSVPEventButton){
            URLConnection http = new URLConnection();
            try {
                http.sendRSVP(thisUser.getEmail(), _event.getEventID());
            } catch (IOException e) {
                e.printStackTrace();
            }
            LinearLayout ll = (LinearLayout) findViewById(R.id.view_event_buttons);
            ll.removeView(_RSVPEventButton);
            Button unRSVPButton = new Button(this);
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
        else if(view == _UnRSVPEventButton){
            URLConnection http = new URLConnection();
            try {
                http.sendUnRSVP(thisUser.getEmail(), _event.getEventID());
            } catch (IOException e) {
                e.printStackTrace();
            }
            LinearLayout ll = (LinearLayout) findViewById(R.id.view_event_buttons);
            ll.removeView(_UnRSVPEventButton);
            Button RSVPButton = new Button(this);
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

            Intent intent = new Intent(getBaseContext(), MMActivity.class);
            startActivity(intent);
        }
    }

}