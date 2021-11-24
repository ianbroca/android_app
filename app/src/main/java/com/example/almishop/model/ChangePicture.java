package com.example.almishop.model;

public class ChangePicture
{
    private int id;
    private String pfp;

    public ChangePicture(int id, String pfp)
    {
        this.id = id;
        this.pfp = pfp;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getPfp()
    {
        return pfp;
    }

    public void setPfp(String pfp)
    {
        this.pfp = pfp;
    }
}
