package csulb.edu.pickup;



import android.os.Parcel;
import android.os.Parcelable;

public class Event implements Parcelable
{
    private int _eventID;
    private String _name;
    private String _creatorName;
    private String _creatorEmail;
    private String _sport;
    private String _address;
    private double _latitude;
    private double _longitude;
    private String  _gender;
    private int _ageMin;
    private int _ageMax;
    private int _minUserRating;
    //    private String _dateCreated;
    private String _eventStartDate;
    private String _eventStartTime;
    private String _eventEndDate;
    private String _eventEndTime;
    //    private boolean _isPrivate;
    private String _skill;
    private String _sportSpecific;
    private int _playersPerTeam;
    private int _numberOfTeams;
    private String _terrain;
    private String _environment;
    private String _category;

    private int _totalHeadCount;

    public Event()
    {
        _eventID = -1;
        _name = null;
        _creatorName = null;
        _creatorEmail = null;
        _sport = null;
        _address = null;
        _longitude = -1;
        _latitude = -1;
        _gender = null;
        _ageMin = -1;
        _ageMax = -1;
        _minUserRating = 0;
        _eventStartDate = null;
        _eventStartTime = null;
        _eventEndDate = null;
        _eventEndTime = null;
        _skill = null;
        _sportSpecific = null;
        _playersPerTeam = -1;
        _numberOfTeams = -1;
        _terrain = null;
        _environment = null;
        _category = null;
        _totalHeadCount = 0;
    }

    /**
     * Event to SEND TO the server
     * @param name
     * @param creatorName
     * @param creatorEmail
     * @param sport
     * @param address
     * @param longitude
     * @param latitude
     * @param gender
     * @param ageMin
     * @param ageMax
     * @param minUserRating
     * @param eventStartDate
     * @param eventStartTime
     * @param eventEndDate
     * @param eventEndTime
     * @param skill
     * @param sportSpecific
     * @param playersPerTeam
     * @param numberOfTeams
     * @param terrain
     * @param environment
     * @param category
     */
    public Event(String name, String creatorName, String creatorEmail, String sport, String address, double latitude, double longitude,
                 String gender, int ageMin, int ageMax, int minUserRating, String eventStartDate, String eventStartTime, String eventEndDate,
                 String eventEndTime, String skill, String sportSpecific, int playersPerTeam, int numberOfTeams, String terrain,
                 String environment, String category)
    {
        _eventID = -1;
        _name = name;
        _creatorName = creatorName;
        _creatorEmail = creatorEmail;
        _sport = sport;
        _address = address;
        _longitude = longitude;
        _latitude = latitude;
        _gender = gender;
        _ageMin = ageMin;
        _ageMax = ageMax;
        _minUserRating = minUserRating;
        _eventStartDate = eventStartDate;
        _eventStartTime = eventStartTime;
        _eventEndDate = eventEndDate;
        _eventEndTime = eventEndTime;
        _skill = skill;
        _sportSpecific = sportSpecific;
        _playersPerTeam = playersPerTeam;
        _numberOfTeams = numberOfTeams;
        _terrain = terrain;
        _environment = environment;
        _category = category;
    }

