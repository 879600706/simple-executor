package com.bo.job;

import com.bo.executor.template.AsyncJobTemplate;
import com.bo.job.data.ListExpertInfoJobData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


@Component
@Slf4j
public class ListExpertInfoJob implements AsyncJobTemplate<ListExpertInfoJobData> {

    @Override
    public boolean canExecute(ListExpertInfoJobData jobData) {
        return !CollectionUtils.isEmpty(jobData.getCollectManageIds());
    }


    @Override
    public ListExpertInfoJobData execute(ListExpertInfoJobData jobData) {
        jobData.setExportExpertDTOS(jobData.getCollectManageIds().stream().map(collectManageId -> {
            ListExpertInfoJobData.ExpertDTO dto = new ListExpertInfoJobData.ExpertDTO();
            dto.setId(collectManageId);
            dto.setCollectManageId(collectManageId);
            dto.setName("ListExportExpertDTO" + collectManageId);
            dto.setUserAccount("ListExportExpertDTO_ACCOUNT" + collectManageId);
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
