package com.dianwoba.cn.service;

import com.dianwoba.cn.domain.Employee;

import java.math.BigDecimal;
import java.util.logging.Logger;

/**
 * Created by leizhen on 2017/6/5.
 * code is far away from bugs with the god animal protecting
 * I love animals. They taste delicious.
 */
public class HumanResourcesApplication {

    private static final Logger log = Logger.getLogger(HumanResourcesApplication.class.getName());

    public static void main(String[] args){
        Employee e = new Employee();
        e.setName("J Smith");
        e.setEmployeeNumber("0001");
        e.setTaxpayerIdentificationNumber("123-45-6789");
        e.setSalary(BigDecimal.valueOf(4500.0));
        e.printAudit(log);
    }
}
