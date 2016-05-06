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



public class ResetPasswordFragment extends Fragment implements View.OnClickListener{

    private Button cancelButton;
    private Button saveButton;

    View rootView;

    User thisUser;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle data = getActivity().getIntent().getExtras();
        thisUser = (User) data.getParcelable("USER");
       // Log.d("SARAH", "username" + thisUser.getEmail());

        //thisUser = new User("ln", "em", "pw", "bday", "gend", "useRate", "a");
        super.onCreate(savedInstanceState);
        rootView = inflater.inflate(R.layout.reset_password, container, false);
        getActivity().setTitle("Reset Password");


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        // setup spinners when page is created

        findViewsById();
        setUpCancelButton();
        setUpSaveButton();
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


        private void findViewsById() {

        cancelButton = (Button) rootView.findViewById(R.id.cancel_btn);
        cancelButton.requestFocus();

        saveButton = (Button) rootView.findViewById(R.id.save_btn);
        saveButton.requestFocus();



    }


    private void setUpSaveButton() {
        saveButton.setOnClickListener(this);
    }

    private void setUpCancelButton() {
        cancelButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {

        if (view == cancelButton) {
            Bundle args = new Bundle();
            Intent myIntent = new Intent(view.getContext(), MainActivity.class);
            myIntent.putExtras(args);

            Fragment fragment = new EditSettingsFragment();
            args.putString(FragmentOne.ITEM_NAME, new DrawerItem("Edit Settings", R.drawable.settings_icon)
                    .getItemName());

            fragment.setArguments(args);
            FragmentManager frgManager = getFragmentManager();
            frgManager.beginTransaction().replace(R.id.content_frame, fragment).addToBackStack( "Reset Password" )
                    .commit();




        } else if (view == saveButton) {
            Log.d("SARAH","save button clicked");
            EditText oldPasswdTextBox = (EditText) rootView.findViewById(R.id.old_passwd);
            String oldPasswd = oldPasswdTextBox.getText().toString();

            EditText newPasswdTextBox = (EditText) rootView.findViewById(R.id.new_passwd);
            String newPasswd = newPasswdTextBox.getText().toString();

            EditText retypePasswdTextBox = (EditText) rootView.findViewById(R.id.retype_password);
            String retypePasswd = retypePasswdTextBox.getText().toString();

            if (oldPasswd.equals("")) {
                createAlert("Old Password Required", "Please Enter Your Current Password");
            } else if (newPasswd.equals("")) {
                createAlert("New Password Required", "Please Enter a New Password");
            } else if (retypePasswd.equals("")) {
                createAlert("Retype Password Required", "Please Retype Your New Password");
            } else if (newPasswd.equals(retypePasswd)) {
                URLConnection http = new URLConnection();
                try {
                    Log.d("SARAH","sending http request");

                    String resetResult = http.sendPasswordReset(thisUser.getEmail(), oldPasswd, newPasswd);
                    Log.d("SARAH","reset Result: "+resetResult);

                    if (resetResult.equals("false")) {
                        createAlert("Invalid Current Password", "Current Password Entered Incorrectly. Please Try Again.");

                    }
                    else {
                        thisUser.setPassword(newPasswd);
                        Bundle args = new Bundle();
                        args.putParcelable("USER", thisUser);
                        Intent myIntent = new Intent(view.getContext(), MainActivity.class);
                        myIntent.putExtras(args);

                        Fragment fragment = new EditSettingsFragment();
                        args.putString(FragmentOne.ITEM_NAME, new DrawerItem("Edit Settings", R.drawable.settings_icon)
                                .getItemName());

                        fragment.setArguments(args);
                        FragmentManager frgManager = getFragmentManager();
                        frgManager.beginTransaction().replace(R.id.content_frame, fragment).addToBackStack( "Reset Password" )
                                .commit();
                    }
                } catch (IOException e) {

                }
            } else {
                createAlert("Invalid New Password Entry", "Passwords do not match - please try again.");
            }
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
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }



}




