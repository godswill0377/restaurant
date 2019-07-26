package com.example.winefi.activity;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.winefi.R;
import com.example.winefi.adapter.WinefiListAdapter;
import com.example.winefi.objects.WinefiData;
import com.example.winefi.utils.Constant;
import com.example.winefi.utils.DividerItemDecoration;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    ImageView ivLogo0;
    ImageView ivLogo1;
    ImageView ivLogo2;
    ImageView ivLogo3;
    ImageView ivLogo4;
    ImageView ivLogo5;
    ImageView ivLogo6;
    ImageView ivLogo7;


    RelativeLayout lout_main0;
    RelativeLayout lout_main1;
    RelativeLayout lout_main2;
    RelativeLayout lout_main3;
    RelativeLayout lout_main4;
    RelativeLayout lout_main5;
    RelativeLayout lout_main6;
    RelativeLayout lout_main7;

    int[] some_array;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    public void init() {
        TypedArray ta = getResources().obtainTypedArray(R.array.add_text_color);
        some_array = new int[ta.length()];
        for (int i = 0; i < ta.length(); i++) {
            some_array[i] = ta.getColor(i, 0);
        }
        ta.recycle();
        ivLogo0 = (ImageView) findViewById(R.id.ivLogo0);
        ivLogo1 = (ImageView) findViewById(R.id.ivLogo1);
        ivLogo2 = (ImageView) findViewById(R.id.ivLogo2);
        ivLogo3 = (ImageView) findViewById(R.id.ivLogo3);
        ivLogo4 = (ImageView) findViewById(R.id.ivLogo4);
        ivLogo5 = (ImageView) findViewById(R.id.ivLogo5);
        ivLogo6 = (ImageView) findViewById(R.id.ivLogo6);
        ivLogo7 = (ImageView) findViewById(R.id.ivLogo7);


        lout_main0 = (RelativeLayout) findViewById(R.id.lout_main0);
        lout_main1 = (RelativeLayout) findViewById(R.id.lout_main1);
        lout_main2 = (RelativeLayout) findViewById(R.id.lout_main2);
        lout_main3 = (RelativeLayout) findViewById(R.id.lout_main3);
        lout_main4 = (RelativeLayout) findViewById(R.id.lout_main4);
        lout_main5 = (RelativeLayout) findViewById(R.id.lout_main5);
        lout_main6 = (RelativeLayout) findViewById(R.id.lout_main6);
        lout_main7 = (RelativeLayout) findViewById(R.id.lout_main7);


        ivLogo0.setBackgroundResource(Constant.home_logo[0]);
        ivLogo1.setBackgroundResource(Constant.home_logo[1]);
        ivLogo2.setBackgroundResource(Constant.home_logo[2]);
        ivLogo3.setBackgroundResource(Constant.home_logo[3]);
        ivLogo4.setBackgroundResource(Constant.home_logo[4]);
        ivLogo5.setBackgroundResource(Constant.home_logo[5]);
        ivLogo6.setBackgroundResource(Constant.home_logo[6]);
        ivLogo7.setBackgroundResource(Constant.home_logo[7]);

        lout_main0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, RangeActivity.class);
                intent.putExtra("pos", 0);
                startActivityForResult(intent, 11);

            }
        });


    }
}
