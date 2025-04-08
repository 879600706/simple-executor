package com.bo.job.data;

import com.bo.executor.TaskContext;
import com.bo.executor.data.JobData;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;


@Data
public class ListCollectManageByIdsJobData implements JobData {

    private List<Long> collectManageIds;

    private List<CollectManageApiDTO> collectManageApiDTOS;

    @Override
    public void build(TaskContext taskContext) {
        ListExpertInfoJobData listExpertInfoJob = (ListExpertInfoJobData) taskContext.getJobData("ListExpertInfoJob");
        setCollectManageIds(listExpertInfoJob.getExportExpertDTOS().stream().map(ListExpertInfoJobData.ExpertDTO::getCollectManageId).distinct().collect(Collectors.toList()));
    }

    @Data
    public static class CollectManageApiDTO {

        private Long collectManageId;

        private String collectManageName;

    }

}
