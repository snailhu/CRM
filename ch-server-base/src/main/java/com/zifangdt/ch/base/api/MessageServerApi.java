package com.zifangdt.ch.base.api;


import com.zifangdt.ch.base.dto.common.Announcement;
import com.zifangdt.ch.base.dto.common.Notice;
import com.zifangdt.ch.base.dto.todo.TodoTask;
import com.zifangdt.ch.base.dto.uaa.User;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "MESSAGE-SERVER", path = "/message")
public interface MessageServerApi {

    /**
     * 创建环信用户
     *
     * @param user : 需要创建的用户
     */
    @PostMapping("/createUser")
    void createUser(@RequestBody User user);

    /**
     * 发送消息
     *
     * @param users:  用户名列表
     * @param message
     */
    @PostMapping("/sendMessage")
    Boolean sendMessage(@RequestBody List<String> users, @RequestParam("message") String message);

    @PostMapping("/sendNotice")
    Boolean sendNotice(@RequestBody Notice notice);

    @PostMapping("/sendTodo")
    Boolean sendTodo(@RequestBody TodoTask todoTask);

    @PostMapping("/sendAnnouncement")
    Boolean sendAnnoucement(@RequestBody Announcement announcement);
}
