package com.yang.springboot.g_customizeEndpoint;

import org.springframework.beans.BeansException;
import org.springframework.boot.actuate.endpoint.AbstractEndpoint;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author Yangjing
 */
//通过＠ConfigurationProperties 的设置，我们可以在application.yml中通过endpoints.status 配置我们的端点。
@ConfigurationProperties(prefix = "endpoints.status", ignoreUnknownFields = false)
//继承AbstractEndpoint 类， AbstractEndpoint 是Endpoint 接口的抽象实现，当前类一定要重写invoke 方法。
// 实现ApplicationContextAware 接口可以让当前类对Spring 容器的资源有意识，即可访问容器的资源。
public class StatusEndpoint extends AbstractEndpoint<String> implements ApplicationContextAware {

    ApplicationContext context;

    public StatusEndpoint() {
        super("status");
    }

    @Override
    public String invoke() {
        StatusService statusService = context.getBean(StatusService.class);
        return "The Current Status is: " + statusService.getStatus();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }
}
