package com.brain.myapplication;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by Brain on 2/16/2016.
 */
public class EditEventActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "brainsMessages";

    private Button cancelEventButton;
    private Button saveChangesButton;

    Calendar newDate = Calendar.getInstance(); // local object to couple date and time

    private EditText createDateEditText;
    private EditText createTimeEditText;

    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;

    private SimpleDateFormat dateFormatter;

    private int hour;
    private int minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_event);

//        Log.i(TAG, "onCreate");


        // setup spinners when page is created
        initSpinners();

        findViewsById();

        setDateField();
        setTimeField();
        setUpCancelButton();
        setUpSaveChangesEventButton();

        populateForm(getEventDetails(1));
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

    @Override
    protected void onStart() {
        super.onStart();
//        Log.i(TAG, "onStart");
    }


    @Override
    protected void onResume() {
        super.onResume();
//        Log.i(TAG, "onResume");
    }


    @Override
    protected void onPause() {
        super.onPause();
//        Log.i(TAG, "onPause");
    }


    @Override
    protected void onStop() {
        super.onStop();
//        Log.i(TAG, "onStop");
    }


    @Override
    protected void onRestart() {
        super.onRestart();
//        Log.i(TAG, "onRestart");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
//        Log.i(TAG, "onDestroy");
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        Log.i(TAG, "onSaveInstanceState");
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
//        Log.i(TAG, "onRestoreInstanceState");
    }

    private void initSpinners()
    {
        int idSportSpinner = R.id.edit_sport_spinner;
        int idSportArray = R.array.sport_array;
        int idGenderSpinner = R.id.edit_gender_spinner;
        int idGenderArray = R.array.gender_array;
        int idAgeMinSpinner = R.id.edit_age_min_spinner;
        int idAgeMaxSpinner = R.id.edit_age_max_spinner;
        int idMaxNumPplSpinner = R.id.edit_max_num_ppl_spinner;
        int idMinUserRatingSpinner = R.id.edit_min_user_rating_spinner;

        // attach values to sport spinner
        initSpinner(idSportSpinner, idSportArray);

        // attach values to gender spinner
        initSpinner(idGenderSpinner, idGenderArray);

        // init min & max age, max num ppl, & min user rating spinners
        initNumSpinner(idAgeMinSpinner, 5, 100);
        initNumSpinner(idAgeMaxSpinner, 5, 100);
        initNumSpinner(idMaxNumPplSpinner, 2, 30);
        initNumSpinner(idMinUserRatingSpinner, -10, 20);
    }

    private void initSpinner(int spinnerId, int arrayId)
    {
        // load values from resources to populate Gender spinner
        Spinner spinner = (Spinner) findViewById(spinnerId);
        // Create an ArrayAdapter with provided resources and layout
        ArrayAdapter<CharSequence> spinnnerAdapter = ArrayAdapter.createFromResource(this,
                arrayId, android.R.layout.simple_spinner_item);
        // Specify the layout
        spinnnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(spinnnerAdapter);
    }

    private void initNumSpinner(int spinnerId, int begin, int end)
    {
        List<String> list=new ArrayList<String>();
        for(int i = begin; i < end; i++) {
            list.add(i + "");
        }
        final Spinner sp=(Spinner) findViewById(spinnerId);
        ArrayAdapter<String> adp = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, list);
        adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(adp);
    }

    private void findViewsById() {
        cancelEventButton = (Button) findViewById(R.id.edit_event_cancel_btn);
        cancelEventButton.requestFocus();

        saveChangesButton = (Button) findViewById(R.id.edit_save_changes_btn);
        saveChangesButton.requestFocus();

        createDateEditText = (EditText) findViewById(R.id.edit_event_date);
        createDateEditText.setInputType(InputType.TYPE_NULL);
        createDateEditText.requestFocus();

        createTimeEditText = (EditText) findViewById(R.id.edit_event_time);
        createTimeEditText.setInputType(InputType.TYPE_NULL);
        createTimeEditText.requestFocus();
    }

    private void setDateField() {
        createDateEditText.setOnClickListener(this);

        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
                createDateEditText.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    private void setTimeField() {
        createTimeEditText.setOnClickListener(this);

//        Calendar newCalendar = Calendar.getInstance();

        timePickerDialog =
                new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                    public void onTimeSet(TimePicker view, int selectedHour,
                                          int selectedMinute) {
                        hour = selectedHour;
                        minute = selectedMinute;
                        newDate.set(Calendar.HOUR, hour);
                        newDate.set(Calendar.MINUTE, minute);
                        newDate.set(Calendar.SECOND, 0);
                        dateFormatter = new SimpleDateFormat("h:mm a", Locale.US);
                        createTimeEditText.setText(dateFormatter.format(newDate.getTime()));
                    }
                }, hour, minute, false);
    }

    private void setUpSaveChangesEventButton()
    {
        saveChangesButton.setOnClickListener(this);
    }

    private void setUpCancelButton()
    {
        cancelEventButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == createDateEditText)
        {
            datePickerDialog.show();
        }
        else if(view == createTimeEditText)
        {
            timePickerDialog.show();
        }
        else if(view == cancelEventButton)
        {
            Intent myIntent = new Intent(view.getContext(), MainActivity.class); //change to map
            startActivityForResult(myIntent, 0);
        }
        else if(view == saveChangesButton) {
            URLConnection http = new URLConnection();

            
            Intent myIntent = new Intent(view.getContext(), MainActivity.class); //change to map
            startActivityForResult(myIntent, 0);
        }
    }

    public void sendCreateEvent(Map<String, String> formMap)
    {

        //   HttpClient client = new HttpClient();

    }

    public void populateForm(Event event)
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

        // get text from the edit text box
        EditText editTextBox1 = (EditText)findViewById(R.id.edit_event_name);
        editTextBox1.setText(name);

        EditText editTextBox2 = (EditText)findViewById(R.id.edit_event_creator);
        editTextBox2.setText(author);

        Spinner sportSpinner = (Spinner)findViewById(R.id.edit_sport_spinner);
        String[] sportArray = getResources().getStringArray(R.array.sport_array);
        for(int i = 0; i < sportArray.length; i++)
        {
            if(sport.equalsIgnoreCase(sportArray[i]))
            {
                sportSpinner.setSelection(i);
            }
        }

        EditText editTextBox4 = (EditText)findViewById(R.id.edit_event_location);
        editTextBox4.setText(address);

        EditText editTextBox5 = (EditText) findViewById(R.id.edit_event_date);
        editTextBox5.setText(date);

        EditText editTextBox6 = (EditText) findViewById(R.id.edit_event_time);
        editTextBox6.setText(time);

        Spinner genderSpinner = (Spinner)findViewById(R.id.edit_gender_spinner);
        String[] genderArray = getResources().getStringArray(R.array.gender_array);
        for(int i = 0; i < genderArray.length; i++)
        {
            if(gender.equalsIgnoreCase(genderArray[i]))
            {
                genderSpinner.setSelection(i);
            }
        }

        int ageGroupMin = 5;
        int ageGroupMax = 100;
        Spinner ageGroupMinSpinner = (Spinner)findViewById(R.id.edit_age_min_spinner);
        for(int i = ageGroupMin; i < ageGroupMax; i++)
        {
            if(Integer.parseInt(ageMin) == i)
            {
                ageGroupMinSpinner.setSelection(i - ageGroupMin);
            }
        }

        Spinner ageGroupMaxSpinner = (Spinner)findViewById(R.id.edit_age_max_spinner);
        for(int i = ageGroupMin; i < ageGroupMax; i++)
        {
            if(Integer.parseInt(ageMax) == i)
            {
                ageGroupMaxSpinner.setSelection(i - ageGroupMin);
            }
        }

        int maxNumMin = 2;
        int maxNumMax = 30;
        Spinner maxNumPplSpinner = (Spinner)findViewById(R.id.edit_max_num_ppl_spinner);
        for(int i = maxNumMin; i < maxNumMax; i++)
        {
            if(Integer.parseInt(maxNumPpl) == i)
            {
                maxNumPplSpinner.setSelection(i - maxNumMin);
            }
        }

        int minUserRatingMin = -10;
        int minUserRatingMax = 20;
        Spinner minUserRatingSpinner = (Spinner)findViewById(R.id.edit_min_user_rating_spinner);
        for(int i = minUserRatingMin; i < minUserRatingMax; i++)
        {
            if(Integer.parseInt(minUserRating) == i)
            {
                minUserRatingSpinner.setSelection(i - minUserRatingMin);
            }
        }
    }

    public void populateForm(Map<String, String> eventDetailsMap)
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

        // get text from the edit text box
        EditText editTextBox1 = (EditText)findViewById(R.id.edit_event_name);
        editTextBox1.setText(name);

        EditText editTextBox2 = (EditText)findViewById(R.id.edit_event_creator);
        editTextBox2.setText(author);

        Spinner sportSpinner = (Spinner)findViewById(R.id.edit_sport_spinner);
        String[] sportArray = getResources().getStringArray(R.array.sport_array);
        for(int i = 0; i < sportArray.length; i++)
        {
            if(sport.equalsIgnoreCase(sportArray[i]))
            {
                sportSpinner.setSelection(i);
            }
        }

        EditText editTextBox4 = (EditText)findViewById(R.id.edit_event_location);
        editTextBox4.setText(location);

        EditText editTextBox5 = (EditText) findViewById(R.id.edit_event_date);
        editTextBox5.setText(date);

        EditText editTextBox6 = (EditText) findViewById(R.id.edit_event_time);
        editTextBox6.setText(time);

        Spinner genderSpinner = (Spinner)findViewById(R.id.edit_gender_spinner);
        String[] genderArray = getResources().getStringArray(R.array.gender_array);
        for(int i = 0; i < genderArray.length; i++)
        {
            if(gender.equalsIgnoreCase(genderArray[i]))
            {
                genderSpinner.setSelection(i);
            }
        }

        int ageGroupMin = 5;
        int ageGroupMax = 100;
        Spinner ageGroupMinSpinner = (Spinner)findViewById(R.id.edit_age_min_spinner);
        for(int i = ageGroupMin; i < ageGroupMax; i++)
        {
            if(Integer.parseInt(ageMin) == i)
            {
                ageGroupMinSpinner.setSelection(i - ageGroupMin);
            }
        }

        Spinner ageGroupMaxSpinner = (Spinner)findViewById(R.id.edit_age_max_spinner);
        for(int i = ageGroupMin; i < ageGroupMax; i++)
        {
            if(Integer.parseInt(ageMax) == i)
            {
                ageGroupMaxSpinner.setSelection(i - ageGroupMin);
            }
        }

        int maxNumMin = 2;
        int maxNumMax = 30;
        Spinner maxNumPplSpinner = (Spinner)findViewById(R.id.edit_max_num_ppl_spinner);
        for(int i = maxNumMin; i < maxNumMax; i++)
        {
            if(Integer.parseInt(maxNumPpl) == i)
            {
                maxNumPplSpinner.setSelection(i - maxNumMin);
            }
        }

        int minUserRatingMin = -10;
        int minUserRatingMax = 20;
        Spinner minUserRatingSpinner = (Spinner)findViewById(R.id.edit_min_user_rating_spinner);
        for(int i = minUserRatingMin; i < minUserRatingMax; i++)
        {
            if(Integer.parseInt(minUserRating) == i)
            {
                minUserRatingSpinner.setSelection(i - minUserRatingMin);
            }
        }
    }

    public Event getEventDetails(int eventID)
    {
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

    public Map<String, String> getEventDetails()
    {
        Map<String, String> eventDetailMap = new HashMap<String,
                String>();

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

}
