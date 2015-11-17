package com.zsl.customviewdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.zsl.customviewdemo.activitys.Custom1Activity;
import com.zsl.customviewdemo.activitys.Custom2Activity;
import com.zsl.customviewdemo.activitys.Custom3Activity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    Button bt_c1, bt_c2, bt_c3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initEvent();
    }

    private void initView() {

        bt_c1 = (Button) findViewById(R.id.main_bt_custom1);
        bt_c2 = (Button) findViewById(R.id.main_bt_custom2);
        bt_c3 = (Button) findViewById(R.id.main_bt_custom3);

    }

    private void initEvent() {
        bt_c1.setOnClickListener(this);
        bt_c2.setOnClickListener(this);
        bt_c3.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        int vId = v.getId();
        if (vId == R.id.main_bt_custom1) {
            intent.setClass(this, Custom1Activity.class);
        } else if (vId == R.id.main_bt_custom2) {
            intent.setClass(this, Custom2Activity.class);
        } else if (vId == R.id.main_bt_custom3) {
            intent.setClass(this, Custom3Activity.class);
        }


        startActivity(intent);
    }
}
