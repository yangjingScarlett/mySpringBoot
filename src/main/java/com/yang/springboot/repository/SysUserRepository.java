package com.yang.springboot.repository;

import com.yang.springboot.model.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Yangjing
 */
public interface SysUserRepository extends JpaRepository<SysUser, Long> {

    SysUser findByUsername(String username);
}
