package com.nikan.clickpaz.nikantest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.nikan.clickpaz.nikantest.Adapter.FactorAdapter;
import com.nikan.clickpaz.nikantest.DatabaseModel.ShopModel;
import com.nikan.clickpaz.nikantest.Sqlite.DatabaseHelper;

import java.util.ArrayList;

public class FactorActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG="FactorActivity";

    private String ItemName,intentT;
    private int cal=0;
    private int totalCost=0;


    private ArrayList<ShopModel> shopModelArray=new ArrayList<>();

    private TextView edit_date,edit_person,firstName,lastName,address,land,day,time,total;
    private CardView compelete;
    private Bundle bundle;
    private String Sday,Stime,Sid;
    private SharedPreferences mPreferences;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_factor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//item_mode


        if(getSupportActionBar()!=null) {
            getSupportActionBar().setTitle("Factor");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FactorActivity.this, CompleteActivity.class);
                startActivity(intent);
            }
        });
        edit_date=(TextView)findViewById(R.id.edit_date);
        edit_person=(TextView)findViewById(R.id.edit_person);

        total=(TextView)findViewById(R.id.total_cost);

        edit_date.setOnClickListener(this);
        edit_person.setOnClickListener(this);
        firstName=(TextView)findViewById(R.id.txt_first);
        lastName=(TextView)findViewById(R.id.txt_last);
        address=(TextView)findViewById(R.id.txt_address);
        land=(TextView)findViewById(R.id.txt_land);
        time=(TextView)findViewById(R.id.txt_time);
        day=(TextView)findViewById(R.id.txt_day);
       // intentT=getIntent().getStringExtra("total");

        bundle=getIntent().getExtras();
        if (bundle!=null) {
            initUserInfo();
        }

        initRecyclerView();

        initDate();

        intentT= String.valueOf(totalCost);

       /* LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("total-message"));*/

        LocalBroadcastManager.getInstance(this).registerReceiver(messageReceiver,
                new IntentFilter("send-final"));


        total.setText(intentT);

    }

   /* public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent

            intentT = intent.getStringExtra("total");
            Log.d(TAG,intentT);

        }
    };*/


    public BroadcastReceiver messageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            int sum = 0;

            ItemName = intent.getStringExtra("price");

            cal= Integer.parseInt(ItemName);

            // sum = cal + sum;
            total.setText(String.valueOf(cal+Integer.parseInt(intentT)));

        }
    };



    private void initRecyclerView(){


       DatabaseHelper databaseHelper=new DatabaseHelper(this);
       Cursor cursor=databaseHelper.selectSpecificId();
       if (cursor.getCount()==0){
           shopModelArray.clear();
           Toast.makeText(getApplicationContext(), "No Tasks", Toast.LENGTH_SHORT).show();

       }
       else {
           shopModelArray.clear();
           while (cursor.moveToNext()){
               ShopModel tddObj = new ShopModel();


               totalCost+=Integer.parseInt(cursor.getString(cursor.getColumnIndex("ShopNumber")))*Integer.parseInt(cursor.getString(cursor.getColumnIndex("ShopCost")));

               tddObj.setTitle(cursor.getString(cursor.getColumnIndex("ShopTitle")));
               tddObj.setCost(String.valueOf(Integer.parseInt(cursor.getString(cursor.getColumnIndex("ShopNumber")))*Integer.parseInt(cursor.getString(cursor.getColumnIndex("ShopCost")))));
               tddObj.setNumber(cursor.getString(cursor.getColumnIndex("ShopNumber")));

               shopModelArray.add(tddObj);

           }
       }
        databaseHelper.close();

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        RecyclerView recyclerView=findViewById(R.id.item_mode);
        recyclerView.setLayoutManager(linearLayoutManager);
        FactorAdapter factorAdapter=new FactorAdapter(this,shopModelArray);

        recyclerView.setAdapter(factorAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();

        // initItemPrice(price_all_item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.edit_date:
                Intent intent=new Intent(FactorActivity.this,TakeOrderActivity.class);
                intent.putExtra("day_update",Sday);
                intent.putExtra("time_update",Stime);
                intent.putExtra("id_update",Sid);


                startActivity(intent);
                break;

            case R.id.edit_person:
                Intent intent1=new Intent(FactorActivity.this,CompleteActivity.class);
               // intent1.putExtra("date_update","test");

                startActivity(intent1);

                break;

        }
    }
    public void showMessage(String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
    private void initUserInfo(){
        String first=bundle.getString("first");
        String last=bundle.getString("last");
        String adres=bundle.getString("address");
        String landPhone=bundle.getString("land");
       // intentT=bundle.getString("total");

        firstName.setText(first);
        lastName.setText(last);
        address.setText(adres);
        land.setText(landPhone);
    }

    private void initDate(){

        DatabaseHelper databaseHelper=new DatabaseHelper(this);
         Cursor cursor= databaseHelper.selectAllDate();
         if(cursor.getCount()==0){
             Log.d(TAG,"database :(");
         }
         else {

             while (cursor.moveToNext()){
                 Sid=cursor.getString(0);
                Sday=  cursor.getString(1);
                 Stime= cursor.getString(2);

                day.setText(Sday);
                time.setText(Stime);
             }
         }
         databaseHelper.close();
    }
}
