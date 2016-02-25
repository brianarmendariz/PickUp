
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;



import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class URLConnectionTester {

	private final String USER_AGENT = "Mozilla/5.0";

	 public static void main(String[] args) throws Exception {
	       
			URLConnectionTester http = new URLConnectionTester();
			   
	        /*test parameter values*/
	        String author = "sarah2"; 
	        String eventName = "event2"; 
	        String sport = "soccer";
	        String location = "some address"; 
	        String latitude = "33.7830608";
	        String longitude = "-118.11489089999998";
	        String eventDateTime = "2012-11-22 14:45:34"; 
	        String ageMax = "100"; 
	        String ageMin = "0";
	        String minUserRating = "0"; 
	        String playerAmount = "20"; 
	        String isPrivate = "false";
	        String gender = "both";
	        int eventID =8;
	        int eventID2 = 7;
	       
	        System.out.println(http.serverToClientTime(eventDateTime));
	        System.out.println(http.serverToClientDate(eventDateTime));

	        /*
	         * uncomment any of the requests below to use them
	         */
	       
	        http.sendCreateEvent(author, eventName, sport,location, latitude,longitude,
	         	eventDateTime, ageMax, ageMin, minUserRating, playerAmount, isPrivate, gender);
	        
	        /*http.sendDeleteEvent(eventID2);
	        
	        http.sendEditEvent(eventID, author, eventName, sport,location, latitude,longitude,
		        	eventDateTime, ageMax, ageMin, minUserRating, playerAmount, isPrivate, gender);
	        */
	        http.sendGetEvents();
	        
	        //http.sendGetEvent(eventID);

	      
	    }
	  /**
	   * Sends event data to the server.
	   * 
	   * @param author
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
		public String sendCreateEvent( String author, String eventName, String sport,
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

			String urlParameters = "Author="+author+"&EventName="+eventName+"&Sport="+sport+"&Location="+location+"&Latitude="+latitude+
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
		 * @param author
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
		public String sendEditEvent( int eventID, String author, String eventName, String sport,
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

			String urlParameters = "EventID="+eventID+"&Author="+author+"&EventName="+eventName+"&Sport="+sport+"&Location="+location+"&Latitude="+latitude+
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
	        System.out.println("Response: "+response);
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
		 * Used for parsing the string response from the server.
		 * Converts a string to a map, and then converts the map to an Event object for each Event.
		 * @param str
		 * @return ArrayList<Event> - one Event for each Event entry in the database.
		 */
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
		    		if(map.containsKey("Longitude")){
		    			
		    			/*Below line used for testing */
	    				//System.out.println("map:"+map);

			    		Event newEvent = new Event(map.get("EventName"),"",
			    				Double.parseDouble(map.get("Longitude")),
			    				Double.parseDouble(map.get("Latitude")),
			    				map.get("Location"),
			    				map.get("AgeMax"),
			    				map.get("AgeMin"), 
			    				map.get("Author"),
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
	    /**
	     * function for converting
	     * @param dateTime
	     * @return
	     */
	    public String serverToClientDate(String dateTime){
	    	String year = dateTime.substring(0,4);
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


}