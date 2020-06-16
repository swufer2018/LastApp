package com.swufe.lastapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SecondFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SecondFragment extends ListFragment implements Runnable{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Handler handler;
    private ListView listView;
    public SecondFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SecondFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SecondFragment newInstance(String param1, String param2) {
        SecondFragment fragment = new SecondFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        Thread t = new Thread();
        t.start();
        handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg2) {
                if (msg2.what == 2) {
                    List<String> list2 = (List<String>) msg2.obj;
                    ListAdapter adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, list2);
                    setListAdapter(adapter);
                }
                super.handleMessage(msg2);
            }
        };
    }
    public void run() {
        List<String> retList = new ArrayList<String>();
        Document doc = null;
        try{
            doc = Jsoup.connect("https://cn.investing.com/commodities/crude-oil-historical-data").get();
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

        Message msg2 = handler.obtainMessage(2);
        msg2.obj = retList;
        handler.sendMessage(msg2);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_second, container, false);
    }
}
