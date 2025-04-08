package com.bo.job.data;

import com.bo.executor.TaskContext;
import com.bo.executor.data.JobData;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;


@Data
public class ListExpertCertJobData implements JobData {


    private List<String> userAccounts;

    private List<CertificateApiDTO> certificateApiDTOS;


    @Override
    public void build(TaskContext taskContext) {
        ListExpertInfoJobData listExpertInfoJob = (ListExpertInfoJobData) taskContext.getJobData("ListExpertInfoJob");
        setUserAccounts(listExpertInfoJob.getExportExpertDTOS().stream().map(ListExpertInfoJobData.ExpertDTO::getUserAccount).collect(Collectors.toList()));
    }

    @Data
    public static class CertificateApiDTO {

        private String userAccount;

        private String certCode;

    }

}
