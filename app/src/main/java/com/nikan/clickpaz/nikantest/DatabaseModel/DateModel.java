package com.nikan.clickpaz.nikantest.DatabaseModel;

/**
 * Created by slim shady on 09/16/2018.
 */

public class DateModel {

    private int id;
    private String day;
    private String time;

    public DateModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
