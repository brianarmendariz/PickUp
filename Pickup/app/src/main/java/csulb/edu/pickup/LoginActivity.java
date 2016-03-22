package csulb.edu.pickup;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.os.StrictMode;
import android.widget.EditText;

/**
 * Created by Sarah on 3/3/2016.
 */
import android.widget.Button;

import java.io.IOException;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {


    private Button loginButton;
    private Button createAccountButton;
    private Button forgotPasswdButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.login);



        findViewsById();
        setUpLoginButton();
        setUpCreateAccountButton();
        setUpForgotPasswdButton();

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


    private void setUpLoginButton() {loginButton.setOnClickListener(this);   }

    private void setUpCreateAccountButton() {createAccountButton.setOnClickListener(this);}

    private void setUpForgotPasswdButton() {forgotPasswdButton.setOnClickListener(this);}


    @Override
    public void onClick(View view) {

        if(view == forgotPasswdButton){
            Log.d("SARAH", "forgot password");
            Intent myIntent = new Intent(view.getContext(), ForgotPasswdActivity.class);
            startActivityForResult(myIntent, 0);
        }

        if (view == loginButton) {
            EditText usernameBox = (EditText) findViewById(R.id.username);
            String username = usernameBox.getText().toString();

            EditText passwordBox = (EditText) findViewById(R.id.password);
            String password = passwordBox.getText().toString();



            URLConnection http = new URLConnection();
            try {
                String loginResult = http.sendLogin(username, password);
                if (loginResult.equals("login failed")) {
                    createAlert("Invalid Login", "Login Failed, Please try again");

                }
                else{
                    User thisUser = http.sendGetUser(username);

                    Bundle b = new Bundle();
                    b.putParcelable("USER", thisUser);
                    Intent myIntent = new Intent(view.getContext(), MapsActivity.class);
                    myIntent.putExtras(b);
                    startActivityForResult(myIntent, 0);
                }
        } catch(IOException e)
        {

        }

        }
        else if (view == createAccountButton) {
            Intent myIntent = new Intent(view.getContext(), CreateAccountActivity.class);
            startActivityForResult(myIntent, 0);

        }


    }
    private void findViewsById() {
        loginButton = (Button) findViewById(R.id.login_btn);
        loginButton.requestFocus();

        createAccountButton = (Button) findViewById(R.id.create_account_btn);
        createAccountButton.requestFocus();


        forgotPasswdButton = (Button) findViewById(R.id.forgot_passwd_btn);
        forgotPasswdButton.requestFocus();

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