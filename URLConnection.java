package csulb.edu.pickup;

import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;



import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class URLConnection
{
    private final String USER_AGENT = "Mozilla/5.0";

    /**
     * Performs the Http connection
     * is used in all of the calls to the server
     * @param url - URL of the php file being called
     * @param urlParameters -  key value pairs being passed to the server
     * @return - a string response from the server
     * @throws IOException
     */
    private String makeHTTPRequest(String url, String urlParameters)throws IOException {
        java.net.URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        //add reuqest header
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
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
        System.out.println(response);
        in.close();
        return response.toString();
    }

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

        String urlParameters = "Username="+username+"&Password="+password+"&FirstName="+firstName+
                "&LastName="+lastName+"&Birthday="+birthday+"&Gender="+gender+
                "&UserRating="+userRating+"&PicturePath="+picturePath;
        return makeHTTPRequest(url,urlParameters);
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
        String urlParameters = "Username="+username+"&Password="+password;
        return makeHTTPRequest(url,urlParameters);
    }

    public ArrayList<Event> sendFilterEvents( String author, String eventName, String sport,
                                    String location, String latitude, String longitude, String dateCreatedStart, String dateCreatedEnd, String eventTimeStart, String eventTimeEnd, String eventDateStart, String eventDateEnd, String ageMax, String ageMin,
                                    String minUserRating, String onlyNotFull, String isPublic, String gender) throws IOException  {
            /*url of route being requested*/
            String url = "http://www.csulbpickup.com/filterEvents.php";
            String urlParameters = "Author="+author+"&EventName="+eventName+"&Sport="+sport+"&Location="+location+"&Latitude="+latitude+
                "&Longitude="+longitude+"&DateCreatedStart="+dateCreatedStart+"&DateCreatedEnd="+dateCreatedEnd+"&EventTimeStart="+eventTimeStart+
                "&EventTimeEnd="+eventTimeEnd+"&EventDateStart="+eventDateStart+"&EventDateEnd="+eventDateEnd+"&AgeMax="+ageMax+"&AgeMin="+ageMin+
                "&OnlyNotFull="+onlyNotFull+"&MinUserRating="+minUserRating+"&IsPublic="+isPublic+"&Gender="+gender;
        String response = makeHTTPRequest(url,urlParameters);
        String stringResponse = response.toString();
        ArrayList<Event> list = convertEventList(stringResponse);

        return list;

    }
    public String sendRSVP(String username, String eventID) throws IOException  {

				/*url of route being requested*/
        String url = "http://www.csulbpickup.com/CreateRSVP.php";
        String urlParameters = "RSVPUser="+username+"&EventID="+eventID;
        return makeHTTPRequest(url,urlParameters);
    }
    public String sendUnRSVP(String username, String eventID) throws IOException  {

				/*url of route being requested*/
        String url = "http://www.csulbpickup.com/CreateUnRSVP.php";
        String urlParameters = "UnRSVPUser="+username+"&EventID="+eventID;
        return makeHTTPRequest(url,urlParameters);
    }
    /**
     * Returns a list of the users who are on the RSVP list
     * @param eventID
     * @return 2d array [user's full name][username]
     * @throws IOException
     */
    public String [][] sendGetRSVPList(int eventID) throws IOException  {

			/*url of route being requested*/
        String url = "http://www.csulbpickup.com/getRSVPList.php";
        String urlParameters = "EventID="+eventID;
        String response =  makeHTTPRequest(url,urlParameters);
        String[][] RSVPList =  convertRSVPList(response);
        for(int i = 0;i<RSVPList.length;i++){
            System.out.println(i+" "+RSVPList[i][0]+" "+RSVPList[i][1]);
        }
        return RSVPList;
    }
    public String sendCheckIfFriends(String myUsername, String thisUsername) throws IOException  {

				/*url of route being requested*/
        String url = "http://www.csulbpickup.com/CheckIfFriends.php";
        String urlParameters = "myUsername="+myUsername+"&thisUsername="+thisUsername;
        return makeHTTPRequest(url,urlParameters);
    }
    public String sendAddFriend(String myUsername, String thisUsername) throws IOException  {

				/*url of route being requested*/
        String url = "http://www.csulbpickup.com/CreateFollow.php";
        String urlParameters = "Follower="+myUsername+"&Followee="+thisUsername;
        return makeHTTPRequest(url,urlParameters);
    }
    public String sendDeleteFriend(String myUsername, String thisUsername) throws IOException  {

				/*url of route being requested*/
        String url = "http://www.csulbpickup.com/deleteFollow.php";
        String urlParameters = "Follower="+myUsername+"&Followee="+thisUsername;
        return makeHTTPRequest(url,urlParameters);
    }
    public String [][] sendGetFriendList(String Username) throws IOException  {

			/*url of route being requested*/
        String url = "http://www.csulbpickup.com/getFollows.php";
        String urlParameters = "Follower="+Username;
        String response =  makeHTTPRequest(url,urlParameters);
        String[][] FriendList =  convertRSVPList(response);
        for(int i = 0;i<FriendList.length;i++){
            System.out.println(i+" "+FriendList[i][0]+" "+FriendList[i][1]);
        }
        return FriendList;
    }
    public String sendResetEmail(String username) throws IOException  {

				/*url of route being requested*/
        String url = "http://www.csulbpickup.com/sendResetEmail.php";

        String urlParameters = "Username="+username;
        return makeHTTPRequest(url, urlParameters);
    }

    public String sendPasswordReset(String username, String oldPassword, String newPassword) throws IOException  {

				/*url of route being requested*/
        String url = "http://www.csulbpickup.com/changePassword.php";

        String urlParameters = "Username="+username+"&Password="+oldPassword+"&NewPassword="+newPassword;
        return makeHTTPRequest(url, urlParameters);
    }


    public String sendEditUser(String username,  String firstName,
                               String lastName, String birthday, String gender,
                               String userRating, String picturePath)
            throws IOException  {

				/*url of route being requested*/
        String url = "http://www.csulbpickup.com/editUser.php";

        String urlParameters = "Username="+username+"&FirstName="+firstName+"&LastName="+lastName+"&Birthday="+birthday+
                "&Gender="+gender+"&UserRating="+userRating+"&PicturePath="+picturePath;
        return makeHTTPRequest(url,urlParameters);
    }


    /**
     *
     * @param username
     * @return
     * @throws IOException
     */
    public String sendChangePassword(String username, String password, String newPassword)
            throws IOException  {
				/*url of route being requested*/
        String url = "http://www.csulbpickup.com/changePassword.php";

        String urlParameters = "Username="+username+"&Password="+password+"&NewPassword="+newPassword;
        return makeHTTPRequest(url, urlParameters);
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

        String urlParameters = "Username="+username;

        return convertUser(makeHTTPRequest(url, urlParameters));
    }


    /**
     * gets a list of all users in database on server.
     * @return ArrayList<String> - a string object for each username in server
     * @throws IOException
     */
    public ArrayList<String> sendGetUsernames() throws IOException  {


				/*url of route being requested*/
        String url = "http://www.csulbpickup.com/getUsers.php";

        String urlParameters = "";
        String response = makeHTTPRequest(url, urlParameters);

        String stringResponse = response.toString();
        ArrayList<String> list = convertUsernamesList(stringResponse);


        return list;
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

        String urlParameters = "Username=" + username;
        return makeHTTPRequest(url,urlParameters);

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
    public String sendCreateEvent(String authorName,String authorEmail, String eventName, String sport,
                                  String location, String latitude, String longitude, String eventDateTime,
                                  String eventEndDateTime, String ageMax, String ageMin, String minUserRating,
                                  String playerAmount, String isPrivate, String gender, String skill, String sportSpecific,
                                  String playersPerTeam, String numberOfTeams, String terrain, String environment)
                                  throws IOException  {

				/*url of route being requested*/
        String url = "http://www.csulbpickup.com/createEvent.php";

        String urlParameters = "AuthorName="+authorName+"&Email="+authorEmail+"&EventName="+eventName+"&Sport="+sport+"&Location="+location+"&Latitude="+latitude+
                "&Longitude="+longitude+"&EventDateTime="+eventDateTime+"&AgeMax="+ageMax+"&AgeMin="+ageMin+
                "&PlayerAmount="+playerAmount+"&MinUserRating="+minUserRating+"&IsPrivate="+isPrivate+"&Gender="+gender+
                "&Skill="+skill+"&SportSpecific="+sportSpecific+"&EventEndDateTime="+eventEndDateTime+"&PlayersPerTeam="+playersPerTeam+"&NumberOfTeams="+numberOfTeams+
                "&Terrain="+terrain+"&Environment="+environment;

        return makeHTTPRequest(url,urlParameters);

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

        String urlParameters = "EventID="+eventID;

        return makeHTTPRequest(url,urlParameters);

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

        String urlParameters = "EventID="+eventID+"&EventName="+eventName+"&Sport="+sport+"&Location="+location+"&Latitude="+latitude+
                "&Longitude="+longitude+"&EventDateTime="+eventDateTime+"&AgeMax="+ageMax+"&AgeMin="+ageMin+
                "&PlayerAmount="+playerAmount+"&MinUserRating="+minUserRating+"&IsPrivate="+isPrivate+"&Gender="+gender;

        return makeHTTPRequest(url,urlParameters);

    }

    /**
     * gets a list of all events in database on server.
     * @return ArrayList<Event> - an event object for each event in server
     * @throws IOException
     */
    public ArrayList<Event> sendGetEvents() throws IOException  {


				/*url of route being requested*/
        String url = "http://www.csulbpickup.com/getEvents.php";

        String urlParameters = "";
        String response = makeHTTPRequest(url, urlParameters);

        String stringResponse = response.toString();
        ArrayList<Event> list = convertEventList(stringResponse);


        return list;
    }

    /**
     * gets a list of all events for a user in database on server.
     * @return ArrayList<Event> - an event object for each event in server
     * @throws IOException
     */
    public ArrayList<Event> sendGetEventsForUser(String username) throws IOException  {

        System.out.println("GetEventsForUser");

		/*url of route being requested*/
        String url = "http://www.csulbpickup.com/getEventsForUser.php";

        String urlParameters = "Username=" + username;


        String response = makeHTTPRequest(url, urlParameters);

        //print result
        String stringResponse = response.toString();
        System.out.println("Response: " + response);
        ArrayList<Event> list = convertEventList(stringResponse);

        return list;
    }

    /**
     * gets a list of all events for a user in database on server.
     * @return ArrayList<Event> - an event object for each event in server
     * @throws IOException
     */
    public ArrayList<Event> sendGetEventsFromDistance(String latitude, String longitude, String distance) throws IOException  {

        System.out.println("GetEventsFromDistance");

		/*url of route being requested*/
        String url = "http://www.csulbpickup.com/getEventsFromDistance.php";

        String urlParameters = "Latitude=" + latitude + "&Longitude=" + longitude + "&Distance=" + distance;

        String response = makeHTTPRequest(url, urlParameters);

        //print result
        String stringResponse = response.toString();
        System.out.println("Response: " + response);
        ArrayList<Event> list = convertEventList(stringResponse);

        return list;
    }

    /**
     * Gets a single event from server for the given EventID
     * @param eventID
     * @return Event - an Event object for the retrieved Event on server.
     * @throws IOException
     */
    public Event sendGetEvent(int eventID) throws IOException  {

        String url = "http://www.csulbpickup.com/getEvent.php";

        String urlParameters = "EventID="+eventID;
        String response = makeHTTPRequest(url,urlParameters);

        //print result
        String stringResponse = response.toString();
        ArrayList<Event> list = convertEventList(stringResponse);
        Event returnedEvent = list.get(0);
        return returnedEvent;
    }

	    /*
	    The functions below are all private and deal with manipulating the server response into objects
	     */

    /**
     * function for converting
     * @param dateTime
     * @return
     */
    private String serverToClientDate(String dateTime){
        String year = dateTime.substring(0, 4);
        String month = dateTime.substring(5,7);
        String day = dateTime.substring(8, 10);
        return day+"-"+month+"-"+year;
    }
    /**
     * function for converting Server datetime format to client time
     * @param dateTime
     * @return String Time
     */
    private String serverToClientTime(String dateTime){
        String hour = dateTime.substring(11, 13);
        String minute = dateTime.substring(14, 16);
        int hourInt = Integer.parseInt(hour);
        if(hourInt>12){
            return hourInt-12+":"+minute+" PM";
        }
        return hourInt+":"+minute+" AM";
    }

    private ArrayList<java.lang.String> distances;

    public ArrayList<java.lang.String> getEventDistances()
    {
        return distances;
    }

    private ArrayList<Event> convertEventList(String str) {

		    	/*Divide string up into lines */
        String[] lines=str.split("#");

        ArrayList<Event> list = new ArrayList<Event>();
        distances = new ArrayList<java.lang.String>();

        /*for each line parse key-value pairs */
        for(String line : lines){
            if(!line.isEmpty()){
                Map<String, String> map = new HashMap<>();
//                int count = 0;
                for(String pair: line.split(",")){
                    String[] tokens = pair.split("::");
                    for (int i=0; i<tokens.length-1; )
                    {
                        map.put(tokens[i++], tokens[i++]);
                    }
                }
			    		/*if map has valid data */
                if(map.containsKey("Longitude")) {

			    			/*Below line used for testing */

                    /* TODO: get date working */
                    Event newEvent = new Event(
                            map.get("EventID"),
                            map.get("EventName"),
                            "",
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
                            map.get("EventEndDateTime"),//serverToClientDate(map.get("EventEndDateTime")),
                            map.get("EventEndDateTime"),//serverToClientTime(map.get("EventEndDateTime")),
                            map.get("IsPrivate"),
                            map.get("Skill"),
                            map.get("SportSpecific"),
                            map.get("PlayersPerTeam"),
                            map.get("NumberOfTeams"),
                            map.get("Terrain"),
                            map.get("Environment"),
                            map.get("Category")
                            );
                    list.add(newEvent);
                }
                if(map.containsKey("_distance"))
                {
                    java.lang.String distance = map.get("_distance");
                    if(distance.contains("."))
                    {
                        int per = distance.indexOf(".");
                        distance = distance.substring(0, per+2);
                    }
                    distances.add(distance);
                }
            }
        }

        return list;
    }


    private User convertUser(String str) {

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

    private ArrayList<String> convertUsernamesList(String str) {

		    	/*Divide string up into lines */
        String[] lines=str.split("#");
        ArrayList<String> list = new ArrayList<String>();

        for(int i = 0; i < lines.length; i++)
        {
            if(!lines[i].isEmpty())
                list.add(lines[i]); // add username to the list
        }

        return list;
    }

    private  String[][] convertRSVPList(String str){
		    	/*Divide string up into lines */
        String[] lines=str.split("#");
        String[][] RSVPPairs = new String[lines.length-1][2];
			        /*for each line parse key-value pairs */
        int arrayCount=0;
        for(String line : lines){
            System.out.println("line:"+line);
            if(line.isEmpty()){

            }
            else{
                Map <String, String> RSVPList = new HashMap<>();
                for(String pair: line.split(",")){
                    System.out.println("Pair:"+pair);
                    String[] tokens = pair.split("::");
                    for (int i=0; i<tokens.length-1;i++ ){
                        RSVPList.put(tokens[i], tokens[i+1]);
                    }
                }
                RSVPPairs[arrayCount][0] = RSVPList.get("Name");
                RSVPPairs[arrayCount][1] = RSVPList.get("Username");
                arrayCount++;
            }
        }
        return RSVPPairs;
    }

    //Create user who rates another user
    public String sendCreateRatings(String RaterUsername, String RatedUsername, int Vote)
            throws IOException  {

        /*url of route being requested*/
        String url = "http://www.csulbpickup.com/createRatings.php";
        String urlParameters = "RaterUsername="+RaterUsername+"&RatedUsername="+RatedUsername+"&Vote="+Vote;
        return makeHTTPRequest(url,urlParameters);
    }

    //Edit user who already rated another person and uppdate value
    public String sendEditUserRatings(String RaterUsername, String RatedUsername, int Vote) throws IOException {
        String url = "http://www.csulbpickup.com/editUserRatings.php";
        String urlParameters = "RaterUsername="+RaterUsername+"&RatedUsername="+RatedUsername+"&Vote="+Vote;
        return makeHTTPRequest(url, urlParameters);
    }

    public String[][] sendGetUserRatingsList(String RaterUsername) throws IOException  {

			/*url of route being requested*/
        String url = "http://www.csulbpickup.com/getUserRatingsList.php";
        String urlParameters = "RaterUsername="+RaterUsername;
        String response =  makeHTTPRequest(url,urlParameters);
        String[][] RatingList =  convertRatingList(response);
        for(int i = 0;i<RatingList.length;i++){
            System.out.println(i+" "+RatingList[i][0]+" "+RatingList[i][1]);
        }

        return RatingList;

    }

    private String[][] convertRatingList (String response){
        String[] lines=response.split("#");
        String[][] RatingPairs = new String[lines.length-1][3];


        int arrayCount=0;
        for(String line : lines){
            if(line.isEmpty()){

            }
            else{
                Map <String, String> RatingList = new HashMap<>();
                for(String pair: line.split(",")){
                    String[] tokens = pair.split("::");
                    for (int i=0; i<tokens.length-1;i++ ){
                        RatingList.put(tokens[i], tokens[i+1]);

                    }
                }
                RatingPairs[arrayCount][0] = RatingList.get("RaterUsername");
                RatingPairs[arrayCount][1] = RatingList.get("RatedUsername");
                RatingPairs[arrayCount][2] = RatingList.get("Vote");
                arrayCount++;
            }
        }



        return RatingPairs;

    }


}