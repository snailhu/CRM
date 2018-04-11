/*
 * Copyright 2012-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.boot.actuate.endpoint.mvc;

import com.zifangdt.ch.base.util.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.LoggersEndpoint;
import org.springframework.boot.actuate.endpoint.LoggersEndpoint.LoggerLevels;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.logging.LogLevel;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Adapter to expose {@link LoggersEndpoint} as an {@link MvcEndpoint}.
 *
 * @author Ben Hale
 * @author Kazuki Shimizu
 * @author Eddú Meléndez
 * @since 1.5.0
 */
@ConfigurationProperties(prefix = "endpoints.loggers")
public class LoggersMvcEndpoint extends EndpointMvcAdapter {

    @Autowired
    private DiscoveryClient client;

    private static final RestTemplate REST_TEMPLATE = new RestTemplate();
    private static final String CONFIG_SERVER = "config-server";

    private final LoggersEndpoint delegate;

    public LoggersMvcEndpoint(LoggersEndpoint delegate) {
        super(delegate);
        this.delegate = delegate;
    }

    @ActuatorGetMapping("/{name:.*}")
    @ResponseBody
    @HypermediaDisabled
    public Object get(@PathVariable String name) {
        if (!this.delegate.isEnabled()) {
            // Shouldn't happen - MVC endpoint shouldn't be registered when delegate's
            // disabled
            return getDisabledResponse();
        }
        LoggerLevels levels = this.delegate.invoke(name);
        return (levels == null ? ResponseEntity.notFound().build() : levels);
    }

    @ActuatorPostMapping("/{name:.*}")
    @ResponseBody
    @HypermediaDisabled
    public Object set(@PathVariable String name,
                      @RequestBody Map<String, String> configuration,
                      HttpServletRequest request) {
        if (!this.delegate.isEnabled()) {
            // Shouldn't happen - MVC endpoint shouldn't be registered when delegate's
            // disabled
            return getDisabledResponse();
        }
        try {
            //获取所有eureka客户端并设置日志级别
            String requestIp = WebUtil.getRequestIp();
            int requestPort = request.getLocalPort();
            for (String serviceId : client.getServices()) {
                for (ServiceInstance serviceInstance : client.getInstances(serviceId)) {
                    if (serviceId.equals(CONFIG_SERVER)) {
                        continue;
                    }
                    if (serviceInstance.getHost().equals(requestIp) &&
                            serviceInstance.getPort() == requestPort) {
                        continue;
                    }
                    String url = serviceInstance.getUri() + request.getRequestURI();
                    REST_TEMPLATE.postForObject(url, configuration, Void.class);
                }
            }

            LogLevel logLevel = getLogLevel(configuration);
            this.delegate.setLogLevel(name, logLevel);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    private LogLevel getLogLevel(Map<String, String> configuration) {
        String level = configuration.get("configuredLevel");
        return (level == null ? null : LogLevel.valueOf(level.toUpperCase()));
    }

}
