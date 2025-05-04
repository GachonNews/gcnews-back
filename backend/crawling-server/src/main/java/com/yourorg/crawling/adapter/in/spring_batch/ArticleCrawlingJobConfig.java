package com.yourorg.crawling.adapter.in.spring_batch;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
@EnableBatchProcessing
public class ArticleCrawlingJobConfig {

    private final ArticleCrawlingTasklet articleCrawlingTasklet;
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    @Bean
    public Job articleCrawlingJob() {
        return new JobBuilder("articleCrawlingJob", jobRepository)
                .start(articleCrawlingStep())
                .build();
    }

    @Bean
    public Step articleCrawlingStep() {
        return new StepBuilder("articleCrawlingStep", jobRepository)
                .tasklet(articleCrawlingTasklet, transactionManager)
                .build();
    }
}
