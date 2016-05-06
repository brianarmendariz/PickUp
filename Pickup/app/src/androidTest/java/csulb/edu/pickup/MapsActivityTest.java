package csulb.edu.pickup;

import android.content.Intent;
import android.os.Bundle;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.ImageButton;
import android.widget.Toast;

import com.facebook.FacebookSdk;

/**
 * Created by Nessa on 5/4/16.
 */
public class MapsActivityTest extends ActivityInstrumentationTestCase2<MapsActivity> {

    MapsActivity mapsActivity;
    ImageButton imageButton;
    @SuppressWarnings("deprecation")
    public MapsActivityTest()
    {
        super( MapsActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        User thisUser = new User("Nessa", "C", "n@hotmail.com","a","2016-01-01","female", "0");
        Bundle b = new Bundle();
        b.putParcelable("USER", thisUser);
        Intent mapIntent = new Intent();
        mapIntent.putExtras(b);
        setActivityIntent(mapIntent);
        mapsActivity = this.getActivity();
        FacebookSdk.sdkInitialize(mapsActivity.getApplicationContext());
        imageButton = (ImageButton) mapsActivity.findViewById(R.id.imageButton);

        assertNotNull("Imagebutton box exists: ", imageButton);




    }

   public void testPressPlus() {


        mapsActivity = this.getActivity();
        final ImageButton imageButton =
                (ImageButton) mapsActivity.findViewById(R.id.imageButton);



        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                imageButton.requestFocus();
                Toast.makeText(mapsActivity.getBaseContext(), "Add Event Button: Success", Toast.LENGTH_SHORT).show();


            }
        });

        getInstrumentation().waitForIdleSync();
        mapsActivity.runOnUiThread(new Runnable() {
            public void run() {

                imageButton.performClick();


            }
        });

       try {
           Thread.sleep(6000);

       }
       catch (InterruptedException e) {
           e.printStackTrace();
       }
    }
}
