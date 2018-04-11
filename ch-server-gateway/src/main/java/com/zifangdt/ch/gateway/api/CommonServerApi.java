package com.zifangdt.ch.gateway.api;

import com.zifangdt.ch.base.dto.common.SystemLog;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Created by 袁兵 on 2017/11/1.
 */
@FeignClient("COMMON-SERVER")
public interface CommonServerApi {

    @PostMapping("/logs")
    void saveLog(@RequestBody SystemLog log);
}
