package com.bo.executor.data;


import com.bo.executor.TaskContext;

public interface JobData {

    default void build(TaskContext taskContext) {

    }

}
