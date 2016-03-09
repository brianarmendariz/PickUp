package csulb.edu.pickup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.os.StrictMode;
import android.widget.EditText;

/**
 * Created by Sarah on 3/3/2016.
 */
import android.widget.Button;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {


    private Button loginButton;
    private Button createAccountButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.login);


        findViewsById();
        setUpLoginButton();
        setUpCreateAccountButton();

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


    private void setUpLoginButton() {
        loginButton.setOnClickListener(this);
    }

    private void setUpCreateAccountButton() {createAccountButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {

        if (view == loginButton) {
            EditText username = (EditText) findViewById(R.id.username);
            EditText password = (EditText) findViewById(R.id.password);
            Intent myIntent = new Intent(view.getContext(), MapsActivity.class);
            startActivityForResult(myIntent, 0);
            /*
            if (username.getText().toString().equals("admin") && password.getText().toString().equals("admin")) {
            } else {
                //wrong password
            }
            */
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

    }

}