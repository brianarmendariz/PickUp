package csulb.edu.pickup;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.os.StrictMode;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;

/**
 * Created by Brain on 2/23/2016.
 */
public class ViewEventActivity extends AppCompatActivity implements View.OnClickListener {

    private final String TAG = "brainsMessages";

    private Button _editEventButton;
    private Button _deleteEventButton;
    private Button _cancelEventButton;

    private Event _event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.view_event);

        Log.i(TAG, "onCreate");

        findViewsById();

        setupEditEventButton();
        setupDeleteEventButton();
        setupCancelEventButton();
        Intent intent = getIntent();
        String extra = intent.getStringExtra("EventID");
        Log.d("VIEW EVENT ID", extra);
        // little sloppy
        _event = getEventDetails(Integer.parseInt(extra));
        //Toast.makeText(getApplicationContext(), event.getAddress(), Toast.LENGTH_SHORT).show();
        putEventDetailsToForm(_event);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
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
        String author = event.getCreator();
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
        Toast.makeText(getApplicationContext(), name, Toast.LENGTH_SHORT).show();

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
        _editEventButton = (Button) findViewById(R.id.event_edit_btn);
        _editEventButton.requestFocus();

        _deleteEventButton = (Button) findViewById(R.id.event_delete_btn);
        _deleteEventButton.requestFocus();

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

    @Override
    public void onClick(View view) {
        if(view == _editEventButton)
        {
            Intent myIntent = new Intent(getBaseContext(), EditEventActivity.class);
            myIntent.putExtra("EventID", _event.getEventID());
            startActivity(myIntent);
            //Intent myIntent = new Intent(view.getContext(), EditEventActivity.class);
            //startActivityForResult(myIntent, 0);
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
                Intent myIntent = new Intent(view.getContext(), MapsActivity.class);
                startActivityForResult(myIntent, 0);
            }
        }
        else if(view == _cancelEventButton)
        {
            Intent myIntent = new Intent(view.getContext(), MapsActivity.class);
            startActivityForResult(myIntent, 0);
        }
    }
}