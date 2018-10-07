package com.nikan.clickpaz.nikantest;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nikan.clickpaz.nikantest.DatabaseModel.DateModel;
import com.nikan.clickpaz.nikantest.Sqlite.DatabaseHelper;

import saman.zamani.persiandate.PersianDate;

public class TakeOrderActivity extends AppCompatActivity implements View.OnClickListener {

    private CardView cardTomorrow,cardAfter,cardTime,completion,compeletionHide;
    private TextView txtCompeletion;
    private Button btn_17,btn_18,btn_19,btn_20;
    private String day="";
    private String time="";
    private String upDay="";
    private String upTime="";
    private static final String TAG="TakeOrderActivity";
    private Bundle bundle;
    private RelativeLayout relativeLayoutHide,relativeLayout;

    private DateModel dateModel;

//take_card

    private Animation shake;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_order);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getSupportActionBar()!=null) {
            getSupportActionBar().setTitle("Delivery time");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TakeOrderActivity.this, BuySthActivity.class);
                startActivity(intent);
            }
        });

        cardAfter=(CardView)findViewById(R.id.card_after);
        cardTomorrow=(CardView)findViewById(R.id.card_tomorrow);
        cardTime=(CardView)findViewById(R.id.show_time_card);
        btn_17=(Button) findViewById(R.id.btn_17);
        btn_18=(Button)findViewById(R.id.btn_18);
        btn_19=(Button)findViewById(R.id.btn_19);
        btn_20=(Button)findViewById(R.id.btn_20);

        completion=(CardView) findViewById(R.id.take_card);
        txtCompeletion=(TextView)findViewById(R.id.txt_compeletion);
        compeletionHide=(CardView)findViewById(R.id.take_card_hide);
        relativeLayoutHide=(RelativeLayout)findViewById(R.id.rl_compeletion_hide);
        relativeLayout=(RelativeLayout)findViewById(R.id.rl_compeletion);

        cardAfter.setOnClickListener(this);
        cardTomorrow.setOnClickListener(this);


        btn_20.setOnClickListener(this);
        btn_19.setOnClickListener(this);
        btn_18.setOnClickListener(this);
        btn_17.setOnClickListener(this);
        completion.setOnClickListener(this);

        bundle=getIntent().getExtras();
        if (bundle!=null){

           // txtCompeletion.setText("FACTOR");
            relativeLayout.setVisibility(View.GONE);
            relativeLayoutHide.setVisibility(View.VISIBLE);

            compeletionHide.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (upDay.equals("") && upTime.equals("")) {

                        Toast.makeText(v.getContext(),"U must pick a day N time",Toast.LENGTH_SHORT).show();
                    }
                    else {

                        initUpdateIntent();
                       // String intentT=getIntent().getStringExtra("total");

                        Intent intent = new Intent(TakeOrderActivity.this, FactorActivity.class);
                       // intent.putExtra("total",intentT);
                        startActivity(intent);
                    }
                }
            });

        }




        shake = AnimationUtils.loadAnimation(this, R.anim.shake);

       }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.take_card:

                if (day.equals("") && time.equals("")) {

                    Toast.makeText(this,"U must pick a day N time",Toast.LENGTH_SHORT).show();
                }
                else if(!day.equals("") && !time.equals("")) {

                    ContentValues contentValues = new ContentValues();
                    contentValues.put("ShopDay", day);
                    contentValues.put("ShopTime", time);


                    DatabaseHelper mysqlite = new DatabaseHelper(getApplicationContext());
                    Boolean b = mysqlite.insertDateInto(contentValues);
                    mysqlite.close();

                    if (b) {
                        // updateCardView();
                        Log.d(TAG,contentValues.toString());

                    } else {

                        Log.d(TAG,contentValues.toString());
                    }

                    Intent intent=new Intent(TakeOrderActivity.this,CompleteActivity.class);
                    startActivity(intent);

                    break;
                }

        }

        switch (v.getId()){

            case R.id.btn_17:
                btn_17.setBackgroundColor(getResources().getColor(R.color.bg_view_sms));
                btn_18.setBackgroundColor(Color.WHITE);
                btn_19.setBackgroundColor(Color.WHITE);
                btn_19.setBackgroundColor(Color.WHITE);

                time="17 to 18";
                upTime="17 to 18";


                break;

            case R.id.btn_18:
                btn_18.setBackgroundColor(getResources().getColor(R.color.bg_view_sms));
                btn_17.setBackgroundColor(Color.WHITE);
                btn_19.setBackgroundColor(Color.WHITE);
                btn_20.setBackgroundColor(Color.WHITE);

                time="18 to 19";
                upTime="18 to 19";


                break;

            case R.id.btn_19:
                btn_19.setBackgroundColor(getResources().getColor(R.color.bg_view_sms));
                btn_18.setBackgroundColor(Color.WHITE);
                btn_17.setBackgroundColor(Color.WHITE);
                btn_20.setBackgroundColor(Color.WHITE);

                time="19 to 20";
                upTime="19 to 20";


                break;

            case R.id.btn_20:
                btn_20.setBackgroundColor(getResources().getColor(R.color.bg_view_sms));
                btn_18.setBackgroundColor(Color.WHITE);
                btn_17.setBackgroundColor(Color.WHITE);
                btn_19.setBackgroundColor(Color.WHITE);

                time="20 to 21";
                upTime="20 to 21";


                break;

        }

        switch (v.getId()) {
            case R.id.card_tomorrow:

                cardTomorrow.setBackgroundColor(getResources().getColor(R.color.bg_view_sms));
                cardAfter.setBackgroundColor(Color.WHITE);
                cardTomorrow.setAnimation(shake);
                cardTime.setVisibility(View.VISIBLE);

                PersianDate persianDate=new PersianDate();
                persianDate.setShDay(persianDate.getShDay()+1);


                day= String.valueOf(persianDate.getShYear()+"/"+persianDate.getShMonth()+"/"+persianDate.getShDay());
                upDay= String.valueOf(persianDate.getShYear()+"/"+persianDate.getShMonth()+"/"+persianDate.getShDay());


                break;

            case R.id.card_after:
                cardAfter.setBackgroundColor(getResources().getColor(R.color.bg_view_sms));
                cardTomorrow.setBackgroundColor(Color.WHITE);
                cardAfter.setAnimation(shake);
                cardTime.setVisibility(View.VISIBLE);

                PersianDate persianDate1=new PersianDate();
                persianDate1.setShDay(persianDate1.getShDay()+2);


                day= String.valueOf(persianDate1.getShYear()+"/"+persianDate1.getShMonth()+"/"+persianDate1.getShDay());
                upDay= String.valueOf(persianDate1.getShYear()+"/"+persianDate1.getShMonth()+"/"+persianDate1.getShDay());

                break;

        }
    }
    private void initUpdateIntent(){

        bundle=getIntent().getExtras();
        String updateDay= bundle.getString("day_update");
        String updateTime=bundle.getString("time_update");
        String updateId=bundle.getString("id_update");




        dateModel=new DateModel();
        //dateModel.setDay();

        dateModel=new DateModel();
        dateModel.setId(Integer.parseInt(updateId));
        dateModel.setDay(upDay);
        dateModel.setTime(upTime);
        DatabaseHelper databaseHelper=new DatabaseHelper(this);
        Cursor cursor= databaseHelper.updateDate(dateModel);
        if (cursor.getCount()==0){
            Log.d(TAG,"update missed it");
        }
        else {
            Log.d(TAG,"job done:)");
            cursor.close();
        }
    }

}
