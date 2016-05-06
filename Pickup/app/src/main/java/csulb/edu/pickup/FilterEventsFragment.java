package csulb.edu.pickup;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
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
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
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


public class FilterEventsFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "brainsMessages";

    private Button cancelButton;
    private Button filterButton;
    Calendar newDate = Calendar.getInstance(); // local object to couple date and time

    private EditText createdAtDateEditText1;
    private EditText createdAtDateEditText2;

    private EditText eventDateEditText1;
    private EditText eventDateEditText2;

    private EditText timeEditText1;
    private EditText timeEditText2;

    private DatePickerDialog eventDatePickerDialog1;
    private DatePickerDialog eventDatePickerDialog2;

    private DatePickerDialog createDatePickerDialog1;
    private DatePickerDialog createDatePickerDialog2;

    private TimePickerDialog timePickerDialog1;
    private TimePickerDialog timePickerDialog2;


    private SimpleDateFormat dateFormatter;

    private int hour;
    private int minute;

    String[] sportStringArray = {"","Badminton", "Baseball", "Basketball", "Football",
            "Handball", "Ice Hockey", "Racquetball", "Roller Hockey",
            "Softball", "Tennis", "Volleyball"};

    String[] genderArray = {"", "Any", "Female", "Male"};
    User thisUser;

    View rootView;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle data = getActivity().getIntent().getExtras();
        thisUser = (User) data.getParcelable("USER");
        //thisUser = new User("Sarah", "Shibley", "sarahshib@hotmail.com","abcd","1994-10-12","female", "");

        super.onCreate(savedInstanceState);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        rootView = inflater.inflate(R.layout.filter_events, container, false);
        getActivity().setTitle("Filter Events");

        // setup spinners when page is created
        initSpinners();

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        findViewsById();
        setTimeFields();
        setDateFields();
        setUpCancelButton();
        setUpFilterButton();
        return rootView;
    }






    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onStop() {
        super.onStop();

    }



    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }



    private void initSpinners()
    {
        int idSportSpinner = R.id.sport_spinner;
        int idSportArray = R.array.sport_array;
        int idGenderSpinner = R.id.gender_spinner;
        int idGenderArray = R.array.gender_array2;
        int idAgeMinSpinner = R.id.age_min_spinner;
        int idAgeMaxSpinner = R.id.age_max_spinner;
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
        initNumSpinner(idMinUserRatingSpinner, -10, 20);
    }

    private void initSpinner(int spinnerId, int arrayId)
    {
        // load values from resources to populate Gender spinner
        Spinner spinner = (Spinner) rootView.findViewById(spinnerId);

        if(spinnerId == R.id.sport_spinner){
            spinner.setAdapter(new MyCAdapter(this.getActivity(), R.layout.row, sportStringArray));
        }
        else {
            // Create an ArrayAdapter with provided resources and layout
            ArrayAdapter<CharSequence> spinnnerAdapter = ArrayAdapter.createFromResource(this.getActivity(),
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
        list.add("");
        for(int i = begin; i < end; i++) {
            list.add(i + "");
        }
        final Spinner sp=(Spinner) rootView.findViewById(spinnerId);
        ArrayAdapter<String> adp = new ArrayAdapter<String>(this.getActivity(),
                android.R.layout.simple_list_item_1, list);
        adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(adp);
    }

    private void findViewsById() {
        cancelButton = (Button) rootView.findViewById(R.id.event_cancel_btn);
        cancelButton.requestFocus();

        filterButton = (Button) rootView.findViewById(R.id.filter_btn);
        filterButton.requestFocus();

        createdAtDateEditText1=(EditText) rootView.findViewById(R.id.event_date_created_1);
        createdAtDateEditText1.setInputType(InputType.TYPE_NULL);
        createdAtDateEditText1.requestFocus();

        createdAtDateEditText2=(EditText) rootView.findViewById(R.id.event_date_created_2);
        createdAtDateEditText2.setInputType(InputType.TYPE_NULL);
        createdAtDateEditText2.requestFocus();

        eventDateEditText1=(EditText) rootView.findViewById(R.id.event_date1);
        eventDateEditText1.setInputType(InputType.TYPE_NULL);
        eventDateEditText1.requestFocus();

        eventDateEditText2=(EditText) rootView.findViewById(R.id.event_date2);
        eventDateEditText2.setInputType(InputType.TYPE_NULL);
        eventDateEditText2.requestFocus();

        timeEditText1 = (EditText) rootView.findViewById(R.id.event_time1);
        timeEditText1.setInputType(InputType.TYPE_NULL);
        timeEditText1.requestFocus();

        timeEditText2 = (EditText) rootView.findViewById(R.id.event_time2);
        timeEditText2.setInputType(InputType.TYPE_NULL);
        timeEditText2.requestFocus();
    }


    private void setDateFields() {
        eventDateEditText1.setOnClickListener(this);

        Calendar newCalendar = Calendar.getInstance();
        eventDatePickerDialog1 = new DatePickerDialog(this.getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
                eventDateEditText1.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        eventDateEditText2.setOnClickListener(this);

        Calendar newCalendar2 = Calendar.getInstance();
        eventDatePickerDialog2 = new DatePickerDialog(this.getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
                eventDateEditText2.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar2.get(Calendar.YEAR), newCalendar2.get(Calendar.MONTH), newCalendar2.get(Calendar.DAY_OF_MONTH));

        createdAtDateEditText1.setOnClickListener(this);

        Calendar newCalendar3 = Calendar.getInstance();
        createDatePickerDialog1 = new DatePickerDialog(this.getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
                createdAtDateEditText1.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar3.get(Calendar.YEAR), newCalendar3.get(Calendar.MONTH), newCalendar3.get(Calendar.DAY_OF_MONTH));

        createdAtDateEditText2.setOnClickListener(this);

        Calendar newCalendar4 = Calendar.getInstance();
        createDatePickerDialog2 = new DatePickerDialog(this.getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
                createdAtDateEditText2.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar4.get(Calendar.YEAR), newCalendar4.get(Calendar.MONTH), newCalendar4.get(Calendar.DAY_OF_MONTH));

    }

    private void setTimeFields() {
        timeEditText1.setOnClickListener(this);

//        Calendar newCalendar = Calendar.getInstance();

        timePickerDialog1 =
                new TimePickerDialog(this.getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    public void onTimeSet(TimePicker view, int selectedHour,
                                          int selectedMinute) {
                        hour = selectedHour;
                        minute = selectedMinute;
                        newDate.set(Calendar.HOUR, hour);
                        newDate.set(Calendar.MINUTE, minute);
                        newDate.set(Calendar.SECOND, 0);
                        dateFormatter = new SimpleDateFormat("hh:mm a", Locale.US);
                        timeEditText1.setText(dateFormatter.format(newDate.getTime()));
                    }
                }, hour, minute, false);

        timeEditText2.setOnClickListener(this);

//        Calendar newCalendar = Calendar.getInstance();

        timePickerDialog2 =
                new TimePickerDialog(this.getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    public void onTimeSet(TimePicker view, int selectedHour,
                                          int selectedMinute) {
                        hour = selectedHour;
                        minute = selectedMinute;
                        newDate.set(Calendar.HOUR, hour);
                        newDate.set(Calendar.MINUTE, minute);
                        newDate.set(Calendar.SECOND, 0);
                        dateFormatter = new SimpleDateFormat("hh:mm a", Locale.US);
                        timeEditText2.setText(dateFormatter.format(newDate.getTime()));
                    }
                }, hour, minute, false);
    }



    private void setUpFilterButton() {
        filterButton.setOnClickListener(this);
    }

    private void setUpCancelButton() {
        cancelButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        if (view == createdAtDateEditText1) {
            createDatePickerDialog1.show();
        }
        if (view == createdAtDateEditText2) {
            createDatePickerDialog2.show();
        }
        if (view == eventDateEditText1) {
            eventDatePickerDialog1.show();
        }
        if (view == eventDateEditText2) {
            eventDatePickerDialog2.show();
        }
        else if(view == timeEditText1)
        {
            timePickerDialog1.show();
        }
        else if(view == timeEditText2)
        {
            timePickerDialog2.show();
        }
        else if (view == cancelButton) {
            Bundle args = new Bundle();

            Fragment fragment = new MapsFragment();
            args.putString(FragmentOne.ITEM_NAME, new DrawerItem("Map", R.drawable.map_icon)
                    .getItemName());

            fragment.setArguments(args);
            FragmentManager frgManager = getFragmentManager();
            frgManager.beginTransaction().replace(R.id.content_frame, fragment).addToBackStack("Filter Events" )
                    .commit();
        } else if (view == filterButton) {

            try {
                Log.d("SARAH", "calling formToMap");

                Map<String, String> formMap = formToMap();
                Log.d("SARAH", "called formToMap");

                System.out.println(formMap);
                String authorName = formMap.get("authorName");
                String eventName = formMap.get("eventName");
                String sport = formMap.get("sport");
                String location = formMap.get("location");
                String eventDate1 = convertDate(formMap.get("eventDate1"));
                String eventDate2 = convertDate(formMap.get("eventDate2"));
                String time1 = formMap.get("eventTime1");
                String time2 = formMap.get("eventTime2");
                System.out.println("time1:"+time1);
                System.out.println("time2:"+time2);

                String createdDate1 = convertDate(formMap.get("createdDate1"));
                String createdDate2 = convertDate(formMap.get("createdDate2"));
                String gender = formMap.get("gender");
                String ageMin = formMap.get("ageMin");
                String ageMax = formMap.get("ageMax");
                String minUserRating = formMap.get("minRating");
                String openEvents = formMap.get("openEvents");

                Log.d("SARAH", "made it past map stuff");


                Geocoder geocoder = new Geocoder(this.getActivity(), Locale.getDefault());
                List<Address> addresses;
                addresses = geocoder.getFromLocationName(location, 1);
                double latitude = 0;
                double longitude = 0;
                if (addresses.size() > 0) {
                    latitude = addresses.get(0).getLatitude();
                    longitude = addresses.get(0).getLongitude();
                }
                String stringLat = ""+latitude;
                String stringLong = ""+longitude;
                //open connection
                URLConnection http = new URLConnection();
                Log.d("SARAH", "created URLConnection");


                ArrayList<Event> eventList = http.sendFilterEvents(authorName, eventName, sport,
                        location, stringLat, stringLong,/*dateCreatedStart*/ createdDate1,/*dateCreatedEnd*/ createdDate2,
                                 /*eventTimeStart*/time1,/*eventTimeEnd*/ time2,/*eventDateStart*/ eventDate1,/*eventDateEnd*/ eventDate2,
                        ageMax, ageMin, minUserRating, /*'OnlyNotFull'*/openEvents, "", gender);
                Bundle args = new Bundle();
                if(latitude !=0 && longitude !=0) {
                    args.putDouble("filterLat", latitude);
                    args.putDouble("filterLong", longitude);
                }
                args.putParcelableArrayList("EVENTS", eventList);


                args.putString(FragmentOne.ITEM_NAME, new DrawerItem("Map", R.drawable.map_icon)
                        .getItemName());
                Fragment fragment = new MapsFragment();

                fragment.setArguments(args);
                FragmentManager frgManager = getFragmentManager();
                frgManager.beginTransaction().replace(R.id.content_frame, fragment).addToBackStack( "Filter Events" )
                        .commit();

                //Return to the MainActivity
/*
                b.putParcelable("USER", thisUser);

                User newUser = newIntent.getExtras().getParcelable("USER");
                Log.d("SARAH", "preuSER"+newUser.getEmail());
                Log.d("SARAH", ""+newIntent.getExtras().getParcelableArrayList("EVENTS").size());
                ArrayList<Event> preEventList = newIntent.getExtras().getParcelableArrayList("EVENTS");
                int listSize = preEventList.size();

                startActivityForResult(newIntent, 0);
                getActivity().finish();
*/
               /* b.putParcelable("EVENT", eventList.get(0));
                Log.d("SARAH", "Event1" + eventList.get(0).getName());
                b.putParcelableArrayList("EVENTS", eventList);
                    b.putParcelable("USER", thisUser);
                    Intent returnIntent = new Intent();
                    returnIntent.putExtras(b);
                    setResult(MapsActivity.RESULT_OK, returnIntent);

               /* }
               else {
                    Bundle b = new Bundle();
                    Log.d("SARAH", "maps activity is not starting");
                    b.putParcelable("USER", thisUser);
                    Intent returnIntent = new Intent();
                    returnIntent.putExtras(b);
                    setResult(MapsActivity.RESULT_CANCELED, returnIntent);
                    finish();
                }*/
            }
            catch (IOException e) {
                Toast.makeText(getActivity().getBaseContext(), "Unable to connect to the location service. Please try again later" ,
                        Toast.LENGTH_LONG).show();
                Log.e(TAG, "Unable connect to Geocoder", e);
            }
            catch(Exception e)
            {
                e.printStackTrace();
                Log.i("SARAH", "error");
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

        EditText editTextBox1 = (EditText)rootView.findViewById(R.id.event_location);
        String eventLocationStr = editTextBox1.getText().toString();
        formMap.put("location", eventLocationStr);

        // get text from the edit text box
        EditText editTextBox2 = (EditText)rootView.findViewById(R.id.event_name);
        String eventNameStr = editTextBox2.getText().toString();
        formMap.put("eventName", eventNameStr);

        // get text from the edit text box
        EditText editTextBox3 = (EditText)rootView.findViewById(R.id.author_name);
        String authorNameStr = editTextBox3.getText().toString();
        formMap.put("authorName", authorNameStr);

        Spinner sportSpinner = (Spinner)rootView.findViewById(R.id.sport_spinner);
        String eventSportStr = sportSpinner.getSelectedItem().toString();
        formMap.put("sport", eventSportStr);


        EditText editTextBox4 = (EditText) rootView.findViewById(R.id.event_date1);
        String eventDateStr1 = editTextBox4.getText().toString();
        formMap.put("eventDate1", eventDateStr1);

        EditText editTextBox5 = (EditText) rootView.findViewById(R.id.event_date2);
        String eventDateStr2 = editTextBox5.getText().toString();
        formMap.put("eventDate2", eventDateStr2);

        EditText editTextBox6 = (EditText) rootView.findViewById(R.id.event_time1);
        String eventTimeStr1 = editTextBox6.getText().toString();
        formMap.put("evenTime1", eventTimeStr1);

        EditText editTextBox7 = (EditText) rootView.findViewById(R.id.event_time2);
        String eventTimeStr2 = editTextBox7.getText().toString();
        formMap.put("eventTime2", eventTimeStr2);

        EditText editTextBox8 = (EditText) rootView.findViewById(R.id.event_date_created_1);
        String eventDateCreated1 = editTextBox8.getText().toString();
        formMap.put("createdDate1", eventDateCreated1);

        EditText editTextBox9 = (EditText) rootView.findViewById(R.id.event_date_created_2);
        String eventDateCreated2 = editTextBox9.getText().toString();
        formMap.put("createdDate2", eventDateCreated2);

        Spinner genderSpinner = (Spinner)rootView.findViewById(R.id.gender_spinner);
        String eventGenderStr = genderSpinner.getSelectedItem().toString();
        formMap.put("gender", eventGenderStr);

        Spinner ageGroupMinSpinner = (Spinner)rootView.findViewById(R.id.age_min_spinner);
        String eventAgeGroupMinStr = ageGroupMinSpinner.getSelectedItem().toString();
        formMap.put("ageMin", eventAgeGroupMinStr);

        Spinner ageGroupMaxSpinner = (Spinner)rootView.findViewById(R.id.age_max_spinner);
        String eventAgeGroupMaxStr = ageGroupMaxSpinner.getSelectedItem().toString();
        formMap.put("ageMax", eventAgeGroupMaxStr);

        String eventAgeGroupStr = eventAgeGroupMinStr + " " + eventAgeGroupMaxStr;


        Spinner minUserRatingSpinner = (Spinner)rootView.findViewById(R.id.min_user_rating_spinner);
        String eventMinUserRatingStr = minUserRatingSpinner.getSelectedItem().toString();
        formMap.put("minRating", eventMinUserRatingStr);

        String openEventsStr;
        CheckBox openEventsCheckBox = (CheckBox)rootView.findViewById(R.id.open_events);
        if (openEventsCheckBox.isChecked()) {openEventsStr = "true"; }
        else{ openEventsStr = "false"; }
        formMap.put("openEvents", openEventsStr);

        Log.d("SARAH", "made it past spinners in formToMap");


        String text = String.format(
                "event name: %s \n" +
                        "author name: %s \n" +
                        "sport: %s \n" +
                        "location: %s \n" +
                        "event date1: %s \n" +
                        "event date2: %s \n" +
                        "event time1: %s \n" +
                        "event time2: %s \n" +
                        "date created 1: %s \n" +
                        "date created 2: %s \n" +
                        "gender: %s \n" +
                        "age min: %s \n" +
                        "age max: %s \n" +
                        "min use Rating: %s \n" +
                        "open events: %s" , eventNameStr, authorNameStr, eventSportStr,
                eventLocationStr, eventDateStr1,eventDateStr2, eventTimeStr1, eventTimeStr2, eventDateCreated1, eventDateCreated2
                , eventGenderStr, eventAgeGroupMinStr, eventAgeGroupMaxStr,
                eventMinUserRatingStr, openEventsStr);

        Log.d("SARAH", "made it past formatString");

        // Log to show that the vars are correct
        Log.i(TAG, text);

        return formMap;
    }


    //Convert date to yyyy-mm-dd format
    private String convertDate(String date) {
        String convertedStr = "";
        String y, m, d;
        if(date.length()>0) {
            d = date.substring(0, date.indexOf("-"));
            m = date.substring(3, 5);
            y = date.substring(date.length() - 4);
            convertedStr = y + "-" + m + "-" + d;
        }
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

        int arr_images[] = {0, R.drawable.badminton_icon,
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

            LayoutInflater inflater = getActivity().getLayoutInflater();
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