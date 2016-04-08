package csulb.edu.pickup;

/**
 * Created by Sarah on 3/9/2016.
 */

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import android.os.Parcelable;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.provider.SyncStateContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateAccountActivity extends AppCompatActivity implements View.OnClickListener{
    Button btpic, btnup;
    private Uri fileUri;
    String picturePath;
    Uri selectedImage;
    Bitmap photo;
    String ba1;
    public static String URL = "Paste your URL here";
    private Button cancelButton;
    private Button createAccountButton;
    private Button uploadPhotoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.create_account);


        // setup spinners when page is created
        initSpinners();

        findViewsById();
        setHints();
        setUpCancelButton();
        setUpUploadPhotoButton();
        setUpCreateAccountButton();
    }





    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onRestart() {
        super.onRestart();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

    }



    private void initSpinners()
    {
        int idBdayYearSpinner = R.id.bday_year_spinner;
        int idBdayDaySpinner = R.id.bday_day_spinner;
        int idBdayMonthSpinner = R.id.bday_month_spinner;
        int idBdayMonthArray = R.array.bday_month_array;


        // attach values to month spinner
        initSpinner(idBdayMonthSpinner, idBdayMonthArray);



        // init date spinners
        initReverseSpinner(idBdayYearSpinner, 2016, 1917);
        initNumSpinner(idBdayDaySpinner, 1, 31);
    }

    private void initSpinner(int spinnerId, int arrayId)
    {
        // load values from resources to populate spinner
        Spinner spinner = (Spinner) findViewById(spinnerId);
        // Create an ArrayAdapter with provided resources and layout
        ArrayAdapter<CharSequence> spinnnerAdapter = ArrayAdapter.createFromResource(this,
                arrayId, android.R.layout.simple_spinner_item);
        // Specify the layout
        spinnnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(spinnnerAdapter);
    }


    private void initNumSpinner(int spinnerId, int begin, int end)
    {
        List<String> list=new ArrayList<String>();
        for(int i = begin; i < end; i++) {
            list.add(i + "");
        }
        final Spinner sp=(Spinner) findViewById(spinnerId);
        ArrayAdapter<String> adp = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, list);
        adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(adp);
    }
    private void initReverseSpinner(int spinnerId, int begin, int end)
    {
        List<String> list=new ArrayList<String>();
        for(int i = begin; i > end; i--) {
            list.add(i + "");
        }
        final Spinner sp=(Spinner) findViewById(spinnerId);
        ArrayAdapter<String> adp = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, list);
        adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(adp);
    }

    private void findViewsById() {

        cancelButton = (Button) findViewById(R.id.cancel_btn);
        cancelButton.requestFocus();

        createAccountButton = (Button) findViewById(R.id.create_account_btn);
        createAccountButton.requestFocus();

        uploadPhotoButton = (Button) findViewById(R.id.upload_photo_btn);
        uploadPhotoButton.requestFocus();


    }





    private void setUpCreateAccountButton() {
        createAccountButton.setOnClickListener(this);
    }

    private void setUpCancelButton() {
        cancelButton.setOnClickListener(this);
    }
    private void setUpUploadPhotoButton() {
        uploadPhotoButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {


        if (view == cancelButton) {
            Intent myIntent = new Intent(view.getContext(), LoginActivity.class);
            startActivityForResult(myIntent, 0);
        }
        else if(view == uploadPhotoButton){

            Intent intent = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, 0);

            /*URLConnection conn = new URLConnection();
            conn.sendUploadPhoto("YODA.jpg","myusername" );
*/
        }
        else if (view == createAccountButton) {

        /*
             Bitmap bm = BitmapFactory.decodeFile(picturePath);
             ByteArrayOutputStream bao = new ByteArrayOutputStream();
             bm.compress(Bitmap.CompressFormat.JPEG, 90, bao);
             byte[] ba = bao.toByteArray();
             //ba1 = Base64.encodeBytes(ba);
        */
            Map<String, String> formMap = formToMap();

            System.out.println(formMap);
            String firstName = formMap.get("firstName");
            String lastName = formMap.get("lastName");
            String email = formMap.get("email");
            String password = formMap.get("password");
            String passwordRetype = formMap.get("passwordRetype");
            String gender = formMap.get("gender");
            String bdayDay = formMap.get("bdayDay");
            String bdayMonth = formMap.get("bdayMonth");
            String bdayYear = formMap.get("bdayYear");
            String bday = bdayYear+"-"+bdayMonth+"-"+bdayDay;
            if(firstName.equals("")){
                createAlert("First Name Required", "Please Enter a First Name");
            }
            else if(lastName.equals("")){
                createAlert("Last Name Required", "Please Enter a Last Name" );
            }
            else if(email.equals("")){
                createAlert("Email Required", "Please Enter an Email");
            }
            else if(password.equals("")){
                createAlert("Password Required", "Please Enter a Password" );
            }
            else if(passwordRetype.equals("")){
                createAlert("Password Validation Required", "Please Enter Password Twice");
            }
            else if(gender.equals("")){
                createAlert("Gender Required", "Please select a gender" );
            }
            else if(password.equals(passwordRetype)) {
                URLConnection http = new URLConnection();
                try {
                    String userResult = http.sendCreateUser(email, password, firstName, lastName, bday, gender, "", "");
                    if(userResult.equals("false")){
                        createAlert("Duplicate Email", "User exists. Please try a different one.");

                    }
                    else{
                        User thisUser = new User(firstName, lastName, email ,password, bday, gender, "0");
                        Bundle b = new Bundle();
                        b.putParcelable("USER", thisUser);
                        Intent myIntent = new Intent(view.getContext(), MapsActivity.class);
                        myIntent.putExtras(b);
                        startActivityForResult(myIntent, 0);
                    }
                } catch(IOException e)
                {

                }
            } else {
                createAlert("Invalid Password Entry","Passwords do not match - please try again." );
            }
        }
    }


    public Map<String, String> formToMap()
    {
        Map<String, String> formMap = new HashMap<>();

        // get text from the edit text box
        EditText editTextBox1 = (EditText)findViewById(R.id.first_name);
        String firstNameStr = editTextBox1.getText().toString();
        formMap.put("firstName", firstNameStr);

        EditText editTextBox2 = (EditText)findViewById(R.id.last_name);
        String lastNameStr = editTextBox2.getText().toString();
        formMap.put("lastName", lastNameStr);

        EditText editTextBox3 = (EditText)findViewById(R.id.email);
        String emailStr = editTextBox3.getText().toString();
        formMap.put("email", emailStr);

        EditText editTextBox4 = (EditText)findViewById(R.id.userPassword);
        String passwordStr = editTextBox4.getText().toString();
        formMap.put("password", passwordStr);

        EditText editTextBox5 = (EditText)findViewById(R.id.retype_password);
        String passwordRetypeStr = editTextBox5.getText().toString();
        formMap.put("passwordRetype", passwordRetypeStr);

        Spinner bdayDaySpinner = (Spinner)findViewById(R.id.bday_day_spinner);
        String bdayDayStr = bdayDaySpinner.getSelectedItem().toString();
        formMap.put("bdayDay", bdayDayStr);

        Spinner bdayMonthSpinner = (Spinner)findViewById(R.id.bday_month_spinner);
        String bdayMonthStr = bdayMonthSpinner.getSelectedItem().toString();
        bdayMonthStr = stringMonthToNum(bdayMonthStr);
        formMap.put("bdayMonth", bdayMonthStr);


        Spinner bdayYearSpinner = (Spinner)findViewById(R.id.bday_year_spinner);
        String bdayYearStr = bdayYearSpinner.getSelectedItem().toString();
        formMap.put("bdayYear", bdayYearStr);

        boolean maleChecked = ((RadioButton) findViewById(R.id.radio_male)).isChecked();
        boolean femaleChecked = ((RadioButton) findViewById(R.id.radio_female)).isChecked();
        String genderStr = "";
        if(maleChecked)
            genderStr = "male";
        else if(femaleChecked)
            genderStr = "female";

        formMap.put("gender", genderStr);


        String text = String.format("first: %s \nlast: %s \nuser: %s " +
                        "\npass: %s \npassRe: %s \ngend: %s \nday: %s \nmon: %s" +
                        "\nyea: %s", firstNameStr, lastNameStr, emailStr,
                passwordStr, passwordRetypeStr, genderStr, bdayDayStr, bdayMonthStr,
                bdayYearStr);


        // Log to show that the vars are correct
        Log.i("SARAH", text);

        return formMap;
    }

    public String stringMonthToNum(String sMonth){
        String month = "00";
        switch(sMonth){
            case "Jan":
                month = "01";
                break;
            case "Feb":
                month = "02";
                break;
            case "Mar":
                month = "03";
                break;
            case "Apr":
                month = "04";
                break;
            case "May":
                month = "05";
                break;
            case "Jun":
                month = "06";
                break;
            case "Jul":
                month = "07";
                break;
            case "Aug":
                month = "08";
                break;
            case "Sept":
                month = "09";
                break;
            case "Oct":
                month = "10";
                break;
            case "Nov":
                month = "11";
                break;
            case "Dec":
                month = "12";
                break;
        }
        return month;
    }


    public void createAlert(String title, String message){
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

/*
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100 && resultCode == RESULT_OK) {

            selectedImage = data.getData();
            photo = (Bitmap) data.getExtras().get("data");

            // Cursor to get image uri to display

            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            picturePath = cursor.getString(columnIndex);
            cursor.close();

            Bitmap photo = (Bitmap) data.getExtras().get("data");
            ImageView imageView = (ImageView) findViewById(R.id.Imageprev);
            imageView.setImageBitmap(photo);
        }
    }*/
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
                ImageView imageView = (ImageView) findViewById(R.id.Imageprev);
                imageView.setImageBitmap(bitmap);
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
    public void setHints(){
        ((EditText) findViewById(R.id.first_name)).setHint("First Name");
        ((EditText) findViewById(R.id.last_name)).setHint("Last Name");
        ((EditText) findViewById(R.id.email)).setHint("Email");
        ((EditText) findViewById(R.id.userPassword)).setHint("Password");
        ((EditText) findViewById(R.id.retype_password)).setHint("Retype Password");


    }
}