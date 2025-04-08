package com.bo.job.data;

import com.bo.controller.request.DemoReq;
import com.bo.executor.TaskContext;
import com.bo.executor.data.JobData;
import lombok.Data;

import java.util.List;


@Data
public class ListExpertInfoJobData implements JobData {

    private List<Long> collectManageIds;

    private List<ExpertDTO> exportExpertDTOS;

    @Override
    public void build(TaskContext taskContext) {
        DemoReq initData = (DemoReq) taskContext.getInitData();
        setCollectManageIds(initData.getCollectManageIds());
    }

    @Data
    public static class ExpertDTO {

        private Long id;

        private String name;

        private Long CollectManageId;

        private String userAccount;

    }
}
