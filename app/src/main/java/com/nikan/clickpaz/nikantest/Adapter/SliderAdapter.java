package com.nikan.clickpaz.nikantest.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nikan.clickpaz.nikantest.R;

/**
 * Created by slim shady on 06/06/2018.
 */

public class SliderAdapter extends PagerAdapter {
    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter (Context context){
        this.context=context;
    }
    public  int[] slide_images ={
  /* R.drawable.ic_no_locations
            ,R.drawable.art_storm
           , R.drawable.icons_question_mark*/

  R.color.blue_green,R.color.dark_grey,R.color.dark_brown
    };

    public String[] headers={
            "Sweets","Baguette","Bread"

    };
    public String[] description={

            "Sweets is made by dissolving sugar in water or milk to form a syrup, which is boiled until it reaches the desired concentration or starts to caramelize. Candy comes in a wide variety of textures, from soft and chewy to hard and brittle. The texture of candy depends on the ingredients and the temperatures that the candy is processed at."
            ,"Baguette de tradition française\" is made from wheat flour, water, yeast, and common salt. It may contain up to 2% broad bean flour, up to 0.5% soya flour, and up to 0.3% wheat malt flour",
            " Bread recipes are stated using the baker's percentage notation. The amount of flour is denoted to be 100%, and the other ingredients are expressed as a percentage of that amount by weight. Measurement by weight is more accurate and consistent than measurement by volume, particularly for dry ingredients"
    };

    @Override
    public int getCount() {
        return headers.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==(RelativeLayout)object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=layoutInflater.inflate(R.layout.slide_view,container,false);

        ImageView slideImageView=view.findViewById(R.id.img_pager);
        TextView slideHeaderTextView=view.findViewById(R.id.txtTitle);
        TextView slideDescTextView=view.findViewById(R.id.txtDesc);

        slideImageView.setImageResource(slide_images[position]);
        slideHeaderTextView.setText(headers[position]);
        slideDescTextView.setText(description[position]);

        container.addView(view);


        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout)object);
    }
}
