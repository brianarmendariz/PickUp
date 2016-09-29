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
public class User implements Parcelable {
    String firstName;
    String lastName;
    String email;
    String password;
    String birthday;
    String age;
    String gender;
    String userRating;
    String picturePath;

    public User(String fn, String ln, String em,String pw,String bday,String gend, String useRate){
        firstName = fn;
        lastName = ln;
        email = em;
        password = pw;
        birthday = bday;
        gender = gend;
        userRating = useRate;
    }
    public User(String fn, String ln, String em,String pw,String bday,String gend, String useRate, String picture){
        firstName = fn;
        lastName = ln;
        email = em;
        password = pw;
        birthday = bday;
        gender = gend;
        userRating = useRate;
        picturePath = picture;
    }
    public User(Parcel in){
        String[] data = new String[7];

        in.readStringArray(data);
        this.firstName = data[0];
        this.lastName = data[1];
        this.email = data[2];
        this.password = data[3];
        this.birthday = data[4];
        this.gender = data[5];
        this.userRating = data[6];
       // this.picturePath = data[7];

    }

    public String getEmail(){
        return email;
    }
    public String getlastName(){
        return lastName;
    }
    public String getFirstName(){
        return firstName;
    }
    public String getBirthday(){
        return birthday;
    }
    public String getAge()
    {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date birth = new Date();
        try {
            birth =  dateFormat.parse(birthday);
        } catch(ParseException e) { }
        long ageInMillis = (new GregorianCalendar().getInstance().getTimeInMillis() - birth.getTime());
        Date age = new Date(ageInMillis);
        Calendar can = new GregorianCalendar().getInstance();
        can.setTime(age);
        return ""+can.YEAR;
    }
    public String getGender(){
        return gender;
    }
    public String getUserRating(){
        return userRating;
    }
    //public String getPicturePath() {return picturePath;}

    public void setPassword(String pw){
        password = pw;
    }
    public void setlastName(String ln){
        lastName = ln;
    }
    public void setFirstName(String fn){
        firstName = fn;
    }
    public void setBirthday(String bday){
        birthday = bday;
    }
    public void setGender(String gend){
        gender = gend;
    }
    public void UserRating(String useRate){
        userRating = useRate;
    }
    public void setPicturePath(String picture) {
        picturePath= picture;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] {firstName ,lastName, email, password, birthday, gender, userRating});
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
