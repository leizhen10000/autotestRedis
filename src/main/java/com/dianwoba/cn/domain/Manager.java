package com.dianwoba.cn.domain;

/**
 * Created by leizhen on 2017/6/5.
 * code is far away from bugs with the god animal protecting
 * I love animals. They taste delicious.
 */
public class Manager extends Employee {

    private DirectReports directReports;

    public Manager(){
        this.directReports = new DirectReports();
    }

    private class DirectReports{
    }

    public static void main(String[] args){
        Manager manager = new Manager();
        Manager.DirectReports dr = manager.new DirectReports();

    }
}
