package com.example.almishop.model;

public class StoreImages {

    private String imgUrl;
    private String bottomText;

    public StoreImages(String imgUrl, String bottomText) {
        this.imgUrl = imgUrl;
        this.bottomText = bottomText;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getBottomText() {
        return bottomText;
    }

    public void setBottomText(String bottomText) {
        this.bottomText = bottomText;
    }
}
