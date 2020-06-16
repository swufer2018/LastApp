package com.swufe.lastapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

public class HistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        String date = getIntent().getStringExtra("date");
        String yest = getIntent().getStringExtra("yest");
        String open = getIntent().getStringExtra("open");
        String high = getIntent().getStringExtra("high");
        String low = getIntent().getStringExtra("low");
        String amount = getIntent().getStringExtra("amount");
        String range = getIntent().getStringExtra("range");
        ((TextView)findViewById(R.id.textView_date)).setText(date);
        ((TextView)findViewById(R.id.textView_close)).setText("收盘" +yest);
        ((TextView)findViewById(R.id.textView_open)).setText("开盘" +open);
        ((TextView)findViewById(R.id.textView_highest)).setText("高" +high);
        ((TextView)findViewById(R.id.textView_lowest)).setText("低" +low);
        ((TextView)findViewById(R.id.textView_amount)).setText("成交量" +amount);
        if(range.contains("-")){
            ((TextView)findViewById(R.id.textView_range)).setTextColor(Color.GREEN);
        }else{
            ((TextView)findViewById(R.id.textView_range)).setTextColor(Color.RED);
        }
        ((TextView)findViewById(R.id.textView_range)).setText("涨跌幅" +range);
    }
}
