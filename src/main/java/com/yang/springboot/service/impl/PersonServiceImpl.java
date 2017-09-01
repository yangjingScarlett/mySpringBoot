package com.yang.springboot.service.impl;

import com.yang.springboot.model.Person;
import com.yang.springboot.repository.PersonRepository;
import com.yang.springboot.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Yangjing
 */
@Service
public class PersonServiceImpl implements PersonService {
    private static final Logger logger = LoggerFactory.getLogger(PersonServiceImpl.class);

    @Autowired
    private PersonRepository personRepository;

    @Override
    @Transactional(rollbackFor = {IllegalArgumentException.class})
    public Person savePersonWithRollBack(Person person) {
        Person p = personRepository.save(person);
        if (person.getName().equals("wangyunfei")) {
            throw new IllegalArgumentException("wwangyunfei已存在，数据将回滚");
        }
        return p;
    }

    @Override
    @Transactional(noRollbackFor = {IllegalArgumentException.class})
    public Person savePersonWithoutRollBack(Person person) {
        Person p = personRepository.save(person);
        if (person.getName().equals("wangyunfei")) {
            throw new IllegalArgumentException("wwangyunfei虽然已存在，但是数据将不会回滚");
        }
        return p;
    }

    //对数据缓存的操作
    //如果缓存时没有指定key，那么方法参数作为key保存到缓存中
    @Override
    @CachePut(value = "people", key = "#person.id")//@CachePut添加新增的或更新的数据到缓存中，其中缓存名称为people，数据的key是person.id
    public Person save(Person person) {
        Person p = personRepository.save(person);
        logger.info("为ID为：" + person.getId() + "的数据建立缓存");
        return p;
    }

    @Override
    @CacheEvict(value = "people")//@CacheEvict从缓存（people）中删除key为id的数据
    public void remove(Long id) {
        personRepository.delete(id);
        logger.info("删除了ID为：" + id + "的数据缓存");
    }

    @Override
    @Cacheable(value = "people", key = "#person.id")
    public Person findOne(Person person) {
        Person p = personRepository.findOne(person.getId());
        logger.info("为id为：" + person.getId() + "的数据建立缓存");
        return p;
    }

}
