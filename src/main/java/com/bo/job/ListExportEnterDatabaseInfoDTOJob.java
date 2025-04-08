package com.bo.job;

import com.bo.executor.template.AsyncJobTemplate;
import com.bo.job.data.ListExportEnterDatabaseJobData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


@Component
@Slf4j
public class ListExportEnterDatabaseInfoDTOJob implements AsyncJobTemplate<ListExportEnterDatabaseJobData> {


    @Override
    public boolean canExecute(ListExportEnterDatabaseJobData jobData) {
        return !CollectionUtils.isEmpty(jobData.getExpertList());
    }

    @Override
    public ListExportEnterDatabaseJobData execute(ListExportEnterDatabaseJobData jobData) {
        jobData.setDatabaseDTOS(jobData.getExpertList().stream().map(expertId -> {
            ListExportEnterDatabaseJobData.DatabaseDTO dto = new ListExportEnterDatabaseJobData.DatabaseDTO();
            dto.setExpertId(expertId);
            dto.setName("ExportEnterDatabaseInfoDTO" + expertId);
            dto.setPhone("ExportEnterDatabaseInfoDTO_PHONE" + expertId);
            return dto;
        }).collect(Collectors.toList()));
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return jobData;
    }


}
