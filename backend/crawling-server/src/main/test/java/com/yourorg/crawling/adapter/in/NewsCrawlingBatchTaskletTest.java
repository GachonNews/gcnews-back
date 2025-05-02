package com.yourorg.crawling.adapter.in;

import com.yourorg.crawling.port.in.CrawlingTriggerPort;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.batch.repeat.RepeatStatus;

import static org.mockito.Mockito.verify;

class NewsCrawlingBatchTaskletTest {
    @Test
    void tasklet_callsCrawlingTriggerPort() throws Exception {
        CrawlingTriggerPort port = Mockito.mock(CrawlingTriggerPort.class);
        NewsCrawlingBatchTasklet tasklet = new NewsCrawlingBatchTasklet(port);

        RepeatStatus status = tasklet.execute(null, null);

        verify(port).triggerCrawling();
    }
}
