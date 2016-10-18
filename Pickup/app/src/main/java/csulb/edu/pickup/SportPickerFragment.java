package csulb.edu.pickup;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;


/**
 * Created by Brian on 10/14/2016.
 */
public class SportPickerFragment extends DialogFragment implements View.OnClickListener {

    User thisUser;
    View rootView;

    int image_buttons[] = {R.id.sp_button_badminton, R.id.sp_button_baseball, R.id.sp_button_basketball,
            R.id.sp_button_football, R.id.sp_button_handball, R.id.sp_button_icehockey, R.id.sp_button_racquetball,
            R.id.sp_button_rollerhockey, R.id.sp_button_running, R.id.sp_button_soccer, R.id.sp_button_softball,
            R.id.sp_button_tennis, R.id.sp_button_volleyball, R.id.sp_button_weightlifting, R.id.sp_button_yoga };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        Bundle data = getActivity().getIntent().getExtras();
        thisUser = (User) data.getParcelable("USER");
        //thisUser = new User("ln", "em", "pw", "bday", "gend", "useRate", "a");

        super.onCreate(savedInstanceState);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        rootView = inflater.inflate(R.layout.sport_picker, container, false);

        // sets the clear background
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // get all references to the ImageButtons in sport_picker.xml
        findViewsById();

        return rootView;
    }

    /**
     * Get reference to the image button and set the onClickListener to this fragment
     */
    private void findViewsById()
    {
        for(int i = 0; i < image_buttons.length; i++)
        {
            ImageButton button = (ImageButton)rootView.findViewById(image_buttons[i]);
            button.setOnClickListener(this);
        }
    }


    @Override
    public void onClick(View view)
    {
        for(int i = 0; i < image_buttons.length; i++)
        {
            if(view == (ImageButton)rootView.findViewById(image_buttons[i]))
            {
                // GOES TO CREATE EVENT PAGE
                //v.startAnimation(animAlpha);
                Bundle args = new Bundle();

                // sending over R.id.[sport_image_button] so you know which sport was picked
                // in the create_event we would use an if statement
                // ex) if(sport == R.id.sp_button_baseball) load baseball page
                args.putInt("SPORT", i);

                Fragment fragment = new CreateEventFragment();
                fragment.setArguments(args);
                FragmentManager frgManager = getFragmentManager();
                frgManager.beginTransaction().replace(R.id.content_frame, fragment).addToBackStack("Sport Picker")
                        .commit();
                dismiss();
            }
        }
    }
}
