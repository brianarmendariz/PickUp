package csulb.edu.pickup;

/*import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;*/
import java.io.File;
import java.io.IOException;

//import javax.imageio.ImageIO;

public class TextToImage {
	
	private int getLines(Event event)
	{
		int lines = 0;
		if(!event.getName().isEmpty() && event.getName() != null)
		{
			++lines;
		}
		if(!event.getCreatorEmail().isEmpty() && event.getCreatorEmail() != null)
		{
			++lines;
		}
		if(!event.getSport().isEmpty() && event.getSport() != null)
		{
			++lines;
		}
		if(!event.getAddress().isEmpty() && event.getAddress() != null)
		{
			++lines;
		}
		if(!event.getEventStartTime().isEmpty() && event.getEventStartTime() != null)
		{
			++lines;
		}
		if(!event.getEventStartDate().isEmpty() && event.getEventStartDate() != null)
		{
			++lines;
		}
		return lines;
	}
	
	private String getMonth(String monthNum)
	{
		String month = null;
		switch(monthNum)
		{
		case "1":
			month = "January";
			break;
		case "2":
			month = "February";
			break;
		case "3":
			month = "March";
			break;
		case "4":
			month = "April";
			break;
		case "5":
			month = "May";
			break;
		case "6":
			month = "June";
			break;
		case "7":
			month = "July";
			break;
		case "8":
			month = "August";
			break;
		case "9":
			month = "September";
			break;
		case "10":
			month = "October";
			break;
		case "11":
			month = "November";
			break;
		case "12":
			month = "December";
			break;
		default:
			month = "no month";			
		}
		return month;
	}
	
	private String getDate(String date)
	{
		String year = date.substring(0, 4);
		int ws1 = date.indexOf(" ") + 1;
		int ws2 = date.substring(ws1).indexOf(" ") + ws1;
		String month = date.substring(ws1, ws2);
		String day = date.substring(ws2+1);
		return getMonth(month) + " " + day + ", " + year;
	}
	
	private String[] makeEventArray(Event event)
	{
		String[] arr = new String[6];
		arr[0] = event.getName();
		arr[1] = event.getCreatorEmail();
		arr[2] = event.getSport();
		arr[3] = event.getAddress();
		arr[4] = event.getEventStartTime();
		arr[5] = getDate(event.getEventStartDate());
		
		return arr;
	}
	
	public void createImage(Event event)
	{
        String[] stringArray = makeEventArray(event);
   
        /*
           Because font metrics is based on a graphics context, we need to create
           a small, temporary image so we can ascertain the width and height
           of the final image
        */
        /*BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        Font font = new Font("Arial", Font.PLAIN, 32); //48
        g2d.setFont(font);
        FontMetrics fm = g2d.getFontMetrics();
        int width = fm.stringWidth(event.toString());
        int height = fm.getHeight()*getLines(event);
        g2d.dispose();

        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        g2d = img.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        g2d.setFont(font);
        fm = g2d.getFontMetrics();
        g2d.setColor(Color.BLUE);
        
        // this is the text to image, it creates a graphic then saves it to a png
        int line = 1;
        for(int i = 0; i < stringArray.length; i++)
        {
        	if(!stringArray[i].isEmpty() && stringArray[i] != null)
        	{
        		g2d.drawString(stringArray[i], 1, fm.getAscent()*line);
        		++line;
        	}
        }

    	String name = event.getName();
    	if(name.contains(" "))
    	{
    		name = name.replaceAll("\\s", "");
    	}
    	
        g2d.dispose();
        try {
            ImageIO.write(img, "png", new File(name+"_pevent.png"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }*/
	}

	public void createFlyer(Event event)
	{
        /*
		BufferedImage image;
		try {
			image = ImageIO.read(new File("images/field.png"));
			
		    Graphics g = image.getGraphics();
		    g.setFont(g.getFont().deriveFont(30f));
		    
		    int width = image.getWidth();
		    
		    String[] stringArray = makeEventArray(event);
		    
	        int line = 1;
	        for(int i = 0; i < stringArray.length; i++)
	        {
	        	if(!stringArray[i].isEmpty() && stringArray[i] != null)
	        	{
	        		g.drawString(stringArray[i], (width/3), (line+3)*30);
	        		++line;
	        	}
	        }
		    
		    g.dispose();
		    
	    	String name = event.getName();
	    	if(name.contains(" "))
	    	{
	    		name = name.replaceAll("\\s", "");
	    	}
	 
		    ImageIO.write(image, "png", new File(name+"_pevent_flyer.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

*/
	}
}