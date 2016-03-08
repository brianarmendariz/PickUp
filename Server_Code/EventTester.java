import org.apache.commons.httpclient.methods.PostMethod;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
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
	        String latitude = "3.11111";
	        String longitude = "43.3333";
	        String eventDateTime = ""; 
	        String ageMax = "100"; 
	        String ageMin = "0";
	        String minUserRating = "0"; 
	        String playerAmount = "20"; 
	        String isPrivate = "false";
	        /*
	         * uncomment any of the requests below to use them
	         */
	       
	        http.sendCreateEvent(client, author, eventName, sport,location, latitude,longitude,
	        		eventDateTime, ageMax, ageMin, minUserRating, playerAmount, isPrivate);
	        http.sendGetEvents(client);
	        
	      
	    }
	  
		private void sendCreateEvent(HttpClient client, String author, String eventName, String sport,
				String location, String latitude, String longitude, String eventDateTime, String ageMax, String ageMin,
				String minUserRating, String playerAmount, String isPrivate) throws HttpException, IOException {

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
		private void sendGetEvents(HttpClient client) throws HttpException, IOException {

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
	       // System.out.println("Response: "+response);
	        convert(response);
	        /*HttpState keeps track of available cookies*/
	        HttpState state = client.getState();
	        for(Cookie c : state.getCookies()) {
	            //System.out.println("cookie = " + c.getName() + "=" + c.getValue());
	        }
		}
	    public void convert(String str) {
	    	String[] lines=str.split("#");
	        ArrayList<Map<String, String>> list = new ArrayList<Map<String, String>>();
	    	for(String line : lines){
		        String[] tokens = line.split(",|::");
		        Map<String, String> map = new HashMap<>();
		        for (int i=0; i<tokens.length-1; ){
		        		map.put(tokens[i++], tokens[i++]);
		        }
		        list.add(map);
		        	System.out.println(map);
		    	
	    	}
	    	//System.out.println(list);
	    }

}