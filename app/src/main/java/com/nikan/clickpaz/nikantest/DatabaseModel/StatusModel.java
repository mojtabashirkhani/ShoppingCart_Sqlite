package com.nikan.clickpaz.nikantest.DatabaseModel;

/**
 * Created by slim shady on 09/16/2018.
 */

public class StatusModel {

    private int orderId;
    private int itemId;
    private String status;
    private String number;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public StatusModel() {

    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
