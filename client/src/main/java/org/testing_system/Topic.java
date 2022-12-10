package org.testing_system;

import java.io.Serializable;

public class Topic implements Serializable{
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Topic [name=" + name + "]";
    }
    
}
