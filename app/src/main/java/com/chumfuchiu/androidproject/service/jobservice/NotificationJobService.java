package com.chumfuchiu.androidproject.service.jobservice;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

/**
 * Created by qinfuchao on 2018/12/6.
 * <p>
 * JobScheduler 是framework层里用来调度将会运行在application进程里各式各样Job的一种机制。
 * 开发人员通过构建JobInfo（一个Job的描述信息）并通过JobScheduler传递给系统。当满足JobInfo
 * 描述的条件时，将会执行JobService。
 * 宗旨:把一些不是特别紧急的任务放到更合适的时机批量处理，这样可以有效的节省电量
 */

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class NotificationJobService extends JobService {
    private static final String TAG = NotificationJobService.class.getSimpleName();

    /**
     * Job开始的时候的回调，实现实际的工作逻辑.工作逻辑不能太耗时，因为onStartJob 运行在主线程。
     * 如果返回值是false,系统假设这个方法返回时任务已经执行完毕。
     * 如果返回值是true,那么系统假定这个任务正要被执行，执行任务的重担就落在了你的肩上。
     * 当任务执行完毕时你需要调用jobFinished来通知系统。
     */
    @Override
    public boolean onStartJob(JobParameters params) {
        executeTask();
        return false;

    }

    /**
     * onStopJob在jobFinished正常调用结束一个job时，也是不会调用的。
     * 在该job没有被执行完，就被cancel掉的时候会被调用。
     * 比如某个job还没有执行就被JobScheduler给Cancel掉时，
     * 或者在某个运行条件不满足时，比如原来在Wifi环境允许的某个任务，执行过程中切换到了非Wifi场景，
     * 那也会调用该方法。改方法也返回一个boolean值。
     * 返回true表示会重新放到JobScheduler里reScheduler，false表示直接忽略,其中，主动cancel的Job是
     * 不会放到reScheduler中的。
     */
    @Override
    public boolean onStopJob(JobParameters params) {
//        Log.d(TAG, "reason:" + params.getStopReason());
        return true;
    }


    private void executeTask() {

    }


}

/**
 * https://blog.csdn.net/allisonchen/article/details/79218713
 * JobService规则
 * 1.Manifest文件里JobService的声明里必须请求android:permission="android.permission.BIND_JOB_SERVICE"的权限。
 * 不然的话，在schdule或者enqueue job的时候会抛出如下的IllegalArgumentException。
 * "Error: requested job be persisted without holding RECEIVE_BOOT_COMPLETED permission."
 * <p>
 * 2.JobInfo创建的时候必须设置一个条件。
 * 不然的话，在创建JobInfo对象时会抛如下的IllegalArgumentException。
 * "You're trying to build a job with no constraints, this is not allowed."
 * <p>
 * 3.同一个UID的进程里只能有唯一一个Job的ID。
 * 不然的话，新生成的Job会抢占已经运行的Job，导致该Job被异常终止。
 * <p>
 * 4.JobService因为运行条件变化后被强制停止后想自启动的话，需要将onStopJob()返回true。
 * <p>
 * 5.JobService不论何种原因被停止了都希望能自动启动的话，
 * 需要在onStopJob()或onDestroy()里强制再次schedule我们的jobservice。
 * <p>
 * 6.如果自行cancel了Job，即便onStopJob()里返回true系统也不会将该Job再度启动。
 * <p>
 * 7.如果自行finished了Job，那么onStopJob()将得不到回调，将只回调onDestroy()。
 * <p>
 * 8.Job如果要执行长时间任务的话，onStartJob()应当返回true。
 * 不然onStartJob()刚回调结束，Job就会被停止。
 */
