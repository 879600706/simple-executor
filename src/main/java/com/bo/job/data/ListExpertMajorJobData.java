package com.bo.job.data;

import com.bo.executor.TaskContext;
import com.bo.executor.data.JobData;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;


@Data
public class ListExpertMajorJobData implements JobData {

    /**
     * 专家id集合
     */
    private List<Long> expertList;

    private List<ExportExpertMajorDTO> majorDTOS;

    @Override
    public void build(TaskContext taskContext) {
        ListExportEnterDatabaseJobData exportEnterDatabaseInfoDTO = (ListExportEnterDatabaseJobData) taskContext.getJobData("ListExportEnterDatabaseInfoDTOJob");
        setExpertList(exportEnterDatabaseInfoDTO.getDatabaseDTOS().stream().map(ListExportEnterDatabaseJobData.DatabaseDTO::getExpertId).collect(Collectors.toList()));
    }

    @Data
    public static class ExportExpertMajorDTO {

        private Long expertId;

        private String industry;

    }
}
