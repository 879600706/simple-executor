package com.bo.job;

import com.bo.executor.template.AsyncJobTemplate;
import com.bo.job.data.ListExportProTitleCertJobData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


@Component
@Slf4j
public class ListExportProTitleCertJob implements AsyncJobTemplate<ListExportProTitleCertJobData> {


    @Override
    public boolean canExecute(ListExportProTitleCertJobData jobData) {
        return !CollectionUtils.isEmpty(jobData.getExpertList());
    }

    @Override
    public ListExportProTitleCertJobData execute(ListExportProTitleCertJobData jobData) {
        jobData.setProTitleCertDTOS(jobData.getExpertList().stream().map(expertId -> {
            ListExportProTitleCertJobData.ExportProTitleCertDTO dto = new ListExportProTitleCertJobData.ExportProTitleCertDTO();
            dto.setExpertId(expertId);
            dto.setTitleName(expertId + "TITLE_NAME");
            return dto;
        }).collect(Collectors.toList()));
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return jobData;
    }


}
