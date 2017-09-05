package com.yang.springboot.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Yangjing
 */
@RestController
public class BatchController {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job importJob;

    public JobParameters jobParameters;

    @RequestMapping("/imp")
    public String imp(String filename)throws Exception{
        String path=filename+".csv";
        jobParameters=new JobParametersBuilder()
                .addLong("time",System.currentTimeMillis())
                .addString("input.file.name",path)
                .toJobParameters();
        jobLauncher.run(importJob,jobParameters);
        return "Success";
    }
}
