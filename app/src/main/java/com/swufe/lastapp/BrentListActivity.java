package com.swufe.lastapp;

import androidx.annotation.NonNull;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BrentListActivity extends ListActivity implements Runnable, AdapterView.OnItemClickListener{
    String[] data = {"wait"};
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_brend_list);
        SharedPreferences sp = getSharedPreferences("mybrent", Context.MODE_PRIVATE);

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
        getListView().setOnItemClickListener(this);
    }

    @Override
    public void run() {
        List<HashMap<String, String>> retList = new ArrayList<>();
        Document doc = null;
        try {
            doc = Jsoup.connect("https://m.cn.investing.com/commodities/brent-oil-historical-data").get();
            Elements tbodys = doc.getElementsByTag("tbody");
            Element tbody = tbodys.get(0);
            Elements tds = tbody.getElementsByTag("td");
            for (int i = 7; i < tds.size(); i += 7) {
                Element td1 = tds.get(i);
                Element td2 = tds.get(i + 1);
                Element td3 = tds.get(i + 2);
                Element td4 = tds.get(i + 3);
                Element td5 = tds.get(i + 4);
                Element td6 = tds.get(i + 5);
                Element td7 = tds.get(i + 6);
                String date = td1.text();
                String yestPrice = td2.text();
                String todayPrice = td3.text();
                String highest = td4.text();
                String lowest = td5.text();
                String amount = td6.text();
                String range = td7.text();
                HashMap<String,String> map = new HashMap<String, String>();
                map.put("日期", date);
                map.put("收盘", yestPrice);
                map.put("开盘", todayPrice);
                map.put("高", highest);
                map.put("低", lowest);
                map.put("成交量", amount);
                map.put("涨跌幅", range);
               //retList.add(date + " 收" + yestPrice + " 开" + todayPrice + " 最高" + highest + " 最低" + lowest
                       // + " 成交量" + amount + " 涨跌幅" + range);
                retList.add(map);

                //retList.add(date +" "+ yestPrice);
                //brentList.add(date, yestPrice, todayPrice, highest, lowest, amount, range);
                //brentList.add(new BrentItem(date, yestPrice));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


         Message msg = handler.obtainMessage(1);
         msg.obj =retList;
         handler.sendMessage(msg);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        HashMap<String,String> map = (HashMap<String,String>) getListView().getItemAtPosition(position);
        String dateStr = map.get("日期");
        String yestStr = map.get("收盘");
        String openStr = map.get("开盘");
        String highStr = map.get("高");
        String lowStr = map.get("低");
        String amountStr = map.get("成交量");
        String rangeStr = map.get("涨跌幅");
        Intent history = new Intent(this,HistoryActivity.class);
        history.putExtra("date", dateStr);
        history.putExtra("yest", yestStr);
        history.putExtra("open", openStr);
        history.putExtra("high", highStr);
        history.putExtra("low", lowStr);
        history.putExtra("amount", amountStr);
        history.putExtra("range", rangeStr);
        startActivity(history);
    }

}
