package csulb.edu.pickup;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.ImageButton;

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
        mapsActivity = this.getActivity();
        imageButton = (ImageButton) mapsActivity.findViewById(R.id.imageButton);

        assertNotNull("Imagebutton box exists: ", imageButton);




    }

   /* public void testPressPlus() {
        mapsActivity = this.getActivity();
        final ImageButton imageButton =
                (ImageButton) mapsActivity.findViewById(R.id.imageButton);


        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                imageButton.requestFocus();


            }
        });

        getInstrumentation().waitForIdleSync();
        mapsActivity.runOnUiThread(new Runnable() {
            public void run() {

                imageButton.performClick();


            }
        });
    }*/
}
