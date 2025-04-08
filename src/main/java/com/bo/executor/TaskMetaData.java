package com.bo.executor;

import com.bo.executor.data.JobData;
import com.bo.executor.template.AsyncJobTemplate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter(AccessLevel.PACKAGE)
public class TaskMetaData {

    private static Map<String, TaskMetaData> taskMetaDataMap = new HashMap<>();

    private String taskName;

    private List<String> startJobKeys;

    private String endJobKey;

    private Map<String, AsyncJobTemplate<JobData>> jobTemplateMap;

    private Map<String, List<String>> jobConsumerMap;

    private Map<String, Class<? extends JobData>> jobDataMAp;

    protected static TaskMetaData getInstance(String taskName) {
        return taskMetaDataMap.get(taskName);
    }

    protected static void saveMetaData(String taskName, TaskMetaData metaData) {
        taskMetaDataMap.put(taskName, metaData);
    }

}
