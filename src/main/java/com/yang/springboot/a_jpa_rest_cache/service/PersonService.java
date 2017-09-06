package com.yang.springboot.a_jpa_rest_cache.service;

import com.yang.springboot.a_jpa_rest_cache.model.Person;

/**
 * @author Yangjing
 */
public interface PersonService {

    /**
     * 测试声明式事务中的回滚机制。事务机制是指开启事务，提交事务来进行数据操作，或者在发生错误时回滚数据
     * spring的事务机制是用统一的机制来处理不同数据访问技术的事务处理。提供了PlatformTransactionManager接口
     * 声明式事务：用注解来选择需要使用事务的方法，用@Transactional注解在方法上说明需要事务支持。被注解的方法在调用时，spring开启一个新的事务，当方法无异常运行结束后，spring会提交这个事务
     * Spring 提供了一个＠EnableTransactionManagement 注解在配置类上来开启声名式事务的支持。
     * Spring Data JPA 对所有的默认方法都开启了事务支持，且查询类事务默认启用readOnly ==true 属性。
     */
    public Person savePersonWithRollBack(Person person);
    public Person savePersonWithoutRollBack(Person person);

    /**
     * 测试spring boot的数据缓存机制
     */
    public Person save(Person person);
    public void remove(Long id);
    public Person findOne(Person person);
}
