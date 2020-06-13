package com.swufe.lastapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class BrentActivity extends AppCompatActivity {
    TextView priceText;
    TextView difText;
    TextView rangeText;
    TextView yestPriceText;
    TextView openText;
    TextView yearRangeText;
    TextView balanceDateText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brent);
        Intent intent =getIntent();
        Float price = intent.getFloatExtra("brent_price_key", 0.0f);
        Float yestPrice = intent.getFloatExtra("brent_yestPrice_key", 0.0f);
        Float open = intent.getFloatExtra("brent_open_key", 0.0f);
        String range = intent.getStringExtra("brent_range_key");
        String yearRange = intent.getStringExtra("brent_yearRange_key");
        String balanceDate = intent.getStringExtra("brent_balanceDate_key");
        String dif = intent.getStringExtra("brent_dif_key");
        priceText = (TextView)findViewById(R.id.text_brentPrice);
        difText = (TextView)findViewById(R.id.text_dif);
        rangeText = (TextView)findViewById(R.id.text_range);
        yestPriceText = (TextView)findViewById(R.id.text_yesterday);
        openText = (TextView)findViewById(R.id.text_open);
        yearRangeText = (TextView)findViewById(R.id.text_yearRange);
        balanceDateText = (TextView)findViewById(R.id.text_balanceDate);
        priceText.setText(String.valueOf(price));
        difText.setText(dif);
        rangeText.setText(range);
        yestPriceText.setText("昨收：" + String.valueOf(yestPrice));
        openText.setText("开盘：" + String.valueOf(open));
        yearRangeText.setText("1年涨幅度:" + yearRange);
        balanceDateText.setText("结算日：" + balanceDate);
    }
    public void save(View btn){
        float newPrice=Float.parseFloat(priceText.getText().toString());
        float newOpen=Float.parseFloat(openText.getText().toString());
        float newYestPrice=Float.parseFloat(yestPriceText.getText().toString());
        String newDif = difText.getText().toString();
        String newYearRange = yearRangeText.getText().toString();
        String newRange = rangeText.getText().toString();
        String newBalanceDate = balanceDateText.getText().toString();
        Intent intent=getIntent();
        Bundle bd1=new Bundle();
        bd1.putFloat("brent_price", newPrice);
        bd1.putFloat("brent_open", newOpen);
        bd1.putFloat("brent_yestPrice", newYestPrice);
        bd1.putString("brent_dif", newDif);
        bd1.putString("brent_yearRange", newYearRange);
        bd1.putString("brent.range", newRange);
        bd1.putString("brent_balanceDate", newBalanceDate);
        intent.putExtras(bd1);
        setResult(2,intent);
        finish();
    }

    public void openBrentList(){
        Intent brentList = new Intent(this,BrentListActivity.class);
        startActivity(brentList);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.menu_tendency){
            openBrentList();
        }
        return super.onOptionsItemSelected(item);
    }

}
