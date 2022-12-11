package org.testing_system;

import java.io.Serializable;

public class ResultOfTest implements Serializable
{
    private int id_employee;

    private String topic_name;
    
    private String test_name;

    private int result_of_test;

    public ResultOfTest()
    {
        id_employee = 0;
        topic_name = "";
        test_name = "";
        result_of_test = 0;
    }

    public ResultOfTest(int id_employee2, String topic_name2, String test_name2, int result_of_test2)
    {
        this.id_employee = id_employee2;
        this.topic_name = topic_name2;
        this.test_name = test_name2;
        this.result_of_test = result_of_test2;
    }

    public int getId_employee() {
        return id_employee;
    }

    public void setId_employee(int id_employee) {
        this.id_employee = id_employee;
    }

    public String getTopic_name() {
        return topic_name;
    }

    public void setTopic_name(String topic_name) {
        this.topic_name = topic_name;
    }

    public String getTest_name() {
        return test_name;
    }

    public void setTest_name(String test_name) {
        this.test_name = test_name;
    }

    public int getResult_of_test() {
        return result_of_test;
    }

    public void setResult_of_test(int result_of_test) {
        this.result_of_test = result_of_test;
    }

    @Override
    public String toString() {
        return "ResultOfTest [id_employee=" + id_employee + ", topic_name=" + topic_name + ", test_name=" + test_name
                + ", result_of_test=" + result_of_test + "]";
    }
}
