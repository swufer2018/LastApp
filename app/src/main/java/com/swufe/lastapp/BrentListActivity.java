package com.swufe.lastapp;

import androidx.annotation.NonNull;

import android.app.ListActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BrentListActivity extends ListActivity implements Runnable{
    String []data ={"wait"};
    Handler handler;
    private String logDate = "";
    private final String DATE_SP_KEY = "lastRateDateStr";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_brend_list);
        SharedPreferences sp = getSharedPreferences("mybrent", Context.MODE_PRIVATE);
        logDate = sp.getString(DATE_SP_KEY, "");

        Thread t = new Thread(this);
        t.start();
        handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                if (msg.what == 1) {
                    List<String> list2 = (List<String>) msg.obj;
                    ListAdapter adapter = new ArrayAdapter<String>(BrentListActivity.this, android.R.layout.simple_list_item_1, list2);
                    setListAdapter(adapter);
                }
                super.handleMessage(msg);
            }
        };
    }

    @Override
    public void run() {
        List<String> retList = new ArrayList<String>();
        String curDateStr =(new SimpleDateFormat("yyyy-MM-dd")).format(new Date());
        if(curDateStr.equals(logDate)){
            BrentManager manager = new BrentManager(this);
            for(BrentItem item : manager.listAll()){
                retList.add(item.getDate() +" "+ item.getyestPrice() +" "+ item.getTodayPrice() +" "+ item.getHighest() +" "+ item.getLowest()
                        +" "+ item.getAmount() +" "+ item.getRange());
            }
        }else {
            Document doc = null;
            try{
                doc = Jsoup.connect("https://m.cn.investing.com/commodities/brent-oil-historical-data").get();
                Elements tbodys = doc.getElementsByTag("tbody");
                Element tbody = tbodys.get(0);
                Elements tds = tbody.getElementsByTag("td");
                List<BrentItem> brentList =new ArrayList<BrentItem>();
                for(int i = 0; i<tds.size(); i+=7){
                    Element td1 = tds.get(i);
                    Element td2 = tds.get(i+1);
                    Element td3 = tds.get(i+2);
                    Element td4 = tds.get(i+3);
                    Element td5 = tds.get(i+4);
                    Element td6 = tds.get(i+5);
                    Element td7 = tds.get(i+6);
                    String date = td1.text();
                    String yestPrice = td2.text();
                    String todayPrice = td3.text();
                    String highest = td4.text();
                    String lowest = td5.text();
                    String amount = td6.text();
                    String range = td7.text();
                    retList.add(date +" 收"+ yestPrice +" 开"+ todayPrice +" 最高"+ highest +" 最低"+ lowest
                            +" 成交量"+ amount +" 涨跌幅"+ range);
                    //retList.add(date +" "+ yestPrice);
                    brentList.add(new BrentItem(date, yestPrice, todayPrice, highest, lowest, amount, range));
                    //brentList.add(new BrentItem(date, yestPrice));
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        Message msg = handler.obtainMessage(1);
        msg.obj = retList;
        handler.sendMessage(msg);
    }
}
