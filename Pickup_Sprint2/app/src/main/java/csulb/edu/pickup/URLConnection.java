package csulb.edu.pickup;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;



import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class URLConnection {

    private final String USER_AGENT = "Mozilla/5.0";


    /**
     * Sends event data to the server.
     *
     * @param username
     * @param password
     * @param firstName
     * @param lastName
     * @param birthday
     * @param gender
     * @param userRating
     * @param picturePath
     * @return String
     * @throws IOException
     */
    //  @SuppressWarnings("unchecked")
    public String sendCreateUser(String username, String password, String firstName,
                                 String lastName, String birthday, String gender,
                                 String userRating, String picturePath)
            throws IOException  {

			/*url of route being requested*/
        String url = "http://www.csulbpickup.com/createUser.php";


        java.net.URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        //add reuqest header
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
//        con.setRequestProperty("Content-Type", "application/json");


        String urlParameters = "Username="+username+"&Password="+password+"&FirstName="+firstName+
                "&LastName="+lastName+"&Birthday="+birthday+"&Gender="+gender+
                "&UserRating="+userRating+"&PicturePath="+picturePath;



        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);

        wr.flush();
        wr.close();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println("Response: CreateUser : " + response.toString());
        return response.toString();
    }


    /**
     *
     * @param username
     * @param password
     * @return
     * @throws IOException
     */
    public String sendLogin(String username, String password) throws IOException  {

			/*url of route being requested*/
        String url = "http://www.csulbpickup.com/login.php";


        java.net.URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        //add reuqest header
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        String urlParameters = "Username="+username+"&Password="+password;

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println("Response: Login : " + response.toString());
        return response.toString();
    }
    public String sendResetEmail(String username) throws IOException  {

			/*url of route being requested*/
        String url = "http://www.csulbpickup.com/sendResetEmail.php";


        java.net.URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        //add reuqest header
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        String urlParameters = "Username="+username;

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        Log.d("SARAH", "Response: Email : " + response.toString());
        return response.toString();
    }

    public String sendPasswordReset(String username, String oldPassword, String newPassword) throws IOException  {

			/*url of route being requested*/
        String url = "http://www.csulbpickup.com/changePassword.php";
        Log.d("SARAH","Inside SendPasswordReset");

        java.net.URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        //add reuqest header
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        String urlParameters = "Username="+username+"&Password="+oldPassword+"&NewPassword="+newPassword;

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();
        Log.d("SARAH","just wrote");
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        Log.d("SARAH","got a response");
        in.close();

        //print result
        Log.d("SARAH", "Response: Password : " + response.toString());
        return response.toString();
    }

    /**
     *
     * @param username
     * @param firstName
     * @param lastName
     * @param birthday
     * @param gender
     * @param userRating
     * @param picturePath
     * @return
     * @throws IOException
     */
    public String sendEditUser(String username,  String firstName,
                               String lastName, String birthday, String gender,
                               String userRating, String picturePath)
            throws IOException  {

			/*url of route being requested*/
        String url = "http://www.csulbpickup.com/editUser.php";


        java.net.URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        //add reuqest header
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        String urlParameters = "Username="+username+"&FirstName="+firstName+"&LastName="+lastName+"&Birthday="+birthday+
                "&Gender="+gender+"&UserRating="+userRating+"&PicturePath="+picturePath;

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println("Response: Edit : " + response.toString());
        return response.toString();
    }


    /**
     *
     * @param username
     * @return
     * @throws IOException
     */
    public String sendChangePassword(String username, String password, String newPassword)
            throws IOException  {
            Log.d("SARAH", "Inside changePassword");
			/*url of route being requested*/
        String url = "http://www.csulbpickup.com/changePassword.php";


        java.net.URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        //add reuqest header
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        String urlParameters = "Username="+username+"&Password="+password+"&NewPassword="+newPassword;

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();
        Log.d("SARAH", "just wrote");

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();


        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        Log.d("SARAH", "got response");

        in.close();

        //print result
        System.out.println("Response: Change Password : " + response.toString());
        return response.toString();
    }

    /**
     *
     * @param username
     * @return
     * @throws IOException
     */
    public User sendGetUser(String username)
            throws IOException  {

			/*url of route being requested*/
        String url = "http://www.csulbpickup.com/getUser.php";


        java.net.URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        //add reuqest header
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        String urlParameters = "Username="+username;

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println("Response: Get : " + response.toString());
        return convertUser(response.toString());
    }


    /**
     * Deletes an event  from the server database
     * @param username
     * @return "true" if deleted, "false" if not
     * @throws IOException
     */
    public String sendDeleteUser(String username) throws IOException  {

			/*url of route being requested*/
        String url = "http://www.csulbpickup.com/deleteUser.php";


        java.net.URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        //add reuqest header
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        String urlParameters = "Username=" + username;


        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println("Response: Delete : " + response.toString());
        return response.toString();
    }
    /**
     * Sends event data to the server.
     *
     * @param authorName
     * @param authorEmail
     * @param eventName
     * @param sport
     * @param location
     * @param latitude
     * @param longitude
     * @param eventDateTime
     * @param ageMax
     * @param ageMin
     * @param minUserRating
     * @param playerAmount
     * @param isPrivate
     * @param gender
     * @return "true" if inserted to database, "false" if not.
     * @throws IOException
     */
    public String sendCreateEvent( String authorName,String authorEmail, String eventName, String sport,
                                   String location, String latitude, String longitude, String eventDateTime, String ageMax, String ageMin,
                                   String minUserRating, String playerAmount, String isPrivate, String gender) throws IOException  {

			/*url of route being requested*/
        String url = "http://www.csulbpickup.com/createEvent.php";


        java.net.URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        //add reuqest header
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        String urlParameters = "Author="+authorName+"&Email="+authorEmail+"&EventName="+eventName+"&Sport="+sport+"&Location="+location+"&Latitude="+latitude+
                "&Longitude="+longitude+"&EventDateTime="+eventDateTime+"&AgeMax="+ageMax+"&AgeMin="+ageMin+
                "&PlayerAmount="+playerAmount+"&MinUserRating="+minUserRating+"&IsPrivate="+isPrivate+"&Gender="+gender;


        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println("Response:"+response.toString());
        return response.toString();
    }

    /**
     * Deletes an event  from the server database
     * @param eventID
     * @return "true" if deleted, "false" if not
     * @throws IOException
     */
    public String sendDeleteEvent( int eventID) throws IOException  {

			/*url of route being requested*/
        String url = "http://www.csulbpickup.com/deleteEvent.php";


        java.net.URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        //add reuqest header
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        String urlParameters = "EventID="+eventID;


        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println("Response:"+response.toString());
        return response.toString();
    }
    /**
     * Edits an event by changing values to the values passed in. Any empty values should be set to "".
     * @param eventID
     * @param eventName
     * @param sport
     * @param location
     * @param latitude
     * @param longitude
     * @param eventDateTime
     * @param ageMax
     * @param ageMin
     * @param minUserRating
     * @param playerAmount
     * @param isPrivate
     * @param gender
     * @return "true" if successful, "false" if not.
     * @throws IOException
     */
    public String sendEditEvent( int eventID, String eventName, String sport,
                                 String location, String latitude, String longitude, String eventDateTime, String ageMax, String ageMin,
                                 String minUserRating, String playerAmount, String isPrivate, String gender) throws IOException  {

			/*url of route being requested*/
        String url = "http://www.csulbpickup.com/editEvent.php";


        java.net.URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        //add reuqest header
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        String urlParameters = "EventID="+eventID+"&EventName="+eventName+"&Sport="+sport+"&Location="+location+"&Latitude="+latitude+
                "&Longitude="+longitude+"&EventDateTime="+eventDateTime+"&AgeMax="+ageMax+"&AgeMin="+ageMin+
                "&PlayerAmount="+playerAmount+"&MinUserRating="+minUserRating+"&IsPrivate="+isPrivate+"&Gender="+gender;;


        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + urlParameters);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println("Response:"+response.toString());
        return response.toString();
    }

    /**
     * gets a list of all events in database on server.
     * @return ArrayList<Event> - an event object for each event in server
     * @throws IOException
     */
    public ArrayList<Event> sendGetEvents() throws IOException  {

        System.out.println("GetEvents");

			/*url of route being requested*/
        String url = "http://www.csulbpickup.com/getEvents.php";



        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        //add reuqest header
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        String urlParameters = "";

        // Send post request
        con.setDoOutput(true);
			/*DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(urlParameters);
			wr.flush();
			wr.close();
			*/
			/*
			int responseCode = con.getResponseCode();
			System.out.println("\nSending 'POST' request to URL : " + url);
			System.out.println("Post parameters : " + urlParameters);
			System.out.println("Response Code : " + responseCode);
			*/
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        String stringResponse = response.toString();
        System.out.println("Response: " + response);
        ArrayList<Event> list = convert(stringResponse);
        for(Event event: list){
            System.out.println("EventID:"+event.getEventID()+", Name:"+event.getName()+", Longitude:"+event.getLongitude()+", Latitude:"+event.getLatitude());
        }

        return list;
    }

    /**
     * Gets a single event from server for the given EventID
     * @param eventID
     * @return Event - an Event object for the retrieved Event on server.
     * @throws IOException
     */
    public Event sendGetEvent(int eventID) throws IOException  {

        System.out.println("GetEvent");

			/*url of route being requested*/
        String url = "http://www.csulbpickup.com/getEvent.php";



        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        //add reuqest header
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        String urlParameters = "EventID="+eventID;

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + urlParameters);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        String stringResponse = response.toString();
        System.out.println("Response: "+response);
        ArrayList<Event> list = convert(stringResponse);
        Event returnedEvent = list.get(0);
        return returnedEvent;
    }


    /**
     * function for converting
     * @param dateTime
     * @return
     */
    public String serverToClientDate(String dateTime){
        String year = dateTime.substring(0, 4);
        String month = dateTime.substring(5,7);
        String day = dateTime.substring(8,10);
        return day+"-"+month+"-"+year;
    }
    /**
     * function for converting Server datetime format to client time
     * @param dateTime
     * @return String Time
     */
    public String serverToClientTime(String dateTime){
        String hour = dateTime.substring(11, 13);
        String minute = dateTime.substring(14, 16);
        int hourInt = Integer.parseInt(hour);
        if(hourInt>12){
            return hourInt-12+":"+minute+" PM";
        }
        return hourInt+":"+minute+" AM";
    }
    public ArrayList<Event> convert(String str) {

	    	/*Divide string up into lines */
        String[] lines=str.split("#");

        //System.out.println(Arrays.toString(lines));
        ArrayList<Event> list = new ArrayList<Event>();

	        /*for each line parse key-value pairs */
        for(String line : lines){
            if(!line.isEmpty()){
                Map<String, String> map = new HashMap<>();
                for(String pair: line.split(",")){
                    String[] tokens = pair.split("::");
                    for (int i=0; i<tokens.length-1; ){
                        map.put(tokens[i++], tokens[i++]);
                    }
                }
		    		/*if map has valid data */
                if(map.containsKey("Longitude")) {

		    			/*Below line used for testing */
                    //System.out.println("map:"+map);

                    Event newEvent = new Event(map.get("EventName"),"",
                            Double.parseDouble(map.get("Longitude")),
                            Double.parseDouble(map.get("Latitude")),
                            map.get("Location"),
                            map.get("AgeMax"),
                            map.get("AgeMin"),
                            map.get("AuthorName"),
                            map.get("Email"),
                            map.get("Sport"),
                            map.get("Gender"),
                            map.get("PlayerNumber"),
                            map.get("MinUserRating"),
                            map.get("DateTimeCreated"),
                            serverToClientDate(map.get("EventDateTime")),
                            serverToClientTime(map.get("EventDateTime")),
                            map.get("IsPrivate"),
                            map.get("EventID"));
                    list.add(newEvent);
                }
            }
        }

        return list;
    }
    public User convertUser(String str) {

                Map<String, String> map = new HashMap<>();
                for(String pair: str.split(",")){
                    String[] tokens = pair.split("::");
                    for (int i=0; i<tokens.length-1; ){
                        map.put(tokens[i++], tokens[i++]);
                    }
                }

		    			/*Below line used for testing */
                    //System.out.println("map:"+map);

                    User thisUser = new User(
                            map.get("FirstName"),
                            map.get("LastName"),
                            map.get("Username"),
                            "",
                            map.get("Birthday"),
                            map.get("Gender"),
                            map.get("UserRating")
                           );
                    return thisUser;

    }
