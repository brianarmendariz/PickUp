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

import android.os.StrictMode;
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

    private EditText createStartDateEditText;
    private EditText createEndDateEditText;
    private EditText createStartTimeEditText;
    private EditText createEndTimeEditText;

    private DatePickerDialog startDatePickerDialog;
    private DatePickerDialog endDatePickerDialog;
    private TimePickerDialog startTimePickerDialog;
    private TimePickerDialog endTimePickerDialog;

    private SimpleDateFormat dateFormatter;

    private int hour;
    private int minute;

//    String[] sportStringArray = {"Badminton", "Baseball", "Basketball", "Football",
//            "Handball", "Ice Hockey", "Racquetball", "Roller Hockey", "Running", "Soccer",
//            "Softball", "Tennis", "Volleyball", "Weightlifting", "Yoga"};
//

//    String[] sportStringArray;

    int arr_images_default[] = {R.drawable.badminton_icon,
            R.drawable.baseball_icon, R.drawable.basketball_icon, R.drawable.football_icon,
            R.drawable.handball_icon, R.drawable.icehockey_icon, R.drawable.racquetball_icon,
            R.drawable.rollerhockey_icon,  R.drawable.running_icon,R.drawable.soccer_icon,
            R.drawable.softball_icon, R.drawable.tennis_icon,R.drawable.volleyball_icon,
            R.drawable.weightlifting_icon, R.drawable.yoga_icon};
    int arr_images_exclusion[] =
            {R.drawable.badminton_icon,
            R.drawable.baseball_icon, R.drawable.basketball_icon, R.drawable.football_icon,
            R.drawable.handball_icon, R.drawable.icehockey_icon, R.drawable.racquetball_icon,
            R.drawable.rollerhockey_icon, R.drawable.soccer_icon, R.drawable.softball_icon, R.drawable.tennis_icon,
            R.drawable.volleyball_icon

    };

    User thisUser;

    View rootView;

    int current_sport;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Bundle from MainActivity
        Bundle mainArgs = getActivity().getIntent().getExtras();
        thisUser = (User) mainArgs.getParcelable("USER");

        // Bundle from SportFragment
        Bundle fragArgs = this.getArguments();
        current_sport = (int) fragArgs.getInt("SPORT"); // R.id.[sport_button_pressed]
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
        int idSportArray = R.array.sport_array_default;
        int idGenderSpinner = R.id.create_event_gender_spinner;
        int idGenderArray = R.array.gender_array;
        int idAgeMinSpinner = R.id.create_event_age_min_spinner;
        int idAgeMaxSpinner = R.id.create_event_age_max_spinner;
        int idMaxNumPplSpinner = R.id.create_event_max_num_ppl_spinner;
        int idNumPplPerTeamSpinner = R.id.create_event_number_of_people_per_team;
        int idNumofTeamsSpinner = R.id.create_event_number_of_teams;
        int idMinUserRatingSpinner = R.id.create_event_min_user_rating_spinner;
        //Sarah new edition

        int idCategorySpinner = R.id.create_event_sport_category;
        int idCategoryArray = R.array.Create_Event_Categories;

        int idIndoorOutdoorSpinner = R.id.create_event_indoor_outdoor;
        int idIndoorOutdoorArray = R.array.indoor_outdoor;

        int idSkillLevel = R.id.create_event_skill_level;
        int idSkillArray = R.array.Event_Skill_Level;

        //attach value to Categories Spinner
        initSpinner(idCategorySpinner,idCategoryArray);
        //attach value to IndoorOutdoorSpinner

        initSpinner(idIndoorOutdoorSpinner,idIndoorOutdoorArray);

        // attach values to Skill Level spinner
        initSpinner(idSkillLevel,idSkillArray);
        // attach values to sport spinner

        initSpinner(idSportSpinner,idSportArray);
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

        if(spinnerId == R.id.create_event_sport_category)
        {
            ArrayAdapter<CharSequence> spinnnerAdapter = ArrayAdapter.createFromResource(this.getActivity(),
                    arrayId, android.R.layout.simple_spinner_item);
            // Specify the layout
            spinnnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            // Apply the adapter to the spinner
            spinner.setAdapter(spinnnerAdapter);
            spinner.setSelection(2);
            spinner.setOnItemSelectedListener(CategoryListener);

        }
        else if(spinnerId == R.id.create_event_sport_spinner)
        {
            if(arrayId == R.array.sport_array_default)
            {
                spinner.setAdapter(new MyCAdapter(this.getActivity(), R.layout.row, arrayId,arr_images_default));
                spinner.setOnItemSelectedListener(SportListener);
            }
            else
            {
                spinner.setAdapter(new MyCAdapter(this.getActivity(), R.layout.row, arrayId,arr_images_exclusion));
                spinner.setOnItemSelectedListener(SportListener);
            }
            spinner.setSelection(current_sport);
        }
        else if(spinnerId == R.id.create_event_gender_spinner)
        {
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.gender_array, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setPrompt("Gender...");
            spinner.setAdapter(adapter);
        }
        else if(spinnerId == R.id.create_event_age_min_spinner || spinnerId == R.id.create_event_age_max_spinner
                || spinnerId == R.id.create_event_max_num_ppl_spinner || spinnerId == R.id.create_event_min_user_rating_spinner
                || spinnerId == R.id.create_event_number_of_people_per_team || spinnerId == R.id.create_event_number_of_teams)
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
                case R.id.create_event_number_of_people_per_team:
                    min = 2;
                    max = 30;
                    spinner.setPrompt("Number of People per Team...");
                    break;
                case R.id.create_event_number_of_teams:
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

    private void findViewsById()
    {
        cancelEventButton = (Button) rootView.findViewById(R.id.create_event_cancel_btn);
        cancelEventButton.requestFocus();

        createEventButton = (Button) rootView.findViewById(R.id.create_event_create_btn);
        createEventButton.requestFocus();

        createStartDateEditText = (EditText) rootView.findViewById(R.id.create_event_start_date);
        createStartDateEditText.setInputType(InputType.TYPE_NULL);
        createStartDateEditText.requestFocus();

        createEndDateEditText = (EditText) rootView.findViewById(R.id.create_event_end_date);
        createEndDateEditText.setInputType(InputType.TYPE_NULL);
        createEndDateEditText.requestFocus();

        createStartTimeEditText = (EditText) rootView.findViewById(R.id.create_event_start_time);
        createStartTimeEditText.setInputType(InputType.TYPE_NULL);
        createStartTimeEditText.requestFocus();

        createEndTimeEditText = (EditText) rootView.findViewById(R.id.create_event_end_time);
        createEndTimeEditText.setInputType(InputType.TYPE_NULL);
        createEndTimeEditText.requestFocus();
    }


    private void setDateField() {
        createStartDateEditText.setOnClickListener(this);
        createEndDateEditText.setOnClickListener(this);
        Calendar newCalendar = Calendar.getInstance();
        startDatePickerDialog = new DatePickerDialog(this.getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
                createStartDateEditText.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        endDatePickerDialog = new DatePickerDialog(this.getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
                createEndDateEditText.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

    }

    private void setTimeField() {
        createStartTimeEditText.setOnClickListener(this);
        createEndTimeEditText.setOnClickListener(this);
        startTimePickerDialog =
                new TimePickerDialog(this.getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    public void onTimeSet(TimePicker view, int selectedHour,
                                          int selectedMinute) {
                        hour = selectedHour;
                        minute = selectedMinute;
                        newDate.set(Calendar.HOUR_OF_DAY, hour);
                        newDate.set(Calendar.MINUTE, minute);
                        newDate.set(Calendar.SECOND, 0);
                        dateFormatter = new SimpleDateFormat("hh:mm a", Locale.US);
                        createStartTimeEditText.setText(dateFormatter.format(newDate.getTime()));

                    }
                }, hour, minute, false);
        endTimePickerDialog =  new TimePickerDialog(this.getActivity(), new TimePickerDialog.OnTimeSetListener() {
            public void onTimeSet(TimePicker view, int selectedHour,
                                  int selectedMinute) {
                hour = selectedHour;
                minute = selectedMinute;
                newDate.set(Calendar.HOUR_OF_DAY, hour);
                newDate.set(Calendar.MINUTE, minute);
                newDate.set(Calendar.SECOND, 0);
                dateFormatter = new SimpleDateFormat("hh:mm a", Locale.US);
                createEndTimeEditText.setText(dateFormatter.format(newDate.getTime()));

            }
        }, hour, minute, false);
    }

    private void setUpCreateEventButton() {
        createEventButton.setOnClickListener(this);
    }

    private void setUpCancelButton() {
        cancelEventButton.setOnClickListener(this);
    }


    //Category Listener
    AdapterView.OnItemSelectedListener CategoryListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
        {
            if(position == 2)
            {
                initSpinner(R.id.create_event_sport_spinner,R.array.sport_array_default);
            }
            else
            {
                initSpinner(R.id.create_event_sport_spinner,R.array.sport_array_exclusion);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent)
        {

        }

    };
    //SportListener
    AdapterView.OnItemSelectedListener SportListener = new AdapterView.OnItemSelectedListener() {
        int idTerrainSpinner = R.id.create_event_terrain;
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
        {
            int idSportSpecificSpinner = R.id.create_event_sport_specific_spinner;
            int idSportSpecificArray;
            int idTerrainArray;
            Spinner spin = (Spinner)rootView.findViewById(idSportSpecificSpinner);
            spin.setVisibility(View.GONE);
            EditText running = (EditText)rootView.findViewById(R.id.create_event_sport_specific_edittext);
            running.setVisibility(View.GONE);
            Spinner numofplayers = (Spinner)rootView.findViewById(R.id.create_event_max_num_ppl_spinner);
            numofplayers.setVisibility(View.GONE);
            Spinner numofteams = (Spinner) rootView.findViewById(R.id.create_event_number_of_teams);
            numofteams.setVisibility(View.VISIBLE);
            Spinner numofplayersperteam = (Spinner) rootView.findViewById(R.id.create_event_number_of_people_per_team);
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
                    //Running
                    case 8:
                        idTerrainArray = R.array.Terrain_Default;
                        running.setVisibility(View.VISIBLE);
                        running.setHint("Distance in miles");
                        numofplayers.setVisibility(View.VISIBLE);
                        numofplayersperteam.setVisibility(View.GONE);
                        numofteams.setVisibility(View.GONE);
                        break;
                    //Soccer
                    case 9:
                        idTerrainArray = R.array.Terrain_Soccer;
                        break;
                    //Softball
                    case 10:
                        idTerrainArray = R.array.Terrain_Baseball_Softball;
                        break;
                    //Tennis
                    case 11:
                        idTerrainArray = R.array.Terrain_Tennis;
                        break;
                    //Volleyball
                    case 12:
                        idTerrainArray = R.array.Terrain_Volleyball;
                        break;
                    //weightlifting
                    case 13:
                        idTerrainArray = R.array.Terrain_Default;
                        idSportSpecificArray = R.array.Sport_Specific_Weightlifting;
                        initSpinner(idSportSpecificSpinner,idSportSpecificArray);
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
            initSpinner(idTerrainSpinner,idTerrainArray);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent)
        {
            initSpinner(idTerrainSpinner,R.array.Terrain_Default);
        }
    };

    @Override
    public void onClick(View view) {

        if (view == createStartDateEditText) {
            startDatePickerDialog.show();
        }
        else if(view == createEndDateEditText)
        {
            endDatePickerDialog.show();
        }
        else if(view == createStartTimeEditText)
        {
            startTimePickerDialog.show();
        }
        else if(view == createEndTimeEditText)
        {
            endTimePickerDialog.show();
        }
        else if (view == cancelEventButton) {
            Bundle args = new Bundle();

            Fragment fragment = new MapsFragment();

            fragment.setArguments(args);
            FragmentManager frgManager = getFragmentManager();
            frgManager.beginTransaction().replace(R.id.content_frame, fragment).addToBackStack("Create Event" )
                    .commit();
        }
        else if (view == createEventButton)
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
            String ageMin = formMap.get("age min");
            String ageMax = formMap.get("age max");
            String playerAmount = formMap.get("max num ppl");
            String minUserRating = formMap.get("min rating");

            /* TODO: get data from user */
            String eventEndDate = formMap.get("end date");
            String eventEndTime = formMap.get("end time");
            //String endDateTime = (convertDate(enddate) + " " + convertTime(endtime));
            String skill = formMap.get("skill level");
            String sportSpecific = formMap.get("sport specific");
            String playersPerTeam = formMap.get("players per team");
            String numberOfTeams = formMap.get("number of teams");
            String terrain = formMap.get("terrain");
            String environment = formMap.get("environment");
            String category = formMap.get("category");

            boolean createEventFlag = checkForm(name, address, eventStartDate, eventStartTime,
                    gender, ageMin, ageMax);

            if (createEventFlag)
            {
                try {
                    Geocoder geocoder = new Geocoder(this.getActivity(), Locale.getDefault());
                    List<Address> addresses;
                    addresses = geocoder.getFromLocationName(address, 1);

                    if (addresses.size() > 0) {
                        double latitude = addresses.get(0).getLatitude();
                        double longitude = addresses.get(0).getLongitude();

                        //open connection
                        URLConnection http = new URLConnection();

                        http.sendCreateEvent(name, creatorName, creatorEmail, sport, address,
                                latitude + "", longitude + "", gender, ageMin, ageMax, minUserRating,
                                eventStartDate, eventStartTime, eventEndDate, eventEndTime,
                                skill, sportSpecific, playersPerTeam, numberOfTeams,
                                terrain, environment, category);


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
                    e.printStackTrace();
                    Log.e(TAG, "Unable connect to Geocoder", e);
                } catch (Exception e) {
                    e.printStackTrace();
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

        EditText editTextBox5 = (EditText) rootView.findViewById(R.id.create_event_start_date);
        String startEventDateStr = editTextBox5.getText().toString();
        formMap.put("start date", startEventDateStr);

        EditText editTextBox6 = (EditText) rootView.findViewById(R.id.create_event_start_time);
        String startEventTimeStr = editTextBox6.getText().toString();
        formMap.put("start time", startEventTimeStr);

        EditText editTextBox7= (EditText) rootView.findViewById(R.id.create_event_end_date);
        String endEventDateStr = editTextBox7.getText().toString();
        formMap.put("end date", endEventDateStr);

        EditText editTextBox8 = (EditText) rootView.findViewById(R.id.create_event_end_time);
        String endEventTimeStr = editTextBox8.getText().toString();
        formMap.put("end time", endEventTimeStr);

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



        Spinner minUserRatingSpinner = (Spinner)rootView.findViewById(R.id.create_event_min_user_rating_spinner);
        String eventMinUserRatingStr = minUserRatingSpinner.getSelectedItem().toString();
        formMap.put("min rating", eventMinUserRatingStr);

        if(eventSportStr.equalsIgnoreCase("Running") || eventSportStr.equalsIgnoreCase("Yoga") || eventSportStr.equalsIgnoreCase("Weightlifting"))
        {
            Spinner maxNumPplSpinner = (Spinner)rootView.findViewById(R.id.create_event_max_num_ppl_spinner);
            String eventMaxNumPplStr = maxNumPplSpinner.getSelectedItem().toString();
            formMap.put("max num ppl", eventMaxNumPplStr);
        }
        else
        {
            Spinner playersPerTeamSpinner = (Spinner) rootView.findViewById(R.id.create_event_number_of_people_per_team);
            String eventPlayersPerTeamStr = playersPerTeamSpinner.getSelectedItem().toString();
            formMap.put("players per team", eventPlayersPerTeamStr);

            Spinner numberOfTeamsSpinner = (Spinner) rootView.findViewById(R.id.create_event_number_of_teams);
            String eventNumOfTeams = numberOfTeamsSpinner.getSelectedItem().toString();
            formMap.put("number of teams", eventNumOfTeams);
        }
        Spinner skillSpinner = (Spinner)rootView.findViewById(R.id.create_event_skill_level);
        String eventSkillStr = skillSpinner.getSelectedItem().toString();
        formMap.put("skill level",eventSkillStr);

        if(eventSportStr.equalsIgnoreCase("Running"))
        {
            EditText SportSpecificEditText = (EditText)rootView.findViewById(R.id.create_event_sport_specific_edittext);
            String SportSpecificStr = SportSpecificEditText.getText().toString();
            formMap.put("sport specific", SportSpecificStr);
        }
        else if (eventSportStr.equalsIgnoreCase("Hockey") ||eventSportStr.equalsIgnoreCase("Weightlifting")
                || eventSportStr.equalsIgnoreCase("Football") )
        {
            Spinner SportSpecificSpinner = (Spinner)rootView.findViewById(R.id.create_event_sport_specific_spinner);
            String SportSpecificStr = SportSpecificSpinner.getSelectedItem().toString();
            formMap.put("sport specific",SportSpecificStr);
        }

        Spinner terrainSpinner = (Spinner)rootView.findViewById(R.id.create_event_terrain);
        String eventTerrainstr = terrainSpinner.getSelectedItem().toString();
        formMap.put("terrain",eventTerrainstr);

        Spinner environmentSpinner = (Spinner)rootView.findViewById(R.id.create_event_indoor_outdoor);
        String eventEnviroStr = environmentSpinner.getSelectedItem().toString();
        formMap.put("environment", eventEnviroStr);

        Spinner categorySpinner = (Spinner)rootView.findViewById(R.id.create_event_sport_category);
        String eventCatStr = categorySpinner.getSelectedItem().toString();
        formMap.put("category", eventCatStr);
        Log.d("SARAH", "made it past spinners in formToMap");

//String authorName,String authorEmail, String eventName, String sport,
  //      String location, String latitude, String longitude, String eventDateTime, String ageMax, String ageMin,
   //         String minUserRating, String playerAmount, String isPrivate, String gender, String skill, String sportSpecific,
    //        String eventEndDateTime, String playersPerTeam, String numberOfTeams, String terrain,
      //      String environment
//        String text = String.format("1: %s \n2: %s \n3: %s " +
//                        "\n4: %s \n5: %s \n6: %s \n7: %s \n8: %s" +
//                        "\n9: %s", eventNameStr, eventSportStr,
//                eventLocationStr, startEventDateStr, startEventTimeStr, eventGenderStr, eventAgeGroupStr,
//                 eventMinUserRatingStr);

        Log.d("SARAH", "made it past formatString");

        // Log to show that the vars are correct
//        Log.i(TAG, text);

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

        int[] arr_images;
        String[] sportsArray;
        public MyCAdapter(Context context, int textViewResourceId, int sportsArrayloc,int[] images) {
            super(context, textViewResourceId,getResources().getStringArray(sportsArrayloc) );
            sportsArray = getResources().getStringArray(sportsArrayloc);
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
            label.setText(sportsArray[position]);
            ImageView icon = (ImageView) row.findViewById(R.id.image);
            icon.setImageResource(arr_images[position]);

            return row;
        }
    }
}