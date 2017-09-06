package com.yang.springboot.d_batch;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author Yangjing
 */
@Getter
@Setter
public class People implements Serializable {

    //用JSR-303注解来校验数据
    //@Size(max = 12, min = 2)
    private String name;

    private String  age;

    private String nation;

    private String address;

}
