package com.nikan.clickpaz.nikantest.Adapter;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.nikan.clickpaz.nikantest.DatabaseModel.ShopModel;
import com.nikan.clickpaz.nikantest.R;
import com.nikan.clickpaz.nikantest.Sqlite.DatabaseHelper;

import java.util.ArrayList;

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.MyViewHolder>  {
    private Context context;
    private ArrayList<ShopModel> shopModelList;
    private static final String TAG="CartListAdapter";
   // private List<String> items;
    private  String numbers="";
    private int number;
    private int calculate=0;
    private String id="";
    private String idm="";
    private String cal;
    private String title="";
    private int idP;
    private ShopModel shopModel;
    private DatabaseHelper databaseHelper;
    private int UUID;


    //private ItemClickListener mCallback;


    public CartListAdapter(Context context, ArrayList<ShopModel> shopModelList) {
        this.context = context;
        this.shopModelList = shopModelList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder  {
        public TextView title, description, price,makeBy,itemNumber;
        public ImageView thumbnail;
        public Button plus,minus;
        public RelativeLayout parentLayout;

        public int price_per_item,price_all_item;
        //public RelativeLayout viewBackground, viewForeground;

        public MyViewHolder(View view) {
            super(view);
            parentLayout = itemView.findViewById(R.id.parent_layout);

            title=(TextView)view.findViewById(R.id.txt_title);
            makeBy=(TextView)view.findViewById(R.id.txt_make);
            description=(TextView)view.findViewById(R.id.description_buy);
            price=(TextView)view.findViewById(R.id.price_all);
            itemNumber=(TextView)view.findViewById(R.id.item_number);

            thumbnail=(ImageView)view.findViewById(R.id.img_product);

            plus=(Button)view.findViewById(R.id.btn_plus);
            minus=(Button)view.findViewById(R.id.btn_minus);

        }

    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_list_item, parent, false);

        //itemView.setOnClickListener(this);

        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        final ShopModel item = shopModelList.get(position);
        holder.title.setText(item.getTitle());
        holder.description.setText(item.getDescription());
        holder.makeBy.setText(item.getMakeBy());
        holder.price.setText(item.getCost());
        holder.itemNumber.setText(item.getNumber());

       /* holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clicked on: " + shopModelList.get(position));

                Toast.makeText(context, shopModelList.get(position), Toast.LENGTH_SHORT).show();


            }
        });*/

        final MyViewHolder myViewHolder = (MyViewHolder) holder;

        myViewHolder.plus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    String titleP = "";
                    int idP = 0;

                  myViewHolder.price_per_item = Integer.parseInt(myViewHolder.itemNumber.getText().toString());
                  myViewHolder.price_per_item = myViewHolder.price_per_item + 1;
                  numbers = String.valueOf(myViewHolder.price_per_item);
                  number=myViewHolder.price_per_item;
                  myViewHolder.itemNumber.setText(numbers);

                        cal = item.getCost();

                        calculate += Integer.valueOf(cal) ;

                        id+=String.valueOf(item.getId())+",";
                        new Handler().post(new Runnable() {
                                               @Override
                                               public void run() {
                                                   shopModel = new ShopModel();
                                                   shopModel.setId((item.getId()));
                                                   shopModel.setNumber(numbers);
                                                   databaseHelper = new DatabaseHelper(context);
                                                   //shopModelList.add(shopModel);
                                                   Cursor cursor = databaseHelper.updateNumber(shopModel);
                                                   if (cursor.getCount() == 0) {

                                                       //Toast.makeText(context, "its ok", Toast.LENGTH_SHORT).show();

                                                       // Code here will run in UI thread
                                                       // notifyDataSetChanged();
                                                       databaseHelper.close();

                                                   }
                                               }
                            });


                Intent intent = new Intent("custom-message");

                /*title+=item.getTitle()+",";
                intent.putExtra("title",title);*/
                intent.putExtra("id",id);

                intent.putExtra("number",numbers);
                intent.putExtra("item", String.valueOf(calculate));

                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

            }
        });
        myViewHolder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (myViewHolder.price_per_item==0){
                    myViewHolder.itemNumber.setText("0");
                    Toast.makeText(context,":)",Toast.LENGTH_SHORT).show();

                }
                else {

                    myViewHolder.price_per_item = Integer.parseInt(myViewHolder.itemNumber.getText().toString());
                    myViewHolder.price_per_item = myViewHolder.price_per_item - 1;
                    myViewHolder.itemNumber.setText(String.valueOf(myViewHolder.price_per_item));
                    numbers = String.valueOf(myViewHolder.price_per_item);

                    String cal = item.getCost();
                    calculate -= Integer.valueOf(cal);

                    idm += String.valueOf(item.getId()) + ",";
                    new Handler().post(new Runnable() {
                                           @Override
                                           public void run() {
                                               shopModel = new ShopModel();
                                               shopModel.setId(item.getId());
                                               shopModel.setNumber(numbers);
                                               databaseHelper = new DatabaseHelper(context);
                                               Cursor cursor = databaseHelper.updateNumber(shopModel);
                                               if (cursor.getCount() == 0) {
                                                  // Toast.makeText(context, "its ok", Toast.LENGTH_SHORT).show();

                                                   // Code here will run in UI thread
                                                   // notifyDataSetChanged();
                                                   databaseHelper.close();

                                               }
                                           }
                        });


                  /*  ContentValues contentValues=new ContentValues();
                    contentValues.put("ShopStatus","open");

                   DatabaseHelper dbHelper=new DatabaseHelper(context);
                   dbHelper.insertStatus(contentValues);*/


                     databaseHelper.close();
                    Intent intent1 = new Intent("custom-message-minus");
                    intent1.putExtra("idm", String.valueOf(idm));

                    //Intent intent = new Intent("custom-message");
                    intent1.putExtra("number", numbers);
                    intent1.putExtra("item", String.valueOf(calculate));
                    // LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent1);

                }

            }
        });

       /* if (!(myViewHolder.price_per_item ==0)){

            Log.d(TAG,"4 test");
        }*/

      // databaseHelper.close();

        Glide.with(context)
                .load(R.drawable.baguette)
                .into(holder.thumbnail);
    }

    @Override
    public int getItemCount() {
        return shopModelList.size();

    }


    public void removeItem(int position) {
       // cartList.remove(position);
        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position);
    }

    public void restoreItem(ShopModel item, int position) {
       // cartList.add(position, item);
        // notify item added by position
        notifyItemInserted(position);
    }



}
