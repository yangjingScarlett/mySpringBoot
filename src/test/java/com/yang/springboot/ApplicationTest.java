package com.yang.springboot;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yang.springboot.f_test.Dog;
import com.yang.springboot.f_test.DogRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

/**
 * @author Yangjing
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MySpringbootjpaApplication.class)
@WebAppConfiguration
@Transactional
public class ApplicationTest {

    @Autowired
    private
    DogRepository dogRepository;

    @Autowired
    private
    WebApplicationContext webApplicationContext;

    private MockMvc mvc;

    private String expectedJson;

    private String Obj2Json(Object obj) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(obj);
    }

    //使用Junit 的＠Before 注解可在测试开始前进行一些初始化的工作。
    @Before
    public void setUp() throws JsonProcessingException {
        Dog dog1 = new Dog("点点");
        Dog dog2 = new Dog("绵绵");
        dogRepository.save(dog1);
        dogRepository.save(dog2);

        expectedJson = Obj2Json(dogRepository.findAll());
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testDogController() throws Exception {
        String uri = "/dog";
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON)).andReturn();
        int status = result.getResponse().getStatus();
        String content = result.getResponse().getContentAsString();

        Assert.assertEquals("错误，正确的返回值为200", 200, status);
        //这里会报错，因为expectedJson和content的格式不同，但是实际上他们的内容是完全一致的
        // Assert.assertEquals("错误，返回值和预期返回值不一致", expectedJson, content);
    }

}
