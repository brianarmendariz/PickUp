package csulb.edu.pickup;

/**
 * Created by Brain on 2/16/2016.
 */
public class Event
{
    private String _name;
    private String _description;
    private String _creator;
    private String _sport;
    private double _longitude;
    private double _latitude;
    private String _address;
    private String  _gender;
    private String _ageMax;
    private String _ageMin;
    private String _minUserRating;
    private String _maxNumberPpl;
    private String _dateCreated;
    private String _eventDate;
    private String _isPrivate;



    public Event(String name, String description, double longitude, double latitude, String address, String ageMax,
                 String ageMin, String creator, String sport, String gender, String maxNumberPpl,String minUserRating,
                 String dateCreated, String eventDate, String isPrivate)
    {
        _name = name;
        _description = description;
        _longitude = longitude;
        _latitude = latitude;
        _address = address;
        _ageMax = ageMax;
        _ageMin = ageMin;
        _creator = creator;
        _sport = sport;
        _gender = gender;
        _maxNumberPpl = maxNumberPpl;
        _minUserRating = minUserRating;
        _dateCreated = dateCreated;
        _eventDate = eventDate;
        _isPrivate = isPrivate;
    }

    /*
    Getter Methods
     */
    public double getLongitude()
    {
        return _longitude;
    }
    public double getLatitude()
    {
        return _latitude;
    }
    public String getCreator()
    {
        return _creator;
    }
    public String getSport()
    {
        return _sport;
    }

    public String getGender()
    {
        return _gender;
    }

    public String getMaxNumberPpl()
    {
        return _maxNumberPpl;
    }

    /*
    Setter Methods
     */
    public void setCreator(String creator)
    {
        _creator = creator;
    }
    public void setSport(String sport)
    {
        _sport = sport;
    }

    public void setGender(String gender)
    {
        _gender = gender;
    }

    public void setMaxNumberPpl(String maxNumberPpl)
    {
        _maxNumberPpl = maxNumberPpl;
    }
}