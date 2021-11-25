package com.example.almishop.model;

public class Videogame extends Product
{
    private String description;
    private String release_date;
    private String pegi;
    private String genre;
    private String platform;
    private String developer;
    private String stock_rent;

    public Videogame() {}

    public Videogame(String id, String id_product_type, String name, String stock_rent, String price, String stock_sale, String id_brand, String cover, String[] images, String discount, String priceDiscounted, String description, String release_date, String pegi, String genre, String platform, String developer)
    {
        super(id, id_product_type, name, price, stock_sale, id_brand, cover, images, discount, priceDiscounted);
        this.description = description;
        this.stock_rent = stock_rent;
        this.release_date = release_date;
        this.pegi = pegi;
        this.genre = genre;
        this.platform = platform;
        this.developer = developer;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getRelease_date()
    {
        return release_date;
    }

    public void setRelease_date(String release_date)
    {
        this.release_date = release_date;
    }

    public String getPegi()
    {
        return pegi;
    }

    public void setPegi(String pegi)
    {
        this.pegi = pegi;
    }

    public String getGenre()
    {
        return genre;
    }

    public void setGenre(String genre)
    {
        this.genre = genre;
    }

    public String getPlatform()
    {
        return platform;
    }

    public void setPlatform(String platform)
    {
        this.platform = platform;
    }

    public String getDeveloper()
    {
        return developer;
    }

    public void setDeveloper(String developer)
    {
        this.developer = developer;
    }

    public String getStock_rent()
    {
        return stock_rent;
    }

    public void setStock_rent(String stock_rent)
    {
        this.stock_rent = stock_rent;
    }
}
