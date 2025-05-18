package com.yourorg.crawling.adapter.in.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@EnableScheduling 
public class ArticleCrawlingScheduler {

    private final JobLauncher jobLauncher;
    private final Job articleCrawlingJob;

    @Scheduled(fixedDelay = 60000) // 60초(1분)마다 실행
    public void runJob() throws Exception {
        jobLauncher.run(
            articleCrawlingJob,
            new JobParametersBuilder()
                .addLong("uniqueness", System.currentTimeMillis()) // 매번 새로운 파라미터
                .toJobParameters()
        );
    }
}
