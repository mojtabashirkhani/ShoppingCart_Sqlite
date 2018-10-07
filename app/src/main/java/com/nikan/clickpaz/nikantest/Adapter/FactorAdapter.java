package com.nikan.clickpaz.nikantest.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.nikan.clickpaz.nikantest.DatabaseModel.ShopModel;
import com.nikan.clickpaz.nikantest.R;

import java.util.ArrayList;

/**
 * Created by slim shady on 09/10/2018.
 */

public class FactorAdapter extends RecyclerView.Adapter<FactorAdapter.ViewHolder> {

    private Context context;
    private ArrayList<ShopModel> shopModels;

    private static String numbers;
    private int calculate=0;

    public FactorAdapter(Context context, ArrayList<ShopModel> shopModels) {
        this.context = context;
        this.shopModels = shopModels;
    }

    @NonNull
    @Override
    public FactorAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.factor_adapter, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FactorAdapter.ViewHolder holder, int position) {

        final ShopModel item = shopModels.get(position);

        holder.product.setText(item.getTitle());
        holder.cost.setText(item.getCost());
        holder.count.setText(item.getNumber());

        final ViewHolder viewHolder= (ViewHolder) holder;

        viewHolder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.price_per_item = Integer.parseInt(viewHolder.count.getText().toString());
                viewHolder.price_per_item=viewHolder.price_per_item+1;
                numbers=String.valueOf(viewHolder.price_per_item);
                viewHolder.count.setText(numbers);

                int calC=0;
                String cal = String.valueOf(Integer.parseInt(item.getCost())/Integer.parseInt(item.getNumber()));
                calculate += Integer.valueOf(cal) ;
                 calC +=Integer.valueOf(cal) ;
                viewHolder.cost.setText(String.valueOf(Integer.parseInt(viewHolder.cost.getText().toString())+calC));

                Intent intent = new Intent("send-final");
                //            intent.putExtra("quantity",Integer.parseInt(quantity.getText().toString()));
                intent.putExtra("price", String.valueOf(calculate));
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
            }
        });

        viewHolder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (viewHolder.price_per_item==0){
                    viewHolder.count.setText("0");
                    Toast.makeText(context,":)",Toast.LENGTH_SHORT).show();

                }
                else {

                    viewHolder.price_per_item = Integer.parseInt(viewHolder.count.getText().toString());
                    viewHolder.price_per_item = viewHolder.price_per_item - 1;
                    numbers = String.valueOf(viewHolder.price_per_item);
                    viewHolder.count.setText(numbers);

                    int calC = 0;

                    String cal = String.valueOf(Integer.parseInt(item.getCost()) / Integer.parseInt(item.getNumber()));
                    calculate -= Integer.valueOf(cal);
                    calC -= Integer.valueOf(cal);

                    viewHolder.cost.setText(String.valueOf(Integer.parseInt(viewHolder.cost.getText().toString()) + calC));

                    Intent intent = new Intent("send-final");
                    //            intent.putExtra("quantity",Integer.parseInt(quantity.getText().toString()));
                    intent.putExtra("price", String.valueOf(calculate));
                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                }
            }
        });


        }


    @Override
    public int getItemCount() {
        return shopModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

         TextView product,cost,plus,minus,count,sign;
        private int price_per_item;


        public ViewHolder(View itemView)
        {
            super(itemView);


            product=(TextView)itemView.findViewById(R.id.factor_title);
            cost=(TextView)itemView.findViewById(R.id.factor_cost);
            plus=(TextView)itemView.findViewById(R.id.factor_plus);
            minus=(TextView)itemView.findViewById(R.id.factor_minus);
            count=(TextView)itemView.findViewById(R.id.factor_count);
            sign=(TextView)itemView.findViewById(R.id.factor_sign);

        }
    }
}
