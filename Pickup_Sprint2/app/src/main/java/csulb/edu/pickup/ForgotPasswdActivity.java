package csulb.edu.pickup;

/**
 * Created by Sarah on 3/19/2016.
 */

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.os.StrictMode;
import android.widget.EditText;

/**
 * Created by Sarah on 3/3/2016.
 */
import android.widget.Button;

import java.io.IOException;

public class ForgotPasswdActivity extends AppCompatActivity implements View.OnClickListener {


    private Button cancelButton;
    private Button sendEmailButton;




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.forgot_passwd);



        findViewsById();
        setupSendEmailButton();
        setupCancelButton();

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


    private void setupCancelButton() {cancelButton.setOnClickListener(this);   }

    private void setupSendEmailButton() {sendEmailButton.setOnClickListener(this);}



    @Override
    public void onClick(View view) {

        if(view == cancelButton){
            Intent myIntent = new Intent(view.getContext(), LoginActivity.class);
            startActivityForResult(myIntent, 0);
        }

        if (view == sendEmailButton) {
            EditText emailTextBox = (EditText)findViewById(R.id.username);
            String emailStr = emailTextBox.getText().toString();
            Log.d("SARAH", "email button clicked");
            URLConnection http = new URLConnection();
            try{
                Log.d("SARAH", "sending email");

                String emailResult = http.sendResetEmail(emailStr);
                Log.d("SARAH", emailResult);
                if (emailResult.equals("false")) {
                    Log.d("SARAH", "result is false");

                    createAlert("Invalid Email", "User does not exist.");

                }

                else{
                    Log.d("SARAH", "result successful");

                    createAlert("Email Sent", "An Email has been sent to you containing a temporary" +
                            " password. Please use this password to log on to our app and reset your" +
                            " password in the 'Change Settings' page.");
                }

            } catch(IOException e)
            {

            }

        }

    }
    private void findViewsById() {
        cancelButton = (Button) findViewById(R.id.cancel_btn);
        cancelButton.requestFocus();

        sendEmailButton = (Button) findViewById(R.id.send_email_btn);
        sendEmailButton.requestFocus();

    }
    public boolean createAlert(String title, String message){
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent myIntent = new Intent(ForgotPasswdActivity.this, LoginActivity.class);
                        startActivityForResult(myIntent, 0);
                    }
                })

                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
        return true;
    }


}