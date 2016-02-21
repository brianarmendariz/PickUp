package csulb.edu.pickup;

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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by Brain on 2/16/2016.
 */
public class CreateEventActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "brainsMessages";

    private Button cancelEventButton;
    private Button createEventButton;

    private EditText createDateEditText;

    private DatePickerDialog fromDatePickerDialog;

    private SimpleDateFormat dateFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_event);

        Log.i(TAG, "onCreate");

        // setup spinners when page is created
        initSpinners();

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        findViewsById();

        setDateField();
        setUpCancelButton();
        setUpCreateEventButton();
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
        Log.i(TAG, "onStart");
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume");
    }


    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause");
    }


    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop");
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "onSaveInstanceState");
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i(TAG, "onRestoreInstanceState");
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
        cancelEventButton = (Button) findViewById(R.id.event_cancel_btn);
        cancelEventButton.requestFocus();

        createEventButton = (Button) findViewById(R.id.event_create_btn);
        createEventButton.requestFocus();

        createDateEditText = (EditText) findViewById(R.id.event_date);
        createDateEditText.setInputType(InputType.TYPE_NULL);
        createDateEditText.requestFocus();
    }

    private void setDateField() {
        createDateEditText.setOnClickListener(this);

        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                createDateEditText.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    private void setTimeField() {
        createDateEditText.setOnClickListener(this);

        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                createDateEditText.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    private void setUpCreateEventButton()
    {
        createEventButton.setOnClickListener(this);
    }

    private void setUpCancelButton()
    {
        cancelEventButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        if(view == createDateEditText) {
            fromDatePickerDialog.show();
        }
        else if(view == cancelEventButton)
        {
            Intent myIntent = new Intent(view.getContext(), MapsActivity.class);
            startActivityForResult(myIntent, 0);
        }
        else if(view == createEventButton)
        {

            // get text from the edit text box
            EditText editTextBox1 = (EditText)findViewById(R.id.event_name);
            String eventNameStr = editTextBox1.getText().toString();

            EditText editTextBox2 = (EditText)findViewById(R.id.event_creator);
            String eventCreatorStr = editTextBox2.getText().toString();

            Spinner sportSpinner = (Spinner)findViewById(R.id.sport_spinner);
            String eventSportStr = sportSpinner.getSelectedItem().toString();

            EditText editTextBox4 = (EditText)findViewById(R.id.event_location);
            String eventLocationStr = editTextBox4.getText().toString();

            EditText editTextBox5 = (EditText) findViewById(R.id.event_date);
            String eventDateStr = editTextBox5.getText().toString();

            Spinner genderSpinner = (Spinner)findViewById(R.id.gender_spinner);
            String eventGenderStr = genderSpinner.getSelectedItem().toString();

            Spinner ageGroupMinSpinner = (Spinner)findViewById(R.id.age_min_spinner);
            String eventAgeGroupMinStr = ageGroupMinSpinner.getSelectedItem().toString();

            Spinner ageGroupMaxSpinner = (Spinner)findViewById(R.id.age_max_spinner);
            String eventAgeGroupMaxStr = ageGroupMaxSpinner.getSelectedItem().toString();

            String eventAgeGroupStr = eventAgeGroupMinStr + " " + eventAgeGroupMaxStr;

            Spinner maxNumPplSpinner = (Spinner)findViewById(R.id.max_num_ppl_spinner);
            String eventMaxNumPplStr = maxNumPplSpinner.getSelectedItem().toString();

            Spinner minUserRatingSpinner = (Spinner)findViewById(R.id.min_user_rating_spinner);
            String eventMinUserRatingStr = minUserRatingSpinner.getSelectedItem().toString();

            String text = String.format("1: %s \n2: %s \n3: %s " +
                            "\n4: %s \n5: %s \n6: %s \n7: %s \n8: %s" +
                            "\n9: %s", eventNameStr, eventCreatorStr,
                    eventSportStr, eventLocationStr, eventDateStr, eventGenderStr, eventAgeGroupStr,
                    eventMaxNumPplStr, eventMinUserRatingStr);

            Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
            // create an event when clicked
            // Event event = new Event();

            // POST it to the server

            // Let user know if successful or not


            //Intent myIntent = new Intent(view.getContext(), CreateEventActivity.class);
            //startActivityForResult(myIntent, 0);

        }
    }

}