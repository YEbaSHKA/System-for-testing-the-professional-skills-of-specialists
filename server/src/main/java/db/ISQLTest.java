package db;

import java.util.ArrayList;

import org.testing_system.Test;

public interface ISQLTest {
    ArrayList<Test> get_tests_by_topic_and_type(String topic, String type);
}