package com.dianwoba.cn.domain;

/**
 * Created by leizhen on 2017/6/5.
 * code is far away from bugs with the god animal protecting
 * I love animals. They taste delicious.
 */
public class Salary extends AbstractEmployee {

    private double salary;

    public Salary(String name, String address, int number, double salary) {
        super(name, address, number);
        this.salary = salary;
    }

    public void mailCheck() {
        System.out.println("Within mailCheck of Salary calss ");
        System.out.println("mailing check to " + getName() + " with salary" + salary);
    }

    public double computePay() {
        System.out.println("Computing salary pay for " + getName());
        return salary / 52;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        if (salary >= 0.0) {
            this.salary = salary;
        }
    }

    public static void main(String [] args)
    {
        Salary s = new Salary("Mohd Mohtashim", "Ambehta, UP", 3, 3600.00);
        AbstractEmployee e = new Salary("John Adams", "Boston, MA", 2, 2400.00);

        System.out.println("Call mailCheck using Salary reference --");
        s.mailCheck();

        System.out.println("\n Call mailCheck using Employee reference--");
        e.mailCheck();
    }


}
