
public class Tester {

    public static void main(String[] args) {
    	
    	TextToImage t2i = new TextToImage();
    	Event _event = new Event("my event", "good times roll", 50.0, 50.0, "CSULB", "40", 
                "15", "Brain", "Basketball", "M", "12", "2",
                "2016 3 19", "2016 3 19", "6:00 PM", "false", "0");
    	t2i.createImage(_event);
    }
}
