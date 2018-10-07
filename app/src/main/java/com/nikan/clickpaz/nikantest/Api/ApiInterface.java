package com.nikan.clickpaz.nikantest.Api;

import com.nikan.clickpaz.nikantest.RestModel.SmsOk;
import com.nikan.clickpaz.nikantest.RestModel.Verify;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by slim shady on 09/03/2018.
 */

public interface ApiInterface {

    @GET("index.php")
    Call<SmsOk> postNumber(@Query("number") String number);


    @GET("verify.php")
    Call<Verify> checkCode(@Query("code") String otp);
}
