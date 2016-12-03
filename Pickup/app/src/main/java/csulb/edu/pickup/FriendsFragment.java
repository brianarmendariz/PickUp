package csulb.edu.pickup;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Sarah on 5/9/2016.
 */
public class FriendsFragment extends Fragment{


    ArrayList<User> friendList;
    User thisUser;
    View rootView;
    private ImageView profileImage;
    private static int RESULT_LOAD_IMAGE = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle data = getActivity().getIntent().getExtras();
        thisUser = (User) data.getParcelable("USER");
        //thisUser = new User("sarah","Shibley","sarahshib@outlook.com", "","","","");

        super.onCreate(savedInstanceState);
        getActivity().setTitle("Friends");


        /**
         * Erwin adding this.
         */
        thisUser = (User) data.getParcelable("USER");

        rootView = inflater.inflate(R.layout.view_profile, container, false);

        System.out.println("UserProfileFragment: " + thisUser.getEmail());
        profileImage = (ImageView) rootView.findViewById(R.id.profileImageView);
        Bitmap bm = BitmapFactory.decodeResource(getResources(),
                R.drawable.com_facebook_profile_picture_blank_portrait);
        Bitmap resized = Bitmap.createScaledBitmap(bm, 40, 40, true);
        Bitmap conv_bm = getRoundedRectBitmap(resized, 40);
        profileImage.setImageBitmap(conv_bm);
        profileImage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });



        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        rootView = inflater.inflate(R.layout.friends_container, container, false);
        ListView listView= (ListView)rootView.findViewById(R.id.listView);

        URLConnection http = new URLConnection();
        // Intent intent = getActivity().getIntent();
        //Bundle args = getArguments();

        try {
            friendList = http.sendGetFriendsList(thisUser.getEmail());

            if(friendList != null)
            {
                BaseAdapter adapter = new FriendsListAdapter<String>(getActivity(), R.layout.friends_list, friendList, getActivity());

                listView = (ListView) rootView.findViewById(R.id.friend_list);
                listView.setAdapter(adapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String email = friendList.get(position).getEmail();

                        Bundle b = new Bundle();
                        //add current user
                        b.putParcelable("USER", thisUser);
                        User user = viewUser(email);
                        if (user != null && !thisUser.getEmail().equals(user.getEmail())) {
                            System.out.println("putting the parceable");
                            b.putParcelable("VIEWUSER", user);
                        } else {
                            System.out.println("not putting the parceable");
                        }
                        Fragment fragment = new UserProfileFragment();
                        fragment.setArguments(b);
                        FragmentManager frgManager = getFragmentManager();
                        frgManager.beginTransaction().replace(R.id.content_frame, fragment).addToBackStack("FriendsFragment")
                                .commit();

                        Intent thisIntent = new Intent(getActivity().getBaseContext(), MainActivity.class);
                        thisIntent.putExtras(b);
                    }

                });

            }
            else
            {
                showAlert("User Has No Friends");

                Bundle b = new Bundle();
                //add current user
                b.putParcelable("USER", thisUser);

                Fragment fragment = new MapsFragment();
                fragment.setArguments(b);
                FragmentManager frgManager = getFragmentManager();
                frgManager.beginTransaction().replace(R.id.content_frame, fragment).addToBackStack("FriendsFragment")
                        .commit();

                Intent thisIntent = new Intent(getActivity().getBaseContext(), MainActivity.class);
                thisIntent.putExtras(b);
            }

        } catch (IOException e) {
            Log.e("ERROR", "FriendList ERROR");
        }



        return rootView;
    }

    public void showAlert(String message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(message);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //TODO
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private User viewUser(String username)
    {
        User user = null;
        URLConnection http = new URLConnection();
        try
        {
            user = http.sendGetUser(username);
            System.out.println("user " + user);
        } catch(IOException e)
        {
        }
        return user;
    }

    /**
     * Erwin adding this.
     */
    public static Bitmap getRoundedRectBitmap(Bitmap bitmap, int pixels) {
        Bitmap result = null;
        try {
            result = Bitmap.createBitmap(40, 40, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(result);

            int color = 0xff424242;
            Paint paint = new Paint();
            Rect rect = new Rect(0, 0, 60, 60);

            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(color);
            canvas.drawCircle(40, 40, 40, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(bitmap, rect, rect, paint);

        } catch (NullPointerException e) {
        } catch (OutOfMemoryError o) {
        }
        return result;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) throws RuntimeException
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == Activity.RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            ImageView imageView = (ImageView) rootView.findViewById(R.id.profileImageView);

            Bitmap bmp = null;
            try {
                bmp = getBitmapFromUri(selectedImage);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            imageView.setImageBitmap(bmp);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] bArray = baos.toByteArray();
            System.out.println("bArray " + bArray);

            String encodedImage = Base64.encodeToString(bArray, Base64.DEFAULT);
            System.out.println("encodedImage " + encodedImage);
        }
    }

    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor =
                getActivity().getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }

}
