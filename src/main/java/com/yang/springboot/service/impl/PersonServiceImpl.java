package com.yang.springboot.service.impl;

import com.yang.springboot.model.Person;
import com.yang.springboot.repository.PersonRepository;
import com.yang.springboot.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Yangjing
 */
@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Transactional(rollbackFor = {IllegalArgumentException.class})
    public Person savePersonWithRollBack(Person person) {
        Person p = personRepository.save(person);
        if (person.getName().equals("wangyunfei")) {
            throw new IllegalArgumentException("wwangyunfei已存在，数据将回滚");
        }
        return p;
    }

    @Transactional(noRollbackFor = {IllegalArgumentException.class})
    public Person savePersonWithoutRollBack(Person person) {
        Person p = personRepository.save(person);
        if (person.getName().equals("wangyunfei")) {
            throw new IllegalArgumentException("wwangyunfei虽然已存在，但是数据将不会回滚");
        }
        return p;
    }

}
