package db;

import java.util.ArrayList;

import org.testing_system.Test;

public interface ITestTable {
    ArrayList<Test> get_tests_by_topic_and_type(String topic, String type);
    boolean update(Test test);
    boolean insert(Test test);
    boolean check(Test test);
    boolean delete(Test test);
}
