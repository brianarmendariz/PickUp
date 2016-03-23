package csulb.edu.pickup;

/**
 * Created by Sarah on 3/17/2016.
 */

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.os.Parcelable;
import android.os.StrictMode;
import android.provider.SyncStateContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EditSettings extends AppCompatActivity implements View.OnClickListener{

    private Button cancelButton;
    private Button createAccountButton;
    private Button uploadPhotoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle data = getIntent().getExtras();
        User thisUser = (User) data.getParcelable("USER");
        super.onCreate(savedInstanceState);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.edit_settings);


        // setup spinners when page is created
        initSpinners();

        findViewsById();
        setUpCancelButton();
        setUpUploadPhotoButton();
        setUpCreateAccountButton();
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
        int idBdayYearSpinner = R.id.bday_year_spinner;
        int idBdayDaySpinner = R.id.bday_day_spinner;
        int idBdayMonthSpinner = R.id.bday_month_spinner;
        int idBdayMonthArray = R.array.bday_month_array;


        // attach values to month spinner
        initSpinner(idBdayMonthSpinner, idBdayMonthArray);



        // init date spinners
        initReverseSpinner(idBdayYearSpinner, 2016, 1917);
        initNumSpinner(idBdayDaySpinner, 1, 31);
    }

    private void initSpinner(int spinnerId, int arrayId)
    {
        // load values from resources to populate spinner
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
    private void initReverseSpinner(int spinnerId, int begin, int end)
    {
        List<String> list=new ArrayList<String>();
        for(int i = begin; i > end; i--) {
            list.add(i + "");
        }
        final Spinner sp=(Spinner) findViewById(spinnerId);
        ArrayAdapter<String> adp = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, list);
        adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(adp);
    }

    private void findViewsById() {

        cancelButton = (Button) findViewById(R.id.cancel_btn);
        cancelButton.requestFocus();

        createAccountButton = (Button) findViewById(R.id.create_account_btn);
        createAccountButton.requestFocus();

        uploadPhotoButton = (Button) findViewById(R.id.upload_photo_btn);
        uploadPhotoButton.requestFocus();


    }





    private void setUpCreateAccountButton() {
        createAccountButton.setOnClickListener(this);
    }

    private void setUpCancelButton() {
        cancelButton.setOnClickListener(this);
    }
    private void setUpUploadPhotoButton() {
        uploadPhotoButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {


        if (view == cancelButton) {
            Intent myIntent = new Intent(view.getContext(), LoginActivity.class);
            startActivityForResult(myIntent, 0);
        } else if (view == createAccountButton) {

            Map<String, String> formMap = formToMap();

            System.out.println(formMap);
            String firstName = formMap.get("firstName");
            String lastName = formMap.get("lastName");
            String username = formMap.get("username");
            String password = formMap.get("password");
            String passwordRetype = formMap.get("passwordRetype");
            String gender = formMap.get("gender");
            String bdayDay = formMap.get("bdayDay");
            String bdayMonth = formMap.get("bdayMonth");
            String bdayYear = formMap.get("bdayYear");
            String bday = bdayYear+"-"+bdayMonth+"-"+bdayDay;
            if(firstName.equals("")){
                createAlert("First Name Required", "Please Enter a First Name");
            }
            else if(lastName.equals("")){
                createAlert("Last Name Required", "Please Enter a Last Name" );
            }
            else if(username.equals("")){
                createAlert("Username Required", "Please Enter a Username");
            }
            else if(password.equals("")){
                createAlert("Password Required", "Please Enter a Password" );
            }
            else if(passwordRetype.equals("")){
                createAlert("Password Validation Required", "Please Enter Password Twice");
            }
            else if(gender.equals("")){
                createAlert("Gender Required", "Please select a gender" );
            }
            else if(password.equals(passwordRetype)) {
                URLConnection http = new URLConnection();
                try {
                    String userResult = http.sendCreateUser(username, password, firstName, lastName, bday, gender, "", "");
                    if(userResult.equals("false")){
                        createAlert("Duplicate Username", "Username exists. Please try a different one.");

                    }
                    else{
                        User thisUser = new User(firstName, lastName, username,password, bday, gender, "0");
                        Bundle b = new Bundle();
                        b.putParcelable("USER", thisUser);
                        Intent myIntent = new Intent(view.getContext(), MapsActivity.class);
                        myIntent.putExtras(b);
                        startActivityForResult(myIntent, 0);
                    }
                } catch(IOException e)
                {

                }
            } else {
                createAlert("Invalid Password Entry","Passwords do not match - please try again." );
            }
        }
    }


    public Map<String, String> formToMap()
    {
        Map<String, String> formMap = new HashMap<>();

        // get text from the edit text box
        EditText editTextBox1 = (EditText)findViewById(R.id.first_name);
        String firstNameStr = editTextBox1.getText().toString();
        formMap.put("firstName", firstNameStr);

        EditText editTextBox2 = (EditText)findViewById(R.id.last_name);
        String lastNameStr = editTextBox2.getText().toString();
        formMap.put("lastName", lastNameStr);

        EditText editTextBox3 = (EditText)findViewById(R.id.user_name);
        String usernameStr = editTextBox3.getText().toString();
        formMap.put("username", usernameStr);

        EditText editTextBox4 = (EditText)findViewById(R.id.userPassword);
        String passwordStr = editTextBox4.getText().toString();
        formMap.put("password", passwordStr);

        EditText editTextBox5 = (EditText)findViewById(R.id.retype_password);
        String passwordRetypeStr = editTextBox5.getText().toString();
        formMap.put("passwordRetype", passwordRetypeStr);

        Spinner bdayDaySpinner = (Spinner)findViewById(R.id.bday_day_spinner);
        String bdayDayStr = bdayDaySpinner.getSelectedItem().toString();
        formMap.put("bdayDay", bdayDayStr);

        Spinner bdayMonthSpinner = (Spinner)findViewById(R.id.bday_month_spinner);
        String bdayMonthStr = bdayMonthSpinner.getSelectedItem().toString();
        bdayMonthStr = stringMonthToNum(bdayMonthStr);
        formMap.put("bdayMonth", bdayMonthStr);


        Spinner bdayYearSpinner = (Spinner)findViewById(R.id.bday_year_spinner);
        String bdayYearStr = bdayYearSpinner.getSelectedItem().toString();
        formMap.put("bdayYear", bdayYearStr);

        boolean maleChecked = ((RadioButton) findViewById(R.id.radio_male)).isChecked();
        boolean femaleChecked = ((RadioButton) findViewById(R.id.radio_female)).isChecked();
        String genderStr = "";
        if(maleChecked)
            genderStr = "male";
        else if(femaleChecked)
            genderStr = "female";

        formMap.put("gender", genderStr);


        String text = String.format("first: %s \nlast: %s \nuser: %s " +
                        "\npass: %s \npassRe: %s \ngend: %s \nday: %s \nmon: %s" +
                        "\nyea: %s", firstNameStr, lastNameStr, usernameStr,
                passwordStr, passwordRetypeStr, genderStr, bdayDayStr, bdayMonthStr,
                bdayYearStr);


        // Log to show that the vars are correct
        Log.i("SARAH", text);

        return formMap;
    }

    public String stringMonthToNum(String sMonth){
        String month = "00";
        switch(sMonth){
            case "Jan":
                month = "01";
                break;
            case "Feb":
                month = "02";
                break;
            case "Mar":
                month = "03";
                break;
            case "Apr":
                month = "04";
                break;
            case "May":
                month = "05";
                break;
            case "Jun":
                month = "06";
                break;
            case "Jul":
                month = "07";
                break;
            case "Aug":
                month = "08";
                break;
            case "Sept":
                month = "09";
                break;
            case "Oct":
                month = "10";
                break;
            case "Nov":
                month = "11";
                break;
            case "Dec":
                month = "12";
                break;
        }
        return month;
    }


    public void createAlert(String title, String message){
        new AlertDialog.Builder(this)
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
}

