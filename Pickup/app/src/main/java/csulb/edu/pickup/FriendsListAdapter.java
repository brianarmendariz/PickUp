package csulb.edu.pickup;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
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
    TextView txtAge;
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
        java.lang.String firstName = friendsList.get(position).getFirstName();
        txtFirst.setText((CharSequence)firstName);
        java.lang.String email = friendsList.get(position).getEmail();
        txtSecond.setText((CharSequence)email);


        txtAge = (TextView) convertView.findViewById(R.id.age_label);
        java.lang.String age = friendsList.get(position).getAge();
        txtAge.setText((CharSequence)age);

        addCreatorPicture(email, convertView);

        return convertView;
    }

    public void addCreatorPicture(java.lang.String email, View view)
    {
        java.lang.String creatorPicture = getUserPicturePath(email);

        //ImageView Setup
        ImageView creatorPicImageView = (ImageView)view.findViewById(R.id.friend_profile_pic);

        // use default if empty
        if(creatorPicture.equals("") || creatorPicture == null)
        {
            //setting image resource
            Bitmap bm = BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.com_facebook_profile_picture_blank_portrait);
            Bitmap resized = Bitmap.createScaledBitmap(bm, 150, 150, true);
            Bitmap conv_bm = BitmapHelper.getRoundedRectBitmap(resized, 150);
            creatorPicImageView.setImageBitmap(conv_bm);
        }
        else // use User's profile picture
        {
            byte[] byteArray = Base64.decode(creatorPicture, Base64.DEFAULT);
            Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

            // set the picture
            creatorPicImageView.setImageBitmap(bmp);

//            DisplayMetrics dm = new DisplayMetrics();
//            getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
//
//            creatorPicImageView.setMinimumHeight(dm.heightPixels);
//            creatorPicImageView.setMinimumWidth(dm.widthPixels);
        }
    }

    private java.lang.String getUserPicturePath(java.lang.String creatorEmail)
    {
        URLConnection http = new URLConnection();

        java.lang.String creatorPicture = "";
        try
        {
            creatorPicture = http.sendGetProfilePicture(creatorEmail);
        } catch(IOException e)
        {

        }
        return creatorPicture;
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
