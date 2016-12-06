package csulb.edu.pickup;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by Sarah on 5/9/2016.
 */
public class FriendsListAdapter<String> extends BaseAdapter {

    public static final java.lang.String FIRST_COLUMN = "First";
    public static final java.lang.String SECOND_COLUMN = "Second";

    public ArrayList<LinkedHashMap<java.lang.String, java.lang.String>> friendList;
    public ArrayList<User> friendsList;
    Activity activity;
    TextView txtFirst;
    TextView txtSecond;
    private ImageView profileImage;
    View rootView;
    int layout;
    private final Context context;

    public FriendsListAdapter(Activity activity, int layout, ArrayList<User> friendList, Context context)
    {
        super();
        this.activity=activity;
        this.layout=R.layout.friends_list;
        this.friendsList = friendList;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater=activity.getLayoutInflater();

        convertView=inflater.inflate(R.layout.friends_list, null);

        txtFirst=(TextView) convertView.findViewById(R.id.name_label);
        txtSecond=(TextView) convertView.findViewById(R.id.email_label);
        //HashMap<java.lang.String, java.lang.String> map=friendList.get(position);
        txtFirst.setText((CharSequence)friendsList.get(position).getFirstName());
        txtSecond.setText((CharSequence)friendsList.get(position).getEmail());

<<<<<<< HEAD
        /**
         * Erwin added this for profile picture.
         */
//        profileImage = (ImageView) rootView.findViewById(R.id.profileImageView);
//        Bitmap bm = BitmapFactory.decodeResource(getActivity().getResource(),
//                R.drawable.com_facebook_profile_picture_blank_portrait);
//        Bitmap resized = Bitmap.createScaledBitmap(bm, 150, 150, true);
//        Bitmap conv_bm = getRoundedRectBitmap(resized, 150);
//        profileImage.setImageBitmap(conv_bm);

        if (position % 2 == 0) {
            //convertView.setBackgroundColor(ContextCompat.getColor(context, R.color.orange));
        } else {
            // view.setBackgroundColor(ContextCompat.getColor(this.getContext(), R.color.dark_grey));
        }

=======
>>>>>>> origin/master
        return convertView;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return friendsList.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return friendsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }
}
