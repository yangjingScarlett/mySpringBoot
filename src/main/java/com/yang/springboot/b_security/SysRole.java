package com.yang.springboot.b_security;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author Yangjing
 */
@Getter
@Setter
@Entity
public class SysRole {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
}
