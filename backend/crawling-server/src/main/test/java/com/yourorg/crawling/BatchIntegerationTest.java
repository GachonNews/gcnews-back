package com.yourorg.crawling;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class BatchIntegrationTest {
    @Autowired JobLauncher jobLauncher;
    @Autowired Job newsCrawlingJob;

    @Test
    void batchJob_runsSuccessfully() throws Exception {
        JobParameters params = new JobParametersBuilder()
                .addLong("now", System.currentTimeMillis())
                .toJobParameters();
        JobExecution execution = jobLauncher.run(newsCrawlingJob, params);
        assertEquals(BatchStatus.COMPLETED, execution.getStatus());
    }
}
