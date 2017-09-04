package com.yang.springboot.security;

import com.yang.springboot.service.impl.SysUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author Yangjing
 */
@Configuration
public class MyWebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public SysUserService userService(){
        return new SysUserService();
    }

     @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService());//添加我们自定义的user detail service认证
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest().authenticated()//所有的请求需要认证即登录后才能访问
                .and()
                .formLogin()
                    .loginPage("/login")
                    .failureUrl("/login?error")
                    .permitAll()//定制登录行为，登录页面可任意访问
                .and()
                .logout().permitAll();//定制注销行为，注销请求可任意访问

    }
}
