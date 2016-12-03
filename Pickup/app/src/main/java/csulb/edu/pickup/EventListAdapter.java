package csulb.edu.pickup;

import android.widget.ImageView;
import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;


/**
 * Created by Brian on 9/16/2016.
 */
public class EventListAdapter<String> extends BaseAdapter
{
    private ArrayList<Event> eventList;
    private ArrayList<java.lang.String> distanceList;
    Activity activity;
    TextView textViewName;
    TextView textViewLocation;
    TextView textViewSport;
    TextView textViewPlayerAmount;
    TextView textViewDistanceAway;
    ImageView imageViewSportImage;
    int layout;
    private final Context context;
    private String fragment;

    public EventListAdapter(Activity activity, int layout, ArrayList<Event> eventList, Context context, String fragment) {
        super();
        this.activity = activity;
        this.layout = R.layout.friends_list;
        this.eventList = eventList;
        //this.distanceList = distanceList;
        this.context = context;
        this.fragment = fragment;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = activity.getLayoutInflater();

        convertView = inflater.inflate(R.layout.event_list, null);

        textViewName =(TextView) convertView.findViewById(R.id.event_list_name);
        textViewLocation = (TextView) convertView.findViewById(R.id.event_list_location);
        textViewSport = (TextView) convertView.findViewById(R.id.event_list_sport);
        textViewPlayerAmount = (TextView) convertView.findViewById(R.id.event_list_player_amount);
        textViewDistanceAway = (TextView) convertView.findViewById(R.id.event_list_distance_away);
        imageViewSportImage = (ImageView) convertView.findViewById(R.id.event_list_sport_image);

        java.lang.String name = eventList.get(position).getName();
        java.lang.String location = eventList.get(position).getAddress();
        java.lang.String sport = eventList.get(position).getSport();

        //Sarah changed in order to use with User Profile
        if(!eventList.isEmpty())
        {
            ArrayList<User> userList = null;
            // get number of people RSVP'd to event
            URLConnection http = new URLConnection();
            try {
                userList = http.sendGetRSVPList(eventList.get(position).getEventID());
            } catch(IOException e)
            {
                e.printStackTrace();
            }

            if(fragment.equals("profile"))
            {

            }
            else if(fragment.equals("list"))
            {
                Double distanceAway = eventList.get(position).getDistance();
                textViewDistanceAway.setText(distanceAway + " mi");
            }
            java.lang.String color1 = "#fea10f";
            java.lang.String color2 = "#696969";
            java.lang.String text = "<font color=" + color1 + ">" + userList.size()
                    + "</font><font color=" + color2 + ">/" + eventList.get(position).getTotalHeadCount() + "</font>";
            textViewPlayerAmount.setText(Html.fromHtml(text));
        }
        textViewName.setText(name);
        textViewLocation.setText(location);
        textViewSport.setText(sport);


        if (sport.equals("Badminton"))
        {
            imageViewSportImage.setImageResource(R.drawable.badminton_icon);
        }
        else if (sport.equals("Baseball"))
        {
            imageViewSportImage.setImageResource(R.drawable.baseball_icon);
        }
        else if (sport.equals("Basketball"))
        {
            imageViewSportImage.setImageResource(R.drawable.basketball_icon);
        }
        else if (sport.equals("Football"))
        {
            imageViewSportImage.setImageResource(R.drawable.football_icon);
        }
        else if (sport.equals("Handball"))
        {
            imageViewSportImage.setImageResource(R.drawable.handball_icon);
        }
        else if (sport.equals("Ice Hockey"))
        {
            imageViewSportImage.setImageResource(R.drawable.icehockey_icon);
        }
        else if (sport.equals("Racquetball"))
        {
            imageViewSportImage.setImageResource(R.drawable.racquetball_icon);
        }
        else if (sport.equals("Roller Hockey"))
        {
            imageViewSportImage.setImageResource(R.drawable.rollerhockey_icon);
        }
        else if (sport.equals("Softball"))
        {
            imageViewSportImage.setImageResource(R.drawable.softball_icon);
        }
        else if (sport.equals("Tennis"))
        {
            imageViewSportImage.setImageResource(R.drawable.tennis_icon);
        }
        else if (sport.equals("Volleyball"))
        {
            imageViewSportImage.setImageResource(R.drawable.volleyball_icon);
        }
        else
        {
            // should never hit this
        }

        return convertView;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return eventList.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return eventList.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }
}
