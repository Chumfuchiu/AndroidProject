package com.chumfuchiu.androidproject.service;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.chumfuchiu.androidproject.R;
import com.chumfuchiu.androidproject.service.jobservice.JobServiceHelper;
import com.chumfuchiu.androidproject.service.jobservice.NotificationJobService;

/**
 * Created by qinfuchao on 2018/12/6.
 */

public class ActService extends Activity implements View.OnClickListener {
    Button btnStartJobService, btnStopJobService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        btnStartJobService = findViewById(R.id.btn_start_job_service);
        btnStopJobService = findViewById(R.id.btn_stop_job_service);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start_job_service:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    JobServiceHelper.scheduler(this, NotificationJobService.class.hashCode(), NotificationJobService.class, null);
                }
                break;
            case R.id.btn_stop_job_service:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    JobServiceHelper.cancel(this, NotificationJobService.class.hashCode());
                }
                break;
        }
    }
}
