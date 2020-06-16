package com.swufe.lastapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class BrentActivity extends AppCompatActivity  {
    TextView priceText;
    TextView difText;
    TextView rangeText;
    TextView yestPriceText;
    TextView openText;
    TextView yearRangeText;
    TextView balanceDateText;
    private SensorManager sensorManager;
    private Vibrator vibrator;
    Handler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brent);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

    }



    protected void onResume() {
        super.onResume();
        if (sensorManager != null) {
            sensorManager.registerListener(sensorEventListener, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    protected void onStop() {
        super.onStop();
        if (sensorManager != null) {
            sensorManager.unregisterListener(sensorEventListener);
        }
    }

    private SensorEventListener sensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float[] values = event.values;
            float x = values[0];
            float y = values[1];
            float z = values[2];
            int medumValue = 10;

            if (Math.abs(x) > medumValue || Math.abs(y) > medumValue || Math.abs(z) > medumValue) {
                vibrator.vibrate(200);
                Message msg = new Message();
                msg.what = 3;
                handler.sendMessage(msg);

            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }

        Handler handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                if (msg.what == 3) {
                    Intent intent = getIntent();
                    Float price = intent.getFloatExtra("brent_price_key", 0.0f);
                    Float yestPrice = intent.getFloatExtra("brent_yestPrice_key", 0.0f);
                    Float open = intent.getFloatExtra("brent_open_key", 0.0f);
                    String range = intent.getStringExtra("brent_range_key");
                    String yearRange = intent.getStringExtra("brent_yearRange_key");
                    String balanceDate = intent.getStringExtra("brent_balanceDate_key");
                    String dif = intent.getStringExtra("brent_dif_key");
                    priceText = (TextView) findViewById(R.id.text_brentPrice);
                    difText = (TextView) findViewById(R.id.text_dif);
                    rangeText = (TextView) findViewById(R.id.text_range);
                    yestPriceText = (TextView) findViewById(R.id.text_yesterday);
                    openText = (TextView) findViewById(R.id.text_open);
                    yearRangeText = (TextView) findViewById(R.id.text_yearRange);
                    balanceDateText = (TextView) findViewById(R.id.text_balanceDate);
                    priceText.setText(String.valueOf(price));
                    if (dif.contains("-")) {
                        difText.setTextColor(Color.GREEN);
                    } else if (dif.contains("+")) {
                        difText.setTextColor(Color.RED);
                    }
                    difText.setText(dif);
                    if (range.contains("-")) {
                        rangeText.setTextColor(Color.parseColor("#00FF00"));
                    } else if (range.contains("+")) {
                        rangeText.setTextColor(Color.parseColor("#FF0000"));
                    }
                    rangeText.setText(range);
                    yestPriceText.setText("昨收：" + String.valueOf(yestPrice));
                    openText.setText("开盘：" + String.valueOf(open));
                    if (yearRange.contains("-")) {
                        yearRangeText.setTextColor(Color.parseColor("#00FF00"));
                    } else if (yearRange.contains("+")) {
                        yearRangeText.setTextColor(Color.parseColor("#FF0000"));
                    }
                    yearRangeText.setText("1年涨幅度:" + yearRange);
                    balanceDateText.setText("结算日：" + balanceDate);
                }
            }

        };
    };

        public void openBrentList() {
            Intent brentList = new Intent(this, BrentListActivity.class);
            startActivity(brentList);
        }

        public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.activity_main, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected(@NonNull MenuItem item) {
            if (item.getItemId() == R.id.menu_tendency) {
                openBrentList();
            }
            return super.onOptionsItemSelected(item);
        }


}
