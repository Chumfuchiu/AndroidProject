package com.chumfuchiu.androidproject.service.jobservice;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;

/**
 * Created by qinfuchao on 2018/12/6.
 */

public final class JobServiceHelper {
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public final static void scheduler(Context context, int jobId, Class<?> clazz, JobInterceptor interceptor) {
        final JobScheduler jobScheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        JobInfo.Builder builder = new JobInfo.Builder(jobId, new ComponentName(context, clazz));
        builder.setMinimumLatency(3000);//设置任务允许最少延迟时间
        builder.setOverrideDeadline(100000);//设置deadline,若到期还没有到达规定的条件也会执行任务
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);//任何网络下均可运行
        builder.setRequiresDeviceIdle(true);//当设备处于Idle状态时才可以运行
        builder.setRequiresCharging(true);//设备充电时才可以运行
//        builder.setRequiresStorageNotLow(true);//存储空间低时不予运行
//        builder.setRequiresBatteryNotLow(true);//低电量时不予运行
//        builder.setPersisted(true);//设备重启后是否还运行
        if (interceptor != null) {
            JobInfo.Builder tempBuilder = interceptor.intercept(builder);
            if (tempBuilder != null) {
                builder = tempBuilder;
            }
        }
        jobScheduler.schedule(builder.build());
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public final static void cancel(Context context, int jobId) {
        final JobScheduler jobScheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        jobScheduler.cancel(jobId);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public final static void cancelAll(Context context) {
        final JobScheduler jobScheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        jobScheduler.cancelAll();
    }


    public interface JobInterceptor {
        JobInfo.Builder intercept(JobInfo.Builder builder);
    }
}
