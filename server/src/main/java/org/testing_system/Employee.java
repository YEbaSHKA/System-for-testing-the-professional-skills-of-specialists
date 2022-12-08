package org.testing_system;

import java.io.Serializable;

public class Employee implements Serializable {
    private int id;
    private String login;
    private String password;
    private String full_name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }
    
    public void setLogin(String login) {
        this.login = login;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getFull_name() {
        return full_name;
    }
    
    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }
    
    @Override
    public String toString() {
        return "Employee [id=" + id + ", login=" + login + ", password=" + password + ", full_name=" + full_name + "]";
    }

}
