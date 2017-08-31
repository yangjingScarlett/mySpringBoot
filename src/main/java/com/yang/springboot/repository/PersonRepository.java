package com.yang.springboot.repository;

import com.yang.springboot.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Yangjing
 * @explain 1.Spring Data JPA 支持通过定义在Repository 接口中的方法名来定义查询，而方法名是根据实体类的属性名来确定的。
 * 2.[Spring Data JPA 支持find,findBy,read,readBy,query,queryBy,get,getBy这些类型的查询方法限制数量用top和first]。
 * 3.Spring Data JPA 支持用 JPA 的NameQuery 来定义查询方法，即一个名称映射一个查询,如：Person中的@NameQuery。
 * 4.Spring Data JPA 还支持用＠Query 注解在接口的方法上实现查询：即支持用索引号又支持用用名称 来匹配查询参数。
 * 5.Spring Data JPA 支持＠Modifying 和＠Query 注解组合来实现更新查询。
 * 6.JPA 提供了基于准则查询的方式，即Criteria 查询。而Spring Data JPA 提供了一个Specification （规范）接口让我们可以
 * 更方便地构造准则查询， Specification 接口定义了一个to Predicate 方法用来构造查询条件。
 * 为了做到这一点，我们的接口类必需实现JpaSpecificationExecutor 接口
 */
public interface PersonRepository extends JpaRepository<Person, Long>, JpaSpecificationExecutor<Person> {

    /**
     * 通过address相等查询，相当于JPQL：select p from Person p where p.address=?1
     */
    List<Person> findByAddress(String address);

    /**
     * 通过name和address查询，相当于JPQL：select p from Person p where p.name=?1 and p.address=?2
     */
    Person findByNameAndAddress(String name, String address);

    /**
     * 通过name like查询，相当于JPQL：select p from Person p where p.name like ?1
     */
    List<Person> findByNameLike(String name);

    //使用@Query查询，参数用名称来匹配
    @Query("select p from Person p where p.name= :name and p.address= :address")
    Person withNameAndAddressQuery(@Param("name") String name,
                                   @Param("address") String address);

    //使用@NamedQuery查询，注意我们在实体类中做的@NamedQuery的定义
    List<Person> withNameAndAddressNamedQuery(String name, String address);

    @Modifying
    @Query("update Person p set p.name=?1")
    @Transactional
    int setName(String name);//int表示更新语句影响的行数

    //这里就是为了将findByNameStartsWith方法暴露为REST资源
    //路径：http://localhost:8080/persons/search/nameStartsWith?name=w
    @RestResource(path = "nameStartsWith", rel = "nameStartsWith")
    List<Person> findByNameStartsWith(@Param("name") String name);
}
