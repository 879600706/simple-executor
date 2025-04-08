package com.bo.job.data;

import com.bo.executor.TaskContext;
import com.bo.executor.data.JobData;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;


@Data
public class ListExportEnterDatabaseJobData implements JobData {

    /**
     * 专家id集合
     */
    private List<Long> expertList;

    private List<DatabaseDTO> databaseDTOS;

    @Override
    public void build(TaskContext taskContext) {
        ListExpertInfoJobData listExpertInfoJob = (ListExpertInfoJobData) taskContext.getJobData("ListExpertInfoJob");
        List<Long> collect = listExpertInfoJob.getExportExpertDTOS().stream().map(ListExpertInfoJobData.ExpertDTO::getId).collect(Collectors.toList());
        setExpertList(collect);
    }

    @Data
    public static class DatabaseDTO {

        private Long expertId;

        private String name;

        private String phone;

    }

}
