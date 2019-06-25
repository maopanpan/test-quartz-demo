package com.myself.quartz.test.job;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Component;

/**
 * 类名称：TestJob<br>
 * 类描述：<br>
 * 创建时间：2019年06月25日<br>
 *
 * @author maopanpan
 * @version 1.0.0
 */
@Component
@Slf4j
public class TestJob extends AbstractJob {

    @Override
    protected void before(JobExecutionContext jobExecutionContext) {
        LOGGER.info("前置调度方法");
    }

    @Override
    protected void doJob(JobExecutionContext jobExecutionContext) {
        LOGGER.info("业务逻辑处理");
    }

    @Override
    protected void after(JobExecutionContext jobExecutionContext) {
        LOGGER.info("后置调度方法");
    }
}
