package com.myself.quartz.test.service.impl;

import com.myself.quartz.test.vo.JobDetailVO;
import com.myself.quartz.test.vo.QuartzSchedulerVO;
import com.myself.quartz.test.vo.TriggerVO;
import com.myself.quartz.test.service.SchedulerRemoteService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * 类名称：SchedulerRemoteServiceImpl<br>
 * 类描述：<br>
 * 创建时间：2019年06月25日<br>
 *
 * @author maopanpan
 * @version 1.0.0
 */
@Slf4j
@Service(value = "schedulerRemoteService")
public class SchedulerRemoteServiceImpl implements SchedulerRemoteService {
    private final Scheduler scheduler;

    @Autowired
    public SchedulerRemoteServiceImpl(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @Override
    public Date createScheduler(QuartzSchedulerVO schd) throws Exception {
        TriggerVO triggerVO = schd.getTrigger();
        TriggerKey triggerKey = TriggerKey.triggerKey(triggerVO.getTriggerName(), triggerVO.getTriggerGroup());
        TriggerBuilder<Trigger> tb = TriggerBuilder.newTrigger().withIdentity(triggerKey);
        triggerBuilderHandler(triggerVO, tb);

        JobDetailVO jobVO = schd.getJobDetail();
        @SuppressWarnings("unchecked")
        Class<Job> jobClass = (Class<Job>) Class.forName(jobVO.getJobClass());
        Boolean needStore = jobVO.getNeedStore();
        JobKey jobKey = JobKey.jobKey(jobVO.getJobName(), jobVO.getJobGroup());
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);

        if (jobDetail != null) {
            Trigger trigger = null;
            List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
            Optional<Trigger> optional = (Optional<Trigger>) triggers.stream().filter(o->(((Trigger) o).getKey().getName().equals(triggerKey.getName()) && ((Trigger) o).getKey().getGroup().equals(triggerKey.getGroup()))).findFirst();
            if (optional.isPresent()) {
                trigger = tb.forJob(jobDetail).build();
                return scheduler.scheduleJob(trigger);
            } else {
                LOGGER.info("触发器{}已存在,将删除后重建", trigger.getKey());
                boolean unscheduleJob = scheduler.unscheduleJob(trigger.getKey());
                if (unscheduleJob) {
                    trigger = tb.forJob(jobDetail).build();
                    return scheduler.scheduleJob(trigger);
                }
            }
        } else {
            JobBuilder jobBuilder = JobBuilder.newJob(jobClass).withIdentity(jobKey);
            if (needStore) {
                LOGGER.info("作业将持久化到数据库中(哪怕没有触发器对应)");
                jobBuilder.storeDurably(true);
            }
            jobDetail = jobBuilder.build();
            Trigger trigger = tb.build();
            return scheduler.scheduleJob(jobDetail, trigger);
        }
        return null;
    }

    @Override
    public Date deleteScheduler(QuartzSchedulerVO schd) throws Exception {
        pauseTrigger(schd);
        if(deleteTrigger(schd)){
            LOGGER.info("触发器{}已经被成功移除...", schd.getTrigger().getTriggerName());
            pauseJob(schd);
            if(deleteJob(schd)){
                LOGGER.info("Job {}已经被成功移除...", schd.getJobDetail().getJobName());
                return new Date();
            }
        }
        return null;
    }

    @Override
    public Date addTrigger(QuartzSchedulerVO schd) throws Exception {

        JobKey jobKey = getJobKey(schd);
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        if (jobDetail == null) {
            throw new NullPointerException("指定的作业不存在!请检查作业名和作业组名是否正确!");
        }
        TriggerVO triggerVO = schd.getTrigger();
        TriggerKey triggerKey = TriggerKey.triggerKey(triggerVO.getTriggerName(), triggerVO.getTriggerGroup());
        Trigger trigger = null;
        List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
        Optional<Trigger> optional = (Optional<Trigger>) triggers.stream().filter(o->(((Trigger) o).getKey().getName().equals(triggerKey.getName()) && ((Trigger) o).getKey().getGroup().equals(triggerKey.getGroup()))).findFirst();
        if (optional.isPresent()) {
            TriggerBuilder<Trigger> tb = TriggerBuilder.newTrigger().withIdentity(triggerKey);
            triggerBuilderHandler(triggerVO, tb);
            trigger = tb.forJob(jobDetail).build();
            return scheduler.scheduleJob(trigger);
        } else {
            return trigger.getNextFireTime();
        }
    }

