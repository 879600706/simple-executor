package com.bo.job;


import com.bo.executor.template.AsyncJobTemplate;
import com.bo.job.data.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@Slf4j
public class BuildExcelDataJob implements AsyncJobTemplate<BuildExcelDataJobData> {


    @Override
    public boolean canExecute(BuildExcelDataJobData jobData) {
        return Objects.nonNull(jobData.getExportExpertDTOS()) && Objects.nonNull(jobData.getDatabaseDTOS())
                && Objects.nonNull(jobData.getMajorDTOS()) && Objects.nonNull(jobData.getProTitleCertDTOS())
                && Objects.nonNull(jobData.getRegCategoryCertDTOS()) && Objects.nonNull(jobData.getCompanyDTOS())
                && Objects.nonNull(jobData.getCompanyRelDTOS()) && Objects.nonNull(jobData.getCertificateApiDTOS())
                && Objects.nonNull(jobData.getCollectManageApiDTOS());
    }

    @Override
    public BuildExcelDataJobData execute(BuildExcelDataJobData jobData) {
        List<ListExpertInfoJobData.ExpertDTO> exportExpertDTOS = jobData.getExportExpertDTOS();
        List<ListExportEnterDatabaseJobData.DatabaseDTO> databaseDTOS = jobData.getDatabaseDTOS();
        List<ListExpertMajorJobData.ExportExpertMajorDTO> majorDTOS = jobData.getMajorDTOS();
        List<ListExportProTitleCertJobData.ExportProTitleCertDTO> proTitleCertDTOS = jobData.getProTitleCertDTOS();
        List<ListExportRegCertCertJobData.ExportRegCategoryCertDTO> regCategoryCertDTOS = jobData.getRegCategoryCertDTOS();
        List<ListExportCompanyJobData.CompanyDTO> companyDTOS = jobData.getCompanyDTOS();
        List<ListExportCompanyRelJobData.ExportCompanyRelDTO> companyRelDTOS = jobData.getCompanyRelDTOS();
        List<ListExpertCertJobData.CertificateApiDTO> certificateApiDTOS = jobData.getCertificateApiDTOS();
        List<ListCollectManageByIdsJobData.CollectManageApiDTO> collectManageApiDTOS = jobData.getCollectManageApiDTOS();
        List<BuildExcelDataJobData.ExportDataDTO> data = buildExcelData(exportExpertDTOS, databaseDTOS,
                majorDTOS, proTitleCertDTOS,
                regCategoryCertDTOS, companyRelDTOS,
                companyDTOS, certificateApiDTOS,
                collectManageApiDTOS);
        jobData.setData(data);
        return jobData;
    }

    private List<BuildExcelDataJobData.ExportDataDTO> buildExcelData(List<ListExpertInfoJobData.ExpertDTO> exportExpertDTOS,
                                                                     List<ListExportEnterDatabaseJobData.DatabaseDTO> databaseDTOS,
                                                                     List<ListExpertMajorJobData.ExportExpertMajorDTO> majorDTOS,
                                                                     List<ListExportProTitleCertJobData.ExportProTitleCertDTO> proTitleCertDTOS,
                                                                     List<ListExportRegCertCertJobData.ExportRegCategoryCertDTO> regCategoryCertDTOS,
                                                                     List<ListExportCompanyRelJobData.ExportCompanyRelDTO> companyRelDTOS,
                                                                     List<ListExportCompanyJobData.CompanyDTO> companyDTOS,
                                                                     List<ListExpertCertJobData.CertificateApiDTO> certificateApiDTOS,
                                                                     List<ListCollectManageByIdsJobData.CollectManageApiDTO> collectManageApiDTOS) {
        Map<Long, ListExportEnterDatabaseJobData.DatabaseDTO> databaseDTOMap = databaseDTOS.stream().collect(Collectors.toMap(ListExportEnterDatabaseJobData.DatabaseDTO::getExpertId, Function.identity()));
        Map<Long, String> expertIndustryMap = majorDTOS.stream().collect(Collectors.toMap(ListExpertMajorJobData.ExportExpertMajorDTO::getExpertId, ListExpertMajorJobData.ExportExpertMajorDTO::getIndustry));
        Map<Long, String> expertTitleMap = proTitleCertDTOS.stream().collect(Collectors.toMap(ListExportProTitleCertJobData.ExportProTitleCertDTO::getExpertId, ListExportProTitleCertJobData.ExportProTitleCertDTO::getTitleName));
        Map<Long, String> expertRegMap = regCategoryCertDTOS.stream().collect(Collectors.toMap(ListExportRegCertCertJobData.ExportRegCategoryCertDTO::getExpertId, ListExportRegCertCertJobData.ExportRegCategoryCertDTO::getRegName));

        Map<Long, ListExportCompanyJobData.CompanyDTO> companyDTOMap = companyDTOS.stream().collect(Collectors.toMap(ListExportCompanyJobData.CompanyDTO::getCompanyId, Function.identity()));
        Map<Long, ListExportCompanyJobData.CompanyDTO> expertIdCompanyMap = companyRelDTOS.stream().collect(Collectors.toMap(ListExportCompanyRelJobData.ExportCompanyRelDTO::getExpertId, rel -> {
            return companyDTOMap.get(rel.getCompanyId());
        }));

        Map<String, ListExpertCertJobData.CertificateApiDTO> certificateApiDTOMap = certificateApiDTOS.stream().collect(Collectors.toMap(ListExpertCertJobData.CertificateApiDTO::getUserAccount, Function.identity()));
        Map<Long, ListCollectManageByIdsJobData.CollectManageApiDTO> collectManageApiDTOMap = collectManageApiDTOS.stream().collect(Collectors.toMap(ListCollectManageByIdsJobData.CollectManageApiDTO::getCollectManageId, Function.identity()));


        return exportExpertDTOS.stream().map(expertDTO -> {
            BuildExcelDataJobData.ExportDataDTO dto = new BuildExcelDataJobData.ExportDataDTO();
            dto.setExpertId(expertDTO.getId());
            dto.setName(expertDTO.getName());
            dto.setUserAccount(expertDTO.getUserAccount());
            dto.setCollectManageId(expertDTO.getCollectManageId());
            dto.setPhone(databaseDTOMap.get(expertDTO.getId()).getPhone());
            dto.setIndustry(expertIndustryMap.get(expertDTO.getId()));
            dto.setTitle(expertTitleMap.get(expertDTO.getId()));
            dto.setRegName(expertRegMap.get(expertDTO.getId()));

            ListExportCompanyJobData.CompanyDTO companyDTO = expertIdCompanyMap.get(expertDTO.getId());
            dto.setCompanyId(companyDTO.getCompanyId());
            dto.setCompanyName(companyDTO.getCompanyName());
            dto.setCertCode(certificateApiDTOMap.get(expertDTO.getUserAccount()).getCertCode());
            dto.setCollectManageName(collectManageApiDTOMap.get(expertDTO.getCollectManageId()).getCollectManageName());
            return dto;
        }).collect(Collectors.toList());
    }


}
