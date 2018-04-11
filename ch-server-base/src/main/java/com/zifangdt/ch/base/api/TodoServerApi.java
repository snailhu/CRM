package com.zifangdt.ch.base.api;

import com.zifangdt.ch.base.dto.todo.TodoTask;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by 袁兵 on 2018/1/20.
 */
@FeignClient("todo-server")
public interface TodoServerApi {
    /**
     * 流程步骤提交后，同步对应的任务状态
     *
     * @param trackId
     */
    @PutMapping("/tasks/operateProcessTask?for=internal")
    void operateProcessTask(@RequestParam("trackId") Long trackId);

    /**
     * 创建类型非“待办”的任务，如流程对应的任务
     *
     * @param todoTask
     * @return
     */
    @PostMapping("/tasks?for=internal")
    Long autoCreateTask(@Valid @RequestBody TodoTask todoTask);

    @PutMapping("/tasks/operateTicketTask?for=internal")
    void operateTicketTask(@RequestParam("ticketId") Long ticketId);
}
