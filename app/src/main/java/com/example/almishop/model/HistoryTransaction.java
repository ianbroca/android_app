package com.example.almishop.model;

import java.io.Serializable;
import java.util.ArrayList;

public class HistoryTransaction implements Serializable
{
    private String id;
    private String date;
    private ArrayList<HistoryProduct> products;

    public HistoryTransaction(String id, String date, ArrayList<HistoryProduct> products) {
        this.id = id;
        this.date = date;
        this.products = products;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<HistoryProduct> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<HistoryProduct> products) {
        this.products = products;
    }
}