public String sendUploadPhoto(String fileName, String username) {
    try{

    HttpURLConnection httpUrlConnection = (HttpURLConnection) new URL("http://www.csulbpickup.com/uploadPhoto.php").openConnection();
    httpUrlConnection.setDoOutput(true);
    httpUrlConnection.setRequestMethod("POST");
    OutputStream os = httpUrlConnection.getOutputStream();
    Thread.sleep(1000);
    BufferedInputStream fis = new BufferedInputStream(new FileInputStream("YODA.jpg"));
       long totalByte =  new File("User.java").length();
       int byteTransferred = 0;

    for (int i = 0; i < totalByte; i++) {
        os.write(fis.read());
        byteTransferred = i + 1;
    }

    os.close();
    BufferedReader in = new BufferedReader(
            new InputStreamReader(
                    httpUrlConnection.getInputStream()));

    String s = null;
    while ((s = in.readLine()) != null) {
        System.out.println(s);
    }
    in.close();
    fis.close();
    int responseCode = httpUrlConnection.getResponseCode();


    BufferedReader bufIn = new BufferedReader(
            new InputStreamReader(httpUrlConnection.getInputStream()));
    String inputLine;
    StringBuffer response = new StringBuffer();

    while ((inputLine = bufIn.readLine()) != null) {
        response.append(inputLine);
    }
    bufIn.close();
        //print result
        String stringResponse = response.toString();
        return stringResponse;

    } catch (MalformedURLException ex) {

        ex.printStackTrace();

        Log.d("SARAH", "error: " + ex.getMessage(), ex);
    } catch (final Exception e) {
        e.printStackTrace();

        Log.d("SARAH",
                "Exception : " + e.getMessage(), e);
    }


return "false";
}
    public String sendCreateUser(String sourceFileUri, String username, String password, String firstName,
                                 String lastName, String birthday, String gender,
                                 String userRating, String picturePath)throws IOException {
        String fileName = sourceFileUri;

        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        File sourceFile = new File(sourceFileUri);

        if (!sourceFile.isFile()) {
            return "false";
        } else {
            try {
                FileInputStream fileInputStream = new FileInputStream(sourceFile);
                String urlString = "http://www.csulbpickup.com/getEvent.php";
                URL url = new URL(urlString);
                conn = (HttpURLConnection) url.openConnection();
                conn.setDoInput(true); // Allow Inputs
                conn.setDoOutput(true); // Allow Outputs
                conn.setUseCaches(false); // Don't use a Cached Copy
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Connection", "Keep-Alive");
                //  conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                conn.setRequestProperty("uploaded_file", fileName);


                dos = new DataOutputStream(conn.getOutputStream());


                dos.writeBytes(twoHyphens + boundary + lineEnd);


                dos.writeBytes("Content-Disposition: form-data; name=\"Username\"" + lineEnd);
                dos.writeBytes(lineEnd);
                dos.writeBytes(username);
                dos.writeBytes(lineEnd);


                dos.writeBytes("Content-Disposition: form-data; name=\"FirstName\"" + lineEnd);
                dos.writeBytes(lineEnd);
                dos.writeBytes(firstName);
                dos.writeBytes(lineEnd);

                dos.writeBytes("Content-Disposition: form-data; name=\"LastName\"" + lineEnd);
                dos.writeBytes(lineEnd);
                dos.writeBytes(lastName);
                dos.writeBytes(lineEnd);

                dos.writeBytes("Content-Disposition: form-data; name=\"Password\"" + lineEnd);
                dos.writeBytes(lineEnd);
                dos.writeBytes(password);
                dos.writeBytes(lineEnd);

                dos.writeBytes("Content-Disposition: form-data; name=\"Gender\"" + lineEnd);
                dos.writeBytes(lineEnd);
                dos.writeBytes(gender);
                dos.writeBytes(lineEnd);

                dos.writeBytes("Content-Disposition: form-data; name=\"UserRating\"" + lineEnd);
                dos.writeBytes(lineEnd);
                dos.writeBytes(userRating);
                dos.writeBytes(lineEnd);

                dos.writeBytes("Content-Disposition: form-data; name=\"Birthday\"" + lineEnd);
                dos.writeBytes(lineEnd);
                dos.writeBytes(birthday);
                dos.writeBytes(lineEnd);

                dos.writeBytes("Content-Disposition: form-data; name=\"PicturePath\"" + lineEnd);
                dos.writeBytes(lineEnd);
                dos.writeBytes(urlString + "/var/www/html/uploads/" + username + "/" + fileName);
                dos.writeBytes(lineEnd);


                //Json_Encoder encode=new Json_Encoder();
                //call to encode method and assigning response data to variable 'data'
                //String data=encode.encod_to_json();
                //response of encoded data
                //System.out.println(data);


//Adding Parameter media file(audio,video and image)

                dos.writeBytes(twoHyphens + boundary + lineEnd);

                dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\"" + fileName + "\"" + lineEnd);
                dos.writeBytes(lineEnd);
                // create a buffer of maximum size
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                buffer = new byte[bufferSize];
                // read file and write it into form...
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                while (bytesRead > 0) {
                    dos.write(buffer, 0, bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                }

                // send multipart form data necesssary after file data...
                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);


                int serverResponseCode = conn.getResponseCode();
                String serverResponseMessage = conn.getResponseMessage();


                Log.i("uploadFile", "HTTP Response is : " + serverResponseMessage + ": " + serverResponseCode);

                if (serverResponseCode == 200) {
                    Log.d("SARAH", "File Upload Complete");
                }

                // close the streams //
                fileInputStream.close();
                dos.flush();
                dos.close();

            } catch (MalformedURLException ex) {

                ex.printStackTrace();

                Log.d("SARAH", "error: " + ex.getMessage(), ex);
            } catch (final Exception e) {
                e.printStackTrace();

                Log.d("SARAH",
                        "Exception : " + e.getMessage(), e);
            }
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            //print result
            String stringResponse = response.toString();
            System.out.println("Response: " + response);
            ArrayList<Event> list = convert(stringResponse);
            return stringResponse;

        }
    }

}