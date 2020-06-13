package com.swufe.lastapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class MyListActivity extends ListActivity implements Runnable{
    Handler handler;
    private List<HashMap<String,String>> listItems;
    private SimpleAdapter listItemAdapter;
    private String TAG = "mylist";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initListView();
        //setContentView(R.layout.activity_my_list);
        this.setListAdapter(listItemAdapter);
        Thread t =new Thread(this);
        t.start();
        handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                if (msg.what == 1) {
                    listItems = (List<HashMap<String, String>>) msg.obj;
                    listItemAdapter = new SimpleAdapter(MyListActivity.this, listItems,
                            R.layout.activity_my_list,
                            new String[] { "ItemDate", "ItemYestPrice" },
                            new int[] {R.id.item_date, R.id.item_yestPrice }
                    );
                    setListAdapter(listItemAdapter);
                }
                super.handleMessage(msg);
            }
        };

    }
    private void initListView(){
        listItems = new ArrayList<HashMap<String, String>>();
        for(int i = 0; i < 7; i++){
            HashMap<String,String> map = new HashMap<String, String>();
            map.put("ItemDate", "Date: " +i);
            map.put("ItemYestPrice", "yestPrice" +i);
//            map.put("ItemTodayPrice", "todayPrice" +i);
//            map.put("ItemHighest", "highest" +i);
//            map.put("ItemLowest", "lowest" +i);
//            map.put("ItemAmount", "amount" +i);
//            map.put("ItemRange", "range" +i);
            listItems.add(map);
        }
        listItemAdapter = new SimpleAdapter(this, listItems,
                R.layout.activity_my_list,
//                new String[] { "ItemDate", "ItemYestPrice", "ItemTodayPrice", "ItemHighest", "ItemLowest", "ItemAmount", "ItemRange" },
                new String[] { "ItemDate", "ItemYestPrice",},
//                new int[] {R.id.item_date, R.id.item_yestPrice, R.id.item_todayPrice, R.id.item_highest, R.id.item_lowest, R.id.item_amount, R.id.item_range }
                new int[] {R.id.item_date, R.id.item_yestPrice}
        );
    }
    public void run() {
        List<HashMap<String,String>> retList = new ArrayList<HashMap<String, String>>();
        Document doc = null;
        try{
            doc = Jsoup.connect("https://m.cn.investing.com/commodities/brent-oil-historical-data").get();
            Elements tbodys = doc.getElementsByTag("tbody");
            Element tbody = tbodys.get(0);
            Elements tds = tbody.getElementsByTag("td");
            for(int i = 7; i<tds.size(); i+=7){
                Element td1 = tds.get(i);
                Element td2 = tds.get(i+1);
//                Element td3 = tds.get(i+2);
//                Element td4 = tds.get(i+3);
//                Element td5 = tds.get(i+4);
//                Element td6 = tds.get(i+5);
//                Element td7 = tds.get(i+6);
                String date = td1.text();
                String yestPrice = td2.text();
//                String todayPrice = td3.text();
//                String highest = td4.text();
//                String lowest = td5.text();
//                String amount = td6.text();
//                String range = td7.text();
                HashMap<String,String> map = new HashMap<String, String>();
                map.put("ItemDate", date);
                map.put("ItemYestPrice", yestPrice);
//                map.put("ItemTodayPrice", todayPrice);
//                map.put("ItemHighest", highest);
//                map.put("ItemLowest", lowest);
//                map.put("ItemAmount", amount);
//                map.put("ItemRange", range);
//                retList.add(map);
            }
        }catch (IOException e){
            e.printStackTrace();
        }

        Message msg = handler.obtainMessage(1);
        msg.obj = retList;
        handler.sendMessage(msg);
    }
}
