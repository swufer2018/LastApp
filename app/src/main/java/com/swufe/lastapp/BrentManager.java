package com.swufe.lastapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;


public class BrentManager {
    private DBHelper dbHelper;
    private String TBNAME;
    public BrentManager(Context context){
        dbHelper = new DBHelper(context);
        TBNAME = DBHelper.TB_NAME;
    }
    public void add(BrentItem item){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("date", item.getDate());
        values.put("yestPrice", item.getyestPrice());
        values.put("todayPrice", item.getTodayPrice());
        values.put("highest", item.getHighest());
        values.put("lowest", item.getLowest());
        values.put("amount", item.getAmount());
        values.put("range", item.getRange());
        db.insert(TBNAME, null, values);
        db.close();
    }
    public void addAll(List<BrentItem> list){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        for(BrentItem item : list){
            ContentValues values = new ContentValues();
            values.put("date", item.getDate());
            values.put("yestPrice", item.getyestPrice());
            values.put("todayPrice", item.getTodayPrice());
            values.put("highest", item.getHighest());
            values.put("lowest", item.getLowest());
            values.put("amount", item.getAmount());
            values.put("range", item.getRange());
            db.insert(TBNAME, null, values);
        }
        db.close();
    }
    public void delete(int id){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(TBNAME, "ID=?", new String[]{String.valueOf(id)});
        db.close();
    }
    public void deleteAll(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(TBNAME, null, null);
        db.close();
    }
    public void update(BrentItem item){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("date", item.getDate());
        values.put("yestPrice", item.getyestPrice());
        values.put("todayPrice", item.getTodayPrice());
        values.put("highest", item.getHighest());
        values.put("lowest", item.getLowest());
        values.put("amount", item.getAmount());
        values.put("range", item.getRange());
        db.close();
    }
    public BrentItem findById(int id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TBNAME, null, "ID=?", new String[]{String.valueOf(id)}, null,
                null, null);
        BrentItem brentItem = null;
        if(cursor!=null && cursor.moveToFirst()){
            brentItem = new BrentItem();
            brentItem.setId(cursor.getInt(cursor.getColumnIndex("ID")));
            brentItem.setDate(cursor.getString(cursor.getColumnIndex("DATE")));
            brentItem.setYestPrice(cursor.getString(cursor.getColumnIndex("YESTPRICE")));
            brentItem.setTodayPrice(cursor.getString(cursor.getColumnIndex("TODAYPRICE")));
            brentItem.setHighest(cursor.getString(cursor.getColumnIndex("HIGHEST")));
            brentItem.setLowest(cursor.getString(cursor.getColumnIndex("LOWEST")));
            brentItem.setAmount(cursor.getString(cursor.getColumnIndex("AMOUNT")));
            brentItem.setRange(cursor.getString(cursor.getColumnIndex("RANGE")));
            cursor.close();
        }
        db.close();
        return brentItem;
    }
    public List<BrentItem> listAll(){
        List<BrentItem> brendList = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TBNAME, null, null, null, null, null, null);
        if(cursor!=null){
            brendList = new ArrayList<BrentItem>();
            while(cursor.moveToNext()){
                BrentItem item = new BrentItem();
                item.setId(cursor.getInt(cursor.getColumnIndex("ID")));
                item.setDate(cursor.getString(cursor.getColumnIndex("DATE")));
                item.setYestPrice(cursor.getString(cursor.getColumnIndex("YESTPRICE")));
                item.setTodayPrice(cursor.getString(cursor.getColumnIndex("TODAYPRICE")));
                item.setHighest(cursor.getString(cursor.getColumnIndex("HIGHEST")));
                item.setLowest(cursor.getString(cursor.getColumnIndex("LOWEST")));
                item.setAmount(cursor.getString(cursor.getColumnIndex("AMOUNT")));
                item.setRange(cursor.getString(cursor.getColumnIndex("RANGE")));
                brendList.add(item);
            }
            cursor.close();
        }
        db.close();
        return brendList;
    }
}

