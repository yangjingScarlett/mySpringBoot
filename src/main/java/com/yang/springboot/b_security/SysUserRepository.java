package com.yang.springboot.b_security;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Yangjing
 */
public interface SysUserRepository extends JpaRepository<SysUser, Long> {

    SysUser findByUsername(String username);
}
