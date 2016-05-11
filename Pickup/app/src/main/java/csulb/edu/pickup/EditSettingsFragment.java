package csulb.edu.pickup;

/**
 * Created by Sarah on 3/19/2016.
 */

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class EditSettingsFragment extends Fragment implements View.OnClickListener{

    private Button cancelButton;
    private Button saveButton;
    private Button resetPasswordButton;
    private Button uploadPhotoButton;
    User thisUser;
    View rootView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle data = getActivity().getIntent().getExtras();
        thisUser = (User) data.getParcelable("USER");
        // Log.d("SARAH","username"+thisUser.getEmail());
        // Log.d("SARAH","birthday"+thisUser.getBirthday());

        //thisUser = new User("ln", "em", "pw", "female", "2016-01-01", "female", "a");
        super.onCreate(savedInstanceState);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        rootView = inflater.inflate(R.layout.edit_settings, container, false);
        getActivity().setTitle("Edit Settings");

        // setup spinners when page is created
        initSpinners();

        findViewsById();
        setUpCancelButton();
        setUpUploadPhotoButton();
        setUpSaveButton();
        setUpResetPasswordButton();
        populateForm(thisUser);




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
        System.out.println("SpinnerID"+ spinnerId);
        Spinner spinner = (Spinner) rootView.findViewById(spinnerId);
        // Create an ArrayAdapter with provided resources and layout
        ArrayAdapter<CharSequence> spinnnerAdapter = ArrayAdapter.createFromResource(this.getActivity(),
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
        final Spinner sp=(Spinner) rootView.findViewById(spinnerId);
        ArrayAdapter<String> adp = new ArrayAdapter<String>(this.getActivity(),
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
        final Spinner sp=(Spinner) rootView.findViewById(spinnerId);
        ArrayAdapter<String> adp = new ArrayAdapter<String>(this.getActivity(),
                android.R.layout.simple_list_item_1, list);
        adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(adp);
    }

    private void findViewsById() {

        resetPasswordButton = (Button) rootView.findViewById(R.id.reset_passwd_btn);
        resetPasswordButton.requestFocus();

        cancelButton = (Button) rootView.findViewById(R.id.cancel_btn);
        cancelButton.requestFocus();

        saveButton = (Button) rootView.findViewById(R.id.save_btn);
        saveButton.requestFocus();

        uploadPhotoButton = (Button) rootView.findViewById(R.id.upload_photo_btn);
        uploadPhotoButton.requestFocus();


    }

    private void setUpResetPasswordButton(){
        resetPasswordButton.setOnClickListener(this);
    }

    private void setUpSaveButton() {
        saveButton.setOnClickListener(this);
    }

    private void setUpCancelButton() {
        cancelButton.setOnClickListener(this);
    }
    private void setUpUploadPhotoButton() {
        uploadPhotoButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {

        if (view == resetPasswordButton){
            Log.d("SARAH", "reset passwd button clicked");

            Bundle args = new Bundle();
            Fragment fragment = new ResetPasswordFragment();


            fragment.setArguments(args);
            FragmentManager frgManager = getFragmentManager();
            frgManager.beginTransaction().replace(R.id.content_frame, fragment).addToBackStack( "Edit Settings" )
                    .commit();
        }

        if (view == cancelButton) {
            Bundle args = new Bundle();

            Fragment fragment = new MapsFragment();

            fragment.setArguments(args);
            FragmentManager frgManager = getFragmentManager();
            frgManager.beginTransaction().replace(R.id.content_frame, fragment).addToBackStack( "Edit Settings" )
                    .commit();

        } else if (view == saveButton) {

            Map<String, String> formMap = formToMap();

            System.out.println(formMap);
            String firstName = formMap.get("firstName");
            String lastName = formMap.get("lastName");
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


            else if(gender.equals("")){
                createAlert("Gender Required", "Please select a gender" );
            }
            else{
                URLConnection http = new URLConnection();
                try {
                    String userResult = http.sendEditUser(thisUser.getEmail(), firstName, lastName, bday, gender, "", "");


                    thisUser.setBirthday(bday);
                    thisUser.setFirstName(firstName);
                    thisUser.setlastName(lastName);
                    thisUser.setGender(gender);


                    Bundle args = new Bundle();
                    args.putParcelable("USER", thisUser);
                    Intent myIntent = new Intent(view.getContext(), MainActivity.class);
                    myIntent.putExtras(args);

                    Fragment fragment = new MapsFragment();


                    fragment.setArguments(args);
                    FragmentManager frgManager = getFragmentManager();
                    frgManager.beginTransaction().replace(R.id.content_frame, fragment).addToBackStack( "Edit Settings" )
                            .commit();

                } catch(IOException e)
                {

                }

            }
        }

    }


    public Map<String, String> formToMap()
    {
        Map<String, String> formMap = new HashMap<>();

        // get text from the edit text box
        EditText editTextBox1 = (EditText)rootView.findViewById(R.id.first_name);
        String firstNameStr = editTextBox1.getText().toString();
        formMap.put("firstName", firstNameStr);

        EditText editTextBox2 = (EditText)rootView.findViewById(R.id.last_name);
        String lastNameStr = editTextBox2.getText().toString();
        formMap.put("lastName", lastNameStr);



        Spinner bdayDaySpinner = (Spinner)rootView.findViewById(R.id.bday_day_spinner);
        String bdayDayStr = bdayDaySpinner.getSelectedItem().toString();
        formMap.put("bdayDay", bdayDayStr);

        Spinner bdayMonthSpinner = (Spinner)rootView.findViewById(R.id.bday_month_spinner);
        String bdayMonthStr = bdayMonthSpinner.getSelectedItem().toString();
        bdayMonthStr = stringMonthToNum(bdayMonthStr);
        formMap.put("bdayMonth", bdayMonthStr);


        Spinner bdayYearSpinner = (Spinner)rootView.findViewById(R.id.bday_year_spinner);
        String bdayYearStr = bdayYearSpinner.getSelectedItem().toString();
        formMap.put("bdayYear", bdayYearStr);

        boolean maleChecked = ((RadioButton) rootView.findViewById(R.id.radio_male)).isChecked();
        boolean femaleChecked = ((RadioButton) rootView.findViewById(R.id.radio_female)).isChecked();
        String genderStr = "";
        if(maleChecked)
            genderStr = "male";
        else if(femaleChecked)
            genderStr = "female";

        formMap.put("gender", genderStr);


        String text = String.format("first: %s \nlast: %s \ngend: %s \nday: %s \nmon: %s \nday: %s", firstNameStr, lastNameStr,
                genderStr, bdayDayStr, bdayMonthStr,
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
    public void populateForm(User user)
    {
        String firstName = user.getFirstName();
        String lastName = user.getlastName();
        String gender = user.getGender();
        if(gender.equals("female")){

        }
        else if(gender.equals("male")){

        }
        String birthday = user.getBirthday();
        String[] parts = birthday.split("-");
        String bdayDay = parts[2];
        Integer.parseInt(bdayDay);
        String bdayMonth = parts[1];
        String bdayYear = parts[0];
        Log.d("SARAH", "YEar" +bdayYear);
        // get text from the edit text box
        EditText editTextBox1 = (EditText)rootView.findViewById(R.id.first_name);
        editTextBox1.setText(firstName);

        EditText editTextBox2 = (EditText)rootView.findViewById(R.id.last_name);
        editTextBox2.setText(lastName);


        Spinner daySpinner = (Spinner)rootView.findViewById(R.id.bday_day_spinner);
        daySpinner.setSelection(Integer.parseInt(bdayDay)-1);

        Spinner monthSpinner = (Spinner)rootView.findViewById(R.id.bday_month_spinner);
        monthSpinner.setSelection(Integer.parseInt(bdayMonth)-1);

        Spinner yearSpinner = (Spinner)rootView.findViewById(R.id.bday_year_spinner);
        int bdayYearIndex = 2016-Integer.parseInt(bdayYear);
        yearSpinner.setSelection(bdayYearIndex);

        RadioButton femaleRadio = (RadioButton)rootView.findViewById(R.id.radio_female);
        RadioButton maleRadio = (RadioButton)rootView.findViewById(R.id.radio_male);
        if(gender.equals("female")){
            femaleRadio.setChecked(true);
            maleRadio.setChecked(false);
        }
        else if(gender.equals("male")){
            femaleRadio.setChecked(false);
            maleRadio.setChecked(true);
        }

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

                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

}



