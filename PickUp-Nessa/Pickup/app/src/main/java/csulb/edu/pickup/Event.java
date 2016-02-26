package csulb.edu.pickup;

import android.os.Parcel;
import android.os.Parcelable;

public class Event
{
    private String _name;
    private String _description;
    private String _creator;
    private String _sport;
    //private Location _location;
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
    private String _eventTime;
    private String _isPrivate;
    private String _eventID;


    public Event()
    {
        _eventID = null;
        _name = null;
        _description = null;
        _longitude = 0;
        _latitude = 0;
        _address = null;
        _ageMax = null;
        _ageMin = null;
        _creator = null;
        _sport = null;
        _gender = null;
        _maxNumberPpl = null;
        _minUserRating = null;
        _dateCreated = null;
        _eventDate = null;
        _eventTime = null;
        _isPrivate = null;
    }

    public Event(String name, String description, double longitude, double latitude, String address, String ageMax,
                 String ageMin, String creator, String sport, String gender, String maxNumberPpl,String minUserRating,
                 String dateCreated, String eventDate, String eventTime, String isPrivate, String eventID)
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
        _eventTime = eventTime;
        _isPrivate = isPrivate;
        _eventID = eventID;

    }

    /*
    Getter Methods
     */
    public String getName()
    {
        return _name;
    }
    public String getDescription()
    {
        return _description;
    }
    public double getLongitude()
    {
        return _longitude;
    }
    public double getLatitude()
    {
        return _latitude;
    }
    public String getAddress()
    {
        return _address;
    }
    public String getAgeMax()
    {
        return _ageMax;
    }
    public String getAgeMin()
    {
        return _ageMin;
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
    public String getMinUserRating()
    {
        return _minUserRating;
    }
    public String getDateCreated()
    {
        return _dateCreated;
    }
    public String getEventDate()
    {
        return _eventDate;
    }
    public String getEventTime()
    {
        return _eventTime;
    }
    public String getIsPrivate()
    {
        return _isPrivate;
    }
    public String getEventID()
    {
        return _eventID;
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
