package com.nikan.clickpaz.nikantest;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nikan.clickpaz.nikantest.Adapter.SliderAdapter;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private ViewPager viewPager;
    private LinearLayout linearLayout;
    private SliderAdapter sliderAdapter;
    private TextView[] mDots;
    private Button skip,back;
    private RelativeLayout relativeLayout;
    private int mCurrentPage;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    private static int SPLASH_TIME_OUT = 2000;
    private Handler handler;
    private static final int ERROR_DIALOG_REQUEST = 9001;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        relativeLayout=(RelativeLayout)findViewById(R.id.relative_color);

        skip=(Button)findViewById(R.id.skip);
        back=(Button)findViewById(R.id.back);


        viewPager=(ViewPager) findViewById(R.id.view_pager);
        linearLayout=(LinearLayout)findViewById(R.id.dots);
        sliderAdapter=new SliderAdapter(this);
        viewPager.setAdapter(sliderAdapter);
        buildDots(0);
        viewPager.addOnPageChangeListener(viewPagerChange);


        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(mCurrentPage +1);
            }
        });


            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewPager.setCurrentItem(mCurrentPage - 1);
                }
            });



    }

    private void init(){

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SmsVerificationActivity.class);
                startActivity(intent);
            }
        });
    }



    private void buildDots(int position){

        mDots=new TextView[3];
        linearLayout.removeAllViews();

        for(int i =0; i<mDots.length; i++){

            mDots[i]=new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226;"));
            mDots[i].setTextColor(getResources().getColor(R.color.white_transparent));
            mDots[i].setTextSize(35);

            linearLayout.addView(mDots[i]);
        }

        if(mDots.length>0){

            mDots[position].setTextColor(getResources().getColor(R.color.viewBg));
        }

    }
    ViewPager.OnPageChangeListener viewPagerChange=new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            buildDots(position);
            mCurrentPage=position;

            if(position==mDots.length-1){

                relativeLayout.setBackgroundColor(getResources().getColor(R.color.dark_brown));

                skip.setEnabled(true);
                back.setEnabled(true);
                skip.setText("Finish");
                back.setText("Back");
                back.setVisibility(View.VISIBLE);
                skip.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent  intent=new Intent(MainActivity.this,SmsVerificationActivity.class);
                        startActivity(intent);
                    }
                });
            }


            else if (position==0){
                relativeLayout.setBackgroundColor(getResources().getColor(R.color.blue_green));

                back.setEnabled(true);
                skip.setEnabled(true);

                back.setVisibility(View.VISIBLE);

                skip.setText("Next");
                back.setText("Skip");


            }

            else {
                relativeLayout.setBackgroundColor(getResources().getColor(R.color.dark_grey));

                skip.setText("Next");
                skip.setEnabled(true);
                back.setEnabled(true);
                back.setVisibility(View.VISIBLE);
                back.setText("Back");
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

}











