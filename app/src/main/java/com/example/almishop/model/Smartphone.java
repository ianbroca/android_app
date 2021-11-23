package com.example.almishop.model;

import java.util.Optional;

public class Smartphone extends Product
{
    private String storage;
    private String ram;
    private String inches;
    private String battery;
    private String has_sd;
    private String color;

    public Smartphone()
    {

    }

    public Smartphone(String id, String name, String price, String stock_sale, String id_brand, String cover, String images, String discount, String priceDiscounted, String storage, String ram, String inches, String battery, String has_sd, String color)
    {
        super(id, name, price, stock_sale, id_brand, cover, images, discount, priceDiscounted);
        this.storage = storage;
        this.ram = ram;
        this.inches = inches;
        this.battery = battery;
        this.has_sd = has_sd;
        this.color = color;
    }

    public String getStorage()
    {
        return storage;
    }

    public void setStorage(String storage)
    {
        this.storage = storage;
    }

    public String getRam()
    {
        return ram;
    }

    public void setRam(String ram)
    {
        this.ram = ram;
    }

    public String getInches()
    {
        return inches;
    }

    public void setInches(String inches)
    {
        this.inches = inches;
    }

    public String getBattery()
    {
        return battery;
    }

    public void setBattery(String battery)
    {
        this.battery = battery;
    }

    public String getHas_sd()
    {
        return has_sd;
    }

    public void setHas_sd(String has_sd)
    {
        this.has_sd = has_sd;
    }

    public String getColor()
    {
        return color;
    }

    public void setColor(String color)
    {
        this.color = color;
    }
}
