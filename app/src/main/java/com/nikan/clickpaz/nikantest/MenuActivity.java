package com.nikan.clickpaz.nikantest;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.nikan.clickpaz.nikantest.DatabaseModel.ShopModel;
import com.nikan.clickpaz.nikantest.Sqlite.DatabaseHelper;

import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    CardView sweetsCard,baguetteCard,breadCard;
    private TextView txtNumber;
    private static final String TAG="MenuActivity";
    private ArrayList<ShopModel> shopModelArray=new ArrayList<>();
    /*private PrefManager pref;
    private String number="";*/



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        breadCard=(CardView)findViewById(R.id.breads_card);

        baguetteCard=(CardView)findViewById(R.id.buguette_card);
        sweetsCard=(CardView)findViewById(R.id.sweets_card);
        txtNumber=(TextView)findViewById(R.id.txt_number);

       /* pref = new PrefManager(this);
       number= pref.getMobileNumber();


           txtNumber.setText(number);*/


        initDb();



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        sweetsCard.setOnClickListener(this);
        baguetteCard.setOnClickListener(this);
        breadCard.setOnClickListener(this);




    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void initDb(){


        new Handler().post(new Runnable() {

            @Override
            public void run() {
                final ShopModel shopModel=new ShopModel();
                shopModel.setNumber("0");
                DatabaseHelper databaseHelper=new DatabaseHelper(getApplicationContext());

                Cursor cursor=databaseHelper.updateZero(shopModel);
                if (cursor.getCount()==0){
                    shopModelArray.clear();
                    Toast.makeText(getApplicationContext(), "update database", Toast.LENGTH_SHORT).show();

                }
                else {
                    Toast.makeText(getApplicationContext(), "database need update", Toast.LENGTH_SHORT).show();

                }
                databaseHelper.close();

            }
        });

                }







    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


       if(id==R.id.nav_pocket){

       }
       else if(id==R.id.nav_account){

       }
       else if(id==R.id.nav_orders){

       }
       else if(id==R.id.nav_support){

       }
       else if (id==R.id.nav_share){

       }
       else if (id==R.id.nav_about){

       }
       else if (id==R.id.nav_free){

       }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.buguette_card:

                Intent intent=new Intent(MenuActivity.this,BuySthActivity.class);
                startActivity(intent);


                break;

            case R.id.breads_card:

                Intent intent3=new Intent(MenuActivity.this,BuySthActivity.class);
                startActivity(intent3);

                break;

            case R.id.sweets_card:

                Intent intent2=new Intent(MenuActivity.this,BuySthActivity.class);
                startActivity(intent2);

                break;

        }
    }
}

