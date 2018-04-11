package com.zifangdt.ch.market.api;

import com.zifangdt.ch.base.dto.todo.TodoTask;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@FeignClient(value = "TODO-SERVER")
public interface TodoServerApi {
    @PostMapping("/tasks")
    Long save(@Valid @RequestBody TodoTask todoTask);
}
