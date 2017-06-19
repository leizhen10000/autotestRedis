package com.dianwoba.cn.domain;

import java.util.logging.Logger;

/**
 * Created by leizhen on 2017/6/5.
 * code is far away from bugs with the god animal protecting
 * I love animals. They taste delicious.
 */
public class Person {

    Logger logger = Logger.getLogger(Person.class.getName());
    public static final String STATE_DELIMITER = "~";

    public enum  Gender{
        MALE,
        FEMALE,
        UNKNOWN
    }

    private String name;
    private int age;
    private int height;
    private int weight;
    private String eyeColor;
    private String gender;

    public Person(){

    }

    public Person(String name, int age, int height, int weight, String eyeColor, String gender) {
        this.name = name;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.eyeColor = eyeColor;
        this.gender = gender;
    }

    public void printAudit(StringBuilder buffer) {
        buffer.append("Name=");
        buffer.append(getName());
        buffer.append(",");
        buffer.append("Age=");
        buffer.append(getAge());
        buffer.append(",");
        buffer.append("Height=");
        buffer.append(getHeight());
        buffer.append(",");
        buffer.append("Weight=");
        buffer.append(getWeight());
        buffer.append(",");
        buffer.append("EyeColor=");
        buffer.append(getEyeColor());
        buffer.append(",");
        buffer.append("Gender=");
        buffer.append(getGender());
    }

    public void printAudit(Logger l) {
        StringBuilder sb = new StringBuilder();
        printAudit(sb);
        l.info(sb.toString());
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getEyeColor() {
        return eyeColor;
    }

    public void setEyeColor(String eyeColor) {
        this.eyeColor = eyeColor;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
