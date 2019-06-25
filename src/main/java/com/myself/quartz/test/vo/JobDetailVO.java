package com.myself.quartz.test.vo;

import lombok.Data;

/**
 * 类名称：JobDetailVO<br>
 * 类描述：<br>
 * 创建时间：2019年06月25日<br>
 *
 * @author maopanpan
 * @version 1.0.0
 */
@Data
public class JobDetailVO {
    /**
     * 作业名称
     */
    private String jobName;
    /**
     * 作业组名称
     */
    private String jobGroup;
    /**
     * 作业类路径
     */
    private String jobClass;
    /**
     * 作业是否需要持久化
     */
    private Boolean needStore = Boolean.FALSE;


}
