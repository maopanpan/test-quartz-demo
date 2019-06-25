package com.myself.quartz.test;

import com.myself.quartz.test.service.SchedulerRemoteService;
import com.myself.quartz.test.service.impl.SchedulerRemoteServiceImpl;
import com.myself.quartz.test.vo.JobDetailVO;
import com.myself.quartz.test.vo.QuartzSchedulerVO;
import com.myself.quartz.test.vo.TriggerVO;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 类名称：QuartzTest<br>
 * 类描述：<br>
 * 创建时间：2019年06月25日<br>
 *
 * @author maopanpan
 * @version 1.0.0
 */
public class QuartzTest extends BaseTestCase {

    @Autowired
    private SchedulerRemoteService schedulerRemoteService;

    /**
     * 添加一个调度任务
     *
     * @throws Exception
     */
    @Test
    public void addTest() throws Exception {
        QuartzSchedulerVO schd = new QuartzSchedulerVO();
        JobDetailVO jobDetail = new JobDetailVO();
        jobDetail.setJobName("test");
        jobDetail.setJobGroup("testGroup");
        jobDetail.setJobClass("com.myself.quartz.test.job.TestJob");
        jobDetail.setNeedStore(Boolean.TRUE);
        TriggerVO trigger = new TriggerVO("testTigger", "testTiggerGroup", TriggerVO.TriggerType.CRON);
        trigger.setCronExp("10/59 * * * * ? ");
        schd.setJobDetail(jobDetail);
        schd.setTrigger(trigger);
        schedulerRemoteService.createScheduler(schd);

        Thread.sleep(1000 * 60 * 10);
    }

    /**
     * 删除一个调度任务
     *
     * @throws Exception
     */
    @Test
    public void delTest() throws Exception {
        QuartzSchedulerVO schd = new QuartzSchedulerVO();
        JobDetailVO jobDetail = new JobDetailVO();
        jobDetail.setJobName("test");
        jobDetail.setJobGroup("testGroup");
        jobDetail.setJobClass("com.myself.quartz.test.job.TestJob");
        jobDetail.setNeedStore(Boolean.TRUE);
        TriggerVO trigger = new TriggerVO("testTigger", "testTiggerGroup", TriggerVO.TriggerType.CRON);
        trigger.setCronExp("10/59 * * * * ? ");
        schd.setJobDetail(jobDetail);
        schd.setTrigger(trigger);
        schedulerRemoteService.deleteScheduler(schd);
    }

    /**
     * 暂停一个JOB
     */
    @Test
    public void pauseJobTest() throws Exception {
        QuartzSchedulerVO schd = new QuartzSchedulerVO();
        JobDetailVO jobDetail = new JobDetailVO();
        jobDetail.setJobName("test");
        jobDetail.setJobGroup("testGroup");
        jobDetail.setJobClass("com.myself.quartz.test.job.TestJob");
        jobDetail.setNeedStore(Boolean.TRUE);
        schd.setJobDetail(jobDetail);
        schedulerRemoteService.pauseJob(schd);
    }

    /**
     * 恢复一个JOB
     *
     * @throws Exception
     */
    @Test
    public void resumeJobTest() throws Exception {
        QuartzSchedulerVO schd = new QuartzSchedulerVO();
        JobDetailVO jobDetail = new JobDetailVO();
        jobDetail.setJobName("test");
        jobDetail.setJobGroup("testGroup");
        jobDetail.setJobClass("com.myself.quartz.test.job.TestJob");
        jobDetail.setNeedStore(Boolean.TRUE);
        schd.setJobDetail(jobDetail);
        schedulerRemoteService.resumeJob(schd);

    }

    /**
     * 暂停一个触发器
     *
     * @throws Exception
     */
    @Test
    public void pauseTriggerTest() throws Exception {
        QuartzSchedulerVO schd = new QuartzSchedulerVO();
        TriggerVO trigger = new TriggerVO("testTigger", "testTiggerGroup", TriggerVO.TriggerType.CRON);
        trigger.setCronExp("10/59 * * * * ? ");
        schd.setTrigger(trigger);
        schedulerRemoteService.pauseTrigger(schd);
    }
    @Test
    public void resumeTriggerTest() throws Exception {
        QuartzSchedulerVO schd = new QuartzSchedulerVO();
        TriggerVO trigger = new TriggerVO("testTigger", "testTiggerGroup", TriggerVO.TriggerType.CRON);
        trigger.setCronExp("10/59 * * * * ? ");
        schd.setTrigger(trigger);
        schedulerRemoteService.resumeTrigger(schd);
    }


}
