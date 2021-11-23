package com.example.almishop.model;

public class Location
{
    private int id_user;
    private Float latitude;
    private Float longitude;

    public Location(int id_user, Float latitude, Float longitude)
    {
        this.id_user = id_user;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getId_user()
    {
        return id_user;
    }

    public void setId_user(int id_user)
    {
        this.id_user = id_user;
    }

    public Float getLatitude()
    {
        return latitude;
    }

    public void setLatitude(Float latitude)
    {
        this.latitude = latitude;
    }

    public Float getLongitude()
    {
        return longitude;
    }

    public void setLongitude(Float longitude)
    {
        this.longitude = longitude;
    }
}
