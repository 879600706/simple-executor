package com.bo.job;

import com.bo.executor.template.AsyncJobTemplate;
import com.bo.job.data.ListExpertMajorJobData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


@Component
@Slf4j
public class ListExpertMajorJob implements AsyncJobTemplate<ListExpertMajorJobData> {

    @Override
    public boolean canExecute(ListExpertMajorJobData jobData) {
        return !CollectionUtils.isEmpty(jobData.getExpertList());
    }

    @Override
    public ListExpertMajorJobData execute(ListExpertMajorJobData jobData) {
        jobData.setMajorDTOS(jobData.getExpertList().stream().map(expertId -> {
            ListExpertMajorJobData.ExportExpertMajorDTO majorDTO = new ListExpertMajorJobData.ExportExpertMajorDTO();
            majorDTO.setExpertId(expertId);
            majorDTO.setIndustry("industry" + expertId);
            return majorDTO;
        }).collect(Collectors.toList()));
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return jobData;
    }

}
