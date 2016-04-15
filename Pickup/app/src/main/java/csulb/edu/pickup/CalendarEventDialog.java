package csulb.edu.pickup;

import android.app.DialogFragment;
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
 * Created by Brain on 4/11/2016.
 */
public class CalendarEventDialog extends DialogFragment {

    User thisUser;
    private static final int VIEW_MAP_EVENT = 2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle data = this.getArguments();
        thisUser = (User) data.getParcelable("USER");

        View rootView = inflater.inflate(R.layout.calendar_dialog, container, false);
        getDialog().setTitle("Event Picker");

        final ArrayList<Event> eventList = getArguments().getParcelableArrayList("EVENTS");

        LinearLayout linearLayout = (LinearLayout) rootView.findViewById(R.id.CalendarLinearLayout);

        for(int i = 0; i < eventList.size(); i++) {
            final int x = i;
            // Add textview
            TextView textView = new TextView(getActivity());
            textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            textView.setTextColor(getResources().getColor(R.color.orange));
            textView.setText(eventList.get(i).getName());
            textView.setPadding(20, 20, 20, 20);// in pixels (left, top, right, bottom)
            linearLayout.addView(textView);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle b = new Bundle();
                    b.putParcelable("USER", thisUser);
                    Intent intent = new Intent(getActivity(), ViewEventActivity.class);
                    intent.putExtra("EventID", eventList.get(x).getEventID());
                    intent.putExtras(b);
                    startActivityForResult(intent, VIEW_MAP_EVENT);
                }
            });
        }

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