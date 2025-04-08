package com.bo.job.data;

import com.bo.executor.TaskContext;
import com.bo.executor.data.JobData;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;


@Data
public class ListExportProTitleCertJobData implements JobData {

    /**
     * 专家id集合
     */
    private List<Long> expertList;

    private List<ExportProTitleCertDTO> proTitleCertDTOS;

    @Override
    public void build(TaskContext taskContext) {
        ListExpertMajorJobData listExpertMajor = (ListExpertMajorJobData) taskContext.getJobData("ListExpertMajorJob");
        setExpertList(listExpertMajor.getMajorDTOS().stream().map(ListExpertMajorJobData.ExportExpertMajorDTO::getExpertId).distinct().collect(Collectors.toList()));
    }

    @Data
    public static class ExportProTitleCertDTO {

        private Long expertId;

        private String titleName;

    }

}
