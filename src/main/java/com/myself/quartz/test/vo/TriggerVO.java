package com.myself.quartz.test.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 类名称：TriggerVO<br>
 * 类描述：<br>
 * 创建时间：2019年06月25日<br>
 *
 * @author maopanpan
 * @version 1.0.0
 */
@Data
public class TriggerVO {
    /**
     * 触发器名称
     */
    private String triggerName;
    /**
     * 触发器所在的组名
     */
    private String triggerGroup;
    /**
     * 触发器类型
     */
    private TriggerType type;
    /**
     * 重复触发次数
     */
    private Integer repeatCount;
    /**
     * 重复触发间隔时间(秒)
     */
    private Long repeatInterval;
    /**
     * 第一次触发延迟时间(毫秒,从当前时刻算起)
     */
    private Long executeDelay;
    /**
     * cron表达式
     */
    private String cronExp;
    /**
     * 请务必使用<code>yyyy-MM-dd HH:mm:ss</code>的时间格式,时区设置为东八区
     * 一次性任务的触发时间
     */
    private Date targetTime;

    public TriggerVO(String triggerName, String triggerGroup, TriggerType type) {
        this.triggerName = triggerName;
        this.triggerGroup = triggerGroup;
        this.type = type;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Date getTargetTime() {
        return targetTime;
    }

    /**
     * 触发器类型枚举
     */
    public static enum TriggerType {
        /**
         * 简单的循环触发类型的触发器,该类型只根据{@link TriggerVO#executeDelay}(延时时间), {@link TriggerVO#repeatCount}(重复次数),
         * {@link TriggerVO#repeatInterval}(重复间隔) 三个属性的值来进行触发器设置
         */
        SIMPLE,
        /**
         * Cron表达式类型的触发器,该类型只根据{@link TriggerVO#cronExp}设置的表达式进行触发器设置
         */
        CRON,
        /**
         * 在预定时间一次性运行的触发器,该类型只根据{@link TriggerVO#targetTime}设置的时间进行触发器设置
         */
        TARGET_TIME;
    }
}
