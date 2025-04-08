package com.bo.job.data;

import com.bo.executor.TaskContext;
import com.bo.executor.data.JobData;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;


@Data
public class ListExportCompanyJobData implements JobData {

    /**
     * 单位id集合
     */
    private List<Long> companyIdList;

    private List<CompanyDTO> companyDTOS;

    @Override
    public void build(TaskContext taskContext) {
        ListExportCompanyRelJobData listExportCompanyRelJob = (ListExportCompanyRelJobData) taskContext.getJobData("ListExportCompanyRelJob");
        setCompanyIdList(listExportCompanyRelJob.getCompanyRelDTOS().stream().map(ListExportCompanyRelJobData.ExportCompanyRelDTO::getCompanyId).distinct().collect(Collectors.toList()));
    }

    @Data
    public static class CompanyDTO {

        private Long companyId;

        private String companyName;
    }
}
