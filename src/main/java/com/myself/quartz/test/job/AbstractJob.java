package com.myself.quartz.test.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 类名称：AbstractJob<br>
 * 类描述：<br>
 * 创建时间：2019年06月25日<br>
 *
 * @author maopanpan
 * @version 1.0.0
 */
public abstract class AbstractJob implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        before(jobExecutionContext);
        doJob(jobExecutionContext);
        after(jobExecutionContext);
    }

    protected abstract void before(JobExecutionContext jobExecutionContext);

    protected abstract void doJob(JobExecutionContext jobExecutionContext);

    protected abstract void after(JobExecutionContext jobExecutionContext);
}
