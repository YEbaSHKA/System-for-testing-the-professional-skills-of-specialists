package org.testing_system;

import java.io.Serializable;

public class Question implements Serializable
{
    private int id;
    
    private String name;

    private String first_answer;

    private String second_answer;

    private String third_answer;

    private int correct_answer_number;

    private int id_test;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirst_answer() {
        return first_answer;
    }

    public void setFirst_answer(String first_answer) {
        this.first_answer = first_answer;
    }

    public String getSecond_answer() {
        return second_answer;
    }

    public void setSecond_answer(String second_answer) {
        this.second_answer = second_answer;
    }

    public String getThird_answer() {
        return third_answer;
    }

    public void setThird_answer(String third_answer) {
        this.third_answer = third_answer;
    }

    public int getCorrect_answer_number() {
        return correct_answer_number;
    }

    public void setCorrect_answer_number(int correct_answer_number) {
        this.correct_answer_number = correct_answer_number;
    }

    public int getId_test() {
        return id_test;
    }

    public void setId_test(int id_test) {
        this.id_test = id_test;
    }

    @Override
    public String toString() {
        return "Question [id=" + id + ", name=" + name + ", first_answer=" + first_answer + ", second_answer="
                + second_answer + ", third_answer=" + third_answer + ", correct_answer_number=" + correct_answer_number
                + ", id_test=" + id_test + "]";
    }

}