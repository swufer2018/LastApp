package com.swufe.lastapp;

public class BrentItem {
    private int id;
    private String date;
    private String yestPrice;
    private String todayPrice;
    private String highest;
    private String lowest;
    private String amount;
    private String range;

    public BrentItem() {
        this.date = "";
        this.yestPrice = "";
        this.todayPrice = "";
        this.highest = "";
        this.lowest = "";
        this.amount = "";
        this.range = "";
    }

    public BrentItem(String date, String yestPrice, String todayPrice, String highest, String lowest, String amount, String range) {
        this.date = date;
        this.yestPrice = yestPrice;
        this.todayPrice = todayPrice;
        this.highest = highest;
        this.lowest = lowest;
        this.amount = amount;
        this.range = range;
    }
//    public BrentItem(String date, String yestPrice) {
//        this.date = date;
//        this.yestPrice = yestPrice;
//
//    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getyestPrice() {
        return yestPrice;
    }

    public void setYestPrice(String yestPrice) {
        this.yestPrice = yestPrice;
    }

    public String getTodayPrice() {
        return todayPrice;
    }

    public void setTodayPrice(String todayPrice) {
        this.todayPrice = todayPrice;
    }

    public String getHighest() {
        return highest;
    }

    public void setHighest(String highest) {
        this.highest = highest;
    }

    public String getLowest() {
        return lowest;
    }

    public void setLowest(String lowest) {
        this.lowest = lowest;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

}


