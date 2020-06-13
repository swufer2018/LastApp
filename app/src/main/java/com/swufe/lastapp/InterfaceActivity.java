package com.swufe.lastapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.jsoup.select.Elements;
import org.jsoup.nodes.Document;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;


import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class InterfaceActivity extends AppCompatActivity implements Runnable{
    Button button;
    Handler handler;
    Float price;
    Float yestPrice;
    Float open;
    String dif;
    String yearRange;
    String range;
    String balanceDate;
    private String TAG = "Interface";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interface);
        button = (Button) findViewById(R.id.button_brent);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBrent();
            }
        });
        Thread t = new Thread(this);
        t.start();
        handler=new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                if(msg.what==1){
                    Bundle bd1=(Bundle)msg.obj;
                    price = bd1.getFloat("brent_price");
                    dif = bd1.getString("brent_dif");
                    yearRange = bd1.getString("brent_yearRange");
                    yestPrice = bd1.getFloat("brent_yestPrice");
                    open = bd1.getFloat("brent_open");
                    range = bd1.getString("brent_range");
                    balanceDate = bd1.getString("brent_balanceDate");
                    SharedPreferences sharedPreferences=getSharedPreferences("mybrent", Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putFloat("brent_price",price);
                    editor.putString("brent_dif",dif);
                    editor.putString("brent_range",range);
                    editor.putFloat("brent_yestPrice",yestPrice);
                    editor.putFloat("brent_open",open);
                    editor.putString("brent_yearRange", yearRange);
                    editor.putString("brent_balanceDate",balanceDate);
                    editor.apply();
                    Toast.makeText(InterfaceActivity.this,"已更新",Toast.LENGTH_LONG).show();
                }
                super.handleMessage(msg);
            }
        };
    }
    public void run(){
        Bundle bundle;
        bundle =getBrent();
        Message msg = handler.obtainMessage(1);
        msg.obj = bundle;
        handler.sendMessage(msg);
    }
    private Bundle getBrent(){
        Bundle bundle = new Bundle();
        Document doc = null;
        try{
            doc = Jsoup.connect("https://cn.investing.com/commodities/brent-oil").get();
            Elements spans = doc.getElementsByTag("span");
            Element span1 = spans.get(88);
            Element span2 = spans.get(90);
            Element span3 = spans.get(138);
            Element span4 = spans.get(134);
            Element span5 = spans.get(72);
            Element span6 = spans.get(73);
            Element span7 = spans.get(75);
            String yestPrice = span1.text();
            String open = span2.text();
            String yearRange = span3.text();
            String balanceDate = span4.text();
            String price = span5.text();
            String different = span6.text();
            String range = span7.text();
            bundle.putFloat("brent_yestPrice", Float.parseFloat(yestPrice));
            bundle.putFloat("brent_open", Float.parseFloat(open));
            bundle.putString("brent_yearRange", yearRange);
            bundle.putString("brent_balanceDate", balanceDate);
            bundle.putFloat("brent_price", Float.parseFloat(price));
            bundle.putString("brent_dif", different);
            bundle.putString("brent_range", range);
        }catch (IOException e){
            e.printStackTrace();
        }
        return bundle;
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==1 && resultCode==2){
            Bundle bundle=data.getExtras();
            price = bundle.getFloat("brent_price");
            range = bundle.getString("brent_range");
            yearRange = bundle.getString("brent_yearRange");
            yestPrice = bundle.getFloat("brent_yestPrice");
            dif = bundle.getString("brent_dif");
            balanceDate = bundle.getString("brent_balanceDate");
            open = bundle.getFloat("brent_open");
            SharedPreferences sharedPreferences=getSharedPreferences("mybrent", Activity.MODE_PRIVATE);
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putFloat("brent_price",price);
            editor.putFloat("brent_open",open);
            editor.putFloat("brent_yestPrice",yestPrice);
            editor.putString("brent_range", range);
            editor.putString("brent_yearRange", yearRange);
            editor.putString("brent_balanceDate", balanceDate);
            editor.putString("brent_dif", dif);
            editor.commit();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private String inputStream2String(InputStream inputStream) throws IOException {
        final int bufferSize=1024;
        final char[] buffer=new char[bufferSize];
        final StringBuilder out=new StringBuilder();
        Reader in=new InputStreamReader(inputStream,"gb2312");
        for(;;){
            int rsz=in.read(buffer,0,buffer.length);
            if(rsz<0){
                break;
            }
            out.append(buffer,0,rsz);
        }
        return out.toString();
    }
    public void openBrent(){
        Intent brent = new Intent(this, BrentActivity.class);
        brent.putExtra("brent_price_key", price);
        brent.putExtra("brent_open_key", open);
        brent.putExtra("brent_yestPrice_key", yestPrice);
        brent.putExtra("brent_range_key", range);
        brent.putExtra("brent_yearRange_key", yearRange);
        brent.putExtra("brent_balanceDate_key", balanceDate);
        brent.putExtra("brent_dif_key", dif);
        startActivityForResult(brent,1);
    }
    public void openWTI(){
        Intent WTI = new Intent(this,WTIActivity.class);
        startActivityForResult(WTI,2);
    }
}
