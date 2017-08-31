package com.yang.springboot.controller;

import com.yang.springboot.model.Person;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * @author Yangjing
 * @explain Spring Data JPA 是基于Spring Data 的repository 之上，可以将repository 自动输出为REST资源。
 * 目前Spring Data REST 支持将Spring Data JPA 、Spring Data MongoDB 、Spring Data Neo4j 、Spring Data GemFire
 * 以及Spring Data Cassandra 的repository "自动转换" 成REST 服务。
 * <p>
 * 这就意味着使用Spring Data JPA的时候我们还需要自己创建restcontroller来讲repository转换成rest输出，但是
 * 使用Spring Data REST的时候我们什么都不用做，直接就可以用浏览器访问定义好的路径来输出数据
 * 例如本例中直接用postman访问：http://localhost:8080/persons；http://localhost:8080/persons/2;等
 */
public class PersonSpecification {

    public static Specification<Person> personFromBeijing() {
        return new Specification<Person>() {
            @Override
            public Predicate toPredicate(Root<Person> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get("address"), "beijing");
            }
        };
    }
}
