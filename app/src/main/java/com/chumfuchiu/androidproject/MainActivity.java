package com.chumfuchiu.androidproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button btnStartActService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnStartActService = findViewById(R.id.btn_start_act_service);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_start_act_service:

                break;
        }
    }
}
