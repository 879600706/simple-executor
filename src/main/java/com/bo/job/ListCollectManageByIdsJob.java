package com.bo.job;

import com.bo.executor.template.AsyncJobTemplate;
import com.bo.job.data.ListCollectManageByIdsJobData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
@Slf4j
public class ListCollectManageByIdsJob implements AsyncJobTemplate<ListCollectManageByIdsJobData> {


    @Override
    public boolean canExecute(ListCollectManageByIdsJobData jobData) {
        return !CollectionUtils.isEmpty(jobData.getCollectManageIds());
    }

    @Override
    public ListCollectManageByIdsJobData execute(ListCollectManageByIdsJobData jobData) {
        jobData.setCollectManageApiDTOS(jobData.getCollectManageIds().stream().map(collectManageId->{
            ListCollectManageByIdsJobData.CollectManageApiDTO dto=new ListCollectManageByIdsJobData.CollectManageApiDTO();
            dto.setCollectManageId(collectManageId);
            dto.setCollectManageName(collectManageId+"NAME");
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
