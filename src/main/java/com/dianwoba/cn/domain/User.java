package com.dianwoba.cn.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by leizhen on 2017/5/11 0011.
 * code is far away from bugs with the god animal protecting
 * I love animals. They taste delicious.
 */
public class User implements Serializable {

    private static final long serialVersionUID = 2202194158877587572L;
    private int id;
    private String username;
    private Date birthday;
    private String sex;
    private String address;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
