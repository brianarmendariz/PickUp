package csulb.edu.pickup;

import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

public class FragmentOne extends Fragment implements View.OnClickListener{

    ImageView ivIcon;
    TextView tvItemName;

    private Button cancelButton;
    private Button saveButton;

    public static final String IMAGE_RESOURCE_ID = "iconResourceID";
    public static final String ITEM_NAME = "itemName";

    public FragmentOne() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.reset_password, container,false);

        //tvItemName = (TextView) view.findViewById(R.id.frag1_text);

        //tvItemName.setText("Something");


        return view;
    }



    User thisUser;



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Bundle data = getActivity().getIntent().getExtras();
       // thisUser = (User) data.getParcelable("USER");
        //Log.d("SARAH", "username" + thisUser.getEmail());

        thisUser = new User("ln", "em", "pw", "bday", "gend", "useRate", "a");
        super.onCreate(savedInstanceState);



        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        // setup spinners when page is created

        findViewsById();
        setUpCancelButton();
        setUpSaveButton();
    }




    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getActivity().getMenuInflater().inflate(R.menu.menu_main, menu);
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


    private void findViewsById() {

        cancelButton = (Button) getView().findViewById(R.id.cancel_btn);
        cancelButton.requestFocus();

        saveButton = (Button) getView().findViewById(R.id.save_btn);
        saveButton.requestFocus();



    }


    private void setUpSaveButton() {
        saveButton.setOnClickListener(this);
    }

    private void setUpCancelButton() {
        cancelButton.setOnClickListener(this);
    }



    public void onClick(View view) {

        if (view == cancelButton) {
            Bundle b = new Bundle();
            b.putParcelable("USER", thisUser);
            Intent myIntent = new Intent(view.getContext(), EditSettingsFragment.class);
            myIntent.putExtras(b);
            startActivityForResult(myIntent, 0);
        } else if (view == saveButton) {
            EditText oldPasswdTextBox = (EditText) getView().findViewById(R.id.old_passwd);
            String oldPasswd = oldPasswdTextBox.getText().toString();

            EditText newPasswdTextBox = (EditText) getView().findViewById(R.id.new_passwd);
            String newPasswd = newPasswdTextBox.getText().toString();

            EditText retypePasswdTextBox = (EditText) getView().findViewById(R.id.retype_password);
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
                        Intent myIntent = new Intent(view.getContext(), EditSettingsFragment.class);
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
        new AlertDialog.Builder(getActivity())
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