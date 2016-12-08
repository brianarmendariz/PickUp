package csulb.edu.pickup;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.IOException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * created by Brain on 2/16/2016.
 */
public class EditEventFragment1 extends Fragment implements View.OnClickListener {

    private static final String TAG = "brainsMessages";

    User thisUser;

    int arr_images_default[] = {R.drawable.badminton_icon,
            R.drawable.baseball_icon, R.drawable.basketball_icon, R.drawable.football_icon,
            R.drawable.handball_icon, R.drawable.icehockey_icon, R.drawable.racquetball_icon,
            R.drawable.rollerhockey_icon, R.drawable.soccer_icon, R.drawable.softball_icon, R.drawable.tennis_icon,
            R.drawable.volleyball_icon, R.drawable.running_icon, R.drawable.weightlifting_icon, R.drawable.yoga_icon};
    int arr_images_exclusion[] ={R.drawable.badminton_icon,
            R.drawable.baseball_icon, R.drawable.basketball_icon, R.drawable.football_icon,
            R.drawable.handball_icon, R.drawable.icehockey_icon, R.drawable.racquetball_icon,
            R.drawable.rollerhockey_icon, R.drawable.soccer_icon, R.drawable.softball_icon, R.drawable.tennis_icon,
            R.drawable.volleyball_icon

    };

    private Button cancelEventButton;
    private Button saveChangesButton;

    Calendar newDate = Calendar.getInstance(); // local object to couple date and time

    private EditText editStartDateEditText;
    private EditText editStartTimeEditText;
    private EditText editEndDateEditText;
    private EditText editEndTimeEditText;

    private DatePickerDialog startDatePickerDialog;
    private TimePickerDialog startTimePickerDialog;
    private DatePickerDialog endDatePickerDialog;
    private TimePickerDialog endTimePickerDialog;

    private SimpleDateFormat dateFormatter;

    private int hour;
    private int minute;

    private int current_sport_position;
    private String current_sport_string;
    private String current_category_string = "Pick Up";
    private int current_category_position = 2;
    private int count;
    private int idSportspecific;
    private int current_terrain_position;
    private Event _event;

    View rootView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle data = getActivity().getIntent().getExtras();
        thisUser = (User) data.getParcelable("USER");
        super.onCreate(savedInstanceState);

//        Log.i(TAG, "onedit");

        rootView = inflater.inflate(R.layout.edit_event1, container, false);
        getActivity().setTitle("Edit Event");

        // setup spinners when page is editd
        initSpinners();

        findViewsById();

        setDateField();
        setTimeField();
        setUpCancelButton();
        setUpSaveChangesEventButton();
        /*uncomment lines below */
        //Intent intent = getIntent();
        String extra = getArguments().getString("EventID");
        //String extra = "5";
        Log.d("VIEW EVENT ID", extra);
        _event = getEventDetails(Integer.parseInt(extra));
        // little sloppy
        populateForm(_event);

