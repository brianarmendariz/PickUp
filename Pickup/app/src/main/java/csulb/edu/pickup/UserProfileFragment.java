package csulb.edu.pickup;


import android.app.Activity;


import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;

import android.net.Uri;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SearchView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


/**
 * Created by Nessa on 3/19/16.
 */

/*
    SHOW THE USER PROFILE NAME GENDER
 */
public class UserProfileFragment extends Fragment implements SearchView.OnQueryTextListener {

    private static final int VIEW_MAP_EVENT = 2;

    private ImageView profileImage;
    private TextView name;
    private TextView birthday;
    private TextView gender;
    private TextView userRating;
    private Button upvote;
    private Button downvote;
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

    View rootView;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu (Menu menu, MenuInflater inflater) {

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

    private ArrayList<String> getUsersFromServer()
    {
        ArrayList<String> usernames = null;
        URLConnection http = new URLConnection();
        try {
            usernames = http.sendGetUsernames();
        } catch (IOException e) {
        }
        return usernames;
    }

    private void viewUsersProfile(String username)
    {
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

    private void popUpAlertDialog(String username)
    {
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
        for(int i = 0; i < usernames.size(); i++)
        {
            // if user is member break from loop
            if(query.equals(usernames.get(i))) {
                member = true;
                break;
            }
        }

        // view the requested users profile
        if(member)
        {
            viewUsersProfile(query);
        }
        else // alert that user does not exist
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle data = getActivity().getIntent().getExtras();
        thisUser = (User) data.getParcelable("USER");
        rootView = inflater.inflate(R.layout.view_profile, container, false);

        System.out.println("UserProfileFragment: " + thisUser.getEmail());


        super.onCreate(savedInstanceState);
        getActivity().setTitle("User Profile");

        profileImage = (ImageView)rootView.findViewById(R.id.profileImageView);
        profileImage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 0);
            }
        });

        name  = (TextView) rootView.findViewById(R.id.NameEditText);
        birthday = (TextView) rootView.findViewById(R.id.BirthdayEditText);
        gender = (TextView) rootView.findViewById(R.id.GenderEditText);
        userRating = (TextView) rootView.findViewById(R.id.UserRatingEditText);

        Bundle fragBundle = this.getArguments();
        User viewUser = (User)fragBundle.getParcelable("VIEWUSER");
        viewUsername = viewUser;

        if(viewUser == null)
        {
            System.out.println("null");
            name.setText(thisUser.getFirstName());
            birthday.setText(thisUser.getBirthday());
            gender.setText(thisUser.getGender());
            userRating.setText(thisUser.getUserRating());
            setupEvents(getEvents(thisUser.getEmail()));
        }
        else  //If the user views his/her own profile
        {
            System.out.println("not null");
            name.setText(viewUser.getFirstName());
            birthday.setText(viewUser.getBirthday());
            gender.setText(viewUser.getGender());
            userRating.setText(viewUser.getUserRating());
            setupEvents(getEvents(viewUser.getEmail()));

            Button upVoteButton = new Button(this.getActivity());
            Button downVoteButton = new Button(this.getActivity());
            LinearLayout llLayout = (LinearLayout) rootView.findViewById(R.id.profileLinearLayout3);
            llLayout.setOrientation(LinearLayout.HORIZONTAL);


            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT);
            params.weight = 1.0f;


            URLConnection http4 = new URLConnection();
            try {
                //get the list
                String[][] RatingList = http4.sendGetUserRatingsList(thisUser.getEmail());
                for (int i = 0; i < RatingList.length; i++) {

                    //already down rated this other user
                    if (RatingList[i][0].equals(thisUser.getEmail()) && RatingList[i][1].equals(viewUsername.getEmail())
                            && RatingList[i][2].equals("-1")) {
                        rated = -1;

                    }
                    //already up rated this other user
                    if (RatingList[i][0].equals(thisUser.getEmail()) && RatingList[i][1].equals(viewUsername.getEmail())
                            && RatingList[i][2].equals("1")) {
                        rated = 1;

                    }

                    //already unrated this other user
                    if (RatingList[i][0].equals(thisUser.getEmail()) && RatingList[i][1].equals(viewUsername.getEmail())
                            && RatingList[i][2].equals("0")) {
                        voted = true;
                        rated = 0;
                    }

                } // end checking for loop
            }
            catch( IOException e) {
                e.printStackTrace();
            }

            if(!voted && rated == 0) {
                upVoteButton.setText("Upvote");
                downVoteButton.setText("Downvote");

            }
            //for the user that undownvoted another user
            else if (rated == -1) {
                upVoteButton.setText("Upvote");
                downVoteButton.setText("Undownvote");

            }

            else if (rated == 0) {
                upVoteButton.setText("Upvote");
                downVoteButton.setText("Downvote");

            }
            else if (rated == 1) {
                upVoteButton.setText("Unupvote");
                downVoteButton.setText("Downvote");
            }

            upVoteButton.setTextColor(Color.parseColor("#008000"));
            upVoteButton.setGravity(Gravity.CENTER_HORIZONTAL);
            upVoteButton.setLayoutParams(params);

            downVoteButton.setTextColor(Color.parseColor("#008000"));
            downVoteButton.setGravity(Gravity.CENTER_HORIZONTAL);
            downVoteButton.setLayoutParams(params);

            //set the the actually upvote button to use
            upvote = upVoteButton;
            downvote = downVoteButton;

            upVoteButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    try {
                        URLConnection http = new URLConnection();
                        //get the list
                        String[][] RatingList = http.sendGetUserRatingsList(thisUser.getEmail());
                        for (int i = 0; i < RatingList.length; i++) {

                            //already down rated this other user
                            if (RatingList[i][0].equals(thisUser.getEmail()) && RatingList[i][1].equals(viewUsername.getEmail())
                                    && RatingList[i][2].equals("-1")) {
                                rated = -1;

                            }
                            //already up rated this other user
                            if (RatingList[i][0].equals(thisUser.getEmail()) && RatingList[i][1].equals(viewUsername.getEmail())
                                    && RatingList[i][2].equals("1")) {
                                rated = 1;

                            }

                            //already unrated this other user
                            if (RatingList[i][0].equals(thisUser.getEmail()) && RatingList[i][1].equals(viewUsername.getEmail())
                                    && RatingList[i][2].equals("0")) {
                                voted = true;
                            }

                        } // end checking for loop

                        URLConnection http2 = new URLConnection();

                        //if they didn't vote to the viewUser at all, create a user in the database
                        if (rated == 0 && !voted) {
                            http.sendCreateRatings(thisUser.getEmail(), viewUsername.getEmail(), 1);
                            rated = 1;

                            viewUsername = http2.sendGetUser(viewUsername.getEmail());

                            int rating = 0;
                            String rate;
                            //Calculate the viewUsername's userRating
                            rating = Integer.parseInt(viewUsername.userRating) + 1;
                            rate =  rating + "";

                            //save to the server
                            http2.sendEditUser(viewUsername.getEmail(), viewUsername.getFirstName(), viewUsername.getlastName()
                                    , viewUsername.getBirthday(), viewUsername.getGender(), rate, "");

                            updatedUser = http2.sendGetUser(viewUsername.getEmail());
                            upvote.setText("Unupvote");
                            downvote.setText("Downvote");
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
                            rating = Integer.parseInt(viewUsername.userRating) + 2;
                            rate =  rating + "";

                            //save to the server
                            http2.sendEditUser(viewUsername.getEmail(), viewUsername.getFirstName(), viewUsername.getlastName()
                                    , viewUsername.getBirthday(), viewUsername.getGender(), rate, "");

                            //update the user
                            updatedUser = http2.sendGetUser(viewUsername.getEmail());

                            upvote.setText("Unupvote");
                            downvote.setText("Downvote");
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
                            rating = Integer.parseInt(viewUsername.userRating) + 1;
                            rate =  rating + "";

                            //save to the server
                            http2.sendEditUser(viewUsername.getEmail(), viewUsername.getFirstName(), viewUsername.getlastName()
                                    , viewUsername.getBirthday(), viewUsername.getGender(), rate, "");

                            //update the user
                            updatedUser = http2.sendGetUser(viewUsername.getEmail());

                            upvote.setText("Unupvote");
                            downvote.setText("Downvote");

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
                            rating = Integer.parseInt(viewUsername.userRating) - 1;
                            rate =  rating + "";

                            //save to the server
                            http2.sendEditUser(viewUsername.getEmail(), viewUsername.getFirstName(), viewUsername.getlastName()
                                    , viewUsername.getBirthday(), viewUsername.getGender(), rate, "");

                            //update the user
                            updatedUser = http2.sendGetUser(viewUsername.getEmail());

                            upvote.setText("Upvote");
                            downvote.setText("Downvote");

                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    finally {

                        //set the text to the updated user rating
                        userRating.setText(updatedUser.getUserRating());

                    }
                }
            });

            downVoteButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    try {
                        URLConnection http = new URLConnection();
                        String[][] RatingList = http.sendGetUserRatingsList(thisUser.getEmail());
                        for (int i = 0; i < RatingList.length; i++) {

                            //already down rated this other user
                            if (RatingList[i][0].equals(thisUser.getEmail()) && RatingList[i][1].equals(viewUsername.getEmail())
                                    && RatingList[i][2].equals("-1")) {
                                rated = -1;
                            }
                            //already up rated this other user
                            if (RatingList[i][0].equals(thisUser.getEmail()) && RatingList[i][1].equals(viewUsername.getEmail())
                                    && RatingList[i][2].equals("1")) {
                                rated = 1;


                            }

                            //already unrated this other user
                            if (RatingList[i][0].equals(thisUser.getEmail()) && RatingList[i][1].equals(viewUsername.getEmail())
                                    && RatingList[i][2].equals("0")) {
                                voted = true;
                            }


                        }

                        URLConnection http3 = new URLConnection();

                        //the user never voted
                        if (rated == 0 && !voted) {
                            http.sendCreateRatings(thisUser.getEmail(), viewUsername.getEmail(), -1);
                            rated = -1;

                            viewUsername = http3.sendGetUser(viewUsername.getEmail());

                            int rating = 0;
                            String rate;
                            //Calculate the viewUsername's userRating
                            rating = Integer.parseInt(viewUsername.userRating) - 1;
                            rate =  rating + "";

                            //save to the server
                            http3.sendEditUser(viewUsername.getEmail(), viewUsername.getFirstName(), viewUsername.getlastName()
                                    , viewUsername.getBirthday(), viewUsername.getGender(), rate, "");

                            updatedUser = http3.sendGetUser(viewUsername.getEmail());

                            upvote.setText("Upvote");
                            downvote.setText("Undownvote");


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
                            rating = Integer.parseInt(viewUsername.userRating) - 2;
                            rate =  rating + "";

                            //save to the server
                            http3.sendEditUser(viewUsername.getEmail(), viewUsername.getFirstName(), viewUsername.getlastName()
                                    , viewUsername.getBirthday(), viewUsername.getGender(), rate, "");

                            //update the user
                            updatedUser = http3.sendGetUser(viewUsername.getEmail());

                            upvote.setText("Upvote");
                            downvote.setText("Undownvote");

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
                            rating = Integer.parseInt(viewUsername.userRating) - 1;
                            rate =  rating + "";

                            //save to the server
                            http3.sendEditUser(viewUsername.getEmail(), viewUsername.getFirstName(), viewUsername.getlastName()
                                    , viewUsername.getBirthday(), viewUsername.getGender(), rate, "");

                            //update the user
                            updatedUser = http3.sendGetUser(viewUsername.getEmail());

                            upvote.setText("Upvote");
                            downvote.setText("Undownvote");

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
                            rating = Integer.parseInt(viewUsername.userRating) + 1;
                            rate =  rating + "";

                            //save to the server
                            http3.sendEditUser(viewUsername.getEmail(), viewUsername.getFirstName(), viewUsername.getlastName()
                                    , viewUsername.getBirthday(), viewUsername.getGender(), rate, "");

                            //update the user
                            updatedUser = http3.sendGetUser(viewUsername.getEmail());

                            upvote.setText("Upvote");
                            downvote.setText("Downvote");
                        }
                        //userRating.setText("10");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    finally {

                        //update the text
                        userRating.setText(updatedUser.getUserRating());

                    }
                }
            });

            llLayout.addView(upvote);
            llLayout.addView(downvote);

        }
        return rootView;
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) throws RuntimeException {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        /*not sure what this is for - uncomment it later?*/
        //if (resultCode == RESULT_OK){
        Uri targetUri = data.getData();
        Bitmap bitmap;


        try {

            //bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(targetUri));
            bitmap = getBitmapFromReturnedImage(targetUri,200,200);
            profileImage.setImageBitmap(bitmap);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        catch(NullPointerException e) {
            e.printStackTrace();
        }
        //}
    }

    public Bitmap getBitmapFromReturnedImage(Uri selectedImage, int reqWidth, int reqHeight) throws IOException {

        InputStream inputStream = getActivity().getContentResolver().openInputStream(selectedImage);

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(inputStream, null, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // close the input stream
        inputStream.close();

        // reopen the input stream
        inputStream = getActivity().getContentResolver().openInputStream(selectedImage);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeStream(inputStream, null, options);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    private User getUser(String username)
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

    private ArrayList<Event> getEvents(String username) {
        ArrayList<Event> events = null;
        URLConnection http = new URLConnection();
        try {
            events = http.sendGetEventsForUser(username);
        } catch (IOException e) {
        }
        return events;
    }

    private void setupEvents(final ArrayList<Event> events)
    {
        TableLayout layout = (TableLayout)rootView.findViewById(R.id.profileTableLayout2);
        TableLayout.LayoutParams tableParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT);
        TableRow.LayoutParams rowParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);

        int prevTextViewId = 0;
        for(int i = 0; i < events.size(); i++)
        {
            final int id = i;
            String name = events.get(i).getName();
            String sport = events.get(i).getSport();
            String date = events.get(i).getEventDate();
            final TextView textViewName = new TextView(this.getActivity());
            textViewName.setPadding(30,0,0,0);
            textViewName.setText(name);
            textViewName.setTextColor(getResources().getColor(R.color.dark_grey));
            textViewName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) { // onClick listener for event name

                    Bundle args = new Bundle();
                    Fragment fragment = new ViewEventFragment();
                    args.putString(FragmentOne.ITEM_NAME, new DrawerItem("View Event", 0)
                            .getItemName());
                    args.putString("EventID", events.get(id).getEventID());
                    fragment.setArguments(args);
                    FragmentManager frgManager = getFragmentManager();
                    frgManager.beginTransaction().replace(R.id.content_frame, fragment).addToBackStack( "User Profile" )
                            .commit();


                }
            });
            textViewName.setLayoutParams(rowParams);
            TableRow tableRow = new TableRow(this.getActivity()); // create a row
            tableRow.setLayoutParams(tableParams);
            tableRow.addView(textViewName); // add event name textview to row
            TextView textViewSport = new TextView(this.getActivity());
            TextView textViewDate = new TextView(this.getActivity());
            textViewSport.setPadding(50,0,50,0);
            textViewDate.setPadding(0,0,30,0);
            textViewSport.setText(sport); // add sport to textview
            textViewDate.setText(date); // add date to textview
            textViewSport.setTextColor(getResources().getColor(R.color.dark_grey));
            textViewDate.setTextColor(getResources().getColor(R.color.dark_grey));
            tableRow.addView(textViewSport); // add sport textview to row
            tableRow.addView(textViewDate);  // add date textview to row
            tableRow.setGravity(Gravity.CENTER);
            layout.addView(tableRow, tableParams); // add row to tablelayout
        }
    }


}