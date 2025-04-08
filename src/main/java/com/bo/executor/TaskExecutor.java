package com.bo.executor;

import cn.hutool.core.thread.NamedThreadFactory;
import com.bo.executor.data.JobData;
import com.bo.executor.template.AsyncJobTemplate;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


@Slf4j
public class TaskExecutor {

    private final static Integer coreSize = Runtime.getRuntime().availableProcessors() * 2;

    private final static ThreadPoolExecutor taskPool = new ThreadPoolExecutor(coreSize,
            coreSize,
            0L,
            TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(1024),
            new NamedThreadFactory("job-execute-t-", false),
            (r, executor) -> {
                throw new RuntimeException("系统繁忙请稍后再试");
            });

    public static void startTask(String taskName, TaskContext taskContext) {
        TaskMetaData taskMetaData = TaskMetaData.getInstance(taskName);
        taskContext.setTaskName(taskName);
        taskContext.initJobList(taskMetaData.getJobDataMAp().keySet());
        List<String> startTaskKeys = taskMetaData.getStartJobKeys();
        taskContext.start();
        startTaskKeys.forEach(startTaskKey -> {
            runTask(startTaskKey, taskContext, taskMetaData);
        });
    }

    private static void runTask(String jobName, TaskContext taskContext, TaskMetaData taskMetaData) {
        String taskName = taskMetaData.getTaskName();
        if (taskContext.isJobDone(jobName)) {
            log.info("taskName {} jobName {} already run ", taskName, jobName);
            return;
        }
        AsyncJobTemplate<JobData> startTasTemplate = taskMetaData.getJobTemplateMap().get(jobName);
        JobData jobData = taskContext.getJobData(jobName);
        if (Objects.isNull(jobData)) {
            Class<? extends JobData> taskDataClass = taskMetaData.getJobDataMAp().get(jobName);
            try {
                jobData = taskDataClass.newInstance();
                Method buildMethod = taskDataClass.getMethod("build", TaskContext.class);
                buildMethod.invoke(jobData, taskContext);
            } catch (Exception e) {
                log.info("taskName {} jobName {} buildData error ", taskName, jobName, e);
                taskContext.jobDone(jobName);
                synchronized (taskContext) {
                    taskContext.notify();
                }
                taskContext.end();
                return;
            }
            log.info("taskName {} jobName {} buildData done", taskName, jobName);
        }
        if (!startTasTemplate.canExecute(jobData)) {
            log.info("taskName {} jobName {} can not execute", taskName, jobName);
            if (taskContext.isLastJob(jobName)) {
                synchronized (taskContext) {
                    taskContext.notify();
                }
                taskContext.end();
            }
            return;
        }
        log.info("taskName {} jobName {} execute", taskName, jobName);
        final JobData finalJobData = jobData;
        CompletableFuture.supplyAsync(() -> {
                    return startTasTemplate.execute(finalJobData);
                }, taskPool)
                .thenAcceptAsync((c) -> {
                    log.info("taskName {} jobName {} execute done", taskName, jobName);
                    taskContext.saveJobData(jobName, c);
                    taskContext.jobDone(jobName);
                    List<String> consumers = taskMetaData.getJobConsumerMap().getOrDefault(jobName, Collections.emptyList());
                    for (String consumer : consumers) {
                        runTask(consumer, taskContext, taskMetaData);
                    }
                    if (jobName.equals(taskMetaData.getEndJobKey())) {
                        synchronized (taskContext) {
                            taskContext.notify();
                            taskContext.end();
                        }
                    }
                }, taskPool).exceptionally((e) -> {
                    log.error("taskName {} jobName {} execute error", taskName, jobName, e);
                    taskContext.jobDone(jobName);
                    synchronized (taskContext) {
                        taskContext.notify();
                        taskContext.end();
                    }
                    return null;
                });
    }

}