        return rootView;

    }



    @Override
    public void onStart() {
        super.onStart();
//        Log.i(TAG, "onStart");
    }


    @Override
    public void onResume() {
        super.onResume();
//        Log.i(TAG, "onResume");
    }


    @Override
    public void onPause() {
        super.onPause();
//        Log.i(TAG, "onPause");
    }


    @Override
    public void onStop() {
        super.onStop();
//        Log.i(TAG, "onStop");
    }




    @Override
    public void onDestroy() {
        super.onDestroy();
//        Log.i(TAG, "onDestroy");
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        Log.i(TAG, "onSaveInstanceState");
    }


    private void initSpinners()
    {
        int idSportSpinner = R.id.edit_event_sport_spinner;
        int idSportArray = R.array.sport_array_default;

        int idGenderSpinner = R.id.edit_event_gender_spinner;
        int idGenderArray = R.array.gender_array;

        int idAgeMinSpinner = R.id.edit_event_age_min_spinner;
        int idAgeMaxSpinner = R.id.edit_event_age_max_spinner;

        int idMaxNumPplSpinner = R.id.edit_event_max_num_ppl_spinner;
        int idNumPplPerTeamSpinner = R.id.edit_event_number_of_people_per_team;
        int idNumofTeamsSpinner = R.id.edit_event_number_of_teams;
        int idMinUserRatingSpinner = R.id.edit_event_min_user_rating_spinner;
        //Sarah new edition

        int idCategorySpinner = R.id.edit_event_sport_category;
        int idCategoryArray = R.array.Create_Event_Categories;

        int idIndoorOutdoorSpinner = R.id.edit_event_indoor_outdoor;
        int idIndoorOutdoorArray = R.array.indoor_outdoor;

        int idSkillLevel = R.id.edit_event_skill_level;
        int idSkillArray = R.array.Event_Skill_Level;


        // attach values to sport spinner

        initSpinner(idSportSpinner,idSportArray);
        //attach value to Categories Spinner
        initSpinner(idCategorySpinner,idCategoryArray);
        //attach value to IndoorOutdoorSpinner

        initSpinner(idIndoorOutdoorSpinner,idIndoorOutdoorArray);

        // attach values to Skill Level spinner
        initSpinner(idSkillLevel,idSkillArray);

        // attach values to gender spinner
        initSpinner(idGenderSpinner, idGenderArray);

        // init min & max age, max num ppl, & min user rating spinners
        initSpinner(idAgeMinSpinner, 0);
        initSpinner(idAgeMaxSpinner, 0);
        initSpinner(idMaxNumPplSpinner, 0);
        initSpinner(idMinUserRatingSpinner, 0);
        initSpinner(idNumofTeamsSpinner, 0);
        initSpinner(idNumPplPerTeamSpinner, 0);
    }


    private void initSpinner(int spinnerId, int arrayId)
    {
        // load values from resources to populate Gender spinner
        Spinner spinner = (Spinner) rootView.findViewById(spinnerId);

        if(spinnerId == R.id.edit_event_sport_category)
        {

            ArrayAdapter<CharSequence> spinnnerAdapter = ArrayAdapter.createFromResource(this.getActivity(),
                arrayId, android.R.layout.simple_spinner_item);
            // Specify the layout
            spinnnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            // Apply the adapter to the spinner
            spinner.setAdapter(spinnnerAdapter);
            if(arrayId == R.array.Create_Event_Categories_Exclusion)
            {
                spinner.setSelection(0);
            }
            else
            {
                spinner.setSelection(current_category_position);
                spinner.setOnItemSelectedListener(CategoryListener);
            }


        }
        else if(spinnerId == R.id.edit_event_terrain)
        {

            ArrayAdapter<CharSequence> spinnnerAdapter = ArrayAdapter.createFromResource(this.getActivity(),
                    arrayId, android.R.layout.simple_spinner_item);
            // Specify the layout
            spinnnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            // Apply the adapter to the spinner
            spinner.setAdapter(spinnnerAdapter);
            spinner.setSelection(current_terrain_position);

        }
        else if(spinnerId == R.id.edit_event_sport_spinner)
        {
            if(arrayId == R.array.sport_array_default)
            {
                spinner.setAdapter(new MyEAdapter(this.getActivity(), R.layout.row, arrayId,arr_images_default));
                spinner.setOnItemSelectedListener(SportListener);
            }
            else
            {
                spinner.setAdapter(new MyEAdapter(this.getActivity(), R.layout.row, arrayId,arr_images_exclusion));
                spinner.setOnItemSelectedListener(SportListener);
                if(current_sport_string.equalsIgnoreCase("Running") ||
                        current_sport_string.equalsIgnoreCase("Yoga") ||
                        current_sport_string.equalsIgnoreCase("Weightlifting"))
                {
                    current_sport_position = 0;
                }
            }
            spinner.setSelection(current_sport_position);
        }
        else if(spinnerId == R.id.edit_event_gender_spinner)
        {
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.gender_array, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setPrompt("Gender...");
            spinner.setAdapter(adapter);
        }
        else if(spinnerId == R.id.edit_event_age_min_spinner || spinnerId == R.id.edit_event_age_max_spinner
                || spinnerId == R.id.edit_event_max_num_ppl_spinner || spinnerId == R.id.edit_event_min_user_rating_spinner
                || spinnerId == R.id.edit_event_number_of_people_per_team || spinnerId == R.id.edit_event_number_of_teams)
        {
            int min = 0;
            int max = 0;

            switch(spinnerId)
            {
                case R.id.edit_event_age_min_spinner:
                    min = 5;
                    max = 80;
                    spinner.setPrompt("Minimum Age...");
                    break;
                case R.id.edit_event_age_max_spinner:
                    min = 5;
                    max = 80;
                    spinner.setPrompt("Maximum Age...");
                    break;
                case R.id.edit_event_max_num_ppl_spinner:
                    min = 2;
                    max = 30;
                    spinner.setPrompt("Number of Attendees...");
                    break;
                case R.id.edit_event_min_user_rating_spinner:
                    min = -10;
                    max = 20;
                    spinner.setPrompt("User Rating...");
                    break;
                case R.id.edit_event_number_of_people_per_team:
                    min = 2;
                    max = 30;
                    spinner.setPrompt("Number of People per Team...");
                    break;
                case R.id.edit_event_number_of_teams:
                    min = 2;
                    max = 10;
                    spinner.setPrompt("Number of Teams");
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
        for(int i = begin; i < end; i++) {
            list.add(i + "");
        }
        final Spinner sp=(Spinner) rootView.findViewById(spinnerId);
        ArrayAdapter<String> adp = new ArrayAdapter<String>(this.getActivity(),
                android.R.layout.simple_list_item_1, list);
        adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(adp);
    }

    //Category Listener
    AdapterView.OnItemSelectedListener CategoryListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
        {
            Spinner s = (Spinner) rootView.findViewById(R.id.edit_event_sport_spinner);

            current_sport_position = s.getSelectedItemPosition();
            current_sport_string = (String) s.getSelectedItem();
            if (position == 2)
            {
                initSpinner(R.id.edit_event_sport_spinner, R.array.sport_array_default);
            }
            else if(position == 0 || position == 1)
            {
                initSpinner(R.id.edit_event_sport_spinner, R.array.sport_array_exclusion);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent)
        {

        }

    };
    //SportListener
    AdapterView.OnItemSelectedListener SportListener = new AdapterView.OnItemSelectedListener() {
        int idTerrainSpinner = R.id.edit_event_terrain;
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
        {
            int idSportSpecificSpinner = R.id.edit_event_sport_specific_spinner;
            int idSportSpecificArray;
            int idTerrainArray;

            Spinner spin = (Spinner)rootView.findViewById(idSportSpecificSpinner);
            spin.setVisibility(View.GONE);
            EditText running = (EditText)rootView.findViewById(R.id.edit_event_sport_specific_edittext);
            running.setVisibility(View.GONE);
            Spinner numofplayers = (Spinner)rootView.findViewById(R.id.edit_event_max_num_ppl_spinner);
            numofplayers.setVisibility(View.GONE);
            Spinner numofteams = (Spinner) rootView.findViewById(R.id.edit_event_number_of_teams);
            numofteams.setVisibility(View.VISIBLE);
            Spinner numofplayersperteam = (Spinner) rootView.findViewById(R.id.edit_event_number_of_people_per_team);
            numofplayersperteam.setVisibility(View.VISIBLE);
            switch(position)
            {
                //Badminton
                case 0:
                    idTerrainArray = R.array.Terrain_Default;
                    break;
                //Baseball
                case 1:
                    idTerrainArray = R.array.Terrain_Baseball_Softball;
                    break;
                //Basketball
                case 2:
                    idTerrainArray = R.array.Terrain_Basketball;
                    break;
                //Football
                case 3:
                    idTerrainArray = R.array.Terrain_Football;
                    idSportSpecificArray = R.array.Sport_Specific_Football;
                    initSpinner(idSportSpecificSpinner,idSportSpecificArray);
                    spin.setPrompt("Choose Football Game Type");
                    spin.setVisibility(View.VISIBLE);
                    break;
                //Handball
                case 4:
                    idTerrainArray = R.array.Terrain_Default;

                    break;
                //Ice Hockey
                case 5:
                    idTerrainArray = R.array.Terrain_Ice_Hockey;
                    idSportSpecificArray = R.array.Sport_Specific_Hockey;
                    initSpinner(idSportSpecificSpinner,idSportSpecificArray);
                    spin.setPrompt("Choose Hockey Type");
                    spin.setVisibility(View.VISIBLE);
                    break;
                //Racquetball
                case 6:
                    idTerrainArray = R.array.Terrain_Default;
                    break;
                //Roller Hockey
                case 7:
                    idTerrainArray = R.array.Terrain_Default;
                    idSportSpecificArray = R.array.Sport_Specific_Hockey;
                    initSpinner(idSportSpecificSpinner,idSportSpecificArray);
                    spin.setPrompt("Choose Hockey Type");
                    spin.setVisibility(View.VISIBLE);
                    break;
                //Soccer
                case 8:
                    idTerrainArray = R.array.Terrain_Soccer;
                    break;
                //Softball
                case 9:
                    idTerrainArray = R.array.Terrain_Baseball_Softball;
                    break;
                //Tennis
                case 10:
                    idTerrainArray = R.array.Terrain_Tennis;
                    break;
                //Volleyball
                case 11:
                    idTerrainArray = R.array.Terrain_Volleyball;
                    break;
                //Running
                case 12:
                    idTerrainArray = R.array.Terrain_Default;
                    running.setVisibility(View.VISIBLE);
                    running.setHint("Distance in miles");
                    numofplayers.setVisibility(View.VISIBLE);
                    numofplayersperteam.setVisibility(View.GONE);
                    numofteams.setVisibility(View.GONE);
                    break;
                //weightlifting
                case 13:
                    idTerrainArray = R.array.Terrain_Default;
                    idSportSpecificArray = R.array.Sport_Specific_Weightlifting;
                    initSpinner(idSportSpecificSpinner,idSportSpecificArray);
                    idSportspecific = idSportSpecificArray;
                    spin.setPrompt("Choose Workout");
                    spin.setVisibility(View.VISIBLE);
                    numofplayers.setVisibility(View.VISIBLE);
                    numofplayersperteam.setVisibility(View.GONE);
                    numofteams.setVisibility(View.GONE);
                    break;
                //yoga
                case 14:
                    idTerrainArray = R.array.Terrain_Default;
                    numofplayers.setVisibility(View.VISIBLE);
                    numofplayersperteam.setVisibility(View.GONE);
                    numofteams.setVisibility(View.GONE);
                    break;
                default:
                    idTerrainArray = R.array.Terrain_Default;
                    break;
            }


            Spinner s = (Spinner) rootView.findViewById(R.id.edit_event_sport_category);
            Spinner t = (Spinner) rootView.findViewById(R.id.edit_event_terrain);

            current_category_string = (String) s.getSelectedItem();
            current_category_position = s.getSelectedItemPosition();

            if(current_category_string == null)
            {
                current_category_string = "Pick Up";
            }
            if(current_category_position > 3 || current_category_position < 0)
            {
                current_category_position = 2;
            }
            //initSpinner(idCategorySpinner,idCategoryArray);
            //current_terrain_position = t.getSelectedItemPosition();
            initSpinner(idTerrainSpinner,idTerrainArray);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent)
        {
            initSpinner(idTerrainSpinner,R.array.Terrain_Default);
        }
    };


    private void findViewsById()
    {
        cancelEventButton = (Button) rootView.findViewById(R.id.edit_event_cancel_btn);
        cancelEventButton.requestFocus();

        saveChangesButton = (Button) rootView.findViewById(R.id.edit_event_save_changes_btn);
        saveChangesButton.requestFocus();

        editStartDateEditText = (EditText) rootView.findViewById(R.id.edit_event_start_date);
        editStartDateEditText.setInputType(InputType.TYPE_NULL);
        editStartDateEditText.requestFocus();

        editStartTimeEditText = (EditText) rootView.findViewById(R.id.edit_event_start_time);
        editStartTimeEditText.setInputType(InputType.TYPE_NULL);
        editStartTimeEditText.requestFocus();

        editEndDateEditText = (EditText) rootView.findViewById(R.id.edit_event_end_date);
        editEndDateEditText.setInputType(InputType.TYPE_NULL);
        editEndDateEditText.requestFocus();

        editEndTimeEditText = (EditText) rootView.findViewById(R.id.edit_event_end_time);
        editEndTimeEditText.setInputType(InputType.TYPE_NULL);
        editEndTimeEditText.requestFocus();
    }

    private void setDateField() {
        editEndDateEditText.setOnClickListener(this);
        editStartDateEditText.setOnClickListener(this);
        Calendar newCalendar = Calendar.getInstance();

        startDatePickerDialog = new DatePickerDialog(this.getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
                editStartDateEditText.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        endDatePickerDialog = new DatePickerDialog(this.getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
                editEndDateEditText.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));


    }

    private void setTimeField() {
        //     editStartTimeEditText.setOnClickListener(this);
        editEndTimeEditText.setOnClickListener(this);
        editStartTimeEditText.setOnClickListener(this);
        endTimePickerDialog =
                new TimePickerDialog(this.getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    public void onTimeSet(TimePicker view, int selectedHour,
                                          int selectedMinute) {
                        hour = selectedHour;
                        minute = selectedMinute;
                        newDate.set(Calendar.HOUR_OF_DAY, hour);
                        newDate.set(Calendar.MINUTE, minute);
                        newDate.set(Calendar.SECOND, 0);
                        dateFormatter = new SimpleDateFormat("h:mm a", Locale.US);
                        editEndTimeEditText.setText(dateFormatter.format(newDate.getTime()));
                    }
                }, hour, minute, false);
        startTimePickerDialog =
                new TimePickerDialog(this.getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    public void onTimeSet(TimePicker view, int selectedHour,
                                          int selectedMinute) {
                        hour = selectedHour;
                        minute = selectedMinute;
                        newDate.set(Calendar.HOUR_OF_DAY, hour);
                        newDate.set(Calendar.MINUTE, minute);
                        newDate.set(Calendar.SECOND, 0);
                        dateFormatter = new SimpleDateFormat("h:mm a", Locale.US);
                        editStartTimeEditText.setText(dateFormatter.format(newDate.getTime()));
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
        if(view == editEndDateEditText)
        {
            endDatePickerDialog.show();
        }
        else if(view == editStartDateEditText)
        {
            startDatePickerDialog.show();
        }
        else if(view == editStartTimeEditText)
        {
            startTimePickerDialog.show();
        }
        else if(view == editEndTimeEditText)
        {
            endTimePickerDialog.show();
        }
        else if(view == cancelEventButton)
        {
            Bundle args = new Bundle();

            Fragment fragment = new MapsFragment();

            fragment.setArguments(args);
            FragmentManager frgManager = getFragmentManager();
            frgManager.beginTransaction().replace(R.id.content_frame, fragment).addToBackStack( "Edit Event" )
                    .commit();
        }
        else if(view == saveChangesButton)
        {
            Map<String, String> formMap = formToMap();

            System.out.println(formMap);
            String creatorName = thisUser.getFirstName()+" "+thisUser.getlastName();
            String creatorEmail = thisUser.getEmail();
            String name = formMap.get("event name");
            String sport = formMap.get("sport");
            String address = formMap.get("location");
            String eventStartDate = formMap.get("start date");
            String eventStartTime = formMap.get("start time");

            //date yyyy-mm-dd  and the time hh:min:ss
            //String startDateTime = (convertDate(date) + " " + convertTime(time));

            String gender = formMap.get("gender");
            int ageMin = Integer.parseInt(formMap.get("age min"));
            int ageMax = Integer.parseInt(formMap.get("age max"));
            String playerAmount = formMap.get("max num ppl");
            int minUserRating = Integer.parseInt(formMap.get("min rating"));

            /* TODO: get data from user */
            String eventEndDate = formMap.get("end date");
            String eventEndTime = formMap.get("end time");
            //String endDateTime = (convertDate(enddate) + " " + convertTime(endtime));
            String skill = formMap.get("skill level");
            String sportSpecific = formMap.get("sport specific");
            int playersPerTeam = Integer.parseInt(formMap.get("players per team"));
            int numberOfTeams = Integer.parseInt(formMap.get("number of teams"));
            String terrain = formMap.get("terrain");
            String environment = formMap.get("environment");
            String category = formMap.get("category");


            boolean editEventFlag = checkForm(name, address, eventStartDate, eventStartTime,
                    gender, ageMin+"", ageMax+"");

            if (editEventFlag) {
                try {
                    Geocoder geocoder = new Geocoder(this.getActivity(), Locale.getDefault());
                    List<Address> addresses;
                    addresses = geocoder.getFromLocationName(address, 1);

                    if (addresses.size() > 0) {
                        double latitude = addresses.get(0).getLatitude();
                        double longitude = addresses.get(0).getLongitude();

                        //open connection
                        URLConnection http = new URLConnection();

                        /*int eventID, String eventName, String creatorName,
                                String creatorEmail, String sport, String address, double longitude,
                                double latitude, String gender, int ageMin, int ageMax,
                                int minUserRating, String eventStartDate, String eventStartTime,
                                String eventEndDate, String eventEndTime, String skill,
                                String sportSpecific, int playersPerTeam, int numberOfTeams,
                                String terrain, String environment, String category
                                */

                        String eventtruth = http.sendEditEvent(_event.getEventID(), name,creatorName, thisUser.getEmail(), sport, address,
                                latitude, longitude, gender, ageMin, ageMax,
                                minUserRating, eventStartDate,eventStartTime,eventStartDate,eventEndTime,
                             skill,sportSpecific,playersPerTeam,numberOfTeams,terrain,environment,
                             category
                        );

                        System.out.println(eventtruth);
                        //Retrieve data from server
                        //      http.sendGetEvents();

                        //Delete event from server
                        //http.sendDeleteEvent(1);

                        //Return to the MainActivity
                        Bundle args = new Bundle();

                        Fragment fragment = new MapsFragment();

                        //args.putString("result", latitude + " " + longitude);
                        //args.putString("EventID", list.get(i).getEventID());
                        Intent returnIntent = new Intent();
                        getActivity().setResult(MainActivity.RESULT_OK, returnIntent);

                        fragment.setArguments(args);
                        FragmentManager frgManager = getFragmentManager();
                        frgManager.beginTransaction().replace(R.id.content_frame, fragment).addToBackStack( "Edit Event" )
                                .commit();

                        //Return to the MainActivity
//                    Intent returnIntent = new Intent();
//                    returnIntent.putExtra("result", latitude + " " + longitude);
//                    setResult(MapsActivity.RESULT_OK, returnIntent);
                        //  Intent intent = new Intent(getActivity()..getBaseContext(), MapsActivity.class);
//                    intent.putExtra("EventID", list.get(i).getEventID());
                        // startActivity(intent);
                        //finish();

                    } else {
                        Intent returnIntent = new Intent();
                        getActivity().setResult(getActivity().RESULT_CANCELED, returnIntent);

                        Bundle args = new Bundle();
                        Intent myIntent = new Intent(view.getContext(), MainActivity.class);
                        myIntent.putExtras(args);

                        Fragment fragment = new MapsFragment();

                        fragment.setArguments(args);
                        FragmentManager frgManager = getFragmentManager();
                        frgManager.beginTransaction().replace(R.id.content_frame, fragment).addToBackStack( "Edit Event" )
                                .commit();

                    }
                } catch (IOException e) {
                    Log.e(TAG, "Unable connect to Geocoder", e);
                } catch (Exception e) {
                    Log.i(TAG, "ERROR");
                }
            }
        }
    }

    public void sendeditEvent(Map<String, String> formMap)
    {

        //   HttpClient client = new HttpClient();

    }

    public void populateForm(Event event)
    {
        String author = event.getCreatorName();
        String name = event.getName();
        String category = event.getCategory();
        String sport = event.getSport();
        String address = event.getAddress();
        String startDate = event.getEventStartDate();
        String startTime = event.getEventStartTime();
        String endDate = event.getEventEndDate();
        String endTime = event.getEventEndTime();
        String gender = event.getGender();
        String ageMin = event.getAgeMin() + "";
        String ageMax = event.getAgeMax() + "";
        int numPlayers = event.getPlayersPerTeam() * event.getNumberOfTeams() == 0 ? event.getPlayersPerTeam()
                : event.getPlayersPerTeam() * event.getNumberOfTeams();
        String maxNumPpl = numPlayers + "";
        String minUserRating = event.getMinUserRating() + "";

        String environment = event.getEnvironment();
        String terrain = event.getTerrain();
        String skillLevel  = event.getSkill();
        int numofTeams = event.getNumberOfTeams() ;
        int peoplePerTeam = event.getPlayersPerTeam();

        String sportSpecific = event.getSportSpecific();

        // get text from the edit text box
        EditText editEventName = (EditText)rootView.findViewById(R.id.edit_event_name);
        editEventName.setText(name);

        Spinner categorySpinner = (Spinner) rootView.findViewById(R.id.edit_event_sport_category);
        String[] categoryArray = getResources().getStringArray(R.array.Create_Event_Categories);

        for(int i = 0; i < categoryArray.length; i++)
        {
            if(category.equalsIgnoreCase(categoryArray[i]))
            {
                categorySpinner.setSelection(i);
            }
        }

        String[] sportArray = getResources().getStringArray(R.array.sport_array_default);
        if(category.equalsIgnoreCase("Pick Up"))
        {
            sportArray = getResources().getStringArray(R.array.sport_array_default);
        }
        else
        {
            sportArray = getResources().getStringArray(R.array.sport_array_exclusion);
        }
        Spinner sportSpinner = (Spinner)rootView.findViewById(R.id.edit_event_sport_spinner);

        for(int i = 0; i < sportArray.length; i++)
        {
            if(sport.equalsIgnoreCase(sportArray[i]))
            {
                sportSpinner.setSelection(i);
            }
        }

        Spinner EnvironmentSpinner = (Spinner) rootView.findViewById(R.id.edit_event_indoor_outdoor);
        String [] environmentArr = getResources().getStringArray(R.array.indoor_outdoor);
        for(int i = 0; i < environmentArr.length; i++ )
        {
            if(environment.equalsIgnoreCase(environmentArr[i]))
            {
                EnvironmentSpinner.setSelection(i);
            }
        }

        String[] terrainArr;
        if(sport.equalsIgnoreCase("Softball") || sport.equalsIgnoreCase("Baseball"))
        {
            terrainArr = getResources().getStringArray(R.array.Terrain_Baseball_Softball);
        }
        else if(sport.equalsIgnoreCase("Basketball"))
        {
            terrainArr = getResources().getStringArray(R.array.Terrain_Basketball);
        }
        else if(sport.equalsIgnoreCase("Football"))
        {
            terrainArr = getResources().getStringArray(R.array.Terrain_Football);
        }
        else if(sport.equalsIgnoreCase("Ice Hockey")) {
            terrainArr = getResources().getStringArray(R.array.Terrain_Ice_Hockey);
        }
        else if(sport.equalsIgnoreCase("Soccer")) {
            terrainArr = getResources().getStringArray(R.array.Terrain_Soccer);
        }
        else if(sport.equalsIgnoreCase("Tennis")) {
            terrainArr = getResources().getStringArray(R.array.Terrain_Tennis);
        }
        else if(sport.equalsIgnoreCase("Volleyball"))
        {
            terrainArr = getResources().getStringArray(R.array.Terrain_Volleyball);
        }
        else
        {
            terrainArr = getResources().getStringArray(R.array.Terrain_Default);
        }


        Spinner terrainSpinner = (Spinner) rootView.findViewById(R.id.edit_event_terrain);

        System.out.println(terrain);
        System.out.println(terrainArr.length);

        for(int i = 0; i < terrainArr.length; i++)
        {
            if (terrainArr[i].equalsIgnoreCase(terrain))
            {
                terrainSpinner.setSelection(i);
                current_terrain_position = i;
            }
        }

        EditText sportSpecifictext = (EditText)rootView.findViewById(R.id.edit_event_sport_specific_edittext);
        if(sportSpecifictext.getVisibility() == View.VISIBLE)
        {
            sportSpecifictext.setText(sportSpecific);
        }

        Spinner sportSpecificSpinner = (Spinner)rootView.findViewById(R.id.edit_event_sport_specific_spinner);

        if(sportSpecificSpinner.getVisibility() == View.VISIBLE)
        {
            String[] sportspecificArr = getResources().getStringArray(idSportspecific);
            for(int i = 0; i < sportspecificArr.length; i++)
            {
                if(sportSpecific.equalsIgnoreCase(sportspecificArr[i]))
                {
                    sportSpecificSpinner.setSelection(i);
                }
            }
        }


        Spinner skillSpinner = (Spinner) rootView.findViewById(R.id.edit_event_skill_level);
        String [] skillArr = getResources().getStringArray(R.array.Event_Skill_Level);
        for(int i = 0; i < skillArr.length; i++)
        {
            if(skillLevel.equalsIgnoreCase(skillArr[i]))
            {
                skillSpinner.setSelection(i);
            }
        }

        EditText editEventLocation = (EditText)rootView.findViewById(R.id.edit_event_location);
        editEventLocation.setText(address);

        EditText editEventStartDate = (EditText) rootView.findViewById(R.id.edit_event_start_date);
        editEventStartDate.setText(startDate);

        EditText editEventStartTime = (EditText) rootView.findViewById(R.id.edit_event_start_time);
        editEventStartTime.setText(startTime);

        EditText editEventEndDate = (EditText) rootView.findViewById(R.id.edit_event_end_date);
        editEventEndDate.setText(endDate);

        EditText editEventEndTime = (EditText) rootView.findViewById(R.id.edit_event_end_time);
        editEventEndTime.setText(endTime);


        Spinner genderSpinner = (Spinner)rootView.findViewById(R.id.edit_event_gender_spinner);
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
        Spinner ageGroupMinSpinner = (Spinner)rootView.findViewById(R.id.edit_event_age_min_spinner);
        for(int i = ageGroupMin; i < ageGroupMax; i++)
        {
            if(Integer.parseInt(ageMin) == i)
            {
                ageGroupMinSpinner.setSelection(i - ageGroupMin);
            }
        }

        Spinner ageGroupMaxSpinner = (Spinner)rootView.findViewById(R.id.edit_event_age_max_spinner);
        for(int i = ageGroupMin; i < ageGroupMax; i++)
        {
            if(Integer.parseInt(ageMax) == i)
            {
                ageGroupMaxSpinner.setSelection(i - ageGroupMin);
            }
        }

        int maxNumMin = 2;
        int maxNumMax = 30;
        Spinner maxNumPplSpinner = (Spinner)rootView.findViewById(R.id.edit_event_max_num_ppl_spinner);
        for(int i = maxNumMin; i < maxNumMax; i++)
        {
            if(Integer.parseInt(maxNumPpl) == i)
            {
                maxNumPplSpinner.setSelection(i - maxNumMin);
            }
        }

        int minUserRatingMin = -10;
        int minUserRatingMax = 20;
        Spinner minUserRatingSpinner = (Spinner)rootView.findViewById(R.id.edit_event_min_user_rating_spinner);
        for(int i = minUserRatingMin; i < minUserRatingMax; i++)
        {
            if(Integer.parseInt(minUserRating) == i)
            {
                minUserRatingSpinner.setSelection(i - minUserRatingMin);
            }
        }

        int minNumteams = 2;
        int maxNumteams = 10;

        Spinner numofteams = (Spinner) rootView.findViewById(R.id.edit_event_number_of_teams);
        for(int i = minNumteams; i < maxNumteams; i++)
        {
            if(numofTeams == i)
            {
                numofteams.setSelection(i - minNumteams);
            }
        }


        Spinner numofpeopleperteam = (Spinner) rootView.findViewById(R.id.edit_event_number_of_people_per_team);
        for(int i = maxNumMin; i < maxNumMax; i++)
        {
            if(peoplePerTeam == i)
            {
                numofpeopleperteam.setSelection(i - maxNumMin);
            }
        }
    }

    public void populateForm(Map<String, String> eventDetailsMap)
    {
        String author = eventDetailsMap.get("author");
        String name = eventDetailsMap.get("event name");
        String sport = eventDetailsMap.get("sport");
        String location = eventDetailsMap.get("location");
        String startDate = eventDetailsMap.get("start date");
        String startTime = eventDetailsMap.get("start time");
        String endDate = eventDetailsMap.get("end date");
        String endTime = eventDetailsMap.get("end time");
        String gender = eventDetailsMap.get("gender");
        String ageMin = eventDetailsMap.get("age min");
        String ageMax = eventDetailsMap.get("age max");
        String maxNumPpl = eventDetailsMap.get("max num ppl");
        String minUserRating = eventDetailsMap.get("min rating");

        // get text from the edit text box
        EditText editTextBox1 = (EditText)rootView.findViewById(R.id.edit_event_name);
        editTextBox1.setText(name);


        Spinner sportSpinner = (Spinner)rootView.findViewById(R.id.edit_event_sport_spinner);
        String[] sportArray = getResources().getStringArray(R.array.sport_array_default);
        for(int i = 0; i < sportArray.length; i++)
        {
            if(sport.equalsIgnoreCase(sportArray[i]))
            {
                sportSpinner.setSelection(i);
            }
        }

        EditText editTextBox4 = (EditText)rootView.findViewById(R.id.edit_event_location);
        editTextBox4.setText(location);

        EditText editTextBox5 = (EditText) rootView.findViewById(R.id.edit_event_start_date);
        editTextBox5.setText(startDate);

        EditText editTextBox6 = (EditText) rootView.findViewById(R.id.edit_event_start_time);
        editTextBox6.setText(startTime);

        Spinner genderSpinner = (Spinner)rootView.findViewById(R.id.edit_event_gender_spinner);
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
        Spinner ageGroupMinSpinner = (Spinner)rootView.findViewById(R.id.edit_event_age_min_spinner);
        for(int i = ageGroupMin; i < ageGroupMax; i++)
        {
            if(Integer.parseInt(ageMin) == i)
            {
                ageGroupMinSpinner.setSelection(i - ageGroupMin);
            }
        }

        Spinner ageGroupMaxSpinner = (Spinner)rootView.findViewById(R.id.edit_event_age_max_spinner);
        for(int i = ageGroupMin; i < ageGroupMax; i++)
        {
            if(Integer.parseInt(ageMax) == i)
            {
                ageGroupMaxSpinner.setSelection(i - ageGroupMin);
            }
        }

        int maxNumMin = 2;
        int maxNumMax = 30;
        Spinner maxNumPplSpinner = (Spinner)rootView.findViewById(R.id.edit_event_max_num_ppl_spinner);
        for(int i = maxNumMin; i < maxNumMax; i++)
        {
            if(Integer.parseInt(maxNumPpl) == i)
            {
                maxNumPplSpinner.setSelection(i - maxNumMin);
            }
        }

        int minUserRatingMin = -10;
        int minUserRatingMax = 20;
        Spinner minUserRatingSpinner = (Spinner)rootView.findViewById(R.id.edit_event_min_user_rating_spinner);
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
            Toast.makeText(getActivity().getApplicationContext(), "404: Event Not Found",
                    Toast.LENGTH_LONG).show();
        }

        return event;
    }

    public Map<String, String> formToMap()
    {
        Map<String, String> formMap = new HashMap<String, String>();


        // get text from the edit text box
        EditText editTextBox1 = (EditText)rootView.findViewById(R.id.edit_event_name);
        String eventNameStr = editTextBox1.getText().toString();
        formMap.put("event name", eventNameStr);

        Spinner sportSpinner = (Spinner) rootView.findViewById(R.id.edit_event_sport_spinner);
        String eventSportStr = sportSpinner.getSelectedItem().toString();
        formMap.put("sport", eventSportStr);

        EditText editTextBox4 = (EditText)rootView.findViewById(R.id.edit_event_location);
        String eventLocationStr = editTextBox4.getText().toString();
        formMap.put("location", eventLocationStr);

        EditText editTextBox5 = (EditText) rootView.findViewById(R.id.edit_event_start_date);
        String startEventDateStr = editTextBox5.getText().toString();
        formMap.put("start date", startEventDateStr);

        EditText editTextBox6 = (EditText) rootView.findViewById(R.id.edit_event_start_time);
        String startEventTimeStr = editTextBox6.getText().toString();
        formMap.put("start time", startEventTimeStr);

        EditText editTextBox7= (EditText) rootView.findViewById(R.id.edit_event_end_date);
        String endEventDateStr = editTextBox7.getText().toString();
        formMap.put("end date", endEventDateStr);

        EditText editTextBox8 = (EditText) rootView.findViewById(R.id.edit_event_end_time);
        String endEventTimeStr = editTextBox8.getText().toString();
        formMap.put("end time", endEventTimeStr);

        Spinner genderSpinner = (Spinner)rootView.findViewById(R.id.edit_event_gender_spinner);
        String eventGenderStr = genderSpinner.getSelectedItem().toString();
        formMap.put("gender", eventGenderStr);

        Spinner ageGroupMinSpinner = (Spinner)rootView.findViewById(R.id.edit_event_age_min_spinner);
        String eventAgeGroupMinStr = ageGroupMinSpinner.getSelectedItem().toString();
        formMap.put("age min", eventAgeGroupMinStr);

        Spinner ageGroupMaxSpinner = (Spinner)rootView.findViewById(R.id.edit_event_age_max_spinner);
        String eventAgeGroupMaxStr = ageGroupMaxSpinner.getSelectedItem().toString();
        formMap.put("age max", eventAgeGroupMaxStr);

        String eventAgeGroupStr = eventAgeGroupMinStr + " " + eventAgeGroupMaxStr;



        Spinner minUserRatingSpinner = (Spinner)rootView.findViewById(R.id.edit_event_min_user_rating_spinner);
        String eventMinUserRatingStr = minUserRatingSpinner.getSelectedItem().toString();
        formMap.put("min rating", eventMinUserRatingStr);

        if(eventSportStr.equalsIgnoreCase("Running") || eventSportStr.equalsIgnoreCase("Yoga") || eventSportStr.equalsIgnoreCase("Weightlifting"))
        {
            Spinner maxNumPplSpinner = (Spinner)rootView.findViewById(R.id.edit_event_max_num_ppl_spinner);
            String eventMaxNumPplStr = maxNumPplSpinner.getSelectedItem().toString();
            formMap.put("players per team", "0");
            formMap.put("number of teams", "0");
            formMap.put("max num ppl", eventMaxNumPplStr);
        }
        else
        {
            Spinner playersPerTeamSpinner = (Spinner) rootView.findViewById(R.id.edit_event_number_of_people_per_team);
            String eventPlayersPerTeamStr = playersPerTeamSpinner.getSelectedItem().toString();
            formMap.put("players per team", eventPlayersPerTeamStr);


            Spinner numberOfTeamsSpinner = (Spinner) rootView.findViewById(R.id.edit_event_number_of_teams);
            String eventNumOfTeams = numberOfTeamsSpinner.getSelectedItem().toString();
            formMap.put("number of teams", eventNumOfTeams);

            int maxnumppl = Integer.parseInt(eventPlayersPerTeamStr) *
                    Integer.parseInt(eventNumOfTeams);
            formMap.put("max num ppl", maxnumppl+"");

        }
        Spinner skillSpinner = (Spinner)rootView.findViewById(R.id.edit_event_skill_level);
        String eventSkillStr = skillSpinner.getSelectedItem().toString();
        formMap.put("skill level",eventSkillStr);

        if(eventSportStr.equalsIgnoreCase("Running"))
        {
            EditText SportSpecificEditText = (EditText)rootView.findViewById(R.id.edit_event_sport_specific_edittext);
            String SportSpecificStr = SportSpecificEditText.getText().toString();
            formMap.put("sport specific", SportSpecificStr);
        }
        else if (eventSportStr.equalsIgnoreCase("Hockey") ||eventSportStr.equalsIgnoreCase("Weightlifting")
                || eventSportStr.equalsIgnoreCase("Football") )
        {
            Spinner SportSpecificSpinner = (Spinner)rootView.findViewById(R.id.edit_event_sport_specific_spinner);
            String SportSpecificStr = SportSpecificSpinner.getSelectedItem().toString();
            formMap.put("sport specific",SportSpecificStr);
        }
        else
        {
            formMap.put("sport specific","");
        }

        Spinner terrainSpinner = (Spinner)rootView.findViewById(R.id.edit_event_terrain);
        String eventTerrainstr = terrainSpinner.getSelectedItem().toString();
        formMap.put("terrain",eventTerrainstr);

        Spinner environmentSpinner = (Spinner)rootView.findViewById(R.id.edit_event_indoor_outdoor);
        String eventEnviroStr = environmentSpinner.getSelectedItem().toString();
        formMap.put("environment", eventEnviroStr);

        Spinner categorySpinner = (Spinner)rootView.findViewById(R.id.edit_event_sport_category);
        String eventCatStr = categorySpinner.getSelectedItem().toString();
        formMap.put("category", eventCatStr);
        Log.d("SARAH", "made it past spinners in formToMap");

        return formMap;
    }

    public boolean checkForm(String name, String location,
                             String date, String time, String gender, String ageMin,
                             String ageMax)
    {
        if(name.equals("")){
            editAlert("Name Required", "Please Enter a Name" );
            return false;
        }
        else if(location.equals("")){
            editAlert("Location Required", "Please Enter a Location" );
            return false;
        }
        else if(date.equals("")){
            editAlert("Date Required", "Please Select a Date");
            return false;
        }
        else if(time.equals("")){
            editAlert("Time Required", "Please Select a Time" );
            return false;
        }
        else if(!ageMin.equals("Any") && !ageMax.equals("Any") &&
                Integer.parseInt(ageMin) > Integer.parseInt(ageMax)){
            editAlert("Age Violation", "Please Select a Minimum Age Less " +
                    "Than Maximum Age");
            return false;
        }
        return true;
    }

    public void editAlert(String title, String message){
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

        if(!date.equals(""))
        {
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

    public class MyEAdapter extends ArrayAdapter<String> {
        int[] arr_images;
        String[] sportStringArray;

        public MyEAdapter(Context context, int textViewResourceId,int sportsArrayloc,int[] images) {
            super(context, textViewResourceId, getResources().getStringArray(sportsArrayloc));
            sportStringArray = getResources().getStringArray(sportsArrayloc);
            arr_images = images;
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
            icon.setImageResource(arr_images_default[position]);

            return row;
        }
    }
}