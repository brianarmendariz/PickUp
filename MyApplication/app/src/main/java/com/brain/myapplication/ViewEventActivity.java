package com.brain.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Brain on 2/23/2016.
 */
public class ViewEventActivity extends AppCompatActivity implements View.OnClickListener {

    private final String TAG = "brainsMessages";

    private Button editEventButton;
    private Button deleteEventButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_event);

        Log.i(TAG, "onCreate");

        findViewsById();

        setupEditEventButton();
        setupDeleteEventButton();

        // little sloppy
        putEventDetailsToForm(getEventDetails());
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

    public Map<String, String> getEventDetails()
    {
        Map<String, String> eventDetailMap = new HashMap<String, String>();

        // reality we will get these values from the db
        String author = "Brain";
        eventDetailMap.put("author", author);

        String name = "Basketball at the Park";
        eventDetailMap.put("event name", name);

        String sport = "Basketball";
        eventDetailMap.put("sport", sport);

        String location = "csulb";
        eventDetailMap.put("location", location);

        String date = "2/23/16";
        eventDetailMap.put("date", date);

        String time = "6:15 pm";
        eventDetailMap.put("time", time);

        String gender = "any";
        eventDetailMap.put("gender", gender);

        String ageMin = "16";
        eventDetailMap.put("age min", ageMin);

        String ageMax = "35";
        eventDetailMap.put("age max", ageMax);

        String maxNumPpl = "12";
        eventDetailMap.put("max num ppl", maxNumPpl);

        String minRating = "0";
        eventDetailMap.put("min rating", minRating);

        return eventDetailMap;
    }

    public void putEventDetailsToForm(Map<String, String> eventDetailsMap)
    {
        String author = eventDetailsMap.get("author");
        String name = eventDetailsMap.get("event name");
        String sport = eventDetailsMap.get("sport");
        String location = eventDetailsMap.get("location");
        String date = eventDetailsMap.get("date");
        String time = eventDetailsMap.get("time");
        String gender = eventDetailsMap.get("gender");
        String ageMin = eventDetailsMap.get("age min");
        String ageMax = eventDetailsMap.get("age max");
        String maxNumPpl = eventDetailsMap.get("max num ppl");
        String minUserRating = eventDetailsMap.get("min rating");

        TextView eventViewName = (TextView) findViewById(R.id.event_view_name);
        if(name.length() > 10)
            eventViewName.setMovementMethod(new ScrollingMovementMethod());
        eventViewName.setText(name);

        TextView eventViewCreator = (TextView) findViewById(R.id.event_view_creator);
        eventViewCreator.setText(author);

        TextView eventViewSport = (TextView) findViewById(R.id.event_view_sport);
        eventViewSport.setText(sport);

        TextView eventViewLocation = (TextView) findViewById(R.id.event_view_location);
        eventViewLocation.setText(location);

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
        editEventButton = (Button) findViewById(R.id.event_edit_btn);
        editEventButton.requestFocus();

        deleteEventButton = (Button) findViewById(R.id.event_delete_btn);
        deleteEventButton.requestFocus();
    }

    public void setupEditEventButton()
    {
        editEventButton.setOnClickListener(this);
    }

    public void setupDeleteEventButton()
    {
        deleteEventButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == editEventButton)
        {
            Intent myIntent = new Intent(view.getContext(), EditEventActivity.class);
            startActivityForResult(myIntent, 0);
        }
        else if(view == deleteEventButton)
        {
            Toast.makeText(getApplicationContext(), "delete", Toast.LENGTH_SHORT).show();
        }
    }
}
