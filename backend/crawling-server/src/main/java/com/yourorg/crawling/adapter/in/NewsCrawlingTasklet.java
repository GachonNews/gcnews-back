package com.yourorg.crawling.adapter.in;

import com.yourorg.crawling.port.in.CrawlingTriggerPort;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NewsCrawlingTasklet implements Tasklet {
    private final CrawlingTriggerPort crawlingTriggerPort;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
        crawlingTriggerPort.triggerCrawling();
        return RepeatStatus.FINISHED;
    }
}