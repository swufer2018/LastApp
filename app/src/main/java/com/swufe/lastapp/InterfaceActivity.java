package com.swufe.lastapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
    Button button1;
    Button button2;
    Button button3;
    Handler handler;
    Float price;
    Float yestPrice;
    Float open;
    String dif;
    String yearRange;
    String range;
    String balanceDate;
    Float price2;
    Float yestPrice2;
    Float open2;
    String dif2;
    String yearRange2;
    String range2;
    String balanceDate2;
    private String TAG = "Interface";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interface);
        button1 = (Button)findViewById(R.id.button_brent);
        button2 = (Button)findViewById(R.id.button_wti);
        button3 = (Button)findViewById(R.id.button_shoutdown);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBrent();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWTI();
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(InterfaceActivity.this);
                builder.setTitle("提示").setMessage("请确认是否关闭").setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).setNegativeButton("否",null);
                builder.create().show();
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
        bundle = getBrent();
        Message msg = handler.obtainMessage(1);
        msg.obj = bundle;
        handler.sendMessage(msg);
    }
    public static Bundle getBrent(){
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
    private Bundle getWTI(){
        Bundle bundle = new Bundle();
        Document doc = null;
        try{
            doc = Jsoup.connect("https://cn.investing.com/commodities/crude-oil").get();
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
        }else if(requestCode==2&&resultCode==3){
            Bundle bundle=data.getExtras();
            price2 = bundle.getFloat("wti_price");
            range2 = bundle.getString("wti_range");
            yearRange2 = bundle.getString("wti_yearRange");
            yestPrice2 = bundle.getFloat("wti_yestPrice");
            dif2 = bundle.getString("wti_dif");
            balanceDate2 = bundle.getString("wti_balanceDate");
            open2 = bundle.getFloat("wti_open");
            SharedPreferences sharedPreferences=getSharedPreferences("mywti", Activity.MODE_PRIVATE);
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putFloat("wti_price",price2);
            editor.putFloat("wti_open",open2);
            editor.putFloat("wti_yestPrice",yestPrice2);
            editor.putString("wti_range", range2);
            editor.putString("wti_yearRange", yearRange2);
            editor.putString("wti_balanceDate", balanceDate2);
            editor.putString("wti_dif", dif2);
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
        Intent wti = new Intent(this,MainActivity.class);
//        wti.putExtra("wti_price_key", price2);
//        wti.putExtra("wti_open_key", open2);
//        wti.putExtra("wti_yestPrice_key", yestPrice2);
//        wti.putExtra("wti_range_key", range2);
//        wti.putExtra("wti_yearRange_key", yearRange2);
//        wti.putExtra("wti_balanceDate_key", balanceDate2);
//        wti.putExtra("wti_dif_key", dif2);
        startActivityForResult(wti,2);
    }
}
