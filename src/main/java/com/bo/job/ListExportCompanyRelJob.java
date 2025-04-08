package com.bo.job;

import com.bo.executor.template.AsyncJobTemplate;
import com.bo.job.data.ListExportCompanyRelJobData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


@Component
@Slf4j
public class ListExportCompanyRelJob implements AsyncJobTemplate<ListExportCompanyRelJobData> {


    @Override
    public boolean canExecute(ListExportCompanyRelJobData jobData) {
        return !CollectionUtils.isEmpty(jobData.getExpertList());
    }

    @Override
    public ListExportCompanyRelJobData execute(ListExportCompanyRelJobData jobData) {
        jobData.setCompanyRelDTOS(jobData.getExpertList().stream().map(expertId -> {
            ListExportCompanyRelJobData.ExportCompanyRelDTO companyRelDTO = new ListExportCompanyRelJobData.ExportCompanyRelDTO();
            companyRelDTO.setCompanyId(expertId);
            companyRelDTO.setExpertId(expertId);
            return companyRelDTO;
        }).collect(Collectors.toList()));
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return jobData;
    }

}
