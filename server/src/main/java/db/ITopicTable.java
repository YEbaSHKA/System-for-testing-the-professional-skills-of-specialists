package db;

import java.util.ArrayList;

import org.testing_system.Topic;

public interface ITopicTable {
    ArrayList<Topic> get_topics();
    boolean update(Topic topic, String new_value);
    boolean insert(Topic topic);
    boolean check(Topic topic);
    boolean delete(Topic topic);
}
