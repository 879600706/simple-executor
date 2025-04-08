# 基于CompletableFuture的异步调用执行器

## 概要
![流程该要](img/img.png)

## TaskContext 任务上下文
包含初始数据 任务执行时间 任务执行后的数据

## TaskExecutor 任务执行器

开始任务 任务调度

## TaskParser 任务解析器
json路径 resources 下 的task文件夹中

解析后的任务名称为文件名不带后缀 解析后会生成TaskMetaData

## TaskMetaData 任务元数据

## json配置

```json
[
  {
    "jobName": "ListExpertInfoJob",
    "jobClass": "com.bo.job.ListExpertInfoJob"
  },
  {
    "jobName": "ListExportEnterDatabaseInfoDTOJob",
    "jobClass": "com.bo.job.ListExportEnterDatabaseInfoDTOJob",
    "dependsOn": [
      "ListExpertInfoJob"
    ]
  }
]

```
# 项目启动 

Application.java中

示例接口

DemoController中  地址 http://127.0.0.1:8080/demo

响应结果示例 id模10为一组 共3组 可以理解为 9个分三片获取
```json
[
  {
    "expertId": 1,
    "name": "ListExportExpertDTO1",
    "phone": "ExportEnterDatabaseInfoDTO_PHONE1",
    "industry": "industry1",
    "title": "1TITLE_NAME",
    "regName": "REG_NAME1",
    "companyId": 1,
    "companyName": "companyName1",
    "userAccount": "ListExportExpertDTO_ACCOUNT1",
    "certCode": "ListExportExpertDTO_ACCOUNT1CODE",
    "collectManageId": 1,
    "collectManageName": "1NAME"
  },
  {
    "expertId": 2,
    "name": "ListExportExpertDTO2",
    "phone": "ExportEnterDatabaseInfoDTO_PHONE2",
    "industry": "industry2",
    "title": "2TITLE_NAME",
    "regName": "REG_NAME2",
    "companyId": 2,
    "companyName": "companyName2",
    "userAccount": "ListExportExpertDTO_ACCOUNT2",
    "certCode": "ListExportExpertDTO_ACCOUNT2CODE",
    "collectManageId": 2,
    "collectManageName": "2NAME"
  },
  {
    "expertId": 3,
    "name": "ListExportExpertDTO3",
    "phone": "ExportEnterDatabaseInfoDTO_PHONE3",
    "industry": "industry3",
    "title": "3TITLE_NAME",
    "regName": "REG_NAME3",
    "companyId": 3,
    "companyName": "companyName3",
    "userAccount": "ListExportExpertDTO_ACCOUNT3",
    "certCode": "ListExportExpertDTO_ACCOUNT3CODE",
    "collectManageId": 3,
    "collectManageName": "3NAME"
  },
  {
    "expertId": 11,
    "name": "ListExportExpertDTO11",
    "phone": "ExportEnterDatabaseInfoDTO_PHONE11",
    "industry": "industry11",
    "title": "11TITLE_NAME",
    "regName": "REG_NAME11",
    "companyId": 11,
    "companyName": "companyName11",
    "userAccount": "ListExportExpertDTO_ACCOUNT11",
    "certCode": "ListExportExpertDTO_ACCOUNT11CODE",
    "collectManageId": 11,
    "collectManageName": "11NAME"
  },
  {
    "expertId": 12,
    "name": "ListExportExpertDTO12",
    "phone": "ExportEnterDatabaseInfoDTO_PHONE12",
    "industry": "industry12",
    "title": "12TITLE_NAME",
    "regName": "REG_NAME12",
    "companyId": 12,
    "companyName": "companyName12",
    "userAccount": "ListExportExpertDTO_ACCOUNT12",
    "certCode": "ListExportExpertDTO_ACCOUNT12CODE",
    "collectManageId": 12,
    "collectManageName": "12NAME"
  },
  {
    "expertId": 13,
    "name": "ListExportExpertDTO13",
    "phone": "ExportEnterDatabaseInfoDTO_PHONE13",
    "industry": "industry13",
    "title": "13TITLE_NAME",
    "regName": "REG_NAME13",
    "companyId": 13,
    "companyName": "companyName13",
    "userAccount": "ListExportExpertDTO_ACCOUNT13",
    "certCode": "ListExportExpertDTO_ACCOUNT13CODE",
    "collectManageId": 13,
    "collectManageName": "13NAME"
  },
  {
    "expertId": 21,
    "name": "ListExportExpertDTO21",
    "phone": "ExportEnterDatabaseInfoDTO_PHONE21",
    "industry": "industry21",
    "title": "21TITLE_NAME",
    "regName": "REG_NAME21",
    "companyId": 21,
    "companyName": "companyName21",
    "userAccount": "ListExportExpertDTO_ACCOUNT21",
    "certCode": "ListExportExpertDTO_ACCOUNT21CODE",
    "collectManageId": 21,
    "collectManageName": "21NAME"
  },
  {
    "expertId": 22,
    "name": "ListExportExpertDTO22",
    "phone": "ExportEnterDatabaseInfoDTO_PHONE22",
    "industry": "industry22",
    "title": "22TITLE_NAME",
    "regName": "REG_NAME22",
    "companyId": 22,
    "companyName": "companyName22",
    "userAccount": "ListExportExpertDTO_ACCOUNT22",
    "certCode": "ListExportExpertDTO_ACCOUNT22CODE",
    "collectManageId": 22,
    "collectManageName": "22NAME"
  },
  {
    "expertId": 23,
    "name": "ListExportExpertDTO23",
    "phone": "ExportEnterDatabaseInfoDTO_PHONE23",
    "industry": "industry23",
    "title": "23TITLE_NAME",
    "regName": "REG_NAME23",
    "companyId": 23,
    "companyName": "companyName23",
    "userAccount": "ListExportExpertDTO_ACCOUNT23",
    "certCode": "ListExportExpertDTO_ACCOUNT23CODE",
    "collectManageId": 23,
    "collectManageName": "23NAME"
  }
]
```