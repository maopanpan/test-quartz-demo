package com.myself.quartz.test.vo;

import lombok.Data;

/**
 * 类名称：QuartzSchedulerVO<br>
 * 类描述：<br>
 * 创建时间：2019年06月25日<br>
 *
 * @author maopanpan
 * @version 1.0.0
 */
@Data
public class QuartzSchedulerVO {
    /**
     * 作业详情
     */
    private JobDetailVO jobDetail;
    /**
     * 触发器详情
     */
    private TriggerVO trigger;
}
