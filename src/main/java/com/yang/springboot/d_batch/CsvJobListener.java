package com.yang.springboot.d_batch;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

/**
 * @author Yangjing
 * @explain 监听
 */
public class CsvJobListener implements JobExecutionListener {

    long startTime;
    long endTime;

    @Override
    public void beforeJob(JobExecution jobExecution) {
        startTime = System.currentTimeMillis();
        System.out.println("任务处理开始");
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        endTime = System.currentTimeMillis();
        System.out.println("任务处理结束");
        System.out.println("耗时：" + (endTime - startTime) + "ms");
    }
}
