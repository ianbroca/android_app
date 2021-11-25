package com.example.almishop.model;

public class Console extends Product
{
    private String storage;
    private String has_cd;
    private String ram;
    private String cpu;
    private String gpu;

    public Console()
    {

    }

    public Console(String id, String id_product_type, String name, String storage, String price, String stock_sale, String id_brand, String cover, String[] images, String discount, String priceDiscounted, String has_cd, String ram, String cpu, String gpu)
    {
        super(id, id_product_type, name, price, stock_sale, id_brand, cover, images, discount, priceDiscounted);
        this.storage = storage;
        this.has_cd = has_cd;
        this.ram = ram;
        this.cpu = cpu;
        this.gpu = gpu;
    }

    public String getHas_cd()
    {
        return has_cd;
    }

    public void setHas_cd(String has_cd)
    {
        this.has_cd = has_cd;
    }

    public String getRam()
    {
        return ram;
    }

    public void setRam(String ram)
    {
        this.ram = ram;
    }

    public String getCpu()
    {
        return cpu;
    }

    public void setCpu(String cpu)
    {
        this.cpu = cpu;
    }

    public String getGpu()
    {
        return gpu;
    }

    public void setGpu(String gpu)
    {
        this.gpu = gpu;
    }

    public String getStorage()
    {
        return storage;
    }

    public void setStorage(String storage)
    {
        this.storage = storage;
    }
}
