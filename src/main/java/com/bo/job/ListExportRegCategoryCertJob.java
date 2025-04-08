package com.bo.job;

import com.bo.executor.template.AsyncJobTemplate;
import com.bo.job.data.ListExportRegCertCertJobData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


@Component
@Slf4j
public class ListExportRegCategoryCertJob implements AsyncJobTemplate<ListExportRegCertCertJobData> {


    @Override
    public boolean canExecute(ListExportRegCertCertJobData jobData) {
        return !CollectionUtils.isEmpty(jobData.getExpertList());
    }

    @Override
    public ListExportRegCertCertJobData execute(ListExportRegCertCertJobData jobData) {
        jobData.setRegCategoryCertDTOS(jobData.getExpertList().stream().map(expertId -> {
            ListExportRegCertCertJobData.ExportRegCategoryCertDTO dto = new ListExportRegCertCertJobData.ExportRegCategoryCertDTO();
            dto.setExpertId(expertId);
            dto.setRegName("REG_NAME" + expertId);
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
