package com.yang.springboot.a_jpa_rest_cache.controller;

import com.yang.springboot.a_jpa_rest_cache.model.Person;
import com.yang.springboot.a_jpa_rest_cache.repository.PersonRepository;
import com.yang.springboot.a_jpa_rest_cache.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.yang.springboot.a_jpa_rest_cache.controller.PersonSpecification.personFromBeijing;

/**
 * @author Yangjing
 */
@RestController
public class PersonController {

    //spring data jpa已自动注册为bean，所以可自动注入
    @Autowired
    private
    PersonRepository personRepository;

    @Autowired
    private PersonService personService;

    //http://localhost:8080/save?name=aa&address=beijing&age=22
    @RequestMapping("/save")
    public Person save(String name, String address, Integer age) {
        Person p = personRepository.save(new Person(null, name, age, address));
        return p;
    }

    //测试findByAddress
    //http://localhost:8080/q1?address=beijing
    @RequestMapping("/q1")
    public List<Person> q1(String address) {
        List<Person> people = personRepository.findByAddress(address);
        return people;
    }

    //测试findByNameAndAddress
    //person一定要序列化，并且所有的属性要定义成public，否则浏览器无法显示json数据
    //http://localhost:8080/q2?name=wangyunfei&address=hefei
    @RequestMapping(value = "/q2", produces = "application/json")
    public List<Person> q2(String name, String address) {
        List<Person> p = personRepository.findByNameAndAddress(name, address);
        return p;
    }

    //测试withNameAndAddressQuery
    //http://localhost:8080/q3?name=wangyunfei&address=hefei
    @RequestMapping("/q3")
    public List<Person> q3(String name, String address) {
        List<Person> p = personRepository.withNameAndAddressQuery(name, address);
        return p;
    }

    //测试withNameAndAddressNamedQuery
    //http://localhost:8080/q4?name=倾城&address=北京
    @RequestMapping("/q4")
    public List<Person> q4(String name, String address) {
        List<Person> people = personRepository.withNameAndAddressNamedQuery(name, address);
        return people;
    }

    //http://localhost:8080/sort
    @RequestMapping("/sort")
    public List<Person> sort() {
        List<Person> people = personRepository.findAll(new Sort(Sort.Direction.ASC, "age"));
        return people;
    }

    //http://localhost:8080/page
    @RequestMapping("/page")
    public Page<Person> page() {
        Page<Person> people = personRepository.findAll(new PageRequest(1, 2));
        return people;
    }

    //测试PersonSpecification的准则查询方法
    //http://localhost:8080/spec
    @RequestMapping("/spec")
    public List<Person> spec() {
        List<Person> people = personRepository.findAll(personFromBeijing());
        return people;
    }

    //测试声明式事务的回滚机制
    //http://localhost:8080/rollback?name=wangyunfei&age=32
    @RequestMapping("/rollback")
    public Person rollback(Person person) {
        return personService.savePersonWithRollBack(person);
    }

    //http://localhost:8080/noRollBack?name=wangyunfei&age=32
    @RequestMapping("/noRollBack")
    public Person noRollBack(Person person) {
        return personService.savePersonWithoutRollBack(person);
    }

    //测试spring boot的数据缓存
    //http://localhost:8080/put?name=ww&address=shijiazhuang&age=15
    @RequestMapping("/put")
    public Person put(Person person) {
        return personService.save(person);
    }

    //http://localhost:8080/evict?id=1
    @RequestMapping("/evict")
    public String remove(Long id) {
        personService.remove(id);
        return "success";
    }

    //http://localhost:8080/able?id=1
    @RequestMapping("/able")
    public Person findOne(Person person) {
        return personService.findOne(person);
    }
}
