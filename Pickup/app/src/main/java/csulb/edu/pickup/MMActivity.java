package csulb.edu.pickup;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.EditText;

import java.io.IOException;

/**
 * Created by Nessa on 4/11/16.
 */
public class MMActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        setContentView(R.layout.matchmaking);

        URLConnection rsvpList = new URLConnection();
        String s [][];

        try {
            if (rsvpList.sendGetRSVPList(1) == null) {
                Log.d("Nessa", "THIS IS NULL");
            }
            else {
                s = rsvpList.sendGetRSVPList(1);
                

            }
        }
        catch (IOException e){
            Log.e("ERROR", "RVSP ERROR");
        }


    }

}
