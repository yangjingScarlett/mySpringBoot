package com.yang.springboot.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Yangjing
 */
@Getter
@Setter
public class Msg {
    private String title;
    private String content;
    private String etraInfo;

    public Msg(String title, String content, String etraInfo) {
        super();
        this.title = title;
        this.content = content;
        this.etraInfo = etraInfo;
    }
}
