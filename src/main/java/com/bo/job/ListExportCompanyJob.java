package com.bo.job;

import com.bo.executor.template.AsyncJobTemplate;
import com.bo.job.data.ListExportCompanyJobData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


@Component
@Slf4j
public class ListExportCompanyJob implements AsyncJobTemplate<ListExportCompanyJobData> {


    @Override
    public boolean canExecute(ListExportCompanyJobData jobData) {
        return !CollectionUtils.isEmpty(jobData.getCompanyIdList());
    }

    @Override
    public ListExportCompanyJobData execute(ListExportCompanyJobData jobData) {
        jobData.setCompanyDTOS(jobData.getCompanyIdList().stream().map(companyId -> {
            ListExportCompanyJobData.CompanyDTO companyDTO = new ListExportCompanyJobData.CompanyDTO();
            companyDTO.setCompanyId(companyId);
            companyDTO.setCompanyName("companyName" + companyId);
            return companyDTO;
        }).collect(Collectors.toList()));
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return jobData;
    }
}
