import org.apache.commons.httpclient.methods.PostMethod;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.*;
public class EventTester {

	
	 public static void main(String[] args) throws Exception {
	        EventTester http = new EventTester();
	        
	    	/*This client must be passed to every request for this session*/
	    	HttpClient client = new HttpClient();
	   
	        /*test parameter values*/
	        String author = "sarah"; 
	        String eventName = "event1"; 
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
	        /*
	         * uncomment any of the requests below to use them
	         */
	       
	       // http.sendCreateEvent(client, author, eventName, sport,location, latitude,longitude,
	        //		eventDateTime, ageMax, ageMin, minUserRating, playerAmount, isPrivate, gender);
	        http.sendGetEvents(client);
	        
	      
	    }
	  
		private void sendCreateEvent(HttpClient client, String author, String eventName, String sport,
				String location, String latitude, String longitude, String eventDateTime, String ageMax, String ageMin,
				String minUserRating, String playerAmount, String isPrivate, String gender) throws HttpException, IOException {

			System.out.println("CreateEvent");
			
			/*url of route being requested*/
			String url = "http://www.csulbpickup.com/createEvent.php";

	        PostMethod post = new PostMethod(url);
			
			 /*parameters to be passed with request*/
	        post.addParameter("Author", author);
	        post.addParameter("EventName", eventName);
	        post.addParameter("Sport", sport);
	        post.addParameter("Location", location);
	        post.addParameter("Latitude", latitude);
	        post.addParameter("Longitude", longitude);
	        post.addParameter("EventDateTime", eventDateTime);
	        post.addParameter("AgeMax", ageMax);
	        post.addParameter("AgeMin", ageMin);
	        post.addParameter("PlayerAmount", playerAmount);
	        post.addParameter("MinUserRating", minUserRating);
	        post.addParameter("IsPrivate", isPrivate);
	        post.addParameter("Gender", gender);
	        

	        /*status of request - 200 if successful*/
	        int status = client.executeMethod(post); 
	        System.out.println("Status: "+status);
	        
	        /*String response from server*/
	        String response = post.getResponseBodyAsString();
	        System.out.println("Response: "+response);
	        
	        /*HttpState keeps track of available cookies*/
	        HttpState state = client.getState();
	        for(Cookie c : state.getCookies()) {
	            System.out.println("cookie = " + c.getName() + "=" + c.getValue());
	        }
		}
		private ArrayList<Event> sendGetEvents(HttpClient client) throws HttpException, IOException {

			System.out.println("GetEvents");
			
			/*url of route being requested*/
			String url = "http://www.csulbpickup.com/getEvents.php";

	        PostMethod post = new PostMethod(url);
			
			 /*parameters to be passed with request*/
	       

	        /*status of request - 200 if successful*/
	        int status = client.executeMethod(post); 
	        //System.out.println("Status: "+status);
	        
	        /*String response from server*/
	        String response = post.getResponseBodyAsString();
	        //System.out.println("Response: "+response);
	        ArrayList<Event> list = convert(response);
	        Event firstEvent = list.get(0);
	        /*HttpState keeps track of available cookies*/
	        HttpState state = client.getState();

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