package com.swufe.lastapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class WTIActivity extends AppCompatActivity {
    TextView priceText2;
    TextView difText2;
    TextView rangeText2;
    TextView yestPriceText2;
    TextView openText2;
    TextView yearRangeText2;
    TextView balanceDateText2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_w_t_i);
        Intent intent = getIntent();
        Float price2 = intent.getFloatExtra("wti_price_key", 0.0f);
        Float yestPrice2 = intent.getFloatExtra("wti_yestPrice_key", 0.0f);
        Float open2 = intent.getFloatExtra("wti_open_key", 0.0f);
        String range2 = intent.getStringExtra("wti_range_key");
        String yearRange2 = intent.getStringExtra("wti_yearRange_key");
        String balanceDate2 = intent.getStringExtra("wti_balanceDate_key");
        String dif2 = intent.getStringExtra("wti_dif_key");
        priceText2 = (TextView)findViewById(R.id.text_wtiPrice);
        difText2 = (TextView)findViewById(R.id.text_wtiDif);
        rangeText2 = (TextView)findViewById(R.id.text_wtiRange);
        yestPriceText2 = (TextView)findViewById(R.id.text_wtiYest);
        openText2 = (TextView)findViewById(R.id.text_wtiOpen);
        yearRangeText2 = (TextView)findViewById(R.id.text_wtiYear);
        balanceDateText2 = (TextView)findViewById(R.id.text_wtiDate);
        priceText2.setText(String.valueOf(price2));
        difText2.setText(dif2);
        rangeText2.setText(range2);
        yestPriceText2.setText("昨收：" + String.valueOf(yestPrice2));
        openText2.setText("开盘：" + String.valueOf(open2));
        yearRangeText2.setText("1年涨幅度:" + yearRange2);
        balanceDateText2.setText("结算日：" + balanceDate2);
    }
}
