package csulb.edu.pickup;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import com.facebook.login.LoginManager;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


public class CreateEventActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "brainsMessages";

    private Button cancelEventButton;
    private Button createEventButton;

    Calendar newDate = Calendar.getInstance(); // local object to couple date and time

    private EditText createDateEditText;
    private EditText createTimeEditText;

    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;

    private SimpleDateFormat dateFormatter;

    private int hour;
    private int minute;

    String[] sportStringArray = {"Badminton", "Baseball", "Basketball", "Football",
            "Handball", "Ice Hockey", "Racquetball", "Roller Hockey",
            "Softball", "Tennis", "Volleyball"};

    User thisUser;

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

        if (id == R.id.user_profile) {
            Bundle b = new Bundle();
            b.putParcelable("USER", thisUser);
            Intent userProfileIntent = new Intent(getBaseContext(), UserProfileActivity.class);
            userProfileIntent.putExtras(b);
            startActivity(userProfileIntent);

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle data = getIntent().getExtras();
        thisUser = (User) data.getParcelable("USER");

        super.onCreate(savedInstanceState);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.create_event);


        // setup spinners when page is created
        initSpinners();

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        findViewsById();
        setTimeField();
        setDateField();
        setUpCancelButton();
        setUpCreateEventButton();
    }






    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onRestart() {
        super.onRestart();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

    }

    private void initSpinners()
    {
        int idSportSpinner = R.id.sport_spinner;
        int idSportArray = R.array.sport_array;
        int idGenderSpinner = R.id.gender_spinner;
        int idGenderArray = R.array.gender_array;
        int idAgeMinSpinner = R.id.age_min_spinner;
        int idAgeMaxSpinner = R.id.age_max_spinner;
        int idMaxNumPplSpinner = R.id.max_num_ppl_spinner;
        int idMinUserRatingSpinner = R.id.min_user_rating_spinner;

        // attach values to sport spinner
        initSpinner(idSportSpinner, idSportArray);
        //Spinner sportSpinner = (Spinner)findViewById(R.id.sport_spinner);
        //sportSpinner.setAdapter(new MyAdapter(CreateEventActivity.this, R.layout.row, strings));

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

        if(spinnerId == R.id.sport_spinner){
            spinner.setAdapter(new MyCAdapter(CreateEventActivity.this, R.layout.row, sportStringArray));
        }
        else {
            // Create an ArrayAdapter with provided resources and layout
            ArrayAdapter<CharSequence> spinnnerAdapter = ArrayAdapter.createFromResource(this,
                    arrayId, android.R.layout.simple_spinner_item);
            // Specify the layout
            spinnnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            // Apply the adapter to the spinner
            spinner.setAdapter(spinnnerAdapter);
        }
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
        cancelEventButton = (Button) findViewById(R.id.event_cancel_btn);
        cancelEventButton.requestFocus();

        createEventButton = (Button) findViewById(R.id.event_create_btn);
        createEventButton.requestFocus();

        createDateEditText = (EditText) findViewById(R.id.event_date);
        createDateEditText.setInputType(InputType.TYPE_NULL);
        createDateEditText.requestFocus();

        createTimeEditText = (EditText) findViewById(R.id.event_time);
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
                        dateFormatter = new SimpleDateFormat("hh:mm a", Locale.US);
                        createTimeEditText.setText(dateFormatter.format(newDate.getTime()));
                    }
                }, hour, minute, false);
    }


    private void setUpCreateEventButton() {
        createEventButton.setOnClickListener(this);
    }

    private void setUpCancelButton() {
        cancelEventButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        if (view == createDateEditText) {
            datePickerDialog.show();
        }
        else if(view == createTimeEditText)
        {
            timePickerDialog.show();
        }
        else if (view == cancelEventButton) {
            Bundle b = new Bundle();
            b.putParcelable("USER", thisUser);
            Intent myIntent = new Intent(view.getContext(), MapsActivity.class);
            myIntent.putExtras(b);
            startActivityForResult(myIntent, 0);
        } else if (view == createEventButton) {

            try {
                Log.d("SARAH", "calling formToMap");

                Map<String, String> formMap = formToMap();
                Log.d("SARAH", "called formToMap");

                System.out.println(formMap);
                String authorName = thisUser.getFirstName()+" "+thisUser.getlastName();
                String authorEmail = thisUser.getEmail();
                String name = formMap.get("event name");
                String sport = formMap.get("sport");
                String location = formMap.get("location");
                String date = formMap.get("date");
                String time = formMap.get("time");

                Log.d("SARAH", "made it past map stuff");
                //date yyyy-mm-dd  and the time hh:min:ss
                String dateTime = (convertDate(date) + " " + convertTime(time));

                String gender = formMap.get("gender");
                String ageMin = formMap.get("age min");
                String ageMax = formMap.get("age max");
                String playerAmount = formMap.get("max num ppl");
                String minUserRating = formMap.get("min rating");

                Log.d("SARAH", "made it past map stuff");


                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                List<Address> addresses;
                addresses = geocoder.getFromLocationName(location, 1);

                if (addresses.size() > 0) {
                    double latitude = addresses.get(0).getLatitude();
                    double longitude = addresses.get(0).getLongitude();

                    //open connection
                    URLConnection http = new URLConnection();
                    Log.d("SARAH", "created URLConnection");

                    http.sendCreateEvent(authorName, authorEmail, name, sport, location,
                            String.valueOf(latitude), String.valueOf(longitude), dateTime,
                            ageMax, ageMin, minUserRating,
                            playerAmount, "P/NP", gender
                    );

                    Log.d("SARAH", "created Events");

                    //Retrieve data from server
                    http.sendGetEvents();

                    //Delete event from server
                    //http.sendDeleteEvent(1);


                    //Return to the MainActivity

                    Bundle b = new Bundle();
                    b.putParcelable("USER", thisUser);
                    Intent returnIntent = new Intent();
                    returnIntent.putExtras(b);
                    returnIntent.putExtra("result", latitude + " " + longitude + " " + name + " " + authorName);

                    setResult(MapsActivity.RESULT_OK, returnIntent);
                    finish();
                }
                else {
                    Bundle b = new Bundle();
                    b.putParcelable("USER", thisUser);
                    Intent returnIntent = new Intent();
                    returnIntent.putExtras(b);
                    setResult(MapsActivity.RESULT_CANCELED, returnIntent);
                    finish();
                }
            }
            catch (IOException e) {
                Toast.makeText(getBaseContext(), "Unable to connect to the location service. Please try again later" ,
                        Toast.LENGTH_LONG).show();
                Log.e(TAG, "Unable connect to Geocoder", e);
            }
            catch(Exception e)
            {
                Log.i(TAG, "HashMap ERROR");
            }

            // create an event when clicked
            // Event event = new Event();

            // POST it to the server

            // Let user know if successful or not


            //Intent myIntent = new Intent(view.getContext(), CreateEventActivity.class);
            //startActivityForResult(myIntent, 0);


        }
    }


    public Map<String, String> formToMap()
    {
        Log.d("SARAH", "inside formToMap");

        Map<String, String> formMap = new HashMap<>();

        // get text from the edit text box
        EditText editTextBox1 = (EditText)findViewById(R.id.event_name);
        String eventNameStr = editTextBox1.getText().toString();
        formMap.put("event name", eventNameStr);

        Spinner sportSpinner = (Spinner)findViewById(R.id.sport_spinner);
        String eventSportStr = sportSpinner.getSelectedItem().toString();
        formMap.put("sport", eventSportStr);

        EditText editTextBox4 = (EditText)findViewById(R.id.event_location);
        String eventLocationStr = editTextBox4.getText().toString();
        formMap.put("location", eventLocationStr);

        EditText editTextBox5 = (EditText) findViewById(R.id.event_date);
        String eventDateStr = editTextBox5.getText().toString();
        formMap.put("date", eventDateStr);

        EditText editTextBox6 = (EditText) findViewById(R.id.event_time);
        String eventTimeStr = editTextBox6.getText().toString();
        formMap.put("time", eventTimeStr);

        Spinner genderSpinner = (Spinner)findViewById(R.id.gender_spinner);
        String eventGenderStr = genderSpinner.getSelectedItem().toString();
        formMap.put("gender", eventGenderStr);

        Spinner ageGroupMinSpinner = (Spinner)findViewById(R.id.age_min_spinner);
        String eventAgeGroupMinStr = ageGroupMinSpinner.getSelectedItem().toString();
        formMap.put("age min", eventAgeGroupMinStr);

        Spinner ageGroupMaxSpinner = (Spinner)findViewById(R.id.age_max_spinner);
        String eventAgeGroupMaxStr = ageGroupMaxSpinner.getSelectedItem().toString();
        formMap.put("age max", eventAgeGroupMaxStr);

        String eventAgeGroupStr = eventAgeGroupMinStr + " " + eventAgeGroupMaxStr;

        Spinner maxNumPplSpinner = (Spinner)findViewById(R.id.max_num_ppl_spinner);
        String eventMaxNumPplStr = maxNumPplSpinner.getSelectedItem().toString();
        formMap.put("max num ppl", eventMaxNumPplStr);

        Spinner minUserRatingSpinner = (Spinner)findViewById(R.id.min_user_rating_spinner);
        String eventMinUserRatingStr = minUserRatingSpinner.getSelectedItem().toString();
        formMap.put("min rating", eventMinUserRatingStr);

        Log.d("SARAH", "made it past spinners in formToMap");


        String text = String.format("1: %s \n2: %s \n3: %s " +
                        "\n4: %s \n5: %s \n6: %s \n7: %s \n8: %s" +
                        "\n9: %s", eventNameStr, eventSportStr,
                eventLocationStr, eventDateStr, eventTimeStr, eventGenderStr, eventAgeGroupStr,
                eventMaxNumPplStr, eventMinUserRatingStr);

        Log.d("SARAH", "made it past formatString");

        // Log to show that the vars are correct
        Log.i(TAG, text);

        return formMap;
    }


    //Convert date to yyyy-mm-dd format
    private String convertDate(String date) {
        String convertedStr = "";
        String y, m, d;

        d = date.substring(0, date.indexOf("-"));
        m = date.substring(3,5);
        y = date.substring(date.length() - 4);
        convertedStr = y + "-" + m + "-" + d;
        return convertedStr;
    }

    //Convert the time to format hh:min:00
    private String convertTime(String time) {
        String convertedTime = "";
        //h and m for hour and min

        String h = time.substring(0, time.indexOf(":"));
        String m = time.substring(3, 5);
        int hour = 0;
        String timeAMPM = time.substring(time.length()-2);
        if (timeAMPM.equals("PM")) {
            hour = Integer.parseInt(h);
            hour += 12;
            h = String.valueOf(hour);
        }
        convertedTime = h + ":" + m + ":00";
        return convertedTime;
    }

    public class MyCAdapter extends ArrayAdapter<String> {

        int arr_images[] = {R.drawable.badminton_icon,
                R.drawable.baseball_icon, R.drawable.basketball_icon, R.drawable.football_icon,
                R.drawable.handball_icon, R.drawable.icehockey_icon, R.drawable.racquetball_icon,
                R.drawable.rollerhockey_icon, R.drawable.softball_icon, R.drawable.tennis_icon,
                R.drawable.volleyball_icon};

        public MyCAdapter(Context context, int textViewResourceId, String[] objects) {
            super(context, textViewResourceId, objects);
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        public View getCustomView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = getLayoutInflater();
            View row = inflater.inflate(R.layout.row, parent, false);
            TextView label = (TextView) row.findViewById(R.id.company);
            label.setText(sportStringArray[position]);

//            TextView sub=(TextView)row.findViewById(R.id.sub);
//            sub.setText(subs[position]        );

            ImageView icon = (ImageView) row.findViewById(R.id.image);
            icon.setImageResource(arr_images[position]);

            return row;
        }
    }
}