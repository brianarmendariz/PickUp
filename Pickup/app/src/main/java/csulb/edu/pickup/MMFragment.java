package csulb.edu.pickup;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
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
public class MMFragment extends Fragment {

    private ArrayList<LinkedHashMap<String, String>> list;
    public static final String FIRST_COLUMN = "First";
    //Email
    public static final String SECOND_COLUMN = "Second";

    private TextView noOfTeams;
    private Button shuffleButton;
    private MMListViewAdapter adapter;
    private Event _event;
    String[][] arr1;

    LinkedHashMap shuffledMap;

    User thisUser;
    View rootView;


    final Context context = getActivity();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle data = getActivity().getIntent().getExtras();
        thisUser = (User) data.getParcelable("USER");

        super.onCreate(savedInstanceState);
        getActivity().setTitle("MatchMaking");

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        rootView = inflater.inflate(R.layout.matchmaking, container, false);
        URLConnection http = new URLConnection();
        // Intent intent = getActivity().getIntent();
        Bundle args = getArguments();
        String extra = args.getString("EventID");

        try {
            arr1 = http.sendGetRSVPList(Integer.parseInt(extra) + "");
            for (int i = 0; i < arr1.length; i++) {
                String name = arr1[i][0];
                String username = arr1[i][1];
            }
        } catch (IOException e) {
            Log.e("ERROR", "RVSP ERROR");
        }

        /*arr1 = new String[][] {{"a", "userA"},

                {"b","userB"}, {"c", "userC"} , {"d","userD"},

                { "e", "userE"} ,{ "f", "userF"} ,{ "g", "userG"} ,

                {"h", "userH"} , {"i", "userI"} , { "j", "userJ"}};*/


        noOfTeams = (TextView) rootView.findViewById(R.id.NoOfTeamsEditText);

        shuffleButton = (Button) rootView.findViewById(R.id.ShuffleButton);


        ListView listView= (ListView)rootView.findViewById(R.id.listView);
        list=new ArrayList<LinkedHashMap<String,String>>();

        adapter=new MMListViewAdapter(this.getActivity(), list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String email = list.get(position).get(SECOND_COLUMN);
                //Toast.makeText(getActivity(), email, Toast.LENGTH_SHORT).show();

                Bundle b = new Bundle();
                //add current user
                b.putParcelable("USER", thisUser);
                User user = viewUser(email);
                if (user != null && !thisUser.getEmail().equals(user.getEmail())) {
                    System.out.println("putting the parceable");
                    b.putParcelable("VIEWUSER", user);
                } else {
                    System.out.println("not putting the parceable");
                }
                Fragment fragment = new UserProfileFragment();
                fragment.setArguments(b);
                FragmentManager frgManager = getFragmentManager();
                frgManager.beginTransaction().replace(R.id.content_frame, fragment).addToBackStack("Map")
                        .commit();


                Intent thisIntent = new Intent(getActivity().getBaseContext(), MainActivity.class);
                thisIntent.putExtras(b);

            }

        });




        shuffleButton.setOnClickListener(new OnClickListener() {

            @Override

            public void onClick(View v) {

                if (noOfTeams.getText().toString().equals("") || Integer.parseInt(noOfTeams.getText().toString()) <= 0
                        || Integer.parseInt(noOfTeams.getText().toString()) > arr1.length) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            getActivity());

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

                    shuffledMap = new LinkedHashMap();
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

                    adapter.notifyDataSetChanged();

                }

            }

        });

        return rootView;





    }

    private User viewUser(String username)
    {
        User user = null;
        URLConnection http = new URLConnection();
        try
        {
            user = http.sendGetUser(username);
            System.out.println("user " + user);
        } catch(IOException e)
        {
        }
        return user;
    }


}
