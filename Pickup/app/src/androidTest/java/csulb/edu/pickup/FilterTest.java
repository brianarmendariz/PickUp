package csulb.edu.pickup;

import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Sarah on 4/14/2016.
 */
public class FilterTest  extends ActivityInstrumentationTestCase2<FilterEvents>{


    private FilterEvents filterEvents;


    private EditText location;
    private EditText creator;
    private Button filterButton;

    @SuppressWarnings("deprecation")
    public FilterTest()
    {
        super("com.main.account.FilterEvents", FilterEvents.class);
    }


    @Override
    protected void setUp() throws Exception {
        super.setUp();
        filterEvents = this.getActivity();
        creator = (EditText) filterEvents.findViewById(R.id.author_name);
        location = (EditText) filterEvents.findViewById(R.id.event_location);
        filterButton = (Button) filterEvents.findViewById(R.id.filter_btn);
        assertNotNull("Creator box exists: ",creator);
        assertNotNull("Location box exists: ", location);

        assertEquals("", creator.getText().toString());
        assertEquals("", location.getText().toString());
    }

    public void testFilter() throws Throwable{
        filterEvents = getActivity();

        // Type name in text input
        // ----------------------

        final EditText creatorEditText =
                (EditText) filterEvents.findViewById(R.id.author_name);
        final EditText locationEditText =
                (EditText) filterEvents.findViewById(R.id.event_location);
        // Send string input value
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                creatorEditText.requestFocus();

            }
        });

        getInstrumentation().waitForIdleSync();
        getInstrumentation().sendStringSync("sarahshib@outlook.com");
        getInstrumentation().waitForIdleSync();
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                locationEditText.requestFocus();

            }
        });

        getInstrumentation().waitForIdleSync();
        getInstrumentation().sendStringSync("Long Beach, CA");
        runTestOnUiThread(new Runnable() {
            @Override
            public void run() {
                filterButton.performClick();
                Toast.makeText(filterEvents.getBaseContext(), "Filter Fill In: Success", Toast.LENGTH_SHORT).show();
            }
        });
        try {
            Thread.sleep(6000);

        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }


    }


    public void tearDown() throws Exception {

        //assertEquals("sarahshib@outlook.com", username.getText().toString());
        //assertEquals("a", password.getText().toString());
    }

}
