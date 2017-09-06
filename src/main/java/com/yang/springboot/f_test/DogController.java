package com.yang.springboot.f_test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Yangjing
 */
@RestController
@RequestMapping("/dog")
public class DogController {

    @Autowired
    private DogRepository dogRepository;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Dog> dog() {
        /*//直接用这种形式，是可以的，dogRepository注入成功
        Dog dog1 = new Dog("点点");
        Dog dog2 = new Dog("绵绵");
        dogRepository.save(dog1);
        dogRepository.save(dog2);*/
        return dogRepository.findAll();
    }
}
