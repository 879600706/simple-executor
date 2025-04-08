package com.bo.controller;

import com.bo.controller.request.DemoReq;
import com.bo.executor.TaskContext;
import com.bo.executor.TaskExecutor;
import com.bo.job.data.BuildExcelDataJobData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@Slf4j
public class DemoController {

    @RequestMapping("demo")
    public List<BuildExcelDataJobData.ExportDataDTO> demo() {

        List<TaskContext> contexts = new ArrayList<>();

        String taskName = "listData";
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        for (int i = 0; i < 3; i++) {
            int minus = i * 10;
            DemoReq demoReq = new DemoReq();
            demoReq.setCollectManageIds(Arrays.asList(1L + minus, 2L + minus, 3L + minus));
            TaskContext taskContext = TaskContext.newInstance(demoReq);
            TaskExecutor.startTask(taskName, taskContext);
            contexts.add(taskContext);
        }

        List<BuildExcelDataJobData.ExportDataDTO> r = new ArrayList<>();
        for (int i = 0; i < contexts.size(); i++) {
            BuildExcelDataJobData result = (BuildExcelDataJobData) contexts.get(i).getResult();
            r.addAll(result.getData());
            log.info("i {} task {} cos {}", i, taskName, contexts.get(i).totalCos());
        }
        stopWatch.stop();
        log.info("total cos {}", stopWatch.getTotalTimeMillis());
        return r;
    }

}
