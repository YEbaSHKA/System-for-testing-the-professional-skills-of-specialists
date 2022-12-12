package db;

import java.sql.SQLException;
import java.util.ArrayList;

import org.testing_system.Topic;

public class TopicTable implements ITopicTable{
    private static TopicTable instance;
    private TestingSystemDB dbConnection;

    private TopicTable() throws SQLException, ClassNotFoundException {
        dbConnection = dbConnection.getInstance();
    }

    public static synchronized TopicTable getInstance() throws SQLException, ClassNotFoundException 
    {
        if (instance == null) 
        {
            instance = new TopicTable();
        }
        return instance;
    }

    @Override
    public ArrayList<Topic> get_topics() 
    {
        String SQl = "SELECT * FROM Topics";
        ArrayList<String[]> sql_result = dbConnection.getArrayResult(SQl);
        ArrayList<Topic> topics = new ArrayList<>();
        for (String[] items : sql_result) 
        {
            Topic topic = new Topic();
            topic.setName(items[0]);
            topics.add(topic);
        }

        return topics;
    }

    @Override
    public boolean update(Topic topic, String new_value) 
    {
        String SQL = "UPDATE Topics SET name = '" + new_value + "' WHERE name = '" + topic.getName() + "'";

        return dbConnection.insert_values(SQL);
    }

    @Override
    public boolean insert(Topic test) {
        String SQL = "INSERT INTO Topics(name) VALUES('" + test.getName() + "')";
        return dbConnection.insert_values(SQL);
    }

    @Override
    public boolean check(Topic test) {
        String SQL = "SELECT * FROM Topics WHERE name = '" + test.getName() + "'";
        ArrayList<String[]> result = dbConnection.getArrayResult(SQL);
        return result.isEmpty();
    }

    @Override
    public boolean delete(Topic topic) {
        String SQL = "DELETE FROM Topics WHERE name = '" + topic.getName() + "'";
        return dbConnection.insert_values(SQL);
    }
}
