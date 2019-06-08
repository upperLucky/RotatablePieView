package com.upp.piechartdemo;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private PieChartView mPieChartView;
    private List<Integer> colors = new ArrayList<>();
    private List<Float> datas = new ArrayList<>();
    private Button bt1,bt2,bt3,bt4, bt5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPieChartView = findViewById(R.id.pieCharView);
        bt1 = findViewById(R.id.bt1);
        bt2 = findViewById(R.id.bt2);
        bt3 = findViewById(R.id.bt3);
        bt4 = findViewById(R.id.bt4);
        bt5 = findViewById(R.id.bt5);

        colors.add(Color.parseColor("#F34E46"));
        colors.add(Color.parseColor("#FFB030"));
        colors.add(Color.parseColor("#FFD871"));
        colors.add(Color.parseColor("#68D4B9"));
        colors.add(Color.parseColor("#3DACEC"));
        colors.add(Color.parseColor("#3C65FA"));
        colors.add(Color.parseColor("#3C65FA"));
        colors.add(Color.parseColor("#6F35E6"));
        colors.add(Color.parseColor("#9C4AE5"));
        colors.add(Color.parseColor("#D05379"));
        colors.add(Color.parseColor("#BF3915"));
        colors.add(Color.parseColor("#BF9215"));
        colors.add(Color.parseColor("#90BF15"));
        colors.add(Color.parseColor("#15BFB3"));
        colors.add(Color.parseColor("#1573BF"));

        datas.add(50f);
        datas.add(20f);
        datas.add(10f);
        datas.add(10f);
        datas.add(10f);

        mPieChartView.setData(colors, datas, 100f);

        bt1.setOnClickListener(this);
        bt2.setOnClickListener(this);
        bt3.setOnClickListener(this);
        bt4.setOnClickListener(this);
        bt5.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt1:
                mPieChartView.setIndex(0);
                break;
            case R.id.bt2:
                mPieChartView.setIndex(1);
                break;
            case R.id.bt3:
                mPieChartView.setIndex(2);
                break;
            case R.id.bt4:
                mPieChartView.setIndex(3);
                break;
            case R.id.bt5:
                mPieChartView.setIndex(4);
                break;
        }
    }
}
