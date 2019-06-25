package com.myself.quartz.test.controller;

import com.myself.quartz.test.service.SchedulerRemoteService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 类名称：SchController<br>
 * 类描述：<br>
 * 创建时间：2019年06月25日<br>
 *
 * @author maopanpan
 * @version 1.0.0
 */
@RestController
public class SchController {
    private final SchedulerRemoteService schedulerRemoteService;

    public SchController(SchedulerRemoteService schedulerRemoteService) {
        this.schedulerRemoteService = schedulerRemoteService;
    }

    @GetMapping(value = "sayHello")
    public String sayHello() {
        return "Hello world!";
    }
}
