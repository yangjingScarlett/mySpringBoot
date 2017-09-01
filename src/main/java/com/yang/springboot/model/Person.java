package com.yang.springboot.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Yangjing
 * @explain 在此例中使用的注解也许和你平时经常使用的注解实体类不大一样，
 * 比如:没有使用@Table(实体类映射表名)、＠Column (属性映射字段名)注解。
 * 这是因为我们是采用正向工程通过实体类生成表结构，而不是通过逆向工程从表结构生成数据库。
 * @extra []里面的注释是根据情况自己做的猜测，并不确定准确
 */
@Getter
@Setter
@Entity//指明这是一个和数据库表映射的实体类,[hibernate会根据该实体类在数据库中创建相应的表，所以在data.sql文件中插入表中的属性要和该实体类中的对应]
@NamedQuery(name = "Person.withNameAndAddressNamedQuery", query = "select p from Person p where p.name=?1 and address=?2")
public class Person implements Serializable {

    @Id//指明这个属性映射为数据库表的主键
    @GeneratedValue//默认使用主键生成方式为自增，hibernate会自动生成一个HIBERNATE_SEQUENCE的序列
    public Long id;

    public String name;
    public Integer age;
    public String address;

    public Person() {
        super();
    }

    public Person(Long id, String name, Integer age, String address) {
        super();
        this.id = id;
        this.name = name;
        this.age = age;
        this.address = address;
    }

}
