package com.bo.executor;

import cn.hutool.core.collection.ConcurrentHashSet;
import com.bo.executor.data.JobData;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class TaskContext {

    @Getter
    private final Object initData;

    @Setter
    private String taskName;

    private long startTime;

    private long endTime;

    private long defaultTimeout = 10000;

    private final Map<String, JobData> jobDataMap = new ConcurrentHashMap<>();

    private final Set<String> waitExecuteSet = new ConcurrentHashSet<>();


    public static TaskContext newInstance(Object initData) {
        return new TaskContext(initData);
    }

    public TaskContext(@NonNull Object initData) {
        this.initData = initData;
    }


    public JobData getResult() {
        String endJobKey = TaskMetaData.getInstance(taskName).getEndJobKey();
        if (!waitExecuteSet.contains(endJobKey)) {
            return jobDataMap.get(endJobKey);
        }
        synchronized (this) {
            try {
                this.wait(defaultTimeout);
            } catch (InterruptedException e) {
                log.error("获取结果失败", e);
            }
        }
        return jobDataMap.get(endJobKey);
    }

    public JobData getJobData(String jobName) {
        return jobDataMap.get(jobName);
    }

    public long totalCos() {
        if (startTime == 0 || endTime == 0)
            return 0;
        return endTime - startTime;
    }

    protected void saveJobData(String jobName, JobData jobData) {
        jobDataMap.put(jobName, jobData);
    }

    protected void jobDone(String jobName) {
        waitExecuteSet.remove(jobName);
    }

    protected boolean isJobDone(String jobName) {
        return !waitExecuteSet.contains(jobName);
    }

    protected boolean isLastJob(String jobName) {
        return waitExecuteSet.contains(jobName) && waitExecuteSet.size() == 1;
    }

    protected void initJobList(Set<String> waitExecuteSet) {
        this.waitExecuteSet.addAll(waitExecuteSet);
    }

    protected void start() {
        startTime = System.currentTimeMillis();
    }

    protected void end() {
        endTime = System.currentTimeMillis();
    }


}
