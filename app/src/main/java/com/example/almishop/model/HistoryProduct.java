package com.example.almishop.model;

import java.io.Serializable;

public class HistoryProduct implements Serializable
{
    private String id;
    private String name;
    private String price;
    private String cover;

    public HistoryProduct(String id, String name, String price, String cover) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.cover = cover;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }
}
