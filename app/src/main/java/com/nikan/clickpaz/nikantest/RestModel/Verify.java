package com.nikan.clickpaz.nikantest.RestModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by slim shady on 09/04/2018.
 */

public class Verify {
    @SerializedName("result")
    @Expose
    private Boolean result;

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }
}
