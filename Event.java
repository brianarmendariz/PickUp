package csulb.edu.pickup;


import android.os.Parcel;
import android.os.Parcelable;

public class Event implements Parcelable
{
    private String _name;
    private String _description;
    private String _creatorName;
    private String _creatorEmail;
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
    private String _eventEndDate;
    private String _eventEndTime;
    private String _isPrivate;
    private String _skill;
    private String _sportSpecific;
    private String _playersPerTeam;
    private String _numberOfTeams;
    private String _terrain;
    private String _environment;
    private String _eventID;
    private String _category;

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
        _creatorName = null;
        _creatorEmail = null;
        _sport = null;
        _gender = null;
        _maxNumberPpl = null;
        _minUserRating = null;
        _dateCreated = null;
        _eventDate = null;
        _eventTime = null;
        _eventEndDate = null;
        _eventEndTime = null;
        _isPrivate = null;
        _skill = null;
        _sportSpecific = null;
        _playersPerTeam = null;
        _numberOfTeams = null;
        _terrain = null;
        _environment = null;
        _category = null;
    }

    public Event(String eventID, String name, String description, double longitude, double latitude, String address, String ageMax,
                 String ageMin, String creatorName, String creatorEmail, String sport, String gender, String maxNumberPpl,
                 String minUserRating, String dateCreated, String eventDate, String eventTime, String eventEndDate,
                 String eventEndTime, String isPrivate, String skill, String sportSpecific, String playersPerTeam,
                 String numberOfTeams, String terrain, String environment, String category)
    {
        _eventID = eventID;
        _name = name;
        _description = description;
        _longitude = longitude;
        _latitude = latitude;
        _address = address;
        _ageMax = ageMax;
        _ageMin = ageMin;
        _creatorName = creatorName;
        _creatorEmail = creatorEmail;
        _sport = sport;
        _gender = gender;
        _maxNumberPpl = maxNumberPpl;
        _minUserRating = minUserRating;
        _dateCreated = dateCreated;
        _eventDate = eventDate;
        _eventTime = eventTime;
        _eventEndDate = eventEndDate;
        _eventEndTime = eventEndTime;
        _isPrivate = isPrivate;
        _skill = skill;
        _sportSpecific = sportSpecific;
        _playersPerTeam = playersPerTeam;
        _numberOfTeams = numberOfTeams;
        _terrain = terrain;
        _environment = environment;
        _category = category;
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
    public String getCreatorName()
    {
        return _creatorName;
    }
    public String getCreatorEmail(){return _creatorEmail;}
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
    public String getDateCreated() { return _dateCreated; }
    public String getEventDate() { return _eventDate; }
    public String getEventTime() { return _eventTime; }
    public String getEventEndDate() { return _eventEndDate; }
    public String getEventEndTime() { return _eventEndTime; }
    public String getIsPrivate()
    {
        return _isPrivate;
    }
    public String getEventID()
    {
        return _eventID;
    }
    public String getSkill() { return _skill; }
    public String getSportSpecific() { return _sportSpecific; }
    public String getPlayersPerTeam() { return _playersPerTeam; }
    public String getNumberOfTeams() { return _numberOfTeams; }
    public String getTerrain() { return _terrain; }
    public String getEnvironment()
    {
        return _environment;
    }
    public String getCategory()
    {
        return _category;
    }

    /*
    Setter Methods
     */
    public void setCreatorName(String creatorName)
    {
        _creatorName = creatorName;
    }
    public void setCreatorEmail(String creatorEmail){_creatorEmail = creatorEmail;}
    public void setSport(String sport)
    {
        _sport = sport;
    }
    public void setEventEndDate(String endDate)
    {
        _eventEndDate = endDate;
    }
    public void setEventEndTime(String endTime)
    {
        _eventEndTime = endTime;
    }
    public void setSkill(String skill)
    {
        _skill = skill;
    }
    public void setSportSpecific(String sportSpecific)
    {
        _sport = _sportSpecific;
    }
    public void setPlayersPerTeam(String playersPerTeam)
    {
        _playersPerTeam = playersPerTeam;
    }
    public void setNumberOfTeams(String numberOfTeams)
    {
        _numberOfTeams = numberOfTeams;
    }
    public void setTerrain(String terrain)
    {
        _terrain = terrain;
    }
    public void setEnvironment(String environment) { _environment = environment;  }
    public void setGender(String gender)
    {
        _gender = gender;
    }
    public void setMaxNumberPpl(String maxNumberPpl)
    {
        _maxNumberPpl = maxNumberPpl;
    }
    public void setCategory(String category)
    {
        _category = category;
    }

    public void setDate(String day, String month, String year)
    {
        _eventDate = day + "-" + month + "-" + year;
    }

    public String getDay()
    {
        String day = _eventDate.substring(0,2);
        if(day.substring(0,1).equals("0"))
            day = day.substring(1);
        return day;
    }
    public String getMonth()
    {
        String month = _eventDate.substring(3,5);
        if(month.substring(0,1).equals("0"))
            month = month.substring(1);
        return month;
    }
    public String getYear()
    {
        return _eventDate.substring(6);
    }

    public int equals(Event otherEvent)
    {
        return _eventID.compareTo(otherEvent.getEventID());
    }
//    public int equals(Event otherEvent)
//    {
//        return _eventDate.compareTo(otherEvent.getEventDate());
//    }


    // PARCELABLE
    private int mData;

    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel out, int flags) {
        //out.writeInt(mData);

        out.writeStringArray(new String[]{_eventID, _name, _description, _creatorName, _creatorEmail,
                _sport, "" +_longitude, ""+_latitude, _address, _gender, _ageMax, _ageMin,_minUserRating,
                _maxNumberPpl, _dateCreated, _eventDate, _eventTime, _eventEndDate,_eventEndTime, _isPrivate,
                _skill, _sportSpecific, _playersPerTeam, _numberOfTeams, _terrain, _environment});

    }


    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }

        public Event[] newArray(int size) {
            return new Event[size];
        }
    };

    private Event(Parcel in) {
        //mData = in.readInt();
        String[] data = new String[18];

        in.readStringArray(data);
        this._eventID = data[0];
        this._name = data[1];
        this._description = data[2];
        this._creatorName = data[3];
        this._creatorEmail = data[4];
        this._sport = data[5];
        this._longitude = Double.parseDouble(data[6]);
        this._latitude = Double.parseDouble(data[7]);
        this._address = data[8];
        this._gender = data[9];
        this._ageMax = data[10];
        this._ageMin = data[11];
        this._minUserRating = data[12];
        this._maxNumberPpl = data[13];
        this._dateCreated = data[14];
        this._eventDate = data[15];
        this._eventTime = data[16];
        this._eventEndDate = data[17];
        this._eventEndDate = data[18];
        this._isPrivate = data[19];
        this._isPrivate = data[20];
        this._skill = data[21];
        this._sportSpecific = data[22];
        this._playersPerTeam = data[23];
        this._numberOfTeams = data[24];
        this._terrain = data[25];
        this._environment = data[26];
    }

}