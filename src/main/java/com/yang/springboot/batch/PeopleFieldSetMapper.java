package com.yang.springboot.batch;

import com.yang.springboot.model.People;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

/**
 * @author Yangjing
 */
public class PeopleFieldSetMapper implements FieldSetMapper<People> {

    @Override
    public People mapFieldSet(FieldSet fieldSet) throws BindException {
        People people = new People();
        people.setName(fieldSet.readString("name"));
        people.setAge(fieldSet.readString("age"));
        people.setNation(fieldSet.readString("nation"));
        people.setAddress(fieldSet.readString("address"));
        return people;
    }
}
