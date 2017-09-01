package com.yang.springboot.service.impl;

import com.yang.springboot.model.SysUser;
import com.yang.springboot.repository.SysUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author Yangjing
 */
public class UserService implements UserDetailsService {//自定义需实现UserDetailsService接口

    @Autowired
    private SysUserRepository sysUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {//重写方法获得用户
        SysUser sysUser = sysUserRepository.findByUsername(username);
        if (sysUser == null) {
            throw new UsernameNotFoundException("用户名不存在！");
        }
        return sysUser;//我们当前的用户实现了UserDetails接口，所以可以直接返回给Spring Security使用
    }
}
