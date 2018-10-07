package com.nikan.clickpaz.nikantest;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.nikan.clickpaz.nikantest.Api.ApiInterface;
import com.nikan.clickpaz.nikantest.RestModel.SmsOk;
import com.nikan.clickpaz.nikantest.RestModel.Verify;
import com.nikan.clickpaz.nikantest.utils.PrefManager;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SmsVerificationActivity extends AppCompatActivity implements View.OnClickListener {



    //private ViewPager viewPager;
   // private ViewPagerAdapter adapter;
    private Button btnRequestSms, btnVerifyOtp;
    private EditText inputName, inputEmail, inputMobile, inputOtp;
    private ProgressBar progressBar;
    private PrefManager pref;
    private ImageButton btnEditMobile;
    private TextView txtEditMobile;
    private LinearLayout layoutEditMobile;
    private String API_LINK="http://mynikan4.ir/myapi/";
    private static final String TAG="SmsVerfication";
    private String mobile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);

        //viewPager = (ViewPager) findViewById(R.id.viewPagerVertical);
       // inputName = (EditText) findViewById(R.id.inputName);
       // inputEmail = (EditText) findViewById(R.id.inputEmail);
        inputMobile = (EditText) findViewById(R.id.inputMobile);
        inputOtp = (EditText) findViewById(R.id.inputOtp);
        btnRequestSms = (Button) findViewById(R.id.btn_request_sms);
        btnVerifyOtp = (Button) findViewById(R.id.btn_verify_otp);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
       // btnEditMobile = (ImageButton) findViewById(R.id.btn_edit_mobile);
       // txtEditMobile = (TextView) findViewById(R.id.txt_edit_mobile);
       // layoutEditMobile = (LinearLayout) findViewById(R.id.layout_edit_mobile);

      //  btnEditMobile.setOnClickListener(this);
        btnRequestSms.setOnClickListener(this);
        btnVerifyOtp.setOnClickListener(this);

        // hiding the edit mobile number
       // layoutEditMobile.setVisibility(View.GONE);

        pref = new PrefManager(this);

        // Checking for user session
        // if user is already logged in, take him to main activity
        if (pref.isLoggedIn()) {
            Intent intent = new Intent(SmsVerificationActivity.this, MenuActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

            finish();
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_request_sms:
                validateForm();
                break;

            case R.id.btn_verify_otp:
                verifyOtp();
                break;

           /* case R.id.btn_edit_mobile:
                viewPager.setCurrentItem(0);
                layoutEditMobile.setVisibility(View.GONE);
                pref.setIsWaitingForSms(false);
                break;*/
        }
    }

    private void validateForm() {


         mobile = inputMobile.getText().toString().trim();



        // validating empty name and email
        if (mobile.length()==0) {
            Toast.makeText(getApplicationContext(), "Please enter your phone number", Toast.LENGTH_SHORT).show();
            return;
        }

        // validating mobile number
        // it should be of 10 digits length
        if (isValidPhoneNumber(mobile)) {

            // request for sms
            //SmsOk smsOk=new SmsOk(inputMobile.getText().toString());
            // sendNetworkRequest(smsOk);

            progressBar.setVisibility(View.VISIBLE);


            requestForSMS(mobile);

            // saving the mobile number in shared preferences
            pref.setMobileNumber(mobile);

            // requesting for sms


        } else {
            Toast.makeText(getApplicationContext(), "Please enter valid mobile number", Toast.LENGTH_SHORT).show();
        }

    }

    private void requestForSMS(String mobile) {



       OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.interceptors().add(interceptor);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_LINK)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface service = retrofit.create(ApiInterface.class);
        Call<SmsOk> call=service.postNumber(mobile);
        //retrofit
        call.enqueue(new Callback<SmsOk>() {
            @Override
            public void onResponse(Call<SmsOk> call, Response<SmsOk> response) {


               if(response.isSuccessful()) {
                   Log.d(TAG, response.message());

                   progressBar.setVisibility(View.GONE);
               }
                  /*Boolean bool=  response.body().getStatus();
                  Log.d(TAG, String.valueOf(bool));

                 String phoneNumber= response.body().getPhone();
                 Log.d(TAG,phoneNumber);*/

            }

            @Override
            public void onFailure(Call<SmsOk> call, Throwable t) {

                Log.d(TAG,t.getMessage());
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "Please enter mobile number witout (0) like 935*******", Toast.LENGTH_SHORT).show();


            }
        });

    }

    private boolean isValidPhoneNumber(String mobile) {

        String regEx = "^[0-9]{10}$";
        return mobile.matches(regEx);
    }

    private boolean isValidOTPNumber(String otp) {

        String regEx = "^[0-9]{5}$";
        return otp.matches(regEx);
    }

    private void verifyOtp() {

        String otp = inputOtp.getText().toString().trim();
        if (isValidOTPNumber(otp)&!otp.isEmpty()) {
            progressBar.setVisibility(View.VISIBLE);

            //retrofit


                OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
                HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
                interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                httpClient.interceptors().add(interceptor);

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(API_LINK)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                ApiInterface service = retrofit.create(ApiInterface.class);
                Call<Verify> call = service.checkCode(otp);
                call.enqueue(new Callback<Verify>() {
                    @Override
                    public void onResponse(Call<Verify> call, Response<Verify> response) {
                        Log.d(TAG, response.message());
                        progressBar.setVisibility(View.GONE);
                        pref.createLogin(mobile);


                        new Handler().post(new Runnable() {

                            @Override
                            public void run() {
                                Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                                startActivity(intent);
                            }
                        });


                    }

                    @Override
                    public void onFailure(Call<Verify> call, Throwable t) {
                        Log.d(TAG, t.getMessage());

                        progressBar.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "Something get wrong ! ", Toast.LENGTH_SHORT).show();


                    }
                });


            } else {
                Toast.makeText(getApplicationContext(), "Please enter the OTP", Toast.LENGTH_SHORT).show();
            }

    }


}
