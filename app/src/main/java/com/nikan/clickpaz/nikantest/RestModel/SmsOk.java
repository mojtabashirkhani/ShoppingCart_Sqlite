package com.nikan.clickpaz.nikantest.RestModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;



public class SmsOk {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("phone")
    @Expose
    private String phone;

    public SmsOk() {
    }

    public SmsOk(Boolean status, String phone) {
        this.status = status;
        this.phone = phone;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
