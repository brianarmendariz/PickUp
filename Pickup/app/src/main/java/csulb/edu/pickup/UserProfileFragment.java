package csulb.edu.pickup;


import android.app.Activity;


import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;

import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;

import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SearchView;
import android.text.Html;
import android.text.Layout;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;

import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


/**
 * Created by Nessa on 3/19/16.
 * Edited by Sarah
 */

/*
    SHOW THE USER PROFILE NAME GENDER
 */
public class UserProfileFragment extends Fragment implements SearchView.OnQueryTextListener
{
    public static final String PREFS_NAME = "MyPrefsFile";
    private static int RESULT_LOAD_IMAGE = 1;
    private static final int VIEW_MAP_EVENT = 2;
    private ImageView profileImage;
    private TextView name;
    private TextView birthday;
    private TextView gender;
    private TextView userRating;
    private ImageButton upvote;
    private ImageButton downvote;
    private ImageButton follow;

    //current user
    User thisUser;
    //the user that is viewed.
    User viewUsername;

    User updatedUser;
    // 1 indicates upvoted
    // 0 indicates unrated
    // -1 indicates downvoted
    int rated = 0;
    boolean voted = false;
    boolean areFriends = false;
    View rootView;

    private static final int REQUEST_CODE = 1;
    private Bitmap bitmap;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu_main_search, menu); // removed to not double the menu items
        MenuItem item = menu.findItem(R.id.menu_search);
        SearchView sv = new SearchView(((MainActivity) getActivity()).getSupportActionBar().getThemedContext());
        MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);
        MenuItemCompat.setActionView(item, sv);
        sv.setOnQueryTextListener(this);
        sv.setIconifiedByDefault(false);
        sv.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        MenuItemCompat.setOnActionExpandListener(item, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                // Do something when collapsed
                return true;  // Return true to collapse action view
            }

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                // Do something when expanded
                return true;  // Return true to expand action view
            }
        });

        super.onCreateOptionsMenu(menu, inflater);


    }

    private ArrayList<String> getUsersFromServer() {
        ArrayList<String> usernames = null;
        URLConnection http = new URLConnection();
        try {
            usernames = http.sendGetUsernames();
        } catch (IOException e) {
        }
        return usernames;
    }

    private void viewUsersProfile(String username) {
        Bundle b = new Bundle();
        //add current user
        b.putParcelable("USER", thisUser);
        User user = getUser(username);
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

    private void popUpAlertDialog(String username) {
        Bundle b = new Bundle();
        b.putParcelable("USER", thisUser);
        Intent thisIntent = new Intent(getActivity().getBaseContext(), MainActivity.class);
        thisIntent.putExtras(b);
        b.putString("TITLE", "User Not Found!");
        b.putString("USERNAME", username);
        MyDialogFragment fragment = new MyDialogFragment();
        fragment.setArguments(b);
        FragmentManager frgManager = getFragmentManager();
        fragment.show(frgManager, "MyDialog");
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        ArrayList<String> usernames = getUsersFromServer(); // get users from the server
        // see if requested user is a member
        boolean member = false;
        for (int i = 0; i < usernames.size(); i++) {
            // if user is member break from loop
            if (query.equals(usernames.get(i))) {
                member = true;
                break;
            }
        }

        // view the requested users profile
        if (member) {
            viewUsersProfile(query);
        } else // alert that user does not exist
        {
            popUpAlertDialog(query);
        }

        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle data = getActivity().getIntent().getExtras();
        getActivity().setTitle("User Profile");

        thisUser = (User) data.getParcelable("USER");

        rootView = inflater.inflate(R.layout.view_profile, container, false);

        System.out.println("UserProfileFragment: " + thisUser.getEmail());


        super.onCreate(savedInstanceState);

        name = (TextView) rootView.findViewById(R.id.NameEditText);
        birthday = (TextView) rootView.findViewById(R.id.BirthdayEditText);
        gender = (TextView) rootView.findViewById(R.id.GenderEditText);
        upvote = (ImageButton) rootView.findViewById(R.id.thumbsup);
        downvote = (ImageButton) rootView.findViewById(R.id.thumbsdown);
        follow = (ImageButton) rootView.findViewById(R.id.follow);



        Bundle fragBundle = this.getArguments();
        final User viewUser = (User) fragBundle.getParcelable("VIEWUSER");
        viewUsername = viewUser;

        //If the user views his/her own profile
        if (viewUser == null)
        {
            System.out.println("null");
            name.setText(thisUser.getFirstName());
            birthday.setText(thisUser.getAge());
            gender.setText(thisUser.getGender());
            setupEvents(getEvents(thisUser.getEmail()));
            upvote.setVisibility(View.INVISIBLE);
            downvote.setVisibility(View.INVISIBLE);
            setupThisUserProfilePicture();
        }
        else
        {
            System.out.println("not null");
            name.setText(viewUser.getFirstName());
            birthday.setText(viewUser.getAge());
            gender.setText(viewUser.getGender());
            setupEvents(getEvents(viewUser.getEmail()));

            viewUserProfilePicture(viewUser.getPicturePath());

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT);
            params.weight = 1.0f;


            URLConnection http = new URLConnection();
            try {
                if (http.checkIfFriends(thisUser.getEmail(), viewUser.getEmail()).equals("true")) {
                    areFriends = true;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                //get the list
              //  String[][] RatingList = http.sendGetUserRatingsList(thisUser.getEmail());
                int rating = http.sendGetUserRatingsList(thisUser.getEmail(),viewUser.getEmail());
                rated = rating;
//
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (!voted && rated == 0) {
                upvote.setEnabled(true);
                downvote.setEnabled(true);
            }
            //for the user that undownvoted another user
            else if (rated == -1) {
//                upVoteButton.setText("Upvote");
//                downVoteButton.setText("Undownvote");
                upvote.setEnabled(true);
                downvote.setEnabled(false);

            } else if (rated == 0) {
                upvote.setEnabled(true);
                downvote.setEnabled(true);
//                upVoteButton.setText("Upvote");
//                downVoteButton.setText("Downvote");

            } else if (rated == 1) {
                upvote.setEnabled(false);
                downvote.setEnabled(true);
//                upVoteButton.setText("Unupvote");
//                downVoteButton.setText("Downvote");
            }

            if (areFriends) {
                follow.setImageResource(R.drawable.following);
            } else {
                follow.setImageResource(R.drawable.follow);

            }
//

            follow.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    URLConnection http = new URLConnection();
                    try {

                        if (areFriends) {
                            /**
                             * Erwin making changes to get rid of the string response and system.out
                             */
                            //String response = http.deleteFollow(thisUser.getEmail(), viewUsername.getEmail());
                            http.deleteFollow(thisUser.getEmail(), viewUsername.getEmail());
                            //System.out.println("delete result" + response);
                            areFriends = false;
                            follow.setImageResource(R.drawable.follow);
                        } else {
                            //String response = http.sendAddFriend(thisUser.getEmail(), viewUsername.getEmail());
                            http.sendFollow(thisUser.getEmail(), viewUsername.getEmail());
                            //System.out.println("add result" + response);

                            areFriends = true;
                            follow.setImageResource(R.drawable.following);
                        }
                    } catch (IOException ie) {
                        ie.printStackTrace();
                    }
                }
            });
            upvote.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    try {
                        URLConnection http = new URLConnection();
                        //get the list
                        rated = http.sendGetUserRatingsList(thisUser.getEmail(), viewUser.getEmail());
                        URLConnection http2 = new URLConnection();

                        //if they didn't vote to the viewUser at all, create a user in the database
                        if (rated == 0 && !voted) {
                            http.sendCreateRatings(thisUser.getEmail(), viewUsername.getEmail(), 1);
                            rated = 1;

                            viewUsername = http2.sendGetUser(viewUsername.getEmail());

                            int rating = 0;
                            String rate;
                            //Calculate the viewUsername's userRating
                            rating = Integer.parseInt(viewUsername.getUserRating()) + 1;
                            rate = rating + "";

                            //save to the server
                            http2.sendEditUser(viewUsername.getEmail(), viewUsername.getFirstName(), viewUsername.getlastName()
                                    , viewUsername.getAge(), viewUsername.getGender(), rate, "");

                            updatedUser = http2.sendGetUser(viewUsername.getEmail());
                            upvote.setEnabled(true);
                            upvote.setImageResource(R.drawable.thumbsup);
                            downvote.setEnabled(true);
                            downvote.setImageResource(R.drawable.thumbsdown);
                        }

                        //if the user downvoted the viewUser before, change the vote to 1
                        else if (rated == -1) {
                            http.sendEditUserRatings(thisUser.getEmail(), viewUsername.getEmail(), 1);
                            rated = 1;


                            //get the updated user
                            viewUsername = http2.sendGetUser(viewUsername.getEmail());

                            int rating = 0;
                            String rate;
                            //Calculate the viewUsername's userRating
                            rating = Integer.parseInt(viewUsername.getUserRating()) + 2;
                            rate = rating + "";

                            //save to the server
                            http2.sendEditUser(viewUsername.getEmail(), viewUsername.getFirstName(), viewUsername.getlastName()
                                    , viewUsername.getAge(), viewUsername.getGender(), rate, "");

                            //update the user
                            updatedUser = http2.sendGetUser(viewUsername.getEmail());
                            upvote.setEnabled(false);
                            upvote.setImageResource(R.drawable.thumbsup_clicked);
                            downvote.setEnabled(true);
                            downvote.setImageResource(R.drawable.thumbsdown);
                        }

                        //if the user vote the unrated viewUser, rate = 1
                        else if (rated == 0) {
                            //change the vote to 1
                            http.sendEditUserRatings(thisUser.getEmail(), viewUsername.getEmail(), 1);
                            rated = 1;


                            //get the updated user
                            viewUsername = http2.sendGetUser(viewUsername.getEmail());


                            int rating = 0;
                            String rate;
                            //Calculate the viewUsername's userRating
                            rating = Integer.parseInt(viewUsername.getUserRating()) + 1;
                            rate = rating + "";

                            //save to the server
                            http2.sendEditUser(viewUsername.getEmail(), viewUsername.getFirstName(), viewUsername.getlastName()
                                    , viewUsername.getAge(), viewUsername.getGender(), rate, "");

                            //update the user
                            updatedUser = http2.sendGetUser(viewUsername.getEmail());
                            upvote.setEnabled(false);
                            upvote.setImageResource(R.drawable.thumbsup_clicked);
                            downvote.setEnabled(true);
                            downvote.setImageResource(R.drawable.thumbsdown);
                        }

                        //if the user upvoted the viewUser before, change the vote to 0
                        else if (rated == 1) {

                            http.sendEditUserRatings(thisUser.getEmail(), viewUsername.getEmail(), 0);

                            rated = 0;

                            int rating = 0;
                            String rate;

                            //get the updated user
                            viewUsername = http2.sendGetUser(viewUsername.getEmail());

                            //Calculate the viewUsername's userRating
                            rating = Integer.parseInt(viewUsername.getUserRating()) - 1;
                            rate = rating + "";

                            //save to the server
                            http2.sendEditUser(viewUsername.getEmail(), viewUsername.getFirstName(), viewUsername.getlastName()
                                    , viewUsername.getAge(), viewUsername.getGender(), rate, "");

                            //update the user
                            updatedUser = http2.sendGetUser(viewUsername.getEmail());
                            upvote.setEnabled(true);
                            upvote.setImageResource(R.drawable.thumbsup);
                            downvote.setEnabled(true);
                            downvote.setImageResource(R.drawable.thumbsdown);
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {

                        //set the text to the updated user rating

                    }
                }
            });

            downvote.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    try {
                        URLConnection http = new URLConnection();
                       rated = http.sendGetUserRatingsList(thisUser.getEmail(),viewUser.getEmail());

                        URLConnection http3 = new URLConnection();

                        //the user never voted
                        if (rated == 0 && !voted) {
                            http.sendCreateRatings(thisUser.getEmail(), viewUsername.getEmail(), -1);
                            rated = -1;

                            viewUsername = http3.sendGetUser(viewUsername.getEmail());

                            int rating = 0;
                            String rate;
                            //Calculate the viewUsername's userRating
                            rating = Integer.parseInt(viewUsername.getUserRating()) - 1;
                            rate = rating + "";

                            //save to the server
                            http3.sendEditUser(viewUsername.getEmail(), viewUsername.getFirstName(), viewUsername.getlastName()
                                    , viewUsername.getAge(), viewUsername.getGender(), rate, "");

                            updatedUser = http3.sendGetUser(viewUsername.getEmail());

                            upvote.setEnabled(true);
                            upvote.setImageResource(R.drawable.thumbsup);
                            downvote.setEnabled(false);
                            downvote.setImageResource(R.drawable.thumbsdown_clicked);
                        }

                        //if the user upvoted before
                        else if (rated == 1) {
                            http.sendEditUserRatings(thisUser.getEmail(), viewUsername.getEmail(), -1);
                            rated = -1;


                            //get the updated user
                            viewUsername = http3.sendGetUser(viewUsername.getEmail());

                            int rating = 0;
                            String rate;
                            //Calculate the viewUsername's userRating
                            rating = Integer.parseInt(viewUsername.getUserRating()) - 2;
                            rate = rating + "";

                            //save to the server
                            http3.sendEditUser(viewUsername.getEmail(), viewUsername.getFirstName(), viewUsername.getlastName()
                                    , viewUsername.getAge(), viewUsername.getGender(), rate, "");

                            //update the user
                            updatedUser = http3.sendGetUser(viewUsername.getEmail());
                            upvote.setEnabled(true);
                            upvote.setImageResource(R.drawable.thumbsup);
                            downvote.setEnabled(false);
                            downvote.setImageResource(R.drawable.thumbsdown_clicked);
                        }

                        //if the user downvote the viewUser
                        else if (rated == 0) {
                            http.sendEditUserRatings(thisUser.getEmail(), viewUsername.getEmail(), -1);
                            rated = -1;

                            //get the updated user
                            viewUsername = http3.sendGetUser(viewUsername.getEmail());


                            int rating = 0;
                            String rate;
                            //Calculate the viewUsername's userRating
                            rating = Integer.parseInt(viewUsername.getUserRating()) - 1;
                            rate = rating + "";

                            //save to the server
                            http3.sendEditUser(viewUsername.getEmail(), viewUsername.getFirstName(), viewUsername.getlastName()
                                    , viewUsername.getAge(), viewUsername.getGender(), rate, "");

                            //update the user
                            updatedUser = http3.sendGetUser(viewUsername.getEmail());
                            upvote.setEnabled(true);
                            upvote.setImageResource(R.drawable.thumbsup);
                            downvote.setEnabled(false);
                            downvote.setImageResource(R.drawable.thumbsdown_clicked);
                        }

                        //if the user downvoted viewUser before
                        else if (rated == -1) {
                            http.sendEditUserRatings(thisUser.getEmail(), viewUsername.getEmail(), 0);
                            rated = 0;


                            int rating = 0;
                            String rate;

                            //get the updated user
                            viewUsername = http3.sendGetUser(viewUsername.getEmail());

                            //Calculate the viewUsername's userRating
                            rating = Integer.parseInt(viewUsername.getUserRating()) + 1;
                            rate = rating + "";

                            //save to the server
                            http3.sendEditUser(viewUsername.getEmail(), viewUsername.getFirstName(), viewUsername.getlastName()
                                    , viewUsername.getAge(), viewUsername.getGender(), rate, "");

                            //update the user
                            updatedUser = http3.sendGetUser(viewUsername.getEmail());
                            upvote.setEnabled(true);
                            upvote.setImageResource(R.drawable.thumbsup);
                            downvote.setEnabled(true);
                            downvote.setImageResource(R.drawable.thumbsup);
                        }
                        //userRating.setText("10");
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {

                        //update the text
                        //userRating.setText(updatedUser.getUserRating());

                    }
                }
            });


        }
        return rootView;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) throws RuntimeException
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == Activity.RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            ImageView imageView = (ImageView) rootView.findViewById(R.id.profileImageView);

            Bitmap bmp = null;
            try {
                bmp = getBitmapFromUri(selectedImage);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            imageView.setImageBitmap(bmp);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] bArray = baos.toByteArray();
            //System.out.println("bArray " + bArray);

            String encodedImage = Base64.encodeToString(bArray, Base64.DEFAULT);
            //System.out.println("encodedImage " + encodedImage);
            System.out.println(encodedImage.length());

            URLConnection url = new URLConnection();
            try
            {
                String response = url.sendEditProfilePic(thisUser.getEmail(), encodedImage);

                if(response.equals("true") || response.equals("true\t"))
                {
                    Toast.makeText(getActivity(), "Profile Pic Updated",
                            Toast.LENGTH_LONG).show();

                    SharedPreferences settings = getActivity().getSharedPreferences(PREFS_NAME, 0);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("PROFILEPICTURE", encodedImage);
                    editor.commit();
                }
                else if(response.equals("false"))
                {
                    Toast.makeText(getActivity(), "Profile Pic NOT Update",
                            Toast.LENGTH_LONG).show();
                }
            } catch(IOException | JSONException e)
            {
                e.printStackTrace();
            }
        }
    }

    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor =
                getActivity().getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }


    private User getUser(String username) {
        User user = null;
        URLConnection http = new URLConnection();
        try {
            user = http.sendGetUser(username);
            System.out.println("user " + user);
        } catch (IOException e) {
        }
        return user;
    }

    private ArrayList<Event> getEvents(String username) {
        ArrayList<Event> events = null;
        URLConnection http = new URLConnection();
        try {
            events = http.sendGetEventsForUser(username);
        } catch (IOException e) {
        }
        return events;
    }

    private void setupEvents(final ArrayList<Event> events) {
        ListView listView = (ListView) rootView.findViewById(R.id.profile_list_event);

        BaseAdapter adapter = new EventListAdapter<String>(getActivity(), R.layout.event_list, events, getActivity(), "profile");

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle b = new Bundle();
                b.putParcelable("USER", thisUser);
                b.putString("EventID", events.get(position).getEventID() + "");
                Fragment fragment = new ViewEventFragment1();
                fragment.setArguments(b);
                FragmentManager frgManager = getFragmentManager();
                frgManager.beginTransaction().replace(R.id.content_frame, fragment).addToBackStack("EventListFragment")
                        .commit();

                Intent thisIntent = new Intent(getActivity().getBaseContext(), MainActivity.class);
                thisIntent.putExtras(b);

            }
        });
    }

    public void setupThisUserProfilePicture()
    {
        profileImage = (ImageView) rootView.findViewById(R.id.profileImageView);

        SharedPreferences settings = getActivity().getSharedPreferences(PREFS_NAME, 0);
        String settingsLastUpdate = settings.getString("PROFILEPICTURE", "DEFAULT");

        // set picture to default pic
        if(settingsLastUpdate.equals("DEFAULT"))
        {
            Bitmap bm = BitmapFactory.decodeResource(getResources(),
                    R.drawable.com_facebook_profile_picture_blank_portrait);
            Bitmap resized = Bitmap.createScaledBitmap(bm, 150, 150, true);
            Bitmap conv_bm = BitmapHelper.getRoundedRectBitmap(resized, 150);
            profileImage.setImageBitmap(conv_bm);
        }
        else // set picture to local pic
        {
            byte[] byteArray = Base64.decode(settingsLastUpdate, Base64.DEFAULT);
            Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

            // set the picture
            profileImage.setImageBitmap(bmp);

            DisplayMetrics dm = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);

            profileImage.setMinimumHeight(dm.heightPixels);
            profileImage.setMinimumWidth(dm.widthPixels);
        }

        // on click listener to be able to set the picture
        profileImage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });
    }

    public void viewUserProfilePicture(String profilePic)
    {
        profileImage = (ImageView) rootView.findViewById(R.id.profileImageView);

        // set picture to default pic
        if(profilePic.equals("") || profilePic == null)
        {
            Bitmap bm = BitmapFactory.decodeResource(getResources(),
                    R.drawable.com_facebook_profile_picture_blank_portrait);
            Bitmap resized = Bitmap.createScaledBitmap(bm, 150, 150, true);
            Bitmap conv_bm = BitmapHelper.getRoundedRectBitmap(resized, 150);
            profileImage.setImageBitmap(conv_bm);
        }
        else // set picture to local pic
        {
            byte[] byteArray = Base64.decode(profilePic, Base64.DEFAULT);
            Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

            // set the picture
            profileImage.setImageBitmap(bmp);

            DisplayMetrics dm = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);

            profileImage.setMinimumHeight(dm.heightPixels);
            profileImage.setMinimumWidth(dm.widthPixels);
        }
    }

}