
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

//import javax.net.HttpURLConnection;


import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.*;
public class URLConnectionTester {

	private final String USER_AGENT = "Mozilla/5.0";

	 public static void main(String[] args) throws Exception {
	       
			URLConnectionTester http = new URLConnectionTester();

		 
		 
			   
	        /*test parameter values*/
	        String author = "sarah"; 
	        String eventName = "event"; 
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
	        int eventID = 1;
	        /*
	         * uncomment any of the requests below to use them
	         */
	       
	       /*http.sendCreateEvent(author, eventName, sport,location, latitude,longitude,
	        	eventDateTime, ageMax, ageMin, minUserRating, playerAmount, isPrivate, gender);
	        */
	        http.sendDeleteEvent(eventID);

	        http.sendGetEvents();
	        
	      
	    }
	  
		private void sendCreateEvent( String author, String eventName, String sport,
				String location, String latitude, String longitude, String eventDateTime, String ageMax, String ageMin,
				String minUserRating, String playerAmount, String isPrivate, String gender) throws HttpException, IOException {

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
		}
		private void sendDeleteEvent( int eventID) throws HttpException, IOException {

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
				System.out.println("appended");
				response.append(inputLine);
			}
			in.close();
			
			//print result
			System.out.println("Response:"+response.toString());
		}
		private ArrayList<Event> sendGetEvents() throws HttpException, IOException {

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
	    				System.out.println("map:"+map);

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