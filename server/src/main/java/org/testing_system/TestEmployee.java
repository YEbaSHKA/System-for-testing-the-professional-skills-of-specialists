package org.testing_system;

import java.io.Serializable;

public class TestEmployee implements Serializable
{
    private int id_test;

    private int id_employee;

    private int result;

    public int getId_test() {
        return id_test;
    }

    public void setId_test(int id_test) {
        this.id_test = id_test;
    }

    public int getId_employee() {
        return id_employee;
    }

    public void setId_employee(int id_employee) {
        this.id_employee = id_employee;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "TestEmployee [id_test=" + id_test + ", id_employee=" + id_employee + ", result=" + result + "]";
    }
}
