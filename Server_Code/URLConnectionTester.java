
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
	        String eventDateTime = ""; 
	        String ageMax = "100"; 
	        String ageMin = "0";
	        String minUserRating = "0"; 
	        String playerAmount = "20"; 
	        String isPrivate = "false";
	        String gender = "both";
	        int eventID =8;
	        int eventID2 = 7;
	        /*
	         * uncomment any of the requests below to use them
	         */
	       
	        http.sendCreateEvent(author, eventName, sport,location, latitude,longitude,
	         	eventDateTime, ageMax, ageMin, minUserRating, playerAmount, isPrivate, gender);
	        
	        http.sendDeleteEvent(eventID2);
	        
	        http.sendEditEvent(eventID, author, eventName, sport,location, latitude,longitude,
		        	eventDateTime, ageMax, ageMin, minUserRating, playerAmount, isPrivate, gender);
	        
	        http.sendGetEvents();
	        
	        http.sendGetEvent(eventID);

	      
	    }
	  
		public String sendCreateEvent( String author, String eventName, String sport,
				String location, String latitude, String longitude, String eventDateTime, String ageMax, String ageMin,
				String minUserRating, String playerAmount, String isPrivate, String gender) throws IOException  {

			System.out.println("CreateEvent");
			
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
		public String sendDeleteEvent( int eventID) throws IOException  {

			System.out.println("deleteEvent");
			
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
		public String sendEditEvent( int eventID, String author, String eventName, String sport,
				String location, String latitude, String longitude, String eventDateTime, String ageMax, String ageMin,
				String minUserRating, String playerAmount, String isPrivate, String gender) throws IOException  {

			System.out.println("editEvent");
			
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
		public ArrayList<Event> sendGetEvents() throws IOException  {

			System.out.println("GetEvents");
			
			/*url of route being requested*/
			String url = "http://www.csulbpickup.com/getEvents.php";

	        

	        URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			//add reuqest header
			con.setRequestMethod("POST");
			con.setRequestProperty("User-Agent", USER_AGENT);
			con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
			
			String urlParameters = "";
			
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
	        Event firstEvent = list.get(0);


	        return list;
		}
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
	    public ArrayList<Event> convert(String str) {
	    	String[] lines=str.split("#");
	    	//System.out.println(Arrays.toString(lines));
	        ArrayList<Event> list = new ArrayList<Event>();

	    	for(String line : lines){
	    		if(!line.isEmpty()){
			        Map<String, String> map = new HashMap<>();
	
		    		for(String pair: line.split(",")){
			    		String[] tokens = pair.split("::");
				        for (int i=0; i<tokens.length-1; ){
				        		map.put(tokens[i++], tokens[i++]);
				        
				        }
		    		}
		    		if(map.containsKey("Longitude")){
	    				//System.out.println("map:"+map);

			    		Event newEvent = new Event(map.get("eventName"),"",
			    				Double.parseDouble(map.get("Longitude")),
			    				Double.parseDouble(map.get("Latitude")),
			    				map.get("Location"), map.get("AgeMax"),
			    				map.get("AgeMin"), map.get("Author"), map.get("Sport"), map.get("Gender"), map.get("PlayerNumber"), map.get("MinUserRating"),
			    				map.get("DateTimeCreated"), map.get("EventDateTime"), map.get("isPrivate"));
				        list.add(newEvent);
			    	}
		    	}
	    	}
	    	
	    	return list;
	    }

}