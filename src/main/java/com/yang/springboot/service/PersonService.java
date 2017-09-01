package com.yang.springboot.service;

import com.yang.springboot.model.Person;

/**
 * @author Yangjing
 */
public interface PersonService {

    public Person savePersonWithRollBack(Person person);
    public Person savePersonWithoutRollBack(Person person);
}
