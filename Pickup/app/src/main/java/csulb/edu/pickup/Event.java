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
        _creatorName = null;
        _creatorEmail = null;
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
                 String ageMin, String creatorName, String creatorEmail, String sport, String gender, String maxNumberPpl,String minUserRating,
                 String dateCreated, String eventDate, String eventTime, String isPrivate, String eventID)
    {
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
    public void setCreatorName(String creatorName)
    {
        _creatorName = creatorName;
    }
    public void setCreatorEmail(String creatorEmail){_creatorEmail = creatorEmail;}
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

        out.writeStringArray(new String[]{_name, _description, _creatorName, _creatorEmail,
                _sport, "" +_longitude, ""+_latitude, _address, _gender, _ageMax, _ageMin,_minUserRating,
                _maxNumberPpl, _dateCreated, _eventDate, _eventTime, _isPrivate, _eventID });

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
        this._name = data[0];
        this._description = data[1];
        this._creatorName = data[2];
        this._creatorEmail = data[3];
        this._sport = data[4];
        this._longitude = Double.parseDouble(data[5]);
        this._latitude = Double.parseDouble(data[6]);
        this._address = data[7];
        this._gender = data[8];
        this._ageMax = data[9];
        this._ageMin = data[10];
        this._minUserRating = data[11];
        this._maxNumberPpl = data[12];
        this._dateCreated = data[13];
        this._eventDate = data[14];
        this._eventTime = data[15];
        this._isPrivate = data[16];
        this._eventID = data[17];
    }

}