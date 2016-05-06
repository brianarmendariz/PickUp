package csulb.edu.pickup;

import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.SmallTest;


public class MapActivityTest extends ActivityInstrumentationTestCase2<MapsActivity> {


    public MapActivityTest() {
        super(MapsActivity.class);
    }


    @SmallTest
    public void testActivityExists() {

       // MapsActivity activity = getActivity();

        //Verifies that MapsActivity is not null
        //assertNotNull(activity);
    }
}