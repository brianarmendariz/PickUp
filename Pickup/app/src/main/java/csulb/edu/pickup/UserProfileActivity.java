package csulb.edu.pickup;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;


import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

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
public class UserProfileActivity extends Activity {

    private static final int VIEW_MAP_EVENT = 2;

    private ImageView profileImage;
    private TextView name;
    private TextView birthday;
    private TextView gender;

    User thisUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle data = getIntent().getExtras();
        thisUser = (User) data.getParcelable("USER");


        //thisUser.getEmail();
        super.onCreate(savedInstanceState);

        setContentView(R.layout.view_profile);
        profileImage = (ImageView)findViewById(R.id.profileImageView);
        profileImage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 0);
            }
        });

        name  = (TextView) findViewById(R.id.NameEditText);
        birthday = (TextView) findViewById(R.id.BirthdayEditText);
        gender = (TextView) findViewById(R.id.GenderEditText);

        User user = data.getParcelable("VIEWUSER");
        System.out.println("viewUser " + user);
        if(user != null)
        {
            System.out.println("not null");
            name.setText(user.getFirstName());
            birthday.setText(user.getBirthday());
            gender.setText(user.getGender());
            setupEvents(viewUser(user.getEmail()));
        }
        else
        {
            System.out.println("null");
            name.setText(thisUser.getFirstName());
            birthday.setText(thisUser.getBirthday());
            gender.setText(thisUser.getGender());
            setupEvents(viewUser(thisUser.getEmail()));
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        if (id == R.id.user_logout) {
            LoginManager.getInstance().logOut();

            Intent loginActivityIntent = new Intent(getBaseContext(), LoginActivity.class);
            loginActivityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(loginActivityIntent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) throws RuntimeException {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK){
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
        }
    }

    public Bitmap getBitmapFromReturnedImage(Uri selectedImage, int reqWidth, int reqHeight) throws IOException {

        InputStream inputStream = getContentResolver().openInputStream(selectedImage);

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(inputStream, null, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // close the input stream
        inputStream.close();

        // reopen the input stream
        inputStream = getContentResolver().openInputStream(selectedImage);

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

    private ArrayList<Event> viewUser(String username) {
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
        TableLayout layout = (TableLayout)findViewById(R.id.profileTableLayout2);
        TableLayout.LayoutParams tableParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT);
        TableRow.LayoutParams rowParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);

        int prevTextViewId = 0;
        for(int i = 0; i < events.size(); i++)
        {
            final int id = i;
            String name = events.get(i).getName();
            String sport = events.get(i).getSport();
            String date = events.get(i).getEventDate();
            final TextView textViewName = new TextView(this);
            textViewName.setPadding(30,0,0,0);
            textViewName.setText(name);
            textViewName.setTextColor(getResources().getColor(R.color.dark_grey));
            textViewName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) { // onClick listener for event name
                    Bundle b = new Bundle();
                    b.putParcelable("USER", thisUser);
                    Intent intent = new Intent(getApplicationContext(), ViewEventActivity.class);
                    intent.putExtra("EventID", events.get(id).getEventID());
                    intent.putExtras(b);
                    startActivityForResult(intent, VIEW_MAP_EVENT);
                }
            });
            textViewName.setLayoutParams(rowParams);
            TableRow tableRow = new TableRow(this); // create a row
            tableRow.setLayoutParams(tableParams);
            tableRow.addView(textViewName); // add event name textview to row
            TextView textViewSport = new TextView(this);
            TextView textViewDate = new TextView(this);
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