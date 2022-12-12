package org.testing_system;

import java.io.Serializable;

public class Test implements Serializable
{
    private int id;
    
    private String name;

    private String topic_name;

    private String type;

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

    public String getTopic_name() {
        return topic_name;
    }

    public void setTopic_name(String topic_name) {
        this.topic_name = topic_name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Test [id=" + id + ", name=" + name + ", topic_name=" + topic_name + ", type=" + type + "]";
    }

}
