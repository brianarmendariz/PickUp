package csulb.edu.pickup;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
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
 * Created by Sarah on 5/9/2016.
 */
public class FriendsFragment extends Fragment{


    ArrayList<User> friendList;
    User thisUser;
    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle data = getActivity().getIntent().getExtras();
        thisUser = (User) data.getParcelable("USER");
        //thisUser = new User("sarah","Shibley","sarahshib@outlook.com", "","","","");

        super.onCreate(savedInstanceState);
        getActivity().setTitle("Friends");

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        rootView = inflater.inflate(R.layout.friends_container, container, false);
        ListView listView= (ListView)rootView.findViewById(R.id.listView);

        URLConnection http = new URLConnection();
        // Intent intent = getActivity().getIntent();
        //Bundle args = getArguments();

        try {
            friendList = http.sendGetFriendsList(thisUser.getEmail());

            if(friendList != null)
            {
                BaseAdapter adapter = new FriendsListAdapter<String>(getActivity(), R.layout.friends_list, friendList, getActivity());

                listView = (ListView) rootView.findViewById(R.id.friend_list);
                listView.setAdapter(adapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String email = friendList.get(position).getEmail();

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
                        frgManager.beginTransaction().replace(R.id.content_frame, fragment).addToBackStack("FriendsFragment")
                                .commit();

                        Intent thisIntent = new Intent(getActivity().getBaseContext(), MainActivity.class);
                        thisIntent.putExtras(b);

                    }

                });
            }
            else
            {
                showAlert("User Has No Friends");

                Bundle b = new Bundle();
                //add current user
                b.putParcelable("USER", thisUser);

                Fragment fragment = new MapsFragment();
                fragment.setArguments(b);
                FragmentManager frgManager = getFragmentManager();
                frgManager.beginTransaction().replace(R.id.content_frame, fragment).addToBackStack("FriendsFragment")
                        .commit();

                Intent thisIntent = new Intent(getActivity().getBaseContext(), MainActivity.class);
                thisIntent.putExtras(b);
            }

        } catch (IOException e) {
            Log.e("ERROR", "FriendList ERROR");
        }



        return rootView;
    }

    public void showAlert(String message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(message);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //TODO
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
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
