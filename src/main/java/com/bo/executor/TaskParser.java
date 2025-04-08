package com.bo.executor;

import com.bo.executor.data.JobData;
import com.bo.executor.template.AsyncJobTemplate;
import com.bo.utils.JsonUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
@Slf4j
@Component
public class TaskParser implements ApplicationContextAware {

    private static final String TASK_JSON_LOCATION = "task/**";

    private static ApplicationContext capplicationContext;


    @PostConstruct
    public void loadTask() throws Exception {
        loadTaskFiles().forEach(resource -> {
            String fileName = resource.getFilename();
            try (InputStream inputStream = resource.getInputStream()) {
                String content = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
                parse(getTaskName(fileName), content);
            } catch (Exception e) {
                log.error("解析json失败", e);
            }
        });
    }

    private List<Resource> loadTaskFiles() throws IOException {
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        return Arrays.asList(resolver.getResources(TASK_JSON_LOCATION));
    }

    private String getTaskName(String filename) {
        return (filename != null && filename.contains("."))
                ? filename.substring(0, filename.lastIndexOf('.'))
                : filename;
    }

    public void parse(String taskName, String content) throws Exception {
        List<ConfigItem> configItems = JsonUtil.jsonToObject(content, new TypeReference<>() {
        });
        List<ConfigItem> startConfigItems = configItems.stream().filter(configItem -> {
            return CollectionUtils.isEmpty(configItem.getDependsOn());
        }).toList();
        List<String> startTaskKeys = startConfigItems.stream().map(ConfigItem::getJobName).collect(Collectors.toList());
        TaskMetaData taskMetaData = new TaskMetaData();
        taskMetaData.setTaskName(taskName);
        taskMetaData.setStartJobKeys(startTaskKeys);

        Map<String, AsyncJobTemplate<JobData>> jobTemplateMap = new HashMap<>();
        Map<String, List<String>> jobConsumerMap = new HashMap<>();
        Map<String, Class<? extends JobData>> jobDataMap = new HashMap<>();

        for (ConfigItem configItem : configItems) {
            Class<?> jobClass = Class.forName(configItem.getJobClass());
            Type type = jobClass.getGenericInterfaces()[0];
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Class<? extends JobData> jobData = (Class<? extends JobData>) parameterizedType.getActualTypeArguments()[0];

            jobTemplateMap.put(configItem.getJobName(), (AsyncJobTemplate<JobData>) capplicationContext.getBean(jobClass));
            jobDataMap.put(configItem.getJobName(), jobData);

            List<String> dependsOn = configItem.getDependsOn();
            if (!CollectionUtils.isEmpty(dependsOn)) {
                dependsOn.forEach(s -> {
                    List<String> strings = jobConsumerMap.computeIfAbsent(s, k -> new ArrayList<>());
                    strings.add(configItem.getJobName());
                });
            }
        }

        taskMetaData.setJobDataMAp(jobDataMap);
        taskMetaData.setJobConsumerMap(jobConsumerMap);
        taskMetaData.setJobTemplateMap(jobTemplateMap);
        taskMetaData.setEndJobKey(configItems.stream().map(ConfigItem::getJobName).filter(name -> !jobConsumerMap.containsKey(name)).findFirst().orElseThrow(() -> new RuntimeException("不允许多个出口类")));
        TaskMetaData.saveMetaData(taskName, taskMetaData);
        log.info("task {} load success", taskName);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (Objects.isNull(capplicationContext)) {
            capplicationContext = applicationContext;
        }
    }

    @Data
    public static class ConfigItem {

        private String jobName;

        private String jobClass;

        private List<String> dependsOn;

    }


}
