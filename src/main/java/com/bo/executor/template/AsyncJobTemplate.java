package com.bo.executor.template;


import com.bo.executor.data.JobData;

public interface AsyncJobTemplate<T extends JobData> {

    /**
     * 任务是否可执行
     *
     * @return
     */
    boolean canExecute(T jobData);

    /**
     * 执行
     *
     * @param jobData
     * @return
     */
    T execute(T jobData);


}
