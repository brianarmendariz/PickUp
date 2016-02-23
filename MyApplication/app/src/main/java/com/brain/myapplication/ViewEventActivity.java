package com.brain.myapplication;

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

/**
 * Created by Brain on 2/23/2016.
 */
public class ViewEventActivity extends AppCompatActivity implements View.OnClickListener {

    private final String TAG = "brainsMessages";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_event);

        Log.i(TAG, "onCreate");

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
        Map<String, String> formMap = new HashMap<String, String>();

        // reality we will get these values from the db
        String author = "Brain";
        formMap.put("author", author);

        String name = "Basketball at the Park";
        formMap.put("event name", name);

        String sport = "Basketball";
        formMap.put("sport", sport);

        String location = "csulb";
        formMap.put("location", location);

        String date = "2/23/16";
        formMap.put("date", date);

        String time = "6:15 pm";
        formMap.put("time", time);

        String gender = "any";
        formMap.put("gender", gender);

        String ageMin = "16";
        formMap.put("age min", ageMin);

        String ageMax = "35";
        formMap.put("age max", ageMax);

        String maxNumPpl = "12";
        formMap.put("max num ppl", maxNumPpl);

        String minRating = "0";
        formMap.put("min rating", minRating);

        return formMap;
    }

    public void putEventDetailsToForm(Map<String, String> formMap)
    {
        String author = formMap.get("author");
        String name = formMap.get("event name");
        String sport = formMap.get("sport");
        String location = formMap.get("location");
        String date = formMap.get("date");
        String time = formMap.get("time");
        String gender = formMap.get("gender");
        String ageMin = formMap.get("age min");
        String ageMax = formMap.get("age max");
        String maxNumPpl = formMap.get("max num ppl");
        String minUserRating = formMap.get("min rating");

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

    @Override
    public void onClick(View v) {

    }
}
