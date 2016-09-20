package csulb.edu.pickup;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Created by Brian on 9/16/2016.
 */
public class EventListAdapter<String> extends BaseAdapter
{
    public ArrayList<Event> eventList;
    Activity activity;
    TextView textViewName;
    TextView textViewLocation;
    TextView textViewSport;
    TextView textViewPlayerAmount;
    TextView textViewPlayerTotal;
    int layout;
    private final Context context;

    public EventListAdapter(Activity activity, int layout, ArrayList<Event> eventList, Context context) {
        super();
        this.activity = activity;
        this.layout = R.layout.friends_list;
        this.eventList = eventList;
        this.context = context;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = activity.getLayoutInflater();

        convertView = inflater.inflate(R.layout.event_list, null);

        textViewName =(TextView) convertView.findViewById(R.id.event_list_name);
        textViewLocation = (TextView) convertView.findViewById(R.id.event_list_location);
        textViewSport = (TextView) convertView.findViewById(R.id.event_list_sport);
        textViewPlayerAmount = (TextView) convertView.findViewById(R.id.event_list_player_amount);


        //HashMap<java.lang.String, java.lang.String> map=friendList.get(position);
        textViewName.setText(eventList.get(position).getName());
        textViewLocation.setText(eventList.get(position).getAddress());
        textViewSport.setText(eventList.get(position).getSport());
        java.lang.String color1 = "#fea10f";
        java.lang.String color2 = "#181818";
        java.lang.String text = "<font color=" + color1 + ">" + eventList.get(position).getMaxNumberPpl()
            + "</font><font color=" + color2 + ">/" + eventList.get(position).getMaxNumberPpl() + "</font>";
        textViewPlayerAmount.setText(Html.fromHtml(text));

        if (position % 2 == 0) {
            convertView.setBackgroundColor(ContextCompat.getColor(context, R.color.lite_grey));
        } else {
            convertView.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
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
