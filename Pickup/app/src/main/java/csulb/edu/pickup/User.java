package csulb.edu.pickup;

import android.os.Parcel;
import android.os.Parcelable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Sarah on 3/17/2016.
 */
public class User implements Parcelable
{
    private String _firstName;
    private String _lastName;
    private String _email;
    private String _password;
    private String _birthday;
    private String _age;
    private String _gender;
    private String _userRating;
    private String _picturePath;

    public User()
    {
        _firstName = null;
        _lastName = null;
        _email = null;
        _password = null;
        _birthday = null;
        _gender = null;
        _userRating = null;
    }

    public User(String fn, String ln, String em,String pw,String bday,String gend, String useRate){
        _firstName = fn;
        _lastName = ln;
        _email = em;
        _password = pw;
        _birthday = bday;
        _gender = gend;
        _userRating = useRate;
    }
    public User(String fn, String ln, String em,String pw,String bday,String gend, String useRate, String picture){
        _firstName = fn;
        _lastName = ln;
        _email = em;
        _password = pw;
        _birthday = bday;
        _gender = gend;
        _userRating = useRate;
        _picturePath = picture;
    }
    public User(Parcel in){
        String[] data = new String[7];

        in.readStringArray(data);
        this._firstName = data[0];
        this._lastName = data[1];
        this._email = data[2];
        this._password = data[3];
        this._birthday = data[4];
        this._gender = data[5];
        this._userRating = data[6];
       // this._picturePath = data[7];

    }

    public String getEmail(){
        return _email;
    }
    public String getPassword()
    {
        return _password;
    }
    public String getlastName(){
        return _lastName;
    }
    public String getFirstName(){
        return _firstName;
    }
    public String getLastName(){
        return _lastName;
    }
    public String getBirthday(){
        return _birthday;
    }
    public String getAge()
    {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date birth = new Date();
        try {
            birth =  dateFormat.parse(_birthday);
        } catch(ParseException e) { }
        long ageInMillis = (new GregorianCalendar().getInstance().getTimeInMillis() - birth.getTime());
        Date age = new Date(ageInMillis);
        Calendar can = new GregorianCalendar().getInstance();
        can.setTime(age);
        return ""+can.YEAR;
    }
    public String getGender(){
        return _gender;
    }
    public String getUserRating(){
        return _userRating;
    }
    public String getPicturePath() {return _picturePath;}

    public void setPassword(String pw){
        _password = pw;
    }
    public void setlastName(String ln){
        _lastName = ln;
    }
    public void setFirstName(String fn){
        _firstName = fn;
    }
    public void setEmail(String em)
    {
        _email = em;
    }
    public void setBirthday(String bday){
        _birthday = bday;
    }
    public void setGender(String gend){
        _gender = gend;
    }
    public void setUserRating(String useRate){
        _userRating = useRate;
    }
    public void setPicturePath(String picture) {
        _picturePath= picture;
    }

    public String toString()
    {
        return "FirstName: " + _firstName +
                "\nLastName: " + _lastName +
                "\nEmail: " + _email +
                "\nPassword: " + _password +
                "\nBirthday: " + _birthday +
                "\nAge: " + getAge() +
                "\nGender: " + _gender +
                "\nUserRating: " + _userRating;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] {_firstName ,_lastName, _email, _password, _birthday, _gender, _userRating});
    }
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
