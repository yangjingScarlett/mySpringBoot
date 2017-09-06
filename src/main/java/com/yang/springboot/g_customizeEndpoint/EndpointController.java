package com.yang.springboot.g_customizeEndpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Yangjing
 */
@RestController
public class EndpointController {

    @Autowired
    StatusService statusService;

    @RequestMapping("/change")
    public String changeStatus(String status) {
        statusService.setStatus(status);
        return "Success";
    }
}
