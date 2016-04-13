package csulb.edu.pickup;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Nessa on 4/11/16.
 */
public class MMActivity extends Activity {

    private ArrayList<LinkedHashMap<String, String>> list;
    public static final String FIRST_COLUMN = "First";
    public static final String SECOND_COLUMN = "Second";

    private TextView noOfTeams;
    private Button shuffleButton;
    private MMListViewAdapter adapter;
    private Event _event;
    String[][] arr1;


    final Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        setContentView(R.layout.matchmaking);
        URLConnection http = new URLConnection();
        Intent intent = getIntent();
        String extra = intent.getStringExtra("EventID");

        try {
            arr1 = http.sendGetRSVPList(Integer.parseInt(extra));
            for (int i = 0; i < arr1.length;i++) {
                String name = arr1[i][0];
                String username = arr1[i][0];
            }
        }
        catch (IOException e){
            Log.e("ERROR", "RVSP ERROR");
        }


        /*arr1 = new String[][] {{"a", "userA"},

                {"b","userB"}, {"c", "userC"} , {"d","userD"},

                { "e", "userE"} ,{ "f", "userF"} ,{ "g", "userG"} ,

                {"h", "userH"} , {"i", "userI"} , { "j", "userJ"}};*/


        noOfTeams = (TextView) findViewById(R.id.NoOfTeamsEditText);

        shuffleButton = (Button) findViewById(R.id.ShuffleButton);


        ListView listView= (ListView)findViewById(R.id.listView);
        list=new ArrayList<LinkedHashMap<String,String>>();

        adapter=new MMListViewAdapter(this, list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int pos = position + 1;
                Toast.makeText(MMActivity.this, Integer.toString(pos) + " Clicked", Toast.LENGTH_SHORT).show();
            }

        });




        shuffleButton.setOnClickListener(new OnClickListener() {

            @Override

            public void onClick(View v) {

                Log.d("Nessa", "YES THIS IS SHUFFLE");

                if (noOfTeams.getText().toString().equals("") || Integer.parseInt(noOfTeams.getText().toString()) <= 0
                        || Integer.parseInt(noOfTeams.getText().toString()) > arr1.length) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            context);

                    // set title
                    alertDialogBuilder.setTitle("Warning: ");

                    // set dialog message
                    alertDialogBuilder
                            .setMessage("Number of teams must be greater than 0 and less than the total number of people.")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // if this button is clicked, close
                                    // current activity
                                }
                            });


                    // create alert dialog
                    AlertDialog alertDialog = alertDialogBuilder.create();

                    // show it
                    alertDialog.show();
                } //end if alert
                else {


                    LinkedHashMap<String, String> hm = new LinkedHashMap<String, String>(arr1.length);

                    for (String[] mapping : arr1) {
                        hm.put(mapping[0], mapping[1]);
                    }
                    // Get a set of the entries
                    Set set = hm.entrySet();
                    // Get an iterator
                    Iterator i = set.iterator();
                    // Display elements
                    while (i.hasNext()) {
                        Map.Entry me = (Map.Entry) i.next();

                    }

                    String numOfTeams = "";
                    numOfTeams = noOfTeams.getText().toString();
                    if (numOfTeams.equals("")) {
                        numOfTeams = "1";
                    }
                    //numberOfmembers on a team is everyone / how many teams.
                    int numberOfMembers = hm.size() / Integer.parseInt(numOfTeams);
                    int remainder = hm.size() % Integer.parseInt(numOfTeams);
                    //get the number of the members of each team
                    int size[] = new int[Integer.parseInt(numOfTeams)];

                    //putting the size of each time to each team
                    for (int num = 0; num < remainder; num++) {
                        size[num] = numberOfMembers + 1;
                    }
                    for (int num = remainder; num < size.length; num++) {
                        size[num] = numberOfMembers;
                    }

                    List<String> keys = new ArrayList<String>(hm.keySet());
                    Collections.shuffle(keys);

                    LinkedHashMap shuffledMap = new LinkedHashMap();
                    //number of teams
                    int n = 0;
                    int iterator = 0;
                    System.out.println("Team # " + (n + 1));
                    for (Object o : keys) {
                        // Access keys/values in a random order
                        if (iterator == size[n]) {
                            System.out.println("Team # " + (n + 2));
                            n++;
                            iterator = 0;
                        }
                        System.out.print(o + ": ");
                        System.out.println(hm.get(o));
                        shuffledMap.put(o, hm.get(o));

                        iterator++;

                    }

                    iterator = 0;
                    n = 0;

                    list.clear();

                    for (Object o : keys) {
                        // Access keys/values in a random order

                        if (iterator == size[n]) {
                            System.out.println("Team # " + (n + 2));
                            n++;
                            iterator = 0;
                        }
                        LinkedHashMap<String, String> temp = new LinkedHashMap<String, String>();
                        temp.put(FIRST_COLUMN, "Team # " + (n + 1));
                        temp.put(SECOND_COLUMN, ((String) shuffledMap.get(o)));

                        System.out.print(o + ": ");
                        System.out.println(shuffledMap.get(o));
                        list.add(temp);

                        iterator++;

                    }

                    Log.d("Nessa", "size" + list.get(0).get(SECOND_COLUMN));

                    adapter.notifyDataSetChanged();

                }

            }

        });







    }


}
