package csulb.edu.pickup;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Nessa on 3/22/16.
 */
public class LoginActivityTest extends ActivityInstrumentationTestCase2 <LoginActivity>{

    private LoginActivity loginActivity;


    private EditText username;
    private EditText password;

    @SuppressWarnings("deprecation")
    public LoginActivityTest()
    {
        super("com.main.account.Login", LoginActivity.class);
    }


    @Override
    protected void setUp() throws Exception {
        super.setUp();
        loginActivity = this.getActivity();
        username = (EditText) loginActivity.findViewById(R.id.username);
        password = (EditText) loginActivity.findViewById(R.id.password);

        assertNotNull("Username box exists: ",username);
        assertNotNull("Password EditText exists: ", password);

        assertEquals("", username.getText().toString());
        assertEquals("", password.getText().toString());
    }


    public void testLogin() {
        loginActivity = getActivity();

        // Type name in text input
        // ----------------------

        final EditText usernameEditText =
                (EditText) loginActivity.findViewById(R.id.username);
        final EditText passwordEditText =
                (EditText) loginActivity.findViewById(R.id.password);
        // Send string input value
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                usernameEditText.requestFocus();

            }
        });

        getInstrumentation().waitForIdleSync();
        getInstrumentation().sendStringSync("sarahshib@hotmail.com");
        getInstrumentation().waitForIdleSync();
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                passwordEditText.requestFocus();
                Toast.makeText(loginActivity.getBaseContext(), "DONE", Toast.LENGTH_SHORT).show();
            }
        });

        getInstrumentation().waitForIdleSync();
        getInstrumentation().sendStringSync("abcd");

    }


    public void tearDown() throws Exception {
        assertEquals("", username.getText().toString());
        assertEquals("", password.getText().toString());
    }

}
