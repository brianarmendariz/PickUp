package csulb.edu.pickup;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;

import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;

import java.io.IOException;
import java.util.ArrayList;


/**
 * Created by Brain on 4/8/2016.
 */
public class CalendarActivity extends AppCompatActivity {

    User thisUser;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.user_profile) {
            Bundle b = new Bundle();
            b.putParcelable("USER", thisUser);
            Intent userProfileIntent = new Intent(getBaseContext(), UserProfileActivity.class);
            userProfileIntent.putExtras(b);
            startActivity(userProfileIntent);
        }

        if (id == R.id.calendar) {
            Bundle b = new Bundle();
            b.putParcelable("USER", thisUser);
            Intent calendarIntent = new Intent(getBaseContext(), CalendarActivity.class);
            calendarIntent.putExtras(b);
            startActivity(calendarIntent);
        }

        if(id==R.id.map){
            Bundle b = new Bundle();
            b.putParcelable("USER", thisUser);
            Intent mapIntent = new Intent(getBaseContext(), MapsActivity.class);
            mapIntent.putExtras(b);
            startActivity(mapIntent);
        }

        if(id==R.id.edit_settings){
            Bundle b = new Bundle();
            b.putParcelable("USER", thisUser);
            Intent mapIntent = new Intent(getBaseContext(), EditSettingsActivity.class);
            mapIntent.putExtras(b);
            startActivity(mapIntent);
        }

        if (id == R.id.user_logout) {
            LoginManager.getInstance().logOut();
            Intent loginActivityIntent = new Intent(getBaseContext(), LoginActivity.class);

            loginActivityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(loginActivityIntent);
            finish();
        }

        if (id == R.id.calendar) {
            Bundle b = new Bundle();
            b.putParcelable("USER", thisUser);
            Intent calendarIntent = new Intent(getBaseContext(), CalendarActivity.class);
            calendarIntent.putExtras(b);
            startActivity(calendarIntent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Bundle data = getIntent().getExtras();
        thisUser = (User) data.getParcelable("USER");

        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.calendar);

        ArrayList<Event> eventsForUser = null;

        URLConnection http = new URLConnection();
        try
        {
            eventsForUser = http.sendGetEventsForUser("sarahshib@hotmail.com");
        } catch(IOException e)
        {
            Toast.makeText(getApplicationContext(), "No Events for User",
                    Toast.LENGTH_LONG).show();
        }

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.CalendarLinearLayout);


        for(int i = 0; i < eventsForUser.size(); i++)
        {
            TextView textView1 = new TextView(this);
            textView1.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT));
            textView1.setTextColor(getResources().getColor(R.color.orange));
            textView1.setText(eventsForUser.get(i).getName());
            textView1.setPadding(20, 20, 20, 20);// in pixels (left, top, right, bottom)
            linearLayout.addView(textView1);
        }
    }


}
