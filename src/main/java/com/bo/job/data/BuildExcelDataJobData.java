package com.bo.job.data;

import com.bo.executor.TaskContext;
import com.bo.executor.data.JobData;
import lombok.Data;

import java.util.List;
import java.util.Objects;

@Data
public class BuildExcelDataJobData implements JobData {

    private List<ListExpertInfoJobData.ExpertDTO> exportExpertDTOS;

    private List<ListExportEnterDatabaseJobData.DatabaseDTO> databaseDTOS;

    private List<ListExpertMajorJobData.ExportExpertMajorDTO> majorDTOS;

    private List<ListExportProTitleCertJobData.ExportProTitleCertDTO> proTitleCertDTOS;

    private List<ListExportRegCertCertJobData.ExportRegCategoryCertDTO> regCategoryCertDTOS;

    private List<ListExportCompanyRelJobData.ExportCompanyRelDTO> companyRelDTOS;

    private List<ListExportCompanyJobData.CompanyDTO> companyDTOS;

    private List<ListExpertCertJobData.CertificateApiDTO> certificateApiDTOS;

    private List<ListCollectManageByIdsJobData.CollectManageApiDTO> collectManageApiDTOS;

    private List<ExportDataDTO> data;

    @Override
    public void build(TaskContext taskContext) {
        ListCollectManageByIdsJobData collectManageByIdsJob = (ListCollectManageByIdsJobData) taskContext.getJobData("ListCollectManageByIdsJob");
        if (Objects.nonNull(collectManageByIdsJob)) {
            setCollectManageApiDTOS(collectManageByIdsJob.getCollectManageApiDTOS());
        }


        ListExpertCertJobData listExpertCertJob = (ListExpertCertJobData) taskContext.getJobData("ListExpertCertJob");
        if (Objects.nonNull(listExpertCertJob)) {
            setCertificateApiDTOS(listExpertCertJob.getCertificateApiDTOS());
        }

        ListExpertInfoJobData listExpertInfoJob = (ListExpertInfoJobData) taskContext.getJobData("ListExpertInfoJob");
        if (Objects.nonNull(listExpertInfoJob)) {
            setExportExpertDTOS(listExpertInfoJob.getExportExpertDTOS());
        }

        ListExpertMajorJobData listExpertMajorJob = (ListExpertMajorJobData) taskContext.getJobData("ListExpertMajorJob");
        if (Objects.nonNull(listExpertMajorJob)) {
            setMajorDTOS(listExpertMajorJob.getMajorDTOS());
        }

        ListExportCompanyJobData listExportCompanyJob = (ListExportCompanyJobData) taskContext.getJobData("ListExportCompanyJob");
        if (Objects.nonNull(listExportCompanyJob)) {
            setCompanyDTOS(listExportCompanyJob.getCompanyDTOS());
        }

        ListExportCompanyRelJobData listExportCompanyRelJob = (ListExportCompanyRelJobData) taskContext.getJobData("ListExportCompanyRelJob");
        if (Objects.nonNull(listExportCompanyRelJob)) {
            setCompanyRelDTOS(listExportCompanyRelJob.getCompanyRelDTOS());
        }

        ListExportEnterDatabaseJobData listExportEnterDatabaseInfoDTOJob = (ListExportEnterDatabaseJobData) taskContext.getJobData("ListExportEnterDatabaseInfoDTOJob");
        if (Objects.nonNull(listExportEnterDatabaseInfoDTOJob)) {
            setDatabaseDTOS(listExportEnterDatabaseInfoDTOJob.getDatabaseDTOS());
        }

        ListExportProTitleCertJobData listExportProTitleCertJob = (ListExportProTitleCertJobData) taskContext.getJobData("ListExportProTitleCertJob");
        if (Objects.nonNull(listExportProTitleCertJob)) {
            setProTitleCertDTOS(listExportProTitleCertJob.getProTitleCertDTOS());
        }

        ListExportRegCertCertJobData listExportRegCategoryCertJob = (ListExportRegCertCertJobData) taskContext.getJobData("ListExportRegCategoryCertJob");
        if (Objects.nonNull(listExportRegCategoryCertJob)) {
            setRegCategoryCertDTOS(listExportRegCategoryCertJob.getRegCategoryCertDTOS());
        }
    }


    @Data
    public static class ExportDataDTO {

        private Long expertId;

        private String name;

        private String phone;

        private String industry;

        private String title;

        private String regName;

        private Long companyId;

        private String companyName;

        private String userAccount;

        private String certCode;

        private Long collectManageId;

        private String collectManageName;

    }

}
