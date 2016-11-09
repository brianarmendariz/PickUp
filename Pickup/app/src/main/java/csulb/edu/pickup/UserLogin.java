package csulb.edu.pickup;

/**
 * Created by Brian on 11/8/2016.
 */
import java.io.Serializable;

public class UserLogin implements Serializable {


    private String _username;
    private String _password;

    public UserLogin()
    {
        _username ="";
        _password = "";
    }

    public UserLogin(String username, String password)
    {
        _username = username;
        _password = password;
    }

    public void setUsername(String username)
    {
        _username = username;
    }

    public void setPassword(String password)
    {
        _password = password;
    }

    public String getUsername()
    {
        return _username;
    }

    public String getPassword()
    {
        return _password;
    }
}
