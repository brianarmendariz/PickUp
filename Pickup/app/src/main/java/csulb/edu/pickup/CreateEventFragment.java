package csulb.edu.pickup;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
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


public class CreateEventFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "brainsMessages";

    private Button genderButton;
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
            "Handball", "Ice Hockey", "Racquetball", "Roller Hockey", "Soccer",
            "Softball", "Tennis", "Volleyball"};

    User thisUser;

    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        Bundle data = getActivity().getIntent().getExtras();
        thisUser = (User) data.getParcelable("USER");
        //thisUser = new User("ln", "em", "pw", "bday", "gend", "useRate", "a");

        super.onCreate(savedInstanceState);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        rootView = inflater.inflate(R.layout.create_event, container, false);
        getActivity().setTitle("Create Event");

        // setup spinners when page is created
        initSpinners();

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        findViewsById();
        setTimeField();
        setDateField();
        setUpCancelButton();
        setUpCreateEventButton();

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
        int idSportSpinner = R.id.create_event_sport_spinner;
        int idSportArray = R.array.sport_array;
        int idGenderSpinner = R.id.create_event_gender_spinner;
        int idGenderArray = R.array.gender_array;
        int idAgeMinSpinner = R.id.create_event_age_min_spinner;
        int idAgeMaxSpinner = R.id.create_event_age_max_spinner;
        int idMaxNumPplSpinner = R.id.create_event_max_num_ppl_spinner;
        int idMinUserRatingSpinner = R.id.create_event_min_user_rating_spinner;

        // attach values to sport spinner
        initSpinner(idSportSpinner, idSportArray);

        // attach values to gender spinner
        initSpinner(idGenderSpinner, idGenderArray);

        // init min & max age, max num ppl, & min user rating spinners
        initSpinner(idAgeMinSpinner, 0);
        initSpinner(idAgeMaxSpinner, 0);
        initSpinner(idMaxNumPplSpinner, 0);
        initSpinner(idMinUserRatingSpinner, 0);
    }

    private void initSpinner(int spinnerId, int arrayId)
    {
        // load values from resources to populate Gender spinner
        Spinner spinner = (Spinner) rootView.findViewById(spinnerId);

        if(spinnerId == R.id.create_event_sport_spinner){
            spinner.setAdapter(new MyCAdapter(this.getActivity(), R.layout.row, sportStringArray));
        }
        else if(spinnerId == R.id.create_event_gender_spinner)
        {
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.gender_array, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setPrompt("Gender...");
            spinner.setAdapter(adapter);
        }
        else if(spinnerId == R.id.create_event_age_min_spinner || spinnerId == R.id.create_event_age_max_spinner
                || spinnerId == R.id.create_event_max_num_ppl_spinner || spinnerId == R.id.create_event_min_user_rating_spinner)
        {
            int min = 0;
            int max = 0;

            switch(spinnerId)
            {
                case R.id.create_event_age_min_spinner:
                    min = 5;
                    max = 80;
                    spinner.setPrompt("Minimum Age...");
                    break;
                case R.id.create_event_age_max_spinner:
                    min = 5;
                    max = 80;
                    spinner.setPrompt("Maximum Age...");
                    break;
                case R.id.create_event_max_num_ppl_spinner:
                    min = 2;
                    max = 30;
                    spinner.setPrompt("Number of Attendees...");
                    break;
                case R.id.create_event_min_user_rating_spinner:
                    min = -10;
                    max = 20;
                    spinner.setPrompt("User Rating...");
                    break;
                default:
                    break;
            }

            List<String> list=new ArrayList<String>();
            for(int i = min; i < max; i++)
            {
                list.add(i + "");
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(),
                    android.R.layout.simple_spinner_item, list);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            spinner.setAdapter(adapter);
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
        for(int i = begin; i < end; i++)
        {
            list.add(i + "");
        }
        final Spinner sp=(Spinner) rootView.findViewById(spinnerId);
        ArrayAdapter<String> adp = new ArrayAdapter<String>(this.getActivity(),
                android.R.layout.simple_list_item_1, list);
        adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(adp);
    }

    private void findViewsById() {


        cancelEventButton = (Button) rootView.findViewById(R.id.create_event_cancel_btn);
        cancelEventButton.requestFocus();

        createEventButton = (Button) rootView.findViewById(R.id.create_event_create_btn);
        createEventButton.requestFocus();

        createDateEditText = (EditText) rootView.findViewById(R.id.create_event_date);
        createDateEditText.setInputType(InputType.TYPE_NULL);
        createDateEditText.requestFocus();

        createTimeEditText = (EditText) rootView.findViewById(R.id.create_event_time);
        createTimeEditText.setInputType(InputType.TYPE_NULL);
        createTimeEditText.requestFocus();
    }


    private void setDateField() {
        createDateEditText.setOnClickListener(this);

        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this.getActivity(), new DatePickerDialog.OnDateSetListener() {

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

        timePickerDialog =
                new TimePickerDialog(this.getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    public void onTimeSet(TimePicker view, int selectedHour,
                                          int selectedMinute) {
                        hour = selectedHour;
                        minute = selectedMinute;
                        newDate.set(Calendar.HOUR_OF_DAY, hour);
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
            Bundle args = new Bundle();

            Fragment fragment = new MapsFragment();

            fragment.setArguments(args);
            FragmentManager frgManager = getFragmentManager();
            frgManager.beginTransaction().replace(R.id.content_frame, fragment).addToBackStack("Create Event" )
                    .commit();
        }
        else if (view == createEventButton) {


            Map<String, String> formMap = formToMap();

            System.out.println(formMap);
            String authorName = thisUser.getFirstName()+" "+thisUser.getlastName();
            String authorEmail = thisUser.getEmail();
            String name = formMap.get("event name");
            String sport = formMap.get("sport");
            String location = formMap.get("location");
            String date = formMap.get("date");
            System.out.println("date = " + date);
            String time = formMap.get("time");

            //date yyyy-mm-dd  and the time hh:min:ss
            String dateTime = (convertDate(date) + " " + convertTime(time));

            Log.d("DATE/TIME", dateTime);
            String gender = formMap.get("gender");
            String ageMin = formMap.get("age min");
            String ageMax = formMap.get("age max");
            String playerAmount = formMap.get("max num ppl");
            String minUserRating = formMap.get("min rating");

            /* TODO: get data from user */
            String endDateTime = "";
            String skill = "";
            String sportSpecific = "";
            String playersPerTeam = "";
            String numberOfTeams = "";
            String terrain = "";
            String environment = "";

            boolean createEventFlag = checkForm(name, location, date, time,
                    gender, ageMin, ageMax);

            if (createEventFlag) {
                try {
                    Geocoder geocoder = new Geocoder(this.getActivity(), Locale.getDefault());
                    List<Address> addresses;
                    addresses = geocoder.getFromLocationName(location, 1);

                    if (addresses.size() > 0) {
                        double latitude = addresses.get(0).getLatitude();
                        double longitude = addresses.get(0).getLongitude();

                        //open connection
                        URLConnection http = new URLConnection();

                        http.sendCreateEvent(authorName, authorEmail, name, sport, location,
                                String.valueOf(latitude), String.valueOf(longitude), dateTime,
                                endDateTime, ageMax, ageMin, minUserRating,
                                playerAmount, "P/NP", gender,
                                skill, sportSpecific, playersPerTeam, numberOfTeams, terrain, environment
                        );


                        //Return to the MainActivity
                        Bundle args = new Bundle();
                        Fragment fragment = new MapsFragment();
                        Intent returnIntent = new Intent();
                        getActivity().setResult(MainActivity.RESULT_OK, returnIntent);

                        fragment.setArguments(args);
                        FragmentManager frgManager = getFragmentManager();
                        frgManager.beginTransaction().replace(R.id.content_frame, fragment).addToBackStack( "Create Event" )
                                .commit();



                    } else {
                       System.out.println("invalid address");
                    }
                } catch (IOException e) {
                    Log.e(TAG, "Unable connect to Geocoder", e);
                } catch (Exception e) {
                    Log.i(TAG, "ERROR");
                }
            }
        }
    }


    public Map<String, String> formToMap()
    {
        Log.d("SARAH", "inside formToMap");

        Map<String, String> formMap = new HashMap<>();

        // get text from the edit text box
        EditText editTextBox1 = (EditText)rootView.findViewById(R.id.create_event_name);
        String eventNameStr = editTextBox1.getText().toString();
        formMap.put("event name", eventNameStr);

        Spinner sportSpinner = (Spinner) rootView.findViewById(R.id.create_event_sport_spinner);
        String eventSportStr = sportSpinner.getSelectedItem().toString();
        formMap.put("sport", eventSportStr);

        EditText editTextBox4 = (EditText)rootView.findViewById(R.id.create_event_location);
        String eventLocationStr = editTextBox4.getText().toString();
        formMap.put("location", eventLocationStr);

        EditText editTextBox5 = (EditText) rootView.findViewById(R.id.create_event_date);
        String eventDateStr = editTextBox5.getText().toString();
        formMap.put("date", eventDateStr);

        EditText editTextBox6 = (EditText) rootView.findViewById(R.id.create_event_time);
        String eventTimeStr = editTextBox6.getText().toString();
        formMap.put("time", eventTimeStr);

        Spinner genderSpinner = (Spinner)rootView.findViewById(R.id.create_event_gender_spinner);
        String eventGenderStr = genderSpinner.getSelectedItem().toString();
        formMap.put("gender", eventGenderStr);

        Spinner ageGroupMinSpinner = (Spinner)rootView.findViewById(R.id.create_event_age_min_spinner);
        String eventAgeGroupMinStr = ageGroupMinSpinner.getSelectedItem().toString();
        formMap.put("age min", eventAgeGroupMinStr);

        Spinner ageGroupMaxSpinner = (Spinner)rootView.findViewById(R.id.create_event_age_max_spinner);
        String eventAgeGroupMaxStr = ageGroupMaxSpinner.getSelectedItem().toString();
        formMap.put("age max", eventAgeGroupMaxStr);

        String eventAgeGroupStr = eventAgeGroupMinStr + " " + eventAgeGroupMaxStr;

        Spinner maxNumPplSpinner = (Spinner)rootView.findViewById(R.id.create_event_max_num_ppl_spinner);
        String eventMaxNumPplStr = maxNumPplSpinner.getSelectedItem().toString();
        formMap.put("max num ppl", eventMaxNumPplStr);

        Spinner minUserRatingSpinner = (Spinner)rootView.findViewById(R.id.create_event_min_user_rating_spinner);
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


    public boolean checkForm(String name, String location,
                             String date, String time, String gender, String ageMin,
                             String ageMax)
    {
        if(name.equals("")){
            createAlert("Name Required", "Please Enter a Name");
            return false;
        }
        else if(location.equals("")){
            createAlert("Location Required", "Please Enter a Location");
            return false;
        }
        else if(date.equals("")){
            createAlert("Date Required", "Please Select a Date");
            return false;
        }
        else if(time.equals("")){
            createAlert("Time Required", "Please Select a Time" );
            return false;
        }
        else if(!ageMin.equals("Any") && !ageMax.equals("Any") &&
                Integer.parseInt(ageMin) > Integer.parseInt(ageMax)){
            createAlert("Age Violation", "Please Select a Minimum Age Less " +
                    "Than Maximum Age");
            return false;
        }
        return true;
    }

    public void createAlert(String title, String message){
        new AlertDialog.Builder(this.getActivity())
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }


    //Convert date to yyyy-mm-dd format
    private String convertDate(String date) {
        String convertedStr = "";
        String y, m, d;

        if(!date.equals("")) {
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

        if(!time.equals(""))
        {
            String h = time.substring(0, time.indexOf(":"));
            String m = time.substring(3, 5);
            int hour = 0;
            String timeAMPM = time.substring(time.length() - 2);
            if (timeAMPM.equals("PM")) {
                hour = Integer.parseInt(h);
                hour += 12;
                h = String.valueOf(hour);
            }
            convertedTime = h + ":" + m + ":00";
        }
        return convertedTime;
    }

    public class MyCAdapter extends ArrayAdapter<String> {

        int arr_images[] = {R.drawable.badminton_icon,
                R.drawable.baseball_icon, R.drawable.basketball_icon, R.drawable.football_icon,
                R.drawable.handball_icon, R.drawable.icehockey_icon, R.drawable.racquetball_icon,
                R.drawable.rollerhockey_icon, R.drawable.soccer, R.drawable.softball_icon, R.drawable.tennis_icon,
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
            ImageView icon = (ImageView) row.findViewById(R.id.image);
            icon.setImageResource(arr_images[position]);

            return row;
        }
    }
}