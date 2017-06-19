package com.dianwoba.cn.service.readproperties;

import com.dianwoba.cn.domain.User;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.PropertiesBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

/**
 * Created by leizhen on 2017/5/26 0026.
 * code is far away from bugs with the god animal protecting
 * I love animals. They taste delicious.
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = "classpath:application.xml")
@Component
public class ReadProperties {

    // 通过Spring注解的形式，直接读取配置文件
    @Value("${riderHost}")
    private String riderHost;

    @Value("${url}")
    private String url;

    @Value("${driverClass}")
    private String driverClass;

    @Value("${password}")
    private String password;

    @Value("${username}")
    private String username;

    public String getDriverClass() {
        return driverClass;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public String getUrl() {
        return url;
    }

    public String getRiderHost() {
        return riderHost;
    }

    public void readLocalBean() {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:application.xml");
        ReadProperties readProperties = context.getBean(ReadProperties.class);
        System.out.println(readProperties.getUrl());
        System.out.println(readProperties.getRiderHost());
    }

    public void test1() {
        // 利用 ClassPathXmlApplicationContext 来配置文件
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:test/beanConfig.xml");
        User user = (User) context.getBean("testuser1");
        System.out.println("address: " + user.getAddress() + " id: " + user.getId());
    }

    public void test2(){
        BeanDefinitionRegistry registry = new DefaultListableBeanFactory();
        PropertiesBeanDefinitionReader reader = new PropertiesBeanDefinitionReader(registry);
        reader.loadBeanDefinitions(new ClassPathResource("test/beanConfig.xml"));
        BeanFactory factory = (BeanFactory) registry;
        User user = (User) factory.getBean("testuser1");
        System.out.println(user.getAddress());
    }

    public void test3(){
    }




    public static void main(String[] args) {
        ReadProperties readProperties = new ReadProperties();
        readProperties.test1();

    }
}
