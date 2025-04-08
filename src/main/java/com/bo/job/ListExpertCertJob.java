package com.bo.job;

import com.bo.executor.template.AsyncJobTemplate;
import com.bo.job.data.ListExpertCertJobData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
@Slf4j
public class ListExpertCertJob implements AsyncJobTemplate<ListExpertCertJobData> {


    @Override
    public boolean canExecute(ListExpertCertJobData jobData) {
        return !CollectionUtils.isEmpty(jobData.getUserAccounts());
    }

    @Override
    public ListExpertCertJobData execute(ListExpertCertJobData jobData) {
        jobData.setCertificateApiDTOS(jobData.getUserAccounts().stream().map(account -> {
            ListExpertCertJobData.CertificateApiDTO certificateApiDTO = new ListExpertCertJobData.CertificateApiDTO();
            certificateApiDTO.setUserAccount(account);
            certificateApiDTO.setCertCode(account + "CODE");
            return certificateApiDTO;
        }).collect(Collectors.toList()));
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return jobData;
    }
}
