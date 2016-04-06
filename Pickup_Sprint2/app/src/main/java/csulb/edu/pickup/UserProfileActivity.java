package csulb.edu.pickup;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;


import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.facebook.login.LoginManager;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;


/**
 * Created by Nessa on 3/19/16.
 */
public class UserProfileActivity extends Activity {

    ImageView profileImage;

    User thisUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle data = getIntent().getExtras();
        thisUser = (User) data.getParcelable("USER");

        thisUser.getEmail();
        super.onCreate(savedInstanceState);

        setContentView(R.layout.view_profile);
        profileImage = (ImageView)findViewById(R.id.imageView);
        profileImage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 0);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        if (id == R.id.user_logout) {
            LoginManager.getInstance().logOut();

            Intent loginActivityIntent = new Intent(getBaseContext(), LoginActivity.class);
            loginActivityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(loginActivityIntent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) throws RuntimeException {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK){
            Uri targetUri = data.getData();
            Bitmap bitmap;


            try {

                //bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(targetUri));
                bitmap = getBitmapFromReturnedImage(targetUri,200,200);
                profileImage.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            catch(IOException e) {
                e.printStackTrace();
            }
            catch(NullPointerException e) {
                e.printStackTrace();
            }
        }
    }

    public Bitmap getBitmapFromReturnedImage(Uri selectedImage, int reqWidth, int reqHeight) throws IOException {

        InputStream inputStream = getContentResolver().openInputStream(selectedImage);

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(inputStream, null, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // close the input stream
        inputStream.close();

        // reopen the input stream
        inputStream = getContentResolver().openInputStream(selectedImage);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeStream(inputStream, null, options);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

}
