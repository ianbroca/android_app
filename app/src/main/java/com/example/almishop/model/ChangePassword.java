package com.example.almishop.model;

public class ChangePassword
{
    private int id;
    private String password;

    public ChangePassword(String password)
    {
        this.password = password;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }
}
