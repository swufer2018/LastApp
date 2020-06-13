package com.swufe.lastapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyAdapter extends ArrayAdapter {
    private static final String TAG = "MyAdapter";

    public MyAdapter(@NonNull Context context, int resource, ArrayList<HashMap<String, String>> list) {
        super(context, resource, list);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View itemView = convertView;
        if (itemView == null) {
            itemView = LayoutInflater.from(getContext()).inflate(R.layout.activity_my_list, parent, false);
        }
        Map<String, String> map = (Map<String, String>) getItem(position);
        TextView date = (TextView) itemView.findViewById(R.id.item_date);
        TextView yestPrice = (TextView) itemView.findViewById(R.id.item_yestPrice);
        TextView todayPrice = (TextView) itemView.findViewById(R.id.item_todayPrice);
        TextView highest = (TextView) itemView.findViewById(R.id.item_highest);
        TextView lowest = (TextView) itemView.findViewById(R.id.item_lowest);
        TextView amount = (TextView) itemView.findViewById(R.id.item_amount);
        TextView range = (TextView) itemView.findViewById(R.id.item_range);
        date.setText("Date:" + map.get("ItemDate"));
        yestPrice.setText("yestPrice" + map.get("ItemYestPrice"));
        todayPrice.setText("todayPrice" + map.get("ItemTodayPrice"));
        highest.setText("Highest:" + map.get("ItemHighest"));
        lowest.setText("Lowhest:" + map.get("ItemLowest"));
        amount.setText("Amount:" + map.get("ItemAmount"));
        range.setText("Range:" + map.get("ItemRange"));
        return itemView;
    }
}
