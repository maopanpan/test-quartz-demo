package com.myself.quartz.test.service;

import com.myself.quartz.test.vo.JobDetailVO;
import com.myself.quartz.test.vo.QuartzSchedulerVO;

import java.util.Date;

/**
 * 类名称：SchedulerRemoteService<br>
 * 类描述：Quartz API实现<br>
 * 创建时间：2019年06月25日<br>
 *
 * @author maopanpan
 * @version 1.0.0
 */
public interface SchedulerRemoteService {
    /**
     * 创建调度<br/>
     * 此方法会同时创建出作业实例和作业关联的一个触发器实例
     *
     * @param schd 调度详情
     * @return 创建的调度的执行时间
     * @throws Exception exception
     */
    Date createScheduler(QuartzSchedulerVO schd) throws Exception;

    /**
     * 删除调度<br/>
     * 此方法会删除作业实例和与该作业关联的触发器实例
     *
     * @param schd 调度详情
     * @return 删除调度的时间(非调度本身触发器的时间)
     * @throws Exception exception
     */
    Date deleteScheduler(QuartzSchedulerVO schd) throws Exception;

    /**
     * 为已存在的作业添加触发器
     *
     * @param schd 调度详情
     * @return 该作业下一次触发时间
     * @throws Exception exception
     */
    Date addTrigger(QuartzSchedulerVO schd) throws Exception;

    /**
     * 删除触发器
     *
     * @param schd 调度详情
     * @return 是否删除成功, 成功为true
     * @throws Exception exception
     */
    boolean deleteTrigger(QuartzSchedulerVO schd) throws Exception;

    /**
     * 暂停正在执行的触发器
     *
     * @param schd 调度详情
     * @throws Exception exception
     */
    void pauseTrigger(QuartzSchedulerVO schd) throws Exception;

    /**
     * 恢复已暂停的指定触发器
     *
     * @param schd 调度详情
     * @throws Exception exception
     */
    void resumeTrigger(QuartzSchedulerVO schd) throws Exception;

    /**
     * 新增作业(可没有关联的触发器),此方法要求作业必须持久化({@link JobDetailVO#needStore}为true)
     *
     * @param schd 调度详情
     * @throws Exception exception
     */
    void addJob(QuartzSchedulerVO schd) throws Exception;

    /**
     * 删除作业
     *
     * @param schd 调度详情
     * @return 正常删除返回true, 否则为false
     * @throws Exception exception
     */
    boolean deleteJob(QuartzSchedulerVO schd) throws Exception;

    /**
     * 暂停作业(正在运行中的也可以进行暂停)
     *
     * @param schd 调度详情
     * @throws Exception exception
     */
    void pauseJob(QuartzSchedulerVO schd) throws Exception;

    /**
     * 恢复作业(将暂停的作业恢复)
     *
     * @param schd 调度详情
     * @throws Exception exception
     */
    void resumeJob(QuartzSchedulerVO schd) throws Exception;

    /**
     * 马上执行作业
     *
     * @param schd 调度详情
     * @throws Exception exception
     */
    void triggerJobNow(QuartzSchedulerVO schd) throws Exception;

    /**
     * 中断作业
     *
     * @param schd 调度详情
     * @return 中断的结果, 成功返回true, 否则为false
     * @throws Exception exception
     */
    boolean interuptJob(QuartzSchedulerVO schd) throws Exception;
}
