package com.nikan.clickpaz.nikantest.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nikan.clickpaz.nikantest.R;

import java.util.ArrayList;

/**
 * Created by slim shady on 09/16/2018.
 */

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.ViewHolder>  {

    private Context context;
    private ArrayList<String> title;
    private ArrayList<String> description;
    private ArrayList<Integer> images;

    public LocationAdapter(Context context, ArrayList<String> title, ArrayList<String> description, ArrayList<Integer> images) {
        this.context = context;
        this.title = title;
        this.description = description;
        this.images = images;
    }

    @NonNull
    @Override
    public LocationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.locaition_list, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationAdapter.ViewHolder holder, int position) {


        final LocationAdapter.ViewHolder viewHolder = (LocationAdapter.ViewHolder) holder;


        holder.title.setText(title.get(position));
        holder.description.setText(description.get(position));
        holder.location.setImageResource(images.get(position));

    }

    private  void initGlide () {

        int[] image={R.mipmap.ic_work_office,R.mipmap.ic_home,R.mipmap.ic_university,R.mipmap.ic_location};

    }

    @Override
    public int getItemCount() {
        return title.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView title,description;
        private ImageView location;
        public ViewHolder(View itemView) {
            super(itemView);

            title=(TextView)itemView.findViewById(R.id.title_location);
            description=(TextView)itemView.findViewById(R.id.description_location);
            location=(ImageView)itemView.findViewById(R.id.img_location);

        }
    }
}
