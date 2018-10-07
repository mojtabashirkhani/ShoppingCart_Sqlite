package com.nikan.clickpaz.nikantest;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.nikan.clickpaz.nikantest.Adapter.LocationAdapter;
import com.nikan.clickpaz.nikantest.utils.LocationPref;

import java.util.ArrayList;

public class CompleteActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG="CompleteActivity";
    private static final int ERROR_DIALOG_REQUEST = 9001;



    private GoogleApiClient mGoogleApiClient;
    private static final int PERMISSION_REQUEST_CODE = 100;
     ArrayList<String> title;
     ArrayList<String> description;
     ArrayList<Integer> images;


    private Button savedLocaiotn;
    private CardView factor,map;
    private EditText inputFirst, inputLast, inputLandPhone,inputAddress;
    private TextInputLayout inputLayoutFirst, inputLayoutLast, inputLayoutLandPhone,inputLayoutAddress;
    private static final int PLACE_PICKER_REQUEST = 1;
    private LocationPref locationPref;

    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;
    private String intentTotal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        if(getSupportActionBar()!=null) {
            getSupportActionBar().setTitle("Complete the information");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            //Complete the information
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CompleteActivity.this, TakeOrderActivity.class);
                startActivity(intent);
            }
        });

        factor=(CardView) findViewById(R.id.compelete_card);

        inputLayoutFirst=(TextInputLayout)findViewById(R.id.input_layout_first);
        inputLayoutLast=(TextInputLayout)findViewById(R.id.input_layout_last);
        inputLayoutLandPhone=(TextInputLayout)findViewById(R.id.input_layout_phone);
        inputLayoutAddress=(TextInputLayout)findViewById(R.id.input_layout_address);

        inputFirst=(EditText)findViewById(R.id.edtFirst);
        inputLast=(EditText)findViewById(R.id.edtLast);
        inputLandPhone=(EditText)findViewById(R.id.edtPhone);
        inputAddress=(EditText)findViewById(R.id.edtAddress);
        initRecyclerView();

        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        //mPreferences = getSharedPreferences("tabian.com.sharedpreferencestest", Context.MODE_PRIVATE);
        mEditor = mPreferences.edit();

        checkSharePref();

       /* if(infoPref!=null) {
            inputFirst.setText(infoPref.getFirstName());
            inputLast.setText(infoPref.getLastName());
            inputAddress.setText(infoPref.getAddress());
            inputLandPhone.setText(infoPref.getLand());

        }*/

        map=(CardView)findViewById(R.id.pick_location);

        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isServicesOK()) {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                        if (mGoogleApiClient.isConnected()) {
                            if (ContextCompat.checkSelfPermission(CompleteActivity.this,
                                    Manifest.permission.ACCESS_FINE_LOCATION)
                                    != PackageManager.PERMISSION_GRANTED) {
                                ActivityCompat.requestPermissions(CompleteActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        PERMISSION_REQUEST_CODE);
                            }
                        } else {
                            callPlaceDetectionApi();

                        }
                    } else {
                        callPlaceDetectionApi();
                    }

                }
            }
        });






        inputFirst.addTextChangedListener(new MyTextWatcher(inputFirst));


        try {
            factor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (validateFirst()&&validateLast()&&validatePhone()&&validateAddress()){


                        String first=inputFirst.getText().toString().trim();
                        String last=inputLast.getText().toString().trim();
                        String address=inputAddress.getText().toString().trim();
                        String land=inputLandPhone.getText().toString().trim();


                        mEditor.putString("first", first);
                        mEditor.commit();

                        mEditor.putString("last", last);
                        mEditor.commit();

                        mEditor.putString("address", address);
                        mEditor.commit();

                        mEditor.putString("land", land);
                        mEditor.commit();


                        Intent intent=new Intent(CompleteActivity.this,FactorActivity.class);
                        intent.putExtra("first",first);
                        intent.putExtra("last",last);
                        intent.putExtra("address",address);
                        intent.putExtra("land",land);

                        startActivity(intent);

                    }

                }
            });

            //PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

        }
        catch (UnsupportedOperationException e){
            e.printStackTrace();
            Log.i(TAG,e.getMessage());
        }

        init();
        }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                String toastMsg = String.format("Place: %s", place.getName());
                LatLng latLng = place.getLatLng();
                double lat = latLng.latitude;
                double lng = latLng.longitude;

                locationPref = new LocationPref(this);

                locationPref.setLat(String.valueOf(lat));
                locationPref.setLng(String.valueOf(lng));
                Toast.makeText(this,"lat :"+locationPref.getLat()+"lng :"+locationPref.getLng()+" saved :)",Toast.LENGTH_SHORT).show();
                //locationPref.clearLocation();


            }
        }

    }

    public boolean isServicesOK(){
        Log.d(TAG, "isServicesOK: checking google services version");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(CompleteActivity.this);

        if(available == ConnectionResult.SUCCESS){
            //everything is fine and the user can make map requests
            Log.d(TAG, "isServicesOK: Google Play Services is working");
            return true;
        }
        else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            //an error occured but we can resolve it
            Log.d(TAG, "isServicesOK: an error occured but we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(CompleteActivity.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        }else{
            Toast.makeText(this, "You can't make map requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    private void checkSharePref(){

        String first=mPreferences.getString("first", "");
        String last=mPreferences.getString("last", "");
        String address=mPreferences.getString("address", "");
        String land=mPreferences.getString("land", "");

        inputFirst.setText(first);
        inputLast.setText(last);
        inputAddress.setText(address);
        inputLandPhone.setText(land);




    }

    private void initRecyclerView(){

        title=new ArrayList<>();

        title.add("work");
        title.add("home");
        title.add("uni");
        title.add("other");

        description=new ArrayList<>();

        description.add("enable");
        description.add("disable");
        description.add("disable");
        description.add("disable");

        images=new ArrayList<>();

        images.add(R.mipmap.ic_work_office);
        images.add(R.mipmap.ic_home);
        images.add(R.mipmap.ic_university);
        images.add(R.mipmap.ic_location);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = findViewById(R.id.recycler_locaition);
        recyclerView.setLayoutManager(linearLayoutManager);
        LocationAdapter cartListAdapter = new LocationAdapter(this,title,description,images);
        recyclerView.setAdapter(cartListAdapter);

    }

    private void init() {
        Log.d(TAG, "init: initializing");

        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();

    }
    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e(TAG, "Google Places API connection failed with error code: "
                + connectionResult.getErrorCode());

        Toast.makeText(this,
                "Google Places API connection failed with error code:" +
                        connectionResult.getErrorCode(),
                Toast.LENGTH_LONG).show();
    }

    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            switch (view.getId()) {
                case R.id.edtFirst:
                    validateFirst();
                    break;
                case R.id.edtLast:
                    validateLast();
                    break;

                case R.id.edtPhone:
                    validatePhone();
                    break;
                    
                case R.id.edtAddress:
                    validateAddress();
                    break;
            }
        }
        }

    private boolean validateAddress() {

        if (inputAddress.getText().toString().trim().isEmpty()) {
            inputLayoutAddress.setError("Enter Address");
            requestFocus(inputAddress);
            return false;
        } else {
            inputLayoutAddress.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateLast() {

        if (inputLast.getText().toString().trim().isEmpty()) {
            inputLayoutLast.setError("Enter Last Name");

            requestFocus(inputLast);
            return false;
        } else {
            inputLayoutLast.setErrorEnabled(false);
        }

        return true;
    }

    private void callPlaceDetectionApi(){



        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

        try {
            startActivityForResult(builder.build(CompleteActivity.this), PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException e) {
            Log.e(TAG, "onClick: GooglePlayServicesRepairableException: " + e.getMessage() );
        } catch (GooglePlayServicesNotAvailableException e) {
            Log.e(TAG, "onClick: GooglePlayServicesNotAvailableException: " + e.getMessage() );
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    callPlaceDetectionApi();
                }
                break;
        }
    }


    private boolean validateFirst() {

        if (inputFirst.getText().toString().trim().isEmpty()) {
            inputLayoutFirst.setError("Enter First Name");
            requestFocus(inputFirst);
            return false;
        } else {
            inputLayoutFirst.setErrorEnabled(false);
        }

        return true;
        
    }
    private boolean validatePhone() {

        if (inputLandPhone.getText().toString().trim().isEmpty()) {
            inputLayoutLandPhone.setError("Enter First Phone Number");
            requestFocus(inputLandPhone);
            return false;
        } else {
            inputLayoutLandPhone.setErrorEnabled(false);
        }

        return true;

    }
}

