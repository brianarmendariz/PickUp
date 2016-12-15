package csulb.edu.pickup;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;


import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.os.StrictMode;
import android.widget.EditText;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

/**
 * Created by Sarah on 3/3/2016.
 */
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class LoginActivity extends Activity implements View.OnClickListener {

    private static final String TAG = "Messages";
    private Button loginBasicButton;
    private Button createAccountButton;
    private Button forgotPasswdButton;

    private TextView mTextDetails;
    private CallbackManager mCallbackManager;
    private AccessToken accessToken;
    private AccessTokenTracker mTokenTracker;
    private ProfileTracker mProfileTracker;

    public static final String PREFS_NAME = "MyPrefsFile";

    private FacebookCallback<LoginResult> mFacebookCallback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            Log.d("NESSA", "onSuccess");

            accessToken = loginResult.getAccessToken();

            GraphRequest request = GraphRequest.newMeRequest(
                    loginResult.getAccessToken(),
                    new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(JSONObject object, GraphResponse response) {
                            //thisUser = new User("Sarah", "Shibley", "sarahshib@hotmail.com","abcd","1994-10-12","female", "");
                            Log.v("LoginActivity", response.toString());
                            try {
                                // Application code
                                String name = object.getString("name");
                                String email = object.getString("email");
                                String birthday = object.getString("birthday"); // 01/31/1980 format
                                String gender = object.getString("gender");
                                //mTextDetails.setText("Name: " + name + " Email: " + email + " Birthday: " + birthday);
                                //thisUser = new User("Sarah", "Shibley", "sarahshib@hotmail.com","abcd","1994-10-12","female", "");
                                User thisUser;

                                thisUser = new User(name,  "", email, "", birthday, gender, "");
                                if (email.equalsIgnoreCase("")) {
                                    email = "";
                                }
                                Log.d("NESSA", name + " " + email + " " + birthday + " " + gender);


                                Bundle b = new Bundle();
                                b.putParcelable("USER", thisUser);
                                Intent mapIntent = new Intent(getBaseContext(), MainActivity.class);
                                mapIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                                mapIntent.putExtras(b);
                                mapIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(mapIntent);
                                finish();

                            } catch (JSONException jE) {
                                throw new RuntimeException(jE);
                            }
                        }
                    });


            Bundle parameters = new Bundle();

            //parameters.putString("fields", s);

            parameters.putString("fields", "id,name,email,birthday,gender");
            request.setParameters(parameters);
            request.executeAsync();



            finish();

        }

        @Override
        public void onCancel() {
            Log.d("NESSA", "onCancel");

        }


        @Override
        public void onError(FacebookException e) {
            Log.d("NESSA", "onError " + e);
        }


    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //Set up facebook callbackmanager
        mCallbackManager = CallbackManager.Factory.create();

        FacebookSdk.sdkInitialize(getApplicationContext());



        //setUpLoginButton();
        setupTokenTracker();
        setupProfileTracker();
        mTokenTracker.startTracking();
        mProfileTracker.startTracking();
        setContentView(R.layout.login);
        setupLoginFBButton();
        mTextDetails = (TextView) findViewById(R.id.text_details);

        //Setup trackers for user profile

        findViewsById();
        setUpLoginBasicButton();
        setUpCreateAccountButton();
        setUpForgotPasswdButton();


    }


    private String constructWelcomeMessage(Profile profile) {
        StringBuffer stringBuffer = new StringBuffer();
        if (profile != null) {
            stringBuffer.append("Welcome " + profile.getName());
        }
        return stringBuffer.toString();
    }



    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //AppEventsLogger.activateApp(this);
        accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken == null) {
            Log.d("LOG", "accessToken null");
        }
    /*    else if (accessToken != null) {
            Intent mapIntent = new Intent(getBaseContext(), MapsActivity.class);
            mapIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(mapIntent);
            finish();
        }*/

    }

    @Override
    protected void onPause() {
        super.onPause();
        //AppEventsLogger.deactivateApp(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mTokenTracker.stopTracking();
        mProfileTracker.stopTracking();
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


    private void setUpLoginBasicButton() {
        loginBasicButton.setOnClickListener(this);

    }

    //print keyhash
    private void setupLoginFBButton() {
        LoginButton mButtonLogin = (LoginButton) findViewById(R.id.facebook_login_button);
        mButtonLogin.setCompoundDrawables(null, null, null, null);
        mButtonLogin.setReadPermissions(Arrays.asList("public_profile", "email", "user_birthday", "user_friends"));
        mButtonLogin.registerCallback(mCallbackManager, mFacebookCallback);
    }


    private void setupTokenTracker() {
        mTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                Log.d("NESSA", "" + currentAccessToken);
            }
        };
    }

    private void setupProfileTracker() {
        mProfileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                mTextDetails.setText(constructWelcomeMessage(currentProfile));
            }
        };
    }


    private void setUpCreateAccountButton() {createAccountButton.setOnClickListener(this);}

    private void setUpForgotPasswdButton() {forgotPasswdButton.setOnClickListener(this);}

    @Override
    public void onClick(View view) {

        if(view == forgotPasswdButton){
            Log.d("SARAH", "forgot password");
            Intent myIntent = new Intent(view.getContext(), ForgotPasswdActivity.class);
            startActivityForResult(myIntent, 0);
        }

        if (view == loginBasicButton) {
            Log.d("SARAH","login Button Clicked");
            EditText usernameBox = (EditText) findViewById(R.id.username);
            String username = usernameBox.getText().toString();

            EditText passwordBox = (EditText) findViewById(R.id.password);
            String password = passwordBox.getText().toString();

            String EMAIL_PATTERN =
                    "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

            if(!username.matches(EMAIL_PATTERN))
            {
                createAlert("Email Required", "Please Enter a Valid Email Address");
            }
            else {

                URLConnection http = new URLConnection();

                try {
                    // Login
                    String loginResult = http.sendLogin(username, password);

                    // Profile Pic
                    String lastUpdate = http.sendGetLastestProfileUpdateDate(username);

                    SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

                    String settingsLastUpdate = settings.getString("lastUpdate", "DEFAULT");

                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date lastUpdateDate = new Date();
                    if(lastUpdate != null)
                    {
                        lastUpdateDate = dateFormat.parse(lastUpdate);
                    }
                    Date settingsLastUpdateDate = new Date();

                    String profilePic = "";

                    // if DEAFULT then settings are not set
                    if(settingsLastUpdate.equals("DEFAULT"))
                    {
                        // call the web service and get the pic
                        profilePic = http.sendGetProfilePicture(username);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString("PROFILEPICTURE", profilePic);
                        editor.commit();
                    }
                    else // use the profile pic from settings
                    {
                        settingsLastUpdateDate = dateFormat.parse(settingsLastUpdate);
                    }

                    // check to see if the picture in settings is the latest version
                    if(settingsLastUpdateDate.before(lastUpdateDate))
                    {
                        // call the web service and get the pic
                        profilePic = http.sendGetProfilePicture(username);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString("PROFILEPICTURE", profilePic);
                        editor.commit();
                    }

                    if (loginResult.equals("false")) {
                        Log.d("SARAH", "login failed");

                        createAlert("Invalid Login", "Login Failed, Please try again");

                    } else {
                        Log.d("SARAH", "login successful");

                        User thisUser = http.sendGetUser(username);

                        Bundle b = new Bundle();
                        b.putParcelable("USER", thisUser);
                        Intent myIntent = new Intent(view.getContext(), MainActivity.class);
                        myIntent.putExtras(b);
                        startActivityForResult(myIntent, 0);
                    }
                }
                catch (IOException | JSONException | ParseException ex) {

                }
            }
        }
        else if (view == createAccountButton)
        {
            Intent myIntent = new Intent(view.getContext(), CreateAccountActivity.class);
            startActivityForResult(myIntent, 0);
        }
    }

    private void findViewsById() {
        loginBasicButton = (Button) findViewById(R.id.login_btn);
        loginBasicButton.requestFocus();

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

    /**
     * Call this method inside onCreate once to get your hash key
     */
    public void printKeyHash() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "csulb.edu.loginactivity",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature :info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());

                //KeyHash: PKWdTyeA5KpYW4mZ5qTyZ4/dNzU=
                Log.e("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }

        }
        catch (PackageManager.NameNotFoundException e) {

        }
        catch (NoSuchAlgorithmException e) {

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }
}