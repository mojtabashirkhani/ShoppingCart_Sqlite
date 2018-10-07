package com.nikan.clickpaz.nikantest.DatabaseModel;

/**
 * Created by slim shady on 09/09/2018.
 */

public class ShopModel {

    private int id;
    private String cost;
    private String title;
    private String description;
    private String detail;
    private String makeBy;
    private String img;
    private String number;




    public ShopModel(String title, String makeBy, String number,String cost,String description) {
        this.title = title;
        this.makeBy = makeBy;
        this.number = number;
        this.cost=cost;
        this.description=description;
    }

    public ShopModel(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getMakeBy() {
        return makeBy;
    }

    public void setMakeBy(String makeBy) {
        this.makeBy = makeBy;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
