package com.dianwoba.cn;

import com.dianwoba.cn.domain.Person;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.logging.Logger;

/**
 * Created by leizhen on 2017/6/5.
 * code is far away from bugs with the god animal protecting
 * I love animals. They taste delicious.
 */
public class PersonTest {

    @Test
    public void testPerson() {
        Person p = new Person("Joe Q Author", 42, 173, 82, "Brown", "MALE");
        Logger logger = Logger.getLogger(Person.class.getName());
        logger.info("Name: " + p.getName());
        logger.info("Age: " + p.getAge());
        logger.info("Height(cm): " + p.getHeight());
        logger.info("Weight(kg): " + p.getWeight());
        logger.info("Eye Color: " + p.getEyeColor());
        logger.info("Gender: " + p.getGender());

        Assert.assertEquals("Joe Q Author", p.getName());
    }


}
