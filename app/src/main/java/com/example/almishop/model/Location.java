package com.example.almishop.model;

public class Location
{
    private int id_user;
    private double latitude;
    private double longitude;

    public Location(int id_user, double latitude, double longitude)
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

    public double getLatitude()
    {
        return latitude;
    }

    public void setLatitude(Float latitude)
    {
        this.latitude = latitude;
    }

    public double getLongitude()
    {
        return longitude;
    }

    public void setLongitude(Float longitude)
    {
        this.longitude = longitude;
    }
}
