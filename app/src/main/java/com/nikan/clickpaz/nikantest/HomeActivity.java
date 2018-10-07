package com.nikan.clickpaz.nikantest;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;


public class HomeActivity extends AppCompatActivity  implements ActivityCompat.OnRequestPermissionsResultCallback,PermissionResultCallback  {


    private TextView nikan, description;

    private View mLayout;
    private static final String TAG = "HomeActivity";
    PermissionUtils permissionUtils;
    ArrayList<String> permissions=new ArrayList<>();


    // private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        // callApi();



        nikan = (TextView) findViewById(R.id.nikan);
        description = (TextView) findViewById(R.id.desc);
        mLayout = findViewById(R.id.main_layout);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            permissionUtils = new PermissionUtils(this);

            permissions.add(Manifest.permission.RECEIVE_SMS);
            permissions.add(Manifest.permission.READ_SMS);
            permissionUtils.check_permission(permissions, "Explain here why the app needs permissions", 1);

        }
        description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Timer();
            }
        });



        nikan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Timer();
            }
        });
    }









    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        permissionUtils.onRequestPermissionsResult(requestCode,permissions,grantResults);


       // Timer();

    }

    public void Timer() {

        Intent intent = new Intent(HomeActivity.this, MainActivity.class);
        startActivity(intent);
       // finish();


            }




    @Override
    public void PermissionGranted(int request_code) {
        Log.i("PERMISSION","GRANTED");

    }

    @Override
    public void PartialPermissionGranted(int request_code, ArrayList<String> granted_permissions) {
        Log.i("PERMISSION PARTIALLY","GRANTED");

    }

    @Override
    public void PermissionDenied(int request_code) {
        Log.i("PERMISSION","DENIED");

    }

    @Override
    public void NeverAskAgain(int request_code) {
        Log.i("PERMISSION","NEVER ASK AGAIN");

    }
}




