package com.example.almishop.model;

import java.util.ArrayList;

public class Sale
{
    private int id_user;
    private ArrayList<String> id_products;

    public Sale(int id_user, ArrayList<String> id_products) {
        this.id_user = id_user;
        this.id_products = id_products;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public ArrayList<String> getId_products() {
        return id_products;
    }

    public void setId_products(ArrayList<String> id_products) {
        this.id_products = id_products;
    }
}
