package com.nikan.clickpaz.nikantest;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.nikan.clickpaz.nikantest.Adapter.CartListAdapter;
import com.nikan.clickpaz.nikantest.Adapter.ItemClickListener;
import com.nikan.clickpaz.nikantest.DatabaseModel.ShopModel;
import com.nikan.clickpaz.nikantest.Sqlite.DatabaseHelper;

import java.util.ArrayList;

public class BuySthActivity extends AppCompatActivity  {

    private static final String TAG = "BuySthActivity";

    private ArrayList<ShopModel> shopModelArray=new ArrayList<>();
    private CardView order;
    private TextView all_cost;
    DatabaseHelper mysqlite;

    private ShopModel shopModel;

   /* private List<String> idJustPlus;
    private List<String> idList;
    private List<String> idListM;
    private List<String> numberJustPlus;
    private List<String> numberItemMinus;
    private List<String> idItemMinus;
    private List<String> zero=new ArrayList<>();

    private ArrayList<String> duplicateList;
    private ArrayList<String> uniqueList;*/

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;

    private String cost;
    private String number;
    private String title;
    private String id="";
    private String idm;
    private String ItemName;
    private ItemClickListener itemClickListener;
    private int cal;

    private Boolean b;
   /* private String idPlus="";
    private String idMinus="";
    private String sIdPlus="";
    private String sIdMinus="";
    private HashMap<String, String> plusHash;
    private HashMap<String, String> minusHash;
    private HashMap<String,String> zeroMap;

    private  Set<String> p;
    private  Set<String> m;
    private Set<String> delete;*/




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_sth);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Our Products");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BuySthActivity.this, MenuActivity.class);
                startActivity(intent);
            }
        });

        order = (CardView) findViewById(R.id.order_card);
        all_cost = (TextView) findViewById(R.id.price_txt);

        initRecyclerView();

        updateCardView();

       /* DatabaseHelper databaseHelper=new DatabaseHelper(this);
        databaseHelper.deleteAllShop();*/

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("custom-message"));

        LocalBroadcastManager.getInstance(this).registerReceiver(messageReceiver,
                new IntentFilter("custom-message-minus"));




        order.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!all_cost.getText().toString().equals("0")) {
                       /* Set<String> unique = new HashSet<String>(idList);
                        for (String key : unique) {
                            idPlus += String.valueOf(Collections.frequency(idList, key))+",";
                            sIdPlus += key+",";

                        }


                        if (idListM != null) {
                            Set<String> unique1 = new HashSet<String>(idListM);
                            for (String key : unique1) {
                                idMinus += String.valueOf(Collections.frequency(idListM, key))+",";
                                sIdMinus += key+",";

                            }


                            numberJustPlus=new ArrayList<>(Arrays.asList(idPlus.split(",")));
                            idJustPlus=new ArrayList<>(Arrays.asList(sIdPlus.split(",")));



                            numberItemMinus=new ArrayList<>(Arrays.asList(idMinus.split(",")));
                            idItemMinus=new ArrayList<>(Arrays.asList(sIdMinus.split(",")));



                            duplicateList = new ArrayList<String>(idJustPlus);
                            duplicateList.retainAll(idItemMinus);


                            uniqueList = new ArrayList<String>(idJustPlus);
                            uniqueList.removeAll(idItemMinus);


                             plusHash = new HashMap<String, String>();
                            plusHash.put(idJustPlus.toString().replaceAll("[\\[\\](){}]",""),numberJustPlus.toString().replaceAll("[\\[\\](){}]",""));



                            Log.d(TAG,plusHash.keySet()+" : "+plusHash.values());


                            minusHash = new HashMap<String, String>();
                            minusHash.put(duplicateList.toString().replaceAll("[\\[\\](){}]",""),numberItemMinus.toString().replaceAll("[\\[\\](){}]",""));


                            Log.d(TAG,minusHash.keySet()+" : "+minusHash.values());


                            zeroMap=new HashMap<String, String>();
                            zeroMap.put(uniqueList.toString().replaceAll("[\\[\\](){}]",""),uniqueList.toString().replaceAll("[\\[\\](){}]",""));


                            delete=new HashSet<String>(zeroMap.keySet());


                            p = new HashSet<String>(plusHash.keySet());
                            m = new HashSet<String>(minusHash.keySet());


                            String plus= plusHash.get(p.toString().replaceAll("[\\[\\](){}]","").replaceAll("[\\[\\](){}]",""));
                            String minus=minusHash.get(m.toString().replaceAll("[\\[\\](){}]","").replaceAll("[\\[\\](){}]",""));

                            String[] plusArray=plus.split(", ");
                            String[] minusArray=minus.split(", ");


                           Log.d(TAG,Arrays.toString(plusArray)+" : "+Arrays.toString(minusArray));


                            int[] results = new int[plusArray.length];
                            int[] results1=new int[minusArray.length];
                            int[] res=new int[minusArray.length];

                            for (int i = 0; i < plusArray.length; i++) {
                                try {


                                        results[i] = Integer.parseInt(plusArray[i]);
                                        results1[i] = Integer.parseInt(minusArray[i]);

                                        res[i] = results[i] - results1[i];


                                } catch (ArrayIndexOutOfBoundsException nfe) {
                                    nfe.getMessage();
                                }

                            }

                            Log.d(TAG,Arrays.toString(results)+" : "+ Arrays.toString(results1)+" = "+Arrays.toString(res));


                        }
                        else {


                            numberJustPlus=new ArrayList<>(Arrays.asList(idPlus.split(",")));
                            idJustPlus=new ArrayList<>(Arrays.asList(sIdPlus.split(",")));

                            //init db right here
                            Log.d(TAG,idJustPlus.size()+" "+numberJustPlus.size());

                        }*/




                        Intent intent=new Intent(BuySthActivity.this,TakeOrderActivity.class);
                        // intent.putExtra("total",all_cost.getText().toString());
                        /*intent.putExtra("total",all_cost.getText().toString());
                        LocalBroadcastManager.getInstance(v.getContext()).sendBroadcast(intent);*/
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(getApplicationContext(),":/",Toast.LENGTH_SHORT).show();
                    }
                }
            });


    }



    private void initRecyclerView() {

        //shopModelArray.addAll(shopModel.setNumber("0"));


        new Handler().post(new Runnable()
            {

                @Override
                public void run() {
                    ContentValues contentValues = new ContentValues();


                    contentValues.put("ShopTitle", "title");
                    contentValues.put("ShopDescription", "description");
                    contentValues.put("ShopMakeBy", "nikan");
                    contentValues.put("ShopCost", "10");
                    contentValues.put("ShopNumber","0");


                    mysqlite = new DatabaseHelper(getApplicationContext());
                    b = mysqlite.insertInto(contentValues);


                    mysqlite.close();

                    if (b) {
                        updateCardView();
                        Log.d(TAG,contentValues.toString());

                    } else {

                        Log.d(TAG,contentValues.toString());
                    }
                }
            });


        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new CartListAdapter(this, shopModelArray);


        recyclerView.setAdapter(adapter);

    }

    private void updateCardView() {
        mysqlite = new DatabaseHelper(getApplicationContext());

        Cursor result = mysqlite.selectAllShop();
        if (result.getCount() == 0) {
            shopModelArray.clear();
            adapter.notifyDataSetChanged();
            Toast.makeText(getApplicationContext(), "No Tasks", Toast.LENGTH_SHORT).show();
        } else {
            shopModelArray.clear();
            adapter.notifyDataSetChanged();
            while (result.moveToNext()) {
                ShopModel tddObj = new ShopModel();
                tddObj.setId(result.getInt(0));
                tddObj.setTitle(result.getString(1));
                tddObj.setDescription(result.getString(3));
                tddObj.setMakeBy(result.getString(4));
                tddObj.setCost(result.getString(6));
                tddObj.setNumber(result.getString(7));

                shopModelArray.add(tddObj);
            }
            adapter.notifyDataSetChanged();
        }
    }


    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent


            ItemName = intent.getStringExtra("item");
            cost=intent.getStringExtra("cost");
            number=intent.getStringExtra("number");
           // title=intent.getStringExtra("title");
            id=intent.getStringExtra("id");

            cal= Integer.parseInt(ItemName);

            all_cost.setText(String.valueOf(cal));

            initDB(number);
           // Log.d(TAG,number+" "+id);
            //idList=new ArrayList<String>(Arrays.asList(id.split(",")));

        }
    };


    public BroadcastReceiver messageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            idm =intent.getStringExtra("idm");
            number=intent.getStringExtra("number");
            cost=intent.getStringExtra("cost");
            ItemName = intent.getStringExtra("item");

            cal= Integer.parseInt(ItemName);
            all_cost.setText(String.valueOf(cal));

            initDB(number);

            //idListM=new ArrayList<String>(Arrays.asList(idm.split(",")));


            //Log.d(TAG,number+" "+idm);

        }
    };

   /* private void initStatus(String[] idArray) {
        this.id=idArray;
    }
*/
    private void initDB( String number) {

        this.number=number;

    }

    @Override
    protected void onPause() {
        super.onPause();
        initDB(number);

    }

    @Override
    protected void onResume() {
        super.onResume();
        initDB(number);

        // initItemPrice(price_all_item);
    }

    @Override
    protected void onDestroy() {
        // Unregister since the activity is about to be closed.
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(messageReceiver);

        super.onDestroy();
    }


}
