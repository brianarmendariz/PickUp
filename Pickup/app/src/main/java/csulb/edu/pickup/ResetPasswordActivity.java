package csulb.edu.pickup;

/**
 * Created by Sarah on 3/19/2016.
 */

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class ResetPasswordActivity extends AppCompatActivity implements View.OnClickListener{

    private Button cancelButton;
    private Button saveButton;

    User thisUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle data = getIntent().getExtras();
        thisUser = (User) data.getParcelable("USER");
        Log.d("SARAH", "username" + thisUser.getEmail());

        //thisUser = new User("ln", "em", "pw", "bday", "gend", "useRate", "a");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reset_password);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.edit_settings);


        // setup spinners when page is created

        findViewsById();
        setUpCancelButton();
        setUpSaveButton();
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
        /*if (id == R.id.action_settings) {
            return true;
        }
*/
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





    private void findViewsById() {

        cancelButton = (Button) findViewById(R.id.cancel_btn);
        cancelButton.requestFocus();

        saveButton = (Button) findViewById(R.id.save_btn);
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
            Bundle b = new Bundle();
            b.putParcelable("USER", thisUser);
            Intent myIntent = new Intent(view.getContext(), EditSettingsActivity.class);
            myIntent.putExtras(b);
            startActivityForResult(myIntent, 0);
        } else if (view == saveButton) {
            EditText oldPasswdTextBox = (EditText) findViewById(R.id.old_passwd);
            String oldPasswd = oldPasswdTextBox.getText().toString();

            EditText newPasswdTextBox = (EditText) findViewById(R.id.new_passwd);
            String newPasswd = newPasswdTextBox.getText().toString();

            EditText retypePasswdTextBox = (EditText) findViewById(R.id.retype_password);
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
                    String resetResult = http.sendPasswordReset(thisUser.getEmail(), oldPasswd, newPasswd);
                    if (resetResult.equals("false")) {
                        createAlert("Invalid Current Password", "Current Password Entered Incorrectly. Please Try Again.");

                    } else {
                        thisUser.setPassword(newPasswd);
                        Bundle b = new Bundle();
                        b.putParcelable("USER", thisUser);
                        Intent myIntent = new Intent(view.getContext(), EditSettingsActivity.class);
                        myIntent.putExtras(b);
                        startActivityForResult(myIntent, 0);
                    }
                } catch (IOException e) {

                }
            } else {
                createAlert("Invalid New Password Entry", "Passwords do not match - please try again.");
            }
        }

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




