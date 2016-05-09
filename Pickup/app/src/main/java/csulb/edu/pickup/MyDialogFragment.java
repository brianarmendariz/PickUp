package csulb.edu.pickup;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Brain on 5/9/2016.
 */
public class MyDialogFragment extends DialogFragment {

    User thisUser;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        Bundle data = this.getArguments();
        thisUser = (User) data.getParcelable("USER");
        String title = (String) data.getString("TITLE");
        String username = (String) data.getString("USERNAME");
        String eventName = (String) data.getString("EVENTNAME");
        String text = "";

        if(username == null)
        {
            text = eventName + " is not an Event.";
        }
        else if(eventName == null)
        {
            text = username + " is not a Member.";
        }

        View rootView = inflater.inflate(R.layout.my_dialog, container, false);
        getDialog().setTitle(title);

        LinearLayout linearLayout = (LinearLayout) rootView.findViewById(R.id.MyDialogLinearLayout);

        // Add textview
        TextView textView = new TextView(getActivity());
        textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        textView.setTextColor(getResources().getColor(R.color.orange));
        textView.setText(text);
        textView.setPadding(20, 20, 20, 20);// in pixels (left, top, right, bottom)
        linearLayout.addView(textView);

        // add a button
        Button dismiss = new Button(getActivity());
        dismiss.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        dismiss.setText("Dismiss");
        dismiss.setPadding(20, 20, 20, 20);// in pixels (left, top, right, bottom)
        linearLayout.addView(dismiss);
        dismiss.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        return rootView;
    }
}
