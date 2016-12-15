package csulb.edu.pickup;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.imanoweb.calendarview.CalendarListener;
import com.imanoweb.calendarview.CustomCalendarView;
import com.imanoweb.calendarview.DayDecorator;
import com.imanoweb.calendarview.DayView;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;


/**
 * Created by Brain on 4/8/2016.
 */
public class CalendarFragment extends Fragment {

    HashMap<String, ArrayList<Event>> userEventsMap;
    ArrayList<Event> eventsForUser;
    User thisUser;
    View rootView;
    CustomCalendarView calendarView;

    /**
     * Creates the calendar. Days with events are seen in a different color. The decorator
     * day pattern was used to accomplish this task.
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle data = getActivity().getIntent().getExtras();
        thisUser = (User) data.getParcelable("USER");

        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        rootView = inflater.inflate(R.layout.calendar, container, false);
        getActivity().setTitle("Calendar");

        getEvents();

        setupCalendar();

        int[] colorArray = { getResources().getColor(R.color.orange) , getResources().getColor(R.color.lite_grey) ,
                getResources().getColor(R.color.white) , getResources().getColor(R.color.white) , };
        LinearLayout linearLayout = (LinearLayout)rootView.findViewById(R.id.CalendarLinearLayout);
        for(int i = 0; i < 2; i++)
        {
            TextView textView1 = new TextView(getActivity());
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            lp.gravity = Gravity.CENTER;
            textView1.setLayoutParams(lp);

            if(i == 0 )
            {
                textView1.setText("Created Event");

            }
            else if(i == 1)
            {
                textView1.setText("RSVP'd  Event");
            }
            textView1.setTextColor(colorArray[i]);
            textView1.setBackgroundColor(colorArray[i+2]);
            textView1.setPadding(20, 20, 20, 20);// in pixels (left, top, right, bottom)
            linearLayout.addView(textView1);
        }

        return rootView;
    }

    /**
     * get events from server and add them to hashmap
     */
    private void getEvents()
    {
        URLConnection http = new URLConnection();
        try
        {
            eventsForUser = http.sendGetEventsForUser(thisUser.getEmail());
        } catch(IOException e)
        {
            Toast.makeText(getActivity().getApplicationContext(), "No Events for User",
                    Toast.LENGTH_LONG).show();
        }

        userEventsMap = new HashMap<String, ArrayList<Event>>();
        for(Event event : eventsForUser)
        {
            String date = getDate(event.getEventStartDate());

            System.out.println("date " + date);
            if(userEventsMap.get(date) != null)
            {
                userEventsMap.get(date).add(event);
            }
            else
            {
                ArrayList<Event> list = new ArrayList<Event>();
                list.add(event);
                userEventsMap.put(date, list);
            }
        }
    }

    private String getDate(String bkwdsDate)
    {
        String[] dateSplit = bkwdsDate.split("-");
        String year = dateSplit[0];
        String month = dateSplit[1];
        String day = dateSplit[2];

        if(month.length() == 1)
        {
            month = "0" + month;
        }
        if(day.length() == 1)
        {
            day = "0" + month;
        }

        return day + "-" + month + "-" + year;
    }

    public void setupCalendar()
    {
        calendarView = (CustomCalendarView) rootView.findViewById(R.id.calendar_view); // initialize CustomCalendarView from layout
        final Calendar currentCalendar = Calendar.getInstance(Locale.getDefault()); // initialize calendar with date
        calendarView.setFirstDayOfWeek(Calendar.MONDAY); // show Monday as first date of week
        calendarView.setShowOverflowDate(false); // show/hide overflow days of a month

        List decorators = new ArrayList<>();
        decorators.add(new DaysDecorator()); //adding calendar day decorators
        calendarView.setDecorators(decorators);
        calendarView.refreshCalendar(currentCalendar);  //call refreshCalendar to update calendar the view

        //Handling custom calendar events
        calendarView.setCalendarListener(new CalendarListener() {
            @Override
            public void onDateSelected(Date date) {
                SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");

                // use hashmap so that the key can be the actual date
                if(userEventsMap.get(df.format(date)) != null) {
                    //send the list of events to the dialog
                    Bundle b = new Bundle();
                    b.putParcelable("USER", thisUser);
                    b.putParcelableArrayList("EVENTS", userEventsMap.get(df.format(date)));
                    //start the fragment
                    FragmentManager fm = getFragmentManager();
                    CalendarEventDialog dialogFragment = new CalendarEventDialog();
                    dialogFragment.setArguments(b);
                    dialogFragment.show(fm, "Sample Fragment");

                }
                calendarView.refreshCalendar(currentCalendar);
            }

            @Override
            public void onMonthChanged(Date date) {
                SimpleDateFormat df = new SimpleDateFormat("MM-yyyy");
//                Toast.makeText(CalendarActivity.this, df.format(date), Toast.LENGTH_SHORT).show();
            }
        });
    }


    /**
     * Created by Brain on 4/9/2016.
     */
    public class DaysDecorator implements DayDecorator {
        @Override
        public void decorate(DayView dayView) {

            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            String date = df.format(dayView.getDate());

            // add color to map to signal there is 1 to N events that day
            if(userEventsMap.get(date) != null)
            {
                boolean flag = true;
                ArrayList<Event> tempList = userEventsMap.get(date);
                for(int i = 0; i < tempList.size(); i++)
                {
                    String eventCreator = tempList.get(i).getCreatorEmail();
                    if(thisUser.getEmail().equals(eventCreator))
                    {
                        dayView.setBackgroundColor(getResources().getColor(R.color.orange));
                        flag = false;
                    }
                    else if(flag)
                    {
                        dayView.setBackgroundColor(getResources().getColor(R.color.lite_grey));
                    }
                }
            }
        }
    }

}
