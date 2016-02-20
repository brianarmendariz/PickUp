package com.brain.myapplication;

import android.location.Location;

/**
 * Created by Brain on 2/16/2016.
 */
public class Event
{
    private String _creator;
    private Sport _sport;
    private Location _location;
    private char _gender;
    private int[] _ageGroup;
    private int _maxNumberPpl;

    public Event()
    {
        _creator = null;
        _sport = null;
        _location = null;
        _gender = 'a';
        _ageGroup = null;
        _maxNumberPpl = 0;
    }
    public Event(String creator, Sport sport, Location location)
    {
        _creator = creator;
        _sport = sport;
        _location = location;
        _gender = 'a';
        _ageGroup = null;
        _maxNumberPpl = 20;
    }
    public Event(String creator, Sport sport, Location location, char gender)
    {
        _creator = creator;
        _sport = sport;
        _location = location;
        _gender = gender;
        _ageGroup = null;
        _maxNumberPpl = 20;
    }
    public Event(String creator, Sport sport, Location location, int maxNumberPpl)
    {
        _creator = creator;
        _sport = sport;
        _location = location;
        _gender = 'a';
        _ageGroup = null;
        _maxNumberPpl = maxNumberPpl;
    }
    public Event(String creator, Sport sport, Location location, char gender, int[] ageGroup, int maxNumberPpl)
    {
        _creator = creator;
        _sport = sport;
        _location = location;
        _gender = gender;
        setAgeGroup(ageGroup);
        _maxNumberPpl = maxNumberPpl;
    }

    /*
    Getter Methods
     */
    public String getCreator()
    {
        return _creator;
    }
    public Sport getSport()
    {
        return _sport;
    }
    public Location getLocation()
    {
        return _location;
    }
    public char getGender()
    {
        return _gender;
    }
    public int[] getAgeGroup()
    {
        return _ageGroup;
    }
    public int getMaxNumberPpl()
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
    public void setSport(Sport sport)
    {
        _sport = sport;
    }
    public void setLocation(Location location)
    {
        _location = location;
    }
    public void setGender(char gender)
    {
        _gender = gender;
    }
    public void setAgeGroup(int[] ageGroup)
    {
        for(int i = 0; i < ageGroup.length; i++)
        {
            _ageGroup[i] = ageGroup[i];
        }
    }
    public void setMaxNumberPpl(int maxNumberPpl)
    {
        _maxNumberPpl = maxNumberPpl;
    }
}