    @Override
    public boolean deleteTrigger(QuartzSchedulerVO schd) throws Exception {
        TriggerKey triggerKey = getTriggerKey(schd);
        return scheduler.unscheduleJob(triggerKey);
    }

    @Override
    public void pauseTrigger(QuartzSchedulerVO schd) throws Exception {
        TriggerKey triggerKey = getTriggerKey(schd);
        scheduler.pauseTrigger(triggerKey);
    }

    @Override
    public void resumeTrigger(QuartzSchedulerVO schd) throws Exception {
        TriggerKey triggerKey = getTriggerKey(schd);
        scheduler.resumeTrigger(triggerKey);
    }

    @Override
    public void addJob(QuartzSchedulerVO schd) throws Exception {
        JobKey jobKey = getJobKey(schd);
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        if (jobDetail == null) {
            @SuppressWarnings("unchecked")
            Class<Job> jobClass = (Class<Job>) Class.forName(schd.getJobDetail().getJobClass());
            JobBuilder jobBuilder = JobBuilder.newJob(jobClass).withIdentity(jobKey);
            if (schd.getJobDetail().getNeedStore()) {
                LOGGER.info("作业将持久化到数据库中(哪怕没有触发器对应)");
                jobBuilder.storeDurably(true);
            }
            jobDetail = jobBuilder.build();
            scheduler.addJob(jobDetail, true);
        }
    }

    @Override
    public boolean deleteJob(QuartzSchedulerVO schd) throws Exception {
        JobKey jobKey = getJobKey(schd);
        return scheduler.deleteJob(jobKey);
    }

    @Override
    public void pauseJob(QuartzSchedulerVO schd) throws Exception {
        JobKey jobKey = getJobKey(schd);
        scheduler.pauseJob(jobKey);
    }

    @Override
    public void resumeJob(QuartzSchedulerVO schd) throws Exception {
        JobKey jobKey = getJobKey(schd);
        scheduler.resumeJob(jobKey);
    }

    @Override
    public void triggerJobNow(QuartzSchedulerVO schd) throws Exception {
        JobKey jobKey = getJobKey(schd);
        scheduler.triggerJob(jobKey);
    }

    @Override
    public boolean interuptJob(QuartzSchedulerVO schd) throws Exception {
        JobKey jobKey = getJobKey(schd);
        return scheduler.interrupt(jobKey);
    }

    private JobKey getJobKey(QuartzSchedulerVO schd) {
        JobDetailVO jobVO = schd.getJobDetail();
        return JobKey.jobKey(jobVO.getJobName(), jobVO.getJobGroup());
    }

    private TriggerKey getTriggerKey(QuartzSchedulerVO schd) {
        TriggerVO triggerVO = schd.getTrigger();
        return TriggerKey.triggerKey(triggerVO.getTriggerName(), triggerVO.getTriggerGroup());
    }

    private void triggerBuilderHandler(TriggerVO triggerVO, TriggerBuilder<Trigger> tb) throws ParseException {
        ScheduleBuilder<?> schedulerBuilder;
        TriggerVO.TriggerType type = triggerVO.getType();
        switch (type) {
            case SIMPLE:
                schedulerBuilder = SimpleScheduleBuilder.repeatSecondlyForTotalCount(triggerVO.getRepeatCount(), triggerVO.getRepeatInterval().intValue()).withMisfireHandlingInstructionIgnoreMisfires();
                if (triggerVO.getExecuteDelay() != null && triggerVO.getExecuteDelay() > 0) {
                    Date startDate = new Date(System.currentTimeMillis() + triggerVO.getExecuteDelay());
                    tb.startAt(startDate);
                }
                tb.withSchedule(schedulerBuilder);
                break;
            case CRON:
                CronExpression.validateExpression(triggerVO.getCronExp());
                schedulerBuilder = CronScheduleBuilder.cronSchedule(triggerVO.getCronExp());
                tb.withSchedule(schedulerBuilder);
                break;
            case TARGET_TIME:
                tb.startAt(triggerVO.getTargetTime());
                break;
            default:
                LOGGER.error("未找到对应的触发器类型!");
                throw new IllegalArgumentException("创建调度所使用的参数错误!");
        }
    }
}
