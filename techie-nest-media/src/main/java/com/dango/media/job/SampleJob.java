package com.dango.media.job;

import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author dango
 * @description
 * @date 2024-03-14
 */
@Slf4j
@Component
public class SampleJob {

    /**
     * 1、简单任务示例（Bean模式）
     */
    @XxlJob("testJob")
    public void testJob() throws Exception {
        log.info("开始执行.....");

    }

}
