package com.yang.springboot.h_customizeHealthIndicator;

import com.yang.springboot.g_customizeEndpoint.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

/**
 * @author Yangjing
 * @explain 定制自己的HealthIndicator 我们只需制定一个实现HealthIndicator 接口的类，并注册为Bean 即可。
 * 接着g中的例子，我们依然通过上例的status值决定健康情况，只有当status的值为running 时才为健康。
 */
@Component
public class StatusHealthIndicator implements HealthIndicator {

    @Autowired
    StatusService statusService;

    @Override
    public Health health() {
        String status = statusService.getStatus();
        if (status == null || !status.equals("running")) {
            return Health.down().withDetail("Error", "Not Running").build();
        }
        return Health.up().build();
    }
}
