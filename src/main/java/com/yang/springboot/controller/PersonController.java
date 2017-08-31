package com.yang.springboot.controller;

import com.yang.springboot.model.Person;
import com.yang.springboot.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.yang.springboot.controller.PersonSpecification.personFromBeijing;

/**
 * @author Yangjing
 */
@RestController
public class PersonController {

    //spring data jpa已自动注册为bean，所以可自动注入
    @Autowired
    private
    PersonRepository personRepository;

    @RequestMapping("/save")
    public Person save(String name, String address, Integer age) {
        Person p = personRepository.save(new Person(null, name, age, address));
        return p;
    }

    //测试findByAddress
    @RequestMapping("/q1")
    public List<Person> q1(String address) {
        List<Person> people = personRepository.findByAddress(address);
        return people;
    }

    //测试findByNameAndAddress
    //person一定要序列化，并且所有的属性要定义成public，否则浏览器无法显示json数据
    @RequestMapping(value = "/q2", produces = "application/json")
    public Person q2(String name, String address) {
        Person p = personRepository.findByNameAndAddress(name, address);
        return p;
    }

    //测试withNameAndAddressQuery
    @RequestMapping("/q3")
    public Person q3(String name, String address) {
        Person p = personRepository.withNameAndAddressQuery(name, address);
        return p;
    }

    //测试withNameAndAddressNamedQuery
    @RequestMapping("/q4")
    public List<Person> q4(String name, String address) {
        List<Person> people = personRepository.withNameAndAddressNamedQuery(name, address);
        return people;
    }

    @RequestMapping("/sort")
    public List<Person> sort() {
        List<Person> people = personRepository.findAll(new Sort(Sort.Direction.ASC, "age"));
        return people;
    }

    @RequestMapping("/page")
    public Page<Person> page() {
        Page<Person> people = personRepository.findAll(new PageRequest(1, 2));
        return people;
    }

    //测试PersonSpecification的准则查询方法
    @RequestMapping("/spec")
    public List<Person> spec(){
        List<Person> people=personRepository.findAll(personFromBeijing());
        return people;
    }
}
