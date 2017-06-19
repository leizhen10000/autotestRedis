package com.dianwoba.cn.domain;

import java.math.BigDecimal;

/**
 * Created by leizhen on 2017/6/5.
 * code is far away from bugs with the god animal protecting
 * I love animals. They taste delicious.
 */
public class Employee extends Person{

    private String taxpayerIdentificationNumber;
    private String employeeNumber;
    private BigDecimal salary;

    public Employee(){
        super();
    }

    public Employee(String name, int age, int height, int weight, String eyeColor, String gender) {
        super(name, age, height, weight, eyeColor, gender);
    }

    public String getTaxpayerIdentificationNumber() {
        return taxpayerIdentificationNumber;
    }

    public void setTaxpayerIdentificationNumber(String taxpayerIdentificationNumber) {
        this.taxpayerIdentificationNumber = taxpayerIdentificationNumber;
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    @Override
    public void printAudit(StringBuilder builder){
        super.printAudit(builder);

        builder.append("TaxpayerIdentificationNumber=");
        builder.append(getTaxpayerIdentificationNumber());
        builder.append(",");builder.append("EmployeeNumber=");
        builder.append(getEmployeeNumber());
        builder.append(",");builder.append("Salary=");
        builder.append(getSalary().setScale(2).toPlainString());

    }
}
