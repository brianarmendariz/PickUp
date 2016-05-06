package csulb.edu.pickup;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by Nessa on 4/12/16.
 */
public class MMListViewAdapter extends BaseAdapter {


    public static final String FIRST_COLUMN = "First";
    public static final String SECOND_COLUMN = "Second";

    public ArrayList<LinkedHashMap<String, String>> list;
    Activity activity;
    TextView txtFirst;
    TextView txtSecond;

    public MMListViewAdapter(Activity activity,ArrayList<LinkedHashMap<String, String>> list){
        super();
        this.activity=activity;
        this.list=list;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub



        LayoutInflater inflater=activity.getLayoutInflater();


        convertView=inflater.inflate(R.layout.column_row_mm, null);

        txtFirst=(TextView) convertView.findViewById(R.id.name);
        txtSecond=(TextView) convertView.findViewById(R.id.username);



        HashMap<String, String> map=list.get(position);
        txtFirst.setText(map.get(FIRST_COLUMN));
        txtSecond.setText(map.get(SECOND_COLUMN));

        return convertView;
    }


}