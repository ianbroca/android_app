package com.example.almishop.model;

import java.io.Serializable;
import java.util.Optional;

public class Product implements Serializable
{
    private String id;
    private String name;
    private String price;
    private String stock_sale;
    private String id_brand;
    private String cover;
    private String images;
    private String discount;
    private String priceDiscounted;

    public Product() { }

    public Product(String id, String name, String price, String stock_sale, String id_brand, String cover, String images, String discount, String priceDiscounted)
    {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock_sale = stock_sale;
        this.id_brand = id_brand;
        this.cover = cover;
        this.images = images;
        this.discount = discount;
        this.priceDiscounted = priceDiscounted;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getPrice()
    {
        return price;
    }

    public void setPrice(String price)
    {
        this.price = price;
    }

    public String getStock_sale()
    {
        return stock_sale;
    }

    public void setStock_sale(String stock_sale)
    {
        this.stock_sale = stock_sale;
    }

    public String getId_brand()
    {
        return id_brand;
    }

    public void setId_brand(String id_brand)
    {
        this.id_brand = id_brand;
    }

    public String getCover()
    {
        return cover;
    }

    public void setCover(String cover)
    {
        this.cover = cover;
    }

    public String getImages()
    {
        return images;
    }

    public void setImages(String images)
    {
        this.images = images;
    }

    public String getDiscount()
    {
        return discount;
    }

    public void setDiscount(String discount)
    {
        this.discount = discount;
    }

    public String getPriceDiscounted()
    {
        return priceDiscounted;
    }

    public void setPriceDiscounted(String priceDiscounted)
    {
        this.priceDiscounted = priceDiscounted;
    }
}