    /**
     * Event to RECIEVE FROM the server
     * @param name
     * @param creatorName
     * @param creatorEmail
     * @param sport
     * @param address
     * @param longitude
     * @param latitude
     * @param gender
     * @param ageMin
     * @param ageMax
     * @param minUserRating
     * @param eventStartDate
     * @param eventStartTime
     * @param eventEndDate
     * @param eventEndTime
     * @param skill
     * @param sportSpecific
     * @param playersPerTeam
     * @param numberOfTeams
     * @param terrain
     * @param environment
     * @param category
     */
    public Event(int id, String name, String creatorName, String creatorEmail, String sport, String address, double latitude, double longitude,
                 String gender, int ageMin, int ageMax, int minUserRating, String eventStartDate, String eventStartTime, String eventEndDate,
                 String eventEndTime, String skill, String sportSpecific, int playersPerTeam, int numberOfTeams, String terrain,
                 String environment, String category)
    {
        _eventID = id;
        _name = name;
        _creatorName = creatorName;
        _creatorEmail = creatorEmail;
        _sport = sport;
        _address = address;
        _longitude = longitude;
        _latitude = latitude;
        _gender = gender;
        _ageMin = ageMin;
        _ageMax = ageMax;
        _minUserRating = minUserRating;
        _eventStartDate = eventStartDate;
        _eventStartTime = eventStartTime;
        _eventEndDate = eventEndDate;
        _eventEndTime = eventEndTime;
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
    public int getEventID()
    {
        return _eventID;
    }
    public String getName()
    {
        return _name;
    }
    public String getCreatorName()
    {
        return _creatorName;
    }
    public String getCreatorEmail()
    {
        return _creatorEmail;
    }
    public String getSport()
    {
        return _sport;
    }
    public String getAddress()
    {
        return _address;
    }
    public double getLongitude()
    {
        return _longitude;
    }
    public double getLatitude()
    {
        return _latitude;
    }
    public String getGender()
    {
        return _gender;
    }
    public int getAgeMin()
    {
        return _ageMin;
    }
    public int getAgeMax()
    {
        return _ageMax;
    }
    public int getMinUserRating()
    {
        return _minUserRating;
    }
    public String getEventStartDate()
    {
        return _eventStartDate;
    }
    public String getEventStartTime()
    {
        return _eventStartTime;
    }
    public String getEventEndDate()
    {
        return _eventEndDate;
    }
    public String getEventEndTime()
    {
        return _eventEndTime;
    }
    public String getSkill()
    {
        return _skill;
    }
    public String getSportSpecific()
    {
        return _sportSpecific;
    }
    public int getPlayersPerTeam()
    {
        return _playersPerTeam;
    }
    public int getNumberOfTeams()
    {
        return _numberOfTeams;
    }
    public String getTerrain()
    {
        return _terrain;
    }
    public String getEnvironment()
    {
        return _environment;
    }
    public String getCategory()
    {
        return _category;
    }
    public int getTotalHeadCount()
    {
        if(_totalHeadCount == 0)
        {
            setTotalHeadCount();
        }
        return _totalHeadCount;
    }






    /*
    Setter Methods
     */
    public void setID(int id)
    {
        _eventID = id;
    }
    public void setName(String name)
    {
        _name = name;
    }
    public void setCreatorName(String creatorName)
    {
        _creatorName = creatorName;
    }
    public void setCreatorEmail(String creatorEmail)
    {
        _creatorEmail = creatorEmail;
    }
    public void setSport(String sport)
    {
        _sport = sport;
    }
    public void setLatitude(double latitude)
    {
        _latitude = latitude;
    }
    public void setLongitude(double longitude)
    {
        _longitude = longitude;
    }
    public void setAgeMin(int ageMin)
    {
        _ageMin = ageMin;
    }
    public void setAgeMax(int ageMax)
    {
        _ageMax = ageMax;
    }
    public void setMinUserRating(int minUserRating)
    {
        _minUserRating = minUserRating;
    }
    public void setEventDate(String date)
    {
        _eventStartDate = date;
    }
    public void setEventTime(String time)
    {
        _eventStartTime = time;
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
    public void setPlayersPerTeam(int playersPerTeam)
    {
        _playersPerTeam = playersPerTeam;
    }
    public void setNumberOfTeams(int numberOfTeams)
    {
        _numberOfTeams = numberOfTeams;
    }
    public void setTerrain(String terrain)
    {
        _terrain = terrain;
    }
    public void setEnvironment(String environment)
    {
        _environment = environment;
    }
    public void setGender(String gender)
    {
        _gender = gender;
    }
    public void setCategory(String category)
    {
        _category = category;
    }

    public void setTotalHeadCount()
    {
        _totalHeadCount = _playersPerTeam * _numberOfTeams == 0 ? _playersPerTeam : _playersPerTeam * _numberOfTeams;
    }
    public void setDate(String day, String month, String year)
    {
        _eventStartDate = day + "-" + month + "-" + year;
    }

    public String getDay()
    {
        String day = _eventStartDate.substring(0,2);
        if(day.substring(0,1).equals("0"))
            day = day.substring(1);
        return day;
    }
    public String getMonth()
    {
        String month = _eventStartDate.substring(3,5);
        if(month.substring(0,1).equals("0"))
            month = month.substring(1);
        return month;
    }
    public String getYear()
    {
        return _eventStartDate.substring(6);
    }

    public String toString()
    {
        return "EventID: " + _eventID +
                "\nEventName: " + _name +
                "\nCreatorName: " + _creatorName +
                "\nCreatorEmail: " + _creatorEmail +
                "\nSport: " + _sport +
                "\nAddress: " + _address +
                "\nLongitude: " + _longitude +
                "\nLatitude: " + _latitude +
                "\nGender: " + _gender +
                "\nAge Min: " + _ageMin +
                "\nAge Max: " + _ageMax +
                "\nMin User Rating: " + _minUserRating +
                "\nEvent Date: " + _eventStartDate +
                "\nEvent Time: " + _eventStartTime +
                "\nEvent End Date: " + _eventEndDate +
                "\nEvent End Time: " + _eventEndTime +
                "\nSkill: " + _skill +
                "\nSport Specific: " + _sportSpecific +
                "\nPlayer Per Team: " +_playersPerTeam +
                "\nNumber of Teams: " + _numberOfTeams +
                "\nTerrain: " + _terrain +
                "\nEnvironment: " + _environment +
                "\nCategory: " + _category;
    }


    // PARCELABLE
    private int mData;

    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel out, int flags) {
        //out.writeInt(mData);

        out.writeStringArray(new String[]{ _eventID + "", _name, _creatorName, _creatorEmail, _sport,
                _address, _longitude + "", _latitude + "", _gender, _ageMin + "", _ageMax + "", _minUserRating + "",
                _eventStartDate, _eventStartTime, _eventEndDate, _eventEndTime, _eventEndDate, _eventEndTime,
                _skill, _sportSpecific, _playersPerTeam + "", _numberOfTeams + "", _terrain, _environment, _category});

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
/*
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
        this._eventStartDate = data[15];
        this._eventStartTime = data[16];
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
		this._category = data[27];
*/
    }


}